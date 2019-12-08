package ex4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StoreTest {

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
        KeyValueStore store = null;
        // TODO: store = ?

        store.put(3, 1990);
        store.put(7, 2303);
        assertEquals(1, store.remove(7));
        assertEquals(0, store.remove(13));
        assertEquals(Integer.valueOf(1990), store.get(3));
        assertEquals(null, store.get(7));

        store.put(9, 929233);
        assertEquals(Integer.valueOf(929233), store.get(9));

        String expectedMessage =
                "This is a DatabaseIml insert." +
                "This is a DatabaseIml insert." +
                "This is a DatabaseIml delete." +
                "This is a DatabaseIml delete." +
                "This is a DatabaseIml retrieve." +
                "This is a DatabaseIml retrieve." +
                "This is a DatabaseIml insert." +
                "This is a DatabaseIml retrieve.";


        // small hack (from https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println)
        // to test what the getStatistics call printed on System.out
        assertEquals(expectedMessage, outContent.toString());
    }
}
