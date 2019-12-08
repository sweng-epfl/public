# Solutions: Adapt the code

## Weather service

Add an `HttpClient` argument to the constructor and store it in a field, like this:

```java
private final HttpClient client;

public WeatherService(HttpClient client) {
    this.client = client;
}
```

Then use it instead of `new RealHttpClient()`.

Note that you could also pass it as a parameter of `getWeatherToday`; however, this is not a very clean design because any code that wants to get the weather would need to be aware that it is retrieved via HTTP.
It would also not make sense in case you write other weather services later that do not need HTTP.


## External dependencies

First, create an interface for getting the user's position:

```java
public interface LocationService {
    Position getUserPosition();
}
```

Then, implement it by wrapping `Geolocator`:

```java
public final class GeolocatorLocationService implements LocationService {
    private final Geolocator locator = new Geolocator();

    @Override
    public Position getUserPosition() {
        return locator.getUserPosition();
    }
}
```

You can now refactor `TreasureFinder` to use `GeolocatorLocationService` by default (replacing the `geolocator` field):

```java
private final LocationService locationService;

public TreasureFinder() {
    locationService = new GeolocatorLocationService();
}

public TreasureFinder(LocationService locationService) {
    this.locationService = locationService;
}
```

And test it:

```java
@Test
public void northPoleTriggersEasterEgg() {
    LocationService locator = serviceReturning(80.0, 1.0);
    TreasureFinder finder = new TreasureFinder(locator);
    assertThat(finder.getHint(new Position(1.0, 1.0)), is("Nope, the treasure is not at the North Pole."));
}

private static LocationService serviceReturning(double lat, double lon) {
    return new LocationService() {
        @Override
        public Position getUserPosition() {
            return new Position(lat, lon);
        }
    };
}
```


## Dependency Injection on Android

We will show here the solution for `WeatherService`; the one for `TreasureFinder` is similar.

First, create the factory:

```java
public final class HttpClientFactory {
    private static HttpClient client = new RealHttpClient();

    public static HttpClient getClient() {
        return client;
    }

    public static void setClient(HttpClient client) {
        HttpClientFactory.client = client;
    }
}
```

Then change the constructor of `WeatherService`:

```java
public WeatherService() {
    this.client = HttpClientFactory.getClient();
}
```

You can now write tests:

```java
@Test
void questionMarksMeanItsRainingMen() {
    HttpClientFactory.setClient(new HttpClient() {
        @Override
        public String get(String url) throws IOException {
            return "???";
        }
    });

    WeatherService service = new WeatherService();

    assertThat(service.getWeatherToday(), is(Weather.ITS_RAINING_MEN_HALLELUJAH));
}
```
