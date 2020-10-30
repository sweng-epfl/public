# Solution - Defensive Programming Quiz

### Question 1

One class method receives garbage input. What is the worst that the method can do?

- [ ] Compute on the garbage and return garbage
- [ ] Compute on the garbage and throw an error, crashing the entire program
- [ ] Immediately throw an exception and stop the execution of the program
- [x] Compute on the garbage and modify program state in some unspecified way
- [ ] Sanitize the garbage and pass the sanitized input to another method
- [ ] Ignore the input and not execute
- [ ] Attempt to compute on the garbage and throw a confusing exception

> The worst case scenario is when the method modifies some program state; since it's done based on the garbage input, the modification is unspecified, and may corrupt program state in unknown ways. This could lead to an error much later in the exection, when some other piece of code attempts to use the respective state. Or even worse, the corruption is saved to a file which then gets used by some other program. This case is very difficult to debug. See the video at 5:30.

### Question 2

For which of the following exceptional conditions is a RuntimeException more suitable than a checked exception? (Check all that apply)

- [x] A division by zero occurs inside some matrix processing code
- [ ] A TCP connection is interrupted while fetching a URL
- [x] A negative number is passed to a natural logarithm function
- [x] A null object is passed to a sorting function

> The TCP connection can fail regardless of the correctness of the program, so it is an exceptional situation but not one that prevents the program from recovering and continuing execution. It is an environment fact, there is no magic that the code can do to prevent a TCP connection from breaking, so the code must suitably account for this situation.  All the other errors result from bugs in the code, or from failure to sanitize inputs, so they're "really bad" exceptional situations. See video at 11:00.

### Question 3

Which of the following are true? Choose all that apply.

- [ ] It is a good practice to use exceptions for control flow transfer if we want the execution to reach the upper levels of the program stack quickly
- [ ] It is a good practice to recover and continue program execution after a failed assertion
- [x] Defensive programming makes explicit what assumptions the code makes about program inputs
- [ ] Exceptions are essentially assertions with a message

> Defensive programming check inputs for validity, and thereby makes it very clear what a valid input is vs. an invalid input.  Exceptions should not be used for control flow transfer. A program will stop execution after an assertion, so there is no room for recovery.

### Question 4

Which of the following is good defensive programming for a public method?

- [ ] an assertion that checks if all the method's preconditions are satisfied
- [ ] a series of checks that ensure all of the method's preconditions are satisfied and throw an AssertionError if not
- [x] a series of checks that ensure all of the method's preconditions are satisfied and throw an exception if not

> We would not use assertions in this case, because this is a public method that will eventually be called by a client that uses the code. Were the assertion to fail, the program would simply exit immediately after possibly emitting an error message. This is not desirable behaviour for code that is being used by other code. Because this is a public method, it is more suitable to use a series of conditional statements to check if all conditions hold, and if it is not the case, to throw an exception which explains why the input is not valid.
      If the method were private instead of public, assertions would be appropriate, because passing bad inputs from code within the same class hierarchy is a bug.

### Question 5

Security software often choose either _whitelisting_ or _blacklisting_ as the technique to restrict access to operations which require additional user privileges. Read about these techniques and select all statements which are true below when these techniques are applied to defensive programming:

- [x] Blacklisting can only protect against known bad inputs.
- [ ] Anti-virus software is based on whitelisting.
- [ ] Whitelisting is easy to scale to large input patterns.
- [ ] Blacklisting is more secure than whitelisting.

> A blacklist is a list of things you know are bad. In the context of an anti-virus program, bad inputs are malicious programs, and the anti-virus software detects such programs by looking for specific bad patterns that security researchers have identified, i.e. such patterns are blacklisted. Whitelisting does not scale to large input patterns as it would require one to approve all possible valid input patterns (which could be exponential depending on the context).

