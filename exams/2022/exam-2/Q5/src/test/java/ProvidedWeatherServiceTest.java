import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Class for the provided WeatherService tests
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * This file is provided for your convenience.
 * You MUST uncomment the @Test annotations in order to run the provided tests
 * You MUST NOT use this file for your own tests
 * You MUST NOT rename this file.
 * You MUST NOT delete this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class ProvidedWeatherServiceTest {

    /**
     * Checks that the cache is populated when performing a request.
     */
    // TODO: Uncomment the @Test annotation to run this test
    //@Test
    public void weatherProviderReturnsIdenticalWeatherTwice() {
        var weatherService = new WeatherService();

        weatherService.getWeather("Lausanne");
        assertThat(weatherService.getWeather("Lausanne"), is("The weather in Lausanne is Rainy"));
    }

    /**
     * Checks that the cache in invalidated when the weather changes and repopulated on the next weather request.
     */
    // TODO: Uncomment the @Test annotation to run this test
    //@Test
    public void weatherProviderReturnsUpdatedWeatherAfterChange() {
        var weatherService = new WeatherService();

        weatherService.getWeather("Geneva");
        assertThat(weatherService.getWeather("Geneva"), is("The weather in Geneva is Rainy"));

        weatherService.overrideWeatherPredictions();
        assertThat(weatherService.getWeather("Geneva"), is("The weather in Geneva is Sunny"));
    }
}
