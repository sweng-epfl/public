You start implementing a super-duper application. Since your application is used by users, you go and 
implement a class `User`. Later on, during development you realize that you do not keep enough information for a user.  
Unfortunately it is too late to change the `User` class, so you decide to use the **decorator pattern**.
Specifically you want to introduce a first name, last name, and an age for a user. 

Create a `DecoratedUser` class and make sure you pass the `testDecoratedUser` test from the `ApplicationTest` class.
After some time, you decide to have the usernames only in uppercase. Write a class named `CapitalizedUser` 
and verify that you pass the test `testCapitalizedUser`.