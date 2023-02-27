# Operations

Your Android app has some code and tests, let's now add code coverage, continuous integration, and static analysis.


## Code coverage with JaCoCo

You will use [JaCoCo](https://www.eclemma.org/jacoco/) to measure the coverage of your tests.

In your **app/build.gradle**, add the following to existing blocks:
```
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
```
Then add the following to the end of the file:
```
tasks.withType(Test) {
    jacoco.includeNoLocationClasses = true
    jacoco.excludes = ['jdk.internal.*']
}

task jacocoTestReport(type: JacocoReport, dependsOn: ['testDebugUnitTest', 'createDebugCoverageReport']) {
    reports {
        xml.required = true
        html.required = true
    }

    def fileFilter = [
        '**/R.class',
        '**/R$*.class',
        '**/BuildConfig.*',
        '**/Manifest*.*',
        '**/*Test*.*',
        'android/**/*.*',
    ]
    def debugTree = fileTree(dir: "$project.buildDir/intermediates/javac/debug/classes", excludes: fileFilter)
    def mainSrc = "$project.projectDir/src/main/java"

    sourceDirectories.setFrom(files([mainSrc]))
    classDirectories.setFrom(files([debugTree]))
    executionData.setFrom(fileTree(dir: project.buildDir, includes: [
            'outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec',
            'outputs/code_coverage/debugAndroidTest/connected/*/coverage.ec'
    ]))
}

connectedCheck {
    finalizedBy jacocoTestReport
}
```

> :information_source: If you use tools that generate code, such as [Hilt](https://developer.android.com/training/dependency-injection/hilt-android),
> you should add exclusions for generated code (in Hilt's case, `'**/*Hilt*.*', 'hilt_aggregated_deps/**', '**/*_Factory.class', '**/*_MembersInjector.class'` should work)

> :information_source: If you use Kotlin instead of Java, `debugTree` should use `"$project.buildDir/tmp/kotlin-classes/debug"` instead

Then, sync Android Studio with Gradle using either the banner that pops up at the top of the editor or the button in the top right.

Running the Gradle task `connectedCheck` will now generate a coverage report, which will be in `app/build/reports/jacoco/jacocoTestReport/html`.
Open `index.html` with a Web browser to see the report.


## Creating a repo

Create a repository on GitHub and push the code of your app up until now, i.e., the code and tests.
This should be a _public_ repository to benefit from the free tiers of various services.

**Important**: Make sure your commits on GitHub are properly attributed to your username.
If that is not the case, consult [the documentation](https://docs.github.com/en/pull-requests/committing-changes-to-your-project/troubleshooting-commits/why-are-my-commits-linked-to-the-wrong-user)
and fix it now.


## Continuous integration with Cirrus CI

In Software Engineering, we presented GitHub Actions as one way to do continuous integration,
i.e., to run your tests automatically on a server so that you can be confident your code works not only on your own machine.

We recommend you use Cirrus CI instead of GitHub Actions, for two main reasons: it supports faster hardware acceleration, which speeds up the builds,
and it has no monthly limits, which means you do not need to worry about the number of builds you do.

Go to [the GitHub Marketplace page for Cirrus CI](https://github.com/marketplace/cirrus-ci), scroll down and select `Public Repositories` (which is the free option),
click on `Install it for free`, and confirm.
You will be asked which repositories the extension should have access to; feel free to pick either all repositories or just the one for your project.

Then, add a file named `.cirrus.yml` to the root of your repository with the following contents, inspired by [the Cirrus CI examples page](https://cirrus-ci.org/examples/#android):
```yaml
container:
  image: cirrusci/android-sdk:30
  cpu: 4
  memory: 16G
  kvm: true

check_android_task:
  name: Run Android tests
  install_emulator_script:
    sdkmanager --install "system-images;android-30;google_apis;x86"
  create_avd_script:
    echo no | avdmanager create avd --force
      --name emulator
      --package "system-images;android-30;google_apis;x86"
  start_avd_background_script:
    $ANDROID_HOME/emulator/emulator
      -avd emulator
      -no-audio
      -no-boot-anim
      -gpu swiftshader_indirect
      -no-snapshot
      -no-window
      -camera-back none
  assemble_instrumented_tests_script: |
    chmod +x gradlew
    ./gradlew assembleDebugAndroidTest
  wait_for_avd_script:
    adb wait-for-device shell 'while [[ -z $(getprop sys.boot_completed) ]]; do sleep 3; done; input keyevent 82'
  disable_animations_script: |
    adb shell settings put global window_animation_scale 0.0
    adb shell settings put global transition_animation_scale 0.0
    adb shell settings put global animator_duration_scale 0.0
  screen_record_background_script:
    for n in $(seq 1 20); do adb exec-out screenrecord --time-limit=180 --output-format=h264 - > $n.h264; done
  check_script:
    ./gradlew check connectedCheck
  always:
    wait_for_screenrecord_script: |
      pkill -2 -x adb
      sleep 2
    screenrecord_artifacts:
      path: "*.h264"
```

> :information_source: This script uploads recordings of the emulator screen as Cirrus CI artifacts, letting you see why tests fail!
> Android limits each recording to 3 minutes, so you will get multiple such recordings if your tests take longer than that.
> However, these recordings are in an uncommon format, your favorite video player might not be able to read them. `ffplay` definitely works
> (and is also available on Windows via the Windows Subsystem for Linux), you could also convert them using software such as `ffmpeg`.

Then, follow the instructions to add a [build cache](https://cirrus-ci.org/examples/#build-cache)
by appending to (or creating) the `settings.gradle` and `gradle.properties` files at the root of your repository, **but, importantly,**
make sure the comparison in the former of `CIRRUS_BRANCH` is done against the actual name of your main branch, which might be `main`, `master`, `dev`, or whatever you're using.
This allows Cirrus CI to cache your builds, which makes them faster,
and requires fewer resources from Cirrus CI (as they are providing these resources for free, being a considerate user is important).

Commit these changes and push, and you should now see a yellow circle next to your commit, on which you can click to get more information.
This circle will turn into a green checkmark or a red cross depending on whether your build succeeds or fails.
You can also go to https://cirrus-ci.com/ and log in to see all of your builds.

> :information_source: A few things to note, if you ever want to modify this script or reuse it for another project:
> - YAML can be tricky to work with; as a general rule, always use the `|` pipe trick to make multi-line scripts in a Cirrus CI config file
> - Using an x86 emulator image and enabling KVM in the container allows for much faster execution than the default ARM emulator, even with HAXM as GitHub does on macOS Android containers
> - The script builds the project after launching the emulator and before waiting for the emulator to have finished booting, thus the emulator boots in parallel with the build, saving time
> - The script disables Android animations, to ensure tests do not fail because animations slow them down
> - The script uses Android 30 because the Android 33 image seems to have stability issues, as multiple system components cause "not responding" dialogs right after boot


## Code quality with Code Climate

Code Climate is an automated code review tool that ensures your projects complies with good software engineering practices, and lets you visualize the code coverage of pull requests without having to build them locally..

Go to the [Code Climate](https://codeclimate.com/quality/) website, click on `Sign up with GitHub` further down the page, and authorize the login with your GitHub account.
Choose `Open Source`, then `Add a repository`, and authorize Code Climate to access your public GitHub repositories.
Then, click on the `Add repo` button next to the repository you're using.

Now, while viewing your repository on Code Climate, go to `Repo Settings` then to `Test coverage`. Copy the `Test reporter ID`.
Go to your repository page on Cirrus CI, click on the settings wheel in the top right, and paste the test reporter ID under `Secured Variables`, then click the `Encrypt` button.
Copy the resulting value.

> :information_source: Despite the "ENCRYPTED[...]" name, the value is not itself an encrypted value, but a unique ID allowing Cirrus CI to know which variable you are referring to.
> Thus, you do not need to worry about putting this value in a public configuration file, as no one can "decrypt" it except Cirrus CI's own servers.

In your repository's `.cirrus.yml` file, add an `env` block at the very beginning, before the `task` block,
with the encrypted value in the environment variables (replacing `ENCRYPTED[abcdefg]` by the actual value you just copied, including the `ENCRYPTED[]` wrapper):
```
env:
  CC_TEST_REPORTER_ID: ENCRYPTED[abcdefg]
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

Finally, still in the repo settings on Code Climate, select `Test coverage`, ensure `Enforce Total Coverage` is enabled, and increase the threshold to 80.
This ensures that your pull requests maintain 80% code coverage at all times, which is required in this course.

> :information_source: At the beginning of the project, you may have lots of code that is not worth the effort of covering,
> such as an implementation of a database interface that talks to a real database.
> You may choose to remove this check until you have enough code that can be covered, at which point you will put it back and never remove it again.


## Tests and Lint in Cirrus CI

Let's enable Cirrus to run the built-in Android "linter", which is another form of static analysis to show potential issues, and display its output as well as failed tests.
The Android linter warns you about common issues in your code. For instance, if you hard-code the user interface text, the linter will warn you that you should use resources instead,
so that you can translate the user interface to another language easily.

In your `.cirrus.yml` file, immediately after the `report_codeclimate_script` block, add the following block:
```
  lint_script:
    ./gradlew lintDebug
```
And after the `screenrecord_artifacts` block, add the following block:

```
    android_lint_artifacts:
      path: ./app/build/reports/lint-results-debug.xml
      format: android-lint
    test_artifacts:
      path: "./app/build/test-results/**/*.xml"
      format: junit
    androidtest_artifacts:
      path: "./app/build/outputs/**/*.xml"
      format: junit
```

Commit and push. You will now be able to see linter and tests output within GitHub, in the detailed output of the Cirrus CI check when clicking on the status icon next to a commit and in pull requests.


## Branch protection in GitHub

Finally, let's make sure nobody can accidentally merge a pull request that does not pass automated and manual checks.

First, make sure that your latest commit has finished building on Cirrus CI after your last push, and that it builds correctly.

In your GitHub repository, go to `Settings`, then `Branches`, then click on the `Add branch protection rule` button, and:
- Write the name of your main branch
- Enable `Require pull request reviews before merging` with `1` approval
- Enable `Require status checks to pass before merging`, and check `Run Android tests`
- Enable `Do not allow bypassing the above settings`, to ensure all team members have the same constraints

Congrats, you're done!
