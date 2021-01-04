# Software Engineering - Homework 3

## Part 2: Practice [80 points]

In this part of the homework you need to write real code.
As is often the case in software engineering, the problem itself is fairly easy to reason about,
and what really matters is that your code employs solid software engineering principles.

You should write code that is:

- Correct and robust
- Readable and concise
- Judiciously commented
- Well tested, aiming for 100% statement coverage

This homework uses Java 11.
To validate your installation, you can run `java -version` from the command line; the output should start with something like `java version "11.0.8"`.
The version number need not be identical, but it must start with `11`.

> :information_source: If you do not have Java 11, you can get it from [https://jdk.java.net/archive/](https://jdk.java.net/archive/). More recent versions may not be compatible.

You can build locally with `gradlew.bat build` on Windows or `./gradlew build` on Mac and Linux, which is also what we will use to build and test your code.
You may not modify the Gradle setup. For instance, you may not add new dependencies, or change the versions of existing dependencies.
You are free to use an editor or IDE of your choice, as long as the code and tests build using the `./gradlew build` command line.
We recommend Android Studio, which can import this project as a Gradle project.

Please remember that we will only grade whatever is in your `master` branch.

**Do not modify in any way the public interface of the code that is given to you**, not even to add checked exceptions.
We will use automated tests when grading; if we cannot build and run your code, you will receive 0 points.
You may add private methods and constructors.

Your code should fail cleanly when it cannot continue safely, and it should throw adequately specific runtime exceptions (or custom versions of these):

- When implementing known interfaces, follow their prescribed exceptions
- Throw `IndexOutOfBoundsException` for any kind of index whose value is too low or too high
- Throw `IllegalArgumentException` for arguments (other than indexes) whose value is invalid
- Throw `IllegalStateException` for operations that fail because of an object's current state


**Remember to check the `Github Actions` tab on your repository on Github during the exam. If your code does not compile there, it will not compile with our tests and you could lose a lot or all of the points! If your code does not compile on `Github Actions` you should fix it immediately.**

## Introduction

You are working in a start-up founded by EPFL students that decided to help the school setting up a booking system for the campus. Indeed, with the pandemic situation, everyone cannot come at once on campus!

Your product is called EPFBooking and will manage the bookings that students will be able to make.

A maximum of 5 **different** students, represented by their SCIPER number, can book the same date. Each student can book at most **one** spot per date.

As every components of the system will be spread over the network, the implementation is fully asynchronous. You and your team decided to use `callbacks` to achieve asynchrony.

You can run the main [`App`](src/main/java/homework03/App.java) if you want to test your CLI. To run it, you must use Android Studio or IntelliJ IDEA.

For this part, we strongly encourage you to write tests but the coverage of your tests **will not** be graded.

## Part 2.1 [17 points]

Your first task is to implement the [`DatabaseImpl`](src/main/java/homework03/DatabaseImpl.java)'s methods according to [its interface documentation](src/main/java/homework03/util/Database.java).

## Part 2.2 [22 points]

You can now implement the [`ServerImpl`](src/main/java/homework03/ServerImpl.java)'s methods according to [its interface documentation](src/main/java/homework03/util/Server.java), as well as its constructor according to its documentation.

## Part 2.3 [16 points]

You can now implement the [`ClientImpl`](src/main/java/homework03/ClientImpl.java)'s methods according to [its interface documentation](src/main/java/homework03/util/Client.java), as well as its constructor according to its documentation.

## Part 2.4 [25 points]

Everything you need to implement the [`CLIImpl`](src/main/java/homework03/CLIImpl.java) is now ready. Implement its methods according to [its interface documentation](src/main/java/homework03/util/CLI.java), as well as its constructor according to its documentation.

> :information_source: HINT: Do not forget regular expressions and `java.text.DateFormat`.
