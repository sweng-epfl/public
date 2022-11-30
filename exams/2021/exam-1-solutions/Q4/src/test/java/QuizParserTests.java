import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class QuizParserTests {
    @Test
    public void parseThrowsIllegalArgOnNullText() {
        var parser = new QuizParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse(null));
    }

    @Test
    public void parseThrowsIllegalArgOnEmptyText() {
        var parser = new QuizParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""));
    }

    @Test
    public void parseThrowsWrongFormatOnWrongHeadingStart() {
        var parser = new QuizParser();
        assertThrows(QuizFormatException.class, () -> parser.parse("Question\n- Choice 1\n- Choice 2\n> Solution"));
    }

    @Test
    public void parseThrowsIllegalArgOnMissingLines() {
        var parser = new QuizParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse("# Question\n"));
    }

    @Test
    public void parseThrowsWrongFormatOnQuestionWithoutChoices() {
        var parser = new QuizParser();
        assertThrows(QuizFormatException.class, () -> parser.parse("# Question\n> Solution"));
    }

    @Test
    public void parseThrowsIllegalArgOnQuestionWithoutSolution() {
        var parser = new QuizParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse("# Question\n- Choice 1\n- Choice 2"));
    }

    @Test
    public void parseThrowsWrongFormatOnQuestionWithoutSolutionPrefix() {
        var parser = new QuizParser();
        assertThrows(QuizFormatException.class, () -> parser.parse("# Question\n- Choice 1\n- Choice 2\nSolution"));
    }

    @Test
    public void parseThrowsIllegalArgOnExtraLinesAfterSolution() {
        var parser = new QuizParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse("# Question\n- Choice 1\n- Choice 2\n> Solution\nSomething else"));
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
        public void validHeaderIsParsed() {
            assertThat(question.heading, is("Question"));
        }

        @Test
        public void validChoicesAreParsed() {
            assertThat(question.choices, contains("Choice 1", "Choice 2"));
        }

        @Test
        public void validSolutionIsParsed() {
            assertThat(question.solution, is("Solution"));
        }
    }
}
