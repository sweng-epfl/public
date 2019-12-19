# Software Engineering - Final Exam

## Part 2: Practice [60 points]

In this part of the exam you need to write real code.
As is often the case in software engineering, the problem itself is fairly easy to reason about, and what really matters is that your code employs solid software engineering principles.
You should write code that is:
- Correct and robust
- Readable and concise
- Judiciously commented

You should use the same development environment as for the [bootcamp](https://github.com/sweng-epfl/public/blob/master/bootcamp/Readme.md).
To validate your installation, you can run `java -version` from the command line; you are good to go as soon as the command outputs something like this:

```
java version "12.0.2"
Java(TM) SE Runtime Environment (build 12)
Java HotSpot(TM) 64-Bit Server VM (build 23.2-b04, mixed mode)
```

The version number need not be identical to the one above, but it must start with `12`.

> :information_source: If you do not have Java 12, you can get it from [https://jdk.java.net/archive/](https://jdk.java.net/archive/). Please note that this exam cannot be done in Java 13. 

You can run locally `./gradlew build`.
You are free to use an editor or IDE of your choice, as long as the code and tests build using `./gradlew build`.
On Windows, use `gradlew.bat` instead of `./gradlew`.

Please remember that we will only grade whatever is in your `master` branch.
We will use `./gradlew build` to build your code.

We provide you with continuous integration using [Travis](https://travis-ci.com).
It automatically retrieves the code from your `master` branch.
If you forget to commit a file, or if you push the wrong version or a version that breaks your previously working version, you may lose all points, so please check Travis after every push.

---

## Prologue

You just started at your new job: a software engineer for the "people@SwEng" application, which lets people search for other people and learn more about them.

The code of the application is in the [source directory](src/main/java).
_(You are free to add new files/classes if you want)_

Your colleagues have already created a [test directory](src/test/java) in which you'll add test classes.
_(You can write tests in any file you want within that directory)_

Your colleagues are responsible for the app's core logic: the [`Directory`](src/main/java/Directory.java) interface, which finds people matching a given search text.
They have not finished the implementation yet, so you will need to write your own implementations when testing. 

---

## Part 1: The View [5 points]

Your first task is to write the UI of a basic console application.

Specifically, you have to implement the [`AppView`](src/main/java/AppView.java) class's `toString` method,
which returns a textual description of the view, including the commands available to the user.

For instance, the main view of the application, whose text is `"Welcome!"` and whose commands are `"search"` and `"show"`, should result in the following text:
```
Welcome!
Available commands:
- search
- show
```

Implement `AppView::toString` as specified by its Javadoc, and write tests that achieve 100% statement coverage on `AppView::toString`.
_(You qualify to get points for coverage if and only if your implementation of `AppView::toString` passes some of our own basic correctness tests)_

---

## Part 2: Caching [10 points]

Your second task is to implement caching on top of directories, to avoid needless requests.

Caching is performed by the [`CachingDirectory`](src/main/java/CachingDirectory.java) class.

Implement `CachingDirectory` as specified by its Javadoc, and write tests that achieve 100% statement coverage on `CachingDirectory`.
_(You qualify to get points for coverage if and only if your implementation of `CachingDirectory` passes some of our own basic correctness tests)_

---

## Part 3: Overrides [15 points]

Your third task is to implement overrides, that is, special results on top of an existing directory, such as returning the current user when they search for "me".

Overrides are performed by the [`OverridableDirectory`](src/main/java/OverridableDirectory.java) class.

Implement `OverridableDirectory` as specified by its Javadoc, and write tests that achieve 100% statement coverage on `OverridableDirectory`.
_(You qualify to get points for coverage if and only if your implementation of `OverridableDirectory` passes some of our own basic correctness tests)_

---

## Part 4: The Controller [30 points]

Your final task is to write the Controller behind the console application.

Specifically, you have to implement the [`AppController`](src/main/java/AppController.java) class's `handle` method.

Note that the final piece of the application, the Model, is the [`Directory`](src/main/java/Directory.java) interface, which as mentioned previously is not your responsibility.

The [`App`](src/main/java/App.java) class contains a console application using the Model, View and Controller,
which you may find useful to manually test your code.

There should be three views in the application: the main view, the search view, and the show view.
The exact output of the View is documented in the View's Javadoc.

The specifications of each view's description are as follows:
- The main view should have the description `"Welcome!"`.
- The search view should have as description the names of each search result, in the order in which the directory returns them, separated by a line separator.
  - If there are no search results, the search view should have the description `"The search returned no results."` instead.
- The show view should have as description the non-null fields of the person, in the order in which they are declared in the class, separated by a line separator.
  - If the directory does not return exactly one person given the search text, the show view should have the description `"The search did not return exactly one person."` instead.

All views should have the same commands:
`"search"`, which should display search results given a search text, 
or `"show"`, which should display details about a single person given a search text.
The user should also be allowed to enter an empty input, which should display the main view, although this is not considered a command.
The exact input format is documented in the Controller's Javadoc.

Implement `AppController::handle` as specified by its Javadoc and by the above description.
_(We do not require you to write tests, but we will grade you using automated tests that check the various features and corner cases)_
