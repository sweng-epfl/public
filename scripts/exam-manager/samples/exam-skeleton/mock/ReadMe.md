# Mock exam

This mock exam exists to test whether your local development environment is set up properly: you need the Java 17 development kit ("JDK").
The real exams will use the exact same setup.

You can compile, run tests, and run the main method using the Gradle build system.

No need to download Gradle manually, as you can use the Gradle "wrapper" that downloads the right version of Gradle for you:

```
# === Windows ===
# compile + run tests + get code coverage
gradlew.bat build
# compile + run the main method
gradlew.bat run

# === Linux / macOS (incl. Windows Subsystem for Linux) ===
# same comments as above
./gradlew build
./gradlew run
```

You can and should use your favorite development environment to do these exercises, for instance:
- In IntelliJ and Android Studio, select "Open" and pick this folder
- In Eclipse, use the File menu >> Import >> Gradle >> Existing Gradle Project, and pick this folder
