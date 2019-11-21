package ex2;


import java.util.Date;

/* Example from Refactoring to Patterns */

/**
 * This class represents a Loan in a bank. There are different types of Loans: term loan, revolver loan and RCTL loan.
 * Several constructors are provided to create different loans of different types.
 */
public class Loan{

    CapitalStrategy capitalStrategy;
    double commitment;
    double outstanding;
    Date maturity;
    Date expiry;
    int riskTaking;

    // Constructor for Term loan
    public static Loan createTermLoan(double commitment, int riskTaking, Date maturity){
        return new Loan(null, commitment, 0.00, riskTaking, maturity, null);
    }

    // Constructor for Revolver loan
    public static Loan createRevolverLoan(double commitment, double outstanding, int customerRating, Date maturity, Date expiry){
        return new Loan(null, commitment, outstanding, customerRating, maturity, expiry);
    }

    // Constructor for Revolver loan
    public static Loan createRevolverLoan(double commitment, int customerRating, Date maturity, Date expiry){
        return new Loan(null, commitment, 0.00, customerRating, maturity, expiry);
    }

    // Constructor for RCTL loan
    public static Loan createRCTLLoan(CapitalStrategy capitalStrategy, double commitment, int riskTaking, Date maturity, Date expiry){
        return new Loan(capitalStrategy, commitment, 0.00, riskTaking, maturity, expiry);
    }

    private Loan(CapitalStrategy capitalStrategy, double commitment, double outstanding, int riskTaking, Date maturity, Date expiry){
        this.commitment = commitment;
        this.outstanding = outstanding;
        this.riskTaking = riskTaking;
        this.maturity = maturity;
        this.expiry = expiry;
        this.capitalStrategy = capitalStrategy;

        if (capitalStrategy == null){
            if(expiry == null)
                this.capitalStrategy = new CapitalStrategyTermLoan();
            else if (maturity == null)
                this.capitalStrategy = new CapitalStrategyRevolver();
            else
                this.capitalStrategy = new CapitalStrategyRCTL();
        }
    }
}

// The following classes are for illustration purpose of this exercise only. Don't do this.
class CapitalStrategy {

}

class CapitalStrategyTermLoan extends CapitalStrategy{

}

class CapitalStrategyRevolver extends CapitalStrategy{

}

class CapitalStrategyRCTL extends CapitalStrategy{

}