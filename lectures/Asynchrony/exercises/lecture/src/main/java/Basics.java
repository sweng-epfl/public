import java.util.concurrent.*;

public final class Basics {

    // NOTE: The CompletableFuture API is documented at
    //       https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/CompletableFuture.html
    //       You can ignore the methods with "async" suffixes for now.

    /** Prints to `System.out` the weather given by `Weather.today` */
    public static CompletableFuture<Void> printTodaysWeather() {
        // TODO
        return CompletableFuture.completedFuture(null);
    }

    /** Uploads using `Server.upload` the weather given by `Weather.today` */
    public static CompletableFuture<Void> uploadTodaysWeather() {
        // TODO
        return CompletableFuture.completedFuture(null);
    }

    /** Prints to `System.out` the weather given by either `Weather.today` or `Weather.yesterday`,
        whichever is available first, prefixed by "Today: " and "Yesterday: " respectively */
    public static CompletableFuture<Void> printSomeWeather() {
        // TODO
        return CompletableFuture.completedFuture(null);
    }

    /** Prints to `System.out` the weather given by `Weather.all`,
        unless it takes more than 2 seconds,
        in which case it prints the weather given by `Weather.today` prefixed with "Today: " */
    public static CompletableFuture<Void> tryPrintAllWeather() {
        // TODO (hint: remember that CompletableFuture's documentation includes methods inherited from CompletionStage)
        return CompletableFuture.completedFuture(null);
    }
}
