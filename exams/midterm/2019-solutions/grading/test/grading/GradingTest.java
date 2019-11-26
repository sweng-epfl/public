package grading;

final class GradingTest {
    // As soon as a stack trace goes into one of these, the current element and all following ones are ignored
    private static final String[] EXCEPTION_IGNORED_PACKAGES = {"java.lang.reflect", "org.gradle", "org.junit", "sun", "java.base/"};

    private final String name;
    private final Throwable problem; // null iff the test was successful

    GradingTest(final String name, final Throwable problem) {
        this.name = name;
        this.problem = problem;
    }

    String name() {
        return name;
    }

    String toCsvValue() {
        if (problem == null) {
            return "OK";
        } else {
            return StringUtils.escapeCsv(StringUtils.makeMarkdownParagraph(printException(problem)));
        }
    }

    private static String printException(final Throwable exception) {
        // If it's a test failure or a timeout, the stack trace is worthless
        if (exception instanceof AssertionError) {
            return exception.getMessage().trim();
        }

        // Same if it's an exception to say that the wrong kind of exception was thrown
        if (exception.getMessage() != null && exception.getMessage().startsWith("Unexpected exception")) {
            return exception.getMessage().trim();
        }

        final StringBuilder result = new StringBuilder();
        outer:
        for (final StackTraceElement element : exception.getStackTrace()) {
            final String elementString = element.toString().trim();
            for (final String packageName : EXCEPTION_IGNORED_PACKAGES) {
                if (elementString.startsWith(packageName)) {
                    break outer;
                }
            }
            result.append("at ");
            result.append(elementString);
            result.append(System.lineSeparator());
        }

        return "[" + exception.getClass().getName() + "] "
                + (exception.getMessage() == null ? "" : exception.getMessage().trim())
                + System.lineSeparator()
                + result.toString();
    }
}
