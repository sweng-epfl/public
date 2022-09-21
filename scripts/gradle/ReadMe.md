This script enables automated updating of all Gradle build files for all exercises and exams.
It is itself a Gradle script.

`build.gradle.template` is the common Gradle build file that is copied as `build.gradle`, with the following features:
- `build` task that runs tests with JUnit 5 and code coverage with Jacoco
- `run` task that runs the main method of the class named `App`
- Tests are listed on the command line, including successful tests and command-line output
- Tests can also use Hamcrest
- If a `build-extra.gradle` file exists, it is imported


# Updating

Run `./gradlew wrapper --gradle-version X.Y.Z` **twice**, where `X.Y.Z` is the Gradle version.
The first run will update the wrapper configuration, the second will use the new Gradle to regenerate the wrapper

Modify `build.gradle.template` as you wish.

Then run `./gradlew update`.


# Example `build-extra.gradle`

To add a plugin:

```
// This is the only way to apply a plugin from an extra file:
// using buildscript and apply plugin, and the full name of the class file without quotes

buildscript {
  repositories {
    gradlePluginPortal()
  }
  dependencies {
    classpath("me.champeau.jmh:jmh-gradle-plugin:0.6.7")
  }
}
apply plugin: me.champeau.jmh.JMHPlugin
```
