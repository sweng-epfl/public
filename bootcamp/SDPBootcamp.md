# Software Development Project - Bootcamp

You have registered for the Software Development Project (CS-306) course. During the semester, you will work in teams and build a brand new Android app from scratch. This will be a great opportunity for you to put into practive several tools and good practices that you learned about during the Software Engineering (CS-305) course, and also to get a sense of what a real world project looks like.

In this Bootcamp, you will create a new working Android app from scratch.

## 1. Get the right tools

Before starting, you'll need to setup a few tools. Make sure to read carefully all instructions. At the end of this section, you will have a working IDE and Git client, as well as an account on GitHub, TravisCI and CodeClimate. You will also have initialized a new GitHub project, and added it to both Travis and CodeClimate.

### IDE: Android Studio

> :information_source: Android Studio is the official recommended IDE for Android developpers.

Download and install Android Studio from [https://developer.android.com](https://developer.android.com). If you need any help, follow the [instructions](https://developer.android.com/studio/install.html).

You should choose the default options (you will do more tool configuration later).

Once you reach the welcome screen, click on **Configure** at the bottom right of the window, and select **SDK Manager**. Then, in the **SDK Platforms** tab, install **Android 10** (API Level 29).
After that, in the **SDK Tools** tab, first select **Show Package Details** (at the bottom right), then choose **Android SDK Build Tool 29.0.3** and install it, if it is not installed already.

You now have installed Android Studio. We will later create a new project.

### Git and GitHub

If you do not know what Git is, or did not already install it, please look at the [Software Engineering bootcamp](../Readme.md) for a refresher.

Create a new repository on GitHub for the bootcamp. Make sure that your repository is **public** in order for the tools introduced later to work correctly. You can name it `sdp-bootcamp`, for example.

For now, leave the repository empty - we will push some stuff later on.

### Travis CI

You will use [travis-ci.org](https://travis-ci.org/) as the continuous integration tool of your project. If you do not know what it is, or did not already use it, please look at the [Software Engineering bootcamp](../Readme.md) for a refresher. 

Once on your Travis dashboard, use the "+" button to add your newly created bootcamp project. You will then get to a build dashboard for your repository.

### CodeClimate account

> :information_source: Code Climate Quality is an automated code review tool, that ensures your projects complies with a predefined set of rules and best practices.

You will use [Code Climate Quality](https://codeclimate.com/quality/) to ensure that your code follows the best practices of Software Engineering.

If you never used Code Climate Quality before, you'll first need to create an account using your GitHub account. Go to [Code Climate Quality](https://codeclimate.com/quality/), and click "Get Started". Authorize Code Climate to access your GitHub account. Click next. In the `Free for open source` panel, add a repository. Again, give access authorization to Code Climate. Add your bootcamp repository.

You should now be ready to start development.

## 2. Create your new app

We will now begin the creation of a friendly greeting application.

Below is a list of steps we suggest to build this app. In addition, you will find below some hints. Try to follow the steps as well as you can. If you get stuck, there are several options:
* Check the hints below.
* Follow the Android documentation links.
* Google your question or search on [StackOverflow](https://stackoverflow.com/questions/tagged/android)
* Ask a question in the course forum.
* Ask a staff member at the lab session.

### a. Create and run the app

In Android Studio, launch the Start a new *Android Studio project* wizard. Name your project **Bootcamp** and choose **ch.epfl.sdp** as the package name. Make sure to set the **project path** to your **repository path**. You also need to check the **Use AndroidX artifacts** checkbox. Leave the other settings at their default (i.e. choose API 21: Android 5.0 (Lollipop) as minimum SDK, Language: Java and add an Empty Activity). After a bit of time, you should see Android Studio open up with a number of files created for this project.

This is a good time to open a file explorer and look at all the files that have been created. The Android developer documentation on [managing projects](https://developer.android.com/studio/projects/index.html) provides a good overview of what all these files do.

Before running your app, please set the correct version of Android. To do so, you will need to open the **app/build.gradle** file.

Beware that an Android project actually has two **build.gradle** files. With a command line, it's easy to distinguish them since one is located at the root of the project while the other one is located in the `app` folder. With Android Studio, it can be a bit tricky to distinguish these files. Under `Gradle Scripts` in the file explorer, you will find the following:

- **build.gradle** `(Project: ...)`: this is the top-level gradle file.
- **build.gradle** `(Module: ...)`: this is the app-level gradle file, i.e. **app/build.gradle**.

Open now the **app/build.gradle**. In this file, please check the following settings:
* `compileSdkVersion 29`
* `buildToolsVersion "29.0.3"` (comes right after `compileSdkVersion 29`)
* `minSdkVersion 21`
* `targetSdkVersion 29`
* In the *dependencies*, multiple lines may be yellow, indicating that newer versions exist. You can bump the versions using the `Alt`+`Enter` shortcut, select `Change to x.y.z`. The dependencies should be updated as follows (or newer):

```
implementation 'androidx.appcompat:appcompat:1.1.0'
implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
testImplementation 'junit:junit:4.12'
androidTestImplementation 'androidx.test.ext:junit:1.1.1'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
```

> :information_source: Android Studio is quite helpful and offers many shortcuts: Alt+Enter resolves a lot of errors, Shift+F6 renames a variable, Alt+Insert creates constructors, getters, setters, ... We recommend getting familiar with Android Studio now, because you will use it throughout the semester.

In the top level **build.gradle** file, please update the following setting:

```gradle
classpath 'com.android.tools.build:gradle:3.5.3'
```

To run your app, use *Run* > *Run 'app'*. You will see a dialog asking about which emulator you want to use. The default (Nexus 5X API 25 x86) is fine. It will take a few seconds to start up the emulator and then you will see Android itself start up, followed by your app. If this emulator is not installed, please create it by selecting **create a new virtual device**.

> :information_source: The emulator should boot and launch your app in 60 seconds or less. If not, there is probably a problem with your configuration, and you might want to revisit hardware acceleration settings.

Once everything works, you should save everything to a brand new **Git Repository**. Init a repository at the root of your project. Add all the files Android Studio just created and commit. Add your previously created GitHub Project as the remote for your repository, and push.

> :information_source: Android Studio should have created the required `.gitignore` when creating a new project. If you don't use it, you may find the [following typical `.gitignore` file](https://github.com/github/gitignore/blob/master/Android.gitignore) very useful. 

### b. Change the main screen

Open the **app/res/layout/activity_main.xml** file and add a "Plain Text" text field and a button to your activity by dragging them from the Palette of components and setting the appropriate properties. In the Properties of the component, enter some text in the "Plain Text" text field as a hint, then style the text field and button as you like.

One part is important, though: set the `id` property of both the field and the button to a meaningful value, such as `mainName` and `mainGoButton`. This ID is needed to access the elements from the source code of the app. To do it, scroll down the Properties and select "View all properties". You can update the id property of the component by clicking on it.

> :information_source: You can set the ID of a View element by double-clicking on it in the graphical editor. Alternatively, look for a property called id in the list of properties, or add **android:id="..."** to the XML code of the element.

Launch your app. This will start the emulator and show the awesome activity you just created. If it works well, this is a good time for another git commit. Do this before continuing.

### c. Test your app

As you learned in the Software Engineering course last semester, testing is very important as it allows you to find some bugs early in the development cycle. We will now learn how to write tests in the Android ecosystem.

Create a new file (**MainActivityTest.java**) in the **ch.epfl.sdp.bootcamp** (androidTest) folder with the following code:
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

> :information_source: You should update `R.id.mainName` and `R.id.mainGoButton` if you chose to name these differently in the previous part.

If you want to know more about this test, have a look at the [Android testing guides](https://developer.android.com/training/testing/index.html). This test relies on a library called [Espresso](https://developer.android.com/training/testing/ui-testing/espresso-testing.html). You need to add Espresso as a dependency to the project. To do so:

* Add this line to the *dependencies* section of your **app/build.gradle** (under "Gradle Scripts" in the project explorer):
    ```Gradle
        androidTestImplementation 'androidx.test:rules:1.2.0'
    ```
* Disable animations in your emulator (Inside the emulator, click the round **Apps** button, then choose **Settings**. If you do not see an entry named Developer options, then go to Settings > About emulated device and click on the Build number entry 5 times. A message should normally tell you that you are x steps away from becoming a developer. Then set Settings > Developer options > Window animation scale to Animation off; same for Transition animation scale and Animator duration scale).

Now, you have all the prerequisites to make your test compile. You still need to complete the test file by adding import statements. Android Studio can do this for you: select the places with compile errors, press Alt+Enter, and choose **import ...**.

Once all compile errors are fixed, run your test by right-clicking on the **MainActivityTest** class and selecting Run 'MainActivityTest', then watch it pass. Works? Great! Don't forget to commit it.

> :warning: If Espresso tests fail with **java.lang.SecurityException**: Injecting to another application requires INJECT_EVENTS permission, the most likely reason is that the soft keyboard is hiding your button. You can disable the soft keyboard (Tools > Android > AVD Manager > Edit > Show Advanced Settings > Enable keyboard input).

You are now able to test your code, but you don't yet know how well your tests cover your code (**code coverage**). Thankfully, the JaCoCo Gradle Plugin enables you to do just that.

First, you need to add the JaCoCo plugin. Add this line to the *dependencies* section of your top-level **build.gradle**:

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

You are now able to generate code coverage reports with JaCoCo. These reports provide detailed information about both classes and methods coverage.

Open a command line. `cd` to where your project is. Run the following command:
```sh
./gradlew clean jacocoTestReport
```

You will find the generated report here: `app/build/reports/coverage/debug/index.html`.

That's all for now. Don't forget to commit your changes again :).

### d. Clean up

This is a good time to clean up your code. Make sure there are no compiler warnings. Remove unneeded code where necessary. Run Code > Optimize imports and Analyze > Inspect code to improve the quality of your code. As final steps, git add, git commit your work, and git push it to your bootcamp repository.

Repeat this process on a regular basis as your code base changes.

#### About some common warnings

* **Add backup properties** and **Firebase App Indexing**: These would be useful in a real app, but not here. Ignore the warnings.
* **Missing return value** in the Gradle script: This appears to be an Android Studio bug. Ignore the warning.
* **Unused property** in gradle.properties: Ignore the warning.
* **Typos** in words like sdp or epfl: Ignore them. (but fix real typos!)
* **Unused parameter** in the *onClick* handler: Android requires that parameter, even if you're not using it. Suppress the warning (for the method only!)
* **Obsolete stuff** in the tests: Suppress the warnings.

If you encounter some other warning, and believe it to either be a false positive, or something that cannot be fixed without too much effort compared to the app's complexity, please ask the staff by creating a thread on the course forum.

It's okay for now. Don't close Android Studio yet, we will come back to your brand new Android app in a moment.

### e. Set-Up Travis CI for your repository

Travis CI needs to know what to do whenever your code base changes. Think of it as a recipe on how to build and test your project.
Add a `.travis.yml` file at the root of your repository. Copy the following snippet and paste it into the file:

```yml
language: android
android:
  components:
    # The BuildTools version used by your project (make sure it's exactly the same as in the build.gradle)
    - build-tools-29.0.3
    # The SDK version used to compile your project
    - android-29
    # The SDK version used by the system image
    - android-22
    # The system image, to run an emulator during the tests
    - sys-img-armeabi-v7a-android-22
before_script:
  # Emulator Management: Create, Start and Wait
  - echo no | android create avd --force -n test -t android-22 --abi armeabi-v7a
  - emulator -avd test -no-audio -no-window &
  - android-wait-for-emulator
  - adb shell input keyevent 82
```

Add the `.travis.yml` file, commit and push. This will trigger a Travis CI build. By default, Travis CI triggers a build on `git push` command and pull request. You will keep it that way.

First, the build clones your repository. Android dependencies are downloaded and installed. Then your project is built. The build proceeds by instantiating an Android emulator. Once the emulator is ready, the build runs your test suite, and exits.

Check the [build status page](https://travis-ci.org) to see if your build passes or fails. The build will run for approximately ten minutes if it passes. In case of failure, refer to the job log to see what went wrong. Your build may fail because of some view not being constrained; you may find useful [this post](https://stackoverflow.com/questions/45226854/this-view-is-not-constrained-it-only-has-designtime-positions-so-it-will-jump?rq=1) in such a case. If you still can't figure out why the build fails, ask a TA or AE or post on the course forum.

> :information_source: If you pushed every time we asked you to do so, you should already have a few failed build on your account (one per push). This is entirely normal: as you didn't have a `.travis.yml` file before, Travis didn't know how to build and therefore just failed.

Your build should pass before you continue the bootcamp.

To reward you for your successful build, we grant you a badge. Create a file `README.md` at the root of your repository (The file may already exist). Paste the following line just below the very first section of your `README` (Replace your ID and your repository in the URLs where necessary):

```
[![Build Status](https://travis-ci.org/your-id/your-repo.svg?branch=master)](https://travis-ci.org/your-id/your-repo)
```

Add the file you just edited, commit and push it. Refresh the web page of your repository. You should see a beautiful badge saying that your build passed.

You are officially a confirmed Travis CI user. Congrats!

> :information_source: The version of Android emulator you defined in the `.travis.yml` is purposely different from the one you configured on your local environment. As a result, you may sometime observe your test suite running successfully on your machine while failing during Travis builds. These failures are often due to the different screen sizes of the devices. As a result, we strongly encourage you to use view dimensions that allow the layout to resize. This way, your Android app can perfecly run on any device.

### f. Set-Up Code Climate Quality

Code Climate should have already reported some code review insights on all your previous commits - it doesn't require any initial configuration to work correctly. 

You are not supposed to have any issues at this point. If you have such things, make sure to fix them all. Call for assistance if that is difficult.

We would now like Code Climate to also report the code coverage metrics for your code. In order for that to work, we must tell Travis how to report that information to Code Climate.

Go to your Travis CI repository's dashboard. Click the **More options** button on the right. Select **Settings**. You need to add a new environment variable:

- Name: `CC_TEST_REPORTER_ID`
- Value: Go to your Code Climate repository's dashboard. Access the **Repos Settings**. Find **Test coverage**. Copy and paste the `TEST REPORTER ID`. Leave unset the `Display value in build log` toggle button.

Edit your `.travis.yml` file. Append the following snippet:

```yml
  # This should be in the `before_script` entry
  # Set up Code Climate test reporter
  - curl -L https://codeclimate.com/downloads/test-reporter/test-reporter-latest-linux-amd64 > ./cc-test-reporter
  - chmod +x ./cc-test-reporter
  - ./cc-test-reporter before-build
script:
  - ./gradlew build connectedCheck jacocoTestReport
after_script:
  # Report test coverage to Code Climate
  - export JACOCO_SOURCE_PATH=app/src/main/java/
  - ./cc-test-reporter format-coverage ./app/build/reports/coverage/debug/report.xml --input-type jacoco
  - ./cc-test-reporter upload-coverage
```

Add the changes, commit and push them.

Your commit will trigger another Travis CI build. Once it finishes and passes, go back to your Code Climate repository's dashboard. You should now also see your code coverage. 

From now on, Travis CI builds generate a test report from your test suite. Once generated, the test report is sent to Code Climate API. Code coverage metrics of your project are available on Code Climate dashboard. 

Finally, we will now enable some Code Climate features on GitHub.

Get back to the Code Climate dashboard of your repository, the **Repo Settings** tab, and click **GitHub**. You will install two Code Climate Quality features to make full use of their capabilities through GitHub:
- Set up the feature `Pull request comments`. You may need to enter your password. Select your bootcamp repository, and save. Go back to **Repo Settings/GitHub**. Enable `Summary comments` and `Inline issue comments`, and save the changes.
- Install the feature `Pull request status updates`. Once installed, make sure it is indeed enabled, and save.

With these two features enabled, you will be able to get Code Climate reviews and code coverage percentage directly in your pull requests. This way, you won't need to use Code Climate dashboard anymore.

By default, Code Climate enforces a 50% test coverage threshold. This means that you cannot merge your pull requets as long as your code coverage is under 50%. You need to increase that threshold to 80%.

Go to the Code Climate dashboard of your repository. Click **Repo Settings**. Find **Test coverage**. Make sure that both `Enforce Diff Coverage` and `Enforce Total Coverage` are checked. Increase the test coverage threshold to 80%.

Save the changes.

We are in a good mood today. We feel really generous. This is why we grant you two other badges, but you will take them yourself this time. On Code Climate dashboard of your repository (`https://codeclimate.com/github/your-id/your-repo`), go to the **Repo Settings** tab, and click **Badges**.

You are interested in both Maintainability and Test Coverage badges. Copy the Markdown version of each snippet. You can now edit your `README.md`, and paste the snippets you just copied right after the line you added earlier.

Add the file you just edited, commit and push it. Refresh the web page of your repository. You should see three badges.

You are officially a confirmed Code Climate Quality user. Congrats!

> There are a few plugins worth using along with Code Climate. We recommend you the following:
> - [Checkstyle](https://docs.codeclimate.com/docs/checkstyle) help you adhere to a coding standard. This would be quite useful during your project, each member of the team having their own coding style.
> - [SonarJava](https://docs.codeclimate.com/docs/sonar-java) is a code analyzer for Java projects. In addition to Code Climate analyses, it performs hundreds of checks to find code smells, bugs and security vulnerabilities.

> You can see the list of available plugins in the **Repo Settings** tab.

### g. Enable GitHub required status checks

Travis CI and Code Climate are really cool tools, but they become useless if one tries to bypass them. As a consequence, you will enforce required status checks before a branch is merged in a pull request or before commits on a local branch can be pushed to the protected remote branch. To achieve it, you need to access your Github repository settings. Go to **Branches**. Under **Branch protection rules**, add a rule. Write `master` in the **Branch name pattern** input field; this means that the rule will be applied to the master branch. Check the following:

- `Require status checks to pass before merging`
- `Require branches to be up to date before merging`
  - `codeclimate`
  - `codeclimate/diff-coverage`
  - `codeclimate/total-coverage`
  - `continuous-integration/travis-ci`
- `Include administrators`

Save the changes.

From now on, you will need to make the checks pass in order to merge your pull requests.

> :information_source: For your SDP project, you will also need to check `Require pull request reviews before merging` and require two reviewers. As a result, two members of your team will have to review your pull requests and approve them before they can be merged.

### h. Add a new greeting feature to your app

It is a good habit to develop a feature on a dedicated branch. You will actually need to get used to that for the project. Given the fact that you need to implement a greeting feature, you will have to create a branch for that. You will name the branch `feature/greeting`.

Open a command line. `cd` to where your project is. Make sure that there is nothing to commit with `git status`. If there are changes that you haven't committed yet, add, commit, and push them. Create the branch `fb-greeting` as follows:

```sh
git checkout -b feature/greeting
```

`git branch` should tell you that you are indeed on the branch `feature/greeting`.

The Git commands you have been using until now still apply. The only difference now is the branch on which you will push your commits.

> :information_source: There are multiple conventions for naming git branches. We suggest you and your team figure out one and stick to it. For example, you can start all the feature branches with `feature/` or `fb-`, all the bugfixes with `bugfix/` or `hotfix/`... 


Go back to your Android project. Uncomment the last statement in your test (the one that is commented out in the example code that we provided). The test will no longer compile, since you do not yet have a view with the name "greetingMessage".

So, add a second activity called **GreetingActivity** to your app, with a TextView that has ID "greetingMessage" (the layout file is defined at `app/res/layout/activity_greeting.xml`). This will make your test compile again. However, the test fails because your app doesn't do anything when the button is clicked.

Now, you can add the code that starts the GreetingActivity when you click the button and sets the greeting message to "Hello <name>!", where <name> is whatever the user entered in the text field. The [Android documentation on starting an activity](https://developer.android.com/training/basics/firstapp/starting-activity.html) contains all you need to know to do this. Reading documentation is part of being a software engineer. Get used to it.

One hint: the Android documentation talks about editing the XML files directly. You can do this, but you don't need to since you can set all of these properties for Views using the interface designer in Android Studio.

Don't forget to commit and push to your branch often! 

It's time for you to let the world know that your greeting feature is now complete. Open your web browser. Go to your GitHub repository, **Pull Requests** tab, and click **New pull request**. Select the branch `feature/greeting` as the branch to compare with `master`. You may read the branch difference, as this is your last chance to catch typos and errors before merging.

The creation of the pull request will trigger both Travis CI and Code Climate Quality checks. You are not allowed to merge your pull request until all checks are successful. Once they are, merge the pull request. Your greeting feature will be merged to the code base on `master`.

> :information_source: Pull requests let you tell others about changes you've pushed to a GitHub repository. Once a pull request is sent, interested parties can review the set of changes, discuss potential modifications, and even push follow-up commits if necessary. In a team project, you should only merge branches through Pull Requests (PRs).

You're done for this bootcamp. Congrats!

## Additional resources

If you want more practice with the Android environment and UI design patterns, have a look at other exercises:

- [Android MVP (DP ex8)](https://github.com/sweng-epfl/exercises/tree/master/design-patterns-ui/exercises/ex8)
- [Android React-like MVVM (DP ex12)](https://github.com/sweng-epfl/exercises/tree/master/design-patterns-ui/exercises/ex12)


Other resources:

- [Android API reference](https://developer.android.com/reference)
