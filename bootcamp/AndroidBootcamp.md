# Android Bootcamp

In the course of the semester, some exercises will introduce you to Android development, to prepare you for next semester and the SDP course, in which you will develop an Android App in small teams. 

As the Android toolchain is quite huge, we will drive you through the differents steps you'll need to be able to use it.

## Setup Android Studio

Android Studio is the official recommended IDE for developpers wishing to develop Android apps. If you are already an IntelliJ IDEA user, you may keep it as long as you install the Android Plugin - as Android Studio is just a special distribution of IntelliJ. Be aware that some menus or buttons may have slightly different names. You are free to use other tools if you want, but we will not provide support for them.

Download and install Android Studio from [https://developer.android.com](https://developer.android.com). If you need any help, follow the [instructions](https://developer.android.com/studio/install.html).

You should choose the default options (you will do more tool configuration later).

Once you reach the welcome screen, please click on **Configure** at the bottom right of the window, and select **SDK Manager**. Then, in the **SDK Platforms** tab, please install **Android 10** (API Level 29).
After that, in the **SDK Tools** tab, first select **Show Package Details** (at the bottom right), then choose **Android SDK Build Tool 29.0.2** and install it, if it is not installed already.

## Create a new project

Launch the Start a new *Android Studio project* wizard. Name your project **Bootcamp** and choose **ch.epfl.sweng** as the package name. Make sure to set the **project path** to your **repository path**. You also need to check the **Use AndroidX artifacts** checkbox. Leave the other settings at their default (i.e. choose API 15: Android 4.0.3 (Ice Cream Sandwich) as minimum SDK and add an Empty Activity). After a bit of time, you should see Android Studio open up with a number of files created for this project.

This is a good time to open a file explorer and look at all the files that have been created. The Android developer documentation on [managing projects](https://developer.android.com/studio/projects/index.html) provides a good overview of what all these files do.

Before running your app, please set the correct version of Android. To do it, you will need to open the **app/build.gradle** file.

Beware that an Android project has actually two **build.gradle** files. With a command line, it's easy to distinguish them since one is located at the root of the project while the other one is located in the `app` folder. With Android Studio, it can be a bit tricky to distinguish these files. Under `Gradle Scripts` in the file explorer, you will find the following:

- **build.gradle** `(Project: ...)`: this is the top-level gradle file.
- **build.gradle** `(Module: ...)`: this is the app-level gradle file, i.e. **app/build.gradle**.

Open now the **app/build.gradle**. In this file, please check the following settings:
* `compileSdkVersion 29`
* `buildToolsVersion "29.0.2"` (comes right after `compileSdkVersion 29`)
* `minSdkVersion 15`
* `targetSdkVersion 29`
* In the *dependencies*, multiple lines may be yellow, indicating that newer versions exist. You can bump the versions using the `Alt`+`Enter` shortcut, select `Change to x.y.z`. The dependencies should be updated as follows:
  * `implementation 'androidx.appcompat:appcompat:1.1.0'`
  * `implementation 'androidx.constraintlayout:constraintlayout:1.1.3'`
  * `androidTestImplementation 'androidx.test:runner:1.2.0'`
  * `androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'`

> :information_source: Android Studio is quite helpful. Did you know that many errors can be resolved by pressing Alt+Enter? That Shift+F6 renames a variable? We recommend getting familiar with Android Studio now, because you will use it throughout the semester.


And in the top level **build.gradle** file, please update the following setting:

```gradle
classpath 'com.android.tools.build:gradle:3.1.4'
```

To run your app, use *Run* > *Run 'app'*. You will see a dialog asking about which emulator you want to use. The default (Nexus 5X API 25 x86) is fine. It will take a few seconds to start up the emulator and then you will see Android itself start up, followed by your app. If this emulator is not installed, please create it by selecting **create a new virtual device**.

Let's save a snapshot of the project, so that you can get back to this stage if you make a mistake.

## Create an Android app

We will kickstart your career as Android software engineer by creating a friendly greeting application.

Below is a list of steps we suggest to build this app. In addition, you will find below some hints. Try to follow the steps as well as you can. If you get stuck, there are several options:
* Check the hints below.
* Follow the Android documentation links.
* Google your question or search on [stackoverflow](https://stackoverflow.com/questions/tagged/android)
* Ask a question in the course forum.
* Ask a TA or AE at the lab session.

## Create the main screen

Edit the **app/res/layout/activity_main.xml** file and add a "Plain Text" text field and a button to your activity by dragging them from the Palette of components and setting the appropriate properties. In the Properties of the component, enter some text in the "Plain Text" text field as a hint, then style the text field and button as you like.

One part is important, though: set the id property of both the field and the button to a meaningful value, such as `mainName` and `mainGoButton`. You will use this ID to access the elements from your source code. To do it, scroll down the Properties and select "View all properties", then you can update the id property of the component by clicking on it.

> :information_source: You can set the ID of a View element by double-clicking on it in the graphical editor. Alternatively, look for a property called id in the list of properties, or add **android:id="..."** to the XML code of the element.

Launch your app. This will start an emulator and show the awesome activity you just created. If it works well, this is a good time for another git commit. Do this before continuing.

## Test your app

Let's make the app do something! In this course, you will learn how to develop high quality software, in other words, make the app do the right thing. One effective way to achieve this is testing, so you will start by writing a test. It goes in a new file (**MainActivityTest.java**) in the **ch.epfl.sweng.swengbootcamp** (androidTest) folder and contains the following code:
```java
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testCanGreetUsers() {
        onView(withId(R.id.mainName)).perform(typeText("from my unit test")).perform(closeSoftKeyboard());
        onView(withId(R.id.mainGoButton)).perform(click());
        // onView(withId(R.id.greetingMessage)).check(matches(withText("Hello from my unit test!")));
    }
}
```

If you want to know more about this test, have a look at the [Android testing guides](https://developer.android.com/training/testing/index.html). This test relies on a library called [Espresso](https://developer.android.com/training/testing/ui-testing/espresso-testing.html). You need to add Espresso as a dependency to the project. To do so:
* Add this line to the *dependencies* section of your **app/build.gradle** (under "Gradle Scripts" in the project explorer):
    ```Gradle
        androidTestImplementation 'androidx.test:rules:1.2.0'
    ```
* Disable animations in your emulator (Inside the emulator, click the round **Apps** button, then choose **Settings**. If you do not see an entry named Developer options, then go to Settings > About emulated device and click on the Build number entry 5 times. A message should normally tell you that you are x steps away from becoming a developer. Then set Settings > Developer options > Window animation scale to Animation off; same for Transition animation scale and Animator duration scale).

Now, you have all the prerequisites to make your test compile. You still need to complete the test file by adding import statements. Android Studio can do this for you: select the places with compile errors, press Alt+Enter, and choose **import ...**.

Once all compile errors are fixed, run your test by right-clicking on the **MainActivityTest** class and selecting Run 'MainActivityTest', then watch it pass. Works? Great! Don't forget to `git add` and `git commit`.

> :information_source: The emulator should boot and launch your app in 60 seconds or less. If not, there is probably a problem with your configuration, and you might want to revisit hardware acceleration settings.


> :warning: If Espresso tests fail with **java.lang.SecurityException**: Injecting to another application requires INJECT_EVENTS permission, the most likely reason is that the soft keyboard is hiding your button. You can disable the soft keyboard (Tools > Android > AVD Manager > Edit > Show Advanced Settings > Enable keyboard input).


## Configure Gradle with JaCoCo plugin

You are now able to test your code, but you could do much better. For instance, you could measure how well your test suite covers your code. We call this measure **code coverage**.

Code coverage is a measure used to describe the degree to which the source code of a program is executed when a particular test suite runs. You will learn more about code coverage during SwEng classes.

For now, you will configure your Android project so that it reports your code coverage. The JaCoCo plugin provides code coverage metrics for Java code. As a result, you need to configure Gradle with JacoCo plugin.

First, you need to add JaCoCo plugin. Add this line to the *dependencies* section of your top-level **build.gradle**:

```gradle
classpath 'org.jacoco:org.jacoco.core:0.8.4'
```

You need then to add a few things in your **app/build.gradle**. We provide you a partial version of the file. Comments emphasize that there is some content. Do not change anything; add only JaCoCo-related lines:

```gradle
apply plugin: 'com.android.application'
apply plugin: 'jacoco'

jacoco {
    toolVersion = "0.8.4"
}

android {
    // ...
    buildTypes {
        debug {
            testCoverageEnabled true
        }
        // ...
    }
}

dependencies {
    // ...
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

    def fileFilter = ['**/R.class', '**/R$*.class', '**/BuildConfig.*', '**/Manifest*.*', '**/*Test*.*', 'android/**/*.*']
    def debugTree = fileTree(dir: "$project.buildDir/intermediates/classes/debug", excludes: fileFilter)
    def mainSrc = "$project.projectDir/src/main/java"

    sourceDirectories = files([mainSrc])
    classDirectories = files([debugTree])
    executionData = fileTree(dir: project.buildDir, includes: [
        'jacoco/testDebugUnitTest.exec', 'outputs/code-coverage/connected/*coverage.ec'
    ])
}
```

You are now able to generate code coverage reports with JaCoCO. These reports provide detailed information about both classes and methods coverage.

Open a command line. `cd` to where your project is. Run the following command:
```sh
./gradlew clean jacocoTestReport
```

You can find the generated report here: `app/build/reports/coverage/debug/index.html`.

That's all for now. We will come back to JaCoCo plugin a bit later. Again, don't forget to `git add` and `git commit`.

## Clean up

This is a good time to clean up your code. Make sure there are no compiler warnings. Remove unneeded code where necessary. Run Code > Optimize imports and Analyze > Inspect code to improve the quality of your code. As final steps, git add, git commit your work, and git push it to your bootcamp repository.

Repeat this process on a regular basis as your code base changes.

## About some common warnings

* **Add backup properties** and **Firebase App Indexing**: These would be useful in a real app, but not here. Ignore the warnings.
* **Missing return value** in the Gradle script: This appears to be an Android Studio bug. Ignore the warning.
* **Unused property** in gradle.properties: Ignore the warning.
* **Typos** in words like sweng or epfl: Ignore them. (but fix real typos!)
* **Unused parameter** in the *onClick* handler: Android requires that parameter, even if you're not using it. Suppress the warning (for the method only!)
* **Obsolete stuff** in the tests: Suppress the warnings.

If you encounter some other warning, and believe it to either be a false positive, or something that cannot be fixed without too much effort compared to the app's complexity, please ask the staff by creating a thread on Piazza.

## Create a greeting activity

Go back to your Android project. Uncomment the last statement in your test, the one that is commented out in the example code that we provided. The test will no longer compile, since you do not yet have a view with the name "greetingMessage".

So, add a second activity called **GreetingActivity** to your app, with a TextView that has ID "greetingMessage" (the layout file is defined at `app/res/layout/activity_greeting.xml`). This will make your test compile again. However, the test fails because your app doesn't do anything when the button is clicked.



## Add behavior to your app

Now, you can add the code that starts the GreetingActivity when you click the button and sets the greeting message to "Hello <name>!", where <name> is whatever the user entered in the text field. The [Android documentation on starting an activity](https://developer.android.com/training/basics/firstapp/starting-activity.html) contains all you need to know to do this. Reading documentation is part of being a software engineer. Get used to it.

One hint: the Android documentation talks about editing the XML files directly. You can do this, but you don't need to since you can set all of these properties for Views using the interface designer in Android Studio.

You're done. Congrats!

## <a name="FAQ"></a> FAQ

### My Android app shows a blank screen when adding controls
You might find useful [this post](https://stackoverflow.com/questions/51126834/why-cant-i-see-text-in-activity-main-xml-when-i-create-a-new-android-studio-pro).

### When running my app, Android Studio indefinitely installs APK

Here are a few hints:

- The build may be corrupted; try running Build > Clean Project, Rebuild Project.
- Your emulator may be stuck. Try rebooting it, and if it stays frozen, delete the virtual device and recreate it (Tools > AVD Manager).
- If you or your friend has an Android phone, you can check whether the emulator is at fault by connecting your phone to the computer, authorize it and run the app on the phone directly (you may need to enable the phone's developer options).

### A .gitignore for Android projects

Android Studio should create the required `.gitignore` when creating a new project. If you don't use it, you may find the following typical `.gitignore` file very useful.

```
# Built application files
*.apk
*.ap_

# Files for the ART/Dalvik VM
*.dex

# Java class files
*.class

# Generated files
bin/
gen/
out/

# Gradle files
.gradle/
build/

# Local configuration file (sdk path, etc)
local.properties

# Proguard folder generated by Eclipse
proguard/

# Log Files
*.log

# Android Studio Navigation editor temp files
.navigation/

# Android Studio captures folder
captures/

# IntelliJ
*.iml
.idea/workspace.xml
.idea/tasks.xml
.idea/gradle.xml
.idea/assetWizardSettings.xml
.idea/dictionaries
.idea/libraries
.idea/caches

# Keystore files
# Uncomment the following line if you do not want to check your keystore files in.
#*.jks

# External native build folder generated in Android Studio 2.2 and later
.externalNativeBuild

# Google Services (e.g. APIs or Firebase)
google-services.json
```

