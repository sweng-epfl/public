package ch.epfl.sweng.testing.part4;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the x-coordinate
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the y-coordinate
     * @return y
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Position)) {
            return false;
        }

        Position other = (Position) obj;

        return other.getX() == y && other.getY() == x;
    }
}
