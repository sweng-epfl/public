# Exercise 2: Java Basics

Java is the language we will use for development in both SwEng and SDP.
These exercises will help get you started with Java, or provide a review if you have used it before.
More specifically, you will learn how to compile Java code, run and debug it from the command line.

## Prerequisites

First, you should complete at least Part 1 of the SwEng [bootcamp](https://github.com/sweng-epfl/Bootcamp).
Ensure that Java has been successfully installed on your machine.
Open a command line and run:

```sh
javac -version
```

Should return the Java compiler version you have installed.


Check that the Java debugger `jdb` is also installed:

```sh
jdb
Initializing jdb ...
> quit

```

## Exercise 2.1: Compiling Java

Copy the following code into a file `TestProgram.java`.
You can use whatever working directory you like. 

```java
public class TestProgram {
  
  public static void main(String[] args) {
    // array to sum
    int[] numbers = new int[]{ 1, 2, 3, 4, 5};

    int sum = 0;

    for (int i=0; i < numbers.length ; i++) {
      sum = sum + numbers[i];
    }

    System.out.println("Sum value of array elements is : " + sum);
  }

}
```

It's a simple program that sums values in an array.

Compile the program:

```sh
javac TestProgram.java
```

Run the program:

```sh
java TestProgram
Sum value of array elements is : 15
```

## Exercise 2.2: The Debugger is Your Best Friend

Imagine you run your freshly compiled program. 
It doesn't output the correct result, or the program crashes.

How do you figure out what's going wrong? You need to _debug_ your program. 

Typically there are two ways to do so. 

1. Put print statements all over your program to track progress and understand "where" in the code the bug is. This is called "printf" debugging, and can be effective.
2. Use a _debugger_, a tool that allows you to stop execution at predetermined code locations and examine the state of your program (and much more).

### Exercise 2.2.1: Printf debugging

Modify the above array summation program with println statements to track the values of `sum` and `numbers[i]` throughout the loop iterations. Recompile and run the program, and you should see something like this:

```sh
current sum value is : 1, current numbers[i] is 1
current sum value is : 3, current numbers[i] is 2
current sum value is : 6, current numbers[i] is 3
current sum value is : 10, current numbers[i] is 4
current sum value is : 15, current numbers[i] is 5
Sum value of array elements is : 15
```

### Exercise 2.2.2: Use the Debugger

Now you will use the debugger to examine these values live, while the program runs.

First, recompile your program with the `-g` flag. This tells the compiler to add extra information to the class file so that the debugger is aware of local stack variables. 

```
javac -g TestProgram.java
```

Run the program with jdb, the Java debugger.

```sh
jdb TestProgram
Initializing jdb ...
>
```

The debugger has many commands, but we will review a few basic ones to get you started. 
At this point, the program is not being run yet. You can run it with the `run` command, but you want to stop execution at some point so you can examine variable values. You can do this with a _breakpoint_. In jdb, use the `stop` command:

```
> stop in TestProgram.main
Deferring breakpoint TestProgram.main.
It will be set after the class is loaded.
```

(You can also use `stop at <classname>:<line>` to break at a specific line in the code.)

Now the program will stop in function `main` in class TestProgram if and when it reaches that point.

Run the program:

```
> run
run TestProgram
Set uncaught java.lang.Throwable
Set deferred uncaught java.lang.Throwable
>
VM Started: Set deferred breakpoint TestProgram.main

Breakpoint hit: "thread=main", TestProgram.main(), line=5 bci=0
5        int[] numbers = new int[]{ 1, 2, 3, 4, 5};

main[1]
```

Execution has stopped. Use `list` command to view the source code where the program has stopped:

```
main[1] list
1    public class TestProgram {
2
3      public static void main(String[] args) {
4        // array to sum
5 =>     int[] numbers = new int[]{ 1, 2, 3, 4, 5};
6
7        int sum = 0;
8
9        for (int i=0; i < numbers.length ; i++) {
10          sum = sum + numbers[i];
```

You can now execute the program line by line using the `next` command. `next` until you are at line 9, at the beginning of the for loop. `list` should show:

```
5        int[] numbers = new int[]{ 1, 2, 3, 4, 5};
6
7        int sum = 0;
8
9 =>     for (int i=0; i < numbers.length ; i++) {
10          sum = sum + numbers[i];
11          System.out.println("current sum value is : " + sum + ", current numbers[i] is " + numbers[i]);
12        }
13
14        System.out.println("Sum value of array elements is : " + sum);
```

The `print` command can be used to show the values of in scope objects. Try `print`ing some local objects.

```
main[1] print numbers[0]
 numbers[0] = 1
main[1] print sum
 sum = 0
 ```

Now, step through the for loop and use `print` to examine how the values change across loop iterations. You can also use the `locals` command to print all variables in the local scope.

The `cont` command will continue execution, either until another breakpoint is hit, or the program terminates (or crashes).

At any time you can use the `help` command to see all available commands in jdb.

This exercise has covered just a few basics that should enable you to effectively debug your Java programs. You can find another basic tutorial [here](https://docs.oracle.com/javase/9/tools/jdb.htm#JSWOR-GUID-B801F121-35B5-4FE2-A307-950412CE4E99) and a more comprehensive one [here](https://www.tutorialspoint.com/jdb/).
