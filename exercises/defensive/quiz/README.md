#  Quiz

## Defensive Programming

### Question 1

One class method receives garbage input. What is the worst that the method can do?

- [ ] Compute on the garbage and return garbage
- [ ] Compute on the garbage and throw an error, crashing the entire program
- [ ] Immediately throw an exception and stop the execution of the program
- [ ] Compute on the garbage and modify program state in some unspecified way 
- [ ] Sanitize the garbage and pass the sanitized input to another method
- [ ] Ignore the input and not execute
- [ ] Attempt to compute on the garbage and throw a confusing exception

### Question 2

For which of the following exceptional conditions is a RuntimeException more suitable than a checked exception? (Check all that apply)

- [ ] A division by zero occurs inside some matrix processing code 
- [ ] A TCP connection is interrupted while fetching a URL
- [ ] A negative number is passed to a natural logarithm function 
- [ ] A null object is passed to a sorting function 

### Question 3

Which of the following are true? Choose all that apply.

- [ ] It is a good practice to use exceptions for control flow transfer if we want the execution to reach the lower levels of the program stack quickly
- [ ] It is a good practice to recover and continue program execution after a failed assertion
- [ ] Defensive programming makes explicit what assumptions the code makes about program inputs 
- [ ] Exceptions are essentially assertions with a message

### Question 4

Which of the following is good defensive programming for a public method?

- [ ] an assertion that checks if all the method's preconditions are satisfied
- [ ] a series of checks that ensure all of the method's preconditions are satisfied and throw an AssertionError if not
- [ ] a series of checks that ensure all of the method's preconditions are satisfied and throw an exception if not 

## Software testing

### Question 1

Testing of a real-world program helps to:

- [ ] Reduce its risk of failure 
- [ ] Eliminate all its bugs
- [ ] Decrease bug density 
- [ ] Prove its correctness

### Question 2

Which of the following is a valid illustration of the bug → error → failure sequence of events? (Check all that apply)

- [ ] A "for" loop was written such that it mistakenly omits incrementing its counter → the loop spins indefinitely → the program hangs 
- [ ] A client sends an invalid HTTP request → the server returns a message with a 400 Bad Request header → the client crashes
- [ ] A server does not sanitize the input received from a client → the SQL statement executed as a result is altered → all server data is lost or corrupted 
- [ ] A USB device is connected to a computer → the device is incompatible with the operating system → the operating system displays a "device unknown" message

## Test-driven development

### Question 1

TDD is mostly useful for (check all that apply):

- [ ] testing program logic 
- [ ] testing that a user interface is user-friendly
- [ ] testing individual classes and methods 
- [ ] testing large code bases of legacy code

### Question 2

What are the problems of TDD raised in the discussion between Jim Coplien and Uncle Bob?

- [ ] It increases the total number of bugs in the code 
- [ ] It is not accepted by management
- [ ] It causes developers to not think enough about the architecture of their system 
- [ ] TDD is called "unprofessional"
- [ ] TDD leads to bad GUI design 

### Question 4

Which of the following is (are) true regarding the debate between Jim Coplien and Uncle Bob? (Select all that apply)

- [ ] They both think that a modern software engineer must adopt TDD
- [ ] Coplien favors CDD over TDD 
- [ ] Uncle Bob thinks that TDD's "write tests and then write code" cycles need to be short 
- [ ] They both believe that a software system's architecture emerges naturally from TDD

