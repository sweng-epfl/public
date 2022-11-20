# Question 5: Weather trouble (35 points)

Your company is developing a weather service with a command-line interface.

The service API is represented in the [`WeatherAPI`](src/main/java/WeatherAPI.java) interface,
but, for now, only a temporary implementation designed for testing is available: [`UnstableWeatherAPI`](src/main/java/UnstableWeatherAPI.java).
The class responsible for translating the weather predictions received from the service into text for the command-line interface is [`WeatherService`](src/main/java/WeatherService.java).

Your team lead explains that, soon, there will be multiple weather API implementations for various countries.
Some of these implementations will allow weather prediction suppliers to ask for the predictions in the service to be refreshed,
and apps using the weather service should not serve deprecated weather predictions to users.
There will also be more ways to interact with the service, such as a mobile app.

It is inefficient to always ask the service for a weather forecast, since most of the time the forecast has not changed.
Your task is to implement a _cache_ for weather forecasts, in a maintainable and self-contained way.
Specifically, once the final weather APIs are available, only the parameter-less constructor of `WeatherService`
should need to change to use the desired API, and the caching should continue to work.

The current `UnstableWeatherAPI` will help you test your cache, since it only allows one query per city before failing,
thus will not work well without a cache. It also has an override feature, which the cache needs to be aware of.

We provide you with a small test suite in [`src/test/java/ProvidedWeatherServiceTest`](src/test/java/ProvidedWeatherServiceTest.java).
We will run this test suite when grading, as well as additional tests.
You must uncomment the `@Test` annotations to run these tests.
**These tests must compile and run without modifications** to their body.
If these basic tests do not pass, you will not get points for this question.

> You can run the app via `gradlew.bat run` on Windows and `./gradlew run` on Linux and macOS.
> You can run the tests via `gradlew.bat build` on Windows and `./gradlew build` on Linux and macOS.

_You will receive up to 15 points for a correct cache implementation,_
_and up to 20 additional points if your code is clean, self-contained, and maintainable given the constraints in the statement._
