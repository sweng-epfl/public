import model.VideoFile;
import provider.CachingProvider;
import provider.SwengflixProvider;
import provider.VideoFileProvider;

import java.util.concurrent.*;

/**
 * Represents the interaction between a client and a video provider
 *
 * NOTE: You are not required to use or write code in this file. It will NOT be graded
 */
public final class App {

    public static void main(String[] args) {
        // Simulate what the client does:
        VideoFileProvider provider = new CachingProvider(new SwengflixProvider());

        var f1 = handleFuture(provider.getVideo(2));

        // This should complete exceptionally, and complete before the request for 2, since no VideoFile with uniqueID 7 exists
        var f2 = handleFuture(provider.getVideo(7));

        f1.join();
        f2.join();
    }

    private static CompletableFuture<Void> handleFuture(CompletableFuture<VideoFile> future) {
        return future.thenAccept(v -> System.out.println("Now playing: " + v.title + " by " + v.author))
                     .orTimeout(10, TimeUnit.SECONDS)
                     .exceptionally(e -> {
                         System.out.println(e.getMessage());
                         return null;
                     });
    }
}
