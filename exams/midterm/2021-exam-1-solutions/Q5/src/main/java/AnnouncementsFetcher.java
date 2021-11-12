import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

interface HttpClient {
    String getText(String url) throws IOException;
}

interface Clock {
    int getDayOfMonth();
}

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

    private final HttpClient client;
    private final Clock clock;

    /** Creates a new announcement fetcher. */
    public AnnouncementsFetcher(HttpClient client, Clock clock) {
        if (client == null) {
            throw new IllegalArgumentException("client cannot be null");
        }
        if (clock == null) {
            throw new IllegalArgumentException("clock cannot be null");
        }
        this.client = client;
        this.clock = clock;
    }

    /** Fetches announcements from the server. */
    public List<String> fetch() {
        var result = new ArrayList<String>();

        String announcementsText;
        try {
            announcementsText = client.getText("https://superquiz.example.org/announcements");
        } catch (IOException e) {
            result.add("Server announcements could not be fetched. Sorry.");
            return result;
        }

        String[] announcements = announcementsText.split("\n");
        for (String announcement : announcements) {
            // BUGFIX: The server spec says announcements could be separated by more than one newline "\n".
            //         Thus, we must ignore empty lines.
            if (announcement.isBlank()) {
                continue;
            }

            String[] parts = announcement.split(":::");
            if (parts.length > 1) {
                int dayOfMonth = clock.getDayOfMonth();
                if (Integer.toString(dayOfMonth).equals(parts[0].trim())) {
                    result.add(parts[1].trim());
                }
            } else {
                result.add(announcement);
            }
        }
        return result;
    }
}
