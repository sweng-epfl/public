// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
package homework03;

import homework03.util.Client;
import homework03.util.Server;

import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN change the bodies of existing methods/constructors
 * You CAN add new private methods/constructors
 * You CANNOT add interface implementations unless explicitly instructed to do so
 * You CANNOT add new public, package-private or protected methods/constructors
 * You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
 * You CANNOT delete existing methods/constructors
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class ClientImpl implements Client {

    private final String sciper;
    private final Server server;

    /**
     * The constructor checks that the given SCIPER has the right format, i.e. 6 digits from 0 to 9
     */
    public ClientImpl(String sciper, Server server) {
        if (sciper == null) {
            throw new IllegalArgumentException("The SCIPER cannot be null");
        }

        if (!Pattern.matches("[0-9]{6}", sciper)) {
            throw new IllegalArgumentException("The SCIPER must be in the format XXXXXX where X is a digit from 0 to 9");
        }

        if (server == null) {
            throw new IllegalArgumentException("The server cannot be null");
        }

        this.sciper = sciper;
        this.server = server;
    }

    @Override
    public void book(String day, Consumer<String> onSuccess, Consumer<Exception> onError) {
        if (day == null) {
            throw new IllegalArgumentException("The day cannot be null");
        }

        if (onSuccess == null) {
            throw new IllegalArgumentException("onSuccess callback cannot be null");
        }

        if (onError == null) {
            throw new IllegalArgumentException("onError callback cannot be null");
        }


        server.book(sciper, day, onSuccess, onError);
    }

    @Override
    public void getBookings(Consumer<Set<String>> onSuccess, Consumer<Exception> onError) {
        if (onSuccess == null) {
            throw new IllegalArgumentException("onSuccess callback cannot be null");
        }

        if (onError == null) {
            throw new IllegalArgumentException("onError callback cannot be null");
        }

        server.getBookings(sciper, onSuccess, onError);
    }
}
