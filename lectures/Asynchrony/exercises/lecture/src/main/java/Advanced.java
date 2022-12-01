import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

public final class Advanced {
    /** Uses `Server.uploadBatch` to upload the given texts, or cancels if 2 seconds have elapsed */
    public static CompletableFuture<Void> upload(String[] texts) {
        // TODO: add cancellation to `Server.upload` and use it here
        var cancelFlag = new AtomicBoolean(false);
        return CompletableFuture.completedFuture(null);
    }

    /** Adapts `OldServer.download` to the `CompletableFuture`-based world,
        and prints the result if successful. */
    public static CompletableFuture<Void> download() {
        // TODO (without modifying OldServer!)
        return CompletableFuture.completedFuture(null);
    }

    /** Retries `download` until it succeeds */
    public static CompletableFuture<Void> reliableDownload() {
        // TODO
        return download();
    }
}
