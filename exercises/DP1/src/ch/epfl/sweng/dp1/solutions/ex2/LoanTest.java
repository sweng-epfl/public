package ch.epfl.sweng.dp1.solutions.ex2;
import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Date;

public class LoanTest {

    private void testTermLoanNoPayments(double commitment, int riskTaking, Date maturity){
        Loan termLoan = Loan.createTermLoan(commitment, riskTaking, maturity);
        assertNotNull(termLoan);
        System.out.println("Test term loan");
    }

    private void testRevolverLoan(double commitment, double outstanding, int customerRating, Date maturity, Date expiry) {
        Loan revolverLoan = Loan.createRevolverLoan(commitment, outstanding, customerRating, maturity, expiry);
        assertNotNull(revolverLoan);
        System.out.println("Test revolver loan");
    }

    public static void main(String[] args) {
        LoanTest test= new LoanTest();
        test.testTermLoanNoPayments(2.0,1,Date.from(Instant.now()));
        test.testRevolverLoan(2.0,1.0,1,Date.from(Instant.now()), Date.from(Instant.now()));
    }
}
