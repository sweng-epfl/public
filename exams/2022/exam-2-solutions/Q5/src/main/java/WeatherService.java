/**
 * Service formatting weather information into human readable strings.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN edit the body of existing methods and constructors.
 * You MUST NOT add new constructors, methods, or nested classes to this class.
 * You MUST NOT edit the signature of the existing methods.
 * You MUST NOT edit the names of existing methods and constructors.
 * You MUST NOT rename this file.
 * You MUST NOT delete this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class WeatherService {
    private final WeatherAPI weatherAPI;

    public WeatherService(WeatherAPI api) {
        this.weatherAPI = new CachingWeatherAPI(api);
    }

    public WeatherService() {
        // When the final weather API implementation is available, only this line setting the default should have to change
        // in order to use whatever default API is desired, keeping the new caching functionality that you must implement.
        this(UnstableWeatherAPI.INSTANCE);
    }

    /**
     * Gets the weather prediction for a city.
     *
     * @param city The city to get the weather for.
     * @return The weather of the city.
     */
    public String getWeather(String city) {
        try {
            return "The weather in " + city + " is " + weatherAPI.getWeather(city);
        } catch (ServiceNotAvailableException e) {
            return "The weather for " + city + " is not available.";
        }
    }

    /**
     * Override the weather predictions.
     * Temporary feature!
     * Real weather APIs will let operators do this from somewhere else in the future app.
     */
    public void overrideWeatherPredictions() {
        UnstableWeatherAPI.INSTANCE.overridePredictions();
    }
}
