package ch.epfl.sweng.javapk.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.BiConsumer;

public class Networking {
    /**
     * Downloads a file located at a specified URL to a specified target file.
     * This method reports progress to the consumer for display purposes
     *
     * @param url      the url to download
     * @param target   the target file
     * @param progress the progress consumer, taking as arguments the downloaded bytes and the total bytes to download
     * @throws IOException if the download cannot be completed successfully
     */
    public static void downloadFile(URL url, File target, BiConsumer<Long, Long> progress) throws IOException {
        if (progress == null)
            progress = (p, q) -> {
            }; // empty consumer

        InputStream stream = null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            connection.setDoInput(true);
            // Open communications link (network traffic occurs here).
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();
            if (stream != null) {
                long contentLength = connection.getContentLengthLong();
                OutputStream out = new FileOutputStream(target);
                byte[] arr;
                long len = 0;

                while ((arr = stream.readNBytes(8192)).length != 0) {
                    // Progress reporting
                    len += arr.length;
                    progress.accept(len, contentLength);

                    // Write retrieved bytes to the file
                    out.write(arr);
                }

                out.flush();
                out.close();
                stream.close();
            }
            connection.disconnect();
        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
