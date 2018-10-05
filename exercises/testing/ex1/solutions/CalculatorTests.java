import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

public class CalculatorTests {
    private final Calculator calc = new Calculator();

    @Test
    public void addOneAndOneIsTwo() {
        assertThat(calc.add(1, 1), is(2L));
    }

    @Test
    public void addTenAndZeroIsTen() {
        assertThat(calc.add(10, 0), is(10L));
    }

    @Test
    public void addMinusTenAndTenIsZero(){
        assertThat(calc.add(-10,10),is(0L));
    }

    // This test checks for overflows
    @Test
    public void addLargeNumbers() {
        assertThat(calc.add(Integer.MAX_VALUE, Integer.MAX_VALUE), is(4294967294L));
    }

    @Test
    public void divideTwoByOneIsTwo() {
        assertThat(calc.divide(2, 1), is(2));
    }

    @Test
    public void divideTenByThreeRoundsDown() {
        assertThat(calc.divide(10, 3), is(3));
    }

    @Test
    public void divideMinusSixByTwoIsMinusThree(){
        assertThat(calc.divide(-6,2),is(-3));
    }

    @Test
    public void divideByZeroThrows() {
        try {
            calc.divide(1, 0);
            fail("Divide by zero should have thrown.");
        } catch (ArithmeticException e) {
            // OK.
        }
    }
}
