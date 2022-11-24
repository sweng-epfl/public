# Setting up Android Studio

The objective of this preliminary exercise is to introduce and set up some of the tools which are
used in Android development, in preparation for Friday's lecture. If you get stuck, don't hesitate
to ask a question in the [course forum](https://edstem.org/eu/courses/191).

## Setup Android Studio

Android Studio is the official recommended IDE for developers wishing to develop Android apps. It
supports all mainstream operating systems; you can work on Windows, Mac, or Linux.

Download and install Android Studio
from [https://developer.android.com](https://developer.android.com). If you need any help, follow
the [instructions](https://developer.android.com/studio/install.html).

You should choose the default options (you will do more tool configuration later).

After installing, start up Android Studio.
On the welcome screen, please click on **More Actions** and select **SDK Manager**.
Then, in the **SDK Platforms** tab, please install **Android 12L** (API Level 32).
After that, again under **More Actions** > **SDK Manager**,
in the **SDK Tools** tab, first select **Show Package Details** (at the bottom right),
then choose **Android SDK Build Tool 33.0.0** and install it, if it is not installed already.

## Open the SwEng App project

Launch Android Studio, and select **open**. Then, navigate to the folder where the SwEng App project
is located, and select the **SwEng App** folder. After a bit of time, you should see Android Studio
open up with a number of files loaded for this project.

> :information_source: While the default is *Java*, *Kotlin* is now the recommended programming language for Android. On Friday, we will use Java.

First of all, Android projects use a build system called *Gradle*. This is a very common tool in the
Java ecosystem. It allows developers to specify the way a project should be built, which
dependencies it needs, how it should be tested... It's similar to a Makefile in C/C++, only with
more features. In *Gradle*, each project is defined using a `build.gradle` file that the tool reads
and understand.

An Android project actually has two **build.gradle** files. With a command line, it's easy to
distinguish them since one is located at the root of the project while the other one is located in
the `app` folder. With Android Studio, it can be a bit tricky to distinguish these files.
Under `Gradle Scripts` in the file explorer, you will find the following:

- **build.gradle** `(Project: ...)`: this is the top-level gradle file.
- **build.gradle** `(Module: ...)`: this is the app-level gradle file, i.e. **app/build.gradle**.

> :information_source: Android Studio is quite helpful. Did you know that many errors can be resolved by pressing Alt+Enter? That Shift+F6 renames a variable? We recommend starting to get familiar with Android Studio now, because you will use it throughout the next semester.

Android Studio may show a banner at the top of the editor suggesting a gradle sync; you should
accept it. You can also manually sync using the "Sync project with Gradle files" at the top right,
which should look like an elephant with an arrow pointing downwards.

## Create an emulator to run the app

The last thing you need to do before running your new app is to create the emulator on which your
app will run. In Android Studio, use *Tools* > *Device Manager*. A dialog will open, guiding you
through the creation of your virtual device. Click *Create device* to start the process. You can
keep all the default settings. You may need to download a system image before continuing. Under
the *Recommended* tab, download the **Android 12L** (API Level 32) image. Once done, your virtual
device should appear in the *Virtual Device Manager*, which you can now close.

To run your app, you can now use *Run* > *Run 'app'*. It will take a few seconds to start up the
emulator and then you will see Android itself start up, followed by your app.

> :information_source: If you're running the emulator on Linux, you may need to manually configure hardware acceleration. Follow the [official instructions](https://developer.android.com/studio/run/emulator-acceleration#vm-linux) on how to setup KVM. If you run into the error `/dev/kvm device permission denied` then [this StackOverflow post](https://stackoverflow.com/questions/37300811/android-studio-dev-kvm-device-permission-denied/45749003) will help you troubleshoot it.

Congrats, you are now all set up for starting Android development ;)
