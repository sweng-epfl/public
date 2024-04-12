namespace ExamManager.Tests;

using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

using Microsoft.VisualStudio.TestTools.UnitTesting;

[TestClass]
public sealed class GradescopeParsingTests
{
    private static readonly FileInfo ReportFile = new(Path.Combine("..", "..", "..", "assets", "GradescopeReport.csv"));
    private static readonly User[] Users = new[]
    {
        new User("example@example.org", new[] {"example"}),
        new User("example2@example.org", new[] {"example2"}),
        new User("missing@example.org", new[] {"missing"})
    };
    private static readonly string Title = "Report Title";

    private Dictionary<User, Report> _reports = new();
    private decimal _maxPoints;
    [TestInitialize]
    public async Task TestInit()
    {
        (_maxPoints, _reports) = await Gradescope.ParseReportsAsync(ReportFile, Users, Title);
    }

    [TestMethod]
    public void MaxPointsIsSumOfGroupMax()
    {
        Assert.AreEqual(15, _maxPoints);
    }

    [TestMethod]
    public void MissingLineYieldsNoReport()
    {
        Assert.IsFalse(_reports.ContainsKey(Users[2]));
    }

    [TestMethod]
    public void ReportHasGivenTitle()
    {
        Assert.AreEqual(Title, _reports[Users[0]].Title);
    }

    [TestMethod]
    public void MaxPointsForSubQuestionWhenChecked()
    {
        var entry = _reports[Users[0]].Entry("Explanation that speculatively generating images would improve performance");

        Assert.AreEqual(5, entry.PointsAvailable);
        Assert.AreEqual(5, entry.PointsObtained);
    }

    [TestMethod]
    public void ZeroPointForSubQuestionWhenNoneChecked()
    {
        var entry = _reports[Users[0]].Entry("No answer to part 2");

        Assert.AreEqual(5, entry.PointsAvailable);
        Assert.AreEqual(0, entry.PointsObtained);
    }


    [TestMethod]
    public void PartialPointsForSubQuestionWhenChecked()
    {
        var entry = _reports[Users[0]].Entry("Explanation that one should optimize for the most gains in relative terms");
        Assert.AreEqual(5, entry.PointsAvailable);
        Assert.AreEqual(4, entry.PointsObtained);
    }

    [TestMethod]
    public void ExtraNegativePointsWhenChecked()
    {
        var entry = _reports[Users[0]].Entry("Overly verbose answer to the first sub-question");
        Assert.AreEqual(0, entry.PointsAvailable);
        Assert.AreEqual(-1, entry.PointsObtained);
    }

    [TestMethod]
    public void ExtraPositivePointsWhenChecked()
    {
        var entry = _reports[Users[0]].Entry("Bonus for some reason");
        Assert.AreEqual(0, entry.PointsAvailable);
        Assert.AreEqual(1, entry.PointsObtained);
    }

    [TestMethod]
    public void NoExtraPointsWhenUnchecked()
    {
        var entry = _reports[Users[0]];
        var missingEntry = entry.Entries.SingleOrDefault(e => e.Description == "Overly verbose answer to the third sub-question");
        Assert.IsNull(missingEntry);
    }

    [TestMethod]
    public void RubricMarkedSkipIsSkipped()
    {
        var entry = _reports[Users[0]];
        var missingEntry = entry.Entries.SingleOrDefault(e => e.Description == "No points");
        Assert.IsNull(missingEntry);
        // just in case...
        var missingEntry2 = entry.Entries.SingleOrDefault(e => e.Description == "SKIP|No points");
        Assert.IsNull(missingEntry2);
    }

    [TestMethod]
    public void PointsCountedWhenHeaderIsDuplicate()
    {
        // this header occurs twice in the report, and this user has it as true for the second instance
        var entry = _reports[Users[1]].Entry("Suggestion to speculatively generate images by predicting topics");
        Assert.AreEqual(2, entry.PointsObtained);
    }
}

internal static class ReportExtensions
{
    public static Report Sub(this Report report, string title)
    {
        return report.SubReports.Single(r => r.Title == title);
    }

    public static ReportEntry Entry(this Report report, string desc)
    {
        return report.Entries.Single(e => e.Description == desc);
    }
}