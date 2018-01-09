package ch.epfl.sweng;

/**
 * Poodle user.
 */
public final class User {
    /**
     * The user's name.
     */
    public final String name;


    public User(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof User && name.equals(((User) obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
