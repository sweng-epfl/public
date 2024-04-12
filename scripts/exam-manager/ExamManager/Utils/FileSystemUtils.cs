namespace ExamManager.Utils;

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

using Microsoft.Extensions.FileSystemGlobbing;

public static class FileSystemUtils
{
    // This class voluntarily does not use async operations;
    // not all of them exist, so it would be inconsistent,
    // and we deal with small files so it's unlikely to be worth it anyway

    public static FileInfo CreateTempFile(string name, string contents)
    {
        // Use a temp file name as the folder name so we get a file with the right name;
        // we're not worried about concurrency here, only about avoiding accidentally sharing a file name
        var folderName = Path.GetTempFileName();
        File.Delete(folderName);
        Directory.CreateDirectory(folderName);
        var filePath = Path.Combine(folderName, name);
        File.WriteAllText(filePath, contents);
        return new FileInfo(filePath);
    }

    // Copies files/folders recursively, overwriting existing files/folders, except ".git".
    // Not resilient to weird stuff like symlinks to parent directories, but we don't need to be
    public static void CopyRecursively(DirectoryInfo sourceDir, DirectoryInfo targetDir, string itemPath)
    {
        if (!sourceDir.Exists)
        {
            throw new ArgumentException("The target directory must exist");
        }

        static void CopyDirectoryRecursively(DirectoryInfo source, DirectoryInfo target)
        {
            // HACK: Hardcoding this here is... not great.
            if (source.Name == ".git")
            {
                return;
            }

            foreach (DirectoryInfo dir in source.EnumerateDirectories())
            {
                CopyDirectoryRecursively(dir, target.CreateSubdirectory(dir.Name));
            }
            foreach (FileInfo file in source.EnumerateFiles())
            {
                file.CopyTo(target.File(file.Name));
            }
        }

        var itemFullPath = Path.Combine(sourceDir.FullName, itemPath);
        if (Directory.Exists(itemFullPath))
        {
            var source = new DirectoryInfo(itemFullPath);
            var target = targetDir.SubDirectory(itemPath);
            // Ensure there are no leftover old files
            if (target.Exists)
            {
                target.Delete(true);
            }
            target.Create();
            CopyDirectoryRecursively(source, target);
        }
        else if (File.Exists(itemFullPath))
        {
            // Create any necessary directories first, since the file could be nested in a bunch of dirs
            var containingDir = Path.GetDirectoryName(itemPath);
            if (!string.IsNullOrEmpty(containingDir))
            {
                targetDir.SubDirectory(containingDir).Create();
            }

            var fileInfo = new FileInfo(itemFullPath);
            fileInfo.CopyTo(targetDir.File(itemPath), overwrite: true);
        }
        else
        {
            throw new ArgumentException("Directory or file not found: " + itemPath);
        }
    }

    public static void CopyContentsRecursively(DirectoryInfo source, DirectoryInfo target)
    {
        foreach (DirectoryInfo dir in source.EnumerateDirectories())
        {
            CopyContentsRecursively(dir, target.CreateSubdirectory(dir.Name));
        }
        foreach (FileInfo file in source.EnumerateFiles())
        {
            file.CopyTo(target.File(file.Name));
        }
    }

    public static IReadOnlyList<FileInfo> FindFilesMatching(DirectoryInfo sourceDir, IReadOnlyCollection<string> patterns)
    {
        // Ensure / paths work everywhere
        patterns = patterns.Select(p => p.Replace('/', Path.DirectorySeparatorChar)).ToArray();

        var matcher = new Matcher();
        matcher.AddIncludePatterns(patterns);
        return matcher.GetResultsInFullPath(sourceDir.FullName).Select(p => new FileInfo(p)).ToArray();
    }

    public static bool HasSignificantDiff(FileInfo file, FileInfo original)
    {
        static IEnumerable<string> GetSignificantLines(FileInfo file)
        {
            return File.ReadLines(file.FullName)
                       .Select(s => s.Trim())
                       .Where(s => s != "");
        }

        if (!file.Exists)
        {
            throw new ArgumentException("File doesn't exist");
        }

        if (!original.Exists)
        {
            return true;
        }

        return !GetSignificantLines(file).SequenceEqual(GetSignificantLines(original));
    }

    public static void DeleteUnchanged(FileInfo file, FileInfo original)
    {
        File.WriteAllLines(file.FullName, GetDiffLines(file, original));
    }

    public static void ReplaceText(FileInfo file, string original, string replacement)
    {
        var text = File.ReadAllText(file.FullName);
        text = text.Replace(original, replacement);
        File.WriteAllText(file.FullName, text);
    }

    private static IEnumerable<string> GetDiffLines(FileInfo file, FileInfo original)
    {
        var lines = File.ReadAllLines(file.FullName);
        if (!original.Exists)
        {
            return lines;
        }

        var originalLines = File.ReadAllLines(original.FullName);

        int beginning = lines.SkipWhile(string.IsNullOrWhiteSpace)
                             .Zip(originalLines.SkipWhile(string.IsNullOrWhiteSpace))
                             .TakeWhile(t => t.First.Trim() == t.Second.Trim())
                             .Count();
        int end = lines.Reverse()
                       .SkipWhile(string.IsNullOrWhiteSpace)
                       .Zip(originalLines.Reverse().SkipWhile(string.IsNullOrWhiteSpace))
                       .TakeWhile(t => t.First.Trim() == t.Second.Trim())
                       .Count();

        return lines.Skip(beginning).Take(lines.Length - beginning - end);
    }
}
