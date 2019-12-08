package ch.epfl.sweng.grading;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import ch.epfl.sweng.grading.models.GradingPoints;
import ch.epfl.sweng.grading.models.GradingProblem;
import ch.epfl.sweng.grading.models.GradingSuite;
import ch.epfl.sweng.grading.models.GradingTest;

/**
 * JUnit listener that outputs a grade sheet.
 * <p>
 * <b>Implementation notes:</b>
 * <p>
 * Since JUnit's RunListener only gives events, we must first build the entire suite in memory,
 * then find the corresponding test each time we get an event and update it.
 * Also, to put the total at the end, we keep the entire text in memory and write it all at once
 * at the end of every suite, including the total (since we can't know when it's the last suite).
 */
final class JUnitGradeSheetListener extends RunListener {
    private final File outputFile = new File("GradingReport_Code.md");
    // Keep the suites sorted for decent output
    private final Collection<GradingSuite> allSuites = new TreeSet<>();

    private GradingPoints totalPoints = GradingPoints.Empty(GradingPoints.PointsFormat.DETAILED);
    private GradingSuite currentSuite = null;
    private Map<Description, GradingTest> currentSuiteTests = null;
    private GradingTest currentTest = null;


    @Override
    public void testRunStarted(final Description description) {
        currentSuiteTests = new HashMap<>();
        currentSuite = parseSuite(description);
    }

    @Override
    public void testRunFinished(final Result result) {
        totalPoints = totalPoints.combine(currentSuite.computePoints());
        allSuites.add(currentSuite);

        final StringBuilder resultText = new StringBuilder();
        resultText.append("# Code: ")
                .append(totalPoints.toString())
                .append(System.lineSeparator())
                .append("Many tests are without points, we wrote them and ran them simply in order " +
                        "to give you more detailed feedback on your solution without penalizing you for them.")
                .append(System.lineSeparator())
                .append(System.lineSeparator());

        HashSet<String> groups = allSuites.stream().filter(GradingSuite::hasGroup)
                .map(suite -> suite.group).collect(Collectors.toCollection(HashSet::new));
        // Write out groups.
        for (String group : groups) {
            resultText.append("## ")
                    .append(group)
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
            allSuites.stream().filter(suite -> group.equals(suite.group))
                    .forEach(suite -> resultText.append("##").append(suite.toString()));
        }
        // Write out everything else.
        allSuites.stream().filter(suite -> !suite.hasGroup())
                .forEach(suite -> resultText.append(suite.toString()));
        writeToFile(resultText.toString());
    }


    @Override
    public void testStarted(final Description description) {
        if (description.isTest() && isGradable(description)) {
            currentTest = currentSuiteTests.get(description);
        } else {
            currentTest = null;
        }
    }

    @Override
    public void testAssumptionFailure(final Failure failure) {
        testProblem(failure, "Assumption failed");
    }

    @Override
    public void testFailure(final Failure failure) {
        testProblem(failure, "Test failed");
    }


    private void testProblem(final Failure failure, final String kind) {
        if (failure.getDescription().isTest() && isGradable(failure.getDescription())) {
            currentTest.problems.add(new GradingProblem(kind, failure.getException()));
        }
    }


    private boolean isGradable(final Description test) {
        return test.getAnnotation(GradedTest.class) != null;
    }


    private GradingSuite parseSuite(final Description description) {
        final GradedCategory annotation = description.getAnnotation(GradedCategory.class);

        final String suiteName =
                annotation.name().equals(GradedTest.DEFAULT_NAME) ?
                        description.getDisplayName() : annotation.name();

        final String suiteDescription =
                annotation.description().equals(GradedTest.DEFAULT_DESCRIPTION) ?
                        null : annotation.description();

        final String suiteGroup =
                annotation.group().equals(GradedCategory.DEFAULT_GROUP) ?
                        null : annotation.group();

        final GradingSuite suite = new GradingSuite(description.getClassName(), suiteName,
                suiteDescription, suiteGroup, annotation.pointsFormat());
        for (final Description child : description.getChildren()) {
            if (isGradable(child)) {
                if (child.isTest()) {
                    suite.tests.add(parseTest(child, annotation.pointsFormat()));
                } else {
                    throw new AssertionError("Non-test child. How did you even do that?");
                }
            }
        }

        return suite;
    }

    private GradingTest parseTest(final Description description, GradingPoints.PointsFormat pointsFormat) {
        final GradedTest annotation = description.getAnnotation(GradedTest.class);
        if (annotation == null) {
            throw new AssertionError("Test missing an annotation: " + description.getDisplayName());
        }
        if (annotation.name().equals(GradedTest.DEFAULT_NAME)) {
            throw new IllegalArgumentException("Test without a name: " + description.getDisplayName());
        }

        final String testDescription =
                annotation.description().equals(GradedTest.DEFAULT_DESCRIPTION) ? null : annotation.description();

        final GradingTest test = new GradingTest(annotation.name(), testDescription, annotation.points(), pointsFormat);
        currentSuiteTests.put(description, test);
        return test;
    }


    private void writeToFile(final String text) {
        try (final FileOutputStream outputStream = new FileOutputStream(outputFile);
             final Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
            writer.write(text);
        } catch (final FileNotFoundException e) {
            throw new AssertionError("File not found? How? What kind of weird filesystem are you using?", e);
        } catch (final IOException e) {
            throw new AssertionError("IOException while writing to the file. C'mon, check your permissions...", e);
        }
    }
}
