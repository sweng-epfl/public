# Operations

This guide introduces continuous integration for your Android app, to which you then add code coverage and static analysis.

> :information_source: If you are using this guide to integrate Cirrus with an existing application, and not one created recently,
> you may need to update some tool versions, such as Gradle itself, and the `com.android.tools.build:gradle` package.


## Continuous integration with Cirrus CI

In Software Engineering, we presented GitHub Actions as one way to do continuous integration, i.e., to run your tests automatically on a server so that you can be confident your code works not only on your own machine.

In SDP, we recommend you use Cirrus CI instead of GitHub Actions, for two main reasons: it supports faster hardware acceleration, which speeds up the builds,
and it has no monthly limits, which means you do not need to worry about the number of builds you do.

Go to [the GitHub Marketplace page for Cirrus CI](https://github.com/marketplace/cirrus-ci), scroll down and select `Public Repositories` (which is the free option), click on `Install it for free`, and confirm.
You will be asked which repositories the extension should have access to; feel free to pick either all repositories or just the one for your SDP project.

Then, add a file named `.cirrus.yml` to the root of your repository with the following contents:

```
check_android_task:
  name: Run Android tests
  env:
    API_LEVEL: 30
    TARGET: google_apis
    ARCH: x86
  container:
    image: reactivecircus/android-emulator-$API_LEVEL:latest
    kvm: true
    cpu: 8
    memory: 10G
  create_device_script:
    echo no | avdmanager create avd --force --name test --abi "$TARGET/$ARCH" --package "system-images;android-$API_LEVEL;$TARGET;$ARCH"
  start_emulator_background_script:
    $ANDROID_SDK_ROOT/emulator/emulator -avd test -no-window -gpu swiftshader_indirect -no-snapshot -no-audio -no-boot-anim -camera-back none
  build_script: |
    chmod +x gradlew
    ./gradlew assembleDebugAndroidTest
  wait_for_emulator_script: |
    adb wait-for-device
    adb shell input keyevent 82
  disable_animations_script: |
    adb shell settings put global window_animation_scale 0.0
    adb shell settings put global transition_animation_scale 0.0
    adb shell settings put global animator_duration_scale 0.0
  check_script:
    ./gradlew check connectedCheck
```

> :information_source: If you need a different API level, check out the supported ones and their corresponding target on [the ReactiveCircus documentation](https://github.com/ReactiveCircus/docker-android-images)

Then, add the following to the end of the `settings.gradle` file at the root of your repository (or create one if there is no such file):

```
ext.isCiServer = System.getenv().containsKey("CIRRUS_CI")
ext.isDefaultBranch = System.getenv()["CIRRUS_BRANCH"] == "master"
ext.buildCacheHost = System.getenv().getOrDefault("CIRRUS_HTTP_CACHE_HOST", "localhost:12321")

buildCache {
    local {
        enabled = !isCiServer
    }
    remote(HttpBuildCache) {
        url = "http://${buildCacheHost}/"
        enabled = isCiServer
        push = isDefaultBranch
    }
}
```

> :information_source: Note that you should change the second line in case your default branch name is not `master`.

And add the following to the end of the `gradle.properties` file at the root of your repository (again, create it if it doesn't exist):

```
org.gradle.daemon=true
org.gradle.caching=true
org.gradle.parallel=true
org.gradle.configureondemand=true
org.gradle.jvmargs=-Dfile.encoding=UTF-8
```

> :information_source: These last two file editions allow Cirrus CI to cache your builds, which makes them faster,
> and requires fewer resources from Cirrus CI (as they are providing these resources for free, being a considerate user is important).

Commit these changes and push, and you should now see a yellow circle next to your commit, on which you can click to get more information.
This circle will turn into a green checkmark or a red cross depending on whether your build succeeds or fails.
You can also go to https://cirrus-ci.com/ and log in to see all of your builds.

> :information_source: A few things to note, if you ever want to modify this script or reuse it for another project:
> - YAML can be tricky to work with; as a general rule, always use the `|` pipe trick to make multi-line scripts in a Cirrus CI config file
> - Using an x86 emulator image and enabling KVM in the container allows for much faster execution than the default ARM emulator, even with HAXM as GitHub does on macOS Android containers
> - The script builds the project after launching the emulator and before waiting for the emulator to have finished booting, thus the emulator boots in parallel with the build, saving time
> - The script disables Android animations, to ensure tests do not fail because animations slow them down


## Code coverage with JaCoCo

One common and useful way to measure the usefulness of automated tests is _code coverage_.

In SDP, we will use JaCoCo to measure code coverage.

In your **app** `build.gradle`, add the following:

```
...
plugins {
    ...
    id 'jacoco'
}

android {
    ...
    buildTypes {
        ...
        debug {
            testCoverageEnabled true
        }
    }
}

dependencies {
    ...
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

    def fileFilter = [
        '**/R.class',
        '**/R$*.class',
        '**/BuildConfig.*',
        '**/Manifest*.*',
        '**/*Test*.*',
        'android/**/*.*',
        // Exclude Hilt generated classes
        '**/*Hilt*.*',
        'hilt_aggregated_deps/**',
        '**/*_Factory.class',
        '**/*_MembersInjector.class'
    ]
    def debugTree = fileTree(dir: "$project.buildDir/intermediates/javac/debug/classes", excludes: fileFilter)
    def mainSrc = "$project.projectDir/src/main/java"

    sourceDirectories.setFrom(files([mainSrc]))
    classDirectories.setFrom(files([debugTree]))
    executionData.setFrom(fileTree(dir: project.buildDir, includes: [
            'jacoco/testDebugUnitTest.exec', 'outputs/code_coverage/debugAndroidTest/connected/*coverage.ec'
    ]))
}

connectedCheck {
    finalizedBy jacocoTestReport
}
```

> :information_source: If you use Kotlin instead of Java, `debugTree` should be `fileTree(dir: "$project.buildDir/tmp/kotlin-classes/debug", excludes: fileFilter)` instead.

Then, sync Android Studio with Gradle using either the banner that pops up at the top of the editor or the button in the top right.

Whenever you run Android unit tests, from Android Studio or from the command line using `gradlew.bat connectedCheck` (Windows) or `./gradlew connectedCheck` (Linux/macOS),
JaCoCo will now generate a coverage report, which will be in `app/build/reports/jacoco/jacocoTestReport/html`. Open `index.html` with a Web browser to see the report.


## Code quality with Code Climate

Code Climate is an automated code review tool that ensures your projects complies with a predefined set of rules and best practices.

You will use Code Climate to ensure that your code follows good software engineering practices, and to visualize code coverage of all builds, including pull requests, without having to run them locally.

Go to the [Code Climate](https://codeclimate.com/quality/) website, click on `Get Started`, and authorize the login with your GitHub account.
Choose `Open Source`, then `Add a repository`, and authorize Code Climate to access your public GitHub repositories.
Then, click on the `Add repo` button next to the repository you're using.

Now, while viewing your repository on Code Climate, go to `Repo Settings` then to `Test coverage`. Copy the `Test reporter ID`.
Go to [Cirrus CI](https://cirrus-ci.com), select your repository, click on the settings wheel in the top right, and paste the test reporter ID under `Secured Variables`, then click the `Encrypt` button.
Copy the resulting value.

> :information_source: Despite the "ENCRYPTED[...]" name, the value is not itself an encrypted value, but a unique ID allowing Cirrus CI to know which variable you are referring to.
> Thus, you do not need to worry about putting this value in a public configuration file, as no one can "decrypt" it except Cirrus CI's own servers.

In your repository's `.cirrus.yml` file, add the encrypted value in the environment variables (replacing `ENCRYPTED[abcdefg]` by the actual value you just copied, including the `ENCRYPTED[]` wrapper):

```
check_android_task:
  ...
  env:
    ...
    CC_TEST_REPORTER_ID: ENCRYPTED[abcdefg]
  ...
```

Then add the following just after `disable_animations_script`:

```
  prepare_codeclimate_script: |
    curl -L https://codeclimate.com/downloads/test-reporter/test-reporter-latest-linux-amd64 > ./cc-test-reporter
    chmod +x ./cc-test-reporter
    ./cc-test-reporter before-build
```

And the following just after `check_script`:

```
  report_codeclimate_script: |
    export JACOCO_SOURCE_PATH=app/src/main/java/
    ./cc-test-reporter format-coverage ./app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml --input-type jacoco
    ./cc-test-reporter upload-coverage
```

To make sure Code Climate does not include the tests themselves in coverage, which would bias the results, create a file named `.codeclimate.yml` at the root of your repository with the following contents:

```
exclude_patterns:
- "**/test/"
- "**/androidTest/"
- "**test"
```

Commit your changes and push them. Cirrus CI will build your code, and push the coverage report to Code Climate, where you will be able to see it.

To avoid having to go to Code Climate manually, go to `Repo settings` in your repository on Code Climate, select `GitHub`, and set up and install `Pull request comments` and `Pull request status updates`.
Code Climate will now send comments directly on pull requests.

Finally, still in the repo settings on Code Climate, select `Test coverage`, ensure both `Enforce Diff Coverage` and `Enforce Total Coverage` are enabled, and increase the threshold to 80.
This ensures that your pull requests maintain 80% code coverage at all times, which we require in SDP.


## Android Lint in Cirrus CI

Let's add another form of static analysis to your project, the built-in Android "linter".
The Android linter warns you about common issues in your code. For instance, if you hard-code the user interface text, the linter will warn you that you should use resources instead,
so that you can translate the user interface to another language easily.

At the end of your repo's `.cirrus.yml` file, add the following:

```
  lint_script:
    ./gradlew lintDebug
  always:
    android-lint_artifacts:
      path: ./app/build/reports/lint-results-debug.xml
      type: text/xml
      format: android-lint
```

Commit and push. You will now be able to see Android's linter output directly from GitHub, in the detailed output of the Cirrus CI check when clicking on the status icon next to a commit and in pull requests.


## Pull request checks

Finally, let's make sure nobody can accidentally merge a pull request that does not pass automated and manual checks.

First, make sure that your latest commit has finished building on Cirrus CI after your last push, and that it builds correctly.

In your GitHub repository, go to `Settings`, then `Branches`, then click on the `Add rule` button, and:
- Set the name pattern to `*` (which matches all branches)
- Enable `Require pull request reviews before merging` with value `2`
- Enable `Require status checks to pass before merging`, and check `Run Android tests`
- Enable `Require branches to be up to date before merging`, to prevent conflicts if multiple pull requests are merged soon after each other
- Enable `Include administrators`, to ensure all team members have the same constraints

Congrats, you're done!


## Bonus: Badges

Now that you have a continuous integration pipeline, you can show off your tests and analysis results to the world!

To show the build status on Cirrus CI, add the following to any Markdown file, such as your project's "read me" file, replacing `USER` and `REPO` with your username and repository name respectively:

```
[![Build Status](https://api.cirrus-ci.com/github/USER/REPO.svg)](https://cirrus-ci.com/github/USER/REPO)
```

You can also get badges from Code Climate, by going to your repo settings there and copying the markdown from the `Badges` section.

If you want even more badges, have a look at https://shields.io/.
