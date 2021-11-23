# Exercise 0: setting up Android Studio

The objective of this preliminary exercise is to introduce and set up some of the tools which are used in Android development, in preparation for Friday's lecture. If you get stuck, don't hesitate to ask a question in the [course forum](https://github.com/sweng-epfl/public/discussions).

## Setup Android Studio

Android Studio is the official recommended IDE for developers wishing to develop Android apps. It supports all mainstream operating systems; you can work on Windows, Mac, or Linux.

Download and install Android Studio from [https://developer.android.com](https://developer.android.com). If you need any help, follow the [instructions](https://developer.android.com/studio/install.html).

You should choose the default options (you will do more tool configuration later).

Once you reach the welcome screen, please click on **More Actions** at the top right of the window, and select **SDK Manager**. Then, in the **SDK Platforms** tab, please install **Android 11** (API Level 30).
After that, in the **SDK Tools** tab, first select **Show Package Details** (at the bottom right), then choose **Android SDK Build Tool 30.0.3** and install it, if it is not installed already. 

## Create a new project

Launch the *New Project* wizard. In the first dialog, select *Empty activity*. In the next dialog, name your project and choose a package name (for instance **com.github.yourGithubId.app**. This naming convention is called [Reverse domain name notation](https://en.wikipedia.org/wiki/Reverse_domain_name_notation)).

In *Language* please select **Java**, and in *minimum SDK* please select **API 23: Android 6.0 (Marshmallow)**. After a bit of time, you should see Android Studio open up with a number of files created for this project.

> :information_source: While the default is *Java*, *Kotlin* is now the recommended programming language for Android. On Friday, we will use Java.

First of all, Android projects use a build system called *Gradle*. This is a very common tool in the Java ecosystem. It allows developers to specify the way a project should be built, which dependencies it needs, how it should be tested... It's similar to a Makefile in C/C++, only with more features. In *Gradle*, each project is defined using a `build.gradle` file that the tool reads and understand.

An Android project actually has two **build.gradle** files. With a command line, it's easy to distinguish them since one is located at the root of the project while the other one is located in the `app` folder. With Android Studio, it can be a bit tricky to distinguish these files. Under `Gradle Scripts` in the file explorer, you will find the following:

- **build.gradle** `(Project: ...)`: this is the top-level gradle file.
- **build.gradle** `(Module: ...)`: this is the app-level gradle file, i.e. **app/build.gradle**.

Open now the **app/build.gradle**, by double clicking it in the left menu of Android Studio. In this file, please modify the following settings if they are not already at these values:
* `compileSdkVersion 30`
* `buildToolsVersion "30.0.3"`
* `minSdkVersion 23`
* `targetSdkVersion 30`
* In the *dependencies*, multiple lines may be yellow, indicating that newer versions exist. You can bump the versions using the `Alt`+`Enter` shortcut, select `Change to x.y.z`. The dependencies should be updated as follows:
  * `implementation 'androidx.appcompat:appcompat:1.2.0'`
  * `implementation 'com.google.android.material:material:1.4.0'`
  * `implementation 'androidx.constraintlayout:constraintlayout:2.1.2'`
  * `testImplementation 'junit:junit:4.13.2'`
  * `androidTestImplementation 'androidx.test.ext:junit:1.1.3'`
  * `androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'`

Plase also make sure that the `compileOptions` block inside the `android` block has Java 8 enabled, so that you can use modern features such as lambdas:
```gradle
android {

    ...

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

...
```

> :information_source: Android Studio is quite helpful. Did you know that many errors can be resolved by pressing Alt+Enter? That Shift+F6 renames a variable? We recommend starting to get familiar with Android Studio now, because you will use it throughout the next semester.

And in the top level **build.gradle** file, opened in Android Studio, please update the following setting:

```gradle
classpath "com.android.tools.build:gradle:4.1.3"
```

After making changes in `build.gradle` files, Android Studio will show a banner at the top of the editor suggesting a gradle sync; you should accept it.
You can also manually sync using the "Sync project with Gradle files" at the top right, which should look like an elephant with an arrow pointing downwards.

## Create an emulator to run the app

The last thing you need to do before running your new app is to create the emulator on which your app will run.
In Android Studio, use *Tools* > *AVD Manager*. A dialog will open, guiding you through the creation of your virtual device. Click *Create virtual device* to start the process.
You can keep all the default settings. You may need to download a system image before continuing. Under the *Recommended* tab, download the **Android 11** (API Level 30) image.
Once done, your virtual device should appear in the *Virtual Device Manager*, which you can now close.

To run your app, you can now use *Run* > *Run 'app'*. It will take a few seconds to start up the emulator and then you will see Android itself start up, followed by your app. 

> :information_source: If you're running the emulator on Linux, you may need to manually configure hardware acceleration. Follow the [official instructions](https://developer.android.com/studio/run/emulator-acceleration#vm-linux) on how to setup KVM. If you run into the error `/dev/kvm device permission denied` then [this StackOverflow post](https://stackoverflow.com/questions/37300811/android-studio-dev-kvm-device-permission-denied/45749003) will help you troubleshoot it.

## Register to OpenWeatherMap

Finally, on Friday, you will use an external service, OpenWeatherMap, to add weather forecast capabilities to your app.

[OpenWeatherMap](https://api.openweathermap.org/) offers an API that you can access over HTTPS. As this API offers both free and paid plans, they need to be able to identify the API caller, i.e. the App that is making the HTTPS requests. This is done using a secret key, that you provide in your requests to the service, and that identifies you uniquely.

To get ready for the lecture, we ask you to obtain a secret key to use the service. For that, you will need to register to [OpenWeatherMap](https://api.openweathermap.org/). Please have a look at their [Get Started](https://openweathermap.org/appid) page to do that.

Congrats, you are now all set up for starting Android development ;)