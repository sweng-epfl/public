namespace ExamManager;

using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using System.Xml.Linq;

using CsvHelper;
using CsvHelper.Configuration;

using ExamManager.Utils;

// This module assumes Gradle is used with our own build.gradle file (see scripts/gradle at the root of the repo),
// which creates a "tests.csv" report at the root of the build directory
// with the tests' full name, display name, result, and optional error message

public static class Gradle
{
    public static async Task<bool> RunAsync(DirectoryInfo dir, params string[] args)
    {
        var gradleExec = OperatingSystem.IsWindows() ? "gradlew.bat" : dir.File("./gradlew").FullName;

        // Ensure nobody messed with the build infrastructure
        // This does mean even the staff cannot mess with it, but that's normal;
        // if you have messed with it to fix a last-minute bug during the exam, you can always temporarily uncomment this
        await Git.ResetFileToInitialStateAsync(dir.File(gradleExec));
        await Git.ResetFileToInitialStateAsync(dir.File("build.gradle"));
        await Git.ResetFileToInitialStateAsync(dir.File("gradle"));

        if (!OperatingSystem.IsWindows())
        {
            await Shell.ExecAsync(dir, "chmod", "+x", gradleExec);
        }

        args = args.Prepend("--max-workers=1") // We might be invoking Gradle in parallel, so limit it to one core
                   .Prepend("--no-daemon") // Running lots of daemons occasionally causes them to wait minutes for various locks...
                   .ToArray();

        // Because of https://github.com/gradle/gradle/issues/2854,
        // which caused spurious failures in the past (and is only closed due to inactivity),
        // we try up to 3 times.
        for (int n = 0; n < 3; n++)
        {
            var (code, _) = await Shell.TryExecAsync(dir, gradleExec, args);
            if (code == 0)
            {
                return true;
            }
        }
        return false;
    }

    public static Task<bool> BuildsAsync(DirectoryInfo dir)
    {
        return RunAsync(dir, "clean", "build");
    }

    public static async Task<IReadOnlyList<TestResult>?> RunTestsAsync(DirectoryInfo dir)
    {
        await RunAsync(dir, "clean", "build");
        var reportFile = dir.SubDirectory("build").File("tests.csv");
        if (!reportFile.Exists)
        {
            return null;
        }

        var config = new CsvConfiguration(CultureInfo.InvariantCulture);
        using var reader = new StreamReader(reportFile.OpenRead());
        using var csv = new CsvReader(reader, config);

        var results = new List<TestResult>();
        csv.Read(); // needed before ReadHeader for it to work
        csv.ReadHeader();
        while (csv.Read())
        {
            var result = csv.GetField("Result");
            if (result is not ("SUCCESS" or "FAILURE"))
            {
                // we should not see any skipped tests here
                throw new Exception("Unknown test result: " + result);
            }
            // Uniformize newlines so the output doesn't change depending on the OS
            var errorMessage = csv.GetField("ErrorMessage").Trim().Replace(Environment.NewLine, "\n");
            if (string.IsNullOrWhiteSpace(errorMessage))
            {
                errorMessage = null;
            }

            results.Add(new TestResult(
                csv.GetField("FullName"),
                csv.GetField("DisplayName"),
                result is "SUCCESS",
                errorMessage
            ));
        }
        return results;
    }

    public static async Task<Dictionary<string, CoverageInfo>?> GetCoverageAsync(DirectoryInfo dir, IReadOnlyCollection<string> testFullNames)
    {
        if (testFullNames.Count == 0)
        {
            // There is no Gradle command to run zero tests, so this must be handled explicitly
            return new Dictionary<string, CoverageInfo>();
        }

        // Let's try to run the tests up to 5 times in case the tests are flaky...
        for (int n = 0; n < 5; n++)
        {
            // We must pass --tests for each test, it's not a multi-arg option despite the plural name
            var canTest = await RunAsync(
                dir,
                new[] { "clean", "test" }.Concat(testFullNames.SelectMany(t => new[] { "--tests", t })).Append("jacocoTestReport").ToArray()
            );
            if (canTest)
            {
                return GetCoverage(dir);
            }
        }
        return null;
    }

    private static Dictionary<string, CoverageInfo> GetCoverage(DirectoryInfo dir)
    {
        static CoverageCounter ParseCounter(XElement? element)
        {
            if (element == null)
            {
                return CoverageCounter.Zero;
            }
            var missed = (decimal?)element.Attribute("missed");
            var covered = (decimal?)element.Attribute("covered");
            if (missed.HasValue && covered.HasValue)
            {
                return new CoverageCounter(covered.Value, covered.Value + missed.Value);
            }
            throw new Exception("Element does not have expected missed/covered attributes");
        }

        var coverageReportFile = dir.SubDirectory("build", "reports", "jacoco", "test").File("jacocoTestReport.xml");
        var coverageReport = XDocument.Load(coverageReportFile.OpenRead());
        ArgumentNullException.ThrowIfNull(coverageReport.Root, "coverageReport has a null root");

        var results = new Dictionary<string, CoverageInfo>();
        foreach (var classElem in coverageReport.Root.Descendants("class"))
        {
            // FirstOrDefault because the elements may not exist if there are no instructions/branches in the source
            var statementsElem = classElem.Elements("counter").FirstOrDefault(e => e.Attribute("type")?.Value == "INSTRUCTION");
            var branchesElem = classElem.Elements("counter").FirstOrDefault(e => e.Attribute("type")?.Value == "BRANCH");

            var info = new CoverageInfo(
                Statement: ParseCounter(statementsElem),
                Branch: ParseCounter(branchesElem)
            );
            var name = classElem.Attribute("name")?.Value;
            ArgumentNullException.ThrowIfNull(name, "Class element has no name");
            // For some reason packages are separated with / instead of . in the results; normalize that
            results.Add(name.Replace('/', '.'), info);
        }
        return results;
    }
}
