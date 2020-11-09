// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

package homework02.util;

/**
 * Classes implementing this interface can register with an {@link InventoryObservable} to be notified of inventory movements.
 */
public interface InventoryObserver {
    /**
     * Called when an inventory movement happened.
     *
     * @param movement The movement, which cannot be null
     */
    void update(Movement movement);
}
