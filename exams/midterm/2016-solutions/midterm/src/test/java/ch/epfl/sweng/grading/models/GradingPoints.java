package ch.epfl.sweng.grading.models;

public final class GradingPoints {
    public enum PointsFormat {
        DETAILED,
        PARSABLE
    }


    public final int available;
    public final int obtained;
    private final PointsFormat format;


    public GradingPoints(final int available, final int obtained, PointsFormat format) {
        this.available = available;
        this.obtained = obtained;
        this.format = format;
    }


    public static GradingPoints Empty(PointsFormat format) {
        return new GradingPoints(0, 0, format);
    }

    public GradingPoints combine(final GradingPoints other) {
        final int realAvailable = Math.max(available, 0);
        return new GradingPoints(realAvailable + other.available, obtained + other.obtained, format);
    }


    @SuppressWarnings("HardcodedFileSeparator") // IntelliJ thinks '/' is a file separator...
    @Override
    public String toString()
    {
        switch (format) {
            case PARSABLE:
                StringBuilder result = new StringBuilder();
                result.append(obtained).append(" ");
                if (obtained == 1) result.append("point");
                else result.append("points");
                return result.toString();
            default:
            case DETAILED:
                return obtained + " / " + available;

        }
    }
}