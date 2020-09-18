# Android Project - Part 1: get started!

In the course of the semester you will apply most of the concepts introduced in the course on an Android environment. This will help you prepare to the SDP course next semester, in which you will develop an Android App in small teams. 

In this first part, we will introduce some of the tools you will use.

## Setup Android Studio

Android Studio is the official recommended IDE for developpers wishing to develop Android apps. If you are already an IntelliJ IDEA user, you may keep it as long as you install the Android Plugin - as Android Studio is just a special distribution of IntelliJ. Be aware that some menus or buttons may have slightly different names. You are free to use other tools if you want, but we will not provide support for them.

> :information_source: Android Studio supports all mainstream operating systems; you can work on Windows, Mac, or Linux.

Download and install Android Studio from [https://developer.android.com](https://developer.android.com). If you need any help, follow the [instructions](https://developer.android.com/studio/install.html).

You should choose the default options (you will do more tool configuration later).

Once you reach the welcome screen, please click on **Configure** at the bottom right of the window, and select **SDK Manager**. Then, in the **SDK Platforms** tab, please install **Android 10** (API Level 29).
After that, in the **SDK Tools** tab, first select **Show Package Details** (at the bottom right), then choose **Android SDK Build Tool 30.0.2** and install it, if it is not installed already. 

## Create a new project

Launch the *Start a new Android Studio project* wizard. In the first dialog, select *Empty activity*. In the next dialog, name your project and choose a package name (for instance **com.github.yourusername.bootcamp**. This naming convention is called [Reverse domain name notation](https://en.wikipedia.org/wiki/Reverse_domain_name_notation)).

In *Language* please select **Java**, and leave the *minimum SDK* as it is. After a bit of time, you should see Android Studio open up with a number of files created for this project.

> While the course is taught in *Java*, *Kotlin* is now the recommended programming language for Android. If you want, you can therefore choose to do the project in Kotlin. However, remember that you may encounter issues with which we may not be able to help.

This is a good time to open a file explorer and look at all the files that have been created. The Android developer documentation on [managing projects](https://developer.android.com/studio/projects/index.html) provides a good overview of what all these files do.

First of all, Android projects use a build system called *Gradle*. This is a very common tool in the Java ecosystem. It allows developpers to specify the way a project should be built, which dependencies it needs, how it should be tested... It's similar to a Makefile in C/C++, only with more features. In *Gradle*, each project is defined using a `build.gradle` file that the tool reads and understand.

An Android project actually has two **build.gradle** files. With a command line, it's easy to distinguish them since one is located at the root of the project while the other one is located in the `app` folder. With Android Studio, it can be a bit tricky to distinguish these files. Under `Gradle Scripts` in the file explorer, you will find the following:

- **build.gradle** `(Project: ...)`: this is the top-level gradle file.
- **build.gradle** `(Module: ...)`: this is the app-level gradle file, i.e. **app/build.gradle**.

Open now the **app/build.gradle**. In this file, please check the following settings:
* `compileSdkVersion 29`
* `buildToolsVersion "30.0.2"` (comes right after `compileSdkVersion 29`)
* `minSdkVersion 16`
* `targetSdkVersion 29`
* In the *dependencies*, multiple lines may be yellow, indicating that newer versions exist. You can bump the versions using the `Alt`+`Enter` shortcut, select `Change to x.y.z`. The dependencies should be updated as follows:
  * `implementation 'androidx.appcompat:appcompat:1.2.0'`
  * `implementation 'androidx.constraintlayout:constraintlayout:2.0.1'`
  * `testImplementation 'junit:junit:4.13'`
  * `androidTestImplementation 'androidx.test.ext:junit:1.1.2'`
  * `androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'`

To enable Java 8 features (such as Lambdas), please add the following `compileOptions` block.
```gradle
android {

    ...

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    ...
}
```

> :information_source: Android Studio is quite helpful. Did you know that many errors can be resolved by pressing Alt+Enter? That Shift+F6 renames a variable? We recommend getting familiar with Android Studio now, because you will use it throughout the semester.

And in the top level **build.gradle** file, please update the following setting:

```gradle
classpath "com.android.tools.build:gradle:4.0.1"
```

## Create an emulator to run the App

The last thing you need to do before running your new app is to create the emulator on which your app will run. In Android Studio, use *Tools* > *AVD Manager*. A dialog will open, guiding you through the creation of your virtual device. Click *Create virtual device* to start the process. You can keep all the default settings. You may need to download a system image before continuing. Under the *Recommended* tab, download the **Android 10.0** (API Level 29) image. Once done, your virtual device should appear in the *Virtual Device Manager*, which you can now close.

To run your app, you can now use *Run* > *Run 'app'*. It will take a few seconds to start up the emulator and then you will see Android itself start up, followed by your app. 

> If you're running the emulator on Linux, you may need to manually configure hardware acceleration. Follow the [official instructions](https://developer.android.com/studio/run/emulator-acceleration#vm-linux) on how to setup KVM. If you run into the error `/dev/kvm device permission denied` then [this StackOverflow post](https://stackoverflow.com/questions/37300811/android-studio-dev-kvm-device-permission-denied/45749003) will help you troubleshoot it.

## Create an Android app

We will kickstart your career as an Android software engineer by creating a friendly greeting application. This application will prompt the user to enter its name, and will then display a friendly welcome message.

Below is a list of steps we suggest you take to build this app. In addition, you will find some hints below. Try to follow the steps as well as you can. If you get stuck, there are several options:
* Check the hints below.
* Follow the Android documentation links.
* Google your question or search on [stackoverflow](https://stackoverflow.com/questions/tagged/android)
* Ask a question in the course forum.
* Ask a TA or AE at the lab session.

### Create the main screen

First, you need to build the screen on which the users will enter their name. 

Edit the **app/res/layout/activity_main.xml** file and add a *Plain Text* text field and a *Button* to your activity by dragging them from the component Palette. Each component has a set of attributes that can be displayed by clicking on it. In the Attributes of the *Plain Text* component, modify the *text* attribute as a hint for the user (e.g., "Name"). You can then style the text field and button as you like. You can also remove the existing *Text View* that Android Studio put there.

One part is important, though: set the `id` attribute of both the text field and the button to a meaningful value, such as `mainName` and `mainGoButton`. You will use this ID to access the components from your source code. To do it, select the component and modify the id attribute in the Attributes tab (usually at the very top of the list). Alternatively, add **android:id="..."** to the XML code of the component.

Launch your app. This will start an emulator and show the awesome activity you just created.

### Create a greeting screen

Now, let's create the screen on which the proper greeting message will be displayed. 

To do so, add a second empty activity called **GreetingActivity** to your app and add a TextView with ID "greetingMessage" to its layout (the layout file is defined at `app/res/layout/activity_greeting.xml`).

## Add behavior to your app

Now, you can add the code that starts the GreetingActivity when you click the button and sets the greeting message to "Hello <name>!", where <name> is the text field input. The [Android documentation on starting an activity](https://developer.android.com/training/basics/firstapp/starting-activity.html) contains all the information you need in order to do this. Reading documentation is an important part of being a software engineer.

One hint: the Android documentation talks about editing the XML files directly. You can do this, but you don't need to since you can set all of the Views attributes using the interface designer in Android Studio.

## Clean up

This is a good time to clean up your code. Make sure there are no compiler warnings. Remove unneeded code where necessary. Run *Code > Optimize imports* and *Code > Reformat code* on all your files, as well as *Analyze > Inspect code* to improve the quality of your code.

Repeat this process on a regular basis as your code base changes.

You're done. Congrats! We will publish a solution next week, so don't forget to check that you got everything right!

## About some common warnings

* **Add backup properties** and **Firebase App Indexing**: These would be useful in a real app, but not here. Ignore the warnings.
* **Missing return value** in the Gradle script: This appears to be an Android Studio bug. Ignore the warning.
* **Unused property** in gradle.properties: Ignore the warning.
* **Typos** in words like sweng or epfl: Ignore them. (but fix real typos!)
* **Unused parameter** in the *onClick* handler: Android requires that parameter, even if you're not using it. Suppress the warning (for the method only!)
* **Obsolete stuff** in the tests: Suppress the warnings.

If you encounter some other warning, and believe it to either be a false positive, or something that cannot be fixed without too much effort compared to the app's complexity, please ask the staff by creating a thread on Piazza.


## <a name="FAQ"></a> FAQ

### My Android app shows a blank screen when adding controls
You might find useful [this post](https://stackoverflow.com/questions/51126834/why-cant-i-see-text-in-activity-main-xml-when-i-create-a-new-android-studio-pro).

### When running my app, Android Studio indefinitely installs APK

Here are a few hints:

- The build may be corrupted; try running Build > Clean Project, Rebuild Project.
- Your emulator may be stuck. Try rebooting it, and if it stays frozen, delete the virtual device and recreate it (Tools > AVD Manager).
- If you or your friend has an Android phone, you can check whether the emulator is at fault by connecting your phone to the computer, authorize it and run the app on the phone directly (you may need to enable the phone's developer options).
