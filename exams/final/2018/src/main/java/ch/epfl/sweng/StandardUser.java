package ch.epfl.sweng;

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CANNOT edit or remove the signatures of the existing methods/constructors.
// You CANNOT add new constructors.
// You CAN change the implementation of the methods/constructors, EXCEPT when explicitly specified.
// You CAN add new methods.
// You CAN add interface implementations.
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.util.HashMap;
import java.util.Map;

public class StandardUser extends User {
    private final String name;

    /**
     * Constructs a standard user with the specified name.
     *
     * @throws IllegalArgumentException if the name is null.
     */
    public StandardUser(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    // Do not forget the base class' documentation for the permission methods

    /**
     * Standard users can only post questions if the text is 10 characters or more.
     */
    @Override
    public boolean canAsk(String text) {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     * Standard users can only answer other users' questions, not their own.
     */
    @Override
    public boolean canAnswer(Question question, String text) {
        // TODO
        throw new UnsupportedOperationException();
    }

    // It seems your colleagues wrote this function but forgot to document it!
    // Unfortunately, the Product Owner who requested this feature is away on holiday.
    // You'll have to understand what this method does to write tests for it...
    // You CANNOT change this method in any way.
    @Override
    public boolean canEdit(Post post, String text) {
        if (!post.getAuthor().equals(this)) {
            return false;
        }

        if (post instanceof Question) {
            if (((Question) post).getAnswers().isEmpty()) {
                return true;
            }

            return false;
        }

        Map<User, Integer> counts = new HashMap<>();
        for (Post answer : ((Answer) post).getQuestion().getAnswers()) {
            if (!counts.containsKey(answer.getAuthor())) {
                counts.put(answer.getAuthor(), 0);
            }

            int count = counts.get(answer.getAuthor());
            if (count >= 2) {
                return true;
            }

            counts.put(answer.getAuthor(), count + 1);
        }

        if (counts.size() >= 3 && ((Answer) post).getQuestion().getAnswers().size() > counts.size()) {
            return true;
        }

        return false;
    }
}