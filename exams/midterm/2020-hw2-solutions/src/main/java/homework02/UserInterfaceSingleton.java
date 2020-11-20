package homework02;

import homework02.util.Logger;
import homework02.util.Manager;
import homework02.util.Supplier;

/**
 * Provides a single instance of the user interface.
 */
public class UserInterfaceSingleton {

    private static UserInterface instance = null;
    

    /**
     * Returns the current user interface instance.
     * If {@link UserInterfaceSingleton#init(Manager, Logger, Supplier)} has not been called yet, returns null.
     */
    public static UserInterface getInstance() {
        return instance;
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
        if (manager == null) {
            throw new IllegalArgumentException("The manager cannot be null");
        }

        if (logger == null) {
            throw new IllegalArgumentException("The logger cannot be null");
        }

        if (supplier == null) {
            throw new IllegalArgumentException("The supplier cannot be null");
        }

        instance = new UserInterface(manager, logger, supplier);
    }

}
