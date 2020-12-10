package ch.epfl.sweng.project.weather;

import androidx.annotation.NonNull;

/**
 * This class represents a weather forecast, i.e. the weather for today and the two coming days.
 * <p>
 * Solution notes:
 * - We used an enumeration for elegance, but a days offset would have worked as well.
 */
public final class WeatherForecast {
    private final WeatherReport[] reports;

    /**
     * Instantiate a forecast
     *
     * @param reports three weather reports, one per day.
     *                reports[0] must be today's report
     *                reports[1] must be tomorrow's report
     *                reports[2] must be the report of the day after tomorrow
     */
    public WeatherForecast(WeatherReport[] reports) {
        if (reports.length < 3) {
            throw new IllegalArgumentException("reports array must contain at least 3 elements.");
        }

        this.reports = reports;
    }

    public enum Day {
        TODAY, TOMORROW, AFTER_TOMORROW
    }

    /**
     * Get the weather report for a specific day
     *
     * @param offset the day for which you need the report
     * @return the weather report for that day
     */
    public WeatherReport getWeatherReport(@NonNull Day offset) {
        return this.reports[offset.ordinal()];
    }
}
