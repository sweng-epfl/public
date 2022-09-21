package util;

/**
 * Utility for async code.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class AsyncUtil {

    /**
     * Simulates an asynchronous setting by putting the thread to
     * sleep for an amount of time sampled from a poisson distribution
     * with lambda = 4
     */
    public static void simulateAsync() {
        try {
            Thread.sleep(Poisson.generatePoissonNumber(4) * 1000);
        } catch (InterruptedException e) {
            throw new AssertionError("An error occurred while putting the thread to sleep: ", e);
        }
    }
}
