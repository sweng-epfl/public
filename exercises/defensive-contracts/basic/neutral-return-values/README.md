# Neutral Return Values
This exercise will teach you about returning **neutral values**. You will be given a Java program that uses `null` values as neutral values. You will need to modify the program so that it adopts a more objet-oriented approach of dealing with neutral values. To achieve that, you will make use of Java 8's [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html), introduced earlier.

Sometimes the best response to bad input data is to continue operating and simply return a value that is known to be harmless. A numeric computation might return 0; a string operation might return an empty string; a pointer operation might return a null pointer. This is of course not always the case, you need to make the right judgment.

The `null` value is the neutral value used by Java programs. Nevertheless, `null` values can be harmful and error-prone if poorly used. You can be the best Java programmer in the world and still see your program crash because of a **NullPointerException**.

As a result, Java 8 introduced the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) container object. The purpose of the class is to provide a type-level solution for representing optional values instead of using `null` references. The goal behind Optional is that you never need to return `null` values any more.

In this exercise, you will modify a program that makes use of `null` values as neutral return values. You will replace `null` usages with instances of `Optional`.

### Tasks
You are given a Java project you need to modify:

1. Make both the [search](src/main/java/ch/epfl/sweng/defensive/neutral/returned/value/goolge/Goolge.java#L10) and [next](src/main/java/ch/epfl/sweng/defensive/neutral/returned/value/model/Result.java#L21) methods return optional results, i.e. `Optional<Result>`.
1. Adapt the [main](src/main/java/ch/epfl/sweng/defensive/neutral/returned/value/Main.java#L9) function of [Main.java](src/main/java/ch/epfl/sweng/defensive/neutral/returned/value/Main.java) in accordance with your changes.
1. Test the program to validate your implementation.
