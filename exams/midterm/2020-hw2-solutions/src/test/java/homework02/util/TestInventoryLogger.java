package homework02.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link Logger} interface. See the interface documentation.
 */
public class TestInventoryLogger implements Logger {
    private List<String> movements;

    /**
     * Creates a new logger for the given manager.
     *
     * @param manager The manager, which cannot be null
     */
    public TestInventoryLogger(Manager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("The manager cannot be null");
        }

        this.movements = new ArrayList<>();
        manager.addObserver(this);
    }

    @Override
    public List<String> getMovements() {
        return new ArrayList<>(movements);
    }

    @Override
    public void empty() {
        movements = new ArrayList<>();
    }

    @Override
    public void update(Movement movement) {
        if (movement == null) {
            throw new IllegalArgumentException("The movement cannot be null");
        }

        movements.add(movement.toString());
    }
}
