import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WeatherServiceTest {
    @Test
    @Order(0)
    @DisplayName("The cache should be used when performing a second request")
    public void weatherProviderReturnsIdenticalWeatherTwice() {
        var weatherService = new WeatherService();

        weatherService.getWeather("Delémont");
        assertThat(weatherService.getWeather("Delémont"), is("The weather in Delémont is Rainy"));
    }

    @Test
    @Order(1)
    @DisplayName("The cache should be invalidated after predictions are overridden")
    public void weatherProviderReturnsUpdatedWeatherAfterChange() {
        var weatherService = new WeatherService();

        weatherService.getWeather("Fribourg");
        assertThat(weatherService.getWeather("Fribourg"), is("The weather in Fribourg is Rainy"));

        weatherService.overrideWeatherPredictions();
        assertThat(weatherService.getWeather("Fribourg"), is("The weather in Fribourg is Sunny"));
    }

    @Test
    @Order(2)
    @DisplayName("The cache should be able to cache weather for multiple cities independently")
    public void weatherProvidedReturnsWeatherForMultipleCities() {
        var weatherService = new WeatherService();

        weatherService.getWeather("Zurich");
        weatherService.getWeather("Paris");

        assertThat(weatherService.getWeather("Zurich"), is("The weather in Zurich is Rainy"));
        assertThat(weatherService.getWeather("Paris"), is("The weather in Paris is Cloudy"));
    }
}
