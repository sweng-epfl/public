package monad;

import java.util.function.Function;

public class Promise<V> {

    /**
     * Create a new Promise from a value
     *
     * @param v   The value
     * @param <V> The type of the value
     * @return The promise
     */
    public static <V> Promise<V> pure(V v) {
        return null;
    }

    /**
     * Bind the current promise to another promise
     *
     * @param f   The function to apply
     * @param <U> The type of the new promise
     * @return The new promise
     */
    public <U> Promise<U> bind(Function<V, Promise<U>> f) {
        return null;
    }

    /**
     * Get the value in the promise
     * As the value is asynchronously 'fetched' this should wait until the value is here
     *
     * @return The value
     */
    public V get() {
        return null;
    }
}
