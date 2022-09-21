package data;

import model.VideoFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents all the data stored on the EU Swengflix database
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class VideoDataEU {

    private static VideoFile titanic = new VideoFile(0, "Titanic", "James Cameron", "Content for Titanic");
    private static VideoFile interstellar = new VideoFile(1, "Interstellar", "Christopher Nolan", "Content for Interstellar");
    private static VideoFile goodfellas = new VideoFile(2, "Goodfellas", "Martin Scorsese", "Content for Goodfellas");
    private static VideoFile dazedAndConfused = new VideoFile(5, "Dazed and Confused", "Richard Linklater", "Content for Dazed and Confused");

    /**
     * Gets the Swengflix videos, in the form of a map mapping the uniqueID to the corresponding VideoFile.
     */
    public static Map<Integer, VideoFile> allVideoData() {
        HashMap<Integer, VideoFile> data = new HashMap<>();
        data.put(titanic.uniqueID, titanic);
        data.put(interstellar.uniqueID, interstellar);
        data.put(goodfellas.uniqueID, goodfellas);
        data.put(dazedAndConfused.uniqueID, dazedAndConfused);
        return data;
    }
}
