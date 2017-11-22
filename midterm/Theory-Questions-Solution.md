# CS305 – Software Engineering

## Midterm Exam Solutions

# Part 2: Theory [40 points]

This second part of the midterm exam has 10 questions, with the number of points indicated next to each one.

This file is named `Theory.md`. Provide the answers in this file and commit the file to the `master` branch of this `midterm-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless the question statements asks you to do so. We will only grade the master branch. Do not introduce extraneous spaces, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.  You should change each one to `[y]` for each correct answer, or to `[n]` for each incorrect answer.  This requires that you replace the space between the brackets with either `y` or `n`. Unless otherwise specified, questions may have zero, one, or more correct answer choices. If you leave a checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question and you will get 0 points for it. 

### Question 1 [2 points]

Which of the following is/are true about test-driven development (TDD)? 

- [n] TDD ensures there are no bugs in the code (__0.5 pt__)
- [n] TDD is synonymous with writing unit tests (__0.5 pt__)
- [y] Tests are the first thing a developer writes (__0.5 pt__)
- [n] The unit tests should not be changed once they have been written (__0.5 pt__)

__Explanation for students__: By definition, TDD starts with writing tests, followed by writing the code that passes the tests, hence the 3rd choice is correct.  In option 1, there is nothing in TDD that can guarantee the absence of bugs in the code (we would need formal verification for that). In option 2, writing unit tests concerns the mechanics of how to do a certain kind of testing, and is orthogonal to a development philosophy like TDD.  Option 4 is wrong in an absolute sense (unit tests _should_ evolve with the implementation they test), and on top of that it also has nothing to do with TDD.

### Question 2 [3 points]

You have just started working as an intern in XYZ Corp. On your first day you start setting up your project: You need to clone the project's GitHub repo, create a new branch called `fix_color_scheme`, do the necessary changes to the code, commit all your changes, and push the branch to the remote repository. Select below the workflow(s) that you would follow. Remember that multiple answers may be possible.

- [n] Workflow 1 (__0.75 pt__)
```
mkdir WebApp && git clone git@github.com:XYZ/WebApp.git
git checkout -b fix_color_scheme
### changes multiple files within the "WebApp" directory
git add .
git commit -m "fix missing blue in the color palette, close #59"
git push origin fix_color_scheme
```
- [n] Workflow 2 (__0.75 pt__)
```
git clone git@github.com:XYZ/WebApp.git
git checkout fix_color_scheme
### changes multiple files within the "WebApp" directory
git add .
git commit -m "fix missing blue in the color palette, close #59"
git push origin fix_color_scheme
```
- [y] Workflow 3 (__0.75 pt__)
```
git clone git@github.com:XYZ/WebApp.git && cd WebApp/
git checkout -b fix_color_scheme
### changes multiple files within the "WebApp" directory
git commit -am "fix missing blue in the color palette, close #59"
git push origin fix_color_scheme
```
- [y] Workflow 4 (__0.75 pt__)
```
git clone git@github.com:XYZ/WebApp.git && cd WebApp/
git branch fix_color_scheme
git checkout fix_color_scheme
### changes multiple files within the "WebApp" directory
git add .
git commit -am "fix missing blue in the color palette, close #59"
git push origin fix_color_scheme
```

__Explanation for students__: Options 3 and 4 are correct and equivalent to each other: `git checkout -b` is shorthand for `git branch` followed by `git checkout`, and `git commit -am` commits all changes on tracked files without adding new files whereas `git add .` followed by `git commit -am` adds to the commit all the non-ignored files in the current directory and then commits the changed files (including the newly added ones). Options 1 and 2 are wrong because they operate one directory above the one in which the repo is cloned, so all `git` operations following the clone will fail.

### Question 3 [3 points]

Your software company employs Scrum to develop their applications. On your second day there, you attend your first standup meeting. Which of the following statements is/are appropriate for you to make in this meeting?

- [y] "Yesterday, I cloned the `WebApp` repo and worked on a minor issue in the color scheme. I ran into a problem while cloning the repo. Mike helped me troubleshoot it. It was a problem related to privileges."  (__0.75 pt__)
- [n] "Yesterday, I ran into a problem while I was cloning the `WebApp` repo (I am new and just got a new company-issued laptop). I searched StackOverflow but there were too many possibilities. Then, I talked to Jack, who didn't have time. He pointed me to Mike. Mike figured it must be because I am still not a collaborator, so he invited me. Everything works now. After I managed to finally clone the project, I worked on a very small portion of the task to change colors in the app." (__0.75 pt__)
- [y] "Today, I will work with Mike to design the new interface." (__0.75 pt__) 
- [n] "Today, I will be getting a new laptop from the sysadmins. They told me they might be late, so I will check on them right away. I asked for a Mac. Is this good for us?" (__0.75 pt__)

__Explanation for students__: Options 1 and 3 represent correct answers to the standup questions "what I did yesterday" and "what I will do today", respectively. They're brief and to the point. Option 2 is incorrect because it relays a lengthy story that is irrelevant in a Scrum standup meeting. Option 4 is incorrect primarily because it poses a question to the team, which by definition has no place in a Scrum standup. It also doesn't strictly answer a relevant Scrum question.

### Question 4 [3 points]

In Scrum, what should happen to your backlogs when your team discovers a major bug in the middle of a sprint?

- [n] The bug becomes an item in the sprint backlog and the team starts working on it right away. (__0.75 pt__)
- [n] The bug becomes an item in the sprint backlog. After the team finishes their other tasks for the sprint, if there is time, they fix the bug. (__0.75 pt__)
- [n] The bug becomes an item in the product backlog. It has the topmost priority. (__0.75 pt__)
- [y] The bug is communicated to the product owner, and she puts it in the product backlog and decides what priority it gets. (__0.75 pt__)

__Explanation for students__: Since this is a _major_ bug, it requires significant resources and perhaps even entails a redesign, so it would cause the development team to not fulfill its commitments for the current sprint. The work therefore exceeds the scope of the current sprint, and the product owner must decide how to prioritize it relative to the other customer wishes present in the product backlog. Minor bugs can be fixed as part of the current sprint without a problem.

### Question 5 [4 points]

During your internship with XYZ Corp., a client requests a feature that allows users to record their preferred colors. Your app now needs to communicate with an external service `FavoriteColorService`, which keeps track of favorite colors. The app generates the favorite colors as `ArrayList<Color> colors`, where `Color` is a [built-in class](https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html). However, `FavoriteColorService` accepts only raw hexadecimal values.

Which design pattern would you use to connect your app to the service? There is exactly one correct choice.  Justify briefly your answer in the indicated space below the choices.  (__2 pts__)

- [y] Adapter
- [n] Singleton
- [n] Decorator
- [n] Factory

 _Justification_ __(2 pt)__: The problem (i.e., that the app generates an `ArrayList<Color>` whereas the service expects raw hexadecimal values) is a classic for the _Adapter_ pattern: we need to transform the app's output into the service's expected input. Said differently, an _Adapter_ wraps the service interface and transforms it into the interface that the app expects.

__Explanation for students__: Option 3 is not a good choice because a Decorator changes the functionality of the wrapped object or service, while typically keeping the interface unchanged -- this would not solve the problem. Options 2 and 4 are completely unrelated to the problem described here.

### Question 6 [3 points]

An ex-employee of your software company wrote a library that creates different shapes. In your current project you are using the compiled code, with no access to the source code. You discover a bug in the library's behavior. Which of the following are potential solutions to this problem?

- [y] If it doesn't affect your code, you leave it as-is and file a bug (__0.75 pt__)
- [y] You re-write the library (__0.75 pt__)
- [y] You use the Decorator design pattern to correct the behavior (__0.75 pt__)
- [n] You write a test case that demonstrates the misbehavior (__0.75 pt__)

__Explanation for students__: The problem you are facing is that a closed-source library misbehaves. If this misbehavior does not affect your code, then you can just move on responsably (option 1). If that behavior does affect your code, then to solve the problem you need to change the bad behavior: either you replace the library with something new (option 2) or you add a layer on top of the library that corrects the bad behavior (option 3). While option 4 is a useful thing to do, it does not solve the problem.

### Question 7 [4 points]

You have been tasked to add some new functionality to the project. This functionality entails adding a new operation that computes the area of objects of types `Circle`, `Rectangle`, `Triangle`, and `Hexagon`. You know that soon you will have to add more operations to all of these classes. If you don't want to change every time the classes, which design pattern would you choose to add the functionality for computing the area? Please justify briefly your choice in the indicated space.
 
- [n] Decorator
- [n] Adaptor
- [n] Factory
- [y] Visitor

 _Justification:_  The problem is that we want to add to the functionality of shape objects an `area()` method but not change the implementation of those objects. The most elegant approach is option 4: use a _Visitor_, as shown in [lecture](https://moodle.epfl.ch/mod/page/view.php?id=963490), to implement each new functionality (area calculation, etc.) in a separate class that implements a `ShapeVisitor` interface, and have each shape object provide an `accept(ShapeVisitor v)` method. We then rely on double dispatch to dynamically add future functionality, including area calculation, to the shape objects. 

__Explanation for students__: We awarded __2 pts__ for the correct choice, and __2 pts__ for a correspondingly correct justification. Exceptionally, we gave full credit for choosing options 1 and/or 2 as correct, but only if they were correctly and convincingly justified. Option 3 is plain wrong, because it is a creational pattern. 


### Question 8 [2 points]

Refactor the following code in place to simplify it.

```java
public void printMessage(String message) {
    if (isNotNull(message)) {
        System.out.println(message);
    }
}

