## Question 1

Teams pick a coding convention...
1. Together
2. Per file
3. Per language and framework
4. To maximize readability

<details>
<summary>Click to show the answer</summary>
<p>

1 & 2 & 4: Teams should pick a convention they all find acceptable, based on the idioms of the language and framework they use, with the goal of making code easier to reason about.
Choosing a different convention per file would work against that last goal.

</p>
</details>


## Question 2

A debugger can...
1. Pause program execution for inspection
2. Find bugs automatically in a program
3. Modify program state regardless of "public"/"private" accessibility
4. Work with only a compiled program

<details>
<summary>Click to show the answer</summary>
<p>

1 & 3 & 4: A debugger can inspect and modify all program state regardless of what visibility is specified in the source code, and does not strictly need source code or debugging symbols though these make the job much easier.
However, debuggers do not perform analyses on their own..

</p>
</details>


## Question 3

A postcondition should be...
1. Explicitly checked by callees
2. Explicitly checked by callers
3. A part of an invariant
4. Used only alongside a precondition

<details>
<summary>Click to show the answer</summary>
<p>

None: A postcondition does not have to be explicitly checked at run-time, it could for instance be checked by static analysis tools or be too informal to be automatically verifiable.
"The disk is not full after this method has been called, assuming nobody else touches the disk" is a valid postcondition, for instance.
It may be used as part of an invariant, and may be used alongside a precondition, but it could also stand on its own.

</p>
</details>


## Question 4

One should defensively copy...
1. All parameters
2. All collections of items
3. The `this`/`self` object
4. Only in object-oriented languages

<details>
<summary>Click to show the answer</summary>
<p>

None: Defensive copying is necessary to avoid corrupting an object's inner state by accident or malice.
It is necessary when a function logically "takes ownership" of a value that is represented using a mutable type, such as `List<Integer>` in Java.
This can also happen using mutable types in functional languages, but it may not happen even in object-oriented languages using immutable types.
One basic example of a type that never needs copying is `int`.
Defensively copying the `this`/`self` object makes little sense.

</p>
</details>


## Question 5

Which of these can help debug?
1. Invariants
2. A debugger
3. Logs
4. Coding conventions

<details>
<summary>Click to show the answer</summary>
<p>

All of these can be useful, to various degrees, depending on the bug.

</p>
</details>
