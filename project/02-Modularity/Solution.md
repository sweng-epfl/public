## Writing the interfaces

Here is a proposed solution:

### `Location`

```java
package ch.epfl.sweng.project.location;

/**
 * This class represents a location in geodesic coordinates (latitude, longitude).
 *
 * Solution notes:
 *  - We used `final` fields here. Using `final` makes your location immutable, which is
 *      definitely a desired property here - as a location doesn't need to change.
 *  - We used public fields instead of writing getter methods. This is not so common in Java, but
 *      is fine for a data class since the fields are final and can therefore not be modified
 *      by the client.
 *  - A design using Interfaces instead of a final class could make sense, depending on the
 *      system you are trying to build.
 */
public final class Location {
    public final double latitude;
    public final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

```

Here is the interface used:

```java
package ch.epfl.sweng.project.location;

/**
 * This service allows you to get the current location of the user.
 */
public interface LocationService {
    /**
     * Finds the current location of the user
     *
     * @return the current location of the user
     */
    Location getCurrentLocation();
}

```

### `Weather`

We chose to use two classes: one to store a specific weather report (i.e. the predictions/status for a specific date/time) and one to store the result from the API, itself containing multiple reports.

```java
package ch.epfl.sweng.project.weather;

/**
 * This class represents a weather report.
 *
 * Solution notes:
 *  - We used `final` fields here. Using `final` makes your location immutable, which is
 *      definitely a desired property here - as a location doesn't need to change.
 *  - We used public fields instead of writing getter methods. This is not so common in Java, but
 *      is fine for a data class since the fields are final and can therefore not be modified
 *      by the client.
 *  - A design using Interfaces instead of a final class could make sense, depending on the
 *      system you are trying to build.
 */
public final class WeatherReport {
    public final double averageTemperature;
    public final double minimumTemperature;
    public final double maximalTemperature;

    public final String weatherType;
    public final String weatherIcon;

    public WeatherReport(double averageTemperature, double minimumTemperature, double maximalTemperature, String weatherType, String weatherIcon) {
        this.averageTemperature = averageTemperature;
        this.minimumTemperature = minimumTemperature;
        this.maximalTemperature = maximalTemperature;
        this.weatherType = weatherType;
        this.weatherIcon = weatherIcon;
    }
}
```

```java
package ch.epfl.sweng.project.weather;

import androidx.annotation.NonNull;

/**
 * This class represents a weather forecast, i.e. the weather for today and the two coming days.
 * <p>
 * Solution notes:
 * - We used an enumeration for elegance, but an int days offset would have worked as well.
 */
public final class WeatherForecast {
    private final WeatherReport[] reports;

    /**
     * Instantiate a forecast
     *
     * @param reports three weather reports, one per day.
     *                reports[0] must be today's report
     *                reports[1] must be tomorrow's report
     *                reports[2] must be the report of the day after tomorrow
     */
    WeatherForecast(WeatherReport[] reports) {
        if (reports.length < 3) {
            throw new IllegalArgumentException("reports array must contain at least 3 elements.");
        }

        this.reports = reports;
    }

    public enum Day {
        TODAY, TOMORROW, AFTER_TOMORROW;
    }

    /**
     * Get the weather report for a specific day
     *
     * @param offset the day for which you need the report
     * @return the weather report for that day
     */
    public WeatherReport getWeatherReport(@NonNull Day offset) {
        return this.reports[offset.ordinal()];
    }
}
```

Finally, here is the interface that returns the forecast.

```java

package ch.epfl.sweng.project.weather;

import java.io.IOException;

import ch.epfl.sweng.project.location.Location;

/**
 * This interface represents a service that enables you to get weather forecasts for a specific
 * location.
 */
public interface WeatherService {

    /**
     * Get the weather forecast at a given location.
     *
     * @param location the location for which you want to get the forecast
     * @return the weather forecast for the given location
     * @throws IOException if there is a network error of any kind
     */
    WeatherForecast getForecast(Location location) throws IOException;

}

```

### `Geocoding`

Here is the data class, that only takes a list of address lines and provides a method to get them in a formatted way.

