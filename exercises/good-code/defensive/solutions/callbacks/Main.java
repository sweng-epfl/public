package ch.epfl.sweng.defensive.error.processing.routine;

import java.util.Scanner;

import ch.epfl.sweng.defensive.error.processing.routine.callback.Callback;
import ch.epfl.sweng.defensive.error.processing.routine.model.Joke;
import ch.epfl.sweng.defensive.error.processing.routine.repository.JokeRepository;

public class Main {
  public static void main(String[] args) {
    JokeRepository repository = new JokeRepository();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("> Press any key to read a joke");
      scanner.nextLine();
      repository.random(new Callback<Joke>() {

        @Override
        public void onSuccess(Joke joke) {
          System.out.println(joke);
        }

        @Override
        public void onError(Exception e) {
          System.out.println(e);
        }
      });
    }
  }
}