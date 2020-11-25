package ch.epfl.sweng.project.weather;

/**
 * This class represents a weather report.
 * <p>
 * Solution notes:
 * - We used `final` fields here. Using `final` makes your location immutable, which is
 * definitely a desired property here - as a location doesn't need to change.
 * - We used public fields instead of writing getter methods. This is not so common in Java, but
 * is fine for a data class since the fields are final and can therefore not be modified
 * by the client.
 * - A design using Interfaces instead of a final class could make sense, depending on the
 * system you are trying to build.
 */
public final class WeatherReport {
    public final double averageTemperature;
    public final double minimumTemperature;
    public final double maximalTemperature;

    public final String weatherType;
    public final String weatherIcon;

    public WeatherReport(double averageTemperature, double minimumTemperature, double maximalTemperature, String weatherType, String weatherIcon) {
        this.averageTemperature = averageTemperature;
        this.minimumTemperature = minimumTemperature;
        this.maximalTemperature = maximalTemperature;
        this.weatherType = weatherType;
        this.weatherIcon = weatherIcon;
    }
}
