package ch.epfl.sweng.defensive.error.processing.routine.service;

import ch.epfl.sweng.defensive.error.processing.routine.exception.NoJokeException;
import ch.epfl.sweng.defensive.error.processing.routine.model.Joke;
import ch.epfl.sweng.defensive.error.processing.routine.store.JokeStore;;

public class JokeService {
  private JokeStore store = JokeStore.get();

  public Joke random() throws NoJokeException {
    boolean lostConnection = Math.random() < 0.2;
    if (lostConnection) {
      throw new NoJokeException("lost connection");
    }
    return store.random();
  }
}