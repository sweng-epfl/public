package ch.epfl.sweng.testing.part4;

abstract class Entity {
    private Position position;

    public Entity(Position position) {
        this.position = position;
    }

    abstract public boolean canGrow();

    abstract public void update();

    /**
     * Get the position of the entity
     * @return the position of the entity
     */
    public Position getPosition() {
        return position;
    }
}
