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
 * Represents an user interface for a client interacting with the inventory.
 */
public class UserInterface {

    /**
     * Creates a new user interface using the given components.
     *
     * @param manager  The manager, which cannot be null
     * @param logger   The logger, which cannot be null
     * @param supplier The supplier, which cannot be null
     */
    public UserInterface(Manager manager, Logger logger, Supplier supplier) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Adds one item of the given kind.
     *
     * @param itemKind The item kind, which cannot be null
     */
    public void add(ItemKind itemKind) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Takes on item out of the inventory (corresponding to the given ItemKind).
     * Returns true if there was an item in the inventory, false otherwise.
     *
     * @param itemKind The item kind, which cannot be null
     */
    public boolean take(ItemKind itemKind) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Sets the supplier's threshold.
     *
     * @param threshold The new value for the threshold
     */
    public void setThreshold(int threshold) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Sets the supplier's supply quantity.
     *
     * @param supplyQuantity The new value for the supply quantity
     */
    public void setSupplyQuantity(int supplyQuantity) {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Gets a string composed of all movements' strings, separated by a newline character,
     * in the order in which they were produced.
     * The newline at the end is optional.
     */
    public String getLog() {
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * Gets a string composed of one line for each item kind value.
     * Each line contains the current quantity in the inventory, in the format `ItemKind=\t{itemKind_NAME}\tQuantity=\t{quantity}`,
     * replacing `{value}` by the corresponding value.
     * The order in which they appear does not matter.
     * The newline at the end is optional.
     */
    public String getItems() {
        throw new UnsupportedOperationException("TODO");
    }
}
