namespace ExamManager.Tests;

using System;
using System.Collections.Generic;

using ExamManager.Utils;

using Microsoft.VisualStudio.TestTools.UnitTesting;

[TestClass]
public sealed class InterpolationTests
{
    [TestMethod]
    public void ComputeMiddlePoint()
    {
        var thresholds = new Dictionary<decimal, decimal>
        {
            { 0, 10 },
            { 1, 20 }
        };

        var result = Interpolate.Linear(0.5m, thresholds);

        Assert.AreEqual(15, result);
    }

    [TestMethod]
    public void ResultIsRoundedToHalfPoint()
    {

        var thresholds = new Dictionary<decimal, decimal>
        {
            { 0, 1 },
            { 1, 2 }
        };

        var result = Interpolate.Linear(0.4m, thresholds);

        Assert.AreEqual(1.5m, result);
    }

    [TestMethod]
    public void UseImplicitZeroFloor()
    {
        var thresholds = new Dictionary<decimal, decimal>
        {
            { 0.5m, 10 },
            { 1, 20 }
        };

        var result = Interpolate.Linear(0.25m, thresholds);

        Assert.AreEqual(5, result);
    }

    [TestMethod]
    public void ClampToCeiling()
    {
        var thresholds = new Dictionary<decimal, decimal>
        {
            { 0, 10 },
            { 0.7m, 20 }
        };

        var result = Interpolate.Linear(1, thresholds);

        Assert.AreEqual(20, result);
    }

    [TestMethod]
    public void UseZeroThreshold()
    {
        var thresholds = new Dictionary<decimal, decimal>
        {
            { 0, 0 },
            { 1, 1 }
        };

        var result = Interpolate.Linear(0, thresholds);

        Assert.AreEqual(0, result);
    }

    [TestMethod]
    public void CannotUseNoThresholds()
    {
        var thresholds = new Dictionary<decimal, decimal>();

        Assert.ThrowsException<ArgumentException>(() => Interpolate.Linear(-0.5m, thresholds));
    }

    [TestMethod]
    public void CannotUseNegativeThreshold()
    {
        var thresholds = new Dictionary<decimal, decimal>
        {
            { -1, 10 },
            { 0, 20 }
        };

        Assert.ThrowsException<ArgumentException>(() => Interpolate.Linear(-0.5m, thresholds));
    }
}