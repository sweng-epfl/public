import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class Joker {
    public static void getJokes(int count) {
        for (int n = 0; n < count; n++) {
            URL url;
            try {
                url = new URL("https://icanhazdadjoke.com/");
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Bad URL");
            }
            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                throw new RuntimeException("Cannot connect to jokes server.");
            }
            connection.setRequestProperty("Accept", "text/plain");
            try (var connectionStream = connection.getInputStream();
                 var s = new Scanner(connectionStream).useDelimiter("\\A")) {
                System.out.println("Joke " + n + ": " + s.next());
            } catch (IOException e) {
                throw new RuntimeException("Cannot fetch jokes.");
            }
        }
    }
}
