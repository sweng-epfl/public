# TDD Part 1

You are a new developer and want to make your own online concert ticket shop. Being mistrustful, you first decide to work on a counterfeit bills detector (see [CounterfeitDetector.java](CounterfeitDetector.java)). You want to accept the following currencies : Swiss Francs, Euros, and US Dollars. Each of them are represented by a class and already available to you (see also the superclass [Currency.java](Currency.java)). You take on the following steps :

1. You first decide to create a method to check that the bill is valid. For simplicity, you can assume that any currency with a positive value constitutes a valid bill.

2. You then want to create a second method to ensure that the bill is not expired, i.e., its expiration date is not due. (Normally currency bills do not expire, but for the sake of this exercise assume that they do have an expiration date.)

3. After gaining some experience, you realize that you can refactor the two previous methods into a single one that checks for both conditions. 

4. You then realize that some additional information must be checked for some of the currencies you accept: Swiss Franc bills are valid only if the issuing authority is "CH" or "SNB", and (say) Euros are valid only if their issue year is older than 2004. You therefore create two new methods that handle those special cases.

Each of these steps should be implemented following the TDD flow, i.e., first writing the (failing) tests, then fixing the compilation errors by writing the code, then modifying your code to make your test pass, and refactor if needed.


