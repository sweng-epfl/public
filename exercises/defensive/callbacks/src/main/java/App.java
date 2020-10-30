import exception.NoJokeException;
import model.Joke;
import repository.JokeRepository;

public class App {
    public static void main(String[] args) throws NoJokeException {
        JokeRepository repository = new JokeRepository();
        Joke joke = repository.random();
        System.out.println(joke);
    }
}