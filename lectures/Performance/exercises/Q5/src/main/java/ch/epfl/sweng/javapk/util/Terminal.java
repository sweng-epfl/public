package ch.epfl.sweng.javapk.util;

import java.io.File;
import java.net.URL;
import java.util.function.BiConsumer;

public final class Terminal {
    /**
     * Displays a progress bar on the screen, with a message and the downloaded sizes.<br>
     *     This method doesn't print a new-line after use. You need to use an empty println if you want to print a new line.
     * @param operation the message to display before the progress bar
     * @param current the current progression (in bytes)
     * @param total the total number of bytes to download
     */
    public static void printProgressBar(String operation, long current, long total) {
        StringBuilder buffer = new StringBuilder("[");
        int progress = (int) ((100 * current) / total);

        for (int i = 0; i <= 100; i += 5) {
            if (i <= progress)
                buffer.append('=');
            else
                buffer.append(' ');
        }

        buffer.append("] ").append(progress).append(" % (")
                .append(Utils.sizeToHumanReadable(current))
                .append('/').append(Utils.sizeToHumanReadable(total))
                .append(')');
        System.out.print("\r" + operation + ": " + buffer.toString());
    }

    /**
     * Returns a consummer that will call progress-bar on each call with the given message. Useful with {@link Networking#downloadFile(URL, File, BiConsumer)}
     * @param message the message to display before the progress bar
     * @return a {@link BiConsumer} that calls {@link Terminal#printProgressBar(String, long, long)}
     */
    public static BiConsumer<Long, Long> progressBar(String message) {
        return (prog, tot) -> printProgressBar(message, prog, tot);
    }
}
