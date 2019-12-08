package ch.epfl.sweng.grading.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public final class StringUtils {
    private StringUtils() {
        // no initialization
    }

    public static int occurrencesOf(final char character, final String text) {
        int result = 0;
        for (int n = 0; n < text.length(); n++) {
            if (text.charAt(n) == character) {
                result++;
            }
        }
        return result;
    }

    public static String suffixLines(final String text, final String suffix) {
        // Weird regex trick: enable multi-line mode, then match the end of lines.
        return text.replaceAll("(?m)$", suffix);
    }

    public static String repeat(final char character, final int count) {
        final StringBuilder result = new StringBuilder();
        for (int n = 0; n < count; n++) {
            result.append(character);
        }
        return result.toString();
    }
}
