package ch.epfl.sweng.defensive.error.processing.routine;

import ch.epfl.sweng.defensive.error.processing.routine.exception.NoJokeException;
import ch.epfl.sweng.defensive.error.processing.routine.model.Joke;
import ch.epfl.sweng.defensive.error.processing.routine.repository.JokeRepository;

public class Main {
    public static void main(String[] args) throws NoJokeException {
        JokeRepository repository = new JokeRepository();
        Joke joke = repository.random();
        System.out.println(joke);
    }
}