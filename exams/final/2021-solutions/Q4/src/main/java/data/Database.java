package data;

import model.VideoFile;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

/*
 * Database for video files.
 * This is a simplified version of a real database.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class Database {
    private final Map<Integer, VideoFile> videos;
    private final int delay;

    /**
     * Creates a new database with the given videos and the given simulated delay.
     */
    public Database(Map<Integer, VideoFile> videos, int delay) {
        if (videos == null) {
            throw new IllegalArgumentException("videos cannot be null!");
        }
        this.videos = new HashMap<>(videos);
        this.delay = delay;
    }

    /**
     * Gets the video file with the given unique ID.
     */
    public CompletableFuture<VideoFile> getVideoFile(int uniqueID) {
        return CompletableFuture.supplyAsync(() -> {
            if (!videos.containsKey(uniqueID)) {
                throw new RuntimeException("The requested file could not be found");
            }
            // Pretend we need some time to completely transfer the VideoFile
            Database.sleep(delay);
            return videos.get(uniqueID);
        });
    }

    /**
     * Utility function for simulating file transfer delay
     */
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new AssertionError("An error occurred while putting the thread to sleep: ", e);
        }
    }

}
