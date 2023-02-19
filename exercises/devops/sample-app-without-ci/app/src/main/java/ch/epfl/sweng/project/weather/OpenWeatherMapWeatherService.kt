package ch.epfl.sweng.project.weather

import android.content.Context
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import ch.epfl.sweng.project.R
import ch.epfl.sweng.project.location.Location
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class OpenWeatherMapWeatherService internal constructor(apiKey: String) : WeatherService {
    private val apiKey: String
    @Throws(JSONException::class)
    private fun parseReport(report: JSONObject): WeatherReport {
        val weather = report.getJSONArray("weather").getJSONObject(0)
        return WeatherReport(
            report.getJSONObject("temp").getDouble("day"),
            report.getJSONObject("temp").getDouble("min"),
            report.getJSONObject("temp").getDouble("max"),
            weather.getString("main"),
            weather.getString("icon")
        )
    }

    @Throws(JSONException::class)
    private fun parseForecast(forecast: JSONObject): WeatherForecast {
        val daily = forecast.getJSONArray("daily")
        val reports = arrayOfNulls<WeatherReport>(3.coerceAtLeast(daily.length()))
        for (i in 0 until 3.coerceAtLeast(daily.length())) {
            if (i >= daily.length()) reports[i] = NO_DATA else try {
                reports[i] = parseReport(daily.getJSONObject(i))
            } catch (ex: JSONException) {
                Log.e("OpenWeatherMapWeatherService", "Error when parsing day $i", ex)
                reports[i] = NO_DATA
            }
        }
        return WeatherForecast(reports)
    }

    @Throws(IOException::class)
    private fun getRawForecast(location: Location): String? {
        val queryUrl = API_ENDPOINT +
                "?lat=" + location.latitude +
                "&lon=" + location.longitude +
                "&units=" + TEMP_UNIT +
                "&exclude=current,minutely,hourly" +
                "&appid=" + apiKey
        val url = URL(queryUrl)
        var stream: InputStream? = null
        var connection: HttpsURLConnection? = null
        var result: String? = null
        try {
            connection = url.openConnection() as HttpsURLConnection
            connection.readTimeout = 3000
            connection.connectTimeout = 3000
            connection.requestMethod = "GET"

            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            connection.doInput = true
            // Open communications link (network traffic occurs here).
            connection.connect()
            val responseCode = connection.responseCode
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw IOException("HTTP error code: $responseCode")
            }

            // Retrieve the response body as an InputStream.
            stream = connection.inputStream
            if (stream != null) {
                val reader = BufferedReader(InputStreamReader(stream, StandardCharsets.UTF_8))
                result = reader.lines().collect(Collectors.joining("\n"))
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
            stream?.close()
            connection?.disconnect()
        }
        return result
    }

    @Throws(IOException::class)
    override fun getForecast(location: Location?): WeatherForecast? {
        val forecast = getRawForecast(location!!)
        return try {
            val json = JSONTokener(forecast).nextValue() as JSONObject
            parseForecast(json)
        } catch (e: JSONException) {
            throw IOException(e)
        }
    }

    companion object {
        private const val API_ENDPOINT = "https://api.openweathermap.org/data/2.5/onecall"
        private const val TEMP_UNIT = "metric"
        private val NO_DATA = WeatherReport(0.0, 0.0, 0.0, "N/A", "N/A")
        fun buildFromContext(context: Context): WeatherService {
            val key = context.getString(R.string.openweather_api_key)
            return OpenWeatherMapWeatherService(key)
        }

    }

    init {
        // Disable StrictMode to allow Synchronous network calls
        StrictMode.setThreadPolicy(ThreadPolicy.Builder().permitNetwork().build())
        this.apiKey = apiKey
    }
}