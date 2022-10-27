import java.io.InputStream;
import java.io.IOException;
import java.util.Scanner;

interface HttpClient {
    // NOTE: in a real app one might want to return `InputStream` as a more general interface,
    //       or `String` if it's always read as one chunk, or even both
    InputStream get(String url) throws IOException;
}

public final class JokeFetcher {
    private final HttpClient client;

    // NOTE: `client` could also be injected in `getJokeText`, in a real app it'd depend if it's used only for one method or more generally in the class
    public JokeFetcher(HttpClient client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }

        this.client = client;
    }

    /**
     * Returns the joke with the specified ID.
     *
     * @param jokeId e.g., "R7UfaahVfFd"
     */
    public String getJokeText(String jokeId) {
        try (var connectionStream = client.get("https://icanhazdadjoke.com/j/" + jokeId);
             var s = new Scanner(connectionStream).useDelimiter("\\A")) {
            return s.next();
        } catch (IOException e) {
            return null;
        }
    }
}
