import java.io.IOException;

public interface HttpClient {
    String get(String url) throws IOException;
}
