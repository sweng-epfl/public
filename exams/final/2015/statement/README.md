# SwEng Practice Final Exam

_This is a slightly modified version of the final exam given in 2015._

Welcome to the SwEng Final!

This exam is worth a total of _100 points_, with the following breakdown:

* 20 points for the theoretical section
* 80 points for the practical (coding) section (each section is marked with its point total)

## Exam Rules

* You may use your laptop or one of the computers in the lab.
* You may consult any electronic resource during the exam (e.g. a website or class material).
    * You may __not__ copy code from any website (or your neighbor). You may use code that you or your team has written during the class or code from the lectures.
* You may __not__ interact with anyone else, student or otherwise, during the exam!
    * This means __absolutely no chat (Facebook, Google Hangouts, IRC, etc.), no texts, and no email__.
    * We (the staff) will be recording and monitoring traffic on the EPFL network during the exam, which is tracked to your GASPAR login.
* You may, of course, ask questions to the course staff.
* If you wish to leave early, please show your CAMIPRO card to one of the course staff.
    * When you leave the exam, we will shut off access to your repository. If you leave early without showing your CAMPIPRO card to one of the staff, you will not receive any points for the final.
    * The staff may ask you to not leave early near the end of the exam to avoid disturbing students who want to work until the end of the exam period.
* If you need to use the bathroom during the exam, you must be escorted by a member of the staff.

## Cloning Your Repo

Clone your repository using the following:

    git clone https://github.com/sweng-epfl-2015/final-GASPAR.git

where `GASPAR` is your GASPAR username.

# Submission and Grading

You will only be graded on the the contents of your repository on GitHub (not on your machine). __Please make sure that all of your changes are pushed to GitHub.__

Access to your repository will be terminated at *18h00* 18.12.2015!

You may work on either the theoretical or the practical section first.

# Theoretical Section

There is a theoretical section in this exam. This is located in the file `Theoretical-Questions.md` in your repo. This file contains both the instructions and the questions themselves. Please follow the instructions in that file.

__Make sure you commit your edited file on the `master` branch.__

# Practical Section

Now for some coding!

This part of the exam will focus around a simple social network application named `NameBook` (in the exam repo). This library is a project located in the `NameBook` subdirectory. The library contains several classes that implement a directed graph and then build
a simple social network on a graph.

In this section, you will do four tasks:

* Improve test coverage and fix any bugs that you expose.
* Implement an Iterator design pattern.
* Implement a Visitor design pattern.
* Resolve conflicting merges.

__It is required that you perform these tasks in the order they are written in this exam.__

## Committing Your Code Correctly

__The four tasks must be completed in the specified branches for them to be graded fully.__ If your solution to a task is in the wrong branch (or just merged to master), you will get zero points.

__Do NOT rebase or delete branches that contain work to be graded.__

All of your work must be __pushed to GitHub__ in order to get credit. To make sure all of your work reaches GitHub, use this command frequently:

    git push --all

# Task 1: Test Coverage

__Points: 20__

The first task is to improve the code coverage of the JUnit tests so that 80% or more of the lines of code in the application are tested. You should increase this coverage by adding new unit tests.

If you find bugs exposed by your tests, then fix the bugs.

You can measure the test coverage using the Coverage command on the Run menu, after you install the EclEmma tool in Eclipse. During the exam you will directly use Jenkins.

__All of this work should be done in a new git branch named 'coverage'.__ After you are finished, merge this branch into master.

## Your sub-tasks

1. Create a branch called `coverage`.
2. Switch to that branch.
3. Write JUnit tests to improve test coverage to greater than 80% of lines. Find and fix bugs.
4. Merge `coverage` back into `master` (`git merge --no-ff`, generate a merge commit even if the merge resolved as a fast-forward).
5. Push __all__ changes back to GitHub.

# Task 2: Iterator Design Pattern

__Points: 25__

You may have noticed that the code returns lists of nodes and edges in several place. This is often a reasonable interface, but in many cases (including this code), these lists are just used to iterate over a collection of nodes or edges. In this situation, it is clearer and often more efficient to use the Iterator design pattern, as discussed in lectures.

In this task, you should implement iterators for collections of 'GraphEdges' and 'GraphNodes' and refactor the code to use these iterators in all of the places where they are appropriate. Use your unit tests to ensure that your changes do not break the code.

Your iterators should be based on the interface (but not the implementation) of Java's [java.util.Iterator][iterator] interface. In other words, __your code cannot invoke the `iterator()` method on any collection.__

You should implement one iterator for `Graph.getAllNodes` and one for `GraphNode.getForwardEdges`.
You are allowed to reuse one of them in the other's implementation.

After you get your code work, __do NOT merge it into the master branch.__ This will occur in Task 4.

[iterator]: https://docs.oracle.com/javase/7/docs/api/java/util/Iterator.html

## Your sub-tasks

1. Create a branch called `iterator` from the `master` branch.
2. Switch to that branch.
3. Implement 'GraphEdgeIterator' and 'GraphNodeIterator' classes.
4. Refactor the code to use these classes where appropriate.
5. Use your unit tests to ensure your new code works correctly.
6. __DO NOT__ merge back into `master`.
7. Push __all__ changes back to GitHub.

# Task 3: Visitor Design Pattern

__Points: 25__

An alternative method for traversing a complex data structure like a graph is the Visitor design pattern. In this assignment, you will implement this design pattern for the graph and use it. Again, your unit tests will ensure that your changes do not break the code.

Starting from the master branch, you will use these two new iterfaces:

* `IGraphElement`
```java
public interface IGraphElement<D> {
    void accept(IGraphElementVisitor<D> visitor);
}
```

* `IGraphElementVisitor`
```java
public interface IGraphElementVisitor<D> {
    void visit(Graph<D> graph);
    void visit(GraphEdge<D> edge);
    void visit(GraphNode<D> node);
}
```

You should also modify the existing code to implement the Visitor pattern and use it to implement the `NameBook.printFriendships` and the `Graph.getAllNodes` methods.

After this code is implemented and tested, you should merge it back into the `master` branch.

##Your (sub-)tasks

1. Create a branch called `visitor` from the `master` branch.
2. Switch to that branch.
3. Create the two interfaces, as above.
4. Modify the code to implement these two interfaces.
5. Refactor `NameBook.printFriendships` and `Graph.getAllNodes` methods to use the Visitor pattern.
6. Test your changes.
7. Merge `visitor` back into `master` (`git merge --no-ff`, generate a merge commit even if the merge resolved as a fast-forward).
8. Push __all__ changes back to GitHub.

# Task 4: Resolving Conflicts

__Points: 10__

In this final task, you will merge the iterator branch into the master and resolve the conflicts that occur because of the overlapping modifications in the `iterator` and `visitor` branches.

The merged code must use the Iterator pattern for `NameBook.printFriendships` and use the Visitor pattern for `Graph.getAllNodes`.

##Your (sub-)tasks

1. Switch to the `master` branch.
2. Merge in the `iterator` branch.
3. Resolve the conflicts so that `NameBook.printFriendships` is implemented with the Iterator pattern and `Graph.getAllNodes` is implemented with the Visitor pattern.
4. Test your code to make sure it works.
5. Commit it and push it back to GitHub.
