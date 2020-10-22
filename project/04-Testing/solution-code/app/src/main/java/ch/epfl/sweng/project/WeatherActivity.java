package ch.epfl.sweng.project;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import ch.epfl.sweng.project.geocoding.GeocodingService;
import ch.epfl.sweng.project.location.Location;
import ch.epfl.sweng.project.location.LocationService;
import ch.epfl.sweng.project.weather.WeatherForecast;
import ch.epfl.sweng.project.weather.WeatherReport;
import ch.epfl.sweng.project.weather.WeatherService;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import dagger.hilt.android.AndroidEntryPoint;
import java.io.IOException;
import javax.inject.Inject;

@AndroidEntryPoint
public class WeatherActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Inject
    LocationService mLocationService;

    @Inject
    WeatherService mWeatherService;

    @Inject
    GeocodingService mGeocodingService;

    private WeatherForecast mLatestForecast;

    private Switch mWeatherUseGps;
    private TextView mWeatherCityName;
    private Button mWeatherQuery;
    private TabLayout mWeatherTabLayout;
    private TextView mWeatherConditionTextView;
    private ImageView mWeatherConditionIcon;
    private TextView mWeatherMinTemperatureTextView;
    private TextView mWeatherAvgTemperatureTextView;
    private TextView mWeatherMaxTemperatureTextView;

    private InputMethodManager mInputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mWeatherUseGps = findViewById(R.id.weatherUseGps);
        mWeatherCityName = findViewById(R.id.weatherCityName);
        mWeatherQuery = findViewById(R.id.weatherQuery);
        mWeatherTabLayout = findViewById(R.id.weatherTabLayout);
        mWeatherConditionTextView = findViewById(R.id.weatherConditionTextView);
        mWeatherConditionIcon = findViewById(R.id.weatherConditionIcon);
        mWeatherMinTemperatureTextView = findViewById(R.id.weatherMinTemperatureTextView);
        mWeatherAvgTemperatureTextView = findViewById(R.id.weatherAvgTemperatureTextView);
        mWeatherMaxTemperatureTextView = findViewById(R.id.weatherMaxTemperatureTextView);

        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Load the weather on button click.
        mWeatherQuery.setOnClickListener(v -> loadWeather());

        // Disable the city name text field whenever we use the GPS.
        mWeatherUseGps.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mWeatherCityName.setEnabled(!isChecked);
        });

        mWeatherTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                displayForecast();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Query the forecast when the user types Enter in the city text field
        mWeatherCityName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mWeatherQuery.callOnClick();
                return true;
            }
            return false;
        });
    }

    private void displayForecast() {
        if (mLatestForecast == null) {
            // If no forecast was fetched return immediately
            return;
        }

        // Get the weather report that correspond to the currently selected day
        int currentTabPosition = mWeatherTabLayout.getSelectedTabPosition();
        WeatherReport currentReport = mLatestForecast
                .getWeatherReport(WeatherForecast.Day.values()[currentTabPosition]);

        // Display the condition information.
        // Use Picasso to download the weather icon and display it in the ImageView
        mWeatherConditionTextView.setText(currentReport.weatherType);
        Picasso.get().load(getString(R.string.weather_iconurl_format, currentReport.weatherIcon))
                .fit().into(mWeatherConditionIcon);

        // Set the temperature values
        mWeatherMinTemperatureTextView
                .setText(String.format("%.1f", currentReport.minimumTemperature));
        mWeatherAvgTemperatureTextView
                .setText(String.format("%.1f", currentReport.averageTemperature));
        mWeatherMaxTemperatureTextView
                .setText(String.format("%.1f", currentReport.maximalTemperature));
    }

    private void updateForecast(WeatherForecast forecast) {
        mLatestForecast = forecast;
        displayForecast();
    }

    private void loadWeather() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_CODE);
            return;
        }

        // Make sure that the keyboard is hidden before loading the forecast
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        try {
            Location loc;
            if (mWeatherUseGps.isChecked()) {
                // Here we use the location service to query our current location.
                loc = mLocationService.getCurrentLocation();
            } else {
                // Here we use the given city name as our query location
                String cityName = mWeatherCityName.getText().toString();
                loc = mGeocodingService.getLocation(cityName);
            }

            WeatherForecast forecast = mWeatherService.getForecast(loc);
            updateForecast(forecast);
        } catch (IOException e) {
            Log.e("WeatherActivity", "Error when retrieving forecast.", e);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            loadWeather();
            // We immediately call back the function, if the permission was not granted the prompt will be shown again
            // This is a dirty solution indeed, in the real world one would display an error message and the app
            // would work in a degraded way.
        }
    }
}