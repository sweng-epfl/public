namespace ExamManager.Utils;

using System;
using System.CommandLine;
using System.Threading.Tasks;

// Simplifies command declaration
// This is rather silly, but System.CommandLine is currently in beta so...

public static class CommandExtensions
{
    public static Command With(this Command command, Func<Task> handler)
    {
        command.SetHandler(handler);
        return command;
    }

    public static Command With<T>(this Command command, Func<T, Task> handler, string msg, string desc)
    {
        var arg = new Argument<T>(msg, desc);
        command.Add(arg);
        command.SetHandler(handler, arg);
        return command;
    }
}
