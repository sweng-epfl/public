package ch.epfl.sweng;

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CANNOT edit or remove the signatures of the existing methods/constructors.
// You CANNOT add new constructors.
// You CAN change the implementation of the methods/constructors.
// You CAN add new methods.
// You CAN add interface implementations.
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/**
 * A moderator, which is a special kind of user that has additional powers:
 * - Moderators can answer any question without any restrictions.
 * - Moderators can edit any posts without any restrictions.
 * Moderation powers do not change the wrapped user's name or their permissions to ask questions.
 */
public class Moderator extends User {
    /**
     * Constructs a new moderator wrapping the specified user.
     *
     * @throws IllegalArgumentException if the user is null.
     */
    public Moderator(User user) {
        // TODO
        throw new UnsupportedOperationException();
    }

    // See the base class' documentation for the other methods

    @Override
    public String getName() {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canAsk(String text) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canAnswer(Question question, String text) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canEdit(Post post, String text) {
        // TODO
        throw new UnsupportedOperationException();
    }
}