private boolean isNotNull(Object o) {
  return o != null;
}
```

___Answer:___

```java
public void printMessage(String message) {
    if (message != null) {
        System.out.println(message);
    }
}
```

__Explanation for students__: The solution is self-explanatory. An answer that did not remove `isNotNull()` does not count as simplifying the code, so it earned 0 pt. Relying solely on a `@NonNull` anotation for the method's parameter earned just 1 pt, because annotations are not enforced by the compiler. An answer that used `if (!(message == null))` earned only 1.5 pt because the condition is unnecessarily complex. Printing without checking if the string is null earned only 0.5 pt, because it changes the behavior of the method. An answer that throws an exception when the string is null earned only 0.5 pt because it significantly changes the intended behavior.


### Question 9 [8 points]

Transform the code below to use a "parametric singleton design pattern with lazy initialization," defined as follows:

- Unlike a normal singleton that ensures there is exactly one instance of the class overall, a "parametric" singleton ensures that there is exactly one instance of the class _for a given set of parameter values_. In the present case, we want a single instance of `BoardGame` for each distinct value of `numberOfPlayers`.  
- "Lazy initialization" of a singleton ensures that we only create whatever instances are truly needed.

```java
public class BoardGame {
    private int numberOfPlayers;
    
    public BoardGame(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
```

___Answer:___

```java
public class BoardGame {
    private static Map<Integer,BoardGame> instances = new HashMap<Integer, BoardGame>();
    private int numberOfPlayers;

    private BoardGame(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public static synchronized BoardGame getBoardGameInstance(int numberOfPlayers) {
        if (instances.containsKey(numberOfPlayers)) {
            return instances.get(numberOfPlayers);
        }
        BoardGame instance = new BoardGame(numberOfPlayers);
        instances.put(numberOfPlayers, instance);
        return instance;
    }
}
```

__Explanation for students__: For the _parametric_ aspect of the singleton (i.e, have no more than one instance for each value of `numberOfPlayers`), we employ a map that stores for each value of `numberOfPlayers` a reference to the corresponding instance. We make this map `static` in order to have a unique map for all objects of type `BoardGame`, and we make it `private` so that only the `BoardGame` class can use it.  The constructor is `private` as in classic singletons. For the _lazy_ aspect of the singleton, the `getBoardGameInstance()` method checks whether an instance has already been created for the desired number of players and, if not, creates it on demand.

If a proper singleton was implemented, we awarded 4 pts. If the singleton is parametric, we awarded another 3 pts. If it is lazy, we awarded an additional 1 pt. If the method to create the singleton is not static, we removed 3 pts. If the code forgets to return an instance when it is not contained in the map, we removed 1 pt. If instead of a map the answer used a set, list, and/or threw exceptions, we removed 4 pts. If the code uses just an unparameterized Map interface, we removed 0.5 pt.

We also noted but did not penalize the following mistakes: cast `int` to `Integer`, use `Map<int, BoardGame>`, update the default constructor, use `hasKey` instead of `containsKey` or checked that `numberOfPlayers >= 0`.

### Question 10 [8 points]

The following code has one or more problem(s). Use only additions and deletions (no modifications of the existing interface) to make the code be consistent with a correct implementation of the Observer design pattern.

```java
public class Test {
    public static void main(String[] args) {
        SwEngMidtermResultsBoard swEngBoard = new SwEngMidtermResultsBoard();
        Student student = new Student();
        swEngBoard.addObserver(student);
        swEngBoard.setMessage("Everyone has 100 points!");
    }
}
```


```java
public interface Observable {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}
```

```java
public interface Observer {
    void update(Observable observable);
}
```

```java
public class Student implements Observer {

    @Override
    public void update(Observable observable) {
        SwEngMidtermResultsBoard board = (SwEngMidtermResultsBoard)observable;
        System.out.println(board.getMessage());
    }
}
```

```java
public class SwEngMidtermResultsBoard  implements Observable {
    private List<Observer> observers = new ArrayList<Observer>();
    private String message;

    public void setMessage(String message) {
        this.message = message;
        this.notifyObservers();  // <--- ADD THIS LINE <--- 
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void addObserver(Observer observer) {
        System.out.println("Adding an observer!");
        this.observers.add(observer);  // <--- ADD THIS LINE <--- 

    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            System.out.println("Notification starts!");
	    observer.update(this);  // <--- ADD THIS LINE <--- 
        }
    }
}
```
__Explanation for students__: Please see above the three lines marked with `<-- ADD THIS LINE <--`. The problems in the given code were that two of the overriden methods were incomplete, and the code forgot to actually trigger notification:

- `addObserver()` forgot to add the new observer to the list of observers (__2 pts__);
- `notifyObservers()` forgot to update the observers (__2 pts__);
- `setMessage()` forgot to start the notification process (__4 pts__).

If the answer modified the signature of `Student.update()` to get the data as argument and call `setMessage`, we removed 4 pt. If the signature of `Student.update()` was modified in any way (e.g., to take an `Object` as argument instead of an `Observable`), we removed 4 pts. If `update()` takes a `SwengMidtermResultsBoard` as argument instead of an `Observable`, we removed 4 pts. If the answer suggests you did not understand that the `for` loop must iterate over the observers and used an undeclared loop counter, we removed 1 pt.

We also noted but did not penalize the following mistakes: calling `notifyObserver()` in the main method instead of in `SwengMidtermResultsBoard.setMessage`; getting the `index` of the observer in the list and using `remove(index)` instead of directly calling `remove(observer)`; and checking that the observer is not already in the map before adding it.
