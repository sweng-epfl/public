namespace ExamManager;

public sealed record CoverageInfo(CoverageCounter Statement, CoverageCounter Branch)
{
    public static readonly CoverageInfo Zero = new(CoverageCounter.Zero, CoverageCounter.Zero);

    public static CoverageInfo operator +(CoverageInfo a, CoverageInfo b)
    {
        return new CoverageInfo(a.Statement + b.Statement, a.Branch + b.Branch);
    }
}
