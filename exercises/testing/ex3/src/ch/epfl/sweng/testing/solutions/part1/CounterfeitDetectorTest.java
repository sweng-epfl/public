package ch.epfl.sweng.testing.solutions.part1;

import ch.epfl.sweng.testing.part1.Dollars;
import ch.epfl.sweng.testing.part1.Euros;
import ch.epfl.sweng.testing.part1.Francs;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CounterfeitDetectorTest {
    private CounterfeitDetector detector = new CounterfeitDetector();
    private Dollars currencyValid = new Dollars(10, 2100);
    private Dollars currencyInvalidValue = new Dollars(-5, 1978);

    private Francs francsValid = new Francs(10, 2019, "CH");
    private Francs francsExpired = new Francs(10, 1970, "CH");
    private Francs francsInvalidValue = new Francs(-2, 2019, "SNB");
    private Francs francsInvalidAuthority = new Francs(10, 2019, "FR");

    private Euros eurosValid = new Euros(10, 2019, 2005);
    private Euros eurosExpired = new Euros(10, 1970, 2005);
    private Euros eurosInvalidValue = new Euros(-2, 2019, 2005);
    private Euros eurosInvalidCreation = new Euros(-2, 2019, 200);

    @Test
    public void isValidTrueTest() {
        assertTrue(detector.isValid(currencyValid));
    }

    @Test
    public void isValidFalseTest() {
        assertFalse(detector.isValid(currencyInvalidValue));
    }

    @Test
    public void isFrancsValidTrueTest() {
        assertTrue(detector.isFrancsValid(francsValid));
    }

    @Test
    public void isFrancsValidFalseTest() {
        assertFalse(detector.isFrancsValid(francsExpired));
        assertFalse(detector.isFrancsValid(francsInvalidValue));
        assertFalse(detector.isFrancsValid(francsInvalidAuthority));
    }

    @Test
    public void isEurosValidTrueTest() {
        assertTrue(detector.isEurosValid(eurosValid));
    }

    @Test
    public void isEurosValidFalseTest() {
        assertFalse(detector.isEurosValid(eurosExpired));
        assertFalse(detector.isEurosValid(eurosInvalidValue));
        assertFalse(detector.isEurosValid(eurosInvalidCreation));
    }

}