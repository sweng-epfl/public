# Android Tests

This guide introduces testing in Android.


## Meet the Espresso framework

When you work with Android, you can write both Unit tests and UI tests.
The former enable you to test individual components, whereas the UI tests allow you to ensure that the app behaves correctly.
In a unit test, you will for example make sure that a `pow(a, b)` method always returns `a^b`, whereas in a UI test you will make sure that clicking the button `Login` opens the form to let the user log in.
Both are complementary.

In a standard Android project, you have two test folders: `src/test` hosts your unit tests, and `src/androidTest` hosts your UI tests.
More precisely, tests in `src/test` run directly on your computer, and tests in `src/androidTest` run in the Android emulator, in your app.
In an `androidTest` you can start an activity, click on UI elements, check if text and components are displayed, ...

To write UI tests, Android offers the [Espresso](https://developer.android.com/training/testing/espresso) framework.
It's a collection of functions you can use in tests to interact automatically with the app and to ensure that the displayed content matches some criteria.

> :information_source: Even though you will use Espresso in this project, you might also want to take a look at [Barista](https://github.com/AdevintaSpain/Barista),
> a framework built on top of Espresso which removes most of the boilerplate needed for Espresso tasks.

In this first part, we will test the **Greeting** part of the app. We will see later that the **Weather** part is a bit harder to test, and will require some additional work.

### Installing the dependencies

In your `app/build.gradle` you will want to add the following dependencies (some may already be listed in your dependencies, while others may require version updates by pressing Alt+Enter on names highlighted in yellow):

```
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
    androidTestImplementation 'androidx.test:runner:1.4.0'
    androidTestImplementation 'androidx.test:rules:1.4.0'
    androidTestImplementation 'androidx.test.espresso:espresso-intents:3.4.0'
```

### Write the tests

Before writing the actual tests, try to remember what your "Greeting" mechanism is supposed to do.

Recall that you have two activities: 
 - On your main activity, the user is prompted for their name and can click on a button to see a greeting message
 - On a second activity, the user sees a greeting message with their name

Your main activity fires an `Intent` containing the username, which enables the second activity to retrieve the name and display it. Therefore, you can test both activities independently:
 - On the main activity, you want to make sure that when the button is clicked an intent is fired and the intent contains the username
 - On the second activity, you want to make sure that when an intent is received, the correct username is displayed

This way, you can test each activity independently.

> :information_source: Testing activities independently is great, as it allows you to tightly control the state of the activity when you start the text.
> But Espresso also allows you to change activities during a test: you can therefore write a full End-To-End test, simulating a complete usage scenario from a user.
> If you want, we suggest you try to write such a test once you understand how Espresso works.

We will begin with the main activity.

### Testing the main activity

In the `androidTest` directory, you should find a package corresponding to your app, with an example test called `ExampleInstrumentedTest`.
This test is very basic, so you can remove it.
Create a new class that you can for example call `MainActivityTest`.

Just before the `public class ...` declaration, add the following annotation: `@RunWith(AndroidJUnit4.class)` (don't forget to import the classes using Alt+Enter).
This indicates that the test will be ran using a special version of JUnit4.

> :information_source: Android only works with JUnit 4 and does not support JUnit 5 yet.
> You have probably been using JUnit 5 until now, so you may find that some things work differently with this version. We advise you to search the web if you need some advices on how to use it.

Then, you will want to start your app, run your test, and close it.
Espresso provides a quick way to start the activity and to close it after each test: the `ActivityScenarioRule`.
You can see how it works [in the documentation](https://developer.android.com/training/testing/junit-rules#activity-test-rule).
To use it, add the following in your test class:

```java
@Rule
public ActivityScenarioRule<MainActivity> testRule = new ActivityScenarioRule<>(MainActivity.class);
```

You can name the rule however you want. Here, you need to provide as an argument the class of your activity.

You can now create a new method, annotate it with `@Test`, and write your test in it.

Here are some tips to write the test.

First, to select UI elements to act on them, you will use `androidx.test.espresso.Espresso`'s method `onView`.
This method takes a `ViewMatcher` as a parameter. The most common one is `ViewMatchers.withId` that takes an ID, just like the `findViewById` method in your activities.
You can therefore write `Espresso.onView(ViewMatchers.withId(R.id.myTextField)` to get a text field.

Once you get the element, you can perform actions on it using method `perform()`, with arguments all being methods of `ViewActions`.
We suggest you to have a look at the methods offered by this class, as they are self explanatory.

> :information_source: It can be quite cumbersome to write the name of the classes every time. We suggest you to statically import the methods you use in your tests.
> For example, if you write `import static androidx.test.espresso.matcher.ViewMatchers.withId` you can then write `withId` directly, instead of `ViewMatchers.withId`.

Finally, you will want to be able to catch the intent.
For that, you will use the `espresso-intents` library, that you added to your `build.gradle` before.
The [Documentation](https://developer.android.com/training/testing/espresso/intents#java) of this library contains all you need to know.

> :warning: **Please note that the doc is outdated.** You **should not** use an `IntentsTestRule`.
> Instead, your test should begin with `Intents.init()` and end with `Intents.release()`. The rest of the documentation is fine. 

Before running the tests, you will need to disable some features on your device.
Launch the emulator, then open the Settings app on it.
Click on "About emulated device" at the bottom, then click multiple times on the build number, at the bottom of the page.
You need to click about 10 times, until a messages tells you that you are now a developper.
Now, go back to the settings menu and go in the "System" section. Unroll the "Advanced" category and click on "Developer options".
Scroll down to the "Drawing" section. You must now disable (set to "animations off") the following 3 settings:
 - Window animation scale
 - Transition animation scale
 - Animator duration scale

You can now run the test by clicking on the green arrows at the left of the test class name.

If your tests don't pass, make sure that:
 - you disabled animations on the device as explained before
 - your test is complete (for example, did you _close the keyboard_ after typing the name?)
 - you didn't forget to `init` and `release` the `Intents` library
 - your intent filter is correct

You can also check what happens on your emulator screen when the test is running to see if something is going wrong. If you still don't know what's wrong, ask on the course forum.
Don't forget to provide a link to your GitHub repository and the error reported when running the test.

### Testing the Greeting activity

Testing the greeting activity will be a bit different. Here, we want to control how the activity is started, so we cannot use a `Rule` as before.

Start by creating a new test class as before, with the `@RunWith` annotation. Create a new test method, and annotate it with `@Test` as before.

In the test, you will first need to create the intent that will launch the activity.
It is done in the same way as in your code, except that you don't have a `Context`.
In tests, you can use `ApplicationProvider.getApplicationContext()` to get it.

Then, you will need to start your activity, using `ActivityScenario.launch(intent)`. 

Finally, you will be able to select your view and make sure it contains the text you expect.
Selection is done as before, using `onView`.
To check, you will use `check()` instead of `perform()`.
The arguments of `check` are any `Matcher` working on a `View`. `ViewMatchers` provides a lot of such matchers (the same ones you can use in `onView`).

To close the activity, you will need to call method `close` on your `ActivityScenario`. Alternatively, you can use a `try-with-resource` block:

```java
try (ActivityScenario<GreetingActivity> scenario = ActivityScenario.launch(intent)) {
    // Assert stuff on the activity           
}
```

This automatically calls `scenario.close()` at the end of the try block, or if an `Exception` occurs - which guarantees you always close the activity at the end of your test.
