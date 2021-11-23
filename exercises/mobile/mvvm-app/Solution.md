# Android Project - Part 5: the MVVM pattern - Solution

## 1. Updating `LocationService`

Here is the minimal functionnality your interface should expose:

```java
    void subscribeToLocationUpdates(Consumer<Location> consumer);
```

The implementation is the following:

```java
    @Override
    public void subscribeToLocationUpdates(Consumer<Location> consumer) {      
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location loc) {
                Location location = new Location(loc.getLatitude(), loc.getLongitude());
                consumer.accept(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        try {
            // Return the current location if we already have one
            consumer.accept(getCurrentLocation());
            this.locationManager.requestLocationUpdates(getLocationProvider(), 1000, 1, listener);
        } catch (SecurityException ex) {
            // We need to explicitly catch this exception, even though we throw it again immediately
            throw ex;
        }
    }
```

In our [solution code](solution-code), we actually bring it a step further and allow the caller to define the `minTime` and `minDistance` parameters. We also store the listeners to enable the caller to _unsubscribe_ from location updates. You can look at the code if you want to see how we do it.

## 2. Writing the `WeatherActivityViewModel`

Here is our viewmodel:

```java


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

    @ViewModelInject
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
```

## 3. Refactoring the WeatherActivity

In our `WeatherActivity`, we removed the code that handled the button click.

We also created a new method `locationGranted()` that is called once the location permission has been granted by the user.

Our `onCreate` now only:
 - gets references to all the views we need using `findViewById`
 - sets the content layout
 - calls the `requestLocationPermission()` function, which will eventually call `locationGranted` once the permission has been granted by the user.

Then, `locationGranted` finishes the initialization of the activity. Here is a heavily commented version.

```java

    private void locationGranted() {
        // 1. We first load the view model we created
        this.viewModel = new ViewModelProvider(this).get(WeatherActivityViewModel.class);

        // 2. Subscribe to livedata updates
        viewModel.getWeather().observe(this, this::updateForecast);
        viewModel.getIsUsingGPS().observe(this, useGps -> mWeatherCityName.setEnabled(!useGps));
        viewModel.canQueryWeather().observe(this, mWeatherQuery::setEnabled);

        // 3. Initialize the viewModel to current state
        viewModel.setIsUsingGPS(mWeatherUseGps.isChecked());
        viewModel.setSelectedAddress(mWeatherCityName.getEditableText().toString());

        // 4. Notifiy updates to the viewModel
        // 4a. Notifiy updates about the useGPS switch
        mWeatherUseGps.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setIsUsingGPS(isChecked);
        });

        // 4b. Notifiy updates in the textfield
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

        // 4c. Notify updates about the load weather button
        mWeatherQuery.setOnClickListener(v -> {
            // Make sure that the keyboard is hidden before loading the forecast
            View currentFocus = getCurrentFocus();
            if (currentFocus != null) {
                mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            viewModel.refreshWeather();
        });


        // ... some code handling the proper display of the weather (see full solution code)
    }

    private void updateForecast(WeatherForecast forecast) {
        mLatestForecast = forecast;
        displayForecast();
    }

    private void displayForecast() {
        // ... sets the tab view to match `mLatestForecast` (see full solution code)
    }
```
