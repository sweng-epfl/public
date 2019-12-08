# Exercise 2
Your pizza shop went bankrupt because nobody wanted to buy pizza from a computer science graduate. You are now working in the IT department of a bank. 
Your first assignment in your new job is to refactor some legacy code.

Your bank allows different types of `Loan` which are term, revolver and RCTL loans. A term loan is a loan that must be fully paid by its maturity date. A revolver, which is like a credit card, is a loan that signifies revolving credit: you have a spending limit and an expiry date. A revolving credit term loan (RCTL) is a revolver that transform into a term loan when the revolver expires.
 
The code you inherit that can create different types of `Loan` is `Loan.java`. As a newcomer to banking business and without specialized knowledge, you have no idea which constructors to use to create a term loan, or a revolver loan. You then ask the author of this code (who is now the CTO of the bank) what the differences between these constructors are. What you learn is that the first constructor is for the term loans, the second and third for revolver loans and the fourth for the RCTL loan.  
 
 ```java
    // Constructor for Term loan
    public Loan(double commitment, int riskTaking, Date maturity){
        this(commitment, 0.00, riskTaking, maturity, null);
    }

    // Constructor for Revolver loan
    public Loan(double commitment, int riskTaking, Date maturity, Date expiry){
        this(commitment, 0.00, riskTaking, maturity, expiry);
    }

    // Constructor for Revolver loan
    public Loan(double commitment, double outstanding, int customerRating, Date maturity, Date expiry){
        this(null, commitment, outstanding, customerRating, maturity, expiry);
    }

    // Constructor for RCTL Loan
    public Loan(CapitalStrategy capitalStrategy, double commitment, int riskTaking, Date maturity, Date expiry){
        this(capitalStrategy, commitment, 0.00, riskTaking, maturity, expiry);
    }
```

As you can see, a term loan is a revolver loan with no expiry date. In the first constructor, its expiry date is set to `null`. Similarly, a revolver loan is an RCTL loan with its `capitalStrategy` set to `null`. Without the comments and specialized knowledge, the next programmer would have a hard time to figure out how to use these constructors.

Deciding to save time for the next programmer, you decide to change this code to make it more intuitive. How can you make this code more elegant using a factory design pattern? Is the Static Factory Method or the GOF Factory Method (i.e., virtual constructor) better suited? Rewrite the code to use the chosen design pattern. 
