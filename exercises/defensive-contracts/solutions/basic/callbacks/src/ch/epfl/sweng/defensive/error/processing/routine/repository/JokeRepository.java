package ch.epfl.sweng.defensive.error.processing.routine.repository;

import java.util.List;

import ch.epfl.sweng.defensive.error.processing.routine.callback.Callback;
import ch.epfl.sweng.defensive.error.processing.routine.exception.NoJokeException;
import ch.epfl.sweng.defensive.error.processing.routine.model.Joke;
import ch.epfl.sweng.defensive.error.processing.routine.service.JokeService;

public class JokeRepository {
  private JokeService service = new JokeService();

  public void random(Callback<Joke> callback) {
    try {
      callback.onSuccess(service.random());
    } catch (NoJokeException e) {
      callback.onError(e);
    }
  }
}