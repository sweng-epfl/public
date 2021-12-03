import internal.Sleeper;
import monad.Async;
import monad.Promise;

import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Promise<String> p = Async.submit(() -> {
            String hello = "Hello world!";
            System.out.println("Sleeping for 5 seconds");
            Sleeper.sleep(5);
            return hello;
        });
        Promise<Integer> p2 = p.bind(s -> Promise.pure(s.hashCode()));
        System.out.println("Example");
        int hashCode = p2.get();

        System.out.println("HashCode = " + hashCode);
        System.exit(0);
    }
}
