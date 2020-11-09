# Software Engineering - Homework 1

## Part 1: Theory [20 points]

This part of the homework has 10 questions, with the number of points indicated next to each one.

This file is named `THEORY.md`. Provide the answers in this file and commit the file to the `master` branch of this `exams-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless the question's statement asks you to do so. We will only grade the `master` branch. Do not introduce extraneous spaces or stray characters, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.  You must change each one to `[y]` for a yes answer and to `[n]` for each no answer.  This requires that you replace the space between all the brackets with either `y` or `n`. Nothing else will be accepted. Answers such as "[o]", "[N]" (uppercase n) or "[x]" will earn you 0 points. Unless otherwise specified, questions may have zero, one, or more correct answer choices. If you leave any checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question at all, and you will get 0 points for it.


### Question 1 [2 points]

Which of the following is true about VCS ?

- [n] The main advantage of feature branches is that features and bug fixes can be kept in separate commits
- [n] There is, in practice, no difference between GitHub and a 2nd generation VCS
- [y] You can cherry-pick any commit from any branch into any other branch
- [n] Pull requests are part of Git

> Having each feature / bugfix in a separate commit is good practice that should be followed all the time, but feature branches serve the more advanced purpose of atomicity of changes, i.e. making sure that half-working code is never part of the main (e.g. master) branch.
> Since Git is a distributed VCS, there are practical differences. For example, in Git, each user’s copy is a repository, whereas in a 2nd generation VCS, that is not the case and checking out from an earlier commit would require a server connection, while for a project on GitHub, that would not be necessary. Another example would be how the “master” branch on GitHub is simply an agreed-upon main branch.
> Aside from the potential manual merging, anything can be cherry-picked.
> Pull requests are a feature of Git hosting services such as Github

### Question 2 [2 points]

What should a developer communicate in the commit messages they write?

- [y] Why this commit was made
- [n] How the implementation was done
- [n] When the commit was made
- [y] What the commit accomplishes

> In the body of a commit message, one should explain what and why a change was made. The message should not describe the implementation, as it is already part of the code. Git already tracks the time of the commit automatically, so there is no need to write it.

### Question 3 [2 points]

Which of these are essential elements of a continuous integration system?

- [y] Building the project after every commit to the designated branch
- [n] Code reviews after each commit
- [y] Test coverage
- [y] Unit tests
- [n] Formal verification

> The core features of a CI pipeline include building the project after each commit of a given branch, automatic testing and code coverage of the tests among others. Code reviews do not happen after every commit (it would take a lot of valuable human work-hours), they usually take place when a branch needs to be merged back to the main branch. Formal verification is a long process which often involves human intervention, and automated tools are not able to verify complex software systems.

### Question 4 [2 points]

Having understood the importance of feature branches, you have created a branch for a new, small and non-critical feature, and you have just committed some code, although the (admittedly small) feature is currently half-finished. Which single one of the following actions has the highest priority before you can merge with the dev branch (which is under Continuous Integration)?

- [n] Write and run unit tests
- [n] Submit a pull request so that a code review is conducted as well
- [y] Finish the feature
- [n] Perform a static analysis of the code

> The feature is not yet finished! As such, the most thing that needs to be done before a merge with the dev branch is finishing the feature (c). Unit tests are essential and a pull request is also very useful, but even if those are done for the half-finished feature branch, it is still bad practice to merge that branch before it’s done. A static analysis of the feature is unnecessary owing to its non-critical nature.

### Question 5 [2 points]

Which of the following tests are not *directly* concerned with the user’s satisfaction with the system?

- [n] Manual acceptance tests
- [y] Unit tests
- [n] A/B tests
- [n] Automatic acceptance tests

> Acceptance tests ensure that the criteria set by the customer are met. A/B testing involves 2 sets of users which are shown different versions of the same feature, so that developers can collect metrics and decide on which version is best. Unit tests however only check a limited set of the input domain against modules of software, but they do not check whether the users are satisfied with the product.

### Question 6 [2 points]

Assume that we are given a system where the total size of the program is *N* lines of code. Each module of size *n* has *O(n)* bugs, and each bug in that module takes *O(n)* time to fix. Assuming that each module is connected to all the other modules, and that a bug in each one of the interconnections takes O(1) time to fix, what is the time complexity of fixing all bugs if the program consists of *k* modules?

- [n] O(N^2/k)
- [n] O(N)
- [y] O(N^2/k + k^2)
- [n] O(N^2/k + k)

> Since the number of bugs grows linearly in each module, then it also grows linearly in the N lines of code. Thus the time to fix all bugs independently in modules is O(N^2/k), and each k module is connected to (k-1) others thus the total time spent is O(N^2/k + k^2).

### Question 7 [2 points]

Which of the following language constructs may violate the single-exit principle of structured programming?

- [y] Assembly `JMP` or C `goto` statement
- [n] Pure functions
- [y] Java Exception `throw`
- [n] Java `if-else` block

> goto statements can break the control flow of the program at any point in time and jump to a completely unrelated portion of code.
> Pure functions take all inputs as arguments and always return the same type of value without performing side-effects, thus the exit is always similar.
> Similarly to a goto statement, an exception can be thrown and caught much further in an unrelated part of the program (or simply not caught at all thus crashing the software).
> An if-else statement conforms to the definition of selection block in structured programming

### Question 8 [2 points]

Which of the following data representations can be said to have a high Kolmogorov index?

- [n] An array of consecutive integers [0, 1, 2, ..., n] (where *n* is very large)
- [y] A matrix of pixels from a photo of the EPFL campus
- [y] A hard drive storing 100 different movies
- [n] A fractal vector graphic

> An array of consecutive integers can be built by simply looping on the array indices
> A picture of the EPFL campus can only be described by enumerating all of its pixels (or reconstructing it first if compression occured).
> Similarly, movies can only be quantified by describing each different frame
> A fractal can be described by recursively applying the same pattern to itself

### Question 9 [2 points]

Which of the following statements are true about virtualization ?

- [n] Virtualization is only useful for debugging and continuous integration systems
- [y] Virtualization enables a form of enforced modularity at the OS or process level
- [n] Virtual machines and containers provide the same level of abstraction to users (e.g., DevOps engineers)
- [y] Docker is a specific use case of containers to isolate single application systems

> Virtualization systems are also used massively in production environments, such as web services.
> As VMs and containers prevent guest OSes / processes to be aware of each other, they enforce modularity through isolation
> VMs virtualize the underlying hardware, while containers virtualize the OS.

### Question 10 [2 points]

Which of the following statements are true about building software ?

- [n] In general, developers should define their own file structure for code and assets
- [y] Usually, the source code of third-party software that this code depends on should not be committed to VCS
- [n] Assume a software system *A* uses a library *B* at version 1.3.6. If version 2.0.1 of *B* was just released, then developers of *A* can simply download the update of library *B* and do not need to change the code of *A*.
- [n] Open source software is immune to bugs and security vulnerabilities

> Each language and framework usually has a set of best practices which developers should use, to ensure the readability and maintainability of their software
> Modern software ecosystems have repositories of dependencies at specific versions, so only a description of the dependency tree is required on VCS to rebuild the project entirely. Older systems usually rely on other repositories (or submodules).
> Since the major number changed, breaking changes to the API occured, so developers of A will probably need to change their own code.
> As seen in the lectures, there are many examples of bugs and security issues with open source software
