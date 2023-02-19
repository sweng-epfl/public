package ch.epfl.sweng.project.weather

/**
 * This class represents a weather forecast, i.e. the weather for today and the two coming days.
 *
 *
 * Solution notes:
 * - We used an enumeration for elegance, but a days offset would have worked as well.
 */
class WeatherForecast internal constructor(reports: Array<WeatherReport?>) {
    private val reports: Array<WeatherReport?>

    enum class Day {
        TODAY, TOMORROW, AFTER_TOMORROW
    }

    /**
     * Get the weather report for a specific day
     *
     * @param offset the day for which you need the report
     * @return the weather report for that day
     */
    fun getWeatherReport(offset: Day): WeatherReport? {
        return reports[offset.ordinal]
    }

    /**
     * Instantiate a forecast
     *
     * @param reports three weather reports, one per day.
     * reports[0] must be today's report
     * reports[1] must be tomorrow's report
     * reports[2] must be the report of the day after tomorrow
     */
    init {
        require(reports.size >= 3) { "reports array must contain at least 3 elements." }
        this.reports = reports
    }
}