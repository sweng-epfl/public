package grading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    String toCsvSuiteHeader() {
        return name + String.join("", Collections.nCopies(tests.size() - 1, ","));
    }

    String toCsvRowHeader() {
        return tests.stream().map(GradingTest::name).map(StringUtils::escapeCsv).collect(Collectors.joining(","));
    }

    String toCsvRow() {
        return tests.stream().map(GradingTest::toCsvValue).collect(Collectors.joining(","));
    }
}
