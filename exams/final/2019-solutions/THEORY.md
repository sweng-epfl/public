# Software Engineering - Final Exam

## Part 1: Theory [40 points]

This part of the exam has 12 questions, with the number of points indicated next to each one.

This file is named `THEORY.md`.
Provide the answers in this file and commit the file to the `master` branch of this `exams-GASPAR` repo.
Do not change the name of the file, and do not change the existing text unless the question's statement asks you to do so.
We will only grade the `master` branch. Do not introduce extraneous spaces, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.
You must change each one to `[y]` for a yes answer and to `[n]` for each no answer.
This requires that you replace the space between all the brackets with either `y` or `n`.
Nothing else will be accepted.
Answers such as "[o]", "[N]" (uppercase n) or "[x]" will earn you 0 points.
Unless otherwise specified, questions may have zero, one, or more correct answer choices.
If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all, and you will get 0 points for it.

---

### Question 1 [2 points]

Which of these local resources can be transparently replaced by a proxy to a remote resource, using the Proxy design pattern?

- [n] A "greatest common divisor" algorithm
- [y] A connection to the Internet
- [y] A webcam
- [y] A filesystem

> Algorithms such as the greatest common divisor are fast and thus usually implemented synchronously. Programs thus could not handle errors such as "no Internet connection" when calling GCD.
> Connections to the Internet can be replaced; this is in fact another meaning of the word "proxy".
> A webcam already needs asynchronous operations and may not be available, making it an excellent candidate for a Proxy.
> Filesystems, like webcams, already need asynchronous operations with many failure cases, thus they are good candidates for proxying.

---

### Question 2 [2 points]

Which of the following are reasons the MVC pattern is not suitable for Android?

- [n] MVC is only for websites
- [n] MVC was not designed with smartphone apps in mind
- [y] In Android, only the View can handle inputs
- [n] In Android, Controllers cannot call Models

> MVC was designed in the ‘70s for desktop applications, but has been adapted for other purposes such as websites.
> There is no fundamental reason why smartphone apps could not do MVC; but on Android, since only Views can handle user inputs, MVC is not feasible.
> Controllers can call Models on any platform; both are standard objects.

---

### Question 3 [2 points]

Which of the following statements are true?

Automated testing and fuzzing can prove...

- [n] ... the absence of bugs
- [y] ... the presence of bugs
- [n] ... that the program satisfies users' needs
- [n] ... that the program meets its specification 

> Testing can prove the presence of bugs by exposing them.
> However, most programs have too many possible inputs for testing to prove the absence of bugs.
> As indicated in the lecture notes, testing does not offer strong guarantees that the program meets its specification.
> Automated testing cannot prove the program satisfies users' needs; only users themselves can decide this.

---

### Question 4 [2 points]

Imagine you are a developer in a team that develops an application to edit photos directly on users' phones.
Your team uses the Scrum method.
In the feedback your app gets on the app store, users request a new feature: to be able to modify pictures stored on cloud services such as Google Photos.

Which of the following actions are sensible steps to take on your own?

- [n] Add the feature request to the Product Backlog during the next Sprint Retrospective
- [n] Investigate how feasible the feature is given the current codebase
- [y] Ask the Product Owner to set a priority for the feature
- [n] Discuss the request at the next Daily Scrum meeting

> As a developer, you should not edit the Product Backlog on your own.
> In order to tell where in the Product Backlog it should be, you should ask the Product Owner.
> You should not investigate the feature's feasibility, or even discuss it with the other developers, before the Product Owner has decided to prioritize it during a Sprint Retrospective.

---

### Question 5 [2 points]

Which of the following statements are true?

Refactoring code is meant to...

- [y] ... make code easier to understand
- [n] ... fix bugs in the code
- [y] ... make writing tests easier
- [n] ... reduce the number of lines of code

> Refactoring can make code easier to understand by clarifying its intent, e.g. by extracting a piece of code into an aptly named method.
> It can make writing tests easier, e.g. by modularizing code.
> However, because the goal of refactoring is specifically to not change the code's externally observable behavior, it is not meant to fix bugs.
> It is also not meant to reduce the number of lines of code; for instance, that number can increase as a result of increased abstraction.

