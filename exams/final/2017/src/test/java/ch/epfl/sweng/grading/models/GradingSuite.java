package ch.epfl.sweng.grading.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GradingSuite implements Comparable<GradingSuite> {
    public final String className;
    public final String name;
    public final String details;
    public final GradingPoints points;
    public final List<GradingTest> tests;


    public GradingSuite(final String className, final String name, final String details, final List<GradingTest> tests) {
        this.className = className;
        this.name = name;
        this.details = details;
        this.tests = Collections.unmodifiableList(new ArrayList<>(tests));

        GradingPoints pointsSum = GradingPoints.empty();
        for (final GradingTest test : tests) {
            pointsSum = pointsSum.combine(test.points);
        }
        points = pointsSum;
    }


    @Override
    public int compareTo(final GradingSuite t) {
        return name.compareTo(t.name);
    }

    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof GradingSuite && name.equals(((GradingSuite) o).name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();

        result.append("## [");
        result.append(points);
        result.append("] ");
        result.append(name);
        result.append(System.lineSeparator());

        if (details != null) {
            result.append(details);
            result.append(System.lineSeparator());
            result.append(System.lineSeparator());
        }

        for (final GradingTest gradingTest : tests) {
            result.append(gradingTest.toString());
            result.append(System.lineSeparator());
        }

        result.append(System.lineSeparator());

        return result.toString();
    }
}