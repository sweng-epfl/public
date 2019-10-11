import java.io.IOException;

public class RealHttpClient implements HttpClient {
    @Override
    public String get(String url) throws IOException {
        // Oh no! Looks like you can't use this class in tests even if you wanted to...
        throw new IOException("No internet connection.");
    }
}
