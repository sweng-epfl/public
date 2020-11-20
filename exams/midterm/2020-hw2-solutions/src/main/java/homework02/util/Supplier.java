// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

package homework02.util;

/**
 * Resupplies items automatically given a configurable threshold and quantity.
 * Both parameters default to 0.
 */
public interface Supplier extends InventoryObserver {

    /**
     * Sets the threshold to the given value.
     *
     * @param threshold The new threshold, which must be >= 0
     */
    void setThreshold(int threshold);

    /**
     * Sets the supply quantity to the given value.
     * If the given value is 0, automatically sets the threshold to 0 as well.
     *
     * @param supplyQuantity The new supply quantity, which must be >= 0
     */
    void setSupplyQuantity(int supplyQuantity);
}
