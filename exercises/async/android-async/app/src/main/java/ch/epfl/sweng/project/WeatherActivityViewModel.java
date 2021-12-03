package ch.epfl.sweng.project;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.Objects;

import javax.inject.Inject;

import ch.epfl.sweng.project.geocoding.GeocodingService;
import ch.epfl.sweng.project.location.Location;
import ch.epfl.sweng.project.location.LocationService;
import ch.epfl.sweng.project.weather.WeatherForecast;
import ch.epfl.sweng.project.weather.WeatherService;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WeatherActivityViewModel extends ViewModel {
    private LocationService locationService;
    private GeocodingService geocodingService;
    private WeatherService weatherService;

    private MutableLiveData<Location> currentLocation;
    private LiveData<Boolean> canQueryWeather;

    private MutableLiveData<String> selectedAddress = new MutableLiveData<>(null);
    private LiveData<Location> locationAtSelectedAddress;
    private LiveData<Location> selectedLocation;

    private MutableLiveData<Boolean> isUsingGPS = new MutableLiveData<>(false);
    private MutableLiveData<WeatherForecast> weather = new MutableLiveData<>(null);

    @Inject
    public WeatherActivityViewModel(LocationService locationService, GeocodingService geocodingService, WeatherService weatherService) {
        this.locationService = locationService;
        this.geocodingService = geocodingService;
        this.weatherService = weatherService;

        this.currentLocation = new MutableLiveData<>(null);
        this.locationService.subscribeToLocationUpdates(currentLocation::postValue, 3000, 1);

        this.locationAtSelectedAddress = Transformations.map(selectedAddress, address -> {
            if (address != null) {
                try {
                    return geocodingService.getLocation(address);
                } catch (IOException e) {
                    // The address is not found
                    return null;
                }
            } else {
                return null;
            }
        });

        this.selectedLocation = Transformations.switchMap(isUsingGPS, useGPS -> useGPS ? currentLocation : locationAtSelectedAddress);
        this.canQueryWeather = Transformations.map(selectedLocation, Objects::nonNull);
    }

    public LiveData<Location> getCurrentLocation() {
        return currentLocation;
    }

    public LiveData<Boolean> canQueryWeather() {
        return this.canQueryWeather;
    }

    public LiveData<WeatherForecast> getWeather() {
        return weather;
    }

    public void setSelectedAddress(String address) {
        this.selectedAddress.postValue(address);
    }

    public void refreshWeather() {
        Location loc = this.selectedLocation.getValue();

        if (loc == null) {
            Log.e("WeatherActivityViewModel", "Trying to get the weather but no location is set.");
            this.weather.postValue(null);
        } else {
            try {
                this.weather.postValue(this.weatherService.getForecast(loc));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public LiveData<Boolean> getIsUsingGPS() {
        return isUsingGPS;
    }

    public void setIsUsingGPS(boolean isUsingGPS) {
        this.isUsingGPS.postValue(isUsingGPS);
    }
}
