# Software Engineering - Homework 3

## Part 1: Theory [20 points]

This part of the homework has 10 questions, with the number of points indicated next to each one.

This file is named `THEORY.md`. Provide the answers in this file and commit the file to the `master` branch of this `student-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless the question's statement asks you to do so. We will only grade the `master` branch. Do not introduce extraneous spaces or stray characters, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.  You must change each one to `[y]` for a yes answer and to `[n]` for each no answer.  This requires that you replace the space between all the brackets with either `y` or `n`. Nothing else will be accepted. Answers such as "[o]", "[N]" (uppercase n) or "[x]" will earn you 0 points. Unless otherwise specified, questions may have zero, one, or more correct answer choices. If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all, and you will get 0 points for it.

### Question 1 [2 points]

Which of the following are examples of code smells?

- [y] A method that takes in a long parameter list
- [y] One class uses internal fields and methods of another class
- [y] Several methods have identical blocks of code
- [y] A method is filled with comments explaining what the code does

> A code smell is any characteristic in the source code of a program that possibly indicates a deeper problem. All 4 of these examples could indicate that parts of a codebase are hard to understand and maintain, and should be addressed during refactoring.

### Question 2 [2 points]

In which of the following Scrum events does the Product Owner participate? Assume that the Product Owner is not part of the Development Team.

- [y] Sprint Planning
- [n] Daily Scrum
- [y] Sprint Review
- [y] Sprint Retrospective

>  The Daily Scrum is an event for the Developers of the Scrum Team. If the Product Owner or the Scrum Master are working on items in the Sprint Backlog, then they participate as Developers. The PO is welcome to attend the Daily Scrum to listen or observe, but they may not participate in it.

### Question 3 [2 points]

You notice that a program you wrote is slower than you would like. What are some good first steps to take in order to improve performance?

- [y] Use a profiler to find out which parts of your code are running slowly
- [n] Rewrite your code in a lower level, faster language like C
- [y] Check if parts of your code are trivially parallelizable
- [n] Replace divisions and modulo operators by bit-shifts
 
> While rewriting code in a lower level language can lead to significant performance improvements, it isn’t a good first step to take, as it is very time consuming. Rewriting code should be considered if the code is performance-critical, and other simpler optimization techniques did not result in a good enough speed-up.
> Using bit-shifts instead of modulo or division operations makes code much harder to understand, and does not usually improve performance as most compilers already routinely optimize multiplications and divisions by constant powers of 2 with bit-shift operations.

### Question 4 [2 points]

When is it a good idea to write a comment in your code ?

- [n] Before a public getter that simply returns a member variable.
- [y] Before handling a special case that occurs only on a certain platform.
- [n] Before a piece of code that you wrote last week but do not really understand anymore.
- [y] Before this function `public boolean setName(String name) throws RuntimeException { … }`

> A simple getter method does not need a comment. The code speaks for itself.
> If you are doing something that is not obvious at first sight, it is a good idea to add a comment so that other developers understand why this piece of code is important and which special case it deals with.
> Comments cannot fix bad code. If you cannot understand your own code, then it is probably in need of a refactor not comments.
> Here, a comment is fitting as it clears up the question of the return value and the conditions that could cause the runtime exception.

### Question 5 [2 points]

What is harmful code ?

- [y] Code that could harm another human being
- [y] Code that could damage someone else’s property
- [y] Code that contain bugs
- [y] Code without tests

> Code that harms people or people’s property is obviously harmful.
> Code that contains bugs by definition does not behave as expected which is harmful behavior. And code without tests almost certainly contains bugs.

### Question 6 [2 points]

What are the most important things to try to optimize in regard to performance ?

- [y] Data locality in memory.
- [n] Sub-optimal use of some CPU instructions.
- [n] Try to reduce the need of the garbage collector.
- [y] Memory allocations.

> Data locality in memory is very important to optimize in order to make good use of the CPU caches which gives considerable performance benefits.
> Compilers are nowadays very intelligent and trying to outsmart them is almost always a waste of time.
> Garbage collectors have become very performant and trying to optimize the small portion of the code that would benefit from this is not of most importance. Furthermore, the benefits of garbage collection easily outweighs the cost of optimization.
> Memory allocation is very important to optimize as it is a very common task that can easily  waste performance on a lot of small/large memory allocations/deallocations that stall the CPU.

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

- [n] immediately
- [n] around 2 seconds
- [y] around 4 seconds
- [n] around 20 seconds

> The machine that runs the code can only run 8 threads in parallel so it will need to run the first 8 tasks which takes 2 seconds and then the last 2 which takes an additional 2 seconds for a total of 4 seconds.

### Question 8 [2 points]

During a Sprint, Matt, the Product Owner, wants to add a new feature to the current Sprint Backlog. Is he allowed to do this?

- [n] Yes, only him and the Scrum Master are allowed to change the current Sprint Backlog during a Sprint.
- [n] Yes, anyone can change the Sprint Backlog at any time.
- [n] No, only the Development Team and the Scrum Master can change the Sprint Backlog during a Sprint.
- [y] No, only the Development Team can change the Sprint Backlog during a Sprint.

> In Scrum, the Sprint Backlog is the set of items from the Product Backlog that the Development Team plans to accomplish during a Sprint. The Sprint Backlog belongs solely to the Development Team.

### Question 9 [2 points]

Which of the following are good examples of commit messages?

- [n] `Improve performance by calling update_database function outside for-loop in utils.py, this is much more efficient and leads to a 1.5x speed-up :)`
- [y] `Add message parsing tests`
- [y] `Fix typo to avoid confusion between Bin and Stack`
- [n] `Implement minor changes`
- [y] `Update user profile upon receiving request`
- [n] `LRU cache function`

> The first message is too descriptive and breaks the 50 character limit.
>  The fourth message is too vague, it doesn’t specify what the changes are.
> The sixth message doesn’t use the imperative mood, and thus doesn’t specify what is done with this function. “Implement LRU cache” or “Modify LRU cache” would be better commit messages.

### Question 10 [2 points]

You are currently writing a server program that handles user requests to a database. The requests are randomly spaced in time. What are the methods you can use to improve the performance and responsiveness of your program ?

- [y] Parallelism
- [n] Batching
- [y] Caching
- [n] Speculative execution

> Parallelism is a great way of improving performance and responsiveness as it enables the server to handle more requests at the same time.
> Batching is not a good fit here as it would wait for many requests to come before executing any of them. If a single user makes a request, then the result would never be sent back.
> Caching is great here as we could reuse results from previous similar requests instead of asking the database again.
> Speculative execution is performed by the CPU to execute in advance code that it thinks might be executed next. However, this is not something that can be controlled by your code to gain performance in this case.
