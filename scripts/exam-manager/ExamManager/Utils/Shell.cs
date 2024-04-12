namespace ExamManager.Utils;

using System;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

public static class Shell
{
    public static async Task<string> ExecAsync(DirectoryInfo? workingDir, string command, params string[] args)
    {
        var (code, text) = await TryExecAsync(workingDir, command, args);
        if (code != 0)
        {
            throw new Exception($"Non-zero error code {code}: {text}");
        }
        return text;
    }

    public static async Task<(int, string)> TryExecAsync(DirectoryInfo? workingDir, string command, params string[] args)
    {
        // Default that won't be a UNC path even if we run in one (because of the issues below)
        workingDir ??= new DirectoryInfo(Environment.GetFolderPath(Environment.SpecialFolder.MyDocuments));

        // Gradle does not work on UNC paths, even when they're symlinked;
        // it fails with a cryptic
        // "Could not create service of type ChecksumService using BuildSessionScopeServices.createChecksumService().
        //      java.io.IOException: Incorrect function"
        // at startup.
        // As a workaround, use gradlew instead, and the rest of this function will use WSL to run it.
        if (OperatingSystem.IsWindows() && workingDir.FullName.StartsWith(@"\\") && command == "gradlew.bat")
        {
            command = "./gradlew";
        }

        // If we're on Windows and given a UNC path as working dir and a batch file to run,
        // symlink it since CMD doesn't support UNC paths as working dirs
        if (OperatingSystem.IsWindows() && workingDir.FullName.StartsWith(@"\\") && command.EndsWith(".bat"))
        {
            var tempFolderPath = Path.GetTempFileName();
            File.Delete(tempFolderPath);
            var (mklinkCode, _) = await TryExecAsync(
                null,
                "cmd.exe", "/C", "mklink", "/D", tempFolderPath, workingDir.FullName
            );
            if (mklinkCode != 0)
            {
                // by default they need admin privileges, which isn't a bad decision since symlinks can wreak havoc, but...
                throw new Exception("Couldn't use mklink /D, please enable Windows developer mode");
            }
            workingDir = new DirectoryInfo(tempFolderPath);
        }

        // On Windows, try WSL if the command doesn't exist; this makes it easy to use e.g. the same git in both worlds
        if (OperatingSystem.IsWindows() && command is not ("where" or "wsl" or "cmd.exe") && !File.Exists(command))
        {
            var (whereResult, _) = await TryExecAsync(workingDir, "where", command);
            if (whereResult != 0)
            {
                for (int n = 0; n < args.Length; n++)
                {
                    // Escape $
                    args[n] = args[n].Replace("$", @"\$");
                    // Translate path-like args (this is very hacky, but in practice for the operations we need it works)
                    if (args[n].StartsWith(@"\\wsl.localhost\"))
                    {
                        var idx = @"\\wsl.localhost\".Length + args[n][@"\\wsl.localhost\".Length..].IndexOf('\\');
                        args[n] = args[n][idx..].Replace('\\', '/');
                    }
                    // C:\\xyz -> /mnt/c/xyz
                    if (args[n].Length > 3 && args[n][1] == ':' && args[n][2] == '\\')
                    {
                        args[n] = "/mnt/" + char.ToLowerInvariant(args[n][0]) + args[n][2..].Replace('\\', '/');
                    }
                }

                return await TryExecAsync(workingDir, "wsl", args.Prepend(command).ToArray());
            }
        }

        var info = new ProcessStartInfo(command)
        {
            UseShellExecute = false, // required to redirect stdout/err
            RedirectStandardOutput = true,
            RedirectStandardError = true,
            WorkingDirectory = workingDir.FullName
        };
        // Windows does not consider batch files as directly executable
        if (OperatingSystem.IsWindows() && command.EndsWith(".bat"))
        {
            info.ArgumentList.Add("/C");
            info.ArgumentList.Add(command);
            info.FileName = "cmd.exe";
        }
        foreach (var arg in args)
        {
            info.ArgumentList.Add(arg);
        }
        using var process = new Process { StartInfo = info };

        try
        {
            process.Start();
        }
        catch (Exception e)
        {
            // Very generic but since we're centralizing errors in the message, this makes it easier
            return (-2, e.Message);
        }

        // Avoid deadlocks from buffering in case the process outputs a lot by reading both streams at the same time as we wait
        var outTask = process.StandardOutput.ReadToEndAsync();
        var errTask = process.StandardError.ReadToEndAsync();
        var processTask = process.WaitForExitAsync();

        try
        {
            // Use a timeout to make sure we're not waiting for the process or its outputs forever
            await Task.WhenAll(outTask, errTask, processTask).WaitAsync(TimeSpan.FromMinutes(5));
        }
        catch
        {
            try
            {
                process.Kill(entireProcessTree: true);
            }
            catch
            {
                // Best effort...
            }
            return (-1, "Timeout");
        }

        if (process.ExitCode == 0)
        {
            return (0, outTask.Result.Trim());
        }
        return (process.ExitCode, errTask.Result.Trim());
    }
}
