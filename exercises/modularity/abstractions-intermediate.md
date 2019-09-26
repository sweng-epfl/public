# Abstractions - Intermediate

In these exercises, you will apply the knowledge you learned in the Basics to more examples, with less guidance from the statement. 


## `gets`

The function `gets` is considered such a bad abstraction that it is the only function ever to be _removed_ from the C standard library, despite the fact that this may break old code that uses it.

```c
char* gets(char* str);
```

Its description is simple: it reads user input from standard input, until the user inputs a newline (i.e., hits the "Enter" key). This input is then copied into the argument `str`.

Why is this a bad abstraction? How would you fix it?


## `java.lang.Cloneable`

The [`java.lang.Cloneable`](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/lang/Cloneable.html) is considered one of Java's major design flaws, for instance by [Joshua Bloch](https://en.wikipedia.org/wiki/Joshua_Bloch), who led the development of some of Java's other features.

Why is `Cloneable` not a good abstraction? How would you fix it?


## The TCP header

Take a look at the [TCP header definition](https://en.wikipedia.org/wiki/Transmission_Control_Protocol#TCP_segment_structure). There is one major abstraction breakage; where? Is it truly necessary? (Optionally, can you think of a reason why it exists?)


## IEEE floating-point

The [IEEE 754 standard](https://en.wikipedia.org/wiki/IEEE_754) is the most common format for real (i.e.,non-integer) numbers in use today, such as in Java's `float` and `double` primitives. However, there are clear use cases where it does not work well as an abstraction.

Can you think of one case where it is not a good abstraction? What about a case where it is?


