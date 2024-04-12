namespace ExamManager;

using System;
using System.IO;
using System.Threading.Tasks;

using ExamManager.Utils;

public static class Git
{
    // For simplicity, the repo name is assumed to be the target directory name

    public static async Task CloneAsync(string remote, string org, DirectoryInfo dir)
    {
        if (dir.Exists)
        {
            // First check whether there are commits, which is necessary for other commands to work
            var (revlistResult, _) = await Shell.TryExecAsync(dir, "git", "rev-list", "-n", "1", "--all");
            if (revlistResult == 0)
            {
                // https://stackoverflow.com/a/8888015
                // Basically "force pull", even if the history was overwritten
                await ResetAsync(dir);
                await Shell.ExecAsync(dir, "git", "fetch", "--all");
                await Shell.ExecAsync(dir, "git", "reset", "--hard", "origin/main");
                return;
            }

            // No commits? Delete the directory and pretend it did not exist
            dir.Delete(true);
        }

        await Shell.ExecAsync(null, "git", "clone", $"{remote}:{org}/{dir.Name}", dir.FullName);
    }

    public static async Task CreateRepoAsync(string remote, string org, DirectoryInfo dir)
    {
        dir.Create();
        await Shell.ExecAsync(dir, "git", "init");
        // Follow the GitHub instructions, with a blank commit first to make the logic simpler
        // since some commands depend on there being at least one commit (and thus a branch)
        await Shell.ExecAsync(dir, "git", "commit", "--allow-empty", "-m", "Initial commit.");
        await Shell.ExecAsync(dir, "git", "branch", "-M", "main");
        await Shell.ExecAsync(dir, "git", "remote", "add", "origin", $"{remote}:{org}/{dir.Name}");
        await Shell.ExecAsync(dir, "git", "push", "-u", "origin", "main");
    }

    public static async Task MakeFileExecutableAsync(FileInfo file)
    {
        // In theory this check should be "does the FS have an executable bit Git recognizes" but in practice this is close enough
        if (OperatingSystem.IsWindows())
        {
            // Also --add so it works even with files not added/pushed yet
            await Shell.ExecAsync(file.Directory, "git", "update-index", "--chmod=+x", "--add", file.Name);
        }
        else
        {
            // Doing it in git is useless because the next git add -A will revert it as its true executable bit won't be set
            await Shell.ExecAsync(null, "chmod", "+x", file.FullName);
        }
    }

    public static async Task PushChangesAsync(DirectoryInfo dir, string commitMessage)
    {
        await Shell.ExecAsync(dir, "git", "add", "-A");

        // Only push if there is something to push
        // But check *after* adding, so that changes that cancel each other out get flattened
        // (relevant due to our "make files executable" logic, which leaves files both staged and modified)
        var currentStatus = await Shell.ExecAsync(dir, "git", "status", "--short");
        if (string.IsNullOrEmpty(currentStatus))
        {
            return;
        }

        await Shell.ExecAsync(dir, "git", "commit", "-m", commitMessage);
        // Reset the upstream branch, our trickery when force-pulling undoes it for some reason
        await Shell.ExecAsync(dir, "git", "push", "-u", "origin", "main");
    }

    public static async Task<(T?, GitCommit)> ExecuteOnLatestSuccessfulCommitAsync<T>(DirectoryInfo dir, Func<Task<T?>> func)
    {
        await ResetAsync(dir);

        while (true)
        {
            var result = await func();
            if (result != null)
            {
                var commit = await GetCurrentCommitAsync(dir);
                await ResetAsync(dir); // clean up after ourselves
                return (result, commit);
            }

            // The function may have dirtied the files
            await CleanAsync(dir);

            if (await TrySwitchToPreviousCommitAsync(dir))
            {
                dir.Refresh(); // we just updated the filesystem state behind .NET's back
                if (dir.Exists)
                {
                    // OK, continue, the loop must terminate since the number of commits is finite
                    continue;
                }
            }

            // Reaching this code means there are no more commits or the folder doesn't exist in the commit we went back to
            await ResetAsync(dir); // clean up after ourselves
            return (default(T), GitCommit.Empty);
        }
    }

    public static async Task ResetFileToInitialStateAsync(FileInfo file)
    {
        var originalCommit = await Shell.ExecAsync(file.Directory, "git", "log", "--diff-filter=A", "--pretty=format:%H", "--", file.Name);
        await Shell.ExecAsync(file.Directory, "git", "checkout", originalCommit, "--", file.Name);
    }

    public static async Task ResetAsync(DirectoryInfo dir)
    {
        // We might be given a subdirectory of the repo, and told to clean it while we're in a commit where this subdirectory does not exist...
        // So we need to find the actual repo first
        dir.Refresh();
        while (!dir.Exists)
        {
            dir = dir.Parent!; // silence nullability warning; if we have no parent here, something has gone so awfully wrong we might as well crash
        }

        await CleanAsync(dir);
        await Shell.ExecAsync(dir, "git", "checkout", "main");
    }

    private static async Task CleanAsync(DirectoryInfo dir)
    {
        await Shell.ExecAsync(dir, "git", "reset", "--hard", "HEAD");
        await Shell.ExecAsync(dir, "git", "clean", "-xfd");
    }

    private static async Task<GitCommit> GetCurrentCommitAsync(DirectoryInfo dir)
    {
        var hash = await Shell.ExecAsync(dir, "git", "rev-parse", "HEAD");
        var date = await Shell.ExecAsync(dir, "git", "show", "-s", "--format=%ci", hash);
        return new GitCommit(hash, date);
    }

    private static async Task<bool> TrySwitchToPreviousCommitAsync(DirectoryInfo dir)
    {
        var output = await Shell.ExecAsync(dir, "git", "rev-list", "HEAD", "--max-count=2");
        var lines = output.Split(Environment.NewLine);
        if (lines.Length == 1)
        {
            return false;
        }

        await Shell.ExecAsync(dir, "git", "checkout", lines[1]);
        return true;
    }
}
