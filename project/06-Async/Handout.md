# Android Project - Part 6: Asynchronous Code

In this sixth and last part of the SwEng project, you will rewrite your code to use asynchronicity when it's necessary. You will also learn about Volley, a library that makes it easy to do HTTP requests in Android.

> :information_source: Don't forget to commit and push your code regularly! If you are stuck, feel free to ask for help on Piazza. Don't forget to provide us with a link to your code, this will help us help you :)

> :information_source: If you didn't work on the project until this point, you can use our solution as a starting point. You will however need to do the part 3 on your own if you want to use GitHub and continuous integration. You also **MUST** checkout part 2 on Modularity to create the API keys for OpenWeatherMap.

## New Dependencies

In this part of the project, we will need the [Volley](https://developer.android.com/training/volley) library to make the HTTP requests.

Previously, you wrote the code to download the data from the OpenWeatherMap API directly using the Java I/O APIs. This is very cumbersome, and it is difficult to do this correctly while still taking care of all the particular things that may happen during a transfer. What if the network connection is lost during the transfer? What if the connection with the server times out? What if there is a redirection? 

Using an external library that is specifically designed to tackle that specific problem, as Volley, allows you to offload this particular problem to a piece of code that you can trust and that exposes an API that is simple to use.

To use Volley, you need to add the following dependency in your `app/build.gradle`:

```groovy

dependencies {
    // ...
    implementation 'com.android.volley:volley:1.1.1'
}

```

## Writing asynchronous code in Java (refresher)

As you have seen in the call, when you write code that _waits_ on something (for example: an external API, a filesystem, ...), you don't want to block the caller. This means that instead of writing something like this...

```java
interface MyExternalService {
  MyResult callTheService(ParamType param);
}
```

...you will usually want to write something like this

```java
interface MyExternalService {
  void callTheService(ParamType param, Callback<MyResult> callback);
}
```

or

```java
interface MyExternalService {
  Future<MyResult> callTheService(ParamType param);
}
```

We here use "stub" types to represent common patterns: a `Callback<T>` is just a function from `T` to `void`. A lot of Java libraries (as well as the standard library since Java 8) provide interfaces that do just that (see for example `java.util.function.Consumer<T>`). `Future` is also a generic interface to represent _something that will provide a result later_. The exact implementation depends on the language, and Java provides a construct that does just that (for example, `java.util.concurrent.CompletableFuture<T>`).

Both these pieces of code allow the caller to not block its thread while waiting for a response, which is particularly critical in a UI thread (because you want to be able to react to user input and draw elements on the screen while waiting for the response). The main difference is in the way they are called. 

With the first example, you will usually write this:

```java

// ... some code
MyExternalService service = ...;

service.callTheService(param, result -> {
  // do something with the result
})

```

While in the second case you will write that:

```java

// ... some code
MyExternalService service = ...;

Future<MyResult> response = service.callTheService(param);

response.onComplete(result -> {
  // do something with the result
});

```

When you chain asynchronous calls, the first example can quickly degenerate to so-called _callback hell_, that look like this:

```java

someAsyncFunction(result -> {
  someOtherAsyncFunction(result, result2 -> {
    anotherAsyncFunction(result, result2, result3 -> {
      andYetAnOtherAsyncFunction(result3, result4 -> {
        // and you get it.
      })
    })
  })
})
```

The _Future_ pattern also makes it easier to handle errors. If your service can produce errors that you want to return to the caller (as is very often the case), you need to take two callbacks as an argument, or a single callback with two arguments.

```java
interface MyExternalService {
  void callTheServiceA(ParamType param, CallbackWithError<MyResult, Exception> callback);

  void callTheServiceB(ParamType param, Callback<MyResult> callback, Callback<Exception> errorCallback);
}
```

You can easily see how the second option can become a callback-hell on its own. Error handling with _Futures_ is usually more elegant, as they offer a way for the callee to communicate errors to the caller.

The interface therefore needs not to be modified, and the previous code could be transformed as follows:

```java
MyExternalService service = ...;

Future<MyResult> response = service.callTheService(param);

response
.catchError(error -> {
  // do something with the error, return a dummy value...
})
.onComplete(result -> {
  // do something with the result
});
```

You've seen some form of _Callback_ pattern in the step 5 on MVVM, so we advise you to use the _Future_ pattern in this step. Our solution will be written using that pattern. However, you're ultimately free to use the one you prefer.

## Making the `GeocodingService` asynchronous

In Android, the geocoding feature uses an external API, provided by Google. Recall that geocoding is the ability to convert a real world address to a GPS position, or the reverse operation. Both of those require using an external name service that contains a mapping between GPS positions and actual addresses, and this service is called via HTTPS.

**Your task in this part is to rewrite your `GeocodingService` interface and its implementation to make them asynchronous.**

Beware, simply using a callback or a `Future` doesn't make your code asynchronous. You need to ask the operating system to run your task in a new thread _and then_ report the result using one of these constructs. Please ask a TA if you want to be sure that your code is actually asynchronous.

Java and Android both have multiple ways of doing this. You can have a look at the Java `ExecutorService` class, or at the Android `Task` classes.

Another simple way to dispatch the task is to use [CompletableFuture#supplyAsync](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html#supplyAsync-java.util.function.Supplier-).

## Making the `WeatherService` asynchronous

In this part, you will both rewrite your network code to be asynchronous instead of synchronous and use the Volley library to simplify your network call.

**First, rewrite the `WeatherService` interface to make retrieving the weather at a position an asynchronous operation.**

Now, you need to update your implementation to fit the new definition of the interface. You could indeed wrap the code you already wrote in a task and execute it on a new thread, but you will instead use Volley to replace it.

By now, you should already be used to reading documentation, so we won't provide you with a guided tour of Volley. You know that as a Software Engineer, experimenting with unknown libraries to understand how they work is part of the job. 

**Visit the documentation section for [Volley](https://developer.android.com/training/volley) in the Android Documentation and try to understand how it works.**

**Once you feel confident, you can replace your manual HTTP code with the correct call(s) to Volley.**

> :information_source: Hint: you can `@Inject` the `RequestQueue` using Hilt. Here is a way to provide it in your `WeatherModule`:

```java
    @Provides
    public static RequestQueue provideRequestQueue(@ApplicationContext Context context) {
        return Volley.newRequestQueue(context);
    }
```

Once you've done that, don't forget to remove the `        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());` you inserted in part. 2! In our solution, this line was in the constructor of the `OpenWeatherMapWeatherService`.

## Update the ViewModel

If you did not update your ViewModel yet, you will have trouble compiling your App, as it still expects immediate results from the two interfaces you updated.

**Update your ViewModel to match the asynchronous methods of the interfaces.**

Once you've done that, you can compile and run your App. **You should notice that the Weather UI no longer freezes when you type in the city text-field or click on the button to display the weather.**

Thanks to your new asynchronous architecture, the UI thread (that performs animation and reacts to input) is not paused when you perform a network request. This means that you can, for example, display a loading animation when the weather is loading. **We won't provide you with an explicit correction for that, but we encourage you to have a look and see how you can do it.**

## Update the tests

If you try running your test suite now, it will either not compile or fail. 

**Update your tests to use the asynchronous version of the interfaces.**

The exact way to do this depends on whether you chose the _Completable Future_ or the _Callback_ pattern.

For the _Callback_ pattern, you have an example of how to do it in project 05-MVVM.

For the _Completable Future_ pattern, you can simply use Mockito's `thenReturn` with some specially crafted `CompletableFuture` value.

> :information_source: Note on testing: you will observe that our actual implementation of the WeatherService is never tested. This _could_ be fine if it only contained the pure network code, but it also contains some code that is responsible of parsing the response from the HTTP service. This code could very well be incorrect, and it should therefore be tested. We won't provide you with solutions for this, but we encourage you to think about how you could minimize the untested code in your app. 

## Wrapping up

That's all for the project this semester. We hope you enjoyed working on it. Ours goals when writing this project were:
 - To introduce you to Android development, which will be very useful for SDP next semester
 - To help you experiment with the concepts of the course in a rather "concrete" environment
 - To let you figure out things with minimal guidance, to make you feel more confident about digging in documentation and hacking

We hope that these goals are reached, and we encourage you to expand beyond the scope of this project with some suggestions of extensions, all based on the theme of asynchronicity. We will not provide solutions for them, but we would be more than happy to discuss about these extensions with you during the exercise sessions.

 - **Schedule the app to run code regularly**: Currently, the user has to open the app to get the weather. It would be nice to allow them to schedule a notification once or twice a day with the forecast for the current day or the day after. This will require you to dig into the documentation for **scheduling code in android** and **sending notifications**. Have a look at [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) for a starting point on this one.
 - **Test the services more precisely**: As mentioned at the end of the testing part of this step of the project, a lot of the services implementation is currently left untested. This is very bad, as these components could be faulty or mishandle some corner cases. Try to see how you could test them to achieve higher code coverage and confidence in the correctness of your code.
 - **Assess the performance of your app**: The Android toolchain has a very complete and useful profiler, that allows you to see how well your app performs. You can try to use this profiler to see if your app runs as smoothly as it possibly could. You can also use it to assess the benefits of asynchronous code, by comparing the timings with and without asynchronicity.
 - **Use health APIs**: the latest trend among OS and hardware vendors these days is to offer _health related_ features to the customers. For your simple weather app, you could for example use these APIs to suggest your user to go for a jogging when the weather is clear and when you see that the user hasn't exercised for some time.
