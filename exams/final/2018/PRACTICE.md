# CS305 – Software Engineering

## Final Exam

# Part 2: Practice [70 points]

In this part of the exam you need to write real code.
As is often the case in software engineering, the problem itself is fairly easy to reason about, and what really matters is that your code employs solid software engineering principles:

- Write correct and rock-solid code
- Follow the SwEng coding principles
- Write readable and concise code
- Use comments judiciously

To build and run tests, use:

- `gradlew.bat check` on Windows
- `./gradlew check` on Mac and Linux

After running that command, you can find coverage results in `build/reports/jacoco/test/html/index.html`.
When referring to coverage in this exam, we mean **statement coverage**, which JaCoCo calls "instruction coverage".
You are free to use an editor or IDE of your choice, as long as the code and tests build using the above command.

**If you get an 'Could not generate test report' error**, try running the command again, this is a known issues that happens rarely.

Please remember that we will only grade whatever is in your `master` branch, using the Gradle command mentioned above to build and test your code (but with our own test suite).

We provide you with continuous integration at [travis-ci.com](https://travis-ci.com/). It automatically retrieves the code from your `master` branch.
You may need to synchronize if you don't see your repository on Travis: click on your avatar in the top-right of the page, then on the "Sync account" button near the top of the menu on the left.

**If you forget to commit a file, or if you push the wrong version or a version that breaks your previously working version, you may lose all points, so please commit your work regularly and check Travis after every push.**

---

#### Common Issues on Android Studio *(skip this section if you don't use Android Studio)*

1. If Android Studio fails to recognize the `final` folder as containing a module, double click on it in the project view, go to the "Sources" tab, and click on "+ Add Content Root" and "OK" and "OK" to let the studio find the final module.
2. If Android Studio does not list the files in the 'Project' view, open the Gradle view (View > Tool Windows > Gradle), click on the 'Refresh all Gradle projects' button (top-left of the pane).
3. If Android Studio cannot find JUnit, use the same workaround as 2.
4. If running the tests in Android Studio fails with no tests found, open the Gradle view (View > Tool Windows > Gradle), click on the 'Execute Gradle Task' button (with the Gradle logo), and run the 'check' task. This will fail, but once it finishes running the tests through a class' right-click menu in the Project view will work.
5. If running the tests a second time does not seem to take your edits into account, use the same workaround as 4.

---

## Preamble
Tired of the monopoly of StackOverflow, you and your friends launched a startup to develop _Segmentation Fault_, a question-and-answer service.

Your team has already started working on the codebase, and wrote documentation for what each class should do; each question introduces the relevant parts.

 
## Question 1: Basics [30 points]
Your friends have written code in the [`Forum`](src/main/java/ch/epfl/sweng/Forum.java) class to let users post questions and answers, as well as edit posts.
Questions and answers are modeled with the [`Question`](src/main/java/ch/epfl/sweng/Question.java) and [`Answer`](src/main/java/ch/epfl/sweng/Answer.java) classes respectively, both of which are a kind of [`Post`](src/main/java/ch/epfl/sweng/Post.java).
Users are modeled with the [`User`](src/main/java/ch/epfl/sweng/User.java) class, which includes permission control: not all users can do everything.  

However, the functionality of Forum is currently bare-bones: it does not perform any argument checking, nor any permission checking.
Furthermore, the standard users, modeled with the [`StandardUser`](src/main/java/ch/epfl/sweng/StandardUser.java) class, have not been fully implemented.
Your colleagues have already written some tests, and those tests currently fail.

**Your task**:
Finish the implementation of [`Forum`](src/main/java/ch/epfl/sweng/Forum.java) such that exceptions are thrown exactly according to the documentation.
Finish the implementation of [`StandardUser`](src/main/java/ch/epfl/sweng/StandardUser.java) as per its documentation.
Write tests that achieve 100% coverage on both classes.

**Grading**:
- 10 points for the correctness of your code
- 5 points for the cleanliness of your code
- 5 points for code coverage of the `Forum` class: 0 points for 70% coverage or less, 2 points for 80% coverage, 3 points for 90% coverage, and 5 points for 100% coverage.
- 10 points for code coverage of the `StandardUser` class: 0 points for 40% coverage or less, 2 points for 60% coverage, 5 points for 80%, 7 points for 90%, and 10 points for 100%.


## Question 2: Leaderboard [20 points]
To encourage users to use your platform, you have decided to include some gamification.
This gamification is represented by the [`Leaderboard`](src/main/java/ch/epfl/sweng/Leaderboard.java) class.

The goal of the leaderboard is to have a textual representation of the current highest-performing users.
The leaderboard must update automatically whenever anything happens on its associated forum, without additional method calls.

Here is an example:

```java
Forum forum = new Forum();
Leaderboard board = new Leaderboard(forum);

User user = new StandardUser("Alice");
forum.postQuestion(user, "Who wants to go to Sat after the exam?");

// This call will return the leaderboard textual representation, where Alice earned points for asking a question.
String result = board.toString();
```

You may assume that the `Forum` is empty when the `Leaderboard` is created.

**Your task**:
Implement the [`Leaderboard`](src/main/java/ch/epfl/sweng/Leaderboard.java) as per its class/methods documentation.
This will require you to use the _Observer_ design pattern; thankfully, your colleagues have already written [`Observable`](src/main/java/ch/epfl/sweng/Observable.java) and [`Observer`](src/main/java/ch/epfl/sweng/Observer.java) interfaces. To keep it clean, you **are not allowed to** use the `Leaderboard` class from the `Forum` class.
Write tests that achieve 100% coverage on the [`Leaderboard`](src/main/java/ch/epfl/sweng/Leaderboard.java) class.


**Grading**:
- 10 points for the correctness of your code
- 5 points for the cleanliness of your code
- 5 points for code coverage: 2 points for 40% coverage, 4 points for 80%, and 5 points for 100%.


## Question 3: User Types [20 points]
You now need to introduce two additional user types in your platform:
- Moderators have extra power compared to standard users.
- Limited users are prevented from using a specific word due to a history of spam.

Since user types must be flexible, your team has decided to use the _Decorator_ design pattern.
This way, it is possible to create not only moderators and limited users, but also for instance limited moderators, who have extended powers but are still prevented from using the banned word.

**Your task**:
Implement the [`Moderator`](src/main/java/ch/epfl/sweng/Moderator.java) and [`LimitedUser`](src/main/java/ch/epfl/sweng/LimitedUser.java) classes according to their documentation, using the _Decorator_ design pattern.
Write tests to achieve 100% coverage on both classes.

**Grading**:
- 10 points for the correctness of your code
- 5 points for the cleanliness of your code
- 5 points for code coverage: 2 points for 40% coverage, 4 points for 80%, and 5 points for 100%.
