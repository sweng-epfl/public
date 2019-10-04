package ch.epfl.sweng.defensive.error.processing.routine.store;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.epfl.sweng.defensive.error.processing.routine.model.Joke;

public class JokeStore {

  private static JokeStore store;
  private List<Joke> jokes = new ArrayList<>();
  private Random random = new Random();

  private JokeStore() {
    try {
      BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/jokes.txt"));
      String line, statement = "";
      while ((line = reader.readLine()) != null) {
        if (!line.isEmpty()) {
          statement += " " + line;
        } else {
          if (!statement.isEmpty()) {
            jokes.add(new Joke(statement));
            statement = "";
          }
        }
      }
      reader.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static JokeStore get() {
    if (store == null) {
      store = new JokeStore();
    }
    return store;
  }

  public Joke random() {
    return jokes.get(random.nextInt(jokes.size()));
  }
}