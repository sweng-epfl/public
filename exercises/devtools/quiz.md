## The role of software in modern life

Which of the following attributes of a software system are not among the key properties we will concern ourselves with in this course?

- [ ] Safety
- [ ] Security
- [ ] Cost 
- [ ] Usability 
- [ ] Performance
- [ ] Manageability
- [ ] Reliability

How many lines of code (LOC) are in the software of systems like the Boeing 767 avionics or o modern premium automobile?

- [ ] Thousands
- [ ] Tens of thousands
- [ ] Hundreds of thousands
- [ ] Millions 

If a software system is deemed to be 100% safe, then is it allowed to ever fail?

- [ ] Yes 
- [ ] No

A secure system is one that protects user information from... (check all that apply)

- [ ] theft 
- [ ] unauthorized access 
- [ ] duplication
- [ ] tampering 
- [ ] being used in erroneous calculations

## The challenge of software complexity

Which statements are correct regarding tests and proofs?

- [ ] Tests cannot cover a significant fraction of a large program's behaviors, because each test can only verify one behavior at a time 
- [ ] Testing is slow because the result of each test has to be inspected for correctness
- [ ] Proof techniques can verify programs with respect to low-level properties (such as division by zero) 
- [ ] Proof techniques can verify higher-level properties (so called functional properties) 

For sel4, how did the overall effort involved in proving corectness compare to the effort of writing the actual code of the kernel?

- [ ] Roughly the same effort
- [ ] Writing the code took 10x more effort than generating the proof
- [ ] Generating the proof took 10x more effort than writing the code
- [ ] Generating the proof took 100x more effort than writing the code 

Is it possible to have a program with an infinite number of paths?

- [ ] No, because the number of paths is exponential in the program size, and the program size is always finite
- [ ] No, because a program always terminates eventually
- [ ] Yes, if the program has a loop that never ends (e.g., a server listening loop) 
- [ ] Yes, if the program runs on a 64-bit processor.

## Preventing and finding errors early

In practice, which part of the software development process takes the most time?

- [ ] Problem definition and requirements analysis
- [ ] Software architecture and design
- [ ] Coding
- [ ] Testing and debugging 
- [ ] Integration and maintenance

Ideally, which part of the software development should take the least time?

- [ ] Problem definition and requirements analysis
- [ ] Software architecture and design
- [ ] Coding
- [ ] Testing
- [ ] Debugging 

## Building Software

Where do you define the expectations a user has from a software project?

- [ ] Problem statement
- [ ] Functional specification 
- [ ] Software architecture

An earlier version of this class had a different type of project. The SwEng homeworks each presented new feature requirements, such that at the end of the semester a team had implemented a complete Android application. Which software building approach would best fit this model?

- [ ] Block approach
- [ ] Agile approach 
- [ ] None of the above

## Developer Tools

Suppose we have the following C source code, in test.c: 

```c
#include <stdio.h>
int main() { printf("Hello World!"); return 0; }
```

If we launch 'gcc -o test test.c', which creates the executable, which tools did we use:

- [ ] Compiler 
- [ ] Preprocessor 
- [ ] Debugger
- [ ] Builder
- [ ] Linker 
- [ ] Profiler

Choose the correct order of the software building steps:

- [ ] link, compile, preprocess, write code
- [ ] preprocess, write code, compile, link
- [ ] write code, preprocess, compile, link 
- [ ] write code, preprocess, link, compile

## Version Control Systems

Suppose a developer wants to cancel the changes they made to a piece of code and revert to a previous version. However, the developer is on a train, without access to the internet. This can be done in:

- [ ] First generation version control systems, or later
- [ ] Second generation version control systems, or later
- [ ] Third generation version control systems 
- [ ] No current version control system supports this scenario

Which VCS does not require merging modifications?

- [ ] Locking VCS (first generation) 
- [ ] Decentralized VCS(third generation)
- [ ] Centralized VCS(second generation)

