import java.util.HashMap;
import java.util.Map;

public final class CachingWeatherAPI implements WeatherAPI {
    private final WeatherAPI wrapped;
    private final Map<String, String> cache;

    public CachingWeatherAPI(WeatherAPI wrapped) {
        this.wrapped = wrapped;
        this.cache = new HashMap<>();

        if (wrapped instanceof WeatherObservable) {
            ((WeatherObservable) wrapped).registerOverrideListener(this::invalidateCache);
        }
    }

    @Override
    public String getWeather(String city) throws ServiceNotAvailableException {
        if (!cache.containsKey(city)) {
            cache.put(city, wrapped.getWeather(city));
        }
        return cache.get(city);
    }

    private void invalidateCache() {
        this.cache.clear();
    }
}
