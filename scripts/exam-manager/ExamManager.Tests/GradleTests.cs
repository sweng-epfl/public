namespace ExamManager.Tests;

using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

using Microsoft.VisualStudio.TestTools.UnitTesting;

[TestClass]
public sealed class GradleTests
{
    private static readonly DirectoryInfo TestProjectDir = new(Path.Combine("..", "..", "..", "assets", "sample-project"));
    private static IReadOnlyList<ExamManager.TestResult> results = new List<ExamManager.TestResult>();

    [ClassInitialize]
    public static void SetUp(TestContext _)
    {
        // Ensure no old results can pollute anything
        TestProjectDir.CreateSubdirectory("build").Delete(true);
        // ClassInitialize can't be async unfortunately
        var results = Gradle.RunTestsAsync(TestProjectDir).Result;
        Assert.IsNotNull(results);
        GradleTests.results = results;
    }

    [TestMethod]
    public void AllNonSkippedTestsAreEnumerated()
    {
        CollectionAssert.AreEquivalent(
            new[]
            {
                "ExampleTest.tryToBreakParsing",
                "ExampleTest.customDisplayNameThatTriesToBreakParsing",
                "ExampleTest.repeatedButFailsSecondTime",
                "ExampleTest.repeatedWithAName",
                "ExampleTest$NestedTest.coverApp",
                "ExampleTest.useOfAssertAll",
                "ExampleTest.failure"
            },
            results.Select(r => r.FullName).ToArray()
        );
    }

    [TestMethod]
    public void SuccessfulTestIsParsedAsSuch()
    {
        var result = results.Single(r => r.FullName == "ExampleTest.useOfAssertAll");
        Assert.IsTrue(result.Success);
    }

    [TestMethod]
    public void FailedTestIsParsedAsSuch()
    {
        var result = results.Single(r => r.FullName == "ExampleTest.failure");
        Assert.IsFalse(result.Success);
    }

    [TestMethod]
    public void DisplayNameEscapesCharactersInCsv()
    {
        var result = results.Single(r => r.FullName == "ExampleTest.customDisplayNameThatTriesToBreakParsing");
        Assert.AreEqual("Let's try to break the parsing [ \\ \"", result.DisplayName);
    }

    [TestMethod]
    public void MultiLineErrorMessageIsParsedFully()
    {
        var result = results.Single(r => r.FullName == "ExampleTest.failure");
        // Hardcoded \n since we want the same output on all OSes
        Assert.AreEqual("Expected: is <2>\n     but: was <1>", result.Message);
    }

    [TestMethod]
    public async Task CoverageIsParsedFromGradle()
    {
        var coverage = await Gradle.GetCoverageAsync(
            TestProjectDir, new[] { "ExampleTest.useOfAssertAll", "ExampleTest$NestedTest.coverApp" }
        );

        Assert.IsNotNull(coverage);
        Assert.AreEqual(new CoverageCounter(0, 0), coverage["App"].Branch);
        Assert.AreEqual(new CoverageCounter(1, 2), coverage["AppPartiallyCovered"].Branch);
        Assert.AreEqual(new CoverageCounter(0, 0), coverage["AppNotCovered"].Branch);
        Assert.AreEqual(new CoverageCounter(7, 7), coverage["App"].Statement);
        Assert.AreEqual(new CoverageCounter(7, 13), coverage["AppPartiallyCovered"].Statement);
        Assert.AreEqual(new CoverageCounter(0, 7), coverage["AppNotCovered"].Statement);
    }
}