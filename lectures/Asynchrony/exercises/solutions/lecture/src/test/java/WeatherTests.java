import java.util.concurrent.*;
import java.util.*;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

final class WeatherTests {
    // Do not use sleep-based methods here!
    // However, since JUnit expects tests to be synchronous, you will need to wait for the operations to finish

    @Test
    void todaysWeatherIsSunny() {
        String result = Weather.today().orTimeout(5, TimeUnit.SECONDS).join();
        assertThat(result, is("Sunny"));
    }

    @Test
    void clickingButtonSetsWeatherToSunny() {
        var future = new CompletableFuture<Void>();
        WeatherView.setCallback(() -> future.complete(null));
        WeatherView.clickButton();
        future.orTimeout(5, TimeUnit.SECONDS).join();
        assertThat(WeatherView.weather(), is("Sunny"));
    }

    @Test
    void weathersContainsYesterdayAndToday() {
        var weathers = new ArrayList<String>();
        Weather.printWeathers(weathers::add).orTimeout(5, TimeUnit.SECONDS).join();
        assertThat(weathers, containsInAnyOrder("Today: Sunny", "Yesterday: Cloudy"));
    }
}
