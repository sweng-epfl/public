# Solution - Quiz

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


