import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for the Joke.java tests.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You MUST use this file to test Joke.java
 * You CAN add new constructors, methods, and nested classes to this class.
 * You MUST NOT rename this file.
 * You MUST NOT delete this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class JokeTests {
    private static final JokeType VALID_JOKE_TYPE = JokeType.Random;
    private static final String VALID_JOKE_CONTENT = "Funny Joke!";


    @Test
    public void throwsExceptionWhenContentNull() {
        assertThrows(IllegalArgumentException.class, () -> new Joke(null, VALID_JOKE_TYPE));
    }

    @Test
    public void throwsExceptionWhenContentBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Joke("", VALID_JOKE_TYPE));
    }

    @Test
    public void throwsExceptionWhenContentManyLines() {
        String manyLinesAndBlankLines = "A\nB\n\nC";
        assertThrows(IllegalArgumentException.class, () -> new Joke(manyLinesAndBlankLines, VALID_JOKE_TYPE));
    }

    @Test
    public void throwsExceptionWhenTypeContentLengthy() {
        String manyWords = "1 2 3 4 5 6";
        assertThrows(IllegalArgumentException.class, () -> new Joke(manyWords, VALID_JOKE_TYPE));
    }

    @Test
    public void throwsExceptionWhenTypeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Joke(VALID_JOKE_CONTENT, null));
    }

    @Test
    public void throwsNoException() {
        assertDoesNotThrow(() -> new Joke(VALID_JOKE_CONTENT, VALID_JOKE_TYPE));
    }

    @Test
    public void adaptReplacesRightWords() {
        Joke joke = new Joke(VALID_JOKE_CONTENT, VALID_JOKE_TYPE);
        Map<String, String> replace = new HashMap<>();
        replace.put("Funny", "Not Funny");
        assertEquals(VALID_JOKE_CONTENT.replace("Funny", "Not Funny"), joke.adapt(replace).getContent());
    }

    @Test
    public void oneLinerJokesHasNoLines() {
        Joke oneLineJoke = new Joke("Funny\n", JokeType.OneLiner);
        assertTrue(!oneLineJoke.toString().contains("\n"));
    }

    @Test
    public void ObservationalJokesHasPrefix() {
        Joke observational = new Joke("Funny", JokeType.Observational);
        assertTrue(observational.toString().startsWith("Have you ever noticed, "));
    }

    @Test
    public void randomJokeHasNoDiff() {
        Joke random = new Joke("Funny", JokeType.Random);
        assertEquals(random.getContent(), random.toString());
    }
}
