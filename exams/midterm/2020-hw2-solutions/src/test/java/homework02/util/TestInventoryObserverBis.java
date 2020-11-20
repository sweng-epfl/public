package homework02.util;

public class TestInventoryObserverBis implements InventoryObserver {

    private int counter;

    public int getUpdateCounter() {
        return counter;
    }

    @Override
    public void update(Movement movement) {
        counter += 1;
    }
}
