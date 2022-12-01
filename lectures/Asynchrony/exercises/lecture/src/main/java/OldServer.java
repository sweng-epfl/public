import java.util.function.*;
import java.util.*;

public final class OldServer {
    private static final Random random = new Random();

    /** Downloads data; rather unreliable, though */
    public static void download(Consumer<String> callback, Consumer<Throwable> errorCallback) {
        new Thread(()-> {
            Utils.sleep(1_000); // pretend we're doing a network request that takes some time
            if (random.nextInt(5) == 0) { // unlikely to succeed!
                callback.accept("Success");
            }
            errorCallback.accept(new RuntimeException("Failure"));
        }).start();
    }
}
