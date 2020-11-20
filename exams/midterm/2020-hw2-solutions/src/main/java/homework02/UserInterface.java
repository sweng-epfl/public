package homework02;

import homework02.util.Logger;
import homework02.util.Manager;
import homework02.util.Supplier;

import java.util.List;

/**
 * Represents an user interface for a client interacting with the inventory.
 */
public class UserInterface {

    private final Manager manager;
    private final Logger logger;
    private final Supplier supplier;

    /**
     * Creates a new user interface using the given components.
     *
     * @param manager  The manager, which cannot be null
     * @param logger   The logger, which cannot be null
     * @param supplier The supplier, which cannot be null
     */
    public UserInterface(Manager manager, Logger logger, Supplier supplier) {
        if (manager == null) {
            throw new IllegalArgumentException("The manager cannot be null");
        }

        if (logger == null) {
            throw new IllegalArgumentException("The logger cannot be null");
        }

        if (supplier == null) {
            throw new IllegalArgumentException("The supplier cannot be null");
        }

        this.manager = manager;
        this.logger = logger;
        this.supplier = supplier;
    }

    /**
     * Adds one item of the given kind.
     *
     * @param itemKind The item kind, which cannot be null
     */
    public void add(ItemKind itemKind) {
        if(itemKind == null){
            throw new IllegalArgumentException("The itemKind cannot be null");
        }
        manager.add(itemKind);
    }

    /**
     * Takes on item out of the inventory (corresponding to the given ItemKind).
     * Returns true if there was an item in the inventory, false otherwise.
     *
     * @param itemKind The item kind, which cannot be null
     */
    public boolean take(ItemKind itemKind) {
        if(itemKind == null){
            throw new IllegalArgumentException("The itemKind cannot be null");
        }
        return manager.take(itemKind);
    }

    /**
     * Sets the supplier's threshold.
     *
     * @param threshold The new value for the threshold
     */
    public void setThreshold(int threshold) {
        supplier.setThreshold(threshold);
    }

    /**
     * Sets the supplier's supply quantity.
     *
     * @param supplyQuantity The new value for the supply quantity
     */
    public void setSupplyQuantity(int supplyQuantity) {
        supplier.setSupplyQuantity(supplyQuantity);
    }

    /**
     * Gets a string composed of all movements' strings, separated by a newline character,
     * in the order in which they were produced.
     * The newline at the end is optional.
     */
    public String getLog() {
        List<String> movements = logger.getMovements();
        StringBuilder builder = new StringBuilder();
        for (String movement : movements) {
            builder.append(movement);
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Gets a string composed of one line for each item kind value.
     * Each line contains the current quantity in the inventory, in the format `ItemKind=\t{itemKind_NAME}\tQuantity=\t{quantity}`,
     * replacing `{value}` by the corresponding value.
     * The order in which they appear does not matter.
     * The newline at the end is optional.
     */
    public String getItems() {
        StringBuilder builder = new StringBuilder();
        for (ItemKind kind : ItemKind.values()) {
            builder.append("ItemKind=\t");
            builder.append(kind.getName());
            builder.append("\t");
            builder.append("Quantity=\t");
            builder.append(manager.getQuantity(kind));
            builder.append("\n");
        }
        return builder.toString();
    }
}
