
package ch.epfl.sweng.contracts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.google.java.contract.PreconditionError;


public class RecursiveFibonacciTest {

    @Test
    public void fibOnNegativeFails() {
        RecursiveFibonacci fib = new RecursiveFibonacci();
        assertThrows(PreconditionError.class, () -> fib.apply(-1));
    }

    @Test
    public void fibOf4() {
        RecursiveFibonacci fib = new RecursiveFibonacci();
        assertEquals(3, fib.apply(4));
    }
}
