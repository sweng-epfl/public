package ch.epfl.sweng;

// !!!!!!!!!!!!!!!!!!!!!!!
// DO NOT MODIFY THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!!

public final class Answer extends Post {
    private final Question question;

    /**
     * Constructs an answer of the specified user, with the specified text.
     */
    Answer(Question question, User author, String text) {
        super(author, text);
        this.question = question;
    }

    Question getQuestion() {
        return question;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Answer)) {
            return false;
        }

        Answer answer = (Answer) o;
        return getAuthor().equals(answer.getAuthor())
                && getText().equals(answer.getText());
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash += getAuthor().hashCode();
        hash *= 17;
        hash += getText().hashCode();
        return hash;
    }
}