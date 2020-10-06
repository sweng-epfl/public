# Android Project - Part 4: Testing

In this fourth part of the SwEng project, you will learn how to write tests in the Android ecosystem. You will also learn about a dependency injection framework, Hilt, and see how to use it and how it makes things easier to test your code.

> :warning: This part of the project is quite long, and is supposed to be done over 1.5 to 2 weeks. There will not be a project part related to next week's lecture on requirements.

> :information_source: Don't forget to commit and push your code regularly! If you are stuck and decide to ask for help on Piazza, don't forget to provide a link to your code! This will help us help you :)

## Meet the Espresso framework

You should already know how to write unit tests using the JUnit framework. If not, have a look at the [exercises](https://github.com/sweng-epfl/public/tree/master/exercises/testing) from this week, as they will refresh your background knowledge on the subject. We suggest you finish these before working on the project, as they will also build upon what you already know about JUnit.

When you work with Android, you can write both Unit tests and UI tests. The former enable you to test individual components, whereas the UI tests allow you to ensure that the app behaves correctly. In a unit test, you will for example make sure that a `pow(a, b)` method always returns `a^b`, whereas in a UI test you will make sure that clicking the button `Login` opens the form to let the user log in. Both are complementary.

In a standard Android project, you have two test folders: `src/test` hosts your unit tests, and `src/androidTest` hosts your UI tests. More precisely, tests in `src/test` run directly on your computer, and tests in `src/androidTest` run in the Android emulator, in your app. In an `androidTest` you can start an activity, click on UI elements, check if text and components are displayed, ...

To write UI tests, Android offers the [Espresso](https://developer.android.com/training/testing/espresso) framework. It's a collection of functions you can use in tests to interact automatically with the app and to ensure that the displayed content matches some criteria.

> :information_source: Even though you will use Espresso in this project, you might also want to take a look at [Barista](https://github.com/AdevintaSpain/Barista), a framework built on top of Espresso which removes most of the boilerplate needed for Espresso tasks.

In this first part, we will test the **Greeting** part of the app. We will see later that the **Weather** part is a bit harder to test, and will require some additional work.

### Installing the dependencies

In your `app/build.gradle` you will want to add the following dependencies (some may already be listed in your dependencies, while others may require version updates by pressing Alt+Enter on names highlighted in yellow):

```
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'
    androidTestImplementation 'androidx.test:runner:1.3.0'
    androidTestImplementation 'androidx.test:rules:1.3.0'
    androidTestImplementation 'androidx.test.espresso:espresso-intents:3.3.0'
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

> :information_source: Testing activities independently is great, as it allows you to tightly control the state of the activity when you start the text. But Espresso also allows you to change activities during a test: you can therefore write a full End-To-End test, simulating a complete usage scenario from a user. If you want, we suggest you try to write such a test once you understand how Espresso works.

We will begin with the main activity.

### Testing the main activity

In the `androidTest` directory, you should find a package corresponding to your app, with an example test called `ExampleInstrumentedTest`. This test is very basic, so you can remove it. Create a new class, that you will call `MainActivityTest`, for example.

Just before the `public class ...` declaration, add the following annotation: `@RunWith(AndroidJUnit4.class)` (don't forget to import the classes using Alt+Enter). This indicates that the test will be ran using a special version of JUnit4.

> :information_source: Android only works with JUnit 4 and does not support JUnit 5 yet. You have probably been using JUnit 5 until now, so you may find that some things work differently with this version. We advise you to search the web if you need some advices on how to use it.

Then, you will want to start your app, run your test, and close it. Espresso provides a quick way to start the activity and to close it after each test: the `ActivityScenarioRule`. You can see how it works [in the documentation](https://developer.android.com/training/testing/junit-rules#activity-test-rule). To use it, add the following in your test class:

```java
@Rule
public ActivityScenarioRule<MainActivity> testRule = new ActivityScenarioRule<>(MainActivity.class);
```

You can name the rule however you want. Here, you need to provide as an argument the class of your activity.

You can now create a new method, annotate it with `@Test`, and write your test in it.

Here are some tips to write the test.

First, to select UI elements to act on them, you will use `androidx.test.espresso.Espresso`'s method `onView`. This method takes a `ViewMatcher` as a parameter. The most common one is `ViewMatchers.withId` that takes an ID, just like the `findViewById` method in your activities. You can therefore write `Espresso.onView(ViewMatchers.withId(R.id.myTextField)` to get a text field.

Once you get the element, you can perform actions on it using method `perform()`, with arguments all being methods of `ViewActions`. We suggest you to have a look at the methods offered by this class, as they are self explanatory.

> :information_source: It can be quite cumbersome to write the name of the classes every time. We suggest you to statically import the methods you use in your tests. For example, if you write `import static androidx.test.espresso.matcher.ViewMatchers.withId` you can then write `withId` directly, instead of `ViewMatchers.withId`.

Finally, you will want to be able to catch the intent. For that, you will use the `espresso-intents` library, that you added to your `build.gradle` before. The [Documentation](https://developer.android.com/training/testing/espresso/intents#java) of this library contains all you need to know.

> :warning: **Please note that the doc is outdated.** You **should not** use an `IntentsTestRule`. Instead, your test should begin with `Intents.init()` and end with `Intents.release()`. The rest of the documentation is fine. 

Before running the tests, you will need to disable some features on your device. Launch the emulator, then open the Settings app on it. Click on "About emulated device" at the bottom, then click multiple times on the build number, at the bottom of the page. You need to click about 10 times, until a messages tells you that you are now a developper. Now, go back to the settings menu and go in the "System" section. Unroll the "Advanced" category and click on "Developer options". Scroll down to the "Drawing" section. You must now disable (set to "animations off") the following 3 settings:
 - Window animation scale
 - Transition animation scale
 - Animator duration scale

You can now run the test by clicking on the green arrows at the left of the test class name.

If your tests don't pass, make sure that:
 - you disabled animations on the device as explained before
 - your test is complete (for example, did you _close the keyboard_ after typing the name?)
 - you didn't forget to `init` and `release` the `Intents` library
 - your intent filter is correct

You can also check what happens on your emulator screen when the test is running to see if something is going wrong. If you still don't know what's wrong, ask a TA - don't forget to provide a link to your GitHub repository and the error reported when running the test.

### Testing the Greeting activity

Testing the greeting activity will be a bit different. Here, we want to control how the activity is started, so we cannot use a `Rule` as before.

Start by creating a new test class as before, with the `@RunWith` annotation. Create a new test method, and annotate it with `@Test` as before.

In the test, you will first need to create the intent that will launch the activity. It is done in the same way as in your code, except that you don't have a `Context`. In tests, you can use `ApplicationProvider.getApplicationContext()` to get it.

Then, you will need to start your activity, using `ActivityScenario.launch(intent)`. 

Finally, you will be able to select your view and make sure it contains the text you expect. Selection is done as before, using `onView`. To check, you will use `check()` instead of `perform()`. The arguments of `check` are any `Matcher` working on a `View`. `ViewMatchers` provides a lot of such matchers (the same ones you can use in `onView`).

> :information_source: To close the activity, you will need to call method `close` on your `ActivityScenario`. Alternatively, you can use a `try-with-resource` block:

```java
try (ActivityScenario<GreetingActivity> scenario = ActivityScenario.launch(intent)) {
	// Assert stuff on the activity           
}
```

This automatically calls `scenario.close()` at the end of the try block, or if an `Exception` occurs - which guarantees you always close the activity at the end of your test.

## Dependency Injection

**Dependency injection** (DI) is a technique widely used in software engineering. By following the principles of DI, you establish the foundations for a good app architecture.

Implementing dependency injection provides you with the following advantages:

- Reusability of code
- Ease of refactoring
- Ease of testing

In order to test your `WeatherActivity` independently from your location, geocoding and weather services, you will need to refactor your code using the Hilt framework we introduce below.

> :information_source: When you write a UI test for an activity, you want to test it in isolation, meaning that the test should not depend on an external service. For example, OpenWeatherMap will provide you with different data everytime you call, since the weather is not static. To test your WeatherActivity efficiently, you need to use fixed data - you test the activity _in isolation_ from the WeatherService.

### Meet the Hilt framework

[Hilt](https://dagger.dev/hilt/) is a Dependency injection library for Android that reduces the boilerplate of using manual DI in your project. Doing manual dependency injection requires constructing every class and its dependencies by hand and using containers to reuse and manage dependencies. Moreover, the way Android is designed prevents developers to fully embrace DI in a manual way as the system controls the instantiation and destruction of application components (such as Activities).

In this section, you will use Hilt to provide the services `WeatherActivity` needs (i.e. its dependencies `LocationService`, `WeatherService` and `GeocodingService`) without creating them in the `onCreate` method.

> :warning: This part of the project can seem hard. However, Hilt hides as much complexity as possible, to the point that all you need to know to complete it can be learnt by reading the [relevant Android documentation](https://developer.android.com/training/dependency-injection/hilt-android).

### Installing the dependencies

First, add the `hilt-android-gradle-plugin` plugin to your project's root build.gradle:

```groovy
buildscript {
    ...
    dependencies {
        ...
        classpath 'com.google.dagger:hilt-android-gradle-plugin:2.28-alpha'
    }
}
```

Then, in your app/build.gradle you will want to apply the plugin and add the following dependencies:

```groovy
...
apply plugin: 'dagger.hilt.android.plugin'

android {
    ...
}

dependencies {
    ...
    implementation 'com.google.dagger:hilt-android:2.28-alpha'
    androidTestImplementation 'com.google.dagger:hilt-android-testing:2.28-alpha'
    annotationProcessor 'com.google.dagger:hilt-android-compiler:2.28-alpha'
    androidTestAnnotationProcessor 'com.google.dagger:hilt-android-compiler:2.28-alpha'
}
```

### Setting up

To use Hilt in your app, you first need to create a class extending `Application` that is annotated with `@HiltAndroidApp`.

> :information_source: Don't forget to update the `name` field of `application` in your `AndroidManifest.xml` as follows (just replace "SwengApplication" with the name of the class):
> 
> ```xml
> <manifest ...>
>   ...
>   <application
>       android:name=".SwengApplication"
>       ...
>   >
> ```

### Usage example

Now that everything is set up, you can provide dependencies to Activities by annotating them with `@AndroidEntryPoint` and injecting fields at will with `@Inject`:

```java
@AndroidEntryPoint
public class ExampleActivity extends AppCompatActivity {

  @Inject
  MyService service;
  ...
}
```

Hilt will automatically take care of initializing the `service` field with an instance of `MyService`.

However, to make it work you need to tell Hilt how to provide instances of the `MyService` dependency. This can be done in different ways, depending on the nature of the dependency:

- If your dependency is a class you defined, you just have to use `@Inject` on its constructor to tell Hilt how to provide instances of that class. Note that the parameters of an annotated constructor of a class are themselves the dependencies of that class. Therefore, Hilt must also know how to provide instances for them.
- If your dependency is an interface or a class from an external library, you need to define a [Hilt module](https://developer.android.com/training/dependency-injection/hilt-android#hilt-modules): interfaces are injected with `@Binds`, and (external classes') instances with `@Provides`.

To continue with the previous example, we want to inject in `ExampleActivity` some custom implementation of `MyService`. Let's imagine that `MyConcreteService` implements `MyService` and has an integer as dependency:

```java
public interface MyService {
    int getSomething();
}

public class MyConcreteService implements MyService {

    private final int number;

    @Inject
    public MyConcreteService(int number) {
        this.number = number;
    }

    @Override
    public int getSomething() {
        return number;
    }
}
```

In order to be able to inject the interface `MyService` in our activity, we need to define a module:

```java
@Module
@InstallIn(ApplicationComponent.class)
public abstract class MyServiceModule {

    @Binds
    public abstract MyService bindMyService(MyConcreteService myServiceImpl); // used to inject MyService

    @Provides
    public static int provideNumber() {
        return 2; // Hilt will pass this to the MyConcreteService constructor when instantiating it
    }
}
```

This module _Binds_ interfaces to their implementations, i.e. they define which implementation should be picked by Hilt when injecting to a field of a given type. There are two ways of declaring bindings:
 - Using `@Provides`, you have to implement a method that constructs the required dependency (here, the example of the number). This method can take arguments, as long as these arguments can themselves be provided (either by an annotation or because another method `@Provides` them in the module).
 - Using `@Binds`, you have to declare an **abstract** method that takes as an argument a concrete implementation of the type you are binding (here, the example of the `MyService` binding). This concrete implementation must either be provided by a method in the module, or have a constructor annotated with `@Inject` and all its arguments must be provided by the module.

> :information_source: You can also inject the application context with `@ApplicationContext` and the activity context with `@ActivityContext`.

> :information_source: By default, Hilt uses the types to resolve `@Provides` annotations. Because the constructor takes an `int` as parameter, and the module provides an `int`, Hilt will automatically pass the later to the former. If you need to provide multiple bindings for the same type, have a look at [the documentation](https://developer.android.com/training/dependency-injection/hilt-android#multiple-bindings).


### Your turn

You need to use Hilt to provide instances of the `LocationService`, `WeatherService` and `GeocodingService` dependencies to `WeatherActivity`.

## Testing the weather activity

Hilt enables you to change which implementations to inject when running tests. This allows you to easily replace the actual implementation of a service (like the `WeatherService`) with another one that doesn't actually uses a remote server.

These "fake" implementations can either be full implementations of the interface that return some fixed values, or they can be mocked implementations, as in [Mockito](https://site.mockito.org/). A _Mock_ is quicker to define than a full implementation of an interface, and allows you to define a different behaviour in each test. You will use this approach in your tests.

In your `build.gradle` add the following dependencies: 
```
testImplementation 'org.mockito:mockito-core:1.10.19'
androidTestImplementation 'org.mockito:mockito-core:1.10.19'
androidTestImplementation 'com.google.dexmaker:dexmaker:1.2'
androidTestImplementation 'com.google.dexmaker:dexmaker-mockito:1.2'
```

Create your new `WeatherActivityTest` in the `androidTest` sources, and have a look at the [Hilt testing documentation](https://developer.android.com/training/dependency-injection/hilt-testing#ui-test). 

In particular, you will need to:
 - use a `@Rule` to instantiate Hilt (it is **complementary** to the `ActivityScenarioRule` you will also need). Please have a look at [the documentation](https://developer.android.com/training/dependency-injection/hilt-testing#multiple-testrules) to see how you can use multiple test rules in the same test class.
 - create a `CustomTestRunner` (you can copy/paste the code) and set it as the default runner
 - use the `@HiltAndroidTest` annotation instead of the `@RunWith` one 
 - uninstall the module(s) you created previously using `@UninstallModules`

In your test, you will need to define _Mocks_ to replace actual implementations of the services. For example, if you had a `HttpService` injected in your activity, you could replace it with a mock using the following:

```java

@BindValue
public HttpService httpService = Mockito.mock(HttpService.class);
```

Then, you will be able to both redefine the behaviour of the method, and check that it was called correctly.

```java

private static String TEST_URL = "https://www.epfl.ch/";
private static String TEST_PAGE_CONTENT = "<h1>Welcome to the EPFL website</h1>";

@Test
public void myExampleTest() {
    // First, redefine the behaviour
    Mockito.when(httpService.downloadUrl(TEST_URL)).thenReturn(TEST_PAGE_CONTENT);

    // then, do some stuff
    // for example:
    assertEquals(httpService.downloadUrl(TEST_URL), TEST_PAGE_CONTENT);

    // Finally, check that the method was called with correct arguments
    Mockito.verify(httpService).downloadUrl(TEST_URL);
}
```

Have a look at the [Mockito Documentation](https://site.mockito.org/) for more examples.

With these building blocks, you should now be able to test your activity in complete isolation of your services. Please check both the case of the user entering a city name, and the case of the user using their GPS position.

> :information_source: To grant permissions to your app, you may want to use [GrantPermissionRule](https://developer.android.com/reference/androidx/test/rule/GrantPermissionRule)

## Code coverage and continuous integration 

As you have learned in the course, it is important to test as much code as possible. Developers use code coverage tools to determine what proportion of the code is executed in the tests. This makes it easier to spot which parts of the code are never tested, to write new tests for these parts.

In Java, one of the tools used to measure code coverage is JaCoCo. We will add it to your project and see how it works.

In your **app** `build.gradle`, simply add the following:

```groovy
...
apply plugin: 'jacoco'

android {
    ...
    buildTypes {
        ...
        debug {
            testCoverageEnabled true
        }
    }
}

dependencies {
    ...
}



tasks.withType(Test) {
    jacoco.includeNoLocationClasses = true
    jacoco.excludes = ['jdk.internal.*']
}

task jacocoTestReport(type: JacocoReport, dependsOn: ['testDebugUnitTest', 'createDebugCoverageReport']) {

    reports {
        xml.enabled = true
        html.enabled = true
    }

    def fileFilter = ['**/R.class',
                      '**/R$*.class',
                      '**/BuildConfig.*',
                      '**/Manifest*.*',
                      '**/*Test*.*',
                      'android/**/*.*',

            // Exclude Hilt generated classes
            '**/*Hilt*.*',
            'hilt_aggregated_deps/**',
            '**/*_Factory.class',
            '**/*_MembersInjector.class'
    ]
    def debugTree = fileTree(dir: "$project.buildDir/intermediates/javac/debug/classes", excludes: fileFilter)
    def mainSrc = "$project.projectDir/src/main/java"

    sourceDirectories.setFrom(files([mainSrc]))
    classDirectories.setFrom(files([debugTree]))
    executionData.setFrom(fileTree(dir: project.buildDir, includes: [
            'jacoco/testDebugUnitTest.exec', 'outputs/code_coverage/debugAndroidTest/connected/*coverage.ec'
    ]))
}

connectedCheck {
    finalizedBy jacocoTestReport
}
```

This configuration may not work with all Gradle versions nor with all Java versions. Please contact the staff if this doesn't work.

You should be able to generate a report by running `./gradlew connectedCheck`. The report will be in `app/build/reports/jacoco/jacocoTestReport/html`. You can open `index.html` with the browser of your choice to see the precise coverage report.

We will now see how to use a coverage service on the internet to keep track of your code coverage. This is very useful on repositories on which multiple developers contribute, as it allows everyone to check the coverage for the repository or a single PR without having to run all the tests on their computer. And as you already run the tests everytime you push, thanks to Github Actions, publishing the coverage will be very easy!

In this project, we will introduce [Codecov.io](https://codecov.io/), a tool that allows you to track your code coverage. You can use other tools, like CodeClimate, if you prefer. We usually use CodeClimate in SDP, as it also gives you insights about the quality of your code.

First, please login on [Codecov.io](https://codecov.io/) using your GitHub account. Then, go to `https://codecov.io/gh/<your github username>/<your github project name>` to setup the project. Copy the **Upload Token** and go to your GitHub repository in **Settings** > **Secrets**. Click on **New Secret**, set the name to **CODECOV_TOKEN** and paste the token you copied in the value.

Now, go to your github action (that you created the previous week), and add the following:

```yaml

jobs:
    test:
        # ...
        steps:
            # ...
            - name: publish coverage
              uses: codecov/codecov-action@v1
              with:
                token: ${{ secrets.CODECOV_TOKEN }}

```

Commit, push, and wait for GitHub Actions to run your tests.

If your build succeeds, you should see a beautiful coverage report on Codecov.io. As you can see, you probably don't get a lot of coverage on your service implementations. If you have some time, you can think about how you could improve this coverage! 

That's all for this session, good job!
