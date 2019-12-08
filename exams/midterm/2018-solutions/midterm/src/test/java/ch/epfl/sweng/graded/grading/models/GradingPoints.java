package ch.epfl.sweng.graded.grading.models;

public final class GradingPoints {
    public final double available;
    public final double obtained;


    public GradingPoints(final double available, final double obtained) {
        this.available = available;
        this.obtained = obtained;
    }


    public static GradingPoints empty() {
        return new GradingPoints(0, 0);
    }


    public GradingPoints combine(final GradingPoints other) {
        final double realAvailable = Math.max(available, 0);
        return new GradingPoints(realAvailable + other.available, obtained + other.obtained);
    }


    @Override
    public String toString() {
        if (available < 0) {
            // Don't show something out of a negative number, it doesn't make sense
            return roundPointsString(obtained);
        }
        return roundPointsString(obtained) + " / " + roundPointsString(available);
    }

    private String roundPointsString(double points) {
        if (Math.floor(points) == points && Double.isFinite(points)) {
            return String.format("%d", (int) points);
        }

        return String.format("%.1f", points);
    }
}