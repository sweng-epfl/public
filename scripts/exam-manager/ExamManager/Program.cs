namespace ExamManager;

using System;
using System.Collections.Generic;
using System.CommandLine;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

using ExamManager.Configuration;
using ExamManager.Utils;

public static class Program
{
    private static readonly DirectoryInfo EvaluationsDirectory = new("evaluations");
    private static readonly DirectoryInfo ReportsDirectory = new("reports");
    private const string ReportsSummaryName = "summary.csv";
    private const string ReportName = "GRADING_REPORT.md";

    private const string ReportsGradePlaceholder = "!!GRADE!!"; // do not use $ here, it'd conflict with TeX notation in GitHub

    private static readonly Config _config;
    private static readonly GitHub _gitHub;

    // The confirmation prompts always include the count since it differs wildly between an actual exam and a test run with a few people
    // and will thus help in case one forgets to switch to the right users config

    // Note that all GitHub-related tasks must not be done in parallel, as it would run afoul of GitHub's secondary rate limits

    private static async Task PublishExam()
    {
        ConfirmationPrompt.Show($"hide and publish {_config.Users.Count} repos");

        var skeletonRepoDir = await _gitHub.CloneSkeletonRepoAsync();
        foreach (var user in _config.Users.WithProgress("Publishing"))
        {
            var userRepoDir = await _gitHub.GetUserRepoAsync(user.MainId, user.Email);

            await _gitHub.HideRepoAsync(user.MainId);

            foreach (var item in _config.ExtraItems.Append(_config.ExamDirectory))
            {
                FileSystemUtils.CopyRecursively(skeletonRepoDir, userRepoDir, item);
            }

            // Theoretically the files should already be executable in the skeleton repo;
            // this is defense in depth in case someone made a mistake
            var examDir = userRepoDir.SubDirectory(_config.ExamDirectory);
            var executablePatterns = _config.ExecutableFilenames.Select(n => "**/" + n).ToArray();
            foreach (var file in FileSystemUtils.FindFilesMatching(examDir, executablePatterns))
            {
                await Git.MakeFileExecutableAsync(file);
            }

            await Git.PushChangesAsync(userRepoDir, "Publish " + _config.ExamName);
        }

        Console.WriteLine();
        Console.WriteLine("NOTE: The repos are all hidden now!");
        Console.WriteLine();
    }

    private static async Task Reinvite()
    {
        foreach (var user in _config.Users.WithProgress("Inviting"))
        {
            await _gitHub.GetUserRepoAsync(user.MainId, user.Email);
        }
    }

    private static async Task Show(string[] ids)
    {
        if (ids.Length == 0)
        {
            ConfirmationPrompt.Show($"show to {_config.Users.Count} people");
        }

        foreach (var user in FindUsers(ids).WithProgress("Showing"))
        {
            await _gitHub.ShowRepoAsync(user.MainId);
        }
    }

    private static async Task Freeze(string[] ids)
    {
        if (ids.Length == 0)
        {
            ConfirmationPrompt.Show($"freeze for {_config.Users.Count} people");
        }

        foreach (var user in FindUsers(ids).WithProgress("Freezing"))
        {
            await _gitHub.FreezeRepoAsync(user.MainId);
        }
    }

    private static async Task Hide(string[] ids)
    {
        if (ids.Length == 0)
        {
            ConfirmationPrompt.Show($"hide from {_config.Users.Count} people");
        }

        foreach (var user in FindUsers(ids).WithProgress("Hiding"))
        {
            await _gitHub.HideRepoAsync(user.MainId);
        }

        // This is useful to double-check that missing students in an exam didn't push code from outside the room
        if (ids.Length != 0)
        {
            Console.WriteLine("Last pushes:");
            foreach (var user in FindUsers(ids))
            {
                Console.WriteLine($"\t{user}: {_gitHub.GetLastPush(user.MainId):F}");
            }
        }
    }

    private static async Task FindUnchanged(int minutes)
    {
        var unchanged = new List<User>();
        var now = DateTimeOffset.UtcNow;

        foreach (var user in _config.Users.WithProgress("Checking"))
        {
            if (!await _gitHub.IsRepoShownAsync(user.MainId))
            {
                // User has no access to the repo, likely because it was hidden after they did not show up, ignore
                continue;
            }

            var lastPush = _gitHub.GetLastPush(user.MainId);
            if (now - lastPush > TimeSpan.FromMinutes(minutes))
            {
                unchanged.Add(user);
            }
        }

        Console.WriteLine($"{unchanged.Count} unchanged");
        foreach (var user in unchanged)
        {
            Console.WriteLine($"\t{user}");
        }
    }