```java
package ch.epfl.sweng.project.geocoding;

import androidx.annotation.NonNull;

import java.util.Collections;

/**
 * This class represents a real world address. The `toString` method enables to get a
 * representation of this address.
 *
 * Solution notes:
 *  - This data class could contain way more fields - and in reality you could possibly even use the
 *  Android one, depending on how your project works.
 */
public final class Address {
    private final List<String> addressLines;

    public Address(List<String> addressLines) {
        this.addressLines = addressLines;
    }

    @NonNull
    public String toString(String separator) {
        StringBuilder buffer = new StringBuilder();
        for (String line : addressLines) buffer.append(line).append(separator);

        return buffer.toString();
    }

    @NonNull
    @Override
    public String toString() {
        return this.toString("\n");
    }
}
```

Here is the service, that also provides reverse GeoCoding (position to address). This was not explicitly requested in the handout, but is a nice bonus to have.

```java

package ch.epfl.sweng.project.geocoding;

import androidx.annotation.NonNull;

import java.io.IOException;

import ch.epfl.sweng.project.location.Location;

/**
 * This service enables you to get a {@link ch.epfl.sweng.project.location.Location} from a String
 * representing a location (i.e. a city, a street, a country...), or to get a {@link Address} from
 * a {@link ch.epfl.sweng.project.location.Location}
 */
public interface GeocodingService {

    /**
     * Gets the location of a textual address
     *
     * @param address the address, i.e. Lausanne, Switzerland
     * @return the location for this address
     *
     * @throws IOException if the network is unavailable
     */
    Location getLocation(@NonNull String address) throws IOException;

    /**
     * Get the address corresponding to a location
     *
     * @param location the location for which you want an address
     * @return the address of this location
     *
     * @throws IOException if the network is unavailable
     */
    Address getAddress(@NonNull Location location) throws IOException;
}
```


## Implementation

### `LocationService`

This is a quite simple implementation. It has a few caveats (that would make our product completely un-usable) but is simple enough
for our demo (that focusses on modularity, remember!). In particular, you need to get a location first from an other app in order for this to work - just open Google Maps in the emulator before trying this out. This is due to the method `getLastKnownLocation`, which returns null if nothing on the system is already subscribed to location updates. Other alternatives do exist but are more complex to use, so we stick to this imperfect behaviour here.

Because we need to access Android services, we need a `Context` to instantiate the `LocationService`. Here, we provide two factory methods that are convenient to use by the caller to instantiate everything he needs.

```java
package ch.epfl.sweng.project.location;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;

public final class AndroidLocationService implements LocationService {
    private final LocationManager locationManager;
    private final String locationProvider;
    private final Criteria locationCriteria;

    AndroidLocationService(LocationManager locationManager, String locationProvider, Criteria locationCriteria) {
        this.locationManager = locationManager;
        this.locationProvider = locationProvider;
        this.locationCriteria = locationCriteria;
    }

    private static LocationManager buildManagerFromContext(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Return a new LocationsService with a fixed location provider
     *
     * @param context  the context of the activity invoking this service
     * @param provider the location provider to use
     * @return
     */
    public static LocationService buildFromContextAndProvider(Context context, String provider) {
        return new AndroidLocationService(
                buildManagerFromContext(context),
                provider,
                null
        );
    }

    /**
     * Return a new LocationsService with a criteria to choose the best location provider
     *
     * @param context  the context of the activity invoking this service
     * @param criteria the criteria used to choose which location provider to use
     * @return
     */
    public static LocationService buildFromContextAndCriteria(Context context, Criteria criteria) {
        return new AndroidLocationService(
                buildManagerFromContext(context),
                null,
                criteria
        );
    }

    private String getLocationProvider() {
        if (this.locationProvider != null) {
            return this.locationProvider;
        }

        return this.locationManager.getBestProvider(this.locationCriteria, true);
    }

    @Override
    public Location getCurrentLocation() {
        try {
            android.location.Location loc = this.locationManager.getLastKnownLocation(getLocationProvider());

            if (loc == null) return null;

            return new Location(loc.getLatitude(), loc.getLongitude());
        } catch (SecurityException ex) {
            // We need to explicitly catch this exception, even though we throw it again immediately
            throw ex;
        }

    }
}

```

### `WeatherService` 

Here, we find the same kind of factories methods that help us build our HTTP client using the given context.

Our JSON parsing is not exactly perfect, and will probably fail for some locations if the provided JSON is incomplete. We therefore handle the errors by passing them to the caller.

