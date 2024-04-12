namespace ExamManager.Tests;

using System.IO;

using ExamManager.Utils;

using Microsoft.VisualStudio.TestTools.UnitTesting;

[TestClass]
public sealed class FileSystemUtilsTests
{
    private static readonly DirectoryInfo FilesDirectory = new(Path.Combine("..", "..", "..", "assets", "diffs"));

    [TestMethod]
    public void HasSignificantDiffIsFalseForIdenticalFiles()
    {
        var file = FilesDirectory.File("identical.txt");
        var original = FilesDirectory.File("original.txt");
        var result = FileSystemUtils.HasSignificantDiff(file, original);
        Assert.IsFalse(result);
    }

    [TestMethod]
    public void HasSignificantDiffIsFalseForSpaceSuffixOnlyChanges()
    {
        var file = FilesDirectory.File("space-suffix.txt");
        var original = FilesDirectory.File("original.txt");
        var result = FileSystemUtils.HasSignificantDiff(file, original);
        Assert.IsFalse(result);
    }

    [TestMethod]
    public void HasSignificantDiffIsFalseForSpacePrefixOnlyChanges()
    {
        var file = FilesDirectory.File("space-prefix.txt");
        var original = FilesDirectory.File("original.txt");
        var result = FileSystemUtils.HasSignificantDiff(file, original);
        Assert.IsFalse(result);
    }

    [TestMethod]
    public void HasSignificantDiffIsTrueForSpaceInfixOnlyChanges()
    {
        var file = FilesDirectory.File("space-infix.txt");
        var original = FilesDirectory.File("original.txt");
        var result = FileSystemUtils.HasSignificantDiff(file, original);
        Assert.IsTrue(result);
    }

    [TestMethod]
    public void HasSignificantDiffIsFalseForNewLineOnlyChanges()
    {
        var file = FilesDirectory.File("newline.txt");
        var original = FilesDirectory.File("original.txt");
        var result = FileSystemUtils.HasSignificantDiff(file, original);
        Assert.IsFalse(result);
    }

    [TestMethod]
    public void HasSignificantDiffIsTrueForWordChanges()
    {
        var file = FilesDirectory.File("word.txt");
        var original = FilesDirectory.File("original.txt");
        var result = FileSystemUtils.HasSignificantDiff(file, original);
        Assert.IsTrue(result);
    }

    [TestMethod]
    public void HasSignificantDiffIsTrueForPunctuationChanges()
    {
        var file = FilesDirectory.File("punctuation.txt");
        var original = FilesDirectory.File("original.txt");
        var result = FileSystemUtils.HasSignificantDiff(file, original);
        Assert.IsTrue(result);
    }
}