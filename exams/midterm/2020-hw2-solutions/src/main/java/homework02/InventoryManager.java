package homework02;


import homework02.util.InventoryObserver;
import homework02.util.Manager;
import homework02.util.Movement;
import homework02.util.MovementType;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link Manager} interface. See the interface documentation.
 */
public class InventoryManager implements Manager {

    private final List<InventoryObserver> observers;
    private final InventoryDatabase database;

    /**
     * Creates a new manager for the given database.
     *
     * @param database The database, which cannot be null
     */
    public InventoryManager(InventoryDatabase database) {
        if (database == null) {
            throw new IllegalArgumentException("The database cannot be null");
        }

        this.database = new InventoryDatabase(database);
        this.observers = new ArrayList<>();
    }


    @Override
    public void add(ItemKind itemKind) {
        if (itemKind == null) {
            throw new IllegalArgumentException("The itemKind cannot be null");
        }
        int currentQuantity = database.get(itemKind);
        database.update(itemKind, currentQuantity + 1);
        notifyObservers(itemKind, MovementType.ADD);
    }

    @Override
    public boolean take(ItemKind itemKind) {
        if (itemKind == null) {
            throw new IllegalArgumentException("The itemKind cannot be null");
        }
        int currentQuantity = database.get(itemKind);
        if (currentQuantity <= 0) {
            return false;
        }
        database.update(itemKind, currentQuantity - 1);
        notifyObservers(itemKind, MovementType.REMOVE);
        return true;
    }

    @Override
    public int getQuantity(ItemKind itemKind) {
        if (itemKind == null) {
            throw new IllegalArgumentException("The itemKind cannot be null");
        }

        return database.get(itemKind);
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

    // Notifies all observers, triggering their `update` method.
    private void notifyObservers(ItemKind itemKind, MovementType movementType) {
        assert itemKind != null : "The itemKind cannot be null";
        assert movementType != null : "The movementType cannot be null";

        Movement movement = new Movement(itemKind, movementType);
        observers.forEach(observer -> observer.update(movement));
    }
}