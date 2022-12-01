import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

public final class Server {
    /** Uploads some text to the server (well, pretends to, at least) */
    public static CompletableFuture<Void> upload(String text) {
        return CompletableFuture.supplyAsync(() -> {
            Utils.sleep(2_000); // pretend we're doing useful work
            System.out.println("Uploaded: " + text);
            return null; // "Void" != "void", we still need to return something
        });
    }

    /** Uploads an array of texts to the server (...or pretends to, at least) */
    public static CompletableFuture<Void> uploadBatch(String[] texts) {
        // TODO cancel if needed before each step
        return CompletableFuture.supplyAsync(() -> {
            for (String text : texts) {
                Utils.sleep(500); // each step takes some time
                System.out.println("Uploaded: " + text);
            }
            return null;
        });
    }
}
