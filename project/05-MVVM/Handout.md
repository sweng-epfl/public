# Android Project - Part 5: the MVVM pattern

In this fifth part of the SwEng project, you will refactor your WeatherActivity to use the MVVM (Model View ViewModel) design pattern.

> :information_source: Don't forget to commit and push your code regularly! If you are stuck and decide to ask for help on Piazza, don't forget to provide a link to your code! This will help us help you. :) 

> :information_source: There will not be a project part next week. 

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

The goal today will be to move most of the business logic of the WeatherActivity to a new `WeatherActivityViewModel`.

In addition, we will also add a way to get continuous locations updates in your `LocationService`, as this provides a nice illustration to the benefits of MVVM.

Finally, we will have to slightly update the `WeatherActivityTest` to reflect our changes.

## 1. Updating `LocationService`

For now, `LocationService` and its implementation only allow to get a snapshot of the current location of the device. Moreover, it requires that the system already has stored such a snapshot of the location - that's why we asked you to launch Google Maps to get the location in the second step of the project.

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
 - A `LiveData` `locationAtSelectedAddress` that contains the `Location` corresponding to the address entered to the user (hint: you can use a `Transformation` on `selectedAddress` - this will make your UI laggy, but we will see a solution to this problem later in the semester) ;
 - A `LiveData` `selectedLocation` that contains the `Location` for which we want to query the weather (hint: you can use a `Transformation.switchMap` on `isUsingGPS`, `currentLocation` and `locationAtSelectedAddress` ; note that it will enable you to express `canQueryWeather` very easily).

Finally, you will also need to expose a few methods for the activity to update the state:
 - A method `setIsUsingGPS` that will be called when the switch is changed ;
 - A method `setSelectedAddress` that will be called when the address text field changes ;
 - A method `refreshWeather` that will be called when the button to refresh the weather is pressed and that will update the `currentWeather` `LiveData`.

> :information_source: You can of course design your `ViewModel` in a different way! Feel free to ask us on Piazza or Discord if you want some advice or feedback on your code.

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

This is in general not great for user experience: in general, you want to offer some gracefully degraded experience to the user if they don't want to share their location. For this project, it doesn't matter much - but feel free to improve this if you want!

## 4. Update the test

As we use a new `LocationService` method that is not yet mocked, the test in `WeatherActivityTest` that checked that getting the weather for the current location of the device worked correctly will not pass.

We therefore need to mock this new method. But since this method takes a `Consumer` as parameter, and doesn't directly return values, we need a way to capture this consumer and call it with our test values.

Mockito provides some tools to do exactly this.

First, you define an `ArgumentCaptor` of the type of argument you want to capture (for example, `ArgumentCaptor<Consumer>`).

Then, instead of calling `Mockito.when`, you use `Mockito.verify` - but instead of providing actual arguments, you will provide placeholders, such as `Mockito.any()` (if you want to accept any argument), or `captor.capture()` if you want to capture an argument using a captor.

Finally, you can use `captor.getValue()` to get the captured value, and call any method on it.

For example, let's imagine a service that returns the current orientation of the device in degrees:

```java

public interface OrientationService {
    void subscribeToOrientationUpdates(Consumer<Integer> callback, int minTimeBetweenUpdatesInSeconds);
}
```

If we want to mock this service, we will write:

```java
    @BindValue
    public OrientationService orientationService = Mockito.mock(OrientationService.class);

    @Test
    public void someTest() throws IOException {
        ArgumentCaptor<Consumer> callbackCaptor = ArgumentCaptor.forClass(Consumer.class);
        Mockito.verify(orientationService).subscribeToOrientationUpdates(callbackCaptor.capture(), Mockito.anyInt());
        callbackCaptor.getValue().accept(180);

        // ... do some other stuff ...
    }
```

And _voilà_! You're done for this part.