```java
package ch.epfl.sweng.project.weather;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import ch.epfl.sweng.project.R;
import ch.epfl.sweng.project.location.Location;

public class OpenWeatherMapWeatherService implements WeatherService {
    private static final String API_ENDPOINT = "https://api.openweathermap.org/data/2.5/onecall";
    private static final String TEMP_UNIT = "metric";
    private static final WeatherReport NO_DATA = new WeatherReport(0, 0, 0, "N/A", "N/A");

    private final String apiKey;

    OpenWeatherMapWeatherService(String apiKey) {
        // Disable StrictMode to allow Synchronous network calls
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        this.apiKey = apiKey;
    }

    public static WeatherService buildFromContext(@NonNull Context context) {
        String key = context.getString(R.string.openweather_api_key);
        return new OpenWeatherMapWeatherService(key);
    }

    public static WeatherService buildFromApiKey(String apiKey) {
        return new OpenWeatherMapWeatherService(apiKey);
    }

    private WeatherReport parseReport(JSONObject report) throws JSONException {
        JSONObject weather = report.getJSONArray("weather").getJSONObject(0);

        return new WeatherReport(
                report.getJSONObject("temp").getDouble("day"),
                report.getJSONObject("temp").getDouble("min"),
                report.getJSONObject("temp").getDouble("max"),

                weather.getString("main"),
                weather.getString("icon")
        );
    }

    private WeatherForecast parseForecast(JSONObject forecast) throws JSONException {
        JSONArray daily = forecast.getJSONArray("daily");
        WeatherReport[] reports = new WeatherReport[Math.max(3, daily.length())];

        for (int i = 0; i < Math.max(3, daily.length()); ++i) {
            if (i >= daily.length())
                reports[i] = NO_DATA;
            else
                try {
                    reports[i] = parseReport(daily.getJSONObject(i));
                } catch (JSONException ex) {
                    Log.e("OpenWeatherMapWeatherService", "Error when parsing day " + i, ex);
                    reports[i] = NO_DATA;
                }
        }

        return new WeatherForecast(reports);
    }

    private String getRawForecast(Location location) throws IOException {
        String queryUrl = API_ENDPOINT +
                "?lat=" + location.latitude +
                "&lon=" + location.longitude +
                "&units=" + TEMP_UNIT +
                "&exclude=current,minutely,hourly" +
                "&appid=" + apiKey;

        URL url = new URL(queryUrl);

        InputStream stream = null;
        HttpsURLConnection connection = null;
        String result = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");

            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            connection.setDoInput(true);
            // Open communications link (network traffic occurs here).
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();
            if (stream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
                result = reader.lines().collect(Collectors.joining("\n"));
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;

    }

    @Override
    public WeatherForecast getForecast(Location location) throws IOException {
        String forecast = getRawForecast(location);
        try {
            JSONObject json = (JSONObject) new JSONTokener(forecast).nextValue();
            return parseForecast(json);
        } catch (JSONException e) {
            throw new IOException(e);
        }
    }
}

```

### `Geocoding`

Our implementation mostly forwards the requests to the android API and converts the result to a usable one. 
We used a few anonymous functions (lambda functions) from Java 8 to simplify the code, but you can obviously write `getLocation` in a more imperative way.

```java

package ch.epfl.sweng.project.geocoding;

import android.content.Context;
import android.location.Geocoder;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.project.location.Location;

public class AndroidGeocodingService implements GeocodingService {
    private final Geocoder geocoder;

    AndroidGeocodingService(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public static final GeocodingService fromContext(Context context) {
        return new AndroidGeocodingService(new Geocoder(context));
    }

    @Override
    public Location getLocation(@NonNull String address) throws IOException {
        return this.geocoder.getFromLocationName(address, 5)
                .stream()
                .filter(addr -> addr.hasLatitude() && addr.hasLongitude())
                .map(addr -> new Location(addr.getLatitude(), addr.getLongitude()))
                .findFirst()
                .get();
    }

    @Override
    public Address getAddress(@NonNull Location location) throws IOException {
        android.location.Address address = this.geocoder.getFromLocation(location.latitude, location.longitude, 1).get(0);
        List<String> addressLines = new ArrayList<>();
        for (int i = 0; i <= address.getMaxAddressLineIndex(); ++i)
            addressLines.add(address.getAddressLine(i));

        return new Address(addressLines);

    }
}
```

### `WeatherActivity`

We chose to create a new `WeatherActivity` to display the weather. Its XML isn't provided here (but is in the solution source code), as it only contains one TextView. We encourage you to display the weather in a more creative way if you want, as for now this weather app is not exactly pretty.

