namespace ExamManager.Utils;

using System.IO;

// Basic stuff Directory/FileInfo should have...

public static class FileSystemExtensions
{
    public static DirectoryInfo SubDirectory(this DirectoryInfo dir, params string[] path)
    {
        // let's be careful and join one by one in a way that always preserves the path being valid,
        // just in case some elements of the path are weird and look like an absolute path?
        var fullPath = dir.FullName;
        foreach (var elem in path)
        {
            fullPath = Path.Combine(fullPath, elem);
        }
        return new DirectoryInfo(fullPath);
    }

    public static FileInfo File(this DirectoryInfo dir, string path)
    {
        return new FileInfo(Path.Combine(dir.FullName, path));
    }

    public static string RelativePath(this DirectoryInfo dir, FileSystemInfo info)
    {
        return Path.GetRelativePath(dir.FullName, info.FullName);
    }

    public static void CopyTo(this FileInfo file, FileInfo target, bool overwrite = false)
    {
        file.CopyTo(target.FullName, overwrite);
    }
}
