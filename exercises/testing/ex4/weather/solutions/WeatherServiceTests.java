import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class WeatherServiceTests {
    @Test
    public void sunny() {
        HttpClient client = clientReturning("Sunny");
        WeatherService service = new WeatherService(client);

        assertThat(service.getWeatherToday(), is(Weather.SUNNY));
    }

    @Test
    public void rainy() {
        HttpClient client = clientReturning("Rainy");
        WeatherService service = new WeatherService(client);

        assertThat(service.getWeatherToday(), is(Weather.RAINY));
    }

    @Test
    public void snowy() {
        HttpClient client = clientReturning("Snowy");
        WeatherService service = new WeatherService(client);

        assertThat(service.getWeatherToday(), is(Weather.SNOWY));
    }

    @Test
    public void easterEgg() {
        HttpClient client = clientReturning("???");
        WeatherService service = new WeatherService(client);

        assertThat(service.getWeatherToday(), is(Weather.ITS_RAINING_MEN_HALLELUJAH));
    }

    @Test
    public void other() {
        HttpClient client = clientReturning("Hail");
        WeatherService service = new WeatherService(client);

        assertThat(service.getWeatherToday(), is(Weather.UNKNOWN));
    }

    @Test
    public void clientCrashes() {
        HttpClient client = new HttpClient() {
            @Override
            public String get(String url) throws IOException {
                throw new IOException("Error!");
            }
        };
        WeatherService service = new WeatherService(client);

        assertThat(service.getWeatherToday(), is(Weather.UNKNOWN));
    }

    private static HttpClient clientReturning(String value) {
        return new HttpClient() {
            @Override
            public String get(String url) throws IOException {
                return value;
            }
        };
    }
}
