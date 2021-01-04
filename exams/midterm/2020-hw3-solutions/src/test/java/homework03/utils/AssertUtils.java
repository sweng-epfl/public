package homework03.utils;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.MatcherAssert.assertThat;
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

    public static void assertCallbackWasCalled(AtomicBoolean flag) {
        assertThat("No callback was called", flag.get());
    }

    public static void assertContainsSubstringsIgnoringCase(String was, String expected, String ... substrings) {
        boolean b = true;
        for (String substring: substrings) {
            b = b && was.toLowerCase().contains(substring.toLowerCase());
        }
        assertThat("Expected: \"" + expected + "\", but was: \"" + was + "\"", b );
    }

}
