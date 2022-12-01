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
        // TODO: Test that `Weather.today` returns `"Sunny"`, without changing `Weather.today`
    }

    @Test
    void clickingButtonSetsWeatherToSunny() {
        // TODO: Test that `WeatherView.clickButton` sets `WeatherView.weather` to `"Sunny"`, without changing `WeatherView`
    }

    @Test
    void weathersContainsYesterdayAndToday() {
        // TODO: Test that `Weather.printWeathers` yields `"Today: Sunny"` and `"Yesterday: Cloudy"` in any order, **changing `Weather.printWeathers` as necessary**
        //       However, keep the logic of prefixing weathers inside `Weather`; make minimal changes
    }
}
