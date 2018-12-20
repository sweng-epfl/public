package ch.epfl.sweng;

// !!!!!!!!!!!!!!!!!!!!!!!
// DO NOT MODIFY THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!!

/**
 * A user, identified by a name.
 * Two users are considered identical if they have the same name.
 */
public abstract class User {
    /**
     * Gets the name of the user.
     * The name cannot be null.
     */
    public abstract String getName();

    /**
     * Determines whether this user can ask a question with the specified text.
     * This method can assume that its arguments are not null.
     */
    public abstract boolean canAsk(String text);

    /**
     * Determines whether this user can post an answer with the specified text.
     * This method can assume that its arguments are not null.
     */
    public abstract boolean canAnswer(Question question, String text);

    /**
     * Determines whether this user can edit the specified post with the specified new text.
     * This method can assume that its arguments are not null.
     */
    public abstract boolean canEdit(Post post, String text);

    /**
     * Determines whether this user is equal to the specified object.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }

        return ((User) o).getName().equals(getName());
    }

    /**
     * Gets this user's hash code.
     */
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}