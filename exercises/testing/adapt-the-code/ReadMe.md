# Adapt the code!

In these exercises, you will change some provided code to allow you to test it.

The code we provide you instantiates its own dependencies. This is problematic for multiple reasons:
- Your tests may fail because of factors outside your control; for instance, testing a class that gets data from the Web may fail if the Internet connection is down for any reason.
- It may be impractical to simulate some failure cases; for instance, if you want to test what happens when a Web server is down, your tests would have to run when it really is down, which is hard to predict.
- Depending on where your tests are executed, they may not have access to external resources, such as a particular Internet service that is available only in some countries.
- Your test may change the state of the external service, in a way that is not recoverable (e.g., delete a user). You would not want your tests to wreak havoc in your production database!

You will change the code to use _dependency injection_ instead.
The idea is simple: instead of instantiating the objects and services that a class depends on (i.e., its "dependencies"), a class should receive them as parameters to its constructor (i.e., get them "injected"). Those dependencies should be _interfaces_, not specific implementations.

This will also be particularly useful to you when working with frameworks such as Android, which require your code to have a certain shape.


## Weather service

We provide a [`WeatherService` class](src/main/java/WeatherService.java) that calls an [`HttpClient` class](src/main/java/HttpClient.java) to retrieve weather information over the Internet.

Change the `WeatherService` so that it can be tested.


### Side node: without changing the constructor

Sometimes you need to test a class, but you cannot change its constructor parameters, for instance because existing code depends on the current constructor signature.
In this case, you can add _another_ constructor to the class for dependency injection. For instance, if for some reason you could not modify the `WeatherService` constructor above, you could modify the class as follows:

```java
class WeatherService {
  private final HttpClient client;

  public WeatherService() {
    this.client = new RealHttpClient();
  }

  public WeatherService(HttpClient client) {
    this.client = client;
  }

  public Weather getWeatherToday() { ... /* omitted for brevity */ ... }
}
```

In this way, the class can be used without providing constructor parameters, but you can also inject the dependencies during testing.


## External dependencies

Another issue is that the dependencies may not be defined in your code, and thus may not have a corresponding interface. For instance, consider the simplified case of Google sign-in:

```java
class GoogleUser {
  // name, email, age, etc.
}

class GoogleService {
  public static GoogleUser signIn() { /* ... */ }
}
```

Not only does `GoogleService` not implement an interface, but its `signIn` method is static! To avoid this issue, create the interface yourself, then use the [Adapter pattern](https://en.wikipedia.org/wiki/Adapter_pattern) to create the "real" implementation. The Adapter pattern is a simple concept: create adapter code so that a type can be used as if it implemented an interface. This is just like real-life adapters, where a Swiss laptop charger can be used in Japan with an adapter that "implements" the Swiss electrical "interface" on top of the Japanese one. For instance, in the Google sign-in example, the code could look like this:

```java
class MyUser {
  // ... properties of GoogleUser that you want to expose to the app ...
  // This class exists so that the rest of the app doesn't depend on GoogleUser;
  // after all, Google is just one way to sign in, you could in the future add Facebook or Twitter.
}

interface SignInService {
  MyUser signIn();
}

class GoogleSignInAdapter implements SignInService {
  public MyUser signIn() {
    GoogleUser user = GoogleService.signIn();
    // ... convert 'user' to an object of class MyUser, and return it ...
  }
}
```

Now any class that used to directly call `GoogleService` can instead have a `SignInService` as a dependency, so that you can write proper tests, and you can use `GoogleSignInAdapter` for the actual implementation in your app.

We provide [`TreasureFinder.java`](src/main/java/TreasureFinder.java) and [`Geolocator.java`](src/main/java/Geolocator.java).
The `TreasureFinder` determines how close the user is to a treasure.
However, it currently cannot be tested properly because `Geolocator` does not implement any interfaces and uses the real location of the user.
Furthermore, `TreasureFinder` must have a parameterless constructor, because of external requirements, and you cannot modify `Geolocator` (e.g. because it's in a library whose source code you do not have).
Refactor `TreasureFinder` so that it can be tested with a fake location service, and write tests for it.



## Dependency Injection on Android

Because the Android OS takes care of creating your activities, you cannot use constructor parameters for dependencies. One way to work around this is to use a static factory for dependencies, for instance:

```java
public class MyFactory {
    private static MyDependency dependency = new MyRealDependency();

    public static MyDependency getDependency() {
        return dependency;
    }

    public static void setDependency(MyDependency dependency) {
        MyFactory.dependency = dependency;
    }
}
```

You can then use this in your activities:

```java
public class MyActivity extends Activity  {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyDependency dependency = MyFactory.getDependency();
        // ... do something with dependency ...
    }
}
```

In your tests, you can then configure the dependency before the activity is created, for instance.

```java
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class){
                @Override
                protected void beforeActivityLaunched() {
                    MyFactory.setDependency(new MyFakeDependency());
                }
            };

    // ... your tests here ...
}
```

As an exercise, change the `WeatherService` and `TreasureFinder` classes from before to use this pattern. The changed classes should only have one constructor, taking no parameters.
