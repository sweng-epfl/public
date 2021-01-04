package homework03;

import grading.GradedCategory;
import grading.GradedTest;
import homework03.mocks.TestClientImpl;
import homework03.mocks.TestDatabaseImpl;
import homework03.mocks.TestServerImpl;
import homework03.util.AlreadyBookedException;
import homework03.util.DayFullException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.function.Consumer;

import static homework03.utils.AssertUtils.assertContainsSubstringsIgnoringCase;
import static homework03.utils.AssertUtils.assertHandlesNullArgument;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@GradedCategory("Part 2.4: Implementing the CLI")
public class CLIImplTest {

    private static final String SCIPER = "263250";

    private void executeOnCLI(String command, Consumer<TestClientImpl> onClientCreated, Consumer<String[]> onOutput, Consumer<Exception> onError) {
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;

        String input = command + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        TestClientImpl client = new TestClientImpl(SCIPER, new TestServerImpl(new TestDatabaseImpl()));
        onClientCreated.accept(client);

        try {
            CLIImpl cli = new CLIImpl(client);
            cli.getQueryAndExecute();
        } catch (Exception e) {
            onError.accept(e);
        }

        System.setIn(oldIn);
        System.setOut(oldOut);

        String[] outputLines = outputStream.toString().split("\\r?\\n");
        onOutput.accept(outputLines);
    }

    @GradedTest("`constructor` should throw an IllegalArgumentException or a NullPointerException if passed a null client")
    public void constructorThrowOnNullClient() {
        assertHandlesNullArgument(() -> new CLIImpl(null));
    }

