package internal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomSleeper {
    /**
     * Async sleep for some time and then run the runnable
     *
     * @param max      The max number of second to wait
     * @param runnable The runnable to run
     */
    public static void sleepRandAndRun(int max, Runnable runnable) {
        Thread newThread = new Thread(() -> {
            sleepRand(max);
            runnable.run();
        });
        newThread.start();
    }

    public static void sleepRand(int max) {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(max));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
