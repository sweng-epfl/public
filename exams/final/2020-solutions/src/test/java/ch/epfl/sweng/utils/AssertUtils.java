package ch.epfl.sweng.utils;

import static org.junit.jupiter.api.Assertions.fail;

public final class AssertUtils {

    public static void assertHandlesNullArgument(Runnable function) {
        try {
            function.run();
        } catch (IllegalArgumentException | NullPointerException e) {
            return;
        }
        fail("No exception of type IllegalArgumentException or NullPointerException was thrown");
    }

    public static void assertStringContains(String actual, String... substrings) {
        for(String substring : substrings) {
            if(!actual.toLowerCase().contains(substring.toLowerCase())) {
                String message =
                        "String contain assertion failed:\n" +
                        "\t expected to contain: " + substring + "\n" +
                        "\t but was: " + actual;
                fail(message);
            }
        }
    }
}
