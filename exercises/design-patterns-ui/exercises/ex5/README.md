# Exercise 5
In this scenario, you want to use a `Logger` in all your classes to centralize all the logging. Assume that the `Logger` is used in various parts of your system and it takes a lot of time to create a new Logger instance, refactor the [`Logger`](src/main/java/ex5/Logger.java) class to handle this scenario.

```java
public class Logger {

    public void print(){
        System.out.println("Logged");
    }
}
``` 

In order to select which design pattern to use, answer this question: do you want many `Logger` instances? Do you want to create them every time?