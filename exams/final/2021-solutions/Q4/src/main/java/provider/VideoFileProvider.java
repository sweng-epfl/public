package provider;

import model.VideoFile;

import java.util.concurrent.CompletableFuture;

/*
 * Interface representing an asynchronous provider of video files.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface VideoFileProvider {
    /**
     * Asynchronously gets a VideoFile with ID uniqueID.
     *
     * @param uniqueID the requested VideoFile's uniqueID
     * @return A VideoFile Future which contains the requested VideoFile if and when getVideo completes successfully
     */
    public CompletableFuture<VideoFile> getVideo(int uniqueID);
}
