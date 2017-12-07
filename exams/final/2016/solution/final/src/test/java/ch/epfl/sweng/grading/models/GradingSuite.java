package ch.epfl.sweng.grading.models;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.grading.utils.StringUtils;

public final class GradingSuite implements Comparable<GradingSuite> {
    public final String className;
    public final String name;
    public final String details;
    public String group = null;
    public final GradingPoints.PointsFormat pointsFormat;

    public final List<GradingTest> tests;


    public GradingSuite(final String className, final String name, final String details,
                        final String group, final GradingPoints.PointsFormat pointsFormat) {
        this(className, name, details, pointsFormat);
        this.group = group;
    }
    public GradingSuite(final String className, final String name, final String details,
                        final GradingPoints.PointsFormat pointsFormat) {
        this.className = className;
        this.name = name;
        this.details = details;
        this.pointsFormat = pointsFormat;

        tests = new ArrayList<>();
    }

    public boolean hasGroup() {
        return group != null;
    }

    public GradingPoints computePoints() {
        GradingPoints result = GradingPoints.Empty(pointsFormat);

        for (final GradingTest test : tests) {
            result = result.combine(test.computePoints());
        }


        return result;
    }

    @Override
    public int compareTo(final GradingSuite t) {
        return className.compareTo(t.className);
    }

    @Override
    public boolean equals(final Object o) {
        return o != null
                && o instanceof GradingSuite
                && className.equals(((GradingSuite) o).className);

    }

    @Override
    public int hashCode() {
        return className.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();

        result.append(StringUtils.repeat('#', 2));
        result.append(" [");
        result.append(computePoints());
        result.append("] ");
        result.append(name);
        result.append(System.lineSeparator());

        if (details != null) {
            result.append(details);
            result.append(System.lineSeparator());
            result.append(System.lineSeparator());
        }

        // Print graded tests first.
        tests.stream().filter(test -> test.availablePoints != 0).forEach(test -> {
            result.append(test.toString());
            result.append(System.lineSeparator());
        });

        tests.stream().filter(test -> test.availablePoints == 0).forEach(test -> {
            result.append(test.toString());
            result.append(System.lineSeparator());
        });

        result.append(System.lineSeparator());

        return result.toString();
    }
}
