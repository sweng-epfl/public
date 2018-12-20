package ch.epfl.sweng.tests;

// !!!!!!!!!!!!!!!!!!!!!!!
// DO NOT MODIFY THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!!

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This test parses theory questions,
 * and makes sure the answers are in the right format.
 */
public final class TheoryReporter {
    private static final int[] ANSWER_COUNT_PER_QUESTION = {
            4, // Q1
            4, // Q2
            4, // Q3
            4, // Q4
            4, // Q5
            4, // Q6
            4, // Q7
            4, // Q8
            4, // Q9
            4, // Q10
    };

    @Test
    public void report() throws IOException {
        List<Boolean> currentAnswer = null;
        List<List<Boolean>> answersPerQuestion = new ArrayList<>();

        for (String line : Files.readAllLines(Paths.get("THEORY.md"))) {
            if (line.startsWith("### Question")) {
                if (currentAnswer != null) {
                    answersPerQuestion.add(currentAnswer);
                }
                currentAnswer = new ArrayList<>();
                continue;
            }

            if (line.startsWith("- [")) {
                assertThat("Theory choice boxes should be within a question!",
                        currentAnswer, is(not(nullValue())));

                if (line.startsWith("- [ ]")) {
                    currentAnswer.add(null);
                } else if (line.startsWith("- [y]")) {
                    currentAnswer.add(true);
                } else if (line.startsWith("- [n]")) {
                    currentAnswer.add(false);
                } else {
                    assertThat("Theory choice boxes should be filled only with 'y' or 'n'",
                            line, not(startsWith("- [")));
                }
            }
        }
        if (currentAnswer != null) {
            answersPerQuestion.add(currentAnswer);
        }

        assertThat("Number of questions in the theory should be correct.",
                answersPerQuestion.size(), is(ANSWER_COUNT_PER_QUESTION.length));

        for (int n = 0; n < ANSWER_COUNT_PER_QUESTION.length; n++) {
            assertThat("Theory question " + n + "'s number of answers should be correct.",
                    answersPerQuestion.get(n).size(), is(ANSWER_COUNT_PER_QUESTION[n]));
        }

        StringBuilder result = new StringBuilder();

        // Top header, with padding
        result.append("Theory");
        result.append(String.join("", Collections.nCopies(Arrays.stream(ANSWER_COUNT_PER_QUESTION).sum(), ",")));
        result.append(System.lineSeparator());

        // Question headers, with padding (index is 0-based but we display it as 1-based)
        for (int n = 0; n < ANSWER_COUNT_PER_QUESTION.length; n++) {
            result.append("Question ").append(n + 1);
            result.append(String.join("", Collections.nCopies(ANSWER_COUNT_PER_QUESTION[n], ",")));
        }
        result.append(System.lineSeparator());

        // Sub-question headers, all empty for automatically-graded questions
        result.append(String.join("", Collections.nCopies(Arrays.stream(ANSWER_COUNT_PER_QUESTION).sum(), ",")));
        result.append(System.lineSeparator());

        for (List<Boolean> questionAnswers : answersPerQuestion) {
            StringBuilder questionResult = new StringBuilder();
            for (Boolean answer : questionAnswers) {
                if (answer == null) {
                    // Unfinished question, ignore all
                    questionResult = null;
                }
                if (questionResult != null) {
                    questionResult.append(answer ? "Y" : "N");
                    questionResult.append(",");
                }
            }

            if (questionResult == null) {
                result.append(String.join("", Collections.nCopies(questionAnswers.size(), ",")));
            } else {
                result.append(questionResult.toString());
            }
        }

        Files.write(
                Paths.get("theory.log"),
                result.toString().getBytes(StandardCharsets.UTF_8)
        );
    }
}