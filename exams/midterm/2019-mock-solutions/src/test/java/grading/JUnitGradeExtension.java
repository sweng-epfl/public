package grading;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * JUnit extension that outputs a grade log.
 * ---
 * NOTE: We don't know how many test suites there are, so we write all known suites to the file at the end of each suite.
 */
final class JUnitGradeExtension implements BeforeAllCallback, AfterAllCallback, AfterEachCallback {
    private static final String CSV_LINE_SEPARATOR = "\r\n";
    private static final Path outputPath = Paths.get("practice.csv");
    // Keep the suites sorted for decent output
    private static final Collection<GradingSuite> allSuites = new TreeSet<>();

    private String currentSuiteDescription = null;
    private List<GradingTest> currentSuiteTests = null;


    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        GradedCategory category = context.getRequiredTestClass().getAnnotation(GradedCategory.class);
        if (category == null) {
            return;
        }

        currentSuiteDescription = category.value();
        currentSuiteTests = new ArrayList<>();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        if (currentSuiteDescription == null) {
            return;
        }

        GradedTest annotation = context.getRequiredTestMethod().getAnnotation(GradedTest.class);
        if (annotation == null) {
            return;
        }

        currentSuiteTests.add(new GradingTest(annotation.value(), context.getExecutionException().orElse(null)));
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        if (currentSuiteDescription == null) {
            return;
        }

        allSuites.add(new GradingSuite(currentSuiteDescription, currentSuiteTests));

        final StringBuilder csvText = new StringBuilder();
        csvText.append("Practice");
        allSuites.forEach(suite -> csvText.append(suite.toCsvEmptyRow()));
        csvText.append(CSV_LINE_SEPARATOR);
        allSuites.forEach(suite -> csvText.append(suite.toCsvSuiteHeader()));
        csvText.append(CSV_LINE_SEPARATOR);
        allSuites.forEach(suite -> csvText.append(suite.toCsvRowHeader()));
        csvText.append(CSV_LINE_SEPARATOR);
        allSuites.forEach(suite -> csvText.append(suite.toCsvRow()));
        writeToFile(csvText.toString());
    }

    private void writeToFile(final String text) {
        try {
            Files.write(outputPath, text.getBytes(StandardCharsets.UTF_8));
        } catch (final IOException e) {
            throw new AssertionError("IO error while writing to file.", e);
        }
    }
}
