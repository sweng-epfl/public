# Software Engineering - Final Exam

## Part 2: Practice [60 points]

In this part of the exam you need to write real code.
As is often the case in software engineering, the problem itself is fairly easy to reason about, and what really matters is that your code employs solid software engineering principles.

You should write code that is:

- Correct and robust
- Readable and concise
- Judiciously commented

This exam uses Java 11.
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

Every time you push a version of your solution to the `master` branch, GitHub Actions will attempt a build and run some simple smoke tests.
Please check the `Actions` tab in the web interface to your repo (_Build and run checks_ step of the workflow) to make sure it's OK.
If your code does not build or pass the smoke tests, it will certainly not pass our tests, and you could lose a lot or all of the points for this part of the exam.

## Introduction

You are developing an application to help the EPFL administration keep track of students that are currently registered in the school.

You plan on using a database that can help you store a large number of students and that can retrieve them by using their unique SCIPER identifier.

Unfortunately, the EPFL administration does not have the infrastructure required to host such a big database. So you decide to buy a cloud hosted database from Ibizon that advertises very high reliability and performance.

However, the downside is that Ibizon's databases can only store string key/value pairs, so the first step is to make it compatible with your current database implementation.

## Part 2.1 [16 points]

Your first task is to adapt Ibizon's database (defined by the [`Database`](src/main/java/ch/epfl/sweng/database/Database.java) interface) so that it can store [`Student`](src/main/java/ch/epfl/sweng/Student.java) objects.
Implement the [`StudentDatabaseAdapter`](src/main/java/ch/epfl/sweng/StudentDatabaseAdapter.java) class according to [its interface documentation](src/main/java/ch/epfl/sweng/StudentDatabase.java).

> :information_source: INFO: You are free to serialize the `Student` class using any method you want, however make sure that it supports any kind of input (i.e., any character in the student's attributes).

## Part 2.2 [18 points]

Now that you are able to store students on Ibizon's database, your next task is to write your application. For this, you choose to implement the *Model View Controller (MVC)* pattern.

The [Model](src/main/java/ch/epfl/sweng/mvc/AppModel.java) and the [View](src/main/java/ch/epfl/sweng/mvc/AppView.java) have already been implemented for you. Use these two components to implement
the [`AppController`](src/main/java/ch/epfl/sweng/mvc/AppController.java) according to its documentation.

Once you've completed this task, you should have a complete student database application. You can test it by running the `main` function of the [`App`](src/main/java/ch/epfl/sweng/App.java) class.
Try adding a student in the database and try retrieving it using the student's SCIPER.

As you can see, something is terribly wrong. The `get` command fails almost half of the time with an ominous *"Database failed to retrieve student"* message. And even when the database succeeds in retrieving
the student, it takes a whole 2 seconds.

> :information_source: HINT: You can have a look at the [`UnreliableDatabase`](src/main/java/ch/epfl/sweng/database/UnreliableDatabase.java) implementation to understand the source of these problems.

> :warning: Make sure that, regardless of these issues, you are still able to transfer students to and from the database. The `get` command should only fail about half of the time. If you get other
> error messages, this means that your code is incorrect.

Keep calm and carry on.

## Part 2.3 [12 points]

You don't give up, so you take matters into your own hands. You remember that you learned about the Decorator pattern in your SwEng course and decide to use it to improve the reliability of Ibizon's database.

Implement the [`RetryDatabase`](src/main/java/ch/epfl/sweng/database/RetryDatabase.java) according to its documentation.

You can now modify the `main` function of the `App` so that it uses your new decorator. Try the same manipulation as before (adding and retrieving a student).

Now the application does not fail with the *"Database failed to retrieve student"* message anymore but the `get` command still take a ridiculous amount of time to execute and you cannot have that.
Seeing how well the decorator has helped solving the first issue, you decide to go even further.

## Part 2.4 [14 points]

You decide to implement a cache that would bypass the database if a user calls the `get` command for the same student multiple times.

Implement the [`CachedDatabase`](src/main/java/ch/epfl/sweng/database/CachedDatabase.java) according to its documentation.

You can now modify the `main` function of the `App` so that it uses both of your new decorators. Try the same action you tried before, namely adding and retrieving a student.

Now the application feels much better. The first `get` command is still a bit slow, but subsequent calls are almost instantaneous. The EPFL administration will definitely be happy about your work.
