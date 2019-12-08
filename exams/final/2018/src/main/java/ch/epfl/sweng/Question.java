package ch.epfl.sweng;

// !!!!!!!!!!!!!!!!!!!!!!!
// DO NOT MODIFY THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!!

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Question extends Post {
    private final List<Answer> answers = new ArrayList<>();

    /**
     * Constructs a question by the specified user with the specified text.
     */
    Question(User author, String text) {
        super(author, text);
    }

    /**
     * Adds the specified answer to this question.
     */
    void addAnswer(Answer answer) {
        answers.add(answer);
    }

    /**
     * Gets the answers of this question.
     */
    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Question)) {
            return false;
        }

        Question question = (Question) o;
        return getAuthor().equals(question.getAuthor())
                && getText().equals(question.getText())
                && answers.equals(question.answers);
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash += getAuthor().hashCode();
        hash *= 17;
        hash += getText().hashCode();
        hash *= 17;
        hash += answers.hashCode();
        return hash;
    }
}