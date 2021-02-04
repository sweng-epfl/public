package ch.epfl.sweng;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Example tests, provided for convenience.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN edit everything in this file
 * You CAN delete this file
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class ExampleTest {
    @Test
    public void howToTestIfAnExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException();
        });
    }

    @Test
    public void answerToTheUltimateQuestionOfLifeTheUniverseAndEverythingIsCorrect() {
        assertThat(42, is(42));
    }
}
