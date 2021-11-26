# Question 4: Experimentation [19 points]

Your new task is to create the new authentication system for EPFL: *Sangria*. The backend implementation of the database
is not entirely finished, but you are provided with its interface and a temporary local implementation in
`src/main/java/model/backend` to let you start working.

You plan to get familiar with the provided API and decide to create a very simple application using the MVC pattern. For
now, you have a simple [`ErrorView`](src/main/java/view/ErrorView.java) that displays error messages and a
[`AuthenticatedUserView`](src/main/java/view/AuthenticatedUserView.java) that is able to print an 
[`AuthenticatedUser`](src/main/java/model/AuthenticatedUser.java). Unfortunately, the database output does not match 
this representation.

Complete the code skeleton in the `main` method of `App.java`, without modifying the existing code in the rest of the codebase. 
You are allowed to create new classes if necessary.

> :information_source: When dealing with an `AuthResult` instance, you can assume that the types of the mapped attributes are
> always correct, i.e., if present, the username is always a `String` and the SCIPER is always an `Integer`.

> :information_source: Use `./gradlew run` (or `gradlew.bat run` on Windows) to run the application.

_You get 13 points if you write a working version of `App.java`._
_You get 6 more points if your solution is well encapsulated._
