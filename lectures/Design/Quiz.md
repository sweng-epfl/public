## Question 1

A good module...
1. Can be described in few words
2. Offers multiple abstraction levels
3. Can be reused in another system
4. Can be tested independently

<details>
<summary>Click to show the answer</summary>
<p>

1 & 2 & 4: These all go together, along with the fact that any given module should have a single level of abstraction.
A program is made up of modules that have different abstraction levels, and may expose multiple levels to others, but individual modules should have one role only.

</p>
</details>


## Question 2

Abstraction leaks...
1. Enable attackers to get private user data
2. Damage modularity
3. Expose implementation details
4. Involve inheritance

<details>
<summary>Click to show the answer</summary>
<p>

2 & 3: Abstraction leaks happen when a module accidentally exposes more than one abstraction level, making the module harder to reason about since the lower level of abstraction should be an implementation detail.
This may or may not involve inheritance.
It is not related to a data leak.

</p>
</details>


## Question 3

Design patterns...
1. Are solutions to common problems
2. Are precise instructions
3. Help with maintainability
4. Must all be known by heart

<details>
<summary>Click to show the answer</summary>
<p>

1 & 3: Design patterns are well-known solutions to well-known problems, which provide a shared vocabulary for software engineers and thus help maintain software they did not write themselves.
However, they are not specific recipes, and there are too many possible variants to know them all.

</p>
</details>


## Question 4

M-V-* patterns...
1. Are only for desktop apps
2. Are only for server apps
3. Make it easier to reuse business logic
4. Make it easier to reuse UI code

<details>
<summary>Click to show the answer</summary>
<p>

3: These patterns enable the reuse of business logic, ideally completely unchanged, with different UI code in various kinds of apps such as desktop and mobile apps.

</p>
</details>


## Question 5

Which of the following are likely a bug?
1. Request failed: overloaded server
2. Parsing input failed: null pointer dereference
3. Processing failed: stack overflow
4. Printing failed: no Internet connection

<details>
<summary>Click to show the answer</summary>
<p>

2 & 3: These involve problems with the code itself, rather than its environment.
Retrying the operation after a while, for instance, is unlikely to be a good workaround.

</p>
</details>
