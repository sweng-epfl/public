package repository;

import exception.NoJokeException;
import model.Joke;
import service.JokeService;

public class JokeRepository {
  private JokeService service = new JokeService();

  public Joke random() throws NoJokeException {
    return service.random();
  }
}