    private static async Task FindFailingCI()
    {
        var failing = new List<User>();
        foreach (var user in _config.Users.WithProgress("Checking"))
        {
            if (await _gitHub.IsBuildFailingAsync(user.MainId))
            {
                failing.Add(user);
            }
        }

        Console.WriteLine($"{failing.Count} failing");
        foreach (var user in failing)
        {
            Console.WriteLine($"\t{user}");
        }
    }

    private static async Task DangerousReplace(string[] items)
    {
        Console.WriteLine("This will OVERWRITE the files/directories you picked. DO NOT USE for things that may have had modifications!");
        ConfirmationPrompt.Show($"dangerously overwrite for {_config.Users.Count} people");

        // The exam repo is already cloned in the setup

        // Instead of cloning all at once, do clone+push per user to minimize the window of time in which there could be conflicts
        var skeletonRepoDir = await _gitHub.CloneSkeletonRepoAsync();
        foreach (var user in _config.Users.WithProgress("Replacing"))
        {
            var wasVisible = await _gitHub.IsRepoShownAsync(user.MainId);
            if (wasVisible)
            {
                await _gitHub.HideRepoAsync(user.MainId);
            }

            var repoDir = await _gitHub.GetUserRepoAsync(user.MainId, user.Email);
            foreach (var item in items.Select(i => Path.Combine(_config.ExamDirectory, i)))
            {
                FileSystemUtils.CopyRecursively(skeletonRepoDir, repoDir, item);
            }
            await Git.PushChangesAsync(repoDir, "Overwrite files");

            if (wasVisible)
            {
                await _gitHub.ShowRepoAsync(user.MainId);
            }
        }
    }

    private static async Task GradescopeRoster()
    {
        var file = new DirectoryInfo(Path.GetTempPath()).File("gradescope-roster.csv");
        await Gradescope.DumpAsRosterAsync(_config.Users, file);
        Console.WriteLine($"Roster: {file.FullName}");
    }

    private static async Task GradescopeUpload()
    {
        Console.Write("Email: ");
        var email = Console.ReadLine()?.Trim();
        Console.Write("Password: ");
        var password = Console.ReadLine()?.Trim();

        if (email == null || password == null)
        {
            // user canceled
            return;
        }

        await Gradescope.LoadTokenAsync(email, password);

        var (examSkeletonDir, examDirs) = await GetExamDirsAsync();

        foreach (var question in _config.Questions)
        {
            if (question.GradescopeId == null)
            {
                continue;
            }
            var filesPerUser = await _config.Users.ParallelSelectWithProgressAsync(
                $"Processing {question.Name}",
                user => question.GetFilesToManuallyGradeAsync(examDirs[user], examSkeletonDir)
            );
            foreach (var (user, files) in filesPerUser.WithProgress($"Uploading {question.Name}", printer: p => p.Item.ToString()))
            {
                if (files.Any())
                {
                    await Gradescope.UploadFilesAsync(
                        _config.GradescopeId,
                        question.GradescopeId,
                        user.MainId,
                        files
                    );
                }
            }
        }
    }

    private static async Task Grade()
    {
        var (_, examDirs) = await GetExamDirsAsync();

        var entries = _config.Users.ToDictionary(u => u, _ => new List<Report>());
        foreach (var question in _config.Questions)
        {
            var report = EvaluationsDirectory.File(question.Name + ".csv");
            if (!report.Exists)
            {
                throw new Exception($"Cannot find: {report.FullName}");
            }

            var questionReports = await question.GradeAsync(report, examDirs);
            foreach (var user in _config.Users)
            {
                entries[user].Add(questionReports[user]);
            }
        }

        // Ensure there's no old stuff laying around
        if (ReportsDirectory.Exists)
        {
            ReportsDirectory.Delete(true);
        }
        ReportsDirectory.Create();

        var points = new Dictionary<User, decimal>();
        foreach (var (user, reports) in entries)
        {
            var report = new Report(_config.ExamName + ": " + ReportsGradePlaceholder, Array.Empty<ReportEntry>(), reports);
            if (_config.ReportExtraRemark != null)
            {
                report = report.AppendEntry(new ReportEntry(_config.ReportExtraRemark, 0, 0));
            }
            File.WriteAllText(ReportsDirectory.File(user.MainId + Report.FileType).FullName, report.ToString());
            points.Add(user, report.GetPoints().Obtained);
        }
        GradesSummary.WritePoints(ReportsDirectory.File(ReportsSummaryName), points);
    }

