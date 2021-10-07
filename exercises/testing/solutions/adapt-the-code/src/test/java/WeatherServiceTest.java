import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;

public final class WeatherServiceTest {

    @Test
    public void getWeatherTodayWithValidWeatherIsCorrect() {
        HttpClient client = new HttpClient() {
            @Override
            public String get(String url) throws IOException {
                return "Sunny";
            }
        };
        WeatherService ws = new WeatherService(client);
        assertThat(ws.getWeatherToday(), is(Weather.SUNNY));
    }

    @Test
    public void getWeatherTodayWithInvalidWeatherIsCorrect() {
        HttpClient client = new HttpClient() {
            @Override
            public String get(String url) throws IOException {
                return "invalid_weather";
            }
        };
        WeatherService ws = new WeatherService(client);
        assertThat(ws.getWeatherToday(), is(Weather.UNKNOWN));
    }

    @Test
    public void getWeatherTodayReturnsUnknownOnException() {
        HttpClient client = new HttpClient() {
            @Override
            public String get(String url) throws IOException {
                throw new IOException();
            }
        };
        WeatherService ws = new WeatherService(client);
        assertThat(ws.getWeatherToday(), is(Weather.UNKNOWN));
    }

    /************************************************/
    /*                   Mockito                    */
    /************************************************/

    @Test
    public void mockito_getWeatherTodayWithValidWeatherIsCorrect() throws IOException {
        HttpClient client = mock(HttpClient.class);
        when(client.get(anyString())).thenReturn("Sunny");
        WeatherService ws = new WeatherService(client);
        assertThat(ws.getWeatherToday(), is(Weather.SUNNY));
    }

    @Test
    public void mockito_getWeatherTodayWithInvalidWeatherIsCorrect() throws IOException {
        HttpClient client = mock(HttpClient.class);
        when(client.get(anyString())).thenReturn("invalid_weather");
        WeatherService ws = new WeatherService(client);
        assertThat(ws.getWeatherToday(), is(Weather.UNKNOWN));
    }

    @Test
    public void mockito_getWeatherTodayReturnsUnknownOnException() throws IOException {
        HttpClient client = mock(HttpClient.class);
        when(client.get(anyString())).thenThrow(new IOException());
        WeatherService ws = new WeatherService(client);
        assertThat(ws.getWeatherToday(), is(Weather.UNKNOWN));
    }
}
