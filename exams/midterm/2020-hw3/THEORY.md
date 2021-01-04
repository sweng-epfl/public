# Software Engineering - Homework 3

## Part 1: Theory [20 points]

This part of the homework has 10 questions, with the number of points indicated next to each one.

This file is named `THEORY.md`. Provide the answers in this file and commit the file to the `master` branch of this `student-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless the question's statement asks you to do so. We will only grade the `master` branch. Do not introduce extraneous spaces or stray characters, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.  You must change each one to `[y]` for a yes answer and to `[n]` for each no answer.  This requires that you replace the space between all the brackets with either `y` or `n`. Nothing else will be accepted. Answers such as "[o]", "[N]" (uppercase n) or "[x]" will earn you 0 points. Unless otherwise specified, questions may have zero, one, or more correct answer choices. If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all, and you will get 0 points for it.

### Question 1 [2 points]

Which of the following are examples of code smells?

- [ ] A method that takes in a long parameter list
- [ ] One class uses internal fields and methods of another class
- [ ] Several methods have identical blocks of code
- [ ] A method is filled with comments explaining what the code does


### Question 2 [2 points]

In which of the following Scrum events does the Product Owner participate? Assume that the Product Owner is not part of the Development Team.

- [ ] Sprint Planning
- [ ] Daily Scrum
- [ ] Sprint Review
- [ ] Sprint Retrospective


### Question 3 [2 points]

You notice that a program you wrote is slower than you would like. What are some good first steps to take in order to improve performance?

- [ ] Use a profiler to find out which parts of your code are running slowly
- [ ] Rewrite your code in a lower level, faster language like C
- [ ] Check if parts of your code are trivially parallelizable
- [ ] Replace divisions and modulo operators by bit-shifts
 

### Question 4 [2 points]

When is it a good idea to write a comment in your code ?

- [ ] Before a public getter that simply returns a member variable.
- [ ] Before handling a special case that occurs only on a certain platform.
- [ ] Before a piece of code that you wrote last week but do not really understand anymore.
- [ ] Before this function `public boolean setName(String name) throws RuntimeException { … }`


### Question 5 [2 points]

What is harmful code ?

- [ ] Code that could harm another human being
- [ ] Code that could damage someone else’s property
- [ ] Code that contain bugs
- [ ] Code without tests


### Question 6 [2 points]

What are the most important things to try to optimize in regard to performance ?

- [ ] Data locality in memory.
- [ ] Sub-optimal use of some CPU instructions.
- [ ] Try to reduce the need of the garbage collector.
- [ ] Memory allocations.


### Question 7 [2 points]

Assume you run this code on an Intel Core i7 with 4 cores / 8 threads. After how much time will the statement “Finished!” be printed in the console when executing this code ? Assume that no exception is thrown anywhere.

```java
CompletableFuture tasks[] = new CompletableFuture[10];
for(int i = 0; i < 10; ++i) {
    tasks[i] = CompletableFuture.runAsync(() -> {
        hardWork(); // Takes 2 seconds to execute
    });
}
CompletableFuture.allOf(tasks).join();
System.out.println(“Finished!”);
```

- [ ] immediately
- [ ] around 2 seconds
- [ ] around 4 seconds
- [ ] around 20 seconds


### Question 8 [2 points]

During a Sprint, Matt, the Product Owner, wants to add a new feature to the current Sprint Backlog. Is he allowed to do this?

- [ ] Yes, only him and the Scrum Master are allowed to change the current Sprint Backlog during a Sprint.
- [ ] Yes, anyone can change the Sprint Backlog at any time.
- [ ] No, only the Development Team and the Scrum Master can change the Sprint Backlog during a Sprint.
- [ ] No, only the Development Team can change the Sprint Backlog during a Sprint.


### Question 9 [2 points]

Which of the following are good examples of commit messages?

- [ ] `Improve performance by calling update_database function outside for-loop in utils.py, this is much more efficient and leads to a 1.5x speed-up :)`
- [ ] `Add message parsing tests`
- [ ] `Fix typo to avoid confusion between Bin and Stack`
- [ ] `Implement minor changes`
- [ ] `Update user profile upon receiving request`
- [ ] `LRU cache function`


### Question 10 [2 points]

You are currently writing a server program that handles user requests to a database. The requests are randomly spaced in time. What are the methods you can use to improve the performance and responsiveness of your program ?

- [ ] Parallelism
- [ ] Batching
- [ ] Caching
- [ ] Speculative execution

