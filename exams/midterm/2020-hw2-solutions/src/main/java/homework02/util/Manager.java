// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

package homework02.util;

import homework02.ItemKind;

/**
 * Provides operations on an inventory, including tracking.
 */
public interface Manager extends InventoryObservable {

    /**
     * Adds one item of the given kind to the underlying database
     *
     * @param itemKind The item kind, which cannot be null
     */
    void add(ItemKind itemKind);

    /**
     * Takes an item out of the inventory if the inventory contains at least one such item, and
     * updates the corresponding available quantity.
     * Returns {@code true} if there was an item to take.
     * If there is no such item in the inventory, returns {@code false}.
     * Movements should only be sent to observers if an item was taken.
     *
     * @param itemKind The item kind, which cannot be null
     */
    boolean take(ItemKind itemKind);

    /**
     * Gets the current inventory quantity for the given {@link ItemKind}.
     *
     * @param itemKind The item kind, which cannot be null
     */
    int getQuantity(ItemKind itemKind);

}
