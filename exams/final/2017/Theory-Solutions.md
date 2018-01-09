# Software Engineering

## Final Exam

---

# Part 2: Theory [25 points]

This second part of the exam has 10 questions, with the number of points indicated next to each one.

This file is named `Theory.md`. Provide the answers in this file and commit the file to the `master` branch of this `final-GASPAR` repo.
Do not change the name of the file, and do not change the existing text unless the question statements asks you to do so.
We will only grade the `master` branch of your repo. Do not introduce extraneous spaces, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`. You should change each one to `[y]` for each correct answer, or to `[n]` for each incorrect answer.
This requires that you replace the space between the brackets with either `y` or `n`.
Unless otherwise specified, questions may have zero, one, or more correct answer choices.
If you leave a checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question and you will get 0 points for it.


## Questions

### Question 1 [2 points]

Which of the following variable names adhere to SwEng good practices for variable naming in Java?

- [n] `String Name`
- [n] `boolean NotAvailable`
- [y] `int numberOfStudents`
- [n] `int nunber`
- [y] `List<Integer> examScores`
- [n] `long q`

**Explanation for students**: `Name` and `NotAvailable` do not comply to the _camelCase_ notation in Java, which mandates that the first letter of variables and members should be lowercase.
`nunber` has a typo, which makes it confusing to the reader and thus disqualifies it. `q` is too short and non-descriptive; it could be good in specific contexts such as math formulas, but is a poor choice overall.
`numberOfStudents` and `examScores` are good, descriptive names.


### Question 2 [2 points]

You have worked for five weeks on a Scrum-managed project in 1-week long sprints.
After these five sprints, you realize you need to refactor a big portion of your code due to unforeseen complications with your initial database design.
Within the Scrum framework, how would a competent Scrum master communicate the situation to the Product Owner?

- [n] "We need to refactor because our database design does not comply with best practices."
- [y] "If we refactor now, future features using the database will take 50% less time to implement."
- [y] "If we don't refactor now, some required features will be impossible to implement."
- [n] "The development team doesn't remember what the code does anymore."

**Explanation for students**: The 1st and 4th options are not something a Scrum master should say to the PO, because customers do not care about internal development issues. The 1st statement has to do with implementation details, and basically says that previous work done by the team was of poor quality. The 4th statement has to do with team internals, and basically states that the development team has serious problems.
The 2nd option is best, because it phrases the request in a way that is clearly understandable and relevant to the PO, with a well-defined metric.
The 3rd option is OK too, because it justifies with a customer-centric benefit, but is not as good as the 2nd option due to its lack of metrics.


### Question 3 [2 points]

In Scrum, who estimates the amount of time it takes to implement a user story? (only one correct answer)

- [n] The Scrum Master
- [y] The Development Team
- [n] The Product Owner
- [n] George Candea

**Explanation for students**: Time estimates can only be made by the team that will implement the features (they know the codebase best, they know their own capabilities, etc.) George Candea may know a lot of stuff, but the 4th option is still wrong, and he knows it.


### Question 4 [2 points]

Which of the following constitute code smells?

- [n] Code that has not been merged properly into the `master` branch.
- [n] Code that breaks the build.
- [y] Identical code that exists in multiple locations in the codebase.
- [y] A class that uses exclusively another class' methods.

**Explanation for students**: Code smells indicate potential problems in the code itself, not its location in version control (1st answer) or with the way it builds (2nd answer).
The 3rd and 4th answers are clear code smells, as described in lecture: they are not fatal issues, but may lead to maintainability problems as the codebase evolves.


### Question 5 [2 points]

When you start a new project, which coding convention should you use? (only one correct answer)

- [n] Google's coding convention, because it's tried and true.
- [n] You don't have to decide right at the beginning on a coding convention.
- [y] You should agree beforehand, as a team, what coding convention you will adopt.
- [n] Each team member should write code using their preferred convention.

**Explanation for students**: The 2nd and 4th choices would lead to inconsistent code formatting, which defeats the whole point of a coding convention, they are thus incorrect. The 3rd answer is correct, and your team can design their own coding convention or pick someone else's (e.g., Google's). We also accepted the 1st answer as correct for students who marked it as such, even though the reason for picking that convention is not entirely sound (e.g., your team may be writing code in a language for which Google does not have a coding convention) and the 3rd choice was more generally applicable.


### Question 6 [2 points]

Why is Waterfall not recommended for most new software projects?

- [n] Designing code is harder because there are too many requirements.
- [y] Validating requirements is harder because clients are not involved in the process.
- [n] Writing code is harder because the team has to settle on technologies at the beginning.
- [y] Fixing bugs is harder because testing comes only after implementation is fully done.

**Explanation for students**: The 1st answer is incorrect, because the number of requirements is dictated by the customer, not by the development method (plus, if requirements are stated upfront, it is actually easier to design the code).
The 2nd answer is correct: _validation_ is about determining whether you're building _the right thing_, and only the customer can decide whether the product is doing the right thing -- by reducing customer involvement (as Waterfall does), you risk correctly building the _wrong_ thing and making the customer unhappy.
The 3rd answer is incorrect, because Waterfall does not require that all technologies be chosen before writing the code.
The 4th answer is correct: as seen in lecture, it is much easier and better to fix bugs immediately after (or ideally while) writing small parts of the code, instead of waiting until all the code has been written.


### Question 7 [3 points]

If the `Fondue` class inherits from the `Meal` class, is a `List<Fondue>` a `List<Meal>`? (there is only one correct answer)

- [n] Yes.
- [n] No, because its `get` method returns a `Fondue` instead of a `Meal`.
- [y] No, because its `add` method requires a `Fondue` instead of a `Meal`.
- [n] No, because its internal representation is different.

**Explanation for students**: If we say that a `List<Fondue>` _is a_ `List<Meal>`, it means that any code that uses `List<Meal>` works without modification if we replace the `List<Meal>` with a `List<Fondue>`. This is ok for the `get` method (it would return a `Fondue` which is indeed a `Meal`), but it's not ok for the `add` method: the code would pass a `Meal` to a method that is expecting a `Fondue`, but unfortunately a `Meal` is not a `Fondue`, so the `add` call would fail. This is why the Java compiler rejects treating a `List<Fondue>` as a `List<Meal>`. This makes the 3rd answer correct and the 1st and 2nd wrong. The 4th answer is wrong because the internal representation is irrelevant for this question.

### Question 8 [3 points]

One of your colleagues decided to refactor a code smell, and transformed the following code:

```java
public static int getMostValuableMemberId(List<Member> members) {
  int result = 0;
  int value = 0;
  int min = 0;
  for (Member m : members) {
    for (Transaction t : member.transactions) {
      value -= t.value;
    }
    if (min < value) {
      continue;
    } else {
      min = value;
      result = m.id;
    }
    value = 0;
  }
  return result;
}
```

into the following better version:

```java
public static int getMostValuableMemberId(List<Member> members) {
  int resultId = 0;
  int currentMax = 0;
  for (Member m : members) {
    int sum = sumTransactions(member);
      if (currentMax < sum) {
        currentMax = sum;
        resultId = member.id;
      }
    }
  }
  return resultId;
}

