
package ch.epfl.sweng.contracts;

import org.junit.Test;
import static org.junit.Assert.*;
import com.google.java.contract.PreconditionError;


public class RecursiveFibonacciTest {

    @Test(expected = PreconditionError.class)
    public void fibOnNegativeFails() {
        RecursiveFibonacci fib = new RecursiveFibonacci();
        fib.apply(-1);
    }

    @Test
    public void fibOf4() {
        RecursiveFibonacci fib = new RecursiveFibonacci();
        assertEquals(3, fib.apply(4));
    }
}
