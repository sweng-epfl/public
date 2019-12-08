package ch.epfl.sweng.grading.models;

import java.util.ArrayList;
import java.util.List;

public final class GradingTest {
    public final String name;
    public final String details;
    public final int availablePoints;
    private final GradingPoints.PointsFormat pointsFormat;

    // Empty iff the test was successful
    public final List<GradingProblem> problems;


    public GradingTest(final String name, final String details, final int availablePoints, final GradingPoints.PointsFormat pointsFormat) {
        this.name = name;
        this.details = details;
        this.availablePoints = availablePoints;
        this.pointsFormat = pointsFormat;

        problems = new ArrayList<>();
    }


    public GradingPoints computePoints() {
        if (availablePoints >= 0) {
            return new GradingPoints(availablePoints, problems.isEmpty() ? availablePoints : 0, pointsFormat);
        }

        return new GradingPoints(availablePoints, problems.isEmpty() ? 0 : availablePoints, pointsFormat);
    }

    @Override
    public String toString() {
        final GradingPoints points = computePoints();

        final StringBuilder result = new StringBuilder();

        result.append("- ");

        boolean passed =
                (points.available == 0) ? problems.isEmpty() :
                        (points.available > 0) ? points.obtained == points.available
                                : (points.obtained == 0);

        if (passed) {
            result.append("**\u2714**"); // tick
        } else {
            result.append("**\u2718**"); // cross
        }

        if (points.available != 0) {
            result.append(" ");

            result.append("*[");
            result.append(computePoints());
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
}
