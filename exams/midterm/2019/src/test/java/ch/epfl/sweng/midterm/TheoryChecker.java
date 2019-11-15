package ch.epfl.sweng.midterm;

// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * This test parses theory questions,
 * and makes sure the answers are in the right format.
 */
final class TheoryChecker {
    private static final int[] ANSWER_COUNT_PER_QUESTION = { 4, 4, 4, 4, 4, 5, 6, 6, 6, 4 };

    @Test
    void check() throws IOException, URISyntaxException {
        List<Boolean> currentAnswer = null;
        List<List<Boolean>> answersPerQuestion = new ArrayList<>();

        Path theoryPath = Paths.get("THEORY.md");
        if (!Files.exists(theoryPath)) {
            // Convoluted fallback in case IntelliJ uses some weird working dir:
            // Get the resources path, then go up to the 'build' dir, and find the THEORY.md on build's parent.
            // Still, gracefully handle the case where we somehow don't find the build dir
            Path buildPath = Paths.get(getClass().getClassLoader().getResource(".").toURI());
            while (buildPath.getFileName() != null && !buildPath.getFileName().toString().equals("build")) {
                buildPath = buildPath.getParent();
            }
            if (buildPath.getFileName() != null) {
                theoryPath = buildPath.getParent().resolve("THEORY.md");
            }
        }
        assertThat("The THEORY.md file must exist!",
                Files.exists(theoryPath), is(true));

        for (String line : Files.readAllLines(theoryPath)) {
            if (line.startsWith("### Question")) {
                if (currentAnswer != null) {
                    answersPerQuestion.add(currentAnswer);
                }
                currentAnswer = new ArrayList<>();
                continue;
            }

            if (line.startsWith("- [")) {
                assertThat("Theory choice boxes should be within a question!",
                        currentAnswer, not(nullValue()));

                if (line.startsWith("- [ ]")) {
                    currentAnswer.add(null);
                } else if (line.startsWith("- [y]")) {
                    currentAnswer.add(true);
                } else if (line.startsWith("- [n]")) {
                    currentAnswer.add(false);
                } else {
                    assertThat("Theory choice boxes should be filled only with 'y' or 'n', in lowercase!",
                            line, not(startsWith("- [")));
                }
            }
        }
        if (currentAnswer != null) {
            answersPerQuestion.add(currentAnswer);
        }

        assertThat("Number of questions in the theory should be correct!",
                answersPerQuestion.size(), is(ANSWER_COUNT_PER_QUESTION.length));

        for (int n = 0; n < ANSWER_COUNT_PER_QUESTION.length; n++) {
            assertThat("Theory question " + n + "'s number of answers should be correct!",
                    answersPerQuestion.get(n).size(), is(ANSWER_COUNT_PER_QUESTION[n]));
        }
    }
}
