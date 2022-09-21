package data;

import model.VideoFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents all the data stored on the US Swengflix database
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class VideoDataUS {

    private static VideoFile fearAndLoathingInLasVegas = new VideoFile(3, "Fear and Loathing in Las Vegas", "Terry Gilliam", "Content for Fear and Loathing in Las Vegas");
    private static VideoFile trainspotting = new VideoFile(4, "Trainspotting", "Danny Boyle", "Content for Trainspotting");
    private static VideoFile dazedAndConfused = new VideoFile(5, "Dazed and Confused", "Richard Linklater", "Content for Dazed and Confused");

    /**
     * Gets the Swengflix videos, in the form of a map mapping the uniqueID to the corresponding VideoFile.
     */
    public static Map<Integer, VideoFile> allVideoData() {
        HashMap<Integer, VideoFile> data = new HashMap<>();
        data.put(fearAndLoathingInLasVegas.uniqueID, fearAndLoathingInLasVegas);
        data.put(trainspotting.uniqueID, trainspotting);
        data.put(dazedAndConfused.uniqueID, dazedAndConfused);
        return data;
    }
}