Here, we take care of:
 - Requesting the necessary permissions (we do that infinitely until we get a positive answer, which could cause an infinite loop - don't do that in a real app.)
 - Instantiating our services (in the future, we will use Dependency Injection for that, as we don't want our WeatherActivity to _care_ about how to instantiate the things it needs to work)
 - Making the call and displaying the result

```java

package ch.epfl.sweng.project;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;

import ch.epfl.sweng.project.geocoding.Address;
import ch.epfl.sweng.project.geocoding.AndroidGeocodingService;
import ch.epfl.sweng.project.geocoding.GeocodingService;
import ch.epfl.sweng.project.location.AndroidLocationService;
import ch.epfl.sweng.project.location.Location;
import ch.epfl.sweng.project.location.LocationService;
import ch.epfl.sweng.project.weather.OpenWeatherMapWeatherService;
import ch.epfl.sweng.project.weather.WeatherForecast;
import ch.epfl.sweng.project.weather.WeatherReport;
import ch.epfl.sweng.project.weather.WeatherService;

public class WeatherActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;

    private LocationService mLocationService;
    private WeatherService mWeatherService;
    private GeocodingService mGeocodingService;
    private TextView mWeatherView;
    private Switch mWeatherUseGps;
    private TextView mWeatherCityName;
    private Button mWeatherQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mWeatherView = findViewById(R.id.weatherTextView);
        mWeatherUseGps = findViewById(R.id.weatherUseGps);
        mWeatherCityName = findViewById(R.id.weatherCityName);
        mWeatherQuery = findViewById(R.id.weatherQuery);

        // Create weather and location services.
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);

        // In an actual app, we will prefer using dependency injection to get these services, as it
        // makes it way easier to test the app, and allows for quick implementation swapping.
        // You'll learn more about that in the testing step of the project!
        mLocationService = AndroidLocationService.buildFromContextAndCriteria(this, criteria);
        mWeatherService = OpenWeatherMapWeatherService.buildFromContext(this);
        mGeocodingService = AndroidGeocodingService.fromContext(this);

        // Initially clear the forecast text.
        mWeatherView.setText("");

        mWeatherQuery.setOnClickListener(v -> {
            // Load the weather on button click.
            loadWeather();
        });

        // Disable the city name text field whenever we use the GPS.
        mWeatherUseGps.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mWeatherCityName.setEnabled(!isChecked);
        });
    }

    private void displayWeather(WeatherForecast forecast, Address address) {
        Log.i("RR", "Retuened");

        mWeatherView.setText(getString(R.string.weather_forecast_format,
                address.toString(", "),
                formatWeatherReport(forecast.getWeatherReport(WeatherForecast.Day.TODAY)),
                formatWeatherReport(forecast.getWeatherReport(WeatherForecast.Day.TOMORROW)),
                formatWeatherReport(forecast.getWeatherReport(WeatherForecast.Day.AFTER_TOMORROW))
        ));
    }

    private String formatWeatherReport(WeatherReport report) {
        return getString(R.string.weather_report_format, report.averageTemperature, report.minimumTemperature, report.maximalTemperature, report.weatherType);
    }

    private void loadWeather() {
        mWeatherView.setText(R.string.weather_loading);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
            return;
        }


        try {
            Location loc;
            if(mWeatherUseGps.isChecked()) {
                // Here we use the location service to query our current location.
                loc = mLocationService.getCurrentLocation();
            } else {
                // Here we use the given city name as our query location
                String cityName = mWeatherCityName.getText().toString();
                loc = mGeocodingService.getLocation(cityName);
            }

            WeatherForecast forecast = mWeatherService.getForecast(loc);
            Address address = mGeocodingService.getAddress(loc);
            displayWeather(forecast, address);
        } catch (IOException e) {
            Log.e("WeatherActivity", "Error when retrieving forecast.", e);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            loadWeather();
            // We immediately call back the function, if the permission was not granted the prompt will be shown again
            // This is a dirty solution indeed, in the real world one would display an error message and the app
            // would work in a degraded way.
        }
    }
}
```

Don't forget to add a button somewhere to access this activity. We did that in our MainActivity. See the solution code for more info.

### Styling

If you have a look at our implementation, you will see that our activity contains a bit more code than the solution we provide here. This is because we implemented a nice look for the activity, with icons loaded from the web. We encourage you to have a look if you're interested in mobile development.