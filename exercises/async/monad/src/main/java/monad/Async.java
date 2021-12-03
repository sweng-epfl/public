package monad;

import java.util.concurrent.Callable;

public class Async {

    /**
     * Submit a new callable to be transformed into a promise
     *
     * @param callable The callable
     * @param <V>      The type of the promise
     * @return the new promise
     */
    public static <V> Promise<V> submit(Callable<V> callable) {
        return null;
    }

}
