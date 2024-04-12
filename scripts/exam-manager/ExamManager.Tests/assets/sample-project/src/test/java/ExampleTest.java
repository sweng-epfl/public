import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

final class ExampleTest {
    @Disabled
    void skippedThroughAnnotation() {
        AppNotCovered.main();
    }

    @Test
    void skippedThroughAssumption() {
        Assumptions.assumeTrue(false);
        AppNotCovered.main();
    }

    @Test
    void tryToBreakParsing() {
        throw new AssertionError("Let's try to break the parsing [ \\ \"");
    }

    @Test
    @DisplayName("Let's try to break the parsing [ \\ \" ")
    void customDisplayNameThatTriesToBreakParsing() {
        // Nothing
    }

    private static int repeatedCount = 0;
    @RepeatedTest(2)
    void repeatedButFailsSecondTime() {
        assertThat(repeatedCount % 2, is(0));
        repeatedCount++;
    }

    @RepeatedTest(name="Repeated with a name: {displayName} {currentRepetition} {totalRepetitions}", value=2)
    void repeatedWithAName() {
        // Nothing
    }

    @Nested
    static class NestedTest {
        @Test
        void coverApp() {
            App.main();
        }
    }

    @Test
    void useOfAssertAll() {
        Assertions.assertAll(
          () -> AppPartiallyCovered.main(true),
          () -> assertThat(new App(), is(not(nullValue())))
        );
    }

    @Test
    void failure() {
        assertThat(1, is(2));
    }
}
