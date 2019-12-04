# CS305 – Software Engineering

## Mock Final Exam

# Part 2: Practice [75 points]

In this part of the exam you need to write real code. As is often the case in software engineering, the problem itself is fairly easy to reason about, and what really matters is that your code employs solid software engineering principles:

- Write correct and rock-solid code
- Follow the SwEng coding principles
- Write readable and concise code
- Use comments judiciously

You should use the same development environment as for the [bootcamp](https://github.com/sweng-epfl/public/tree/master/bootcamp). To validate your installation, you can do a `java -version` from the command line. You are ready to go as soon as the command outputs something like this:

```
java version "1.8.0_144"
Java(TM) SE Runtime Environment (build 1.8.0_144-b01)
Java HotSpot(TM) 64-Bit Server VM (build 25.144-b01, mixed mode)
```
The version number need not be identical to the one above, but it must start with `1.8`.

You can run locally `./gradlew test` and `./gradlew jacoco`, after which you can find coverage results in `mock-final/build/reports/jacoco/test/html/index.html`. You are free to use an editor or IDE of your choice, as long as the code and tests build using `./gradlew build`. On Windows, use `gradlew.bat` instead of `./gradlew`.

Please remember that we will only grade whatever is in your `master` branch. We will use `./gradlew build` to build your code.

We provide you with continuous integration at [travis-ci.com](https://travis-ci.com/). It automatically retrieves the code from your `master` branch. You may need to synchronize your GitHub account if you don't see your repository on Travis. Please commit your work regularly and check Travis after every push. We will grade the last version that builds without errors on Travis.

---
#### Some tips for Android Studio users *(skip this section if you don't use Android Studio)*
- If Android Studio fails to recognize the `mock-final` folder as containing a module, double click on it in the project view, go to the "Sources" tab, and click on "+ Add Content Root" and "OK" and "OK" to let the studio find the mock final module.
- If Android Studio does not list the files in the 'Project' view, click on the 'Sync Project with Gradle Files' button (top-right, left of the Android Devices button).
- If running the tests in Android Studio fails with no tests found, open the Gradle view (View > Tool Windows > Gradle), click on the 'Execute Gradle Task' button (with the Gradle logo), and run the 'check' task. This will fail, but once it finishes running the tests through a class' right-click menu in the Project view will work.
- If Android Studio cannot find the `junit` package, click on the 'Sync Project with Gradle Files' button (top-right, left of the Android Devices button).

---

This practical part of the exam has 3 questions with the number of points indicated next to each one. The preamble section introduces the setting of the problem. 

## Preamble

You've just been hired in a fancy new startup: "Poodle, Inc". Its product, Poodle, is a website for teachers to provide materials to their students. The startup is in its early stages, so there is a lot of work left to be done.

Poodle's current code is simple:

- The [`User`](mock-final/src/main/java/ch/epfl/sweng/User.java) class models users (students or teachers).
  - A [`User`](mock-final/src/main/java/ch/epfl/sweng/User.java) has a `name`.
- The [`Course`](mock-final/src/main/java/ch/epfl/sweng/Course.java) class models courses. You can assume that a user will never be both teaching and attending a course.
  - A [`Course`](mock-final/src/main/java/ch/epfl/sweng/Course.java) has a `name`, a list of `teachers`, and a list of `students`.
- All methods must throw an `IllegalArgumentException` when any of their arguments is `null`.
- Some of the methods currently throw an `UnsupportedOperationException` as they have not been implemented yet.


## Question 1: Homepage [30 points]
  
Poodle, Inc. determined that users want to see the following on the homepage:

- Start by greeting the user by name: `Hello, <NAME>!`
- The courses the user teaches come first, sorted alphabetically in ascending order. 
  Each course is displayed by printing its name, preceded by a dash `-` and a space.
- The courses the user attends come second, with the same sorting and formatting as above.
- Each list is preceded by a title: `Courses you teach` and `Courses you attend`, respectively.
- If the lists are empty, they should be replaced by the texts `You are not teaching any courses.` and `You are not attending any courses.`, and the titles should not be displayed.
- The page text should end with a new line.

As an example, if `Alice` was teaching `Software Engineering` and attending `Intro to Travis`, her homepage would look like this:

```
Hello, Alice!
Courses you teach
- Software Engineering
Courses you attend
- Intro to Travis

```

I.M. Badd, a consultant for the startup, implemented the [`Homepage`](mock-final/src/main/java/ch/epfl/sweng/Homepage.java) class' single method `String print(User, List<Course>)`. But users are not satisfied with how it works, and furthermore there are no unit tests.

Your tasks are to:

- Write unit tests to provide 100% line coverage of the [`Homepage`](mock-final/src/main/java/ch/epfl/sweng/Homepage.java) class. **[15 points]**
- Fix all bugs in the [`Homepage`](mock-final/src/main/java/ch/epfl/sweng/Homepage.java)  class so that it fully satisfies the above specification, and the tests provide 100% line coverage. **[15 points]**


## Question 2: Integration with IS-Papademia [30 points]

Poodle's killer feature is integration with an existing legacy system, IS-Papademia. Students and teachers enroll themselves in courses via IS-Papademia.

Unfortunately for Poodle, IS-Papademia's models are not directly compatible with the [`User`](mock-final/src/main/java/ch/epfl/sweng/User.java) and [`Course`](mock-final/src/main/java/ch/epfl/sweng/Course.java) classes:

- [`PapademiaUser`](mock-final/src/main/java/ch/epfl/sweng/PapademiaUser.java) has a `name`, a list of `taughtCourses` and a list of `attendedCourses`. Here too, you can assume that a user will never be both teaching and attending a course.
- [`PapademiaCourse`](mock-final/src/main/java/ch/epfl/sweng/PapademiaCourse.java) has a `name`.

In addition, IS-Papademia sometimes has `null` values in the lists of users and courses. These values must be ignored, i.e., treated as if they did not exist.

Aside from this, you can assume that the data from IS-Papademia is correct, i.e., there are no invalid usernames and there is no data that is nonsensical.

Your tasks are to:

- Implement [`PapademiaAdapter`](mock-final/src/main/java/ch/epfl/sweng/PapademiaAdapter.java) by filling in the `getUsers` and `getCourses` methods. **[15 points]**
- Write tests for [`PapademiaAdapter`](mock-final/src/main/java/ch/epfl/sweng/PapademiaAdapter.java) that achieve 100% line coverage. **[15 points]**


## Question 3: Course quizzes [15 points]

Course quizzes, which test students' knowledge, are displayed by the `CourseQuizFormatter` interface, which is currently not implemented.

Depending on how a user is related to a course, they see quizzes in different ways.

[`CourseQuizFormatter`](mock-final/src/main/java/ch/epfl/sweng/CourseQuizFormatter.java) has the following methods:

- `String getGreeting()` returns a greeting depending on the user:
  - If the user is teaching the course: `Hello, Prof.` followed by a space, then the user's name, then an exclamation mark `!` (e.g., `Hello, Prof. Alice!`).
  - If the user is attending the course: `Hello,` followed by a space, then the user's name, then an exclamation mark `!` (e.g., `Hello, Alice!`).
  - Otherwise, the text `Hello!`.
- `String getQuestionText(String statement, String answer)` returns a question description in the following way:
  - If the user is teaching the course: the `statement`, followed by a space, followed by the `answer`.
  - If the user is attending the course: the `statement` only.
  - Otherwise, the text `You cannot see this question.`.


Your task is to:

- Implement the `CourseQuizFormatter getQuizFormatter(User)` method in the [`Course`](mock-final/src/main/java/ch/epfl/sweng/Course.java) class using the Factory design pattern. **[15 points]**
  - To get full credit for your Factory implementation, make sure none of the `CourseQuizFormatter` implementations contain any conditions (such as `if` statements).

We strongly recommend that you write basic tests to check that your implementation is correct, but there are no points assigned for testing in this part (Question 3) of the exam.
