# CS305 – Software Engineering

## Final Exam

---

# Part 1: Practice [75 points]

## General Information

This part of the exam requires you to write code. As is often the case in software engineering, the problem itself is fairly easy to reason about; what really matters is that your code employs solid engineering principles:

- Write correct and rock-solid code;
- Follow the SwEng coding principles (write readable and concise code, use comments judiciously, etc.);
- Do not change the public interface of the provided classes:
  - Do not change the names of existing classes or methods
  - Do not change the parameter lists and return types of existing methods
  - Do not add or remove checked exceptions in existing methods
  - You may add new classes or new methods if you wish

This practical part of the exam contains three sub-parts that are self-contained tasks and thus may be done in any order. It is not necessary to understand the provided codebase in its absolute entirety before starting your work, so in the interest of time you may want to directly dive into the tasks.

This exam was designed to be imported in Android Studio as a Gradle project, for your convenience, but you are free to use any editor or IDE of your choice. All we care about is that the code and tests build using `./gradlew check` (or `gradlew.bat check` on Windows). You can run `./gradlew check` locally, after which you can find coverage results in `build/reports/coverage/index.html`.

However, if you use an IDE such as Android Studio, make sure you verify the `import` statements that are automatically added as you type. If you import classes you did not mean to, such as those in the `com.sun` namespace, `./gradlew build` may fail on other machines such as Jenkins, because these classes are not guaranteed to exist in all versions of Java.

We provide you with continuous integration on [Jenkins](https://jenkins.epfl.ch/sweng) during the exam on a best-effort basis. No need to create a pull request, Jenkins will automatically retrieve the code from your `master` branch periodically. You can use Jenkins to validate that the master branch in your repo builds and runs as expected. Remember that if you forget to commit a file, or if you push the wrong version or a version that breaks your previously working version, you may lose all points. Please be extra careful, and also check Jenkins after every push (if Jenkins is happy, there is a good chance you've pushed the right files).

We will use automated tests when grading. If we cannot build and run your code, you will receive 0 points. We will only grade the code in the `master` branch in your repo.

---

You've just been hired in a fancy new startup: "Poodle, Inc". Its product, Poodle, is a website for teachers to provide materials to their students. The startup is in its early stages, so there is a lot of work left to be done.

Poodle's current code is simple:

- The [`User`](src/main/java/ch/epfl/sweng/User.java) class models users (students or teachers).
  - A [`User`](src/main/java/ch/epfl/sweng/User.java) has a `name`.
- The [`Course`](src/main/java/ch/epfl/sweng/Course.java) class models courses. You can assume that a user will never be both teaching and attending a course.
  - A [`Course`](src/main/java/ch/epfl/sweng/Course.java) has a `name`, a list of `teachers`, and a list of `students`.
- All methods must throw an `IllegalArgumentException` when any of their arguments is `null`.
- Some of the methods currently throw an `UnsupportedOperationException` as they have not been implemented yet.


## Part 1.1 Homepage [30 points]
  
Poodle, Inc. determined that users want to see the following on the homepage:

- Start by greeting the user by name: `Hello, <NAME>!`
- The courses the user teaches come first, sorted alphabetically in ascending order. 
  Each course is displayed by printing its name, preceded by a dash `-` and a space.
- The courses the user attends come second, with the same sorting and formatting as above.
- Each list is preceded by a title: `Courses you teach` and `Courses you attend`, respectively.
- If the lists are empty, they should be replaced by the texts `You are not teaching any courses.` and `You are not attending any courses.`, and the titles should not be displayed.
- The page text should end with a new line.

As an example, if `Alice` was teaching `Software Engineering` and attending `Intro to Jenkins`, her homepage would look like this:

```
Hello, Alice!
Courses you teach
- Software Engineering
Courses you attend
- Intro to Jenkins

```

I.M. Badd, a consultant for the startup, implemented the [`Homepage`](src/main/java/ch/epfl/sweng/Homepage.java) class' single method `String print(User, List<Course>)`. But users are not satisfied with how it works, and furthermore there are no unit tests.

Your tasks are to:

- Write unit tests to provide 100% line coverage of the [`Homepage`](src/main/java/ch/epfl/sweng/Homepage.java) class. **[15 points]**
- Fix all bugs in the [`Homepage`](src/main/java/ch/epfl/sweng/Homepage.java)  class so that it fully satisfies the above specification, and the tests provide 100% line coverage. **[15 points]**


## Part 1.2: Integration with IS-Papademia [30 points]

Poodle's killer feature is integration with an existing legacy system, IS-Papademia. Students and teachers enroll themselves in courses via IS-Papademia.

Unfortunately for Poodle, IS-Papademia's models are not directly compatible with the [`User`](src/main/java/ch/epfl/sweng/User.java) and [`Course`](src/main/java/ch/epfl/sweng/Course.java) classes:

- [`PapademiaUser`](src/main/java/ch/epfl/sweng/PapademiaUser.java) has a `name`, a list of `taughtCourses` and a list of `attendedCourses`. Here too, you can assume that a user will never be both teaching and attending a course.
- [`PapademiaCourse`](src/main/java/ch/epfl/sweng/PapademiaCourse.java) has a `name`.

In addition, IS-Papademia sometimes has `null` values in the lists of users and courses. These values must be ignored, i.e., treated as if they did not exist.

Aside from this, you can assume that the data from IS-Papademia is correct, i.e., there are no invalid usernames and there is no data that is nonsensical.

Your tasks are to:

- Implement [`PapademiaAdapter`](src/main/java/ch/epfl/sweng/PapademiaAdapter.java) by filling in the `getUsers` and `getCourses` methods. **[15 points]**
- Write tests for [`PapademiaAdapter`](src/main/java/ch/epfl/sweng/PapademiaAdapter.java) that achieve 100% line coverage. **[15 points]**


## Part 1.3: Course quizzes [15 points]

Course quizzes, which test students' knowledge, are displayed by the `CourseQuizFormatter` interface, which is currently not implemented.

Depending on how a user is related to a course, they see quizzes in different ways.

[`CourseQuizFormatter`](src/main/java/ch/epfl/sweng/CourseQuizFormatter.java) has the following methods:

- `String getGreeting()` returns a greeting depending on the user:
  - If the user is teaching the course: `Hello, Prof.` followed by a space, then the user's name, then an exclamation mark `!` (e.g., `Hello, Prof. Alice!`).
  - If the user is attending the course: `Hello,` followed by a space, then the user's name, then an exclamation mark `!` (e.g., `Hello, Alice!`).
  - Otherwise, the text `Hello!`.
- `String getQuestionText(String statement, String answer)` returns a question description in the following way:
  - If the user is teaching the course: the `statement`, followed by a space, followed by the `answer`.
  - If the user is attending the course: the `statement` only.
  - Otherwise, the text `You cannot see this question.`.


Your task is to:

- Implement the `CourseQuizFormatter getQuizFormatter(User)` method in the [`Course`](src/main/java/ch/epfl/sweng/Course.java) class using the Factory design pattern. **[15 points]**
  - To get full credit for your Factory implementation, make sure none of the `CourseQuizFormatter` implementations contain any conditions (such as `if` statements).

We strongly recommend that you write basic tests to check that your implementation is correct, but there are no points assigned for testing in this part (1.3) of the exam.


## Grading

Your grade on this practical part of the exam has three components: correctness, testing, and code quality. Correctness and testing have their assigned points indicated next to each task. We have automated tests to check the correctness of your code, which will automatically compute the number of points you get in this category; it is therefore crucial that your code compiles and runs properly. We will subtract points for code that does not obey the SwEng quality standards we taught you. 
