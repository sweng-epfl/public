package ch.epfl.sweng.project.weather;

import java.util.concurrent.CompletionStage;

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
     */
    CompletionStage<WeatherForecast> getForecast(Location location);

}
