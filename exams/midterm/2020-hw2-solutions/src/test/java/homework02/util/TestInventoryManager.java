package homework02.util;


import homework02.InventoryDatabase;
import homework02.ItemKind;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link Manager} interface. See the interface documentation.
 */
public class TestInventoryManager implements Manager {

    private final List<InventoryObserver> observers;
    private final InventoryDatabase database;

    /**
     * Creates a new manager for the given database.
     *
     * @param database The database, which cannot be null
     */
    public TestInventoryManager(InventoryDatabase database) {
        if (database == null) {
            throw new IllegalArgumentException("The database cannot be null");
        }

        this.database = new InventoryDatabase(database);
        this.observers = new ArrayList<>();
    }


    @Override
    public void add(ItemKind itemKind) {
        int currentQuantity = database.get(itemKind) == null ? 0 : database.get(itemKind);
        database.update(itemKind, currentQuantity + 1);
        notifyObservers(itemKind, MovementType.ADD);
    }

    @Override
    public boolean take(ItemKind itemKind) {
        Integer currentQuantity = database.get(itemKind);
        if (currentQuantity == null || currentQuantity <= 0) {
            return false;
        }
        database.update(itemKind, currentQuantity - 1);
        notifyObservers(itemKind, MovementType.REMOVE);
        return true;
    }


    @Override
    public int getQuantity(ItemKind itemKind) {
        Integer currentQuantity = database.get(itemKind);
        if (currentQuantity == null) {
            return 0;
        }
        return currentQuantity;
    }

    @Override
    public void addObserver(InventoryObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer cannot be null");
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    private void notifyObservers(ItemKind itemKind, MovementType movementType) {
        assert itemKind != null : "The itemKind cannot be null";
        assert movementType != null : "The movementType cannot be null";

        Movement movement = new Movement(itemKind, movementType);
        observers.forEach(observer -> observer.update(movement));
    }
}