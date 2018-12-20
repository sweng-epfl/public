package ch.epfl.sweng;

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CANNOT edit or remove the signatures of the existing methods/constructors.
// You CANNOT add new constructors.
// You CAN change the implementation of the methods/constructors.
// You CAN add new methods.
// You CAN add interface implementations.
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/**
 * A limited user, which is an user that is banned from using a specific word:
 * - The word cannot appear in a question text.
 * - The word cannot appear in an answer text.
 * - The word cannot be edited in.
 * - The word must be detected in a case-insensitive manner.
 * Aside from these limitations, limited users inherit their wrapped user's permissions.
 * The banned word is "Travis".
 */
public class LimitedUser extends User {
    private final User user;

    /**
     * Constructs a new limited user wrapping the specified user.
     *
     * @throws IllegalArgumentException if the user is null.
     */
    public LimitedUser(User user) {
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
        return isAllowed(text) && user.canAsk(text);
    }

    @Override
    public boolean canAnswer(Question question, String text) {
        return isAllowed(text) && user.canAnswer(question, text);
    }

    @Override
    public boolean canEdit(Post post, String text) {
        return isAllowed(text) && user.canEdit(post, text);
    }

    private static boolean isAllowed(String text) {
        return !text.toLowerCase().contains("travis");
    }
}