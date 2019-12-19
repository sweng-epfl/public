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

- [ ] A "greatest common divisor" algorithm
- [ ] A connection to the Internet
- [ ] A webcam
- [ ] A filesystem


---

### Question 2 [2 points]

Which of the following are reasons the MVC pattern is not suitable for Android?

- [ ] MVC is only for websites
- [ ] MVC was not designed with smartphone apps in mind
- [ ] In Android, only the View can handle inputs
- [ ] In Android, Controllers cannot call Models


---

### Question 3 [2 points]

Which of the following statements are true?

Automated testing and fuzzing can prove...

- [ ] ... the absence of bugs
- [ ] ... the presence of bugs
- [ ] ... that the program satisfies users' needs
- [ ] ... that the program meets its specification 


---

### Question 4 [2 points]

Imagine you are a developer in a team that develops an application to edit photos directly on users' phones.
Your team uses the Scrum method.
In the feedback your app gets on the app store, users request a new feature: to be able to modify pictures stored on cloud services such as Google Photos.

Which of the following actions are sensible steps to take on your own?

- [ ] Add the feature request to the Product Backlog during the next Sprint Retrospective
- [ ] Investigate how feasible the feature is given the current codebase
- [ ] Ask the Product Owner to set a priority for the feature
- [ ] Discuss the request at the next Daily Scrum meeting


---

### Question 5 [2 points]

Which of the following statements are true?

Refactoring code is meant to...

- [ ] ... make code easier to understand
- [ ] ... fix bugs in the code
- [ ] ... make writing tests easier
- [ ] ... reduce the number of lines of code


---

### Question 6 [2 points]

Your colleague has written a `Room` interface, and implementations such as `Classroom` and `Office`.
Your customers want you to implement a new feature: storing room descriptions as XML files.

Which of the following techniques could you use to implement this feature?

- [ ] The Visitor pattern
- [ ] The Decorator pattern
- [ ] Polymorphism
- [ ] Composition


---

### Question 7 [4 points]

Consider the following real-world bug from the Civilization video game ([source](https://web.archive.org/web/20191129124533/https://kotaku.com/why-gandhi-is-such-an-asshole-in-civilization-1653818245)),
which became famous among fans of the series because the behavior it causes is so unexpected since Gandhi was a well-known pacifist:

"Each leader in the game had an "aggression" rating, and Gandhi [...] was given the lowest score possible, a 1 [...].
Only, there was a problem. When a player adopted democracy in Civilization, their aggression would be automatically reduced by 2.
Code being code, if Gandhi went democratic his aggression wouldn't go to -1, it looped back around to the ludicrously high figure of 255, making him as aggressive as a civilization could possibly be."

Which kinds of tests are meant to detect this kind of problem?

- [ ] Unit tests
- [ ] End-to-end tests
- [ ] Acceptance tests
- [ ] Regression tests


---

### Question 8 [4 points]

In September 2019, because of exceptionally heavy rain, the road below train tracks in the city of Renens, Switzerland was flooded.
After an investigation, the city discovered that while the pumps designed to remove excess water worked fine, the electricians who installed the electrical panel had not cabled the pumps correctly.
This was an embarrassment for the city, since the road had already flooded before for another reason.

If the flood prevention system was a software system, which kinds of tests are meant to detect this kind of problem?

- [ ] Unit tests
- [ ] End-to-end tests
- [ ] Acceptance tests
- [ ] Regression tests


---

### Question 9 [4 points]

Which of the following statements are true of the "fake" implementations of dependencies that you use in your tests instead of the real dependencies?

- [ ] They make unit testing easier
- [ ] They make end-to-end testing easier
- [ ] They might never exhibit behaviors that real dependencies have
- [ ] They might exhibit behaviors that real dependencies do not have


---

### Question 10 [4 points]

Which of the following are true of fuzzing?

- [ ] Fuzzing completely at random is a waste of CPU cycles
- [ ] Most inputs fail to find any bugs
- [ ] It is best used as a last resort, since results are not guaranteed
- [ ] It is incompatible with more precise techniques from the formal verification world


---

### Question 11 [6 points]

If a `Tea` is a `Drink`, which of the following statements are true?

- [ ] A `java.util.List<Tea>` is a `java.util.List<Drink>`
- [ ] A `java.util.List<Drink>` is a `java.util.List<Tea>`
- [ ] A `java.util.Comparator<Tea>` is a `java.util.Comparator<Drink>`
- [ ] A `java.util.Comparator<Drink>` is a `java.util.Comparator<Tea>`
- [ ] A `java.util.Optional<Tea>` is a `java.util.Optional<Drink>`
- [ ] A `java.util.Optional<Drink>` is a `java.util.Optional<Tea>`


---

### Question 12 [6 points]

You are designing a software system that will handle cafeteria menus at a university and display them on a website.
The students want to see which menus are offered today, and to filter and sort them by criteria such as price or kind of food.
The cafeteria owners want to upload the menus they have written in software such as Excel or Notepad. They also want to make small edits in the system itself, for instance to fix typos.
The university IT department has just signed a massive long-term contract with an IT services provider, and consequently has little budget for your software.
They want it to ship as fast as possible, without extraneous features.
They do, however, have plans for a mobile application soon.

Which of the following design decisions are likely to be good ideas?

- [ ] You should make the database classes abstract, so that the system can support different kinds of databases: SQL, NoSQL, graph...
- [ ] You should avoid patterns such as MVC or MVVM, to minimize the code's complexity and make development faster
- [ ] The UI for cafeteria owners should use the Factory pattern to allow them to upload menus in different formats
- [ ] The UI for cafeteria owners should use the Decorator pattern to allow them to fix mistakes in existing menus
- [ ] The UI for students should use the Singleton pattern to ensure their preferences are applied consistently
- [ ] The UI for students should use the Decorator pattern to allow them to filter out menus based on their preferences
