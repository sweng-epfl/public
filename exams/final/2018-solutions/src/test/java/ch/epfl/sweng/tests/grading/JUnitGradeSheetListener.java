package ch.epfl.sweng.tests.grading;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import ch.epfl.sweng.tests.grading.models.*;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * JUnit listener that outputs a grade sheet.
 * ---
 * Implementation notes:
 * Since JUnit's RunListener only gives events, we must first build the entire suite in memory,
 * then find the corresponding test each time we get an event and update it.
 * Also, to put the total at the end, we keep the entire text in memory and (over-)write it all at once
 * to the file at the end of every suite, including the total (since we can't know when it's the last suite).
 */
final class JUnitGradeSheetListener extends RunListener {
    private final Path outputPath = Paths.get("practice.log");
    // Keep the suites sorted for decent output
    private final Collection<GradingSuite> allSuites = new TreeSet<>();

    private Description currentSuiteDescription = null;
    private List<GradingTest> currentSuiteTests = null;
    private List<GradingProblem> currentProblems = null;


    @Override
    public void testRunStarted(final Description description) {
        currentSuiteDescription = description;
        currentSuiteTests = new ArrayList<>();
    }

    @Override
    public void testStarted(final Description description) {
        if (description.isTest() && description.getAnnotation(GradedTest.class) != null) {
            currentProblems = new ArrayList<>();
        } else {
            currentProblems = null;
        }
    }

    @Override
    public void testAssumptionFailure(final Failure failure) {
        if (currentProblems != null) {
            currentProblems.add(new GradingProblem("Assumption failed", failure.getException()));
        }
    }

    @Override
    public void testFailure(final Failure failure) {
        if (currentProblems != null) {
            currentProblems.add(new GradingProblem("Test failed", failure.getException()));
        }
    }

    @Override
    public void testFinished(final Description description) {
        if (currentProblems != null) {
            final GradingTest currentTest = parseTest(description, currentProblems);
            currentSuiteTests.add(currentTest);
        }
    }

    @Override
    public void testRunFinished(final Result result) {
        final GradingSuite currentSuite = parseSuite(currentSuiteDescription, currentSuiteTests);
        allSuites.add(currentSuite);

        final StringBuilder csvText = new StringBuilder();
        csvText.append("Practice");
        allSuites.forEach(suite -> csvText.append(suite.toCsvEmptyRow()));
        csvText.append(System.lineSeparator());
        allSuites.forEach(suite -> csvText.append(suite.toCsvSuiteHeader()));
        csvText.append(System.lineSeparator());
        allSuites.forEach(suite -> csvText.append(suite.toCsvRowHeader()));
        csvText.append(System.lineSeparator());
        allSuites.forEach(suite -> csvText.append(suite.toCsvRow()));
        writeToFile(csvText.toString());
    }

    private static GradingSuite parseSuite(final Description description, final List<GradingTest> tests) {
        final GradedCategory annotation = description.getAnnotation(GradedCategory.class);
        if (annotation == null) {
            throw new AssertionError("Category missing an annotation: " + description.getDisplayName());
        }
        return new GradingSuite(annotation.value(), tests);
    }

    private static GradingTest parseTest(final Description description, final List<GradingProblem> problems) {
        final GradedTest annotation = description.getAnnotation(GradedTest.class);
        if (annotation == null) {
            throw new AssertionError("Test missing an annotation: " + description.getDisplayName());
        }
        return new GradingTest(annotation.value(), problems);
    }

    private void writeToFile(final String text) {
        try {
            Files.write(outputPath, text.getBytes(StandardCharsets.UTF_8));
        } catch (final IOException e) {
            throw new AssertionError("IO error while writing to file.", e);
        }
    }
}