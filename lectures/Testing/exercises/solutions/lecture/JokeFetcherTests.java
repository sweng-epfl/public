import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

final class ExerciseTests {
    @Test
    void jokeFetcherReturnsNullOnIOError() {
        var client = new HttpClient() {
            @Override
            public InputStream get(String url) throws IOException {
                throw new IOException("Fake exception");
            }
        };
        var fetcher = new JokeFetcher(client);
        assertThat(fetcher.getJokeText("x"), is(nullValue()));
    }

    @Test
    void jokeFetcherReturnsFullText() {
        var client = new HttpClient() {
            @Override
            public InputStream get(String url) throws IOException {
                return new ByteArrayInputStream("Hello!".getBytes(StandardCharsets.UTF_8));
            }
        };
        var fetcher = new JokeFetcher(client);
        assertThat(fetcher.getJokeText("x"), is("Hello!"));
    }

    @Test
    void jokeFetcherPutsIdInUrl() {
        var flag = new AtomicBoolean(false); // atomicity doesn't matter, we just need a way to pass a boolean by reference
        var client = new HttpClient() {
            @Override
            public InputStream get(String url) throws IOException {
                assertThat(url, is("https://icanhazdadjoke.com/j/XYZ"));
                flag.set(true);
                return new ByteArrayInputStream("Hello!".getBytes(StandardCharsets.UTF_8));
            }
        };
        var fetcher = new JokeFetcher(client);
        fetcher.getJokeText("XYZ");
        assertThat(flag.get(), is(true)); // the fetcher must have actually called the client for this test to make sense
    }
}
