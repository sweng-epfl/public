# Using Web APIs

When working on an project, you might find it interesting to use dynamic data from the web. For example, you might want to display the weather forecast, the latest news, or a stream of bits for an image. This is where web APIs come in.

Let's do a rundown of the most common things to keep in mind when working with web APIs!

## APIs are unreliable

APIs and external data are not under your control. They take time to load, they might return unexpected results, or they might simply be down. It is important to keep this in mind when using them. 

### Step 1: Carefully check a site's API documentation.
You should read the documentation of the API you are using, and make sure you understand how it works. Both the **request** format and the **response** format are important.

> [!NOTE]
> [JSON](https://www.json.org/json-en.html) is a common format for APIs. It is a way to represent data in a human-readable format. It is similar to a dictionary in Python, or a map in Java. For example, here is how a server could respond to the following JSON-encoded request:
>
> ```json
> // Client request
> {
>     "id": 3110,
> }
>
> // Server response
> {
>     "id": 3110,
>     "username": "Truddy",
>     "last_login": "1677016003",
>     "is_admin": false,
>     "avatar": {
>         "url": "https://example.com/avatar.png",
>         "width": 128,
>         "height": 128
>     }
> }

Sometimes, an API will require you to register an account, and provide an API key. This is usually done to prevent abuse of the API. You might need to specify this key in the request headers if you build requests manually (for example, requests to the [OpenWeatherMap API](https://openweathermap.org/api) require an API key).


### Step 2: When requesting, check response codes.
When you make a request to an API, you will get a [response code](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status). This indicates basic information about the status of the response from the server. For instance, recieving a `200 OK` response code means that the request was successful. Any other response code may indicate an error.

It is a good idea to check this response code, and handle it appropriately. For example, if you get a `404 Not Found` response code, you might want to perform a specific action.

### Step 3: Handle fatal errors.
Worse, if the API is down, or if the user is offline, you should handle this gracefully. For example, you could display a message to the user, or use a cached version of the data.


### Step 4: Caching/Offline mode
Whether you repeatedly ask for large amounts of data, or just accounting for network issues, it is a good practice to think in advance and to some cache data locally.

A simple way to cache basic data is to use [SharedPreferences](https://developer.android.com/reference/android/content/SharedPreferences). Shared Preferences is a commonly used mechanism for storing small amounts of data in Android. It's a key-value store that allows you to save primitive data types (such as strings, booleans, and integers) and retrieve them later.

For storing data in a database like structure, you can use [Room](https://developer.android.com/training/data-storage/room), or refer to this [exercise](https://github.com/sweng-epfl/public/tree/main/lectures/Mobile/exercises/Q5) from the Software Engineering course.

Finally, for more types of data, you should check out [the Android data storage guide](https://developer.android.com/training/data-storage).

### Step 5: Never block the UI thread.
You shouldn't be running I/O tasks on the main thread, as that thread is also responsible for UI updates. If the main thread is blocked, the user will see a frozen app, and might get impatient and close it.

Take a look at [Kotlin coroutines](https://developer.android.com/kotlin/coroutines) (or equivalent asynchronous Java functions, such as the fancy [Futures](https://developer.android.com/reference/java/util/concurrent/Future) as seen in [the Asynchrony lecture](https://github.com/sweng-epfl/public/tree/main/lectures/Asynchrony)). 

### Step 6: Use a library.
There are many libraries that can help you with API requests. For example, [Retrofit](https://square.github.io/retrofit/) is a popular library for making HTTP requests. It is built on top of [OkHttp](https://square.github.io/okhttp/), which is a popular HTTP client. They allow you to make requests in a simple and concise way.


## Let's try it out!
Let's try to make a simple activity that fetches a random challenge from the [BoredAPI](https://www.boredapi.com): when heading over to https://www.boredapi.com/api/activity, this API returns a random activity that you can do when you're bored.
We'll use [Retrofit](https://square.github.io/retrofit/) to make the request, in Kotlin.

### Step 1: Basics with Retrofit

Create a new project in Android Studio. You can use the Empty Activity template.

Before we code, don't forget to add the following dependencies to your `build.gradle` file (in the `app` module):
    
```groovy
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.9.0'

implementation 'com.google.code.gson:gson:2.8.7'
```

Create a new Kotlin class called `BoredActivity`. This class will represent the data we get from the API.

```kotlin
data class BoredActivity(
    // Check the response format of the response! See the documentation
    val activity: String,
    // TODO: ...
)
```

In Retrofit, you define an interface that describes the API you want to call. To call the BoredAPI API, you can define an interface like this:

```kotlin
interface BoredApi {
    @GET("activity")
    fun getActivity(): Call<BoredActivity>
}
```

By using the `@GET` we specify the type of request. The `activity` parameter is the endpoint of the API. The `getActivity()` method returns a `Call` object, which is a Retrofit class that represents an HTTP request. This `Call` object will be used to make the request.

Now, we need to create an instance of the `BoredApi` interface. We can do this by using the `Retrofit.Builder` class.

```kotlin
val retrofit = Retrofit.Builder()
    .baseUrl("https://www.boredapi.com/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
```

The `baseUrl` parameter is the base URL of the API. The `addConverterFactory` method specifies how the response will be converted to an object. In this case, we use `GsonConverterFactory` (the G instead of J is intentional, it is named after Gson, a library that handles JSON). This will convert the response to a `BoredActivity` object.

> [!TIP]
> You don't need to store all value fields in the response from the server into the data class `BoredActivity`, you can keep a subset of those that you want. Retrofit won't complain about missing properties since it only maps what we need, and it won't even complain if we were to add properties that are not in the JSON response.

Now, we can create an instance of the `BoredApi` interface:

```kotlin
val boredApi = retrofit.create(BoredApi::class.java)
```

Let's make the request. We can do this by calling the `getActivity()` method on the `boredApi` object. This method returns a `Call` object, which we can use to make the request.

```kotlin
boredApi.getActivity().enqueue(object : Callback<BoredActivity> {
    override fun onResponse(call: Call<BoredActivity>, response: Response<BoredActivity>) {
        // TODO: Handle the response
    }

    override fun onFailure(call: Call<BoredActivity>, t: Throwable) {
        // TODO: Handle the error
    }
})
```

### Step 2: Complete the app!

With the stubs we provided, you are now be able to make a simple app that fetches a random activity from the BoredAPI.

- Add a button that will make the request to the API when clicked and fetch the data.
- Add a text view that will display this activity. Simpy use the `activity` field of the `BoredActivity` object, or you can get creative and display the other fields as well, add new textviews for other information, etc.

<details>
<summary>Is this code blocking the UI thread? (think about it, then click to see an answer)</summary>
No, because we are using the `enqueue` method, which will make the request in the background. If we were using the `execute` method, the request would be made on the main thread, and the UI would freeze.
</details>

### Step 3: Going further, going offline... Caching!
Now imagine you lose connection to the Internet (you can simulate this by turning off your Wi-Fi and data connections). What happens? 

Let's expand our app to handle this case. We could do this by using [Room](https://developer.android.com/training/data-storage/room), or with the quick and dirty SharedPreferences, but it is up to you.

For this part:
- For each time you make a request to the API, you should store the response in the local database.
- When you lose connection to the internet (failing request), load the data from the local database, select a random activity, and display it to the user. Don't forget to tell the user that you're using cached data!

Test your app by clicking the button a couple of times, then disabling your Internet connection, and keep clicking the button.

### Step 4: It's testing time!

When testing, you will connect to a fake server, and you will test that your app behaves correctly when the server returns a response, and when it returns an error.

A mock web server imitates a real server without making internet requests. It allows testing of APIs without hitting rate limits or worrying about too much delay, API unavailablity or network issues. It intercepts HTTP requests, processes them offline, and returns specified data.

Now, try using [MockWebServer](https://www.kodeco.com/33855511-testing-rest-apis-using-mockwebserver) to test your app! It was developed by the same people who made Retrofit.

In your viewmodel, it is a good workaround to inject the `BoredApi` object, so that you can easily replace it with a mock object when testing. Take a look at [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) to see how to automatically inject the right object (testing vs production) in your viewmodel.
