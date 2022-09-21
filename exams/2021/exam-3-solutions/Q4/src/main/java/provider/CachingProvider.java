package provider;

import model.VideoFile;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.CompletableFuture;

public final class CachingProvider implements VideoFileProvider {
    private final int MAX_SIZE = 2;

    private final VideoFileProvider wrapped;
    private final Deque<VideoFile> cache;

    public CachingProvider(VideoFileProvider wrapped) {
        this.wrapped = wrapped;
        this.cache = new ArrayDeque<>();
    }

    @Override
    public CompletableFuture<VideoFile> getVideo(int uniqueID) {
        for (VideoFile file : cache) {
            if (file.uniqueID == uniqueID) {
                // Remove + addFirst == "refresh" its position in the cache
                cache.remove(file);
                cache.addFirst(file);
                return CompletableFuture.completedFuture(file);
            }
        }
        return wrapped.getVideo(uniqueID).thenApply(file -> {
            if (cache.size() >= MAX_SIZE) {
                cache.removeLast();
            }
            cache.addFirst(file);
            return file;
        });
    }
}
