package grading;

import org.junit.jupiter.api.extension.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * JUnit extension that outputs a grade log.
 * <p>
 * If the environment variable SKIP_TESTS is set, all tests are skipped and the grade log contains the className.methodName of each test instead of its result.
 * ---
 * NOTE: We don't know how many test suites there are, so we write all known suites to the file at the end of each suite.
 */
final class JUnitGradeExtension implements BeforeAllCallback, ExecutionCondition, AfterAllCallback, AfterEachCallback {
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
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        if (System.getenv("SKIP_TESTS") != null) {
            if (context.getTestMethod().isPresent()) {
                addTestToCurrentSuite(context, true);
                return ConditionEvaluationResult.disabled("Skipped");
            }
        }
        return ConditionEvaluationResult.enabled("OK");
    }

    @Override
    public void afterEach(ExtensionContext context) {
        addTestToCurrentSuite(context, false);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        if (currentSuiteDescription == null || currentSuiteTests.isEmpty()) {
            return;
        }

        allSuites.add(new GradingSuite(currentSuiteDescription, currentSuiteTests));

        final StringBuilder csvText = new StringBuilder();
        csvText.append(allSuites.stream().map(GradingSuite::toCsvSuiteHeader).collect(Collectors.joining(",")));
        csvText.append(CSV_LINE_SEPARATOR);
        csvText.append(allSuites.stream().map(GradingSuite::toCsvRowHeader).collect(Collectors.joining(",")));
        csvText.append(CSV_LINE_SEPARATOR);
        csvText.append(allSuites.stream().map(GradingSuite::toCsvRow).collect(Collectors.joining(",")));
        writeToFile(csvText.toString());
    }

    private void addTestToCurrentSuite(ExtensionContext context, boolean skip) {
        if (currentSuiteDescription == null) {
            return;
        }

        GradedTest annotation = context.getRequiredTestMethod().getAnnotation(GradedTest.class);
        if (annotation == null) {
            return;
        }

        String name = annotation.value();
        if (skip) {
            currentSuiteTests.add(GradingTest.other(name, context.getRequiredTestClass().getName() + "." + context.getRequiredTestMethod().getName()));
        } else {
            currentSuiteTests.add(context.getExecutionException().map(e -> GradingTest.failure(name, e)).orElse(GradingTest.success(name)));
        }
    }

    private void writeToFile(final String text) {
        try {
            Files.write(outputPath, text.getBytes(StandardCharsets.UTF_8));
        } catch (final IOException e) {
            throw new AssertionError("IO error while writing to file.", e);
        }
    }
}
