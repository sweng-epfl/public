package ex2;

/* Example from Refactoring to Patterns */

import java.util.Date;

/**
 * This class represents a Loan in a bank. There are different types of Loans: term loan, revolver loan and RCTL loan.
 * Several constructors are provided to create different loans of different types.
 */
public class Loan{

    CapitalStrategy capitalStrategy;
    double commitment;
    double outstanding;
    int customerRating;
    Date maturity;
    Date expiry;
    int riskTaking;

    public Loan(double commitment, int riskTaking, Date maturity){
        this(commitment, 0.00, riskTaking, maturity, null);
    }

    public Loan(double commitment, int riskTaking, Date maturity, Date expiry){
        this(commitment, 0.00, riskTaking, maturity, expiry);
    }

    public Loan(double commitment, double outstanding, int customerRating, Date maturity, Date expiry){
        this(null, commitment, outstanding, customerRating, maturity, expiry);
    }

    public Loan(CapitalStrategy capitalStrategy, double commitment, int riskTaking, Date maturity, Date expiry){
        this(capitalStrategy, commitment, 0.00, riskTaking, maturity, expiry);
    }

    public Loan(CapitalStrategy capitalStrategy, double commitment, double outstanding, int riskTaking, Date maturity, Date expiry){
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

