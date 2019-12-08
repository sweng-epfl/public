package ch.epfl.sweng.tests.grading.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ch.epfl.sweng.tests.grading.utils.StringUtils;

public final class GradingTest {
    private final String name;
    private final List<GradingProblem> problems; // Empty iff the test was successful


    public GradingTest(final String name, final List<GradingProblem> problems) {
        this.name = name;
        this.problems = Collections.unmodifiableList(new ArrayList<>(problems));
    }

    String name() {
        return name;
    }

    String toCsvValue() {
        if (problems.isEmpty()) {
            return "OK";
        } else {
            return StringUtils.escapeCsv(
                    problems.stream().map(GradingProblem::toString).collect(Collectors.joining(System.lineSeparator()))
            );
        }
    }
}