
package ch.epfl.sweng.contracts;

import org.junit.Test;
import static org.junit.Assert.*;
import com.google.java.contract.PreconditionError;


public class FractionTests {
    @Test(expected = PreconditionError.class)
    public void fractionOnZeroFails() {
        new Fraction(1, 0);
    }

    @Test
    public void divideBy2() {
        assertEquals(2, (new Fraction(4, 2)).toInt());
    }
}
