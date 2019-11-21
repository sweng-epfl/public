# Exercise 5
In this scenario, you want to use a `Logger` in all your classes to centralize all the logging. Assume that the `Logger` is used in various parts of your system and it takes a lot of time to create a new Logger instance, refactor the `Logger` class to handle this scenario.

```java
public class Logger {

    public void print(){
        System.out.println("Logged");
    }
}
``` 

In order to select which design pattern to use, answer this question: do you want many `Logger` instances? Do you want to create them every time?

**ANSWER:** In this exercise, it is said that creating a new `Logger` instance takes a lot of time. Then, to avoid wasting lots of time, we would like to have only one instance of `Logger`. Hence, we have to turn `Logger` into a singleton. There are multiple ways to do this, one is presented in the [`Logger`](src/main/java/ex5/Logger.java) class and another in the [`Logger2`](src/main/java/ex5/Logger2.java) class.