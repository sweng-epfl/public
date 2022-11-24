# _Model View ViewModel_ on Android

<p align="center">
<img src="https://user-images.githubusercontent.com/6318990/203812544-9593cdbd-d5cb-47b7-8acf-bf6ac3b1b8d4.jpg" width="200">
</p>

In this exercise, you will learn how to implement the MVVM pattern on Android. To do so, you will be
building a simple Hacker News client, which displays the top stories from the
official [Hacker News API](https://github.com/HackerNews/API).

## Step 1: Getting Started

The project is already set up for you. Here are its main packages:

* `ch.epfl.sweng.domain` will contain the model of the application. It features the `Story` class,
  which represents a story from Hacker News. Moreover, its `.api` subpackage will contain the
  classes that will be used to fetch the stories from the API
  using [Retrofit](https://square.github.io/retrofit/), a library that makes it easy to fetch data
  from REST APIs.
* `ch.epfl.sweng.presentation` (currently empty) is the view-model package. It will contain
  the `StoriesViewModel` class, which is the view-model of the application and will responsible for
  fetching the stories from the Hacker News API and exposing them to the view.
* `ch.epfl.sweng.view` contains the view of the application. It features the `MainActivity` class,
  which is the main activity of the application, as well as the `StoriesAdapter` class, which you
  previously implemented and which is responsible for displaying the stories in a list.

## Step 2: MVVM building blocks in Android

In Android, building blocks to implement the MVVM pattern are found in the `androidx.lifecycle`
packages. They are automatically provided by the `androidx.appcompat:appcompat` dependency, so you
don't have to add them to your `build.gradle`. In case you need it, you can see the list
of `lifecycle` packages here: https://developer.android.com/jetpack/androidx/releases/lifecycle

In `androidx.lifecycle`, a ViewModel is represented by a class that extends `ViewModel`.
A `ViewModel` allows you to "separate out view data ownership from UI controller logic". You can see
a detailed documentation here: https://developer.android.com/topic/libraries/architecture/viewmodel.

A typical Android `ViewModel` contains multiple `LiveData` fields. A `LiveData` is a perfect example
of the Observer pattern: it enables activities to subscribe to updates of some data.

There are two main ways to create a `LiveData` instance:

- Use `MutableLiveData`: as the name implies, a `MutableLiveData` directly exposes an underlying
  value, that you can update using `postValue`.
- Use `Transformations` to transform the value of another `LiveData`.

Here is a small example of what these can do:

```java
class Example extends Activity {
    @Override
    protected void onCreate() {
        var age = new MutableLiveData<>(10);
        var isMinor = Transformations.map(age, age -> age < 18);

        isMinor.observeForever(isMinor -> Log.i("Example", "Is minor? " + isMinor));
        // The current value is immediately delivered to the observer, so a log line will show printing "Is minor? true"

        age.postValue(21);
        // The update is propagated to all observers, including transformations
        // A new log line will show, printing "Is minor? false"
    }
}
```

It's also possible to define custom `LiveData` transformations. For example, you could define a
`combineLatest` method that combines the latest values of a list of `LiveData` instances into a
single `LiveData` instance, using a special subclass of `LiveData` called `MediatorLiveData`:

```java
public final class LiveDataTransformations {

    public static <T> LiveData<List<T>> combineLatest(List<LiveData<T>> list) {
        var result = new MediatorLiveData<List<T>>();
        var initial = new ArrayList<T>(list.size());
        result.postValue(initial);
        for (int i = 0; i < list.size(); i++) {
            final int index = i;
            initial.add(null);
            result.addSource(
                    list.get(i),
                    value -> {
                        var current = requireNonNull(result.getValue());
                        var updated = new ArrayList<>(current);
                        updated.set(index, value);
                        result.postValue(updated);
                    });
        }
        return result;
    }
}

```

`MediatorLiveData` is a special `LiveData` that allows you to add sources to it. When a source is
added, the `MediatorLiveData` will observe it and propagate its value to its own observers. When a
source is removed, the `MediatorLiveData` will stop observing it.

In Android, to invoke a `ViewModel` in an activity, you will not instantiate it yourself. You will
instead use a `ViewModelProvider`. This enables the operating system to maintain a
single `ViewModel` instance across activity restarts, for example.

If you have a ViewModel called `AgeViewModel` and you want to use it, you need to add something like
this in your activity `onCreate`:

```java
this.viewModel=new ViewModelProvider(this).get(AgeViewModel.class);
```

By default, this requires you to have an empty constructor in `AgeViewModel`. Since a `ViewModel`
constructor usually requires some parameters (for example, the services it will call to fetch data
on behalf of the view), you may need to write an implementation of `ViewModelProvider` that takes
care of providing the arguments.

## Step 3: Making network calls using Retrofit

In this step, you will learn how to use [Retrofit](https://square.github.io/retrofit/) to make
network calls. [Retrofit](https://square.github.io/retrofit/) is a library that makes it easy to
make network calls in Android.

In Retrofit, you define an interface that describes the API you want to call. For example, if you
want to call the Hacker News API, you can define an interface like this:

```java
interface HackerNewsApi {
    @GET("topstories.json")
    Call<List<Long>> getTopStories();
}
```

This interface defines a method `getTopStories` that returns a `Call` to a list of story IDs. A
`Call` is an object that represents a network call. You can use it to execute the call, and to
register a callback that will be called when the call is finished.

In this exercise, you will use Retrofit to make network calls to the Hacker News API. To do so, you
will need to create a `Retrofit` instance. You can do so by adding the following code in your
project:

```java
public interface HackerNewsApiFactory {
    HackerNewsApi create();
}

public final class RetrofitHackerNewsApiFactory implements HackerNewsApiFactory {
    private final Retrofit retrofit =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    // only if you want to use LiveData
                    // .addCallAdapterFactory(new LiveDataAdapterFactory()) 
                    .baseUrl(HackerNewsApi.BASE_URL)
                    .build();

    @Override
    public HackerNewsApi create() {
        return retrofit.create(HackerNewsApi.class);
    }
}
```

This code creates a `Retrofit` instance that is configured to use the `GsonConverterFactory` to
convert the JSON responses to Java objects. It also uses the `LiveDataAdapterFactory` class to
convert the `Call` objects to `LiveData` objects. By default, Retrofit returns `Call` objects, which
are not compatible with the `LiveData` class. The `LiveDataAdapterFactory` is a custom class that
converts `Call` objects to `LiveData` objects, and that you can find in
the `ch.epfl.sweng.domain.api.retrofit` package if you want to use it.

## Step 4: Putting it all together

You will now implement the main feature of the application: displaying the list of stories. To do
so, you will need to:

1. Create a `StoriesViewModel` which will bind data to the `StoriesFragment` view.
   Your `MainActivity` should be notified whenever the list of stories changes, and it should
   display the list of stories in a `RecyclerView`. To update the contents of the `RecyclerView`,
   you will have to call the `submitList` method of the `RecyclerView` adapter. Use `LiveData`'
   s `observe` method of the `LiveData` class to bind the data to the view.
2. Define a Retrofit interface for `HackerNewsApi`. This requires looking at the
   [official documentation](https://github.com/HackerNews/API), and adding an appropriate `@GET`
   endpoints that fetches Hacker News items from the API using its ID. You will need to create a new
   data class to represent the Hacker News item.
3. Once you have implemented the `HackerNewsApi`, you can implement `StoriesViewModel`. You will
   need to fetch the top stories from the API, and then fetch the corresponding items from the API.
   You will then need to map the hacker news items objects to `Story` objects, and expose them to
   the view using a `LiveData` object. You will need to transform the `List<Long>` returned by the
   API into a `List<Story>`. You may use the static `map` or `switchMap` methods of
   the `Transformations` class to do so.
