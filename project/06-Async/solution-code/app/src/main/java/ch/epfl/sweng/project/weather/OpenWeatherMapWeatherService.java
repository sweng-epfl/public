package ch.epfl.sweng.project.weather;

import android.os.StrictMode;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import javax.inject.Inject;
import javax.inject.Singleton;

import ch.epfl.sweng.project.location.Location;
import ch.epfl.sweng.project.weather.WeatherModule.ApiKey;

@Singleton
public class OpenWeatherMapWeatherService implements WeatherService {
    private static final String API_ENDPOINT = "https://api.openweathermap.org/data/2.5/onecall";
    private static final String TEMP_UNIT = "metric";
    private static final WeatherReport NO_DATA = new WeatherReport(0, 0, 0, "N/A", "N/A");

    private final String apiKey;
    private final RequestQueue requestQueue;

    @Inject
    OpenWeatherMapWeatherService(@ApiKey String apiKey, RequestQueue requestQueue) {
        this.apiKey = apiKey;
        this.requestQueue = requestQueue;
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

    private WeatherForecast parseForecast(JSONObject forecast) {
        JSONArray daily;
        try {
            daily = forecast.getJSONArray("daily");
        } catch (JSONException e) {
            throw new CompletionException(e);
        }

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

    private CompletableFuture<JSONObject> getRawForecast(Location location) {
        String queryUrl = API_ENDPOINT +
                "?lat=" + location.latitude +
                "&lon=" + location.longitude +
                "&units=" + TEMP_UNIT +
                "&exclude=current,minutely,hourly" +
                "&appid=" + apiKey;

        CompletableFuture<JSONObject> cf = new CompletableFuture<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, queryUrl,
                null, cf::complete, cf::completeExceptionally);

        requestQueue.add(request);

        return cf;
    }

    @Override
    public CompletableFuture<WeatherForecast> getForecast(Location location) {
        return getRawForecast(location).thenApply(this::parseForecast);
    }
}
