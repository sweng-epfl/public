package homework02;

import homework02.util.Manager;
import homework02.util.Movement;
import homework02.util.Supplier;

/**
 * Implementation of the {@link Supplier} interface. See the interface documentation.
 */
public class InventorySupplier implements Supplier {

    private final Manager manager;

    private int threshold = 0;
    private int supplyQuantity = 0;
    private boolean resupplying = false;

    /**
     * Creates a new supplier for the given manager.
     *
     * @param manager The manager, which cannot be null
     */
    public InventorySupplier(Manager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("The manager cannot be null");
        }

        this.manager = manager;
        manager.addObserver(this);
    }

    @Override
    public void setThreshold(int threshold) {
        if (threshold < 0) {
            throw new IllegalArgumentException("The threshold must be non-negative.");
        }

        this.threshold = threshold;
    }

    @Override
    public void setSupplyQuantity(int supplyQuantity) {
        if (supplyQuantity < 0) {
            throw new IllegalArgumentException("The supply quantity must be non-negative.");
        }

        if (supplyQuantity == 0) {
            setThreshold(0);
        }

        this.supplyQuantity = supplyQuantity;
    }

    @Override
    public void update(Movement movement) {
        if (movement == null) {
            throw new IllegalArgumentException("The movement cannot be null");
        }

        if (!resupplying && supplyQuantity != 0) {
            resupplying = true;

            ItemKind kind = movement.itemKind;
            int quantity = manager.getQuantity(kind);
            while (quantity < threshold) {
                for (int i = 0; i < supplyQuantity; i++) {
                    manager.add(kind);
                }
                quantity = manager.getQuantity(kind);
            }
            resupplying = false;
        }
    }
}
