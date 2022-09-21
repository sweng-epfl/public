import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Fetcher for server announcements.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN add new constructors, methods, and nested classes to this class.
 * You CAN edit the parameters, return types, and checked exceptions of the existing method and constructor.
 * You MUST NOT edit the names of the existing method and constructor.
 * You MUST NOT rename this file.
 * You MUST NOT delete this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class AnnouncementsFetcher {
    // Notes from the server developers:
    // - The API is at https://superquiz.example.org/announcements
    // - It returns a textual list of announcements separated by one or more new line '\n' characters
    // - Announcements may begin with a day of the month (as a number) then a ':::' separator, in which case they should only be shown on that day
    // - The ':::' separator immediately after the day of month is not a part of the announcement and does not occur otherwise

    /** Creates a new announcement fetcher. */
    public AnnouncementsFetcher() {
        // TODO it would be nice if this class's dependencies were explicit and given in the constructor.
    }

    /** Fetches announcements from the server. */
    public void fetch() {
        URL serverUrl;
        try {
            serverUrl = new URL("https://superquiz.example.org/announcements");
        } catch (MalformedURLException e) {
            throw new AssertionError("The URL to the backend is malformed.", e);
        }

        String announcementsText;
        try (var serverStream = serverUrl.openStream();
             // Using \\A as the delimiter is a Java trick to get the Scanner to read the entire input
             var s = new Scanner(serverStream).useDelimiter("\\A")) {
            announcementsText = s.next();
        } catch (IOException e) {
            System.out.println("Server announcements could not be fetched. Sorry.");
            return;
        }

        String[] announcements = announcementsText.split("\n");
        for (String announcement : announcements) {
            String[] parts = announcement.split(":::");
            if (parts.length > 1) {
                int dayOfMonth = LocalDate.now().getDayOfMonth();
                if (Integer.toString(dayOfMonth).equals(parts[0].trim())) {
                    System.out.println(parts[1].trim());
                }
            } else {
                System.out.println(announcement);
            }
        }
    }
}
