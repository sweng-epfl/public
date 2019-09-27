# Solutions: Abstractions - Basics

## `java.util.Stack`

The indexing system of `search` is completely inconsistent with every other Java collection, which uses 0-based indexing. While it is not an incorrect abstraction on its own, it is clearly wrong in context. This method should use 0-based indexing.

If a class's equality semantics do not match what the developer intended, for instance because the class's developer did not bother overriding `equals`, then one would have to write a "wrapper" class that has the same properties as the original but implements `equals` in the desired way. This is a lot of boilerplate code, and certainly no abstraction! Instead, the `search` method should allow its users to pass in a custom `Comparator`, which is what e.g. [`java.util.TreeSet`](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/TreeSet.html) does.

The biggest issue is that `Stack` inherits from `Vector`, which means anyone can use `Vector` methods such as `elementAt` (to get an item not at the top of the stack) or `add(int index, E element)` (to add an item not at the top of the stack). This class should instead _contain_ a `Vector` as an implementation detail.


## `java.util.Date`

When thinking of years, you most likely think of numbers such as 1994 or 2019. You may think of 94 as equivalent of 1994 (unless you are reading this near the end of the 21st century), but certainly 119 is not a good way to express 2019. And yet that is exactly how `java.util.Date` abstracts the year.

You most likely do not think of December as the 11th month, or January as the 0th. While this is consistent with Java collections' 0-based indexing, this is one case where this consistency is not a good idea. This class is not designed as a collection, it is designed as a date, and therefore should match people's expectations of what a date is: December is the 12th month, and January is the 1st.

`Calendar.getTime` returns a `Date`. Even without knowing the exact semantics of this operation, you can already tell something is wrong: why is a "get time" method returning a date and not a time? There should be a `Time` class for this.

The next answer follows naturally: `Date.UTC` is supposed to be replaced by `Calendar.getTime().getTime()`, which does not make sense: why would one need to get the time of a time? We've already seen that the first `getTime` is not a good abstraction because it returns a `Date`; the second one is not great either since it returns... a `long`, which actually represents a Unix-specific concept. Not only does the name not match the return type, but the "abstraction" is non-existent since it exposes implementation details of a 50-year-old operating system.


## `java.net.URL` equality

There are certainly cases where one would like to know if two URLs point to the same Web server. However, this should be a high-level feature built on top of low-level abstractions such as `java.net.URL`, not the only way to compare URLs.

The `URL.equals` method is blocking, i.e. it will pause the program's execution waiting for a network request. This is unlike any other `equals` method in the standard library. Imagine writing a program that checks if a `List<URL>` of 100 elements contains one specific `URL`: your program may make 100 network requests, even though all you wanted was to know if two URLs had the same text!

This operation could cause bugs because its result depends on whether the computer has an active Internet connection, and also on the user's network settings. Different DNS providers could return different values for the same URL, e.g. to give the user the IP of a server closest to them as a performance optimization.

