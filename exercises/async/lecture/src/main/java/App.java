import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.Random;

public class App {
    // NOTE: The CompletableFuture API is documented at
    //       https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/CompletableFuture.html
    //       You can ignore the methods with "async" suffixes for now.

    public static void main(String[] args) {
        // TODO: Create the following Futures using the functions below and the CompletableFuture methods.
        //       (note that "creating" also implicitly runs them)

        // --- Part 1 ---

        // 1. Print the exact answer to System.out as soon as it is available

        // 2. Upload the exact answer as soon as it is available

        // 3. Either the exact answer or the approximate answer,
        //    whichever one is available first,
        //    prefixed with "Exact: " or "Approximate: " as necessary,
        //    and printed to System.out

        // 4. The answer with justification, printed to System.out;
        //    unless it throws an exception, in which case the normal answer, prefixed with "Exact: ",
        //    should be printed instead
        //    (hint: don't forget the documentation of CompletableFuture's interface CompletionStage...)


        // --- Part 2 ---

        // 5. Count sheep using the provided method, but stop the operation after 5 seconds if it's not finished.
        //    Cancellation isn't implemented yet; implement it in countSheep below.
        //    Then print the number of sheep.
        var flag = new AtomicBoolean();

        // 6. A reliable version of "unreliableDownload" below, by filling in the "firstSuccess" method
        //    Try to do this without using join(), isDone(), or other sync methods!

        // 7. There is an "oldAsyncSend" method below, wrap it into a CompletableFuture in the
        //    "newAsyncSend" method under it, and print its result.

        // -- End --

        // For this example app, we just want to wait for everything to finish;
        // in a real application, this would be handled by a framework somehow
        // (or by the language itself, e.g., C# can have its "main" method return a future)
        sleep(30_000);
    }

    // --- Methods for Part 1 ---

    static CompletableFuture<Void> upload(String text) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(2_000); // pretend we're doing some uploading for 2 seconds
            System.out.println("Uploaded: " + text);
            return null;
        });
    }

    static CompletableFuture<Integer> getExactAnswer() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(2_000); // pretend it takes 2 seconds to get the answer
            return 42;
        });
    }

    static CompletableFuture<Integer> getApproximateAnswer() {
        return CompletableFuture.completedFuture(40);
    }

    static CompletableFuture<String> getAnswerWithJustification() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(2_000); // after 2 seconds, we still don't know
            throw new RuntimeException("Sorry, couldn't find a justification");
        });
    }


    // --- Methods for Part 2 ---

    static CompletableFuture<Integer> countSheep(AtomicBoolean cancelFlag) {
        // TODO throw a CancellationException if cancelled
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int n = 0; n < 1_000_000_000; n++) {
                sleep(10); // each step takes some time
                sum += n;
            }
            return sum;
        });
    }

    static Random random = new Random();

    static CompletableFuture<String> unreliableDownload() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1_000); // pretend we're doing a network request that takes some time
            if (random.nextInt(5) == 0) { // unlikely to succeed!
                return "Success";
            }
            throw new RuntimeException("Failure");
        });
    }

    static <T> CompletableFuture<T> firstSuccess(Supplier<CompletableFuture<T>> supplier) {
        throw new RuntimeException("TODO (~1 line)");
    }

    static void oldAsyncSend(String text, Consumer<String> callback, Consumer<Throwable> errorCallback) {
        new Thread(() -> {
            sleep(1_000); // pretend we're doing a network request that takes some time
            if (text.equals("ping")) {
                callback.accept("pong");
            } else {
                errorCallback.accept(new RuntimeException("Unknown message"));
            }
        }).start();
    }

    static CompletableFuture<String> newAsyncSend(String text) {
        throw new RuntimeException("TODO (~3 lines)");
    }


    // --- Methods for part 3 ---

    static boolean buttonWasClicked = false;

    // pretend there's a framework like Android requiring this method to return "void" :-)
    static void clickButton(Runnable callback) {
        new Thread(() -> {
            sleep(1_000); // pretend it takes some time
            buttonWasClicked = true;
            callback.run();
        }).start();
    }

    static void printAnswers() {
        getExactAnswer()
                .thenApply(a -> "Exact: " + a)
                .thenAccept(System.out::println);
        getApproximateAnswer()
                .thenApply(a -> "Approximate: " + a)
                .thenAccept(System.out::println);
    }


    // --- Utility methods you can ignore ---

    // convenience to not have to handle InterruptedException every time
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new AssertionError("This should never happen in this example code", e);
        }
    }
}
