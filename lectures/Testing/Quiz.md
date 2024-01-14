## Question 1

We write automated tests to...
1. Check the handling of known corner cases
2. Check that code still compiles
3. Ensure old bugs are still fixed
4. Prove the absence of bugs

<details>
<summary>Click to show the answer</summary>
<p>

1 & 3: Automated tests require developers to provide concrete inputs and sequences of operations to run, thus they can test known corner cases and known bugs.
However, they cannot prove the absence of bugs in general, and are not needed to check that code still compiles.

</p>
</details>


## Question 2

Ideally, we would test...
1. All public functions
2. All private functions
3. All modules
4. The whole program end-to-end

<details>
<summary>Click to show the answer</summary>
<p>

1 & 2 & 4: These are all good targets to test, though in practice software engineers must prioritize what to test given their time budget.
One should not test private implementation details, since the tests would break even if the code remained correct!

</p>
</details>


## Question 3

A regression test must...
1. Be automated
2. Fail before fixing the bug
3. Be deleted after fixing the bug
4. Be written after the program is released

<details>
<summary>Click to show the answer</summary>
<p>

2: A regression test must fail before its corresponding bug is fixed, otherwise it is not actually testing that bug.
However, it does not have to be automated if doing so is too difficult, especially in complex end-to-end scenarios,
should be written after a bug is found regardless of release date, and definitely must not be deleted.

</p>
</details>


## Question 4

Test-Driven Development...
1. Makes it easy to change requirements during development
2. Gives developers instant feedback on their implementation
3. Involves writing one test per public method
4. Helps ensure tests do not "over-fit" the implementation

<details>
<summary>Click to show the answer</summary>
<p>

2 & 4: Using TDD, developers must think about the behavior of their code early, rather than the specific implementation, and obtain feedback as soon as they have written said implementation thanks to the tests.
However, requirements are harder to change since tests have already been written, and there is no specific number of tests per method since one test might cover multiple methods.

</p>
</details>


## Question 5

Which of these dependencies should one inject?
1. A user interface
2. An HTTP client
3. A regular expression parser
4. A file reader

<details>
<summary>Click to show the answer</summary>
<p>

1 & 2 & 4: Anything that involves communicating with the environment, such as users, the network, or disks.
A regular expression parser is a pure function, thus there is no point in injecting it and using a "fake" version in tests as this fake would have to mimic the real one in every way.

</p>
</details>
