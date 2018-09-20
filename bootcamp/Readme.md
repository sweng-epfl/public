# Bootcamp - Intro to Android, Git, and testing
## CS305 Bootcamp
**The bootcamp is due on Friday 28-Sep-2018 at 18:00**
This bootcamp will show you how to get started with the tools you will need for this course. You will learn about Android development, Git, unit testing, Travis CI and Code Climate Quality.

The bootcamp is divided into two parts: i) Git basics that will familiarize you with the productive use of GitHub; ii) a few steps that will showcase you the tools useful later for the project. SwEng students do only part I, and SDP students do the whole bootcamp.

Please use your own laptop for the bootcamp (not on the BC machines and not in a remote VM).

The lab session is a great opportunity to ask the teaching assistants about any problems you have with the bootcamp. Thus, we encourage you to start it today, and ask your questions at the lab.

Now let's dive right in.

### Part I
#### Install Java, and validate your setup
You should use Java 8. Download and install Java SE 8 from [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). For detailed installation look [here](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html).

To validate your installation, you can do a `java -version` from the command line. You are ready to go as soon as the command outputs something like this:

```sh
java version "1.8.0_144"
Java(TM) SE Runtime Environment (build 1.8.0_144-b01)
Java HotSpot(TM) 64-Bit Server VM (build 25.144-b01, mixed mode)

```

Do not worry if the version number is not exactly the same.

A command line, a text editor, and a working Java environment should be sufficient enough for this part of the bootcamp. We do not ask you to install an integrated development environment (IDE). You may do so if you wish, though.

#### Create a Hello World program, compile it, and run it
You'll need some text files to play with so that you can make use of Git. To this end, you need to write a `hello world` program.

Create first a project folder where you will store your Java program. You can name your folder `Bootcamp` for instance.

Here is a `hello world` program ready to be used:

```java
public class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello, world!");
  }
}
```

Create a file `HelloWorld.java`. Copy into it the given snippet. Open a command line. `cd` to where your project is. Compile your program with `javac HelloWorld.java`. Run it with `java HelloWorld`.

You should get a `Hello, world!` message. If it does not happen, please, check again the sequence of steps, and ask an assistant if it doesn't help.

#### Set up Git, and create a repository
Let's save a snapshot of the program, so that you can get back to this stage if you make a mistake. You will use Git throughout the course for version control and to submit your answers during the exams.

Git is a version control system for tracking changes in computer files and coordinating work on those files among multiple people. It is primarily used for source code management in software development, but it can be used to keep track of changes in any set of files.

