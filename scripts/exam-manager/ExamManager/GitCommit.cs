namespace ExamManager;

public sealed record GitCommit(string Hash, string Date)
{
    public static readonly GitCommit Empty = new(string.Empty, string.Empty);
}
