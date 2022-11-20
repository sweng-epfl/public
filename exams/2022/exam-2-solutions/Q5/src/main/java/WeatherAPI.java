/**
 * API that fetches weather data.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface WeatherAPI {
    /**
     * Get the current weather for a city.
     *
     * @param city The city to get the weather for.
     * @return The current weather for the city.
     */
    String getWeather(String city) throws ServiceNotAvailableException;
}
