# Solution - Callback-based error-processing routines

### Defining a Callback interface

The callback interface provided as an example in the exercise statement is the very same one we use for the exercise solution. A callback defines two methods: 1) one method invoked on success along with the return value; 2) one method invoked on error along with error, an exception in the.

```java
public interface Callback<T> {
  void onSuccess(T value);
  void onError(Exception e);
}
```

### Modifying the random method of JokeRepository

As described in the exercise statement, modifying the `random` method of `JokeRepository` is a three-step task.

First, we need to change the method signature. To this end, we need to pass a callaback to the method, and hence we add such a parameter to the latter. Then, since now we use a callback, we don't need a return value anymore. For that reason, the return type becomes _void_.

```java
public void random(Callback<Joke> callback) {
  // ...
}
```
We need then to return a random joke as before. However, we don't use a _return_ statement, but rather return a joke using the callback parameter. To achieve that, we call the `onSuccess` method on the callback and we pass a randomly chosen joke to it.

```java
public void random(Callback<Joke> callback) {
  callback.onSuccess(service.random());
}
```

At this point, the code will not compile because an exception may be thrown from the `random` method of the service and we do not handle it. We can fix that by catching the exception locally, and then propage the exception through the callback. As a result, we invoke the `onError` method of the callback.

```java
public void random(Callback<Joke> callback) {
  try {
    callback.onSuccess(service.random());
  } catch (NoJokeException e) {
    callback.onError(e);
  }
}
```

### Modifying the infinite loop of Main.java:

Before, the main used a _try-catch_ statement when calling the `random` method of `JokeRepository`. Now that we rely on callbacks, we don't need this construct anymore. Therefore, we just need to pass an instance of `Callback` when calling this method and handle the relevant cases accordingly, i.e. printing the joke on success or printing the exception stack trace on error:

```java
repository.random(new Callback<Joke>() {

  @Override
  public void onSuccess(Joke joke) {
    System.out.println(joke);
  }

  @Override
  public void onError(Exception e) {
    System.out.println(e);
  }
});
```
