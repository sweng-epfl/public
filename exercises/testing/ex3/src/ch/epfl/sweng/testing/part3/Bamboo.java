package ch.epfl.sweng.testing.part4;

public class Bamboo extends Entity {
    private int length;
    private int age;

    public Bamboo(Position position) {
        super(position);
        length = 1;
        age = 0;
    }

    /**
     * Can grow indefinitely
     * @return true
     */
    public boolean canGrow() {
        return true;
    }

    /**
     * Increases the age and gains in length every 2 years.
     */
    public void update() {
        age += 1;

        if (age % 2 == 1) {
            length += 1;
        }
    }

    public int getAge() {
        return age;
    }

    public int getLength() {
        return length;
    }
}
