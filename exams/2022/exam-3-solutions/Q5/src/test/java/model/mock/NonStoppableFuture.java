package model.mock;

import java.util.concurrent.CompletableFuture;

/**
 * This class is only for test purposes.
 * It represents a future where it is not possible to use join or get.
 */
public class NonStoppableFuture<T> extends CompletableFuture<T> {
    @Override
    public T join() {
        throw new UnsupportedOperationException("join() is not allowed in this exercise, as it breaks asynchrony");
    }

    @Override
    public T get() {
        throw new UnsupportedOperationException("get() is not allowed in this exercise, as it breaks asynchrony");
    }
}
