// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

package homework02;

import homework02.util.Logger;
import homework02.util.Manager;
import homework02.util.Supplier;

/**
 * Provides a single instance of the user interface.
 */
public class UserInterfaceSingleton {
    /**
     * Returns the current user interface instance.
     * If {@link UserInterfaceSingleton#init(Manager, Logger, Supplier)} has not been called yet, returns null.
     */
    public static UserInterface getInstance() {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Initializes the singleton with the given parameters.
     * If this method is called again, the current instance is replaced with a new one using the given parameters.
     *
     * @param manager  The manager to use, which cannot be null
     * @param logger   The logger to use, which cannot be null
     * @param supplier The supplier to use, which cannot be null
     */
    public static void init(Manager manager, Logger logger, Supplier supplier) {
        throw new UnsupportedOperationException("TODO");
    }
}
