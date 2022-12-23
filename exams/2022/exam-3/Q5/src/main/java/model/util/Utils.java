package model.util;

import java.util.Random;

/**
 * Utility class.
 * <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 * DO NOT TOUCH THIS FILE <p>
 * !!!!!!!!!!!!!!!!!!!!!! <p>
 */
public final class Utils {
    private static final int SEED = 2022;

    private static final Random RANDOM = new Random(SEED);

    /**
     * Sleeps for the given number of milliseconds.
     */
    public static void sleep() {
        try {
            Thread.sleep(getSleepTime());
        } catch (InterruptedException e) {
            throw new AssertionError("This should never happen in this example code", e);
        }
    }

    /**
     * @return a random number between 1 and 10 seconds
     */
    private static long getSleepTime() {
        return RANDOM.nextInt(1, 10) * 1_000L;
    }
}
