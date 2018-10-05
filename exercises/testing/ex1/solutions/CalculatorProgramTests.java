import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CalculatorProgramTests {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream bytes;

    @Before
    public void before() {
        bytes = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bytes));
    }

    @After
    public void after() {
        System.setOut(originalOut);
    }

    @Test
    public void addPositiveNumbers() {
        CalculatorProgram.main(new String[]{"1", "+", "2"});
        String result = new String(bytes.toByteArray());
        assertThat(result, is("3"));
    }

    @Test
    public void addNegativeNumbers() {
        CalculatorProgram.main(new String[]{"-1", "+", "-2"});
        String result = new String(bytes.toByteArray());
        assertThat(result, is("-3"));
    }

    @Test
    public void addPositiveAndNegativeNumbers() {
        CalculatorProgram.main(new String[]{"10", "+", "-4"});
        String result = new String(bytes.toByteArray());
        assertThat(result, is("6"));
    }

    @Test
    public void addBigNumbers() {
        CalculatorProgram.main(new String[]{"2147483647", "+", "2147483647"});
        String result = new String(bytes.toByteArray());
        assertThat(result, is("4294967294"));
    }

    @Test
    public void divideTwoByOne() {
        CalculatorProgram.main(new String[]{"2", "/", "1"});
        String result = new String(bytes.toByteArray());
        assertThat(result, is("2"));
    }

    @Test
    public void divideNegativeNumbers() {
        CalculatorProgram.main(new String[]{"-10", "/", "-3"});
        String result = new String(bytes.toByteArray());
        assertThat(result, is("3"));
    }

    @Test
    public void dividePositveByNegativeNumber() {
        CalculatorProgram.main(new String[]{"4", "/", "-2"});
        String result = new String(bytes.toByteArray());
        assertThat(result, is("-2"));
    }

    @Test
    public void divideByZero() {
        CalculatorProgram.main(new String[]{"6", "/", "0"});
        String result = new String(bytes.toByteArray());
        assertThat(result, is("Error!"));
    }

    @Test
    public void tooFewArguments() {
        CalculatorProgram.main(new String[]{"6", "/"});
        String result = new String(bytes.toByteArray());
        assertThat(result, is("Wrong number of arguments."));
    }

    @Test
    public void tooManyArguments() {
        CalculatorProgram.main(new String[]{"6", "/", "2", "10"});
        String result = new String(bytes.toByteArray());
        assertThat(result, is("Wrong number of arguments."));
    }

    @Test
    public void minusOperation() {
        CalculatorProgram.main(new String[]{"6", "-", "2"});
        String result = new String(bytes.toByteArray());
        assertThat(result, is("Unknown operation."));
    }
}
