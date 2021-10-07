package ch.epfl.sweng.locator;

public final class PositionRange {

    public double minLatitude;
    public double maxLatitude;
    public double minLongitude;
    public double maxLongitude;

    public PositionRange(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
        this.minLatitude = minLatitude;
        this.maxLatitude = maxLatitude;
        this.minLongitude = minLongitude;
        this.maxLongitude = maxLongitude;
    }
}
