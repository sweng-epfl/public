package internal;

import java.util.concurrent.TimeUnit;

public class Sleeper {

    public static void sleep(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
