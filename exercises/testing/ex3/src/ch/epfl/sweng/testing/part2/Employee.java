package ch.epfl.sweng.testing.part3;

import static ch.epfl.sweng.testing.part3.Level.PRINCIPAL;
import static ch.epfl.sweng.testing.part3.Level.SENIOR;
import static ch.epfl.sweng.testing.part3.Level.STAFF;

public class Employee {
    private String name;
    private int experience;
    private Level level;

    public Employee(String name, int experience) {
        this.name = name;
        this.experience = experience;
        level = Level.JUNIOR;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public Level getLevel() {
        return level;
    }

    public void improveLevel() {
        switch(level) {
            case JUNIOR:
                level = SENIOR;
                break;

            case SENIOR:
                level = STAFF;
                break;

            case STAFF:
                level = PRINCIPAL;
                break;

            default:
                break;

        }
    }
}
