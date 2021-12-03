import internal.HeavyComputation;
import threads.CompletableFutureExecutor;
import threads.FutureTaskExecutor;
import threads.ThreadExecutor;

public class App {
    public static void main(String[] args) {
        HeavyComputation heavyComputation = new HeavyComputation();
        System.out.println("------------------------------");
        System.out.println("Thread executor:");
        System.out.println("Result: " + (new ThreadExecutor()).execute(heavyComputation, args));
        System.out.println("------------------------------");
        System.out.println("Future task executor:");
        System.out.println("Result: " + (new FutureTaskExecutor()).execute(heavyComputation, args));
        System.out.println("------------------------------");
        System.out.println("Completable future executor:");
        System.out.println("Result: " + (new CompletableFutureExecutor()).execute(heavyComputation, args));
    }
}
