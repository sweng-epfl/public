package ch.epfl.sweng.defensive.error.processing.routine.repository;

import java.util.List;

import ch.epfl.sweng.defensive.error.processing.routine.exception.NoJokeException;
import ch.epfl.sweng.defensive.error.processing.routine.model.Joke;
import ch.epfl.sweng.defensive.error.processing.routine.service.JokeService;

public class JokeRepository {
  private JokeService service = new JokeService();

  public Joke random() throws NoJokeException {
    return service.random();
  }
}