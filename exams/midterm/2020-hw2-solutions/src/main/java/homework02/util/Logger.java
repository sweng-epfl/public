// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

package homework02.util;

import java.util.List;

/**
 * Logger of inventory movements.
 */
public interface Logger extends InventoryObserver {
    /**
     * Gets all the logged movements as a list of strings, using the {@link Movement} class's toString method.
     */
    List<String> getMovements();

    /**
     * Empties the list of logged movements.
     */
    void empty();
}
