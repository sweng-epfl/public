namespace ExamManager.Utils;

using System;
using System.Collections.Generic;
using System.Linq;

public static class Interpolate
{
    public static decimal Linear(decimal val, IReadOnlyDictionary<decimal, decimal> thresholds)
    {
        if (thresholds.Count == 0)
        {
            throw new ArgumentException("There must be >=1 thresholds");
        }
        if (thresholds.Any(p => p.Key < 0))
        {
            throw new ArgumentException("Thresholds must be >=0");
        }

        var below = thresholds.Any(p => p.Key <= val)
            ? thresholds.Where(p => p.Key <= val).Max(p => p.Key)
            : 0;
        var above = thresholds.Any(p => p.Key >= val)
            ? thresholds.Where(p => p.Key >= val).Min(p => p.Key)
            : thresholds.Max(p => p.Key);

        if (above == below)
        {
            return thresholds[above];
        }

        // Implicit floor at 0
        var threshBelow = thresholds.GetValueOrDefault(below, 0);
        return threshBelow + RoundToHalfPoint((val - below) / (above - below) * (thresholds[above] - threshBelow));
    }

    private static decimal RoundToHalfPoint(decimal val)
    {
        return Math.Round(val * 2, MidpointRounding.AwayFromZero) / 2;
    }
}
