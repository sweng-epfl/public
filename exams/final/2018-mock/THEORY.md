# CS305 – Software Engineering
## Mock Final Exam

# Part 1: Theory [25 points]

This part of the mock final exam has 10 questions, with the number of points indicated next to each one.

This file is named `THEORY.md`. Provide the answers in this file and commit the file to the `master` branch of this `exams-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless the question's statement asks you to do so. We will only grade the `master` branch. Do not introduce extraneous spaces, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.  You should change each one to `[y]` for a correct answer and to `[n]` for each incorrect answer.  This requires that you replace the space between all the brackets with either `y` or `n`. Unless otherwise specified, questions may have zero, one, or more correct answer choices. If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all, and you will get 0 points for it.

### Question 1 [2 points]

Which of the following variable names adhere to SwEng good practices for variable naming in Java?

- [ ] `String Name`
- [ ] `boolean NotAvailable`
- [ ] `int numberOfStudents`
- [ ] `int nunber`
- [ ] `List<Integer> examScores`
- [ ] `long q`


### Question 2 [2 points]

You have worked for five weeks on a Scrum-managed project in 1-week long sprints.
After these five sprints, you realize you need to refactor a big portion of your code due to unforeseen complications with your initial database design.
Within the Scrum framework, how would a competent Scrum master communicate the situation to the Product Owner?

- [ ] "We need to refactor because our database design does not comply with best practices."
- [ ] "If we refactor now, future features using the database will take 50% less time to implement."
- [ ] "If we don't refactor now, some required features will be impossible to implement."
- [ ] "The development team doesn't remember what the code does anymore."


### Question 3 [2 points]

In Scrum, who estimates the amount of time it takes to implement a user story? (only one correct answer)

- [ ] The Scrum Master
- [ ] The Development Team
- [ ] The Product Owner
- [ ] George Candea


### Question 4 [2 points]

Which of the following constitute code smells?

- [ ] Code that has not been merged properly into the `master` branch.
- [ ] Code that breaks the build.
- [ ] Identical code that exists in multiple locations in the codebase.
- [ ] A class that uses exclusively another class' methods.


### Question 5 [2 points]

When you start a new project, which coding convention should you use? (only one correct answer)

- [ ] Google's coding convention, because it's tried and true.
- [ ] You don't have to decide right at the beginning on a coding convention.
- [ ] You should agree beforehand, as a team, what coding convention you will adopt.
- [ ] Each team member should write code using their preferred convention.


### Question 6 [2 points]

Why is Waterfall not recommended for most new software projects?

- [ ] Designing code is harder because there are too many requirements.
- [ ] Validating requirements is harder because clients are not involved in the process.
- [ ] Writing code is harder because the team has to settle on technologies at the beginning.
- [ ] Fixing bugs is harder because testing comes only after implementation is fully done.


### Question 7 [3 points]

If the `Fondue` class inherits from the `Meal` class, is a `List<Fondue>` a `List<Meal>`? (there is only one correct answer)

- [ ] Yes.
- [ ] No, because its `get` method returns a `Fondue` instead of a `Meal`.
- [ ] No, because its `add` method requires a `Fondue` instead of a `Meal`.
- [ ] No, because its internal representation is different.


### Question 8 [3 points]

One of your colleagues decided to refactor a code smell, and transformed the following code

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

- [ ] Inline method
- [ ] Extract class
- [ ] Extract method
- [ ] Substitute algorithm


### Question 9 [3 points]

Below are multiple code snippets annotated with preconditions, postconditions, and/or invariants in the CoFoJa framework mentioned in the lectures. Indicate which snippet(s) is/are valid and accomplish(es) both of the following:

- the `score` field is never negative (assume there are no overflows) _and_
- the `points` that get added to `score` are always positive?

- [ ] Option 1

```java
@Invariant("score >= 0;")
public class ScoreKeeper {
   private int score = 0;

   @Requires("points > 0")
   public addPoints(int points) {
      this.score += points;
  }
}
```

- [ ] Option 2

```java
@Invariant("score >= 0;")
public class ScoreKeeper {
   private int score = 0;

   @Ensures("points > 0")
   public addPoints(int points) {
      this.score += points;
  }
}
```

- [ ] Option 3

```java
public class ScoreKeeper {
   private int score;

   public ScoreKeeper(int initialScore) {
      this.score = initialScore;
   }

   @Requires("points > 0")
   @Ensures("score > 0")
   public addPoints(int points) {
      this.score += points;
  }
}
```

- [ ] Option 4

```java
public class ScoreKeeper {
   private int score = 0;

   @Requires("points > 0")
   public addPoints(int points) {
      this.score += points;
  }
}
```


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

- [ ] 0 ≤ path coverage < 1%
- [ ] 1% ≤ path coverage < 25%
- [ ] 25% ≤ path coverage < 50%
- [ ] 50% ≤ path coverage ≤ 100%