private static int sumTransactions(Member member) {
  int sum = 0;
  for (Transaction transaction : member.transactions) {
    sum = sum + transaction.value;
  }
  return sum;
}
```

Indicate below which refactoring technique(s) your colleague used?

- [n] Inline method
- [n] Extract class
- [y] Extract method
- [y] Substitute algorithm

**Explanation for students**: No method was inlined, thus the 1st answer is incorrect; in fact, the opposite happened, a method was extracted, thus the 3rd answer is correct.
No class was extracted, thus the 2nd answer is incorrect (the code does even mention a class).
The algorithm to pick the most valuable member was changed and improved, thus the 4th answer is correct.


### Question 9 [3 points]

Below are multiple code snippets annotated with preconditions, postconditions, and/or invariants in the CoFoJa framework mentioned in  lecture. Indicate which snippet(s) is/are valid and accomplish(es) **both** of the following:

- the `score` field is never negative (assume there are no overflows) _and_
- the `points` that get added to `score` are always positive?

- [y] Option 1

```java
@Invariant("score >= 0;")
public class ScoreKeeper {
   private int score = 0;

   @Requires("points > 0")
   public void addPoints(int points) {
      this.score += points;
  }
}
```

- [n] Option 2

```java
@Invariant("score >= 0;")
public class ScoreKeeper {
   private int score = 0;

   @Ensures("points > 0")
   public void addPoints(int points) {
      this.score += points;
  }
}
```

- [n] Option 3

```java
public class ScoreKeeper {
   private int score;

   public ScoreKeeper(int initialScore) {
      this.score = initialScore;
   }

   @Requires("points > 0")
   @Ensures("score > 0")
   public void addPoints(int points) {
      this.score += points;
  }
}
```

- [y] Option 4

```java
public class ScoreKeeper {
   private int score = 0;

   @Requires("points > 0")
   public void addPoints(int points) {
      this.score += points;
  }
}
```

**Explanation for students**: The 1st answer is correct, both due to the code and the statement of the invariant; the 4th answer is the same but without the invariant stated explicitly, and it's correct as well.
The 2nd answer does not make sense, because a contract cannot `ensure` the value of an incoming stack parameter after the method completes execution.
The 3rd answer is incorrect, because the code doesn't check the value of `initialScore`, so it could be a negative number and thus break the requirement that `score` always be non-negative.


### Question 10 [4 points]

Given the following code:

```java
while(true) {
   int temperature = getTemperature();

   if (temperature < 0) {
      System.out.println("Let it snow!");

      if (temperature < -100) {
         System.out.println("...Whoa!");
     }
   }
}
```

Say you are tasked with writing tests for this code. What is the maximum path coverage you can hope to obtain?

- [y] 0 ≤ path coverage < 1%
- [n] 1% ≤ path coverage < 25%
- [n] 25% ≤ path coverage < 50%
- [n] 50% ≤ path coverage ≤ 100%

**Explanation for students**: The `while(true)` loop is infinite, so there is a countably infinite number of possible execution paths: at least 3 for the first iteration, at least 3 for the second iteration, and so on. Your tests can execute a finite number `N` of iterations of the loop, and no matter how large `N` is, there will still be `∞ - N` paths left uncovered. Hence, achieved path coverage is 0% after any finite test execution time.
