# Solution - Quiz

##  Defensive programming

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

- [ ] It is a good practice to use exceptions for control flow transfer if we want the execution to reach the lower levels of the program stack quickly
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

## Software testing

### Question 1

Testing of a real-world program helps to:

- [x] Reduce its risk of failure 
- [ ] Eliminate all its bugs
- [x] Decrease bug density 
- [ ] Prove its correctness

> The number of paths in a program is exponential in the size of the program, and so it is impractical to eliminate all bugs from (and thus prove the correctness of) a real-world program with traditional testing.

### Question 2

Which of the following is a valid illustration of the bug → error → failure sequence of events? (Check all that apply)

- [x] A "for" loop was written such that it mistakenly omits incrementing its counter → the loop spins indefinitely → the program hangs 
- [ ] A client sends an invalid HTTP request → the server returns a message with a 400 Bad Request header → the client crashes
- [x] A server does not sanitize the input received from a client → the SQL statement executed as a result is altered → all server data is lost or corrupted 
- [ ] A USB device is connected to a computer → the device is incompatible with the operating system → the operating system displays a "device unknown" message

> The second and fourth options are invalid.
      The client crashing after receiving a 400 Bad Request message is indeed a failure; however, the bug that triggers it is not the invalid HTTP request, but rather poor handling of a 400 error message received from the server. Thus, the second example actually represents two different bugs: the client sending an invalid HTTP request and the client crashing when receiving 400 Bad Request from the server.
      The fourth option is not necessarily a bug, it's more an absence of a feature. The OS has a set of devices it supports. If a device is not supported, the OS correctly displays the "device unknown" message. This is a correct way of handling unexpected/unknown devices. If the device were supported, the fourth option would still not be a correct illustration: the fact that the USB device is inserted is not a bug; the fact that the OS code does not correctly identify the device is a bug.

## Test-driven development

### Question 1

TDD is mostly useful for (check all that apply):

- [x] testing program logic 
- [ ] testing that a user interface is user-friendly
- [x] testing individual classes and methods 
- [ ] testing large code bases of legacy code

> TDD is meant for testing the logic of programs.  It is hard to express with a test what user friendliness means. TDD is not useful for testing large pieces of existing code, but rather for encoding and checking the requirements of a module before actually implementing it.

### Question 2

What are the problems of TDD raised in the discussion between Jim Coplien and Uncle Bob?

- [x] It increases the total number of bugs in the code 
- [ ] It is not accepted by management
- [x] It causes developers to not think enough about the architecture of their system 
- [ ] TDD is called "unprofessional"
- [x] TDD leads to bad GUI design 

> Because tests can also contain bugs, the total number of bugs in a system might increase as tests are added. This allegedly happened for the Algol compiler.
      Coplien also says that developers focusing on tests do not produce clean architectures and GUIs.

### Question 3

Which of the following is (are) true regarding the debate between Jim Coplien and Uncle Bob? (Select all that apply)

- [ ] They both think that a modern software engineer must adopt TDD
- [x] Coplien favors CDD over TDD 
- [x] Uncle Bob thinks that TDD's "write tests and then write code" cycles need to be short 
- [ ] They both believe that a software system's architecture emerges naturally from TDD

> Coplien does not think that TDD adoption is mandatory. They both think that coming up with sound software architecture requires more than employing TDD.

