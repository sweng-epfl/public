package ex3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PosterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void testFunctionality() {
        FacebookPoster fb = null;
        // TODO: instantiate fb

        fb.postMessage("Hello World!");
        fb.postMessage("Hello world! Still here!");
        assertEquals("Hello world! Still here!", fb.getMessage(1));

        fb.postMessage("Another day, another post!");
        assertEquals("Another day, another post!", fb.getMessage(2));

        String expectedMessage =
                "Using the NewFacebookPoster.post" +
                "Using the NewFacebookPoster.post" +
                "Using the NewFacebookPoster.get" +
                "Using the NewFacebookPoster.post" +
                "Using the NewFacebookPoster.get";

        assertEquals(expectedMessage, outContent.toString());
    }
}