    private static async Task PublishReports()
    {
        ConfirmationPrompt.Show($"publish {_config.Users.Count} grading reports");

        var grades = GradesSummary.ReadGrades(ReportsDirectory.File(ReportsSummaryName), _config.Users);

        foreach (var user in _config.Users.WithProgress("Publishing report"))
        {
            // Ensure we don't get any conflicts
            await _gitHub.FreezeRepoAsync(user.MainId);

            var reportFile = ReportsDirectory.File(user.MainId + Report.FileType);
            var repoDir = await _gitHub.GetUserRepoAsync(user.MainId, user.Email);
            var repoReportFile = repoDir.SubDirectory(_config.ExamDirectory).File(ReportName);

            reportFile.CopyTo(repoReportFile, overwrite: true);
            FileSystemUtils.ReplaceText(repoReportFile, ReportsGradePlaceholder, grades[user]);

            var message = "Publish grading report for " + _config.ExamName;
            await Git.PushChangesAsync(repoDir, message);
        }

        Console.WriteLine();
        Console.WriteLine("NOTE: The repos are all frozen now!");
        Console.WriteLine();
    }

    private static async Task Clone()
    {
        foreach (var user in _config.Users.WithProgress("Cloning"))
        {
            await _gitHub.GetUserRepoAsync(user.MainId, user.Email);
        }
        await _gitHub.CloneSkeletonRepoAsync();
    }

    private static async Task Cleanup()
    {
        ConfirmationPrompt.Show($"clean up the org and its {_config.Users.Count} users");
        ConfirmationPrompt.Show("delete repositories that I swear I have backed up");

        foreach (var user in _config.Users.WithProgress("Deleting"))
        {
            await _gitHub.DeleteRepoAsync(user.MainId);
        }
    }

    private static async Task<(DirectoryInfo, IReadOnlyDictionary<User, DirectoryInfo>)> GetExamDirsAsync()
    {
        var skeletonDir = await _gitHub.CloneSkeletonRepoAsync();
        var examSkeletonDir = skeletonDir.SubDirectory(_config.ExamDirectory);

        var examDirs = new Dictionary<User, DirectoryInfo>();
        foreach (var user in _config.Users.WithProgress("Cloning"))
        {
            var repoDir = await _gitHub.GetUserRepoAsync(user.MainId, user.Email);
            examDirs.Add(user, repoDir.SubDirectory(_config.ExamDirectory));
        }

        return (examSkeletonDir, examDirs);
    }

    private static IReadOnlyList<User> FindUsers(string[] ids)
    {
        if (ids.Length == 0)
        {
            return _config.Users;
        }
        return ids.Select(id => _config.Users.First(u => u.Ids.Contains(id))).ToArray();
    }


    static Program()
    {
        Console.Write("Initializing...");
        _config = Config.Load();
        // yes, sync over async, bad :-) but that way we know it's always initialized
        _gitHub = GitHub.InitializeAsync(_config.GithubToken, _config.GitHub).Result;
    }

    public static async Task<int> Main(string[] args)
    {
        // beginning of the message is in the cctor
        Console.WriteLine($"done; GitHub rate limit info: {_gitHub.GetRateLimitInfo()}.");
        Console.WriteLine();

        return await new RootCommand
        {
            new Command("publish-exam", description: "Hide repos and publish the exam")
            .With(PublishExam),

            new Command("reinvite", description: "Ensure all users have fresh invites if they haven't already joined")
            .With(Reinvite),

            new Command("show", description: "Show the exam to everyone or the given users")
            .With<string[]>(Show, "ids", "The IDs of the users, to show only to them"),

            new Command("freeze", description: "Freeze, i.e., make read-only, the exam from everyone or the given users")
            .With<string[]>(Freeze, "ids", "The IDs of the users, to freeze only for them"),

            new Command("hide", description: "Hide the exam from everyone or the given users")
            .With<string[]>(Hide, "ids", "The IDs of the users, to hide only from them"),

            new Command("find-unchanged", description: "Find which users have not pushed in the last N minutes")
            .With<int>(FindUnchanged, "minutes", "The number of minutes"),

            new Command("find-failing-ci", description: "Find which repos are currently failing CI in the main branch")
            .With(FindFailingCI),

            new Command("dangerous-replace", description: "DANGEROUS: Replace specific files/directories, if you really need to during an exam")
            .With<string[]>(DangerousReplace,"items", "The files/directories to replace, relative to the exam directory"),

            new Command("gradescope-roster", description: "Create a Gradescope roster")
            .With(GradescopeRoster),

            new Command("gradescope-upload", description: "Upload exams to Gradescope")
            .With(GradescopeUpload),

            new Command("grade", description: "Grade exams, creating a reports/ folder with the reports")
            .With(Grade),

            new Command("publish-reports", description: "Publish grading reports")
            .With(PublishReports),

            new Command("clone", description: "Clone all repos locally, for backup purposes")
            .With(Clone),

            new Command("cleanup", description: "Clean up the org, for after the course is over")
            .With(Cleanup)
        }.InvokeAsync(args);
    }
}
