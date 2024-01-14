## Question 1

Which of the following are refactorings?
1. Rewriting a module
2. Renaming a variable
3. Adding a base class
4. Fixing a bug

<details>
<summary>Click to show the answer</summary>
<p>

2 & 3: Refactorings are small changes that do not affect behavior.

</p>
</details>


## Question 2

Architectural Decision Records contain...
1. Context
2. Implementation details
3. Changes to be made
4. Each team member's opinion

<details>
<summary>Click to show the answer</summary>
<p>

1 & 3: These are two of the three key components, along with the decision itself.
Implementation details and teammates' opinions might be useful in specific cases but are not generally a part of such records.

</p>
</details>


## Question 3

Which of the following _break_ source compatibility?
1. Making a parameter type _less_ specific
2. Making a parameter type _more_ specific
3. Making a variable type _more_ specific
4. Making a variable type _less_ specific

<details>
<summary>Click to show the answer</summary>
<p>

2: If the parameter type is more specific, code that previously compiled may no longer compile since it uses a type that is not specific enough.
The types of variables are irrelevant to compatibility as they are implementation details.

</p>
</details>


## Question 4

Obsolete methods...
1. Throw an exception when called
2. Are kept forever for compatibility
3. Are kept until the next minor version
4. Should not be called

<details>
<summary>Click to show the answer</summary>
<p>

4: Once a method is obsolete, it should no longer be used as it will be removed in some future major version, but it should still work so existing callers are not affected in the short term.

</p>
</details>


## Question 5

Which of these help navigate a legacy codebase?
1. A debugger
2. Pen and paper
3. Refactorings
4. Unit tests

<details>
<summary>Click to show the answer</summary>
<p>

All: These are all useful tools to navigate legacy code, making small changes to improve both the code and your understanding of the code as you go.

</p>
</details>
