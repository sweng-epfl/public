namespace ExamManager;

using System.Collections.Generic;

public static class CoverageExtensions
{
    public static CoverageInfo SumFor(this IReadOnlyDictionary<string, CoverageInfo> coverage, IReadOnlyCollection<string> classes)
    {
        var result = CoverageInfo.Zero;
        foreach (var cla in classes)
        {
            if (coverage.TryGetValue(cla, out var cov))
            {
                result += cov;
            }
        }
        return result;
    }
}
