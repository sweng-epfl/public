import java.util.*;

/**
 * A temporary singleton implementation of an unstable API that fetches data.
 * The weather is only available for the first time it is queried per city.
 * There is also an override function, not part of the usual Weather API interface,
 * to change the weather predictions, at which point they become available again once per city.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN add new constructors, methods, attributes, and nested classes to this class.
 * You CAN make this class implement any interface you want.
 * You CAN modify the code of existing methods and add new fields.
 * You MUST NOT rename this file.
 * You MUST NOT delete this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class UnstableWeatherAPI implements WeatherAPI, WeatherObservable {
    private final Set<String> unavailableCities = new HashSet<>();
    private Map<String, String> weatherMap = new HashMap<>();
    private final List<Runnable> overrideListeners = new ArrayList<>();

    /**
     * Singleton UnstableWeatherAPI.
     */
    public static final UnstableWeatherAPI INSTANCE = new UnstableWeatherAPI();

    private UnstableWeatherAPI() {
        // Singleton, thus the constructor is private
    }

    /**
     * Get the current weather for a city.
     *
     * @param city The city to get the weather for.
     * @return The current weather for the city.
     */
    public String getWeather(String city) throws ServiceNotAvailableException {
        if (unavailableCities.add(city)) {
            if (!weatherMap.containsKey(city)) {
                // Temporary implementation that returns fake weather
                weatherMap.put(city, city.length() > 5 ? "Rainy" : "Cloudy");
            }
            return weatherMap.get(city);
        }
        throw new ServiceNotAvailableException();
    }

    /**
     * Override the weather predictions.
     */
    public void overridePredictions() {
        unavailableCities.clear();
        var newWeather = new HashMap<String, String>();
        for (String city : weatherMap.keySet()) {
            newWeather.put(city, "Sunny");
        }
        weatherMap = newWeather;

        for (var listener : overrideListeners) {
            listener.run();
        }
    }

    @Override
    public void registerOverrideListener(Runnable listener) {
        overrideListeners.add(listener);
    }
}
