# CS305 – Software Engineering

## Midterm Exam

# Part 2: Theory [40 points]

This second part of the midterm exam has 10 questions, with the number of points indicated next to each one.

This file is named `Theory.md`. Provide the answers in this file and commit the file to the `master` branch of this `midterm-GASPAR` repo. Do not change the name of the file, and do not change the existing text unless the question statements asks you to do so. We will only grade the master branch. Do not introduce extraneous spaces, as that will break our scripts and you will lose points.

The answer choices are provided as `[ ]`.  You should change each one to `[y]` for each correct answer, or to `[n]` for each incorrect answer.  This requires that you replace the space between the brackets with either `y` or `n`. Unless otherwise specified, questions may have zero, one, or more correct answer choices. If you leave a checkbox empty, it means that you did not finish answering the corresponding question, so we will not grade that question and you will get 0 points for it. 

### Question 1 [2 points]

Which of the following is/are true about test-driven development (TDD)? 

- [ ] TDD ensures there are no bugs in the code
- [ ] TDD is synonymous with writing unit tests
- [ ] Tests are the first thing a developer writes
- [ ] The unit tests should not be changed once they have been written

### Question 2 [3 points]


You have just started working as an intern in XYZ Corp. On your first day you start setting up your project: You need to clone the project's GitHub repo, create a new branch called `fix_color_scheme`, do the necessary changes to the code, commit all your changes, and push the branch to the remote repository. Select below the workflow(s) that you would follow. Remember that multiple answers may be possible.

- [ ] Workflow 1 
```
mkdir WebApp && git clone git@github.com:XYZ/WebApp.git
git checkout -b fix_color_scheme
### changes multiple files within the "WebApp" directory
git add .
git commit -m "fix missing blue in the color palette, close #59"
git push origin fix_color_scheme
```
- [ ] Workflow 2
```
git clone git@github.com:XYZ/WebApp.git
git checkout fix_color_scheme
### changes multiple files within the "WebApp" directory
git add .
git commit -m "fix missing blue in the color palette, close #59"
git push origin fix_color_scheme
```
- [ ] Workflow 3
```
git clone git@github.com:XYZ/WebApp.git && cd WebApp/
git checkout -b fix_color_scheme
### changes multiple files within the "WebApp" directory
git commit -am "fix missing blue in the color palette, close #59"
git push origin fix_color_scheme
```
- [ ] Workflow 4
```
git clone git@github.com:XYZ/WebApp.git && cd WebApp/
git branch fix_color_scheme
git checkout fix_color_scheme
### changes multiple files within the "WebApp" directory
git add .
git commit -am "fix missing blue in the color palette, close #59"
git push origin fix_color_scheme
```

### Question 3 [3 points]

Your software company employs Scrum to develop their applications. On your second day there, you attend your first standup meeting. Which of the following statements is/are appropriate for you to make in this meeting?

- [ ] "Yesterday, I cloned the `WebApp` repo and worked on a minor issue in the color scheme. I ran into a problem while cloning the repo. Mike helped me troubleshoot it. It was a problem related to privileges."
- [ ] "Yesterday, I ran into a problem while I was cloning the `WebApp` repo (I am new and just got a new company-issued laptop). I searched StackOverflow but there were too many possibilities. Then, I talked to Jack, who didn't have time. He pointed me to Mike. Mike figured it must be because I am still not a collaborator, so he invited me. Everything works now. After I managed to finally clone the project, I worked on a very small portion of the task to change colors in the app."
- [ ] "Today, I will work with Mike to design the new interface."
- [ ] "Today, I will be getting a new laptop from the sysadmins. They told me they might be late, so I will check on them right away. I asked for a Mac. Is this good for us?"

### Question 4 [3 points]

In Scrum, what should happen to your backlogs when your team discovers a major bug in the middle of a sprint?

- [ ] The bug becomes an item in the sprint backlog and the team starts working on it right away.
- [ ] The bug becomes an item in the sprint backlog. After the team finishes their other tasks for the sprint, if there is time, they fix the bug.
- [ ] The bug becomes an item in the product backlog. It has the topmost priority.
- [ ] The bug is communicated to the product owner, and she puts it in the product backlog and decides what priority it gets.

### Question 5 [4 points]

During your internship with XYZ Corp., a client requests a feature that allows users to record their preferred colors. Your app now needs to communicate with an external service `FavoriteColorService`, which keeps track of favorite colors. The app generates the favorite colors as `ArrayList<Color> colors`, where `Color` is a [built-in class](https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html). However, `FavoriteColorService` accepts only raw hexadecimal values.

Which design pattern would you use to connect your app to the service? There is exactly one correct choice.  Justify briefly your answer in the indicated space below the choices.

- [ ] Adapter
- [ ] Singleton
- [ ] Decorator
- [ ] Factory

- ___Justification:___

### Question 6 [3 points]

An ex-employee of your software company wrote a library that creates different shapes. In your current project you are using the compiled code, with no access to the source code. You discover a bug in the library's behavior. Which of the following are potential solutions to this problem?

- [ ] If it doesn't affect your code, you leave it as-is and file a bug
- [ ] You re-write the library
- [ ] You use the Decorator design pattern to correct the behavior
- [ ] You write a test case that demonstrates the misbehavior

### Question 7 [4 points]

You have been tasked to add some new functionality to the project. This functionality entails adding a new operation that computes the area of objects of types `Circle`, `Rectangle`, `Triangle`, and `Hexagon`. You know that soon you will have to add more operations to all of these classes. If you don't want to change every time the classes, which design pattern would you choose to add the functionality for computing the area? Please justify briefly your choice in the indicated space.
 
- [ ] Decorator
- [ ] Adaptor
- [ ] Factory
- [ ] Visitor

- ___Justification:___

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

### Question 9 [8 points]

Transform the code below to use a "parametric singleton design pattern with lazy initialization," defined as follows:

- Unlike a normal singleton that ensures there is exactly one instance of the class overall, a "parametric singleton" ensures that there is exactly one instance of the class _for a given set of parameter values_. In the present case, we want a single instance of `BoardGame` for each distinct value of `numberOfPlayers`. 
- "Lazy initialization" of a singleton ensures that we only create whatever instances are truly needed.

```java
public class BoardGame {
    private int numberOfPlayers;
    
    public BoardGame(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
```

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
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void addObserver(Observer observer) {
        System.out.println("Adding an observer!");
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            System.out.println("Notification starts!");
        }
    }
}
```

 
