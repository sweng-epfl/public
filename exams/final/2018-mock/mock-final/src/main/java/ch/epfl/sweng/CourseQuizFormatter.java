package ch.epfl.sweng;

/**
 * Poodle course quiz formatter.
 */
public interface CourseQuizFormatter {
    /**
     * Gets a greeting for the user.
     */
    String getGreeting();

    /**
     * Gets a question's text based on its statement and answer..
     */
    String getQuestionText(String statement, String answer);
}