Setting up Git is easy: First, you need an account on [GitHub](https://github.com) (the site that will host our code during this course). If you don't already have a GitHub account, create one by [following these instructions](https://github.com/join). Please make sure that your profile shows your real name.

Next, follow the [setup instructions](https://help.github.com/articles/set-up-git/) to install Git on your computer.

- **If you are on Windows**, when installing Git, select "Use Git from the Windows Command Prompt" when asked about adjusting your PATH; if you choose one of the other options, whenever this bootcamp talks about "a command line", open Git Bash instead.
- **If you are on a *NIX system (macOS, Linux)**, Git may already be preinstalled on your system. You can check this by running `git --version`.

To track your program using Git, open a command line. cd to where your program is; this folder should have a file named `HelloWorld.java`. From there, execute the following commands:
```sh
git init   # Initialize a new repository
git add .  # Add all the files to the repository
git commit # Create a first commit
```

> As EPFL students, you can claim the [GitHub Student Developer Pack](https://education.github.com/pack). This pack give students free access to a few interesting developer tools. Amongst them, there is GitHub's feature for creating private repositories.

#### Publish your repository on GitHub
Once you have set up Git, please create a **public** repository on GitHub. You can name it as you please. You will need this repository to complete the next steps.

Before going further, please [fill out this form](https://docs.google.com/forms/d/e/1FAIpQLSfUJhaDp21Kf9SAOWbPAogZ7P7hMqPDhA_l_Ir7-631D70uHg/viewform). We will need this information to associate your GitHub account with your GASPAR identity.

Now, you need to publish your commits on GitHub since that is where we look for submitted bootcamp. This requires two steps:
1. Add GitHub as a "remote" to your repository, using the following command: `git remote add origin https://github.com/your-id/your-repo.git`
2. Push your commits to GitHub: `git push -u origin master`

This should be done once per branch and "remote". From now on, the commands to create a commit and push it are:

```sh
git add . # Add all the changes
git commit # Commit; you can also add -m "put your commit message here..."
git push # The "-u origin master" parameters are only needed the first time.
```

#### Adding and updating files
You will apply some changes to our `hello world` program. You need to get rid of the comma and the exclamation mark from the printed message.

Modify your program accordingly so that it prints **Hello world**. Make sure to compile your program, and run it again to validate your changes.

Add the file you just changed. You can also add any generated file for now.
```sh
git add <file>
```

The ```git add``` command adds a change in the working directory to the staging area. It tells Git that you want to include updates to a particular file in the next commit. However, ```git add``` doesn't really affect the repository in any significant way; changes are not actually recorded until you commit them.

Commit the changes you just made to your local repository.
```sh
git commit -m "<message>"
```

Commits are like checkpoints you set for yourself. It is a good practice to keep each commit as small and to the point as possible. In the long run, this keeps the code base organized, and will help preserve the sanity of everyone on your team.

When it comes to commit messages, beginners (and non-beginners) tend to write anything they have in mind just to get rid of them. It is actually quite a challenge to write a good commit message. There is no absolute rule, but there are several guidelines over the net. Check for instance [Chris Beams' post](https://chris.beams.io/posts/git-commit/) on how to write a Git commit message.

Keep in mind his `Seven rules of a great Git commit message`:

1. Separate subject from body with a blank line
1. Limit the subject line to 50 characters
1. Capitalize the subject line
1. Do not end the subject line with a period
1. Use the imperative mood in the subject line
1. Wrap the body at 72 characters
1. Use the body to explain *what* and *why* vs. *how*

You can now push your commit to your remote repository. Use the ``master`` branch for that purpose. This is the main branch for any Git repository.
```sh
git push origin <branch>
```

The ```git push``` command is used to upload local repository content to a remote repository. Pushing is how you transfer commits from your local repository to a remote repository.

#### Three Handy Commands
These are handy for displaying information and showing changes.

```sh
git status
```
Prints your current status, including files that have changed since your last commit and files that are not currently tracked by your repository.

```sh
git branch
```
Displays the branch you are currently on.

```sh
git diff
```
Shows changes between commits, branches, files, and more.

#### Ignoring files
Git sees every file in your working copy as one of three things:

1. tracked - a file which has been previously staged or committed;
1. untracked - a file which has not been staged or committed; or
1. ignored - a file which Git has been explicitly told to ignore.

Ignored files are usually build artifacts and machine generated files that can be derived from your repository source or should otherwise not be committed. In the particular case of this part of the bootcamp, these generated files are the **.class** files.

Git uses a special file named **.gitignore** to list all the ignored files. It should be at the root of your repository. There is no explicit ```git ignore``` command: instead the **.gitignore** file must be edited and committed by hand when you have new files that you wish to ignore.

Note, that unlike the files it ignores **.gitignore** file itself should be tracked.

Create a **.gitignore** file at the root of your repository. Edit its content with the following line:

```
*.class
```

This line basically says to Git to ignore any files whose name ends with **.class**.

You can now add the file you just made. Commit it, and push it to the remote repository.

If you look into your remote repository on GitHub, you will notice that the **.class** are still there. You cannot force a file that is already committed in the repository to be removed just because it is added to the **.gitignore**. To get rid of them, run ``git rm --cached <file>``.

Remove the generated files with this command. Add the changes, commit, and push.

These generated files should have disappeared from your remote repository. You can also remove them locally with a regular `rm`.

Once these files are removed, compile again your program. The generated files are back. Run a `git status`. You should see that there is nothing to commit which means that **.class** files are indeed ignored.

You are done with this part of the bootcamp. Congrats!

### Part II
This part is not required for the students that do not take the SDP class. However, if you want to get to know basics of Android development, you are very welcome to proceed.
#### Fresh start
Let's start this part of the bootcamp with a clean repository. To this end, you need to get rid of all the files you have created so far. You won't need them anymore.

```sh
git rm -rf .
```

Delete all files on your branch `master`. Add the changes (the file deletions in this case), and commit them. Make sure to push the state of your local repository to your remote repository.

#### Setup Android Studio
Download and install Android Studio from [https://developer.android.com](https://developer.android.com). If you need any help, follow the [instructions](https://developer.android.com/studio/install.html).

You should choose the default options (you will do more tool configuration later).

Once you reach the welcome screen, please click on **Configure** at the bottom right of the window, and select **SDK Manager**. Then, in the **SDK Platforms** tab, please install **Android 7.1.1** (API Level 25).
After that, in the **SDK Tools** tab, first select **Show Package Details** (at the bottom right), then choose **Android SDK Build Tool 27.0.3** and install it.
#### Create a new project
Launch the Start a new *Android Studio project* wizard. Name your project **Bootcamp** and choose **sweng.epfl.ch** as the domain. Make sure to set the **project path** to your **repository path**. Leave the other settings at their default (i.e. choose API 15: Android 4.0.3 (Ice Cream Sandwich) as minimum SDK and add an Empty Activity). After a bit of time, you should see Android Studio open up with a number of files created for this project.

This is a good time to open a file explorer and look at all the files that have been created. The Android developer documentation on [managing projects](https://developer.android.com/studio/projects/index.html) provides a good overview of what all these files do.

Before running your app, please set the correct version of Android. To do it, you will need to open the **app/build.gradle** file.

Beware that an Android project has actually two **build.gradle** files. With a command line, it's easy to distinguish them since one is located at the root of the project while the other one is located in the `app` folder. With Android Studio, it can be a bit tricky to distinguish these files. Under `Gradle Scripts` in the file explorer, you will find the following:

- **build.gradle** `(Project: ...)`: this is the top-level gradle file.
- **build.gradle** `(Module: ...)`: this is the app-level gradle file, i.e. **app/build.gradle**.

Open now the **app/build.gradle**. In this file, please update the following settings:
* `compileSdkVersion 28`
* `buildToolsVersion "27.0.3"` (comes right after `compileSdkVersion 28`)
* `minSdkVersion 15`
* `targetSdkVersion 28`
* In the *dependencies*, `implementation 'com.android.support:appcompat-v7:28.0.0-rc02'`

And in the top level **build.gradle** file, please update the following setting:

```gradle
classpath 'com.android.tools.build:gradle:3.1.4'
```

To run your app, use *Run* > *Run 'app'*. You will see a dialog asking about which emulator you want to use. The default (Nexus 5X API 25 x86) is fine. It will take a few seconds to start up the emulator and then you will see Android itself start up, followed by your app. If this emulator is not installed, please create it by selecting **create a new virtual device**.

Let's save a snapshot of the project, so that you can get back to this stage if you make a mistake.

#### Create an Android app
We will kickstart your career as Android software engineer by creating a friendly greeting application.

Below is a list of steps we suggest to build this app. In addition, you will find below some hints. Try to follow the steps as well as you can. If you get stuck, there are several options:
* Check the hints below.
* Follow the Android documentation links.
* Google your question or search on [stackoverflow](https://stackoverflow.com/questions/tagged/android)
* Ask a question in the course forum.
* Ask a TA or AE at the lab session.

#### Create the main screen
Edit the **app/res/layout/activity_main.xml** file and add a "Plain Text" text field and a button to your activity by dragging them from the Palette of components and setting the appropriate properties. In the Properties of the component, enter some text in the "Plain Text" text field as a hint, then style the text field and button as you like.

One part is important, though: set the id property of both the field and the button to a meaningful value, such as `mainName` and `mainGoButton`. You will use this ID to access the elements from your source code. To do it, scroll down the Properties and select "View all properties", then you can update the id property of the component by clicking on it.

Launch your app. This will start an emulator and show the awesome activity you just created. If it works well, this is a good time for another git commit. Do this before continuing.

#### Test your app
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
        androidTestImplementation 'com.android.support.test:rules:1.0.2'
    ```
* Set your test runner to **android.support.test.runner.AndroidJUnitRunner** (File > Project Structure, select 'app' in the module list, Flavors > Test Instrumentation Runner).
* Disable animations in your emulator (Inside the emulator, click the round **Apps** button, then choose **Settings**. If you do not see an entry named Developer options, then go to Settings > About emulated device and click on the Build number entry 5 times. A message should normally tell you that you are x steps away from becoming a developer. Then set Settings > Developer options > Window animation scale to Animation off; same for Transition animation scale and Animator duration scale).

Now, you have all the prerequisites to make your test compile. You still need to complete the test file by adding import statements. Android Studio can do this for you: select the places with compile errors, press Alt+Enter, and choose **import ...**.

Once all compile errors are fixed, run your test by right-clicking on the **MainActivityTest** class and selecting Run 'MainActivityTest', then watch it pass. Works? Great! Don't forget to `git add` and `git commit`.

#### Configure Gradle with JaCoCo plugin
You are now able to test your code, but you could do much better. For instance, you could measure how well your test suite covers your code. We call this measure **code coverage**.

Code coverage is a measure used to describe the degree to which the source code of a program is executed when a particular test suite runs. You will learn more about code coverage during SwEng classes.

For now, you will configure your Android project so that it reports your code coverage. The JaCoCo plugin provides code coverage metrics for Java code. As a result, you need to configure Gradle with JacoCo plugin.

First, you need to add JaCoCo plugin. Add this line to the *dependencies* section of your top-level **build.gradle**:

```gradle
classpath 'org.jacoco:org.jacoco.core:0.8.0'
```

You need then to add a few things in your **app/build.gradle**. We provide you a partial version of the file. Comments emphasize that there is some content. Do not change anything; add only JaCoCo-related lines:

```gradle
apply plugin: 'com.android.application'
apply plugin: 'jacoco'

jacoco {
    toolVersion = "0.8.0"
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

That's all for now. We will come back to JaCoCo plugin a bit later. Again, don't forget to `git add` and `git commit`.

#### Clean up
This is a good time to clean up your code. Make sure there are no compiler warnings. Remove unneeded code where necessary. Run Code > Optimize imports and Analyze > Inspect code to improve the quality of your code. As final steps, git add, git commit your work, and git push it to your bootcamp repository.

Repeat this process on a regular basis as your code base changes.

#### About some common warnings
* **Add backup properties** and **Firebase App Indexing**: These would be useful in a real app, but not here. Ignore the warnings.
* **Missing return value** in the Gradle script: This appears to be an Android Studio bug. Ignore the warning.
* **Unused property** in gradle.properties: Ignore the warning.
* **Typos** in words like sweng or epfl: Ignore them. (but fix real typos!)
* **Unused parameter** in the *onClick* handler: Android requires that parameter, even if you're not using it. Suppress the warning (for the method only!)
* **Obsolete stuff** in the tests: Suppress the warnings.

If you encounter some other warning, and believe it to either be a false positive, or something that cannot be fixed without too much effort compared to the app's complexity, please ask the staff by creating a thread on [this forum](https://piazza.com/class/jm0kpuvsnq61l9).

#### Set up continuous integration with Travis CI
Continuous integration is a development practice that calls upon development teams to ensure that a build and subsequent testing is conducted for every code change made.

You will be using Travis CI for that very purpose.

Setting up continuous integration with Travis CI is easy: First, you need an account on [Travis CI](https://travis-ci.org). You may be asked to sign in to GitHub, if not yet done. Authorize Travis CI to access your GitHub account. Select the repository you want to use with Travis CI (Use the toggle button). Once selected, click on your bootcamp repository: you will get to a build dashboard for your repositories.

We strongly recommend you to get familiar with [Travis CI's core concepts](https://docs.travis-ci.com/user/for-beginners).

#### Configure Travis CI through your repository
Travis CI needs to know what to do whenever your code base changes. Think of it as a recipe on how to build and test your project.
Add a `.travis.yml` file at the root of your repository. Copy the following snippet and paste it into the file:

```yml
language: android
android:
  components:
    # The BuildTools version used by your project
    - build-tools-27.0.3
    # The SDK version used to compile your project
    - android-28
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

Add the `.travis.yml` file, commit and push. This will trigger a Travis CI build. By default, Travis CI triggers a build on `git push` command and pull request (We'll come back to pull requests a bit later). You will keep it that way.

First, the build clones your repository. Android dependencies are downloaded and installed. Then your project is built. The build proceeds by instantiating an Android emulator. Once the emulator is ready, the build runs your test suite, and exits.

Check the [build status page](https://travis-ci.org) to see if your build passes or fails. The build will run for approximately ten minutes if it passes. In case of failure, refer to the job log to see what went wrong. Your build may fail because of some view not being constrained; you may find useful [this post](https://stackoverflow.com/questions/45226854/this-view-is-not-constrained-it-only-has-designtime-positions-so-it-will-jump?rq=1) in such a case. If you still can't figure out why the build fails, ask a TA or AE or post on the course forum.

Your build should pass before you continue the bootcamp.

To reward you for your successful build, we grant you a badge. Create a file `README.md` at the root of your repository (The file may already exist). Paste the following line just below the very first section of your `README` (Replace your ID and your repository in the URLs where necessary):

```
[![Build Status](https://travis-ci.org/your-id/your-repo.svg?branch=master)](https://travis-ci.org/your-id/your-repo)
```

Add the file you just edited, commit and push it. Refresh the web page of your repository. You should see a beautiful badge saying that your build passed.

You are officially a confirmed Travis CI user. Congrats!

#### Set up automated code review with Code Climate Quality
Automated code review checks source code for compliance with a predefined set of rules or best practices. The use of analytical methods to inspect and review source code to detect bugs has been a standard development practice.

You will be using Code Climate Quality for that very purpose.

Setting up automated code review with Code Climate Quality is easy: First, you need an account on [Code Climate Quality](https://codeclimate.com/quality/). Start the Free Quality Trial. You may be asked to sign in to GitHub, if not yet done. Authorize Code Climate to access your GitHub account. Click next. In the `Free for open source` panel, add a repository. Again, give access authorization to Code Climate. Add your bootcamp repository.

Code Climate Quality will perform an initial automated code review of your `master` branch. Once the review is over, see the results.

You are not supposed to have any issues at this point. If you have such things, make sure to fix them all. Call for assistance if that is difficult.

#### Enable Code Climate features on GitHub
Get back to the Code Climate dashboard of your repository, the **Repo Settings** tab, and click **GitHub**. You will install two Code Climate Quality features to make full use of their capabilities through GitHub.

- Set up the feature `Pull request comments`. You may need to enter your password. Select your bootcamp repository, and save. Go back to **Repo Settings/GitHub**. Enable `Summary comments` and `Inline issue comments`, and save the changes.
- Install the feature `Pull request status updates`. Once installed, make sure it is indeed enabled, and save.

With these two features enabled, you will be able to get Code Climate reviews directly in your pull requests. This way, you won't need to use Code Climate dashboard anymore.

We are in a good mood today. We feel really generous. This is why we grant you two other badges, but you will take them yourself this time. On Code Climate dashboard of your repository (`https://codeclimate.com/github/your-id/your-repo`), go to the **Repo Settings** tab, and click **Badges**.

You are interested in both Maintainability and Test Coverage badges. Copy the Markdown version of each snippet. You can now edit your `README.md`, and paste the snippets you just copied right after the line you added earlier.

Add the file you just edited, commit and push it. Refresh the web page of your repository. You should see three badges.

You are officially a confirmed Code Climate Quality user. Congrats!

> There are a few plugins worth using along with Code Climate. We recommend you the following:
> - [Checkstyle](https://docs.codeclimate.com/docs/checkstyle) help you adhere to a coding standard. This would be quite useful during your project, each member of the team having their own coding style.
> - [SonarJava](https://docs.codeclimate.com/docs/sonar-java) is a code analyzer for Java projects. In addition to Code Climate analyses, it performs hundreds of checks to find code smells, bugs and security vulnerabilities.

> You can see the list of available plugins in the **Repo Settings** tab.

#### Use Code Climate with Travis CI
Now that Travis CI and Code Climate are all set, it's time for them to work together. So far, Travis CI builds your project and runs your test suite while Code Climate performs automated code review. It goes without saying that both tasks are independent for now.

Code Climate can actually make use of the test suite run by Travis CI, namely code coverage metrics. In order to do so, you need to generate a test report. You will be using JaCoCo plugin you configured earlier. Then the test report needs somehow to be sent to Code Climate. Travis CI provides ready-to-use test reporters for that purpose.

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
  - ./cc-test-reporter format-coverage ./app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml --input-type jacoco
  - ./cc-test-reporter upload-coverage
```

Add the changes, commit and push them.

Your commit will trigger another Travis CI build. Once it finishes and passes, go back to your Code Climate repository's dashboard. You should see your code coverage. Also, the badge you added in the `README` file earlier should also display it.

From now on, Travis CI builds generate a test report from your test suite. Once generated, the test report is sent to Code Climate API. Code coverage metrics of your project are available on Code Climate dashboard. Also, all your GitHub pull requests will display your code coverage percentage alongside Code Climate checks.

#### Enable GitHub required status checks
Travis CI and Code Climate are really cool tools, but they become useless if one tries to bypass them. As a consequence, you will enforce required status checks before a branch is merged in a pull request or before commits on a local branch can be pushed to the protected remote branch. To achieve it, you need to access your Github repository settings. Go to **Branches**. Under **Branch protection rules**, add a rule. Write `master` in the **Apply rule to** input field; this means that the rule will be applied to all branches. Check the following:

- `Require status checks to pass before merging`
- `Require branches to be up to date before merging`
  - `codeclimate`
  - `continuous-integration/travis-ci`
- `Include administrators`

Save the changes.

From now on, you will need to make the checks pass in order to merge your pull requests.

#### A small introduction to branches
Soon enough you will come to realize that team-based software development projects are quite challenging to carry on. So far, you have probably worked on several programming assignments with a single peer, and collaboration was already not always easy. Imagine what it would be with teammates from five to six persons.

One issue that you have often experienced with your peer is code sharing. You surely shared your code with your mate through mails or via messaging apps. You know better than anyone that this way of working is a mess. You and your mate always ended up messing with each other's code.

Git answers this issue with the concept of branch (There are many more uses of branches). We won't go into the details. There will be an exercise set that introduces everything you need to know about branches. For now, we will only provide you the commands so that you can proceed with the bootcamp.

#### Branching for the greeting feature
It is a good habit to develop a feature on a dedicated branch. You will actually need to get used to that for the project. Given the fact that you need to implement a greeting feature, you will have to create a branch for that. You will name the branch `fb-greeting` (`fb` stands for feature branch).

Open a command line. `cd` to where your project is. Make sure that there is nothing to commit with `git status`. If there are changes that you haven't committed yet, add, commit, and push them. Create the branch `fb-greeting` as follows:

```sh
git checkout -b fb-greeting
```

`git branch` should tell you that you are indeed on the branch `fb-greeting`.

The Git commands you have been using for the first part of the bootcamp still apply. The only difference now is the branch on which you will push your commits. This means that whenever you want to add changes, you will be doing the following:

```sh
git add .
git commit -m "Add some stuff"
git push origin fb-greeting
```

#### Create a greeting activity
Go back to your Android project. Uncomment the last statement in your test, the one that is commented out in the example code that we provided. The test will no longer compile, since you do not yet have a view with the name "greetingMessage".

So, add a second activity called **GreetingActivity** to your app, with a TextView that has ID "greetingMessage" (the layout file is defined at `app/res/layout/activity_greeting.xml`). This will make your test compile again. However, the test fails because your app doesn't do anything when the button is clicked.

#### Add behavior to your app
Now, you can add the code that starts the GreetingActivity when you click the button and sets the greeting message to "Hello <name>!", where <name> is whatever the user entered in the text field. The [Android documentation on starting an activity](https://developer.android.com/training/basics/firstapp/starting-activity.html) contains all you need to know to do this. Reading documentation is part of being a software engineer. Get used to it.

One hint: the Android documentation talks about editing the XML files directly. You can do this, but you don't need to since you can set all of these properties for Views using the interface designer in Android Studio.

#### Create a pull request
Let's begin with definition. Pull requests let you tell others about changes you've pushed to a GitHub repository. Once a pull request is sent, interested parties can review the set of changes, discuss potential modifications, and even push follow-up commits if necessary.

It's time for you to let the world know that your greeting feature is now complete. Open your web browser. Go to your GitHub repository, **Pull Requests** tab, and click **New pull request**. Select the branch `fb-greeting` as the branch to compare with `master`. You may read the branch difference, as this is your last chance to catch typos and errors before merging.

The creation of the pull request will trigger both Travis CI and Code Climate Quality checks. You are not allowed to merge your pull request until all checks are successful. Once they are, merge the pull request. Your greeting feature will be merged to the code base on `master`.

You're done. Congrats!

### Hints
If you don't want to enter a password every time you connect to GitHub, you can [set up authentication via SSH](https://help.github.com/articles/connecting-to-github-with-ssh/).

The emulator should boot and launch your app in 60 seconds or less. If not, there is probably a problem with your configuration, and you might want to revisit hardware acceleration settings.

You can set the ID of a View element by double-clicking on it in the graphical editor. Alternatively, look for a property called id in the list of properties, or add **android:id="..."** to the XML code of the element.

Android Studio is quite helpful. Did you know that many errors can be resolved by pressing Alt+Enter? That Shift+F6 renames a variable? We recommend getting familiar with Android Studio now, because you will use it throughout the semester.

If Espresso tests fail with **java.lang.SecurityException**: Injecting to another application requires INJECT_EVENTS permission, the most likely reason is that the soft keyboard is hiding your button. You can disable the soft keyboard (Tools > Android > AVD Manager > Edit > Show Advanced Settings > Enable keyboard input).

If you are stuck and cannot get something to work, see if you can't find the answer in the documentation or online. But if that does not work, do not waste a lot of your time randomly trying alternatives. Ask a TA or AE or post on the course forum.
