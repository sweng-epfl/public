# Android Tests

This guide introduces testing in Android.

When you work with Android, you can write both Unit tests and UI tests.
The former enable you to test individual components, whereas the UI tests allow you to ensure that the app behaves correctly.
In a unit test, you will for example make sure that a `pow(a, b)` method always returns `a^b`,
whereas in a UI test you will make sure that clicking the "Log in" button opens the form to let the user log in.
Both are complementary.

In a standard Android project, you have two test folders: `src/test` hosts your unit tests, and `src/androidTest` hosts your UI tests.
More precisely, tests in `src/test` run directly on your computer, and tests in `src/androidTest` run in the Android emulator, in your app.
In an `androidTest` you can start an activity, click on UI elements, check if text and components are displayed, and so on.

To write UI tests, Android offers the [Espresso](https://developer.android.com/training/testing/espresso) framework.
It's a collection of functions you can use in tests to interact automatically with the app and to ensure that the displayed content matches some criteria.

> [!TIP]
> You might also want to take a look at [Barista](https://github.com/AdevintaSpain/Barista),
> a framework built on top of Espresso which removes most of the boilerplate needed for Espresso tasks.

## Installing the dependencies

In your `app/build.gradle` you will want to have the following dependencies:
```
androidTestImplementation 'androidx.test.ext:junit:1.1.5'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
androidTestImplementation 'androidx.test:runner:1.5.2'
androidTestImplementation 'androidx.test:rules:1.5.0'
androidTestImplementation 'androidx.test.espresso:espresso-intents:3.5.1'
```
Don't forget to sync the Gradle file once you've modified it, so that you can actually use these dependencies during development.


## Configuring the emulator

Before running tests, you will need to disable some features on your device.
Launch the emulator, then open the Settings app on it.
Click on "About emulated device" at the bottom, then click multiple times on the build number, at the bottom of the page.
You need to click about 10 times, until a messages tells you that you are now a developper.

Now, go back to the settings menu, go in the "System" section, and click on "Developer options".
Scroll down to the "Drawing" section. You must now disable (set to "animations off") the following 3 settings:
 - Window animation scale
 - Transition animation scale
 - Animator duration scale


## Write the tests

Before writing the actual tests, try to remember what your "Greeting" mechanism is supposed to do.

Recall that you have two activities: 
- On your main activity, the user is prompted for their name and can click on a button to see a greeting message
- On a second activity, the user sees a greeting message with their name

Your main activity fires an `Intent` containing the username, which enables the second activity to retrieve the name and display it. Therefore, you can test both activities independently:
- On the main activity, you want to make sure that when the button is clicked an intent is fired and the intent contains the name
- On the second activity, you want to make sure that when an intent is received, the correct greeting is displayed

This way, you can test each activity independently.

> [!TIP]
> Testing activities independently is great, as it allows you to tightly control the state of the activity when you start the text.
> But Espresso also allows you to change activities during a test: you can therefore write an "end-to-end" test, simulating a complete usage scenario from a user.
> If you want, we suggest you try to write such a test once you understand how Espresso works.


## Testing the main activity

In the `androidTest` directory, you should find a package corresponding to your app, with an example test called `ExampleInstrumentedTest`.
This test is very basic, so you can remove it.
Create a new class that you can for example call `MainActivityTest`.

Just before the `public class ...` declaration, add the following annotation: `@RunWith(AndroidJUnit4.class)` (don't forget to import the classes using Alt+Enter).
This indicates that the test will be ran using a special version of JUnit4.

Then, you will want to start your app, run your test, and close it.
Espresso provides a quick way to start the activity and to close it after each test: the `ActivityScenarioRule`.
You can see how it works [in the documentation](https://developer.android.com/training/testing/instrumented-tests/androidx-test-libraries/rules#activity-test-rule).
To use it, add the following in your test class:
```java
@Rule
public ActivityScenarioRule<MainActivity> testRule = new ActivityScenarioRule<>(MainActivity.class);
```
You can name the rule however you want, but you need to provide as an argument the class of your activity.
You can now create a new method, annotate it with `@Test`, and write your test in it.

To write the test:
- Select UI elements with `androidx.test.espresso.Espresso`'s method `onView`, which takes a `ViewMatcher` as parameter
  - The most common one is `ViewMatchers.withId` that takes an ID, just like the `findViewById` method in your activities.
- Once you have an element, you can perform actions on it using the method `perform()`, with arguments all being methods of `ViewActions`.
  - We suggest you to have a look at the methods offered by `ViewActions`, as they are self explanatory.
- To catch intents, tests should begin by calling `Intents.init()` and end by calling `Intents.release()`, then use `intended(...)` with a matcher for the intent
  - You will likely want the `allOf` matcher combining some [`IntentMatchers`](https://developer.android.com/reference/androidx/test/espresso/intent/matcher/IntentMatchers)
  - To test the intent goes to the right activity, use `hasComponent(YourActivity.class.getName())`
- You should use `import static` statements to avoid typing the long class names every time

You can run the test by clicking on the green arrows left of the test class name.

If your tests don't pass, make sure that:
 - you disabled animations on the device as explained before
 - your test is complete (for example, did you _close the keyboard_ after typing the name?)
 - you didn't forget to `init` and `release` the `Intents` library
 - your intent matcher is correct

You can also check what happens on your emulator screen when the test is running to see if something is going wrong.


## Testing the greeting activity

Testing the greeting activity will be a bit different. Here, we want to control how the activity is started, so we cannot use a `Rule` as before.

Start by creating a new test class as before, with the `@RunWith` annotation. Create a new test method, and annotate it with `@Test` as before.

What you need is:
- Create an Intent to launch the greeting activity, just like in your code, except the context should be `ApplicationProvider.getApplicationContext()` within tests
- Launch the intent with `ActivityScenario.launch(intent)`
  - You can either manually call `close` on the value returned by that method, or use a `try-with-resource` block, i.e., `try (...) { ... }`, to do that for you
- Check properties of views by calling `.check(matches(...))` on the result of an `onView`; the matchers within `matches` are the same ones you use in `onView`
