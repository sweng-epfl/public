# Android Intro

This introduction document presents the tools you will use.


## Setup Android Studio

Android Studio is the official recommended IDE for developpers wishing to develop Android apps.
If you are already an IntelliJ IDEA user, you may keep it as long as you install the Android Plugin, as Android Studio is just a special distribution of IntelliJ.
Be aware that some menus or buttons may have slightly different names. You are free to use other tools if you want, but we will not provide support for them.

> :information_source: Android Studio supports all mainstream operating systems; you can work on Windows, Mac, or Linux.

Download and install Android Studio from [https://developer.android.com](https://developer.android.com), or update it if you already have it.
If you need any help, follow the [instructions](https://developer.android.com/studio/install.html).

Choose the default options during installation and during the first-run wizard.
Once you reach the welcome screen, please click on **More Actions** at the middle of the window, and select **SDK Manager**.
Then, in the **SDK Platforms** tab, tick the box for **Android 13.0** (API Level 33) and click the "Apply" button to install it.
After that, in the **SDK Tools** tab, first select **Show Package Details** (at the bottom right),
then tick the box for **Android SDK Build Tool 33.0.2** and click the "Apply" button to install it, if it is not installed already. 


## Create a new project

Click on the "New project" button.
In the first dialog, select *Empty activity*.
In the next dialog, name your project and choose a package name (for instance **com.github.yourusername.bootcamp**.
This naming convention is called [Reverse domain name notation](https://en.wikipedia.org/wiki/Reverse_domain_name_notation)).

In *Language* select **Java**, and in *Minimum SDK* select **API 24: Android 7.0 (Nougat)**, then click "Finish".
After a bit of time, you should see Android Studio open up with a number of files created for this project.

> :information_source: You can always change the minimum SDK later if you need to, but please keep in mind that not all devices have the latest version of Android.

> :information_source: You can pick **Kotlin** as a language if you'd like. Your entire team must agree on which language you will use for the project.

This is a good time to open a file explorer and look at all the files that have been created, since you anyway have to wait until Android Studio has finished indexing files,
which the progress bar at the bottom-right of the window indicates.
The Android developer documentation on [managing projects](https://developer.android.com/studio/projects/index.html) provides a good overview of what all these files do.

First of all, Android projects use *Gradle* a common build system in the Java ecosystem that handles compilation, dependencies, testing, and so on.
In Gradle, each project is defined using a `build.gradle` file that the tool reads and understands.

An Android project actually has two **build.gradle** files.
With a command line, it's easy to distinguish them since one is located at the root of the project while the other one is located in the `app` folder.
With Android Studio, it can be a bit tricky to distinguish these files.
Under `Gradle Scripts` in the file explorer, you will find the following:

- **build.gradle** `(Project: ...)`: this is the top-level gradle file.
- **build.gradle** `(Module: ...)`: this is the app-level gradle file, i.e. **app/build.gradle**.

Open now the **app/build.gradle**. In this file, please modify the following settings if they are not already at these values:
- `compileSdk 33`
- `minSdk 28`
- `targetSdk 33`
- In the *dependencies*, multiple lines may be yellow, indicating that newer versions exist.
  You can bump the versions using the `Alt`+`Enter` shortcut, select `Change to x.y.z`.
  The dependencies should be updated as follows:
  - `implementation 'androidx.appcompat:appcompat:1.6.1'`
  - `implementation 'com.google.android.material:material:1.8.0'`
  - `implementation 'androidx.constraintlayout:constraintlayout:2.1.4'`
  - `testImplementation 'junit:junit:4.13.2'`
  - `androidTestImplementation 'androidx.test.ext:junit:1.1.5'`
  - `androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'`

Plase also make sure that the `compileOptions` block inside the `android` block has both `sourceCompatibility` and `targetCompatibility`
set to `JavaVersion.VERSION_1_8`, i.e., Java 8, so that you can use modern features such as lambdas.

> :information_source: You may have noticed JUnit is set to version 4, not 5 as we used in the Software Engineering course.
> This is because Google does not provide official support for the newer JUnit 5 on Android.
> Such an incompatibility is a common issue in software engineering.
> That being said, if you're feeling adventurous, you could try [this unofficial plugin](https://github.com/mannodermaus/android-junit5) to use JUnit 5 on Android.

> :information_source: If you picked Kotlin, make sure you have `androidx.core:core-ktx` as a dependency, and update it if necessary.

After making changes in a `build.gradle` file, Android Studio will show a blue banner at the top of the editor suggesting to "Sync Now", you should do so.
You can also manually sync using the "Sync project with Gradle files" at the top right, which should look like an elephant with an arrow pointing downwards.


## Create an emulator to run the app

The last thing you need to do before running your new app is to create the emulator on which your app will run.
In Android Studio, use "Tools" > "Device Manager".
A tab will open, guiding you through the creation of your virtual device.
Keep the default phone definition, but make sure you pick **Tiramisu** (API level 33) as system image,
which you will need to download using the little arrow next to the image name before continuing.
Keep the default system configuration.
Once done, your virtual device should appear in the Device Manager, which you can now close.

To run your app, you can now use "Run" > "Run 'app'".
It will take a few seconds to start up the emulator and then you will see Android itself start up, followed by your app. 

> :information_source: If you're running the emulator on Linux, you may need to manually configure hardware acceleration.
> Follow the [official instructions](https://developer.android.com/studio/run/emulator-acceleration#vm-linux) on how to setup KVM.
> If you run into the error `/dev/kvm device permission denied` then [this StackOverflow post](https://stackoverflow.com/questions/37300811/android-studio-dev-kvm-device-permission-denied/45749003) will help you troubleshoot it.


## Fill in your Android app

We will kickstart your career as an Android software engineer by creating a friendly greeting application.
This application will prompt the user to enter their name, and will then display a friendly welcome message.

Below is a list of steps we suggest you take to build this app. In addition, you will find some hints below. Try to follow the steps as well as you can. If you get stuck, there are several options:
- Check the hints below.
- Follow the Android documentation links.
- Look up your question in a search engine, including [StackOverflow](https://stackoverflow.com/questions/tagged/android)
- Ask a question in the course forum.

### Create the main screen

First, you need to build the screen on which the users will enter their name. 

Edit the **app > res > layout > activity_main.xml** file and add a *Plain Text* text field and a *Button* to your activity by dragging them from the component palette.
Each component has a set of attributes that can be displayed by clicking on it. In the Attributes of the *Plain Text* component, modify the *text* attribute as a hint for the user (e.g., "Your Name").
You can then style the text field and button as you like. You can also remove the existing *Text View* that Android Studio put there.

One part is important, though: set the `id` attribute of both the text field and the button to a meaningful value, such as `mainName` and `mainGoButton`.
You will use this ID to access the components from your source code. To do it, select the component and modify the *id* attribute in the Attributes tab (usually at the very top of the list).
Alternatively, add *android:id="..."* to the XML code of the component.

Launch your app to see the awesome activity you just created.
If the components don't quite look aligned properly, add constraints by dragging the circles around each component in the designer.
For instance, if you drag the left circle to the left edge of the screen, and the right circle to the right edge of the screen, your component will be constrained to vertical center of the screen.
You can read [the documentation](https://developer.android.com/develop/ui/views/layout/constraint-layout#add-a-constraint) for more information.

### Create a greeting screen

Now, let's create the screen on which the proper greeting message will be displayed.
add a second empty activity called **GreetingActivity** to your app and add a TextView with ID "greetingMessage" to its layout
(the layout file is defined at  **app > res > layout > activity_greeting.xml**).


## Add behavior to your app

Now, you can add the code that starts the GreetingActivity when you click the button and sets the greeting message to "Hello NAME!", where NAME is the text field input.
To do so, you can look up how to handle button clicks and how to start an activity on Android,
which might lead you to [this documentation page](https://developer.android.com/develop/ui/views/components/button)
and [this StackOverflow question](https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click), for instance.
Reading documentation and looking up information are important parts of being a software engineer.


## Clean up

This is a good time to clean up your code.
Make sure there are no compiler warnings.
Remove unneeded code where necessary.
Run *Code > Optimize imports* and *Code > Reformat code* on all your files, as well as *Code > Inspect code* to improve the quality of your code.
Repeat this process on a regular basis as your code base changes.
It's up to you to decide which warnings are important enough to address and when.

You're done. Congrats!
