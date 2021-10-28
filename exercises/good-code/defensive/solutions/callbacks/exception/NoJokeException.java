package exception;

public class NoJokeException extends Exception {
  public NoJokeException(String message) {
    super(String.format("no kidding: %s", message));
  }
}
