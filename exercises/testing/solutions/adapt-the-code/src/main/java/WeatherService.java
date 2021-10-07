import java.io.IOException;

public class WeatherService {

    private final HttpClient client;

    /* We can keep the parameterless constructor by
       explicitly instantiating the dependency */
    public WeatherService() {
        this.client = new RealHttpClient();
    }

    /* This is the constructor used for dependency
       injection */
    public WeatherService(HttpClient client) {
        this.client = client;
    }

    public Weather getWeatherToday() {
        String data;
        try {
            // Use our injected client
            data = client.get("http://example.org/weather/today");
        } catch (IOException e) {
            return Weather.UNKNOWN;
        }

        switch (data) {
            case "Sunny":
                return Weather.SUNNY;

            case "Rainy":
                return Weather.RAINY;

            case "Snowy":
                return Weather.SNOWY;

            case "???":
                return Weather.ITS_RAINING_MEN_HALLELUJAH;

            default:
                return Weather.UNKNOWN;
        }
    }
}
