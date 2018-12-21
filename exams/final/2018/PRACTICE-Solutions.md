# CS305 – Software Engineering

## Final Exam Solutions

---

# Part 2: Practice [70 points]

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

**Solution**:

- [`Q1Tests.java`](src/test/java/ch/epfl/sweng/tests/graded/Q1Tests.java) contains tests that achieve 100% coverage.
- See [`Forum.java`](src/main/java/ch/epfl/sweng/Forum.java) and [`StandardUser.java`](src/main/java/ch/epfl/sweng/StandardUser.java) for one way to complete the implementation of those classes.


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

**Solution**:
- [`Q2Tests.java`](src/test/java/ch/epfl/sweng/tests/graded/Q1Tests.java) contains tests that achieve 100% coverage.
- See [`Leaderboard.java`](src/main/java/ch/epfl/sweng/Leaderboard.java) and [`Forum.java`](src/main/java/ch/epfl/sweng/Forum.java) for one way to implement the leaderboard with the Observable pattern.


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

**Solution**:

- [`Q3Tests.java`](src/test/java/ch/epfl/sweng/tests/graded/Q1Tests.java) contains tests that achieve 100% coverage.
- See [`Moderator.java`](src/main/java/ch/epfl/sweng/Moderator.java) and [`LimitedUser.java`](src/main/java/ch/epfl/sweng/LimitedUser.java) for one way to implement those classes using the Decorator pattern.
