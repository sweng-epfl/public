import java.io.IOException;
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

    /**
     * Creates a new client for the JaaS API.
     */
    public JaasClient(String path) {
        if (path == null) {
            throw new IllegalArgumentException("JaaS backend URL may not be null.");
        }
        this.path = path;
    }

    /**
     * Runs the client, querying for users with specific roles and printing them.
     */
    public void run() {

        // Retrieve the list of roles.
        var roles = new TreeSet<String>();
        var input = new Scanner(System.in);
        while (input.hasNextLine()) {
            var line = input.nextLine();
            if (line.isEmpty()) {
                break;
            }
            roles.add(line);
        }

        var url = path + "/users/" + String.join(",", roles);

        String users;
        try (var stream = new URL(url).openStream()) {
            // Using \\A as the delimiter is a Java trick to get the Scanner to read the entire input
            var s = new Scanner(stream).useDelimiter("\\A");
            users = s.next();
        } catch (MalformedURLException exception) {
            throw new IllegalArgumentException("Bad roles");
        } catch (IOException e) {
            System.out.println("Users could not be fetched from the server.");
            return;
        }

        for (var user : users.split("\n")) {
            System.out.println(user);
        }
    }
}