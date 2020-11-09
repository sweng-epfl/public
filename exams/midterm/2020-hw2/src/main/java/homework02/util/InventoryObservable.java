// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

package homework02.util;

/**
 * Classes implementing this interface can be observed for inventory changes.
 * Observers will be notified whenever the inventory is updated, including information about whether items were added or removed.
 */
public interface InventoryObservable {
    /**
     * Adds the given InventoryObserver.
     * If the same InventoryObserver was added previously, does nothing.
     *
     * @param observer The observer, which cannot be null
     */
    void addObserver(InventoryObserver observer);
}
