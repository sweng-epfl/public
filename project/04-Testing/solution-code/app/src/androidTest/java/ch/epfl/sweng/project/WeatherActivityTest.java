package ch.epfl.sweng.project;

import android.Manifest;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.GrantPermissionRule;

import com.google.common.collect.Lists;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.mockito.Mockito;

import java.io.IOException;
import java.security.Permission;
import java.util.Random;

import ch.epfl.sweng.project.geocoding.Address;
import ch.epfl.sweng.project.geocoding.GeocodingModule;
import ch.epfl.sweng.project.geocoding.GeocodingService;
import ch.epfl.sweng.project.location.Location;
import ch.epfl.sweng.project.location.LocationModule;
import ch.epfl.sweng.project.location.LocationService;
import ch.epfl.sweng.project.weather.WeatherForecast;
import ch.epfl.sweng.project.weather.WeatherModule;
import ch.epfl.sweng.project.weather.WeatherReport;
import ch.epfl.sweng.project.weather.WeatherService;
import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@HiltAndroidTest
@UninstallModules({WeatherModule.class, LocationModule.class, GeocodingModule.class})
public class WeatherActivityTest {
    private HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule
    public RuleChain testRule = RuleChain.outerRule(hiltRule)
            .around(new ActivityScenarioRule<>(WeatherActivity.class));

    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION);

    @BindValue
    public WeatherService weatherService = Mockito.mock(WeatherService.class);
    @BindValue
    public LocationService locationService = Mockito.mock(LocationService.class);
    @BindValue
    public GeocodingService geocodingService = Mockito.mock(GeocodingService.class);

    private static final Random RANDOM = new Random();
    private static final String CITY = "Lausanne";
    private static final Location TEST_LOCATION = new Location((RANDOM.nextDouble() - 0.5) * 90, (RANDOM.nextDouble() - 0.5) * 90);
    private static final WeatherReport TEST_REPORT_1 = new WeatherReport(10, 5, 15, "clear", "clear");
    private static final WeatherReport TEST_REPORT_2 = new WeatherReport(20, 15, 25, "cloudy", "cloudy");
    private static final WeatherReport TEST_REPORT_3 = new WeatherReport(30, 25, 35, "rain", "rain");

    private static final WeatherForecast TEST_FORECAST = new WeatherForecast(new WeatherReport[]{TEST_REPORT_1, TEST_REPORT_2, TEST_REPORT_3});
    private static final Address TEST_ADDRESS = new Address(Lists.newArrayList("Avenue Piccard 1", CITY, "Switzerland"));

    @Test
    public void clickingOnMyPositionGetsCurrentPositionAndDisplaysWeather() throws IOException {
        // Initialize the mocked behaviour of the services
        Mockito.when(locationService.getCurrentLocation()).thenReturn(TEST_LOCATION);
        Mockito.when(weatherService.getForecast(TEST_LOCATION)).thenReturn(TEST_FORECAST);

        // Perform UI interactions
        onView(withId(R.id.weatherUseGps)).perform(click());
        onView(withId(R.id.weatherQuery)).perform(click());

        // Check that the weather is properly displayed
        checkWeatherIsProperlyDisplayed();

        // Ensure methods were called with the correct arguments
        Mockito.verify(locationService).getCurrentLocation();
        Mockito.verify(weatherService).getForecast(TEST_LOCATION);
    }

    @Test
    public void usingHardcodedCityGetsAndDisplaysWeather() throws IOException {
        // Initialize the mocked behaviour of the services
        Mockito.when(weatherService.getForecast(TEST_LOCATION)).thenReturn(TEST_FORECAST);
        Mockito.when(geocodingService.getLocation(CITY)).thenReturn(TEST_LOCATION);

        // Perform UI interactions
        onView(withId(R.id.weatherCityName)).perform(clearText(), typeText(CITY), closeSoftKeyboard());
        onView(withId(R.id.weatherQuery)).perform(click());

        // Check that the weather is properly displayed
        checkWeatherIsProperlyDisplayed();

        // Ensure methods were called with the correct arguments
        Mockito.verify(geocodingService).getLocation(CITY);
        Mockito.verify(weatherService).getForecast(TEST_LOCATION);
    }

    private void checkWeatherIsProperlyDisplayed() {
        String[] tabs = new String[]{"Today", "Tomorrow", "In 2 days"};
        WeatherReport[] reports = new WeatherReport[]{TEST_REPORT_1, TEST_REPORT_2, TEST_REPORT_3};

        for (int i = 0; i < tabs.length; ++i) {
            String tab = tabs[i];
            WeatherReport report = reports[i];

            onView(withText(Matchers.equalToIgnoringCase(tab))).perform(click());
            onView(withId(R.id.weatherMinTemperatureTextView)).check(ViewAssertions.matches(withText(String.format("%.1f", report.minimumTemperature))));
            onView(withId(R.id.weatherMaxTemperatureTextView)).check(ViewAssertions.matches(withText(String.format("%.1f", report.maximalTemperature))));
            onView(withId(R.id.weatherAvgTemperatureTextView)).check(ViewAssertions.matches(withText(String.format("%.1f", report.averageTemperature))));
            onView(withId(R.id.weatherConditionTextView)).check(ViewAssertions.matches(withText(report.weatherType)));
        }
    }
}