    @GradedTest("`getQueryAndExecute` should print a message before the user can enter its query")
    public void test02_getQueryAndExecuteFirstPrintQueryMessage() {
        executeOnCLI("", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            String prompt = lines[0];
            assertContainsSubstringsIgnoringCase(
                    prompt,
                    "Please enter your query:",
                    "Please", "enter", "query");

        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output a success message when a booking is successfully performed")
    public void test03_getQueryAndExecuteWithBookingCommandSuccess() {
        String day = "01.01.2020";
        executeOnCLI(String.format("book %s", day), c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(
                    completeOutput,
                    String.format("Your booking for %s was successful.", day),
                    "booking", day, "successful");

        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output an error message when a booking cannot be performed because of a full day")
    public void test04_getQueryAndExecuteWithBookingCommandErrorFull() {
        String day = "01.01.2020";
        executeOnCLI(String.format("book %s", day), c -> c.setBookImpl((d, s, e) ->
                e.accept(new DayFullException("day full"))), lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(
                    completeOutput,
                    String.format("Your booking for %s was unsuccessful. All the available slots are occupied.", day),
                    "booking", day, "unsuccessful", "slots", "occupied"
            );

        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output an error message when a booking cannot be performed because the day was already booked")
    public void test05_getQueryAndExecuteWithBookingCommandErrorAlreadyBooked() {
        String day = "01.01.2020";
        executeOnCLI(String.format("book %s", day), c -> c.setBookImpl((d, s, e) ->
                e.accept(new AlreadyBookedException("already booked"))), lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    String.format("Your booking for %s was unsuccessful. You already booked this date.", day),
                    "booking", day, "unsuccessful", "already booked");
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output an error message when the date format is invalid 1")
    public void test06_1_getQueryAndExecuteWithBookingCommandInvalidDate() {
        executeOnCLI("book 01:01:2020", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Your date has the wrong format or represents a date that does not exist. The correct format is 'dd.MM.yyyy'.",
                    "date", "wrong format", "does not exist", "correct format", "dd.MM.yyyy");

        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output an error message when the date format is invalid 2")
    public void test06_2_getQueryAndExecuteWithBookingCommandInvalidDate(){
        executeOnCLI("book 31.01.20", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Your date has the wrong format or represents a date that does not exist. The correct format is 'dd.MM.yyyy'.",
                    "date", "wrong format", "does not exist", "correct format", "dd.MM.yyyy");

        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output an error message when the date format is invalid 3")
    public void test06_3_getQueryAndExecuteWithBookingCommandInvalidDate(){
        executeOnCLI("book aa.bb.cccc", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Your date has the wrong format or represents a date that does not exist. The correct format is 'dd.MM.yyyy'.",
                    "date", "wrong format", "does not exist", "correct format", "dd.MM.yyyy");

        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output an error message when the date does not exist 1")
    public void test07_1_getQueryAndExecuteWithBookingCommandInvalidDate() {
        executeOnCLI("book 00.01.2020", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Your date has the wrong format or represents a date that does not exist. The correct format is 'dd.MM.yyyy'.",
                    "date", "wrong format", "does not exist", "correct format", "dd.MM.yyyy");

        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output an error message when the date does not exist 2")
    public void test07_2_getQueryAndExecuteWithBookingCommandInvalidDate() {
        executeOnCLI("book 01.13.2020", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Your date has the wrong format or represents a date that does not exist. The correct format is 'dd.MM.yyyy'.",
                    "date", "wrong format", "does not exist", "correct format", "dd.MM.yyyy");
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output an error message when the date does not exist 3")
    public void test07_3_getQueryAndExecuteWithBookingCommandInvalidDate() {
        executeOnCLI("book 32.01.2020", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Your date has the wrong format or represents a date that does not exist. The correct format is 'dd.MM.yyyy'.",
                    "date", "wrong format", "does not exist", "correct format", "dd.MM.yyyy");
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output an error message when the date does not exist 4")
    public void test07_4_getQueryAndExecuteWithBookingCommandInvalidDate() {
        executeOnCLI("book 31.02.2020", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Your date has the wrong format or represents a date that does not exist. The correct format is 'dd.MM.yyyy'.",
                    "date", "wrong format", "does not exist", "correct format", "dd.MM.yyyy");
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output an error message when a booking cannot be performed because of an error")
    public void test08_getQueryAndExecuteWithBookingCommandError() {
        executeOnCLI("book 01.01.2020", c -> c.setBookImpl((d, s, e) ->
                e.accept(new IllegalArgumentException())), lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "An error occurred, please try again later.",
                    "error", "try", "again");
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output the list of bookings when the bookings command is used")
    public void test09_getQueryAndExecuteWithGetBookingsCommand() {
        String day1 = "01.01.2020";
        String day2 = "02.01.2020";

        executeOnCLI("getBookings", c -> {
            c.book(day1, s -> {
            }, e -> {
            });
            c.book(day2, s -> {
            }, e -> {
            });
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Here are your current booking(s):",
                    "current booking");

            assertContainsSubstringsIgnoringCase(completeOutput,
                    "\t" + day1,
                    day1
            );
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "\t" + day2,
                    day2
            );
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output the empty list when the bookings command is used and no booking exist")
    public void test10_getQueryAndExecuteWithGetBookingsCommandEmpty() {
        executeOnCLI("getBookings", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Here are your current booking(s):",
                    "current booking");
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output an error message when the booking command fails")
    public void test11_getQueryAndExecuteWithGetBookingsCommandError() {
        executeOnCLI("getBookings", c -> c.setGetBookingsImpl((s, e) -> e.accept(new IllegalArgumentException())), lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "An error occurred, please try again later.",
                    "error occurred", "try again");
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output that the query does not conform the syntax when the command is the empty string")
    public void test12_getQueryAndExecuteWithInvalidCommandEmpty() {
        executeOnCLI("", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Your query does not conform to the syntax.",
                    "query", "does not conform", "syntax");
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output that the query does not conform the syntax when the command is a wrong command name")
    public void test13_getQueryAndExecuteWithInvalidCommandWrongName() {
        executeOnCLI("test", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Your query does not conform to the syntax.",
                    "query", "does not conform", "syntax");
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output that the query does not conform the syntax when the command is `getBookings` and that there are arguments")
    public void test14_getQueryAndExecuteWithInvalidCommandGetBookingsArgs() {
        executeOnCLI("getBookings foo", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Your query does not conform to the syntax.",
                    "query", "does not conform", "syntax");
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output that the query does not conform the syntax when the command is `book` and that there are more than one arguments")
    public void test15_getQueryAndExecuteWithInvalidCommandBookArgs() {
        executeOnCLI("book 21.12.2020 foo", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Your query does not conform to the syntax.",
                    "query", "does not conform", "syntax");
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }

    @GradedTest("`getQueryAndExecute` should output that the query does not conform the syntax when the command is `book` and that there are no argument")
    public void test16_getQueryAndExecuteWithInvalidCommandBookNoArgs() {
        executeOnCLI("book", c -> {
        }, lines -> {
            assertTrue(lines.length > 0, "The CLI did not output any message");
            if(lines[0].contains("query") && lines[0].contains("enter")) {
                lines = Arrays.copyOfRange(lines, 1, lines.length);
            }
            String completeOutput = String.join("\n", lines);
            assertContainsSubstringsIgnoringCase(completeOutput,
                    "Your query does not conform to the syntax.",
                    "query", "does not conform", "syntax");
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
    }
}
