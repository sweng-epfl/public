package ch.epfl.sweng.grading.models;

import org.junit.runners.model.TestTimedOutException;

import ch.epfl.sweng.grading.utils.StringUtils;

public final class GradingProblem {
    // As soon as a stack trace goes into one of these, the current element and all following ones are ignored
    private static final String[] EXCEPTION_IGNORED_PACKAGES = {"java.lang.reflect", "org.gradle", "org.junit", "sun"};


    public final String kind;
    public final Throwable exception;


    public GradingProblem(final String kind, final Throwable exception) {
        this.kind = kind;
        this.exception = exception;
    }


    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();

        result.append(kind);

        if (exception == null) {
            result.append(": <no details available, sorry>");
        } else {
            result.append(": ");

            String exceptionString = printException(exception);
            // Escape < and > so that Markdown parsers don't think they're HTML tags...
            // TODO better escaping, for every string!
            exceptionString = exceptionString.replace("<", "&lt;").replace(">", "&gt;");
            result.append(StringUtils.suffixLines(exceptionString, "  "));
        }

        return result.toString();
    }


    private static String printException(final Throwable exception) {
        // If it's a test failure or a timeout, the stack trace is worthless
        if (exception instanceof AssertionError || exception instanceof TestTimedOutException) {
            return exception.getMessage().trim();
        }

        // Same if it's an exception to say that the wrong kind of exception was thrown
        if (exception.getMessage() != null && exception.getMessage().startsWith("Unexpected exception")) {
            return exception.getMessage().trim();
        }

        final StringBuilder result = new StringBuilder();
        outer:
        for (final StackTraceElement element : exception.getStackTrace()) {
            for (final String packageName : EXCEPTION_IGNORED_PACKAGES) {
                if (element.getClassName().startsWith(packageName)) {
                    break outer;
                }
            }
            result.append("at ");
            result.append(element.toString().trim());
            result.append(System.lineSeparator());
        }

        return "[" + exception.getClass().getName() + "] "
                + (exception.getMessage() == null ? "" : exception.getMessage().trim())
                + System.lineSeparator()
                + result.toString();
    }
}