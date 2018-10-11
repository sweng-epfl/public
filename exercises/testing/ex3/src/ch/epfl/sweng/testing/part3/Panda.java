package ch.epfl.sweng.testing.part4;

public class Panda extends Entity {
    private Position position;
    private int age;

    public Panda(Position position) {
        super(position);
        age = 0;
    }

    /**
     * Can grow if its age is positive but smaller or equal to 20
     *
     * @return true si l'âge correspond aux contraintes données, false sinon
     */
    @Override
    public boolean canGrow() {
        return (age > 0 && age >= 20);
    }

    /**
     * Increases the age by 1
     */
    @Override
    public void update() {
        age += 1;
    }

    public int getAge() {
        return age;
    }
}
