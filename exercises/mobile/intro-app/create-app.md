# Exercise 1.1: Creating an Android app

We will kickstart your career as an Android software engineer by creating a friendly greeting application. This application will prompt the user to enter its name, and will then display a friendly welcome message.

## Create the main screen

First, you need to build the screen on which the users will enter their name. 

Edit the **app/res/layout/activity_main.xml** file and add a *Plain Text* text field and a *Button* to your activity by dragging them from the component Palette. Each component has a set of attributes that can be displayed by clicking on it. In the Attributes of the *Plain Text* component, modify the *text* attribute as a hint for the user (e.g., "Name"). You can then style the text field and button as you like. You can also remove the existing *Text View* that Android Studio put there.

One part is important, though: set the `id` attribute of both the text field and the button to a meaningful value, such as `mainName` and `mainGoButton`. You will use this ID to access the components from your source code. To do it, select the component and modify the id attribute in the Attributes tab (usually at the very top of the list). Alternatively, add **android:id="..."** to the XML code of the component.

Launch your app. This will start an emulator and show the awesome activity you just created.

## Create a greeting screen

Now, let's create the screen on which the proper greeting message will be displayed. 

To do so, add a second empty activity called **GreetingActivity** to your app and add a TextView with ID "greetingMessage" to its layout (the layout file is defined at `app/res/layout/activity_greeting.xml`).