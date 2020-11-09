// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

package homework02;

import homework02.util.Logger;
import homework02.util.Manager;
import homework02.util.Movement;

import java.util.List;

/**
 * Implementation of the {@link Logger} interface. See the interface documentation.
 */
public class InventoryLogger implements Logger {
    /**
     * Creates a new logger for the given manager.
     *
     * @param manager The manager, which cannot be null
     */
    public InventoryLogger(Manager manager) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public List<String> getMovements() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void empty() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void update(Movement movement) {
        throw new UnsupportedOperationException("TODO");
    }
}
