import model.VideoFile;
import provider.SwengflixProvider;
import provider.VideoFileProvider;

import java.util.concurrent.CompletableFuture;

/**
 * Represents the interaction between a client and a video provider
 *
 * NOTE: You are not required to use or write code in this file. It will NOT be graded
 */
public final class App {

    public static void main(String[] args) {
        // Simulate what the client does:
        VideoFileProvider provider = new SwengflixProvider();

        handleFuture(provider.getVideo(2));

        // This should complete exceptionally, and complete before the request for 2:
        // No VideoFile with uniqueID 7 exists
        handleFuture(provider.getVideo(7));

        // Wait, to be sure that the client has received the video(s) what they wanted
        sleep(30_000);
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new AssertionError("An error occurred while putting the thread to sleep: ", e);
        }
    }

    private static void handleFuture(CompletableFuture<VideoFile> future) {
        future.thenAccept(App::showVideo)
                .exceptionally(throwable -> {
                    System.out.println(throwable.getMessage());
                    return null;
                });
    }

    private static void showVideo(VideoFile videoFile) {
        System.out.println("Now playing: " + videoFile.title + " by " + videoFile.author);
    }

}
