# Google Maps
Google Maps is an API that you are likely to use in your application. It can be used in many ways: to indicate the position of certain elements on the map, indicate areas of interest or to guide you to a certain point using GPS.

# API key
Before being able to use Google Maps in your application, you will need to create an API key for Google Maps. You will find [here](https://developers.google.com/maps/documentation/android-sdk/get-api-key) a tutorial on how to set up the API key.

After creating the key, you can create you Google Maps fragment or activity in your app, and you will need to add the API key in your app's manifest.

# Basic exercise
When you load the map for the first time, you will be in a place far from your actual location. The first thing we want to do when the map loads is to set the location of the map. Set it to the EPFL latitude and longitude (46.520536, 6.568318).

Add a Marker to coordinates (46.520544, 6.567825) with the label "Satellite". 

After that, add a listener to the click of the information window of the marker, print a message with the coordinates of the Marker (you can create a Toast). All elements added to the map can be clicked, long clicked, dragged, etc.

Other features that are interesting to limit what the user can do is to fix the zoom or limit it, and set a bound that the map cannot cross.

You will find examples and tutorials here:
- [Setup of a map with markers](https://developers.google.com/maps/documentation/android-sdk/map-with-marker)
- [Working with lines and shapes](https://developers.google.com/maps/documentation/android-sdk/polygon-tutorial)
- [Set current place](https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial)

# Cool features
Google map has a lot of more advanced features that might interest you. Here are some examples of such features.

The markers are fully customizable, you can modify the icon of the marker and all its properties (e.g., the opacity). Before, we defined the label of the marker as a string, but you can create an XML layout and customize the window as you want.

It is possible to add layers above the map. For instance, [here](https://developers.google.com/maps/documentation/android-sdk/utility/heatmap) is how to create a heatmap of the density of points that have been added on top of the map.

[Geocoder](https://developer.android.com/reference/android/location/Geocoder) lets you convert longitude and latitude coordinates to an address

You will find everything you need to know in the [Google Maps documentation](https://developers.google.com/maps/documentation/android-sdk/overview) and in the [API reference](https://developers.google.com/android/reference/com/google/android/gms/maps/package-summary).
# Testing
To test your code for Google Maps, you will have to write unit tests, but they will cause you some trouble because all classes in Google Maps are final so you cannot create a mock of them. 
You will have to write UI tests, but since you cannot set the inputs, it will be difficult to achieve 100% of coverage.

Therefore, we advise you to write as much as possible code that is independent from map components, and that you can easily test.

# Warning
You may want to draw lines and shapes on the map, compute line intersections or project the current position of the user on a certain road. Don't forget that the longitude latitude coordinate is **NOT** cartesian, it is spherical. So if you want to compute line intersections, projections, etc. you will have results that may surprise you. When working on small areas, you can change coordinate space before and after computations, but due to its nature, the result will always be an approximation.