package ch.epfl.sweng.graded.grading;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.*;

import ch.epfl.sweng.graded.grading.models.*;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * JUnit listener that outputs a grade sheet.
 * <p>
 * <b>Implementation notes:</b>
 * <p>
 * Since JUnit's RunListener only gives events, we must first build the entire suite in memory,
 * then find the corresponding test each time we get an event and update it.
 * Also, to put the total at the end, we keep the entire text in memory and (over-)write it all at once
 * to the file at the end of every suite, including the total (since we can't know when it's the last suite).
 */
final class JUnitGradeSheetListener extends RunListener {
    private final File outputFile = new File("GradingReport_Practice.");
    // Keep the suites sorted for decent output
    private final Collection<GradingSuite> allSuites = new TreeSet<>();

    private GradingPoints totalPoints = GradingPoints.empty();
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
        if (description.isTest() && isGradable(description)) {
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
        totalPoints = totalPoints.combine(currentSuite.points);

        final StringBuilder resultText = new StringBuilder();
        resultText.append("# Practice: ").append(totalPoints.toString());

        final boolean hasInformationalTests = allSuites.stream().anyMatch(s -> s.tests.stream().anyMatch(t -> t.points.available == 0));
        if (hasInformationalTests) {
            resultText.append(System.lineSeparator())
                    .append("Some tests are without points, we wrote them and ran them simply in order " +
                            "to give you more detailed feedback on your solution without penalizing you for them.");
        }

        resultText.append(System.lineSeparator())
                .append(System.lineSeparator());

        final StringBuilder csvText = new StringBuilder();

        allSuites.stream().sorted().forEach(suite -> resultText.append(suite.toString()));

        allSuites.stream().sorted().forEach(suite -> csvText.append(suite.csvHeader()));
        csvText.append(System.lineSeparator());
        allSuites.stream().sorted().forEach(suite -> csvText.append(suite.toCsv()));
        writeToFile(resultText.toString());
        writeToCsvFile(csvText.toString());
    }

    private static boolean isGradable(final Description test) {
        return test.getAnnotation(GradedTest.class) != null;
    }


    private static GradingSuite parseSuite(final Description description, final List<GradingTest> tests) {
        final GradedCategory annotation = description.getAnnotation(GradedCategory.class);

        final String suiteName =
                annotation.name().equals(GradedTest.DEFAULT_NAME) ?
                        description.getDisplayName() : annotation.name();

        final String suiteDescription =
                annotation.description().equals(GradedTest.DEFAULT_DESCRIPTION) ?
                        null : annotation.description();

        return new GradingSuite(description.getClassName(), suiteName, suiteDescription, tests);
    }

    private static GradingTest parseTest(final Description description, final List<GradingProblem> problems) {
        final GradedTest annotation = description.getAnnotation(GradedTest.class);
        if (annotation == null) {
            throw new AssertionError("Test missing an annotation: " + description.getDisplayName());
        }
        if (annotation.name().equals(GradedTest.DEFAULT_NAME)) {
            throw new IllegalArgumentException("Test without a name: " + description.getDisplayName());
        }

        final String testDescription = annotation.description().equals(GradedTest.DEFAULT_DESCRIPTION) ? null : annotation.description();

        return new GradingTest(annotation.name(), testDescription, annotation.points(), problems);
    }


    private void writeToFile(final String text) {
        try (final FileOutputStream outputStream = new FileOutputStream(outputFile + "md");
             final Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            writer.write(text);
        } catch (final FileNotFoundException e) {
            throw new AssertionError("File not found? How? What kind of weird filesystem are you using?", e);
        } catch (final IOException e) {
            throw new AssertionError("IOException while writing to the file. C'mon, check your permissions...", e);
        }
    }
    private void writeToCsvFile(final String text) {
        try (final FileOutputStream outputStream = new FileOutputStream(outputFile + "csv");
             final Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            writer.write(text);
        } catch (final FileNotFoundException e) {
            throw new AssertionError("File not found? How? What kind of weird filesystem are you using?", e);
        } catch (final IOException e) {
            throw new AssertionError("IOException while writing to the file. C'mon, check your permissions...", e);
        }
    }
}
