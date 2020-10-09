# Find the bugs - advanced!

In these exercises, you will write tests to uncover bugs in code we provide.

This is a form of _regression testing_: adding tests that show the existence of bugs before you fix the bugs. The workflow is:

-   Somebody reports a bug. This can be a developer, a customer, or anyone else
-   You write a test that fails, showing the bug exists
-   You fix the bug
-   Your test now passes
-   The bug doesn't happen in any future version of the product, because there is now a test to catch it.

You may be thinking, _can't I just fix the bug first and then write a test that passes?_ The issue with doing that is that if you write the test afterwards, you can't be sure that it really catches the bug. You know that the test passes, but maybe it would have on the buggy code before you fixed it!

This time you will work on an Android application (it should remind you something 8-) ). We do not tell you where to search the bug, we instead provide you a bug report which ressembles more an user provided one.

You could of course search bugs directly into the code but we strongly recommend you to write tests instead to spot first from where the problem emerges. In a real project, it would be easier and safer this way.

To find the following bugs, you only need to write Unit Tests. You do not need to mock external services or to write Android instrumentation tests.

If you want to run the application, please follow the instructions about OpenWeatherMap in the project part 02 handout ([this chapter](https://github.com/sweng-epfl/public/blob/master/project/02-Modularity/Handout.md#register-to-openweathermap)). You need to put your secret API key into the string resources file ("res/values/strings.xml") for the entry *openweather_api_key*.

## Bug report 1

"The application does not crash but the weather information seems wrong: the temperature information seems not right."

## Bug report 2

"No crash, your application is smooth but the accuracy seems a bit off. It happens especially when the weather changes a lot between days, it seems like it is late on the prediction."

## Bug report 3

"The forecast using city name works fine but when I use the GPS, I always get tropical weather forecast. The problem is that I live in London ..."

