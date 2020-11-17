package ch.epfl.sweng.javapk.util;

public class Utils {
    /**
     * Converts a size in bytes to a human-readable format (i.e. 1024B = 1MiB)
     * @return a string representing the given filesize in an approximated human readable format
     */
    public static String sizeToHumanReadable(long sizeInBytes) {
        long next = sizeInBytes;
        int power = 0;

        while ((next = sizeInBytes / 1024) > 0) {
            sizeInBytes = next;
            power++;
        }

        switch (power) {
            case 0:
                return sizeInBytes + "B";
            case 1:
                return sizeInBytes + "KiB";
            case 2:
                return sizeInBytes + "MiB";
            case 3:
                return sizeInBytes + "GiB";
            case 4:
                return sizeInBytes + "TiB";
            default:
                return sizeInBytes * Math.pow(2, power) + "B";
        }
    }
}
