package provider;

import data.Database;
import data.VideoDataEU;
import data.VideoDataUS;
import model.VideoFile;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of a video file provider for Swengflix.
 * !!!!!!!!!!!!!!!!!!!!!!!
 * You CAN edit this file.
 * !!!!!!!!!!!!!!!!!!!!!!!
 */
public class SwengflixProvider implements VideoFileProvider {

    private final List<Database> dbs;

    /**
     * Creates a SwengflixProvider using the given databases.
     */
    public SwengflixProvider(Database... dbs) {
        this.dbs = Arrays.asList(dbs);
    }

    /**
     * Creates a SwengflixProvider using the default databases.
     */
    public SwengflixProvider() {
        // the EU database is geographically closer so its delay is lower
        this.dbs = Arrays.asList(
                new Database(VideoDataEU.allVideoData(), 3_000),
                new Database(VideoDataUS.allVideoData(), 5_000)
        );
    }

    @Override
    public CompletableFuture<VideoFile> getVideo(int uniqueID) {
        return dbs.stream()
                .map(db -> db.getVideoFile(uniqueID))
                .reduce((a, b) -> a.applyToEither(b, r -> r))
                .orElse(CompletableFuture.failedFuture(new AssertionError("There are no databases.")));
    }
}
