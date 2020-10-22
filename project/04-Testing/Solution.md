# Android Project - Part 4: Testing - Solution

## Meet the Espresso framework

Here are a few example of basic tests:

### Testing the main activity

```java

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> testRule = new ActivityScenarioRule<>(MainActivity.class);

    // In general, it is good practice to keep test values in constant fields
    private static final String TEST_NAME = "Alice"; 

    @Test
    public void intentIsFiredWhenUserClicksOnButton() {
        Intents.init();

        onView(withId(R.id.mainName)).perform(clearText(), typeText(TEST_NAME));
        closeSoftKeyboard();
        onView(withId(R.id.mainGoButton)).perform(click());

        Intents.intended(Matchers.allOf(IntentMatchers.hasComponent(GreetingActivity.class.getName()), IntentMatchers.hasExtra(GreetingActivity.EXTRA_USER_NAME, TEST_NAME)));

        Intents.release();
    }
}

```

### Testing the Greeting activity

```java

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class GreetingActivityTest {
    private static final String TEST_NAME = "Alice";

    @Test
    public void usernameFromIntentIsDisplayedProperly() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GreetingActivity.class);
        intent.putExtra(GreetingActivity.EXTRA_USER_NAME, TEST_NAME);

        // We don't have a rule here, because we need to control the content of the intent.
        // We use try-with-resource to automatically close the activity at the end of the test
        try (ActivityScenario<GreetingActivity> scenario = ActivityScenario.launch(intent)) {
            onView(withId(R.id.greetingMessage)).check(ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString(TEST_NAME))));
        }
    }
}

```

## Dependency Injection

You can check-out the solution for this part in the [Solution Code](solution-code).

## Testing the weather activity

You need to define a custom test runner:

```java

public final class CustomTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return super.newApplication(cl, HiltTestApplication.class.getName(), context);
    }
}

```

Don't forget to set it as your default runner in your `app/build.gradle`:

```groovy


android {
    ...

    defaultConfig {
        ---

        testInstrumentationRunner "ch.epfl.sweng.project.CustomTestRunner"
    }
    ...
}
```

Here is an example of test class. 

Here are the static imports we used:

```java


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

```

We then need to declare the test with the correct annotation. We also need to unregister the modules from the main app. You may have less modules than we do, it is fine.

```java
@HiltAndroidTest
@UninstallModules({WeatherModule.class, LocationModule.class, GeocodingModule.class})
public class WeatherActivityTest {
```

We need to declare the different rules we will need. First, we need a `HiltAndroidRule` to inject the fields of the activity. Then, we also need an `ActivityScenarioRule` to launch the activity when the test starts. We need to chain both of them, using `RuleChain`.

Finally, we need a third rule to grant the location permissions to the test. This rule is independent from the other two - it doesn't need to be executed in a particular order - so we can leave it out of the `RuleChain`.

```java
    private HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule
    public RuleChain testRule = RuleChain.outerRule(hiltRule)
            .around(new ActivityScenarioRule<>(WeatherActivity.class));

    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION);
```

Then, because we unregistered the app modules, we need to provide the implementations for the injected services. Here, we will use Mockito to provide these implementations for us.

```java
    @BindValue
    public WeatherService weatherService = Mockito.mock(WeatherService.class);
    @BindValue
    public LocationService locationService = Mockito.mock(LocationService.class);
    @BindValue
    public GeocodingService geocodingService = Mockito.mock(GeocodingService.class);
```

Finally, we declare some test values that we will use in our tests:

```java
    private static final Random RANDOM = new Random();
    private static final String CITY = "Lausanne";
    private static final Location TEST_LOCATION = new Location((RANDOM.nextDouble() - 0.5) * 90, (RANDOM.nextDouble() - 0.5) * 90);
    private static final WeatherReport TEST_REPORT_1 = new WeatherReport(10, 5, 15, "clear", "clear");
    private static final WeatherReport TEST_REPORT_2 = new WeatherReport(20, 15, 25, "cloudy", "cloudy");
    private static final WeatherReport TEST_REPORT_3 = new WeatherReport(30, 25, 35, "rain", "rain");

    private static final WeatherForecast TEST_FORECAST = new WeatherForecast(new WeatherReport[]{TEST_REPORT_1, TEST_REPORT_2, TEST_REPORT_3});
    private static final Address TEST_ADDRESS = new Address(Lists.newArrayList("Avenue Piccard 1", CITY, "Switzerland"));
```

Finally, here are the two tests - one when using an address and one when using the device position. They use an util method to check that the weather is displayed correctly.

```java
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
```

You can look at the complete [solution code here](solution-codes)