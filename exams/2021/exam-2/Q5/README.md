# Question 5: User interface [39 points]

It has been a few days since you have worked on *Sangria*, the new authentication service for EPFL, and you have made
good progress. You are now focusing on creating a simple user interface. The backend database API has changed since the
last time you used it to make your life easier.

## Part 1: The MVP pattern [26 points]

You decide to use the MVP pattern for your user interface. Implement the presenter in a new file
called `SangriaPresenter.java` in the `presenter` package.

You also decide to improve the security of *Sangria* and want to prevent attackers from brute-forcing user passwords. To
solve this problem, you decide to block the account of existing users that make three or more failed login attempts 
(that are not interspersed with a successful attempt for that account). 
Implement this new feature in your MVP application. You are free to create new classes if necessary.

You can test your implementation in the `main` method of `App.java` using one of the two views provided to you
([`SangriaSwingView`](src/main/java/view/SangriaSwingView.java)
or [`SangriaCliView`](src/main/java/view/SangriaCliView.java)) and the mock implementation of the
database, [`SangriaDatabase`](src/main/java/model/SangriaDatabase.java).

_You get 11 points if you write a working MVP application that can authenticate users and report potential errors._
_You get 8 more points if you implement the security feature correctly._
_You get 7 more points if your solution is modular and well encapsulated._

> :information_source: If for some reason your computer cannot run a basic Swing application you can use the simple command-line
> view, but otherwise, your application should work with either of them.

> :information_source: Use `./gradlew run` (or `gradlew.bat run` on Windows) to run the application.

## Part 2: Testing [13 points]

Finally, you decide to write a few end-to-end test cases for the code that you wrote. You define the following scenarios:

* Given the inputs `username` and `password`, when `password` is the correct password for `username` 
  and `username` is not blocked, then the application displays the user's information.
* Given the inputs `username` and `password`, when `password` is not the password associated with `username`
  and `username` is not blocked, then the application displays an error message.
* Given the inputs `username` and `password` and `password` is not the password associated with `username`, when the user
  tries to authenticate three or more times, then the application displays an error message and the user cannot
  authenticate anymore.

Write test cases that implement these scenarios in `PresenterTest.java`.

_You get 8 points if you write test cases that check the provided scenarios._
_You get 5 more points if your tests are independent of the usernames and passwords stored in `SangriaDatabase`._
