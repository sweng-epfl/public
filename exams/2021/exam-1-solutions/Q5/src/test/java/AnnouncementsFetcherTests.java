import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class AnnouncementsFetcherTests {
    @Test
    public void constructorThrowsOnNullClient() {
        // Arrange
        Clock clock = () -> 0;
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new AnnouncementsFetcher(null, clock));
    }

    @Test
    public void constructorThrowsOnNullClock() {
        // Arrange
        HttpClient client = url -> "text";
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> new AnnouncementsFetcher(client, null));
    }

    @Test
    public void fetchReturnsErrorMessageInCaseOfIOException() {
        // Arrange
        HttpClient client = url -> {
            throw new IOException();
        };
        Clock clock = () -> 0;
        var fetcher = new AnnouncementsFetcher(client, clock);
        // Act
        var announcements = fetcher.fetch();
        // Assert
        assertThat(announcements, contains("Server announcements could not be fetched. Sorry."));
    }

    @Test
    public void singleAnnouncementIsParsed() {
        // Arrange
        HttpClient client = url -> "Announcement";
        Clock clock = () -> 0;
        var fetcher = new AnnouncementsFetcher(client, clock);
        // Act
        var announcements = fetcher.fetch();
        // Assert
        assertThat(announcements, contains("Announcement"));
    }

    @Test
    public void multipleAnnouncementWithMultipleNewlinesAreParsed() {
        // Arrange
        HttpClient client = url -> "Announcement one\nTwo\n\n3\n";
        Clock clock = () -> 0;
        var fetcher = new AnnouncementsFetcher(client, clock);
        // Act
        var announcements = fetcher.fetch();
        // Assert
        assertThat(announcements, contains("Announcement one", "Two", "3"));
    }

    @Test
    public void dayOfTheWeekIsRespectedForVisibility() {
        // Arrange
        HttpClient client = url -> "0:::Hidden\n3:::Visible\nAlways visible";
        Clock clock = () -> 3;
        var fetcher = new AnnouncementsFetcher(client, clock);
        // Act
        var announcements = fetcher.fetch();
        // Assert
        assertThat(announcements, contains("Visible", "Always visible"));
    }
}
