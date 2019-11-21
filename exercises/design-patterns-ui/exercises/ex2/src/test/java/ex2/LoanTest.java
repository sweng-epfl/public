package ex2;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoanTest {

    @Test
    public void testTermLoanNoPayments(){
        Loan termLoan = new Loan(2.0,1,Date.from(Instant.now()));
        assertNotNull(termLoan);
    }

    @Test
    public void testRevolverLoan() {
        Loan revolverLoan = new Loan(2.0,1.0,1,Date.from(Instant.now()), Date.from(Instant.now()));
        assertNotNull(revolverLoan);
    }
}