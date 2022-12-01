import java.util.concurrent.*;
import java.util.function.*;

public final class Weather {
    /** Gets today's weather */
    public static CompletableFuture<String> today() {
        return CompletableFuture.supplyAsync(() -> {
            Utils.sleep(2_000); // pretend we're doing useful work
            return "Sunny";
        });
    }

    /** Gets yesterday's weather */
    public static CompletableFuture<String> yesterday() {
        // We already know yesterday's weather, so we can answer immediately
        return CompletableFuture.completedFuture("Cloudy");
    }

    /** Gets the weather for all days ever */
    public static CompletableFuture<String> all() {
        return CompletableFuture.supplyAsync(() -> {
            Utils.sleep(1_000_000_000); // takes a lot of time!
            throw new RuntimeException("This is too hard");
        });
    }

    /** Prints the weather for yesterday and today, in some undefined order. */
    public static CompletableFuture<Void> printWeathers(Consumer<String> printer) {
        return CompletableFuture.allOf(
            today().thenApply(a -> "Today: " + a)
                   .thenAccept(printer),
            yesterday().thenApply(a -> "Yesterday: " + a)
                       .thenAccept(printer)
        );
    }
}
