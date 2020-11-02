# Software Engineering - Homework 1

## Part 1: Theory [20 points]

This part of the homework has 10 questions, with the number of points indicated next to each one.

This file is named `THEORY.md`. Provide the answers in this file and commit the file to the `master` branch of this `exams-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless the question's statement asks you to do so. We will only grade the `master` branch. Do not introduce extraneous spaces or stray characters, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.  You must change each one to `[y]` for a yes answer and to `[n]` for each no answer.  This requires that you replace the space between all the brackets with either `y` or `n`. Nothing else will be accepted. Answers such as "[o]", "[N]" (uppercase n) or "[x]" will earn you 0 points. Unless otherwise specified, questions may have zero, one, or more correct answer choices. If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all, and you will get 0 points for it.


### Question 1 [2 points]

Which of the following is true about VCS ?

- [ ] The main advantage of feature branches is that features and bug fixes can be kept in separate commits
- [ ] There is, in practice, no difference between GitHub and a 2nd generation VCS
- [ ] You can cherry-pick any commit from any branch into any other branch
- [ ] Pull requests are part of Git


### Question 2 [2 points]

What should a developer communicate in the commit messages they write?

- [ ] Why this commit was made
- [ ] How the implementation was done
- [ ] When the commit was made
- [ ] What the commit accomplishes


### Question 3 [2 points]

Which of these are essential elements of a continuous integration system?

- [ ] Building the project after every commit to the designated branch
- [ ] Code reviews after each commit
- [ ] Test coverage
- [ ] Unit tests
- [ ] Formal verification


### Question 4 [2 points]

Having understood the importance of feature branches, you have created a branch for a new, small and non-critical feature, and you have just committed some code, although the (admittedly small) feature is currently half-finished. Which single one of the following actions has the highest priority before you can merge with the dev branch (which is under Continuous Integration)?

- [ ] Write and run unit tests
- [ ] Submit a pull request so that a code review is conducted as well
- [ ] Finish the feature
- [ ] Perform a static analysis of the code


### Question 5 [2 points]

Which of the following tests are not *directly* concerned with the user’s satisfaction with the system?

- [ ] Manual acceptance tests
- [ ] Unit tests
- [ ] A/B tests
- [ ] Automatic acceptance tests


### Question 6 [2 points]

Assume that we are given a system where the total size of the program is *N* lines of code. Each module of size *n* has *O(n)* bugs, and each bug in that module takes *O(n)* time to fix. Assuming that each module is connected to all the other modules, and that a bug in each one of the interconnections takes O(1) time to fix, what is the time complexity of fixing all bugs if the program consists of *k* modules?

- [ ] O(N^2/k)
- [ ] O(N)
- [ ] O(N^2/k + k^2)
- [ ] O(N^2/k + k)


### Question 7 [2 points]

Which of the following language constructs may violate the single-exit principle of structured programming?

- [ ] Assembly `JMP` or C `goto` statement
- [ ] Pure functions
- [ ] Java Exception `throw`
- [ ] Java `if-else` block


### Question 8 [2 points]

Which of the following data representations can be said to have a high Kolmogorov index?

- [ ] An array of consecutive integers [0, 1, 2, ..., n] (where *n* is very large)
- [ ] A matrix of pixels from a photo of the EPFL campus
- [ ] A hard drive storing 100 different movies
- [ ] A fractal vector graphic


### Question 9 [2 points]

Which of the following statements are true about virtualization ?

- [ ] Virtualization is only useful for debugging and continuous integration systems
- [ ] Virtualization enables a form of enforced modularity at the OS or process level
- [ ] Virtual machines and containers provide the same level of abstraction to users (e.g., DevOps engineers)
- [ ] Docker is a specific use case of containers to isolate single application systems


### Question 10 [2 points]

Which of the following statements are true about building software ?

- [ ] In general, developers should define their own file structure for code and assets
- [ ] Usually, the source code of third-party software that this code depends on should not be committed to VCS
- [ ] Assume a software system *A* uses a library *B* at version 1.3.6. If version 2.0.1 of *B* was just released, then developers of *A* can simply download the update of library *B* and do not need to change the code of *A*.
- [ ] Open source software is immune to bugs and security vulnerabilities

