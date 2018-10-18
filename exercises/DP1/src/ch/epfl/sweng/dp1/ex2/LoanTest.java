package ch.epfl.sweng.dp1.ex2;

import java.time.Instant;
import java.util.Date;
import static org.junit.Assert.*;

public class LoanTest {
    public void testTermLoanNoPayments(double commitment, int riskTaking, Date maturity){
        Loan termLoan = new Loan(commitment, riskTaking, maturity);
        assertNotNull(termLoan);
    }

    public void testRevolverLoan(double commitment, double outstanding, int customerRating, Date maturity, Date expiry) {
        Loan revolverLoan = new Loan(commitment, outstanding, customerRating, maturity, expiry);
        assertNotNull(revolverLoan);
    }

    public static void main(String[] args) {
        LoanTest test= new LoanTest();
        test.testTermLoanNoPayments(2.0,1,Date.from(Instant.now()));
        test.testRevolverLoan(2.0,1.0,1,Date.from(Instant.now()), Date.from(Instant.now()));
    }
}