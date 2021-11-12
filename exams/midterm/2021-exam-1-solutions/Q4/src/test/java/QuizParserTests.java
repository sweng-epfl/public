import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class QuizParserTests {
    @Test
    public void nullTextIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new QuizParser().parse(null));
    }

    @Test
    public void emptyTextIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new QuizParser().parse(""));
    }

    @Test
    public void wrongHeadingStartIsInvalid() {
        assertThrows(QuizFormatException.class, () -> new QuizParser().parse("Question\n- Choice 1\n- Choice 2\n> Solution"));
    }

    @Test
    public void missingLinesAfterHeadingIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new QuizParser().parse("# Question\n"));
    }

    @Test
    public void questionWithoutChoicesIsInvalid() {
        assertThrows(QuizFormatException.class, () -> new QuizParser().parse("# Question\n> Solution"));
    }

    @Test
    public void questionWithoutSolutionIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new QuizParser().parse("# Question\n- Choice 1\n- Choice 2"));
    }

    @Test
    public void questionWithoutSolutionPrefixIsInvalid() {
        assertThrows(QuizFormatException.class, () -> new QuizParser().parse("# Question\n- Choice 1\n- Choice 2\nSolution"));
    }

    @Test
    public void extraLinesAfterSolutionAreInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new QuizParser().parse("# Question\n- Choice 1\n- Choice 2\n> Solution\nSomething else"));
    }

    @Nested
    public final class ValidQuestion {
        private final QuizQuestion question;

        public ValidQuestion() {
            // Arrange
            var text = "# Question\n- Choice 1\n- Choice 2\n> Solution";
            // Act
            question = new QuizParser().parse(text);
        }

        // Assert

        @Test
        public void heading() {
            assertThat(question.heading, is("Question"));
        }

        @Test
        public void choices() {
            assertThat(question.choices, contains("Choice 1", "Choice 2"));
        }

        @Test
        public void solution() {
            assertThat(question.solution, is("Solution"));
        }
    }
}
