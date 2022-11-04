import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Client for the JaaS API.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN add new constructors, methods, and nested classes to this class.
 * You CAN edit the parameters, return types, and checked exceptions of the existing method and constructor.
 * You MUST NOT edit the names of the existing method and constructor.
 * You MUST NOT rename this file.
 * You MUST NOT delete this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class JaasClient {

    private final String path;

    private final Input input;
    private final Output output;
    private final Server server;

    /**
     * Creates a new client for the JaaS API.
     */
    public JaasClient(String path, Input input, Output output, Server server) {
        if (path == null) {
            throw new IllegalArgumentException("JaaS backend URL may not be null.");
        }
        this.path = path;
        this.input = input;
        this.output = output;
        this.server = server;
    }

    /**
     * Runs the client, querying for users with specific roles and printing them.
     */
    public void run() {

        // Retrieve the list of roles.
        var roles = new TreeSet<String>();
        while (input.hasNextLine()) {
            var line = input.nextLine();
            if (line.isEmpty()) {
                break;
            }
            roles.add(line);
        }

        var url = path + "/users/" + String.join(",", roles);

        String users;
        try (var stream = server.openStream(url)) {
            // Using \\A as the delimiter is a Java trick to get the Scanner to read the entire input
            var s = new Scanner(stream).useDelimiter("\\A");
            users = s.next();
        } catch (IOException e) {
            output.println("Users could not be fetched from the server.");
            return;
        }

        for (var user : users.split("\n")) {
            output.println(user);
        }
    }
}

interface Input {

    boolean hasNextLine();

    String nextLine();
}

@FunctionalInterface
interface Output {

    void println(String line);
}

@FunctionalInterface
interface Server {

    InputStream openStream(String url) throws IOException;
}
