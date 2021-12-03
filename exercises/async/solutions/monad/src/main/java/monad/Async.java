package monad;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Async {

    public static ExecutorService defaultExecutor = Executors.newCachedThreadPool();

    public static void shutdown() {
        defaultExecutor.shutdownNow();
    }

    /**
     * Submit a new callable to be transformed into a promise
     *
     * @param callable The callable
     * @param <V>      The type of the promise
     * @return the new promise
     */
    public static <V> Promise<V> submit(Callable<V> callable) {
        final Promise<V> promise = new Promise<>();
        defaultExecutor.submit(() -> {
            try {
                V result = callable.call();
                promise.invoke(result);
                return result;
            } catch (Throwable e) {
                promise.invokeWithException(e);
                return null;
            }
        });
        return promise;
    }

}
