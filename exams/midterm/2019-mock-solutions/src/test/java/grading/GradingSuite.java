package grading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class GradingSuite implements Comparable<GradingSuite> {
    private final String name;
    private final List<GradingTest> tests;

    GradingSuite(final String name, final List<GradingTest> tests) {
        this.name = name;
        this.tests = Collections.unmodifiableList(new ArrayList<>(tests));
    }

    @Override
    public int compareTo(final GradingSuite t) {
        return name.compareTo(t.name);
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof GradingSuite && name.equals(((GradingSuite) o).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    String toCsvEmptyRow() {
        // Empty cells so that it has the same length as the row header
        return String.join("", Collections.nCopies(tests.size(), ","));
    }

    String toCsvSuiteHeader() {
        return name + toCsvEmptyRow();
    }

    String toCsvRowHeader() {
        final StringBuilder result = new StringBuilder();

        for (final GradingTest gradingTest : tests) {
            result.append(StringUtils.escapeCsv(gradingTest.name()));
            result.append(',');
        }

        return result.toString();
    }

    String toCsvRow() {
        final StringBuilder result = new StringBuilder();

        for (final GradingTest gradingTest : tests) {
            result.append(gradingTest.toCsvValue());
            result.append(',');
        }

        return result.toString();
    }
}
