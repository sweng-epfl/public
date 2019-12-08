package ex2;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoanTest {

    @Test
    public void testTermLoanNoPayments() {
        Loan termLoan = Loan.createTermLoan(2.0, 1, Date.from(Instant.now()));
        assertNotNull(termLoan);
        System.out.println("Test term loan");
    }

    @Test
    public void testRevolverLoan() {
        Loan revolverLoan = Loan.createRevolverLoan(2.0, 1.0, 1, Date.from(Instant.now()), Date.from(Instant.now()));
        assertNotNull(revolverLoan);
        System.out.println("Test revolver loan");
    }
}
