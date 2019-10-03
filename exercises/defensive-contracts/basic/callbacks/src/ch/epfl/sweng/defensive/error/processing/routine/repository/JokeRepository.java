package ch.epfl.sweng.defensive.error.processing.routine.repository;

import java.util.List;

import ch.epfl.sweng.defensive.error.processing.object.exception.NoJokeException;
import ch.epfl.sweng.defensive.error.processing.object.model.Joke;
import ch.epfl.sweng.defensive.error.processing.object.service.JokeService;

public class JokeRepository {
  private JokeService service = new JokeService();

  public Joke random() throws NoJokeException {
    return service.random();
  }
}