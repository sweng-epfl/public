# Solution - Neutral return values

### Making both the search and next methods return optional results

Since we want to return neutral values when searching over Google, we need to make use of the `Optional` class. The search logic remains same except that we no longer return results but rather results wrapped inside optionals. This implies returning an optional of a result when there are available results. Otherwise, we return an empty `Optional` when there is no more result. The relevant changes lead to the following implementation of `search`:

```java
public static Optional<Result> search(String[] keywords) {
  Iterator<String> iterator = Internet.find(keywords);
  if (iterator.hasNext()) {
    return Optional.of(new Result(iterator));
  }
  return Optional.empty();
}
```

Similarly, the changes on the `next` method consist in returning an instance of `Optional<Result>` rather than an instance of `Result`. The same remark regarding returning an empty optional applies here too:

```java
public Optional<Result> next() {
  if (iterator.hasNext()) {
    return Optional.of(new Result(iterator));
  }
  return Optional.empty();
}
```

### Adapting the main function of Main.java

The adaptation of the main function requires to get an `Optional` instance when searching data with some keywords and iterate over it as long as there is a present value that is wrapped inside the optional:

```java
public static void main(String[] args) {
  // ...
  while (true) {
    // ...
    if (!line.isEmpty()) {
      String[] keywords = line.split(" ");
      Optional<Result> optional = Google.search(keywords);
      while (optional.isPresent()) {
        Result result = optional.get();
        // ...
        optional = result.next();
      }
      // ...
    }
  }
}
```

The logic of the initial version of the main function remains the same otherwise, i.e. printing out the search results to the standard output.
