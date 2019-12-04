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

public final class Forum implements Observable {
    private final List<Question> questions;
    private final List<Observer> observers;

    /**
     * Constructs an empty forum.
     */
    public Forum() {
        questions = new ArrayList<>();
        observers = new ArrayList<>();
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
        if (user == null || text == null) {
            throw new IllegalArgumentException("Null args.");
        }
        if (!user.canAsk(text)) {
            throw new IllegalOperationException();
        }

        Question question = new Question(user, text);
        questions.add(question);
        notifyObservers(question);
    }

    /**
     * Posts an answer from the specified user to the specified question with the specified text.
     *
     * @throws IllegalArgumentException  if the user, question or text is null.
     * @throws NoSuchPostException       if the question does not belong to the forum.
     * @throws IllegalOperationException if the user cannot perform this operation.
     */
    public void postAnswer(User user, Question question, String text) {
        if (user == null || question == null || text == null) {
            throw new IllegalArgumentException("Null args.");
        }
        if (!questions.contains(question)) {
            throw new NoSuchPostException();
        }
        if (!user.canAnswer(question, text)) {
            throw new IllegalOperationException();
        }

        Answer answer = new Answer(question, user, text);
        question.addAnswer(answer);
        notifyObservers(answer);
    }

    /**
     * Edits the specified post, as the specified user, with the specified new text.
     *
     * @throws IllegalArgumentException  if the user, post or text is null.
     * @throws NoSuchPostException       if the post does not belong to the forum.
     * @throws IllegalOperationException if the user cannot perform this operation.
     */
    public void editPost(User user, Post post, String text) {
        if (user == null || post == null || text == null) {
            throw new IllegalArgumentException("Null args.");
        }
        if (!questions.contains(post) && questions.stream().noneMatch(q -> q.getAnswers().contains(post))) {
            throw new NoSuchPostException();
        }
        if (!user.canEdit(post, text)) {
            throw new IllegalOperationException();
        }

        post.setText(text);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(Object arg) {
        observers.forEach(o -> o.update(this, arg));
    }
}