package ch.epfl.sweng.grading.models;

public final class GradingPoints {
    public enum PointsFormat {
        DETAILED,
        PARSABLE
    }


    public final double available;
    public final double obtained;
    private final PointsFormat format;


    public GradingPoints(final double available, final double obtained, PointsFormat format) {
        this.available = available;
        this.obtained = obtained;
        this.format = format;
    }


    public static GradingPoints Empty(PointsFormat format) {
        return new GradingPoints(0, 0, format);
    }

    public GradingPoints combine(final GradingPoints other) {
        final double realAvailable = Math.max(available, 0);
        return new GradingPoints(realAvailable + other.available, obtained + other.obtained, format);
    }


    private String roundPointsString(double points) {
        if (Math.floor(points) == points && Double.isFinite(points))
            return String.format("%d", (int)points);

        return String.format("%.1f", points);
    }

    @SuppressWarnings("HardcodedFileSeparator") // IntelliJ thinks '/' is a file separator...
    @Override
    public String toString()
    {
        switch (format) {
            case PARSABLE:
                StringBuilder result = new StringBuilder();
                result.append(roundPointsString(obtained)).append(" ");
                if (obtained == 1) result.append("point");
                else result.append("points");
                return result.toString();
            default:
            case DETAILED:
                return roundPointsString(obtained) + " / " + roundPointsString(available);

        }
    }
}