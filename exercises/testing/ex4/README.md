Dependency Injection and Mocks
==============================

Up until now, the exercises asked you to test simple code that does not depend on any external services. In a real project, a lot of code depends on various external services, such as an HTTP client to fetch data from the network, or an external library to log into Google accounts.

It is tempting to simply test this code as normal, for instance by fetching real data over HTTP. But this is problematic for multiple reasons:

- Your tests may fail because of factors outside your control; for instance, testing a class that gets data from the Web may fail if the Internet connection is down for any reason.
- It may be impractical to simulate some failure cases; for instance, if you want to test what happens when a Web server is down, your tests would have to run when it really is down, which is hard to predict.
- Depending on where your tests are executed, they may not have access to external resources, such as a particular Internet service that is available only in some countries.
- Your test may change the state of the external service, in a way that is not recoverable (e.g., delete a user). You would not want your tests to wreak havoc in your production database!

To avoid this, use _dependency injection_. The idea is simple: instead of instantiating the objects and services that a class depends on (i.e., its "dependencies"), a class should receive them as parameters to its constructor (i.e., get them "injected"). Those dependencies should be _interfaces_, not specific implementations.

For instance, consider the following classes:

```java
class HttpClient {
  String get(String url) throws IOException { /* ... access the Internet ... */ }
}

enum Weather { /* ... sunny, rainy, ... */ }

class WeatherService {
  private final HttpClient client;

  public WeatherService() {
    client = new HttpClient();
  }

  public Weather getWeatherToday() {
    String data = client.get("http://example.org/weather/today");
    // ... process data, return the weather ...
  }
}
```

The `WeatherService` class is difficult to test, because it calls an `HttpClient` that accesses the Internet. Instead, by adding an interface for HTTP clients, it can be written like this:

```java
interface HttpClient {
  String get(String url) throws IOException;
}

class RealHttpClient implements HttpClient {
  @Override
  public String get(String url) throws IOException { /* ... access the Internet ... */ }
}

enum Weather { /* ... sunny, rainy, ... */ }

class WeatherService {
  private final HttpClient client;

  public WeatherService(HttpClient client) {
    this.client = client;
  }

  public Weather getWeatherToday() {
    String data = client.get("http://example.org/weather/today");
    // ... process data, return the weather ...
  }
}
```

Since the `WeatherService` takes an `HttpClient` (which is now an interface) as a constructor parameter, it does not have a hard dependency on the Internet any more. You can then test it like this:

```java
HttpClient fakeClient = new HttpClient() {
  @Override
  public String get(String url) throws IOException { /* ... return whatever your test needs ... */ }
};

WeatherService service = new WeatherService(fakeClient);
// now assert something about service.getWeatherToday()
```

Thus it becomes easy to test any scenario you need: returning real data, returning malformed data, throwing exceptions, and so on.

**Exercise**: We provide [`HttpClient.java`](./weather/HttpClient.java), [`RealHttpClient.java`](./weather/RealHttpClient.java), and [`WeatherService.java`](./weather/WeatherService.java) files that contain implementations of the example above. Write tests for the `WeatherService`, exploring all cases you  think are interesting to test.


Optional dependency injection
-----------------------------

Sometimes you need to test a class, but you cannot change its constructor parameters, for instance existing code depends on the current constructor signature. In this case, you can add _another_ constructor to the class for dependency injection. For instance, if for some reason you could not modify the `WeatherService` constructor above, you could modify the class as follows:

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


External dependencies
---------------------

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

class GoogleSignInAdapter : SignInService {
  public MyUser signIn() {
    GoogleUser user = GoogleService.signIn();
    // ... convert 'user' to an object of class MyUser, and return it ...
  }
}
```

Now any class that used to directly call `GoogleService` can instead have a `SignInService` as a dependency, so that you can write proper tests, and you can use `GoogleSignInAdapter` for the actual implementation in your app.

**Exercise**: We provide [`TreasureFinder.java`](./treasure/TreasureFinder.java) and [`Geolocator.java`](./treasure/Geolocator.java). The `TreasureFinder` determines how close the user is to a treasure. However, it currently cannot be tested properly because `Geolocator` does not implement any interfaces and uses the real location of the user. Furthermore, `TreasureFinder` must have a parameterless constructor, because of external requirements. Refactor `TreasureFinder` so that it can be tested with a fake location service, and write tests for it.


Mocks
-----

Now that you've written a few tests using dependency injection, you're probably thinking that writing full implementations of the dependencies for every test is rather annoying. And in a real app, those interfaces would likely have many methods, all of which need to be implemented in every fake dependency class! To solve this, use a _mocking library_.

Mocking libraries help you write fake dependencies, also called _mocks_, by making it easy to create implementations of any dependency interface that return the values you want, instead of having to write an implementation of the interface every time.

We recommend using the _Mockito_ library; take a look at the [Mockito website](https://site.mockito.org/#how) for a quick tutorial.

**Exercise**: Rewrite the tests from the previous exercises to use Mockito instead of ad-hoc interface implementations.


Dependency Injection on Android
-------------------------------

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
