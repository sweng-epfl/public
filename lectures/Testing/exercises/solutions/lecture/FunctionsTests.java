import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

final class ExerciseTests {
    @Test
    void fibonacci1Is1() {
        assertThat(Functions.fibonacci(1), is(1));
    }

    @Test
    void fibonacci10Is55() {
        assertThat(Functions.fibonacci(10), is(55));
    }

    @Test
    void fibonacciMinusOneThrows() {
        assertThrows(IllegalArgumentException.class, () -> Functions.fibonacci(-1));
    }

    @Test
    void splitWithMiddleSeparator() {
        assertThat(Functions.splitString("a b", ' '), contains("a", "b"));
    }

    @Test
    void shuffleDoesNotModifyIndividualItems() {
        // Arrange
        var vals = new String[]{"a", "b", "c"};
        // Act
        Functions.shuffle(vals);
        // Assert
        assertThat(vals,  arrayContainingInAnyOrder("a", "b", "c"));
    }
}
