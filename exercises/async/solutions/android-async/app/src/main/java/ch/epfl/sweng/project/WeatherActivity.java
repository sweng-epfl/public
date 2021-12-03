package ch.epfl.sweng.project;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import ch.epfl.sweng.project.weather.WeatherForecast;
import ch.epfl.sweng.project.weather.WeatherReport;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

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

    private WeatherActivityViewModel viewModel;

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

        // Require GPS activation
        requestLocationPermission();
    }

    private void locationGranted() {
        viewModel = new ViewModelProvider(this).get(WeatherActivityViewModel.class);

        // Subscribe to ViewModel updates
        viewModel.getWeather().observe(this, this::updateForecast);
        viewModel.getIsUsingGPS().observe(this, useGps -> mWeatherCityName.setEnabled(!useGps));
        viewModel.canQueryWeather().observe(this, mWeatherQuery::setEnabled);

        // Initialize the viewModel to current state
        viewModel.setIsUsingGPS(mWeatherUseGps.isChecked());
        viewModel.setSelectedAddress(mWeatherCityName.getEditableText().toString());

        // Send events to the viewModel
        mWeatherUseGps.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setIsUsingGPS(isChecked);
        });

        mWeatherCityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSelectedAddress(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mWeatherQuery.setOnClickListener(v -> {
            // Make sure that the keyboard is hidden before loading the forecast
            View currentFocus = getCurrentFocus();
            if (currentFocus != null) {
                mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            viewModel.refreshWeather();
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

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        } else {
            locationGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            requestLocationPermission();
            // We immediately call back the function, if the permission was not granted the prompt will be shown again
            // This is a dirty solution indeed, in the real world one would display an error message and the app
            // would work in a degraded way.
        }
    }
}