

# Exercise 3: MVVM refactoring

In this third exercise, you will refactor your WeatherActivity to use the MVVM (Model View ViewModel) design pattern.

## Meet the Hilt framework

Before getting into the MVVM refactoring, we will get acquainted with a framework which will simplify our work: Hilt.

[Hilt](https://dagger.dev/hilt/) is a Dependency injection library for Android that reduces the boilerplate of using manual DI in your project. Doing manual dependency injection requires constructing every class and its dependencies by hand and using containers to reuse and manage dependencies. Moreover, the way Android is designed prevents developers to fully embrace DI in a manual way as the system controls the instantiation and destruction of application components (such as Activities).

In this section, you will use Hilt to provide the services `WeatherActivity` needs (i.e. its dependencies `LocationService`, `WeatherService` and `GeocodingService`) without creating them in the `onCreate` method.

> :warning: This part can seem hard. However, Hilt hides as much complexity as possible, to the point that all you need to know to complete it can be learnt by reading the [relevant Android documentation](https://developer.android.com/training/dependency-injection/hilt-android).

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

## New dependencies

In Android, building blocks to implement the MVVM pattern are found in the `androidx.lifecycle` packages. They are automatically provided by the `androidx.appcompat:appcompat` dependency, so you don't have to add them to your `build.gradle`. In case you need it, you can see the list of `lifecycle` packages here: https://developer.android.com/jetpack/androidx/releases/lifecycle

Because your project uses Hilt, you will need to add two new dependencies so that the `lifecycle` building blocks can work with it. Add them in your app `build.gradle`.

```groovy

dependencies {
    // ...
    annotationProcessor 'androidx.hilt:hilt-compiler:1.0.0-alpha02'
    implementation 'androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02'
}

```


## MVVM building blocks in Android

In `androidx.lifecycle`, a ViewModel is represented by a class that extends `ViewModel`. A `ViewModel` allows you to "separate out view data ownership from UI controller logic". You can see a detailed documentation here: https://developer.android.com/topic/libraries/architecture/viewmodel.

A typical Android `ViewModel` contains multiple `LiveData` fields. A `LiveData` is a perfect example of the Observer pattern: it enables activities to subscribe to updates of some data. 

There are two main ways to create a `LiveData` instance:
 - Use `MutableLiveData`: as the name implies, a `MutableLiveData` directly exposes an underlying value, that you can update using `postValue`. 
 - Use `Transformations` to transform the value of another `LiveData`. 

Here is a small example of what these can do:

```java

MutableLiveData<Integer> age = new MutableLiveData<>(10); 
LiveData<Boolean> isMinor = Transformations.map(age, age -> age < 18);

isMinor.observeForever(isMinor -> Log.i("Example", "Is minor? " + isMinor));
// The current value is immediately delivered to the observer, so a log line will show printing "Is minor? true"

age.postValue(21);

// The update is propagated to all observers, including transformations
// A new log line will show, printing "Is minor? false"
```

In Android, to invoke a `ViewModel` in an activity, you will not instantiate it yourself. You will instead use a `ViewModelProvider`. This enables the operating system to maintain a single `ViewModel` instance across activity restarts, for example. 

If you have a ViewModel called `AgeViewModel` and you want to use it, you need to add something like this in your activity `onCreate`:

```java
this.viewModel = new ViewModelProvider(this).get(AgeViewModel.class);
```

By default, this requires you to have an empty constructor in `AgeViewModel`. Since a `ViewModel` constructor usually requires some parameters (for example, the services it will call to fetch data on behalf of the view), you need to write an implementation of `ViewModelProvider` that takes care of providing the arguments.

As this is a lot of boilerplate, **Hilt takes care of it for us**. We simply need to annotate the constructor in the ViewModel with `@ViewModelInject`, and Hilt will inject all the required parameters when instanciating the ViewModel.

## Tasks

The goal will be to move most of the business logic of the WeatherActivity to a new `WeatherActivityViewModel`.

In addition, we will also add a way to get continuous locations updates in your `LocationService`, as this provides a nice illustration to the benefits of MVVM.

Finally, we will have to slightly update the `WeatherActivityTest` to reflect our changes.

## 1. Updating `LocationService`

For now, `LocationService` and its implementation only allow to get a snapshot of the current location of the device. Moreover, it requires that the system already has stored such a snapshot of the location - that's why we asked you to launch Google Maps to get the location in the second exercise.

You will now add a way to get continuous updates in this service.

First, add a method in your `LocationService` interface to return continuous updates. It will take as parameter a `Consumer<Location>`, that will be called every time a new location is available.

Then, you'll need to implement the new function in your `AndroidLocationService`. You should use `LocationManager.requestLocationUpdates(...)` for this purpose. You will have to create an instance of `LocationListener`. This interface defines a method `onLocationChanged` that is called every time the location changes, with the new location as parameter. 

## 2. Writing the `WeatherActivityViewModel`

Currently, your `WeatherActivity` handles the following:
 - Let the user input an address
 - Let the user choose to use their device location instead of the address (using a switch or checkbox)
 - Let the user refresh the weather at the chosen location using a button

We would also like the address input field to be disabled if the user decided to use their location, and the weather button to be disabled if we don't have any location (either because the address is incorrect or because we can't find the current location of the device).

To enable these features, you will now create a `ViewModel` that will expose multiple values through `LiveData` objects.

You will need to expose:
 - A `LiveData` `currentWeather` that contains the current weather ;
 - A `LiveData` `canQueryWeather` that contains a boolean, set to `true` if the `ViewModel` currently has a suitable location, and `false` if not ;
 - A `LiveData` `isUsingGPS` that contains a boolean, set to `true` if the user wants to use their device position, or `false` if they want to enter an address manually.

We suggest you to create additional `LiveData` objects:
 - A `LiveData` `currentLocation` that contains the current device position (using the method you defined in #1) ;
 - A `LiveData` `selectedAddress` that contains the `String` address entered by the user ;
 - A `LiveData` `locationAtSelectedAddress` that contains the `Location` corresponding to the address entered to the user (hint: you can use a `Transformation` on `selectedAddress` - this will make your UI laggy, but we will see a solution to this problem later in the semester)
 - A `LiveData` `selectedLocation` that contains the `Location` for which we want to query the weather (hint: you can use a `Transformation.switchMap` on `isUsingGPS`, `currentLocation` and `locationAtSelectedAddress` ; note that it will enable you to express `canQueryWeather` very easily).

Finally, you will also need to expose a few methods for the activity to update the state:
 - A method `setIsUsingGPS` that will be called when the switch is changed ;
 - A method `setSelectedAddress` that will be called when the address text field changes ;
 - A method `refreshWeather` that will be called when the button to refresh the weather is pressed and that will update the `currentWeather` `LiveData`.

> :information_source: You can of course design your `ViewModel` in a different way! Feel free to ask us on the forum if you want some advice or feedback on your code.

## 3. Refactoring the WeatherActivity

Now you will need to replace the business logic you wrote in the `WeatherActivity` by the correct calls to your `ViewModel`.

First, don't forget to instanciate the `ViewModel`, as described above.

Then, you will want to "rewire" your buttons and fields to the appropriate methods in the `ViewModel`. In particular, you want to use:
 - `Switch.setOnCheckedChangeListener(...)` on your GPS switch ;
 - `TextView.addTextChangedListener(...)` on your city text field ;
 - `Button.setOnClickListener(...)` on your retrieve weather button.

These fields should not do much on their own, and they should only call the correct methods in the `ViewModel`. 

Now, you will need to make sure that changes in the `ViewModel` are indeed reflected in the activity.

For that purpose, simply get the `LiveData` objects from your `ViewModel` and call `observe(...)` on them. The second argument of `observe` is a function that will be called every time a new value is available in the `LiveData`. You should use this value to update your view.

Finally, you need to make sure that you request location permissions early in the creation of the activity. Depending on the way you implemented your `ViewModel`, it may request location updates as soon as it is created - which means you should not instanciate the `ViewModel` before getting the permission.

This is in general not great for user experience: in general, you want to offer some gracefully degraded experience to the user if they don't want to share their location. For this exercise, it doesn't matter much - but feel free to improve this if you want!