package ch.epfl.sweng.defensive.error.processing.routine;

import java.util.Scanner;

import ch.epfl.sweng.defensive.error.processing.routine.exception.NoJokeException;
import ch.epfl.sweng.defensive.error.processing.routine.model.Joke;
import ch.epfl.sweng.defensive.error.processing.routine.repository.JokeRepository;

public class Main {
  public static void main(String[] args) {
    JokeRepository repository = new JokeRepository();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("> Press any key to read a joke");
      scanner.nextLine();
      try {
        Joke joke = repository.random();
        System.out.println(joke);
      } catch (NoJokeException e) {
        System.out.println(e);
      }
    }
  }
}