package repository;

import java.util.List;

import callback.Callback;
import exception.NoJokeException;
import model.Joke;
import service.JokeService;

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
