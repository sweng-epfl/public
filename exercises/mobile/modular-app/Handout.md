# Exercise 2: making the app modular

In this second exercise, you will add weather forecast capabilities to your app. Using an external service, [OpenWeatherMap](https://api.openweathermap.org/), you will display to the user a weather forecast at their location, or at an address of their choice. For that, you will also need to be able to find out where the user is located.

> :information_source: In this exercise, we will use features that were introduced in Java 8. These features were not available on older versions of Android (Android < N, API Level 24). To use them, we either need to provide alternatives, or to lose compatibility with these older devices. Here, we choose to do the latter. To enable this, please go to your app `build.gradle` and set `minSdkVersion` to `24`.

In this exercise, we will mostly focus on writing good abstractions and working modules. Handling errors is for later, so it doesn't matter _for now_ if your app crashes when something goes wrong. We will learn later how to handle errors gracefully.

## Write down the structure of the system

Before diving into the code, we ask you to first write down what the system you are designing will look like. 

Please take a moment to think about the modules and interfaces you will need and what kind of methods they should expose to their client (the rest of your app). We advise you to write that down on a sheet of paper, or try to explain it to a rubber duck on your desk, to convince you that it works and will fulfill its purpose.

Remember that your code will need to retrieve the location (latitude and longitude) of the user, let them input a custom address, convert an address to a location, and finally retrieve the weather at a given location.

## Design and write the interfaces

> :warning: We advise you not to read this section before thinking about the previous one, as we will "spoil" one of the possible structures of this system.

Now that you know how your system should work, you can write interfaces that _describe_ this behaviour. Don't think too much about the actual implementation, just write an interface that exposes the behaviour you need.

In theory, you will have three interfaces (the names are indicative):
 - `LocationService` that defines a method to get the current location of the device
 - `GeocodingService` that defines a method to convert an address to a location (the reverse can also be useful if you want to display it to the user)
 - `WeatherService` that defines a method to get the weather at a given location.

You will also need a few classes to store the resulting forecast data returned by the server. Think about _which values your client will need_ (while still making sure you can provide them - have a look at the [OneCall API documentation](https://openweathermap.org/api/one-call-api)). A `Location` class will also be needed to store the location - both when retrieving it in and when sending it to the weather API. Android already provides one, but you don't want to use it here, as it would not cleanly allow you to separate concerns and use another provider in the future with the same interfaces. Feel free to create any additional class to store the data you think you need.

Regarding the design of the classes storing the forecast, here is some advice. The API returns data in JSON (a sort of key-value object notation), containing in particular the current weather and the forecast for the following days. See which fields interest you and design the interfaces and classes you need.

> :information_source: When you design a service that _waits_ on something (for example: an external API), you usually don't want to block the caller, but rather use asynchronous structures. We will introduce them later in the course, so **we don't ask you to use them for now**. **However, remember that -outside of this exercise- calling a web service synchronously from the main thread is in most cases a very bad practice.** 

## Implement the interfaces

Now that you wrote the interfaces for your modules, you need to implement them. For each interface, create a class that implements the methods you defined. Here are a few hints and tips on what to do precisely. Feel free to ask on the forum if you need suggestions!

> :information_source: You may notice while writing the implementation that the interface is missing something: a method parameter, a list of thrown exceptions, etc. This is not that big of a deal: sometimes, when writing the actual code, you come up with something you didn't think you'd need. Updating the interface at this stage to provide what you need is not an issue. **However, please go through the same reasoning every time you update the interface as when you wrote it: is it specific to my implementation? Does it give too much details about the implementation? Is it really needed? ...**. 

> :warning: If you realize you need a `Context` (or similar Android system classes) to do something, you should not add it as a method parameter in the interface, as it makes it tightly coupled to the Android operating system. Think about other ways to provide the `Context` to your implementation. 

> :information_source: A `Context` is an interface to global information about an application environment. This is an abstract class whose implementation is provided by the Android system. It allows access to application-specific resources and classes, as well as up-calls for application-level operations such as launching activities, broadcasting and receiving intents, etc. (Source: [Android Documentation](https://developer.android.com/reference/android/content/Context))

### HTTP requests

There are a few options to access the network in Android. We suggest you to use the native `HttpsUrlConnection`. See the [Android Documentation](https://developer.android.com/training/basics/network-ops/connecting#httpsurlconnection) for a good example of how it works.

But first, you need to disable a check. By default, Android disallows doing networking on the main thread - as we explained, doing that is very bad. But since we will introduce asynchronicity later in the course, you must add this code somewhere (for example in your main activity). This needs to run before the first call to your service.

```java
// TODO: Remove this line of code once I learn about asynchronous operations!
StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build()); 
```

> Hint: you will need to add the `INTERNET` permission to access the net. To do so, add the following to your App Manifest (`AndroidManifest.xml`)
> `<uses-permission android:name="android.permission.INTERNET" />`
> To apply the change, **uninstall the app from the device/virtual device**. Otherwise, the permission won't be granted.

>Hint: we provide here a small example of HTTP request done in this fashion:
```java
String queryUrl = "www.google.com"
URL url = new URL(queryUrl);

InputStream stream = null;
HttpsURLConnection connection = null;

connection = (HttpsURLConnection) url.openConnection();
connection.setReadTimeout(3000);
connection.setConnectTimeout(3000);
connection.setRequestMethod("GET");

// Already true by default but setting just in case; needs to be true since this request
// is carrying an input (response) body.
connection.setDoInput(true);

int responseCode = connection.getResponseCode();
// Do something with responseCode

stream = connection.getInputStream();

// Do something with the stream

stream.close();
connection.disconnect();
```
>This example is simplified to be the most concise so it is not perfect, it gives a sense of how to perform requests. Feel free to make it great.
### JSON Parsing

After receiving the result of the HTTP request, you will need to parse it to extract the values and build your data objects.

You should have received the response as a `String`. To make it exploitable, you will want to have a `JSONObject`. Look at the documentation of the class `org.json.JSONTokener` to see how you can do that.

`JSONObject` makes it very simple to access any property in the JSON data you received.

For example, in the following (example) JSON object:

```json
{
  "weather": {
    "temperature": {
      "current": 10.0
    }
  }
}
```

To access the temperature in this **example** JSON, you will write something like:

```java
public double getTemperature(JSONObject json) throws JSONException {
  return json.getJSONObject("weather").getJSONObject("temperature").getDouble("current");
}
```

You will find the exact content of the JSON in the [OneCall API documentation](https://openweathermap.org/api/one-call-api). Please note that in the array `daily`, the first entry (i.e. `daily[0]`) corresponds to the current day.

### Location

To get the location, Android provides a system service called `LocationManager`. To retrieve it, a `Context` is required:
`(LocationManager) context.getSystemService(Context.LOCATION_SERVICE)`.

This service provides a method called `getLastKnownLocation(...)` (see [the documentation](https://developer.android.com/reference/android/location/LocationManager.html#getLastKnownLocation%28java.lang.String%29)) that immediately returns the last recorded location. To work, it requires a _location provider_ which you can obtain from the method `getBestProvider` of the same class. This method in turn requires a `Criteria` to compare the providers.

> :warning: **Warning:** `getLastKnownLocation` will not return anything if no app tried to get a position before you do. As we are not trying to teach you everything about Android here, this will suffice. To generate a location, you will therefore need to open Google Maps once on the virtual device before actually testing the app. If you want, you can try to use more reliable positionning methods. Feel free!

### Geocoding

Android provides a small service called [Geocoder](https://developer.android.com/reference/android/location/Geocoder). 
This service provides everything your implementation will normally need.

> :warning: Geocoder relies on the Google Play Services to work, so you need to make sure they are installed on your virtual device for this to work. Other providers of this feature may however exist, if you don't want to use Google Play. 

## Display the weather

To wrap up, you will need to use the services you wrote to display the weather in an actual activity.

We suggest you create a new activity, and create a button on the main screen to open it. 

In this new activity, do everything you need to display the weather: add a text field for the user to input a city name, a toggle or checkbox to allow them to use their GPS location instead, and finally add a button to fetch and display the weather. Don't forget to request the permissions required to access the location of the device! 

Feel free to use icons to make it as pretty as you want, although this is not required and this is not the goal of this exercise. 

### Quick note on permissions

To access the location, the app will first have to request the corresponding permission. 

Android provides multiple methods to check for and request permissions. The two you are likely to use are `ActivityCompat.checkSelfPermission` and `ActivityCompat.requestPermissions`. Check out the documentation of those methods, as well as some examples, to see how to use them. You will probably have to call them directly from your activity - this is not a problem for now, but please try to keep the rest of the location related code in your service implementation.

