package homework02.util;

public class TestInventoryObserver implements InventoryObserver {

    private Movement movement;

    public Movement getMovement() {
        return movement;
    }

    @Override
    public void update(Movement movement) {
        this.movement = movement;
    }
}
