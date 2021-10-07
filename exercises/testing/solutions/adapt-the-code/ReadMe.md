# Solution: Adapt the code!

## 1. Weather service

Add an `HttpClient` argument to the constructor and store it in a field, like this:

```java
private final HttpClient client;

public WeatherService(HttpClient client) {
    this.client = client;
}
```

Then use it instead of `new RealHttpClient()`.

Note that you could also pass it as a parameter of `getWeatherToday`; however, this is not a very clean design because 
any code that wants to get the weather would need to be aware that it is retrieved via HTTP.
It would also not make sense in case you write other weather services later that do not need HTTP.

If required, you can keep the parameterless constructor and explicitly instantiate the dependency just like this:

```java
public WeatherService() {
    this.client = new RealHttpClient();
}
```

We can then write unit tests in [`WeatherServiceTest.java`](src/test/java/TreasureFinderTest.java).

## 2. External dependencies

First, create an interface for getting the user's position:

```java
public interface LocationService {
    Position getUserPosition() throws Exception;
}
```

Then, implement it by wrapping `Geolocator`:

```java
public class GeolocatorLocationService implements LocationService {
    private final Geolocator geolocator = new Geolocator(Precision.FINE);
    
    Position getUserPosition() throws Exception {
        if (!geolocator.isLocationServiceRunning()) {
            geolocator.startLocationService();
        }
        PositionRange userPositionRange = geolocator.getUserPosition();
        double userLatitude = (userPositionRange.maxLatitude + userPositionRange.minLatitude) / 2.0;
        double userLongitude = (userPositionRange.maxLongitude + userPositionRange.minLongitude) / 2.0;
        
        return new Position(userLatitude, userLongitude);
    }
}
```

You can now refactor `TreasureFinder` to use `GeolocatorLocationService` by default (replacing the `geolocator` field):

```java
private final LocationService locationService;

public TreasureFinder() {
        locationService = new GeolocatorLocationService();
        }

public TreasureFinder(LocationService locationService) { this.locationService = locationService; }
```

You need to also refactor `TreasureFinder.getHint()` to use the new `LocationService` interface:

```java
public String getHint(Position treasurePos) {
    Position userPosition;
    try {
        userPosition = locationService.getUserPosition();
    } catch(Exception e) {
        return "The treasure is on Saturn!";
    }
    
    if (userPostion.latitude > 70) {
        return "Nope, the treasure is not at the North Pole.";
    }
    
    double diff = Math.sqrt(Math.pow(treasurePos.latitude - userPostion.latitude, 2) + 
                            Math.pow(treasurePos.longitude - userPostion.longitude, 2));
    
    ...
```

We can then write unit tests in [`TreasureFinderTest.java`](src/test/java/TreasureFinderTest.java).

## 3. Debugging

Q: What is the source of the bug ?

A: The problematic lines of code are located in the `Geolocator`:

```java
if (!supportInfo.hasFinegrainSupport && precision == Precision.FINE) {
    precision = Precision.COARSE;
}
```

The library, being poorly written, does not warn the user if the device supports finegrain geolocation and simply
downgrades the precision.

Looking at [`DevicePrecisionQuery.java`](src/main/java/ch/epfl/sweng/locator/DevicePrecisionQuery.java):

```java
List<Precision> result = new ArrayList<>();
result.add(Precision.COARSE);
result.add(Precision.FINE);

if (name.contains("hal9000")) {
    result.remove(1);
}
```

we can see that finegrain geolocation support is not supported for devices whose name contains "hal9000", which by looking
at [`DeviceNameQuery.java`](src/main/java/ch/epfl/sweng/locator/DeviceNameQuery.java), is indeed the case.

Q: Are you able to fix it immediately ?

A: In theory, a fix would be to change the device name to something else which does not contain "hal9000". However, as
the code is provided by an external library you are not able to modify it and therefore fix the bug.

Q: What would be the next logical step into fixing the bug ?

A: The next thing to do would be to either change the library and use another one, which you can easily do thanks to your
generic interface. Another possible solution would be to contact the author of the library or write a bug report stating
your issue.

## 4. Mocking

You can find a Mockito version of the unit tests of each part in `TreasureFinderTest.java` and `WeatherServiceTest.java`.
