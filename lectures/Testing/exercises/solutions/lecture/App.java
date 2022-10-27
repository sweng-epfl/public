import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class App {
    public static void main(String[] args) {
        var fetcher = new JokeFetcher(new RealHttpClient());
        var joke = fetcher.getJokeText("R7UfaahVfFd");
        if (joke == null) {
            System.out.println("Cannot fetch joke.");
        } else {
            System.out.println(joke);
        }
    }
}

final class RealHttpClient implements HttpClient {
    @Override
    public InputStream get(String url) throws IOException {
        URL javaUrl;
        try {
            javaUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Bad URL");
        }
        var connection = (HttpURLConnection) javaUrl.openConnection();
        connection.setRequestProperty("Accept", "text/plain");
        return connection.getInputStream();
    }
}
