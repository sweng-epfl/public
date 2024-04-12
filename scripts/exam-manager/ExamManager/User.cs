namespace ExamManager;

using System;
using System.Collections.Generic;
using System.Linq;

public sealed record User(string Email, IReadOnlyList<string> Ids)
{
    public string MainId => Ids[0];

    public static User Parse(string line)
    {
        var parts = line.Split(',');
        if (parts.Length < 2)
        {
            throw new Exception("Users must have at least an email and an ID, separated by a comma");
        }
        return new User(parts[0].Trim(), parts.Skip(1).Select(s => s.Trim()).ToArray());
    }

    public override string ToString()
    {
        return $"{Email} ({string.Join(", ", Ids)})";
    }
}
