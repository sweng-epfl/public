package service;

import exception.NoJokeException;
import model.Joke;
import store.JokeStore;;

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
