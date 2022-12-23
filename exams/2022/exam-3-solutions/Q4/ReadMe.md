# Question 4: I am fluent in over 6 million kinds of API [30 points]

This exercise is split into two independent parts.

You are developing Komix, a software to read different kinds of comic books,
from various online providers. Those providers all have their own API and way
to represent data, they are therefore adapted to expose a unique interface:
[`BookProvider`](src/main/java/providers/BookProvider.java).


## Part 1: New API

You are given a new API, [`Manganese`](src/main/java/providers/manganese/ManganeseApi.java),
which is a book provider for various mangas, that you need to adapt in
[`ManganeseBookProvider`](src/main/java/providers/manganese/ManganeseBookProvider.java).
Concretely you have to implement the `findBooksByTitle` and `fetchPage`
methods, the first one searches for a book base on the title, and the second
fetches the content of a particular page. Read carefully what is the expected
behavior, described in [`BookProvider`](src/main/java/providers/BookProvider.java).

A mock API [`MockManganeseApi`](src/main/java/providers/manganese/MockManganeseApi.java)
is given for local testing, as well as the [`App`](src/main/java/App.java)
which provides a simple interactive command-line interface to test your
implementation. You can modify it, it won't be graded, but make sure it compiles.

_You will get up to 15 points for a correct and maintainable `ManganeseBookProvider`._


## Part 2: Testing

As a good software engineer, you also need to test your implementation, in
[`ManganeseProviderTest`](src/test/java/providers/manganese/ManganeseProviderTest.java).

_You will get up to 15 points for a complete and clean test suite._
_(The tests will be graded independently of the implementation)_
