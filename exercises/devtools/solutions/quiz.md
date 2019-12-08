## The role of software in modern life

Which of the following attributes of a software system are not among the key properties we will concern ourselves with in this course?

- [ ] Safety
- [ ] Security
- [x] Cost 
- [x] Usability 
- [ ] Performance
- [ ] Manageability
- [ ] Reliability

> As mentioned in lecture (see slide 7), we will be focused on five key properties of software: safety, security, reliability, performance, and manageability.

How many lines of code (LOC) are in the software of systems like the Boeing 767 avionics or o modern premium automobile?

- [ ] Thousands
- [ ] Tens of thousands
- [ ] Hundreds of thousands
- [x] Millions 

> The avionics software in the Boeing 767 has 6.5 million LOC, while a modern premium automobile runs on the order of 100 million LOC.

If a software system is deemed to be 100% safe, then is it allowed to ever fail?

- [x] Yes 
- [ ] No

> A safe system is one that, even if unreliable, guarantees that, whenever it fails, the consequences are not catastrophic. In other words, it is allowed to fail, as long as this does not lead to a catastrophy.

A secure system is one that protects user information from... (check all that apply)

- [x] theft 
- [x] unauthorized access 
- [ ] duplication
- [x] tampering 
- [ ] being used in erroneous calculations

> The correct choices are theft, unauthorized access, and tampering. In general, duplication is not related to security, and is often done as part of backup operations or data replication (in fact, duplication is one fundamental approach to detecting and repairing tampering). How the data is used (e.g., in erroneous calculations) is not a security concern - as long as the user is authorized to access the data, he/she can do whatever he/she wants with it.

## The challenge of software complexity

Which statements are correct regarding tests and proofs?

- [x] Tests cannot cover a significant fraction of a large program's behaviors, because each test can only verify one behavior at a time 
- [ ] Testing is slow because the result of each test has to be inspected for correctness
- [x] Proof techniques can verify programs with respect to low-level properties (such as division by zero) 
- [x] Proof techniques can verify higher-level properties (so called functional properties) 

> Roughly speaking, each path through the code corresponds to one possible behavior; one test exercises exactly one path; given that there are exponentially many paths through the code, it's hard to write tests for all of them.

Is it possible to have a program with an infinite number of paths?

- [ ] No, because the number of paths is exponential in the program size, and the program size is always finite
- [ ] No, because a program always terminates eventually
- [x] Yes, if the program has a loop that never ends (e.g., a server listening loop) 
- [ ] Yes, if the program runs on a 64-bit processor.

> A server program that has a listening loop can receive an infinite number of inputs; for each such input, the loop can branch multiple times, therefore creating at least one new path per received input

## Preventing and finding errors early

In practice, which part of the software development process takes the most time?

- [ ] Problem definition and requirements analysis
- [ ] Software architecture and design
- [ ] Coding
- [x] Testing and debugging 
- [ ] Integration and maintenance

> Activities related to testing and debugging account for more than 50% of the software development cycle.

Ideally, which part of the software development should take the least time?

- [ ] Problem definition and requirements analysis
- [ ] Software architecture and design
- [ ] Coding
- [ ] Testing
- [x] Debugging 

> Debugging is merely a consequence of not having written the code correctly; ideally we should not need to spend any time debugging.

## Building Software

Where do you define the expectations a user has from a software project?

- [ ] Problem statement
- [x] Functional specification 
- [ ] Software architecture

> See video at 3:45.

An earlier version of this class had a different type of project. The SwEng homeworks each presented new feature requirements, such that at the end of the semester a team had implemented a complete Android application. Which software building approach would best fit this model?

- [ ] Block approach
- [x] Agile approach 
- [ ] None of the above

> The agile approach supports changing requirements, while the block approach is more suitable to scenarios where the requirements are fixed and well-known ahead of time. See video at 13:00.

## Version Control Systems

Suppose a developer wants to cancel the changes they made to a piece of code and revert to a previous version. However, the developer is on a train, without access to the internet. This can be done in:

- [ ] First generation version control systems, or later
- [ ] Second generation version control systems, or later
- [x] Third generation version control systems 
- [ ] No current version control system supports this scenario

> Third generation version control systems, such as Git, are distributed; each node has the entire version history locally. Thus, Git allows reverting to previous versions without network communication.

Which VCS does not require merging modifications?

- [x] Locking VCS (first generation) 
- [ ] Decentralized VCS(third generation)
- [ ] Centralized VCS(second generation)
