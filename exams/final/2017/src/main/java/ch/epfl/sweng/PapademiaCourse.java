package ch.epfl.sweng;

/**
 * Papademia course.
 */
public final class PapademiaCourse {
    /**
     * The course's name.
     */
    public final String name;


    public PapademiaCourse(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        this.name = name;
    }
}
