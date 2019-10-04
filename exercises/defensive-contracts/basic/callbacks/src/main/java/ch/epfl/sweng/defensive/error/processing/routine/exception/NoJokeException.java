package ch.epfl.sweng.defensive.error.processing.routine.exception;

public class NoJokeException extends Exception {
  public NoJokeException(String message) {
    super(String.format("no kidding: %s", message));
  }
}