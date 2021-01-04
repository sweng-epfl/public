// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
package homework03;

import homework03.util.Database;
import homework03.util.Server;

import java.util.Set;
import java.util.function.Consumer;

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
public class ServerImpl implements Server {

    public ServerImpl(Database database) {
        throw new UnsupportedOperationException("TODO: Replace this line with your code.");
    }

    @Override
    public void book(String sciper, String day, Consumer<String> onSuccess, Consumer<Exception> onError) {
        throw new UnsupportedOperationException("TODO: Replace this line with your code.");
    }

    @Override
    public void getBookings(String sciper, Consumer<Set<String>> onSuccess, Consumer<Exception> onError) {
        throw new UnsupportedOperationException("TODO: Replace this line with your code.");
    }
}
