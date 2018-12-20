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
    private final User user;

    /**
     * Constructs a new moderator wrapping the specified user.
     *
     * @throws IllegalArgumentException if the user is null.
     */
    public Moderator(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Null arg.");
        }

        this.user = user;
    }

    // See the base class' documentation for the other methods

    @Override
    public String getName() {
        return user.getName();
    }

    @Override
    public boolean canAsk(String text) {
        return user.canAsk(text);
    }

    @Override
    public boolean canAnswer(Question question, String text) {
        return true;
    }

    @Override
    public boolean canEdit(Post post, String text) {
        return true;
    }
}