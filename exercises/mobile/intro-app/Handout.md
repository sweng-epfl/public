# Exercise 1: getting started with Android development

## Create an Android app

We will kickstart your career as an Android software engineer by creating a friendly greeting application. This application will prompt the user to enter its name, and will then display a friendly welcome message.

Below is a list of steps we suggest you take to build this app. In addition, you will find some hints below. 

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

## About some common warnings

* **Add backup properties** and **Firebase App Indexing**: These would be useful in a real app, but not here. Ignore the warnings.
* **Missing return value** in the Gradle script: This appears to be an Android Studio bug. Ignore the warning.
* **Unused property** in gradle.properties: Ignore the warning.
* **Typos** in words like sweng or epfl: Ignore them. (but fix real typos!)
* **Unused parameter** in the *onClick* handler: Android requires that parameter, even if you're not using it. Suppress the warning (for the method only!)
* **Obsolete stuff** in the tests: Suppress the warnings.

If you encounter some other warning, and believe it to either be a false positive, or something that cannot be fixed without too much effort compared to the app's complexity, please ask the staff.


## <a name="FAQ"></a> FAQ

### My Android app shows a blank screen when adding controls
You might find useful [this post](https://stackoverflow.com/questions/51126834/why-cant-i-see-text-in-activity-main-xml-when-i-create-a-new-android-studio-pro).

### When running my app, Android Studio indefinitely installs APK

Here are a few hints:

- The build may be corrupted; try running Build > Clean Project, Rebuild Project.
- Your emulator may be stuck. Try rebooting it, and if it stays frozen, delete the virtual device and recreate it (Tools > AVD Manager).
- If you or your friend has an Android phone, you can check whether the emulator is at fault by connecting your phone to the computer, authorize it and run the app on the phone directly (you may need to enable the phone's developer options).
