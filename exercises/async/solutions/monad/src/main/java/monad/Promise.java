package monad;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Promise<V> implements Future<V> {
    protected List<Consumer<Promise<V>>> callbacks = new ArrayList<>();
    protected boolean invoked = false;
    protected V result = null;
    protected Throwable exception = null;
    protected final CountDownLatch taskLock = new CountDownLatch(1);

    /**
     * Create a new Promise from a value
     *
     * @param v   The value
     * @param <V> The type of the value
     * @return The promise
     */
    public static <V> Promise<V> pure(V v) {
        Promise<V> p = new Promise<>();
        p.invoke(v);
        return p;
    }

    /**
     * Bind the current promise to another promise
     *
     * @param f   The function to apply
     * @param <U> The type of the new promise
     * @return The new promise
     */
    public <U> Promise<U> bind(Function<V, Promise<U>> f) {
        Promise<U> result = new Promise<>();
        this.onRedeem(callback -> {
            try {
                V v = callback.get();
                Promise<U> applicationResult = f.apply(v);
                applicationResult.onRedeem(applicationCallback -> {
                    try {
                        U u = applicationCallback.get();
                        result.invoke(u);
                    } catch (Throwable e) {
                        result.invokeWithException(e);
                    }
                });
            } catch (Throwable e) {
                result.invokeWithException(e);
            }
        });
        return result;
    }

    public void onRedeem(Consumer<Promise<V>> callback) {
        final boolean mustCallCallback;
        synchronized (this) {
            if (!invoked) {
                callbacks.add(callback);
            }
            mustCallCallback = invoked;
        }
        if (mustCallCallback) {
            callback.accept(this);
        }
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        taskLock.await();
        if (exception != null) {
            throw new ExecutionException(exception);
        }
        return result;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (!taskLock.await(timeout, unit)) {
            throw new TimeoutException(String.format("Promise didn't redeem in %s %s", timeout, unit));
        }
        if (exception != null) {
            throw new ExecutionException(exception);
        }
        return result;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return invoked;
    }

    public void invoke(V result) {
        invokeWithResultOrException(result, null);
    }

    public void invokeWithException(Throwable t) {
        invokeWithResultOrException(null, t);
    }

    protected void invokeWithResultOrException(V result, Throwable t) {
        synchronized (this) {
            if (!invoked) {
                invoked = true;
                this.result = result;
                this.exception = t;
                taskLock.countDown();
            } else {
                return;
            }
        }
        for (Consumer<Promise<V>> callback : callbacks) {
            callback.accept(this);
        }
    }
}
