namespace ExamManager;

public sealed record CoverageCounter(decimal Covered, decimal Total)
{
    public static readonly CoverageCounter Zero = new(0, 0);


    // In cases where we don't know Total, that's because there are no tests => 0/0 = 0
    public decimal Fraction => Total == 0 ? 0 : (Covered / Total);

    public decimal Percent => Fraction * 100;


    public static CoverageCounter operator +(CoverageCounter a, CoverageCounter b)
    {
        return new CoverageCounter(a.Covered + b.Covered, a.Total + b.Total);
    }
}
