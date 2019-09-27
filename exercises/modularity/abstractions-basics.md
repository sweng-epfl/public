# Abstractions - Basics

Separating a system into modules is good, but how should those modules communicate with each other? That's what _abstractions_ are all about.

An abstraction is a simplified model of a system. Abstractions can be high-level, such as representing a [mechanical printing press](https://en.wikipedia.org/wiki/Original_Heidelberg_Platen_Press) as "something that can write text", which is a description that could also apply to a human with a pencil! Abstractions can also be low-level, such as representing that same printing press as a machine that takes in a template, ink, sheets of paper, and performs operations resulting in modified sheets of paper.

There are two main metrics to judge an abstraction:
- Does it expose what its consumers need, at the right level, and _no more than that_?  
  A good abstraction will "speak" the same language as its users, and not bother them with unnecessary details.
- Does it enforce its abstraction level?  
  A good abstraction will force its users to use it properly, and not allow itself to be bypassed.

For instance, a high-level abstraction for the printing press should not require users to know what [antimony](https://en.wikipedia.org/wiki/Antimony) is, even though it is a critical component of traditional letter-printing. It should also not allow its users to "break" the abstraction by changing the ink without the press knowing about it.

It is sometimes necessary to "leak" internal details for practical performance reasons, if the underlying system cannot provide good performance without exposing those details. For instance, a video capture device can theoretically be thought of as a camera that takes pictures very often. But in practice, because of the various overheads involved in communicating with camera hardware, it is not actually feasible to implement video-capturing software by taking a lot of pictures. Thus, the abstraction will not only include a "take photo" function, but also a "take video" function, even though the latter is not theoretically needed.

In this series of exercises, you will learn how to design good abstractions, as well as how to identify poor abstractions and improve them.


## `java.util.Stack`

A good source of poor abstractions comes from legacy code, because the first few attempts at abstracting specific concepts often do not work very well; learning how to design abstractions is a skill that comes with experience.

Thus we'll start with one of the oldest collections in the Java standard library, `java.util.Stack`.

You most likely have seen the concept of a stack before: a collection in which you can push and pop items, where "pop" means "remove the last pushed item and return it". Such a collection is often called LIFO, for Last-In-First-Out.

Let's first look at one of `Stack`'s methods: [`search`](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Stack.html#search(java.lang.Object)):

> Returns the 1-based position where an object is on this stack. If the object `o` occurs as an item in this stack, this method returns the distance from the top of the stack of the occurrence nearest the top of the stack; the topmost item on the stack is considered to be at distance `1`. The `equals` method is used to compare `o` to the items in this stack.

There is already one obvious issue here: what do you think of the indexing system? Is it consistent with other collections in Java and other common programming languages? How should it have been designed instead?

There is another issue with this method. Think of how a `Stack` might be used: to store a bunch of objects of some class, and not necessarily a class written by the developer using `Stack`. What if the class' default equality method does not have the semantics that the developer wants? Can they still use `Stack::search`? How would you design this method to enable this use case?

Surprisingly, the issues we just saw are not actually the biggest problem with this class! Remember that the definition of a stack is a thing into which objects can be pushed, and objects can be popped out. Now look at the class which `Stack` extends. This class' methods can all be used on `Stack`. Is this OK? Can those methods be misused to break the abstraction provided by `Stack`? How would you have written this class instead?

In modern Java code, you should use `Deque`, which does not have these issues.


## `java.util.Date`

Let's look at another infamous example of a bad abstraction, the venerable [`java.util.Date`](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Date.html). This is the earliest class in the Java standard library to manipulate dates; as explained earlier, the first attempts at an abstraction do not always end well.

First, look at how the class represents the year. Is this what you expected? Can you imagine how you would use it most often in code? What would you have used instead?

Second, look at how the class represents the month. Do you see why months are represented this way? Do you think this is a good abstraction?

You've probably seen the deprecation warnings on `Date` suggesting you use [`Calendar`](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/Calendar.html) instead, a slightly newer class that unfortunately contains a lot of design mistakes as well.

Look at the documentation for `Calendar`'s `getTime` method. What do you think of the return type? What would you have done instead?

Consider a common use case on Unix-like platforms, such as Linux and macOS: getting the number of seconds from January 1, 1970 to a specific date. This is how Unix represents dates. This used to be done with `Date`'s `UTC` static method; how does one do this using `Calendar`? If you saw such a line of code in a program handling dates and times, would you understand it without the documentation? How would you improve this class?

In modern Java code, you should use the `java.time` package, whose design learned a lot from earlier mistakes.


## `java.net.URL` equality

As a last example, take a look at how [`java.net.URL`](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/net/URL.html) handles equality.

Is this a good level of abstraction, knowing that `URL` was supposed to be the basic class used whenever developers want to handle URLs in Java?

What is the main "surprise" this method could give to a developer who is experienced with other parts of the Java standard library?

How could this operation cause bugs on an user's machine that the developer did not notice?

In modern Java code, you can use the `URI` class instead since it does not have this design issue, but keep in mind that it is a more general abstraction since it deals with non-web cases.
