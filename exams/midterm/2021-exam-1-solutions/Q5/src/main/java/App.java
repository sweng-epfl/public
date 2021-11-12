import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Entry point of the application.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You MUST use this file for the real dependencies of the announcements fetcher.
 * You CAN add new constructors, methods, and nested classes to this class.
 * You MUST NOT edit the name, parameters, checked exceptions or return type of the main method.
 * You MUST NOT delete the main method.
 * You MUST NOT rename this file.
 * You MUST NOT delete this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class App {
    public static void main(String[] args) {
        var client = new HttpClient() {
            @Override
            public String getText(String url) throws IOException {
                URL serverUrl;
                try {
                    serverUrl = new URL(url);
                } catch (MalformedURLException e) {
                    throw new IOException("The URL is malformed.", e);
                }

                try (var serverStream = serverUrl.openStream();
                     // Using \\A as the delimiter is a Java trick to get the Scanner to read the entire input
                     var s = new Scanner(serverStream).useDelimiter("\\A")) {
                    return s.next();
                }
            }
        };

        var clock = new Clock() {
            @Override
            public int getDayOfMonth() {
                return LocalDate.now().getDayOfMonth();
            }
        };

        System.out.println("Hello, World! Once the server people do their job this app will stop crashing...");
        System.out.println("But for now it just needs to be correct so that it'll work when the server is set up.");
        for (var announcement : new AnnouncementsFetcher(client, clock).fetch()) {
            System.out.println(announcement);
        }
    }
}
