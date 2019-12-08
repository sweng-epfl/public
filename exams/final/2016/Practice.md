# Part 1: Practice [60 points]

In this part of the exam, you will write code that must compile and run.
Make sure it is rock-solid, follows the SwEng best practices, and is both readable and concise. 
You may use comments and/or Javadoc, but you are not required to do so. Test your code well.

We provide you with a project that can be used with Android Studio and built with Gradle. 
You can use any IDE as long as the code and tests build using `gradlew build`.
To check line coverage, you may:

- [Run coverage in Android Studio](http://sweng.discoursehosting.net/t/code-coverage-in-android-studio/292), or
- Run `gradlew test` then `gradlew jacocoTestReport` from the command line, or
- Push your code to GitHub, and check the corresponding job https://jenkins.epfl.ch/view/Final/job/final-GASPAR on Jenkins. 
  *(This is what the staff will take into consideration when grading)*

**Do not modify in any way the public interface** of the code we give you, including:

- Do not alter method definitions, not even the checked exceptions.
- Do not add public, protected, or package-private methods or fields; 
  if you wish, you can add private members and classes, but nothing visible from the outside.

Make sure that **your code builds on Jenkins**. You will receive 0 points if your code does not pass the basic tests on Jenkins. 
It is 100% your responsibility to check the status of your `final-GASPAR` job on Jenkins, and we will not make any exceptions. 
 We will use many automated tests when grading, so if we cannot build and run your code, **you will receive 0 points**.

**Code that has been commented out does not count**. 
If you choose to push commented code to the repo, we will not accept any requests to uncomment it for purposes of grading.

## The Teal (Toy Exam Abstract Language) specification

Teal is a simple programming language for adding integers. 
A Teal program defines a library of functions and then invokes these functions to compute expressions. 
A library is a block of code that defines one or more `functions`, with each line of code defining exactly one function.

For example, you can define a library that looks as follows:
~~~~
double(x): x + x 
triple(x): !double(x) + x
tenTimes(x): !triple(!double(x)) + !double(!double(x))
hundredTimes(x): !tenTimes(!tenTimes(x))
theAnswer(): 42
~~~~

and then call the Teal interpreter to invoke these functions, such as `invoke("theAnswer", null)` returns 42, 
or `invoke("hundredTimes", 13)` returns 1300.

Functions in Teal take one parameter or none at all, and return an integer. 
A function declaration consists of the function name followed by zero or one parameter enclosed in parentheses, then a colon ":",
followed by the function body, which is an `expression`. Function names are unique within the library. 

An `expression` can be:

- a literal integer value (e.g., 2, 10), or 
- the name of the function's parameter, which at runtime gets replaced by the parameter's value, or 
- an addition operation consisting of two expressions separated by "+", or
- an invocation of a function, beginning with a "!", followed by the function name, 
  and then the parameter expression (if any) between parentheses. 
  Teal does not support recursion, neither direct nor indirect via another function.

## Question 1: `TealParser` [`20` points]

The Teal parser takes the code for a Teal library and turns it into an object that can be used by the interpreter (in the next question) to execute Teal programs. 
It's in the `TealParser` class found in `final/src/main/java/ch/epfl/sweng/parser/TealParser.java`. 

Write a test suite that achieves 95% line coverage on the provided `TealParser` implementation (including the nested class), 
using the skeleton we provided in your exam repo. Do not modify the `TealParser` class in any way.

Our grading will use a coverage utility function similar to the one used in the midterm.

## Question 2: `TealInterpreter` [`40` points]

The Teal interpreter enables a programmer to invoke functions defined in the library. 
It takes as arguments a function and a single integer parameter (`null` if the function is parameter-less), 
and returns an integer representing the result of invoking that function with the parameter (if any).

The interface of the interpreter is in `src/main/java/ch/epfl/sweng/interpreter/TealInterpreter.java`, 
and a factory to create interpreters in `src/main/java/ch/epfl/sweng/interpreter/TealInterpreterFactory.java`.

For each of the following tasks you need to employ a different design pattern studied in SwEng.

You are allowed to add any new classes you need, provided they respect the given interfaces. Do not modify the given interfaces.


**(2.a)**: Fill in the `basicInterpreter()` method of `TealInterpreterFactory`, 
such that it returns a basic implementation of TealInterpreter that invokes a function from a given library and returns the result.

*Example*: If you have a library with one function `f(n): n + 1`, then the following Java statement
~~~~
int result = myBasicInterpreter.invoke("f", 41);
~~~~
would return the result `42`.

**(2.b)**: Fill in the `cachedInterpreter()` method of `TealInterpreterFactory`, 
such that it returns an interpreter that caches top-level results: 
if asked to perform an invocation that is identical to one it did in the past, the interpreter simply returns the cached result.

*Example*: If you have a library with one function `f(n): n + 1`, then
~~~~
int result = myCachedInterpreter.invoke("f", 41);
~~~~
invokes function f with parameter 41 and returns 42, after which
~~~~
int resultAgain = myCachedInterpreter.invoke("f", 41);
~~~~
does not invoke function f but immediately returns 42, after which
~~~~
int resultOnceMore = myCachedInterpreter.invoke("f", 2);
~~~~
invokes function f with parameter 2 and returns 3.

Your code should throw adequately specific runtime exceptions or custom versions thereof. 

For instance, throw `IllegalArgumentException` for arguments whose values are invalid, 
and throw `TealInterpretationException` if you discover during interpretation that the invoked function itself is invalid.
