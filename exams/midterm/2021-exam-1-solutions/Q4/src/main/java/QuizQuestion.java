import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing a quiz question, which is multiple-choice.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class QuizQuestion {
    /** The question's heading. */
    public final String heading;
    /** The question's choices. */
    public final List<String> choices;
    /** The question's solution. */
    public final String solution;

    /** Creates a new question with the given values. */
    public QuizQuestion(String heading, List<String> choices, String solution) {
        if (heading == null) {
            throw new IllegalArgumentException("Question headings cannot be null.");
        }
        if (choices == null) {
            throw new IllegalArgumentException("Question choice arrays cannot be null.");
        }
        if (solution == null) {
            throw new IllegalArgumentException("Question solutions cannot be null.");
        }
        for (String choice : choices) {
            if (choice == null) {
                throw new IllegalArgumentException("Question choices cannot be null");
            }
        }

        this.heading = heading;
        this.choices = Collections.unmodifiableList(new ArrayList<>(choices));
        this.solution = solution;
    }
}
