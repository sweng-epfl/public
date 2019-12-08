package ch.epfl.sweng.graded.grading.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GradingTest {
    public final String name;
    public final String details;
    public final GradingPoints points;
    public final List<GradingProblem> problems; // Empty iff the test was successful


    public GradingTest(final String name, final String details, final double availablePoints, final List<GradingProblem> problems) {
        this.name = name;
        this.details = details;
        this.problems = Collections.unmodifiableList(new ArrayList<>(problems));
        points = new GradingPoints(availablePoints, problems.isEmpty() ? availablePoints : 0);
    }


    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();

        result.append("- ");

        final boolean shouldPrintPoints;
        if (problems.isEmpty()) {
            result.append("**\u2714**"); // tick
            shouldPrintPoints = true;
        } else {
            result.append("**\u2718**"); // cross
            // Print only if points were not obtained
            // Otherwise, "you failed and got 0 points" implies there were points to give
            shouldPrintPoints = points.available != 0;
        }

        if (shouldPrintPoints) {
            result.append(" ");

            result.append("*[");
            result.append(points);
            result.append("]*");
        }

        result.append(" ");

        result.append(name);

        if (details != null) {
            result.append("  ");
            result.append(System.lineSeparator());
            result.append(details);
        }

        for (final GradingProblem problem : problems) {
            result.append("  ");
            result.append(System.lineSeparator());
            result.append("\u00A0  "); // Markdown trick to do new paragraphs within a bullet
            result.append(System.lineSeparator());
            result.append(problem.toString());
            result.append(System.lineSeparator());
        }

        return result.toString();
    }

    public String toCsv() {
        Double d = new Double(points.obtained);
        Integer i = d.intValue();

        return i.toString();
    }
}
