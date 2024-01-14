## Question 1

Compared to older generations, 3rd gen version control...
1. Enables the use of multiple branches
2. Does not require an Internet connexion to commit
3. Does not require a central server
4. Automatically resolves conflicts

<details>
<summary>Click to show the answer</summary>
<p>

2 & 3: Each developer's machine has a full copy of the entire repository, and thus developers can work independently and push wherever they want, though teams typically treat one location, such as GitHub, as the source of truth.
Branches already existed before, and conflicts cannot always be resolved automatically since they involve human opinions.

</p>
</details>


## Question 2

Which of the following workflows make sense?
1. Commit, add, push
2. Push, pull, commit
3. Add, commit, push
4. Pull, merge, push

<details>
<summary>Click to show the answer</summary>
<p>

3 & 4: These are common workflows when using version control. Adding after committing does not make sense, and neither does pulling immediately after pushing.

</p>
</details>


## Question 3

Commit messages should...
1. Be less than 127 characters long
2. Start with a summary
3. Describe how newly-added code works
4. Be written with curious users in mind

<details>
<summary>Click to show the answer</summary>
<p>

2: Commit messages should start with a summary making logs easy to review, but they do not need to overall have a certain length, should not contain implementation details, and should be targeted at teammates rather than users.

</p>
</details>


## Question 4

Which of these commit messages are useful?
1. `Checkpoint`
2. `Apply changes discussed in person`
3. `Refactor UI tests to match the style of other tests`
4. `Forbid ordering a negative number of books, fixing bug #234`

<details>
<summary>Click to show the answer</summary>
<p>

3 & 4: Both have enough content to describe what they do in a log. Including a bug number can be useful but is not required.
However, referring to an offline discussion is useless for anyone who was not part of it, and even for those who were since they are likely to forget after a while.

</p>
</details>


## Question 5

Continuous Integration involves...
1. Compiling the code every few hours
2. Automatically removing bugs from new commits
3. Building new commits when they are pushed
4. Running various tools on new commits when they are pushed

<details>
<summary>Click to show the answer</summary>
<p>

3 & 4: When a commit is pushed, continuous integration involves at a minimum compiling the code, and possibly also running other tools such as static analyzers to find possible bugs.
However, no tool can automatically remove bugs, and continuous integration depends on pushes rather than time.

</p>
</details>
