package ch.epfl.sweng;

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CANNOT edit or remove the signatures of the existing methods/constructors.
// You CANNOT add new constructors.
// You CAN change the implementation of the methods/constructors.
// You CAN add new methods.
// You CAN add interface implementations.
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Forum {
    private final List<Question> questions;

    /**
     * Constructs an empty forum.
     */
    public Forum() {
        questions = new ArrayList<>();
    }

    /**
     * Gets the forum's questions.
     */
    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    /**
     * Posts a question from the specified user with the specified text.
     *
     * @throws IllegalArgumentException  if the user or text is null.
     * @throws IllegalOperationException if the user cannot perform this operation.
     */
    public void postQuestion(User user, String text) {
        // TODO (remove the following if block, and write the rest of the implementation)
        if (true) {
            throw new UnsupportedOperationException();
        }

        questions.add(new Question(user, text));
    }

    /**
     * Posts an answer from the specified user to the specified question with the specified text.
     *
     * @throws IllegalArgumentException  if the user, question or text is null.
     * @throws NoSuchPostException       if the question does not belong to the forum.
     * @throws IllegalOperationException if the user cannot perform this operation.
     */
    public void postAnswer(User user, Question question, String text) {
        // TODO (remove the following if block, and write the rest of the implementation)
        if (true) {
            throw new UnsupportedOperationException();
        }

        question.addAnswer(new Answer(question, user, text));
    }

    /**
     * Edits the specified post, as the specified user, with the specified new text.
     *
     * @throws IllegalArgumentException  if the user, post or text is null.
     * @throws NoSuchPostException       if the post does not belong to the forum.
     * @throws IllegalOperationException if the user cannot perform this operation.
     */
    public void editPost(User user, Post post, String text) {
        // TODO (remove the following if block, and write the rest of the implementation)
        if (true) {
            throw new UnsupportedOperationException();
        }

        post.setText(text);
    }
}