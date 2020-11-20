// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

package homework02;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a database used to store the inventory.
 */
public class InventoryDatabase {
    private final Map<ItemKind, Integer> db;

    /**
     * Creates an empty database.
     */
    public InventoryDatabase() {
        this.db = new HashMap<>();
    }

    /**
     * Creates a copy of another database.
     *
     * @param database The database to copy, which cannot be null
     */
    public InventoryDatabase(InventoryDatabase database) {
        if (database == null) {
            throw new IllegalArgumentException("The database cannot be null");
        }

        this.db = new HashMap<>(database.db);
    }

    /**
     * Updates the quantity corresponding to the given {@link ItemKind} with the given value.
     * Passing a quantity smaller than 0 throws an {@link IllegalArgumentException}.
     *
     * @param kind     The item kind whose quantity should be updated, which cannot be null
     * @param quantity The new quantity for the given kind, which must be >=0
     */
    public void update(ItemKind kind, int quantity) {
        if (kind == null) {
            throw new IllegalArgumentException("The kind cannot be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("The quantity cannot be <0");
        }
        db.put(kind, quantity);
    }

    /**
     * Gets the quantity corresponding to the given {@link ItemKind}.
     *
     * @param kind The item kind whose quantity should be retrieved, which cannot be null
     */
    public Integer get(ItemKind kind) {
        if (kind == null) {
            throw new IllegalArgumentException("The kind cannot be null");
        }
        return db.getOrDefault(kind, 0);
    }
}