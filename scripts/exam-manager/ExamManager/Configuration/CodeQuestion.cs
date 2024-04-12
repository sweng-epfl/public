namespace ExamManager.Configuration;

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

using ExamManager.Utils;

public sealed record CodeQuestion(string Name, string GradescopeId, string Path,
                                  IReadOnlyList<string> Files,
                                  string? MinimalTestsDirectory,
                                  string? GradedTestsDirectory,
                                  IReadOnlyDictionary<string, decimal>? GradedTestsPoints,
                                  IReadOnlyList<string>? CoverageClasses,
                                  IReadOnlyDictionary<decimal, decimal>? StatementCoverageThresholds,
                                  IReadOnlyDictionary<decimal, decimal>? BranchCoverageThresholds)
    : Question(QuestionType.Code, Name, GradescopeId)
{
    public override async Task<IReadOnlyCollection<FileInfo>> GetFilesToManuallyGradeAsync(DirectoryInfo userDir, DirectoryInfo skeletonDir)
    {
        var questionDir = userDir.SubDirectory(Path);

        // Only care about files that changed
        var files =
            FileSystemUtils.FindFilesMatching(questionDir, Files)
                           .Where(f => FileSystemUtils.HasSignificantDiff(f, skeletonDir.File(userDir.RelativePath(f))))
                           .ToArray();

        if (files.Length == 0)
        {
            // Don't spend time running tests if nothing changed
            return Array.Empty<FileInfo>();
        }

        // If there are minimal tests, only take into account users whose code passes them in at least one commit
        // (we're being nice; we could in theory require the latest commit to pass them, but exams are stressful)
        if (MinimalTestsDirectory != null)
        {
            var (r, _) = await Git.ExecuteOnLatestSuccessfulCommitAsync(questionDir, async () =>
            {
                var testResults = await RunCustomTestsAsync(questionDir, new DirectoryInfo(MinimalTestsDirectory));
                if (testResults != null && testResults.All(r => r.Success))
                {
                    return testResults;
                }
                return null;
            });
            if (r == null)
            {
                return Array.Empty<FileInfo>();
            }
        }

        // If there are graded tests, upload a summary of which ones pass (on the version of the code we upload)
        if (GradedTestsDirectory != null)
        {
            var testResults = await RunCustomTestsAsync(questionDir, new DirectoryInfo(GradedTestsDirectory));
            // capital letters for failures, and put the status first so it's easy to see at a glance what's failing
            var summary = testResults == null
                ? "BUILD FAILURE"
                : string.Join(Environment.NewLine, testResults.OrderBy(r => r.FullName)
                                                              .Select(r => $"{(r.Success ? "pass" : "FAIL")}: {r.FullName}"));
            files = files.Prepend(FileSystemUtils.CreateTempFile("_summary", summary)).ToArray();

            // Reset the repo since we just ran some custom tests
            await Git.ResetAsync(userDir);
        }

        return files;
    }

    public override async Task<IReadOnlyDictionary<User, Report>> GradeAsync(FileInfo report, IReadOnlyDictionary<User, DirectoryInfo> userDirs)
    {
        // This will not return missing data, i.e., those who failed basic checks,
        // which is why we use reports.Keys when checking graded tests + coverage and not config.Users;
        // we fill in missing values at the end
        var (qualityPoints, reports) = await Gradescope.ParseReportsAsync(report, userDirs.Keys.ToArray(), Name);

        var gradedTestsPoints = GradedTestsPoints?.Sum(p => p.Value) ?? 0;
        if (GradedTestsDirectory != null)
        {
            if (GradedTestsPoints == null)
            {
                throw new Exception($"{nameof(GradedTestsDirectory)} and {nameof(GradedTestsPoints)} should be either both set or both not set.");
            }

            var userReports = await reports.Keys.ParallelSelectWithProgressAsync($"Running graded tests for {Name}", async user =>
            {
                var questionDir = userDirs[user].SubDirectory(Path);

                var (results, commit) = await Git.ExecuteOnLatestSuccessfulCommitAsync(
                    questionDir,
                    () => RunCustomTestsAsync(questionDir, new DirectoryInfo(GradedTestsDirectory))
                );

                const string Title = "Code functionality (graded automatically)";

                // Should never happen unless the user rewrote history
                if (results == null)
                {
                    return new Report(
                        Title,
                        new[] { new ReportEntry("Our graded unit tests could not run against any version of your code.", 0, gradedTestsPoints) },
                        Array.Empty<Report>()
                    );
                }

                if (results.Count != GradedTestsPoints.Count)
                {
                    // Should never happen, ever
                    throw new Exception($"{results.Count} test results, expected {GradedTestsPoints.Count}");
                }

                return new Report(
                    Title,
                    results.OrderBy(res => res.FullName)
                           .Select(res =>
                        new ReportEntry(
                            res.Success ? res.DisplayName : ("Test failed: " + res.DisplayName + ": " + res.Message),
                            res.Success ? GradedTestsPoints[res.FullName] : 0,
                            GradedTestsPoints[res.FullName]
                        )
                    ).Append(
                        GetCommitInfoEntry(commit, "automated tests")
                    ).ToArray(),
                    Array.Empty<Report>()
                );
            });
            foreach (var (user, userReport) in userReports)
            {
                reports[user] = reports[user].AppendSubReport(userReport);
            }
        }

        var coveragePoints =
              (StatementCoverageThresholds?.Max(p => p.Value) ?? 0)
            + (BranchCoverageThresholds?.Max(p => p.Value) ?? 0);
        if (CoverageClasses != null)
        {
            if (StatementCoverageThresholds == null && BranchCoverageThresholds == null)
            {
                throw new Exception("There must be at least one kind of coverage thresholds");
            }

            var userReports = await reports.Keys.ParallelSelectWithProgressAsync($"Computing coverage for {Name}", async user =>
            {
                var questionDir = userDirs[user].SubDirectory(Path);

                var coverageEntries = new List<ReportEntry>();
                var (coverage, commit) = await Git.ExecuteOnLatestSuccessfulCommitAsync(questionDir, async () =>
                {
                    var testResults = await Gradle.RunTestsAsync(questionDir);
                    if (testResults == null)
                    {
                        return null;
                    }
                    return await Gradle.GetCoverageAsync(questionDir, testResults.Where(r => r.Success).Select(r => r.FullName).ToArray());
                });

                const string Title = "Coverage";

                // Should never happen unless the user rewrote history
                if (coverage == null)
                {
                    return new Report(
                        Title,
                        new[] { new ReportEntry("No version of your code could build.", 0, coveragePoints) },
                        Array.Empty<Report>()
                    );
                }

                var sumCoverage = coverage.SumFor(CoverageClasses);
                if (StatementCoverageThresholds != null)
                {
                    var obtained = Interpolate.Linear(sumCoverage.Statement.Fraction, StatementCoverageThresholds);
                    var available = StatementCoverageThresholds.Select(p => p.Value).Max();
                    coverageEntries.Add(new ReportEntry($"Statement coverage (passing tests only): {sumCoverage.Statement.Percent:0.##}%", obtained, available));
                }
                if (BranchCoverageThresholds != null)
                {
                    var obtained = Interpolate.Linear(sumCoverage.Branch.Fraction, BranchCoverageThresholds);
                    var available = BranchCoverageThresholds.Select(p => p.Value).Max();
                    coverageEntries.Add(new ReportEntry($"Branch coverage (passing tests only): {sumCoverage.Branch.Percent:0.##}%", obtained, available));
                }
                coverageEntries.Add(GetCommitInfoEntry(commit, "coverage"));

                return new Report(
                    Title,
                    coverageEntries,
                    Array.Empty<Report>()
                );
            });
            foreach (var (user, userReport) in userReports)
            {
                reports[user] = reports[user].AppendSubReport(userReport);
            }
        }

        var totalPoints = qualityPoints + gradedTestsPoints + coveragePoints;
        var missingText = MinimalTestsDirectory == null
            ? "No answer given"
            : "No answer complete enough to grade";
        foreach (var user in userDirs.Keys)
        {
            if (!reports.ContainsKey(user))
            {
                reports[user] = new Report(
                    Name,
                    new[] { new ReportEntry(missingText, 0, totalPoints) },
                    Array.Empty<Report>()
                );
            }
        }

        return reports;
    }

    private static async Task<IReadOnlyList<TestResult>?> RunCustomTestsAsync(DirectoryInfo questionDir, DirectoryInfo customTestsDir)
    {
        var testsDir = questionDir.SubDirectory("src").SubDirectory("test").SubDirectory("java");

        // Might happen if it was either deleted or we're going back in old commits without the exam
        if (!testsDir.Exists)
        {
            return null;
        }

        // First delete anything test-related to ensure there are no conflicts
        // between the user's tests and the graded ones
        testsDir.Delete(true);

        testsDir.Create();
        FileSystemUtils.CopyContentsRecursively(customTestsDir, testsDir);

        return await Gradle.RunTestsAsync(questionDir);
    }

    private static ReportEntry GetCommitInfoEntry(GitCommit commit, string purpose)
    {
        // 7 chars is enough for a commit, per https://stackoverflow.com/a/18134919
        return new ReportEntry($"Commit used for {purpose}: `{commit.Hash[..7]}` dated {commit.Date} (the latest one in which your code compiles and follows our grading compatibility instructions)", 0, 0);
    }
}
