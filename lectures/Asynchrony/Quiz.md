## Question 1

Asynchronous code...
1. Executes in parallel to other code
2. Uses a pattern such as Future
3. Requires special language syntax
4. Improves program performance

<details>
<summary>Click to show the answer</summary>
<p>

2: Async code uses futures, sometimes also called by other names such as "tasks", or other patterns such as callbacks.
However, it does not require parallelism and can be used even in languages without special syntax by composing futures manually.
While it may improve performance in some cases, it is mainly about maintainability and keeping the rest of the system reactive.

</p>
</details>


## Question 2

Canceling an asynchronous operation...
1. Requires deleting the thread executing it
2. Helps avoid unnecessary work
3. Should always be done after a timeout
4. Can only be done shortly after starting it

<details>
<summary>Click to show the answer</summary>
<p>

2: Cancellation can help avoid unnecessary work, regardless of what stage the operation is in.
It should not require dealing with low-level concepts such as threads.
Whether it should be done at all depends on context.

</p>
</details>


## Question 3

Testing async code...
1. Involves `sleep` calls to wait for the result
2. Should generally not be done
3. Is generally not a priority
4. Can involve tests being async themselves

<details>
<summary>Click to show the answer</summary>
<p>

4: Testing async code can be done with async tests if the test framework supports it, but this is not required.
Async code is no different than other kinds of code in terms of prioritization of testing.
Never use `sleep` to wait for an operation to finish in the background, this is brittle and slow!

</p>
</details>


## Question 4

A function should be async...
1. If its current implementation uses asynchrony
2. If it is likely to be implemented in an asynchronous way
3. Unless there is a reason to choose otherwise
4. If it helps maintain consistency with related functions

<details>
<summary>Click to show the answer</summary>
<p>

2 & 4: If it is part of a set of functions that are overall likely to use asynchrony, such as dealing with I/O, a function is probably better off as async,
even if it is possible to implement it synchronously.

</p>
</details>


## Question 5

Viewing asynchrony as a monad...
1. Is required in functional programming languages
2. Helps understand asynchrony using existing concepts
3. Means existing code can be reused without changes
4. Requires an extra layer of indirection

<details>
<summary>Click to show the answer</summary>
<p>

3: Using existing concepts makes it easier to both understand async code, though this does not mean code that deals with other monads can or should be reused for async.
This does not require a functional programming language nor more indirection.

</p>
</details>
