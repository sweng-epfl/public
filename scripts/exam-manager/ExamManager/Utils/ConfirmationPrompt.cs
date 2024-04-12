namespace ExamManager.Utils;

using System;

public static class ConfirmationPrompt
{
    public static void Show(string confirmation)
    {
        Console.WriteLine($"You are about to {confirmation.ToUpper()}.");
        Console.Write($"To confirm, write '{confirmation}': ");
        var input = Console.ReadLine();
        if (input?.Trim() != confirmation)
        {
            Environment.Exit(1);
        }
    }
}
