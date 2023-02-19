package ch.epfl.sweng.project

import android.Manifest
import android.content.pm.PackageManager
import android.location.Criteria
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import ch.epfl.sweng.project.geocoding.AndroidGeocodingService
import ch.epfl.sweng.project.geocoding.GeocodingService
import ch.epfl.sweng.project.location.AndroidLocationService
import ch.epfl.sweng.project.location.Location
import ch.epfl.sweng.project.location.LocationService
import ch.epfl.sweng.project.weather.OpenWeatherMapWeatherService
import ch.epfl.sweng.project.weather.WeatherForecast
import ch.epfl.sweng.project.weather.WeatherService
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.squareup.picasso.Picasso
import java.io.IOException

class WeatherActivity : AppCompatActivity() {
    private var mLocationService: LocationService? = null
    private var mWeatherService: WeatherService? = null
    private var mGeocodingService: GeocodingService? = null
    private var mLatestForecast: WeatherForecast? = null
    private var mWeatherUseGps: Switch? = null
    private var mWeatherCityName: TextView? = null
    private var mWeatherQuery: Button? = null
    private var mWeatherTabLayout: TabLayout? = null
    private var mWeatherConditionTextView: TextView? = null
    private var mWeatherConditionIcon: ImageView? = null
    private var mWeatherMinTemperatureTextView: TextView? = null
    private var mWeatherAvgTemperatureTextView: TextView? = null
    private var mWeatherMaxTemperatureTextView: TextView? = null
    private var mInputMethodManager: InputMethodManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        mWeatherUseGps = findViewById(R.id.weatherUseGps)
        mWeatherCityName = findViewById(R.id.weatherCityName)
        mWeatherQuery = findViewById(R.id.weatherQuery)
        mWeatherTabLayout = findViewById(R.id.weatherTabLayout)
        mWeatherConditionTextView = findViewById(R.id.weatherConditionTextView)
        mWeatherConditionIcon = findViewById(R.id.weatherConditionIcon)
        mWeatherMinTemperatureTextView = findViewById(R.id.weatherMinTemperatureTextView)
        mWeatherAvgTemperatureTextView = findViewById(R.id.weatherAvgTemperatureTextView)
        mWeatherMaxTemperatureTextView = findViewById(R.id.weatherMaxTemperatureTextView)
        mInputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        // Create weather and location services.
        val criteria = Criteria()
        criteria.powerRequirement = Criteria.POWER_MEDIUM

        // In an actual app, we will prefer using dependency injection to get these services, as it
        // makes it way easier to test the app, and allows for quick implementation swapping.
        // You'll learn more about that in the testing step of the project!
        mLocationService = AndroidLocationService.buildFromContextAndCriteria(this, criteria)
        mWeatherService = OpenWeatherMapWeatherService.buildFromContext(this)
        mGeocodingService = AndroidGeocodingService.fromContext(this)

        // Load the weather on button click.
        mWeatherQuery!!.setOnClickListener { loadWeather() }

        // Disable the city name text field whenever we use the GPS.
        mWeatherUseGps!!.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mWeatherCityName!!.isEnabled = !isChecked
        }
        mWeatherTabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                displayForecast()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // Query the forecast when the user types Enter in the city text field
        mWeatherCityName!!.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mWeatherQuery!!.callOnClick()
            }
            false
        }
    }

    private fun displayForecast() {
        if (mLatestForecast == null) {
            // If no forecast was fetched return immediately
            return
        }

        // Get the weather report that correspond to the currently selected day
        val currentTabPosition = mWeatherTabLayout!!.selectedTabPosition
        val currentReport =
            mLatestForecast!!.getWeatherReport(WeatherForecast.Day.values()[currentTabPosition])

        // Display the condition information.
        // Use Picasso to download the weather icon and display it in the ImageView
        mWeatherConditionTextView!!.text = currentReport!!.weatherType
        Picasso.get().load(getString(R.string.weather_iconurl_format, currentReport.weatherIcon))
            .fit().into(mWeatherConditionIcon)

        // Set the temperature values
        mWeatherMinTemperatureTextView!!.text =
            String.format("%.1f", currentReport.minimumTemperature)
        mWeatherAvgTemperatureTextView!!.text =
            String.format("%.1f", currentReport.averageTemperature)
        mWeatherMaxTemperatureTextView!!.text =
            String.format("%.1f", currentReport.maximalTemperature)
    }

    private fun updateForecast(forecast: WeatherForecast) {
        mLatestForecast = forecast
        displayForecast()
    }

    private fun loadWeather() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
            return
        }

        // Make sure that the keyboard is hidden before loading the forecast
        val currentFocus = currentFocus
        if (currentFocus != null) {
            mInputMethodManager!!.hideSoftInputFromWindow(getCurrentFocus()!!.windowToken, 0)
        }
        try {
            val loc: Location? = if (mWeatherUseGps!!.isChecked) {
                // Here we use the location service to query our current location.
                mLocationService!!.currentLocation
            } else {
                // Here we use the given city name as our query location
                val cityName = mWeatherCityName!!.text.toString()
                mGeocodingService!!.getLocation(cityName)
            }
            val forecast = mWeatherService!!.getForecast(loc)
            updateForecast(forecast!!)
        } catch (e: IOException) {
            Log.e("WeatherActivity", "Error when retrieving forecast.", e)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            loadWeather()
            // We immediately call back the function, if the permission was not granted the prompt will be shown again
            // This is a dirty solution indeed, in the real world one would display an error message and the app
            // would work in a degraded way.
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }
}