---

### Question 6 [2 points]

Your colleague has written a `Room` interface, and implementations such as `Classroom` and `Office`.
Your customers want you to implement a new feature: storing room descriptions as XML files.

Which of the following techniques could you use to implement this feature?

- [y] The Visitor pattern
- [n] The Decorator pattern
- [y] Polymorphism
- [n] Composition

> You could use polymorphism, i.e. add a method to the `Room` interface to get an XML description and have each type of `Room` implement the method.
> You could also use the Visitor pattern and implement a Visitor that gets XML descriptions.
> The Decorator pattern cannot be used here, since it exposes the same interface as it wraps.
> Composition is about delegating tasks to other objects as fields instead of using inheritance;
> since you did not already have a way to store rooms as XML files, it would not be helpful here.

---

### Question 7 [4 points]

Consider the following real-world bug from the Civilization video game ([source](https://web.archive.org/web/20191129124533/https://kotaku.com/why-gandhi-is-such-an-asshole-in-civilization-1653818245)),
which became famous among fans of the series because the behavior it causes is so unexpected since Gandhi was a well-known pacifist:

"Each leader in the game had an "aggression" rating, and Gandhi [...] was given the lowest score possible, a 1 [...].
Only, there was a problem. When a player adopted democracy in Civilization, their aggression would be automatically reduced by 2.
Code being code, if Gandhi went democratic his aggression wouldn't go to -1, it looped back around to the ludicrously high figure of 255, making him as aggressive as a civilization could possibly be."

Which kinds of tests are meant to detect this kind of problem?

- [y] Unit tests
- [y] End-to-end tests
- [y] Acceptance tests
- [n] Regression tests

> Unit tests can show an error in a single module, such as the potential for an overflow in this case.
> End-to-end tests can show an error in the interaction between modules, such as the actual presence of an overflow given the reduction in aggression in this case.
> Acceptance tests can show whether users accept the program's behavior, such as Gandhi being incredibly aggressive in this case.
> Regression tests are written in response to previous bugs to prevent them from happening again,
> but they would not have helped in this case since this was a new bug.

---

### Question 8 [4 points]

In September 2019, because of exceptionally heavy rain, the road below train tracks in the city of Renens, Switzerland was flooded.
After an investigation, the city discovered that while the pumps designed to remove excess water worked fine,
the electricians who installed the electrical panel had not cabled the pumps correctly.
This was an embarrassment for the city, since the road had already flooded before for another reason.

If the flood prevention system was a software system, which kinds of tests are meant to detect this kind of problem?

- [n] Unit tests
- [y] End-to-end tests
- [n] Acceptance tests
- [y] Regression tests

> The pumps worked correctly, and the electrical panel was not the problem either, thus unit tests would not have uncovered a problem.
> Acceptance tests would not have done the job either, because the problem only happened during exceptionally heavy rain; 
> people using the road in normal conditions would not notice any issue.
> End-to-end tests would have shown the problem since they exercise connections between components.
> Regression tests would also have shown the problem because they would have been written in response to previous instances of a bug,
> and this bug had already happened before.

---

### Question 9 [4 points]

Which of the following statements are true of the "fake" implementations of dependencies that you use in your tests instead of the real dependencies?

- [y] They make unit testing easier
- [n] They make end-to-end testing easier
- [y] They might never exhibit behaviors that real dependencies have
- [y] They might exhibit behaviors that real dependencies do not have

> Mocks ease unit testing because they allow programmers to isolate the component they want to test by controlling its environment.
> They are not useful in end-to-end testing, whose goal is specifically to run components in the real environment.
> Mocks may have behavior that real dependencies do not have, because a mock can do anything allowed by the dependency interface while a real implementation may choose to never behave in some ways.
> However, a mock can also be incomplete, and not exhibit behaviors that real implementations have.

---

### Question 10 [4 points]

Which of the following are true of fuzzing?

- [y] Fuzzing completely at random is a waste of CPU cycles
- [y] Most inputs fail to find any bugs
- [n] It is best used as a last resort, since results are not guaranteed
- [n] It is incompatible with more precise techniques from the formal verification world

> Whereas in the '80s completely random fuzzing was a good use of cycles since it was cutting-edge technology,
> doing so today would be wasteful since modern fuzzers such as AFL have smarter strategies, such as mutation of existing inputs.
> It is true that most inputs fail to find bugs, but this is a design choice of fuzzing: the goal is to focus on quantity, not quality.
> Fuzzing is a great start when testing complex systems, since it requires low effort and will often find "low-hanging fruit" that would otherwise need human effort in the form of tests.
> Fuzzing can be combined with formal techniques such as symbolic execution, as in SAGE.

---

### Question 11 [6 points]

If a `Tea` is a `Drink`, which of the following statements are true?

- [n] A `java.util.List<Tea>` is a `java.util.List<Drink>`
- [n] A `java.util.List<Drink>` is a `java.util.List<Tea>`
- [n] A `java.util.Comparator<Tea>` is a `java.util.Comparator<Drink>`
- [y] A `java.util.Comparator<Drink>` is a `java.util.Comparator<Tea>`
- [y] A `java.util.Optional<Tea>` is a `java.util.Optional<Drink>`
- [n] A `java.util.Optional<Drink>` is a `java.util.Optional<Tea>`

> A `List<Tea>` is not a `List<Drink>`, otherwise one could call `((List<Drink>) new ArrayList<Tea>()).add(new Coffee())` and break the class's contract.
> A `List<Drink>` is not a `List<Tea>` since it may contain other kinds of drinks than tea.
> A `Comparator<Tea>` is not a comparator for drinks, since it can only compare teas, and not e.g. coffees.
> A `Comparator<Drink>` is also a comparator for tea, though it may not be a very good one since it is not aware of tea-specific properties.
> (you can also think of it this way: since a `Comparator<Tea>` is not a `Comparator<Drink>`, and the two are not unrelated either, the only remaining option is that a `Comparator<Drink>` is a `Comparator<Tea>`.)
> An `Optional<Tea>` is an `Optional<Drink>`. Unlike `List`, one cannot change an `Optional`'s content, only get it. Always returning a `Tea` is a valid behavior for an `Optional<Drink>`.
> An `Optional<Drink>` is not an `Optional<Tea>` since it may contain another kind of drink than tea.

---

### Question 12 [6 points]

You are designing a software system that will handle cafeteria menus at a university and display them on a website.
The students want to see which menus are offered today, and to filter and sort them by criteria such as price or kind of food.
The cafeteria owners want to upload the menus they have written in software such as Excel or Notepad. They also want to make small edits in the system itself, for instance to fix typos.
The university IT department has just signed a massive long-term contract with an IT services provider, and consequently has little budget for your software.
They want it to ship as fast as possible, without extraneous features.
They do, however, have plans for a mobile application soon.

Which of the following design decisions are likely to be good ideas?

- [n] You should make the database classes abstract, so that the system can support different kinds of databases: SQL, NoSQL, graph...
- [n] You should avoid patterns such as MVC or MVVM, to minimize the code's complexity and make development faster
- [y] The UI for cafeteria owners should use the Factory pattern to allow them to upload menus in different formats
- [n] The UI for cafeteria owners should use the Decorator pattern to allow them to fix mistakes in existing menus
- [y] The UI for students should use the Singleton pattern to ensure their preferences are applied consistently
- [y] The UI for students should use the Decorator pattern to allow them to filter out menus based on their preferences

> Since the IT department has tied itself to a specific IT provider, abstracting away the database type is unlikely to be useful since the database will most likely not change; it will also increase the software's complexity.
> Patterns such as MVC and MVVM are designed to reduce the complexity of all but the most trivial applications, thus you should not avoid them!
> The Factory pattern is a good choice to handle different kinds of file formats.
> The Decorator pattern is about exposing the same interface but adding features; in this case, while it could be used to represent typo corrections as patches over incorrect menus,
> this is unlikely to be what users want sine they are changing text, not keeping track of all old versions. Furthermore, it would complicate the overall design by adding a form of version control that was not requested.
> The Singleton pattern is a typical choice for user preferences.
> The Decorator pattern is a typical choice for filtering data.
