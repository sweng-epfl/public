import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

import static java.lang.System.lineSeparator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Class for the JaaS API client tests.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You MUST use this file for the users fetcher tests.
 * You CAN add new constructors, methods, and nested classes to this class.
 * You MUST NOT rename this file.
 * You MUST NOT delete this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class JaasClientTests {

    @Test
    void nullUrlThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new JaasClient(null, new StringInput(""), new StringOutput(), new FailingServer())
        );
    }

    @Test
    void successfulServerPrintsJokes() {
        var output = new StringOutput();
        var server = new SuccessfulServer("User 1");
        var client = new JaasClient("https://example.org", new StringInput("admin"), output, server);

        client.run();

        assertThat(output.toString(), is("User 1\n"));
    }

    @Test
    void failingServerOutputsError() {
        var output = new StringOutput();
        var server = new FailingServer();
        var client = new JaasClient("https://example.org", new StringInput("admin"), output, server);

        client.run();

        assertThat(output.toString(), is("Users could not be fetched from the server.\n"));
    }

    @Test
    void emptyInputGeneratesUrlWithoutRoles() {
        var output = new StringOutput();
        var server = new SuccessfulServer("User 1");
        var client = new JaasClient("https://example.org", new StringInput(""), output, server);

        client.run();

        assertThat(server.lastUrl, is("https://example.org/users/"));
    }
}

class StringInput implements Input {

    private final Iterator<String> iterator;

    public StringInput(String... lines) {
        this.iterator = Arrays.asList(lines).iterator();
    }

    @Override
    public boolean hasNextLine() {
        return iterator.hasNext();
    }

    @Override
    public String nextLine() {
        return iterator.next();
    }
}

class StringOutput implements Output {

    private final StringBuilder builder = new StringBuilder();

    @Override
    public void println(String line) {
        builder.append(line).append(lineSeparator());
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}

class FailingServer implements Server {

    @Override
    public InputStream openStream(String ignored) throws IOException {
        throw new IOException();
    }
}

class SuccessfulServer implements Server {

    private final String[] users;
    String lastUrl;

    public SuccessfulServer(String... users) {
        this.users = users;
    }

    @Override
    public InputStream openStream(String url) {
        lastUrl = url;
        return new ByteArrayInputStream(String.join("\n", users).getBytes());
    }
}
