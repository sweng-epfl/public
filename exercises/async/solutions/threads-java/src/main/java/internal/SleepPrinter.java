package internal;

import java.util.concurrent.TimeUnit;

public class SleepPrinter {

    public static void printAfter(String msg, int after) {
        try {
            TimeUnit.SECONDS.sleep(after);
            System.out.println(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
