# Exercise 1: Commit Messages

## Good Commit Messages

The purpose of the commit message is to convey the context of a given change or set of changes, and briefly summarize the purpose of the changes and what they are for. 

Imagine someone has pushed a commit to your project, and it involves changes to multiple files in multiple locations. 
You want to understand what this fix is for and what it does, so you read the commit messages and it says `fixes`. 
Very helpful, you think, very helpful indeed. 
You must now examine each change in each file, and waste a bunch of time figuring out what this contributor has actually done. 
Writing a good commit message can save time and energy, and help with the maintenance of a project with many team members. 

Avoid lumping too many changes together in one commit.
A commit should generally fix one bug, or implement one feature (although, depending on the size and complexity of the feature, this may be better accomplished with multiple commits, see exercises on branching for how to develop complex features over multiple commits). 

As mentioned in the bootcamp, Chris Beam has some great [guidelines](https://chris.beams.io/posts/git-commit/) for writing commits. 

* Separate subject from body with a blank line
* Limit the subject line to 50 characters
* Capitalize the subject line
* Do not end the subject line with a period
* Use the imperative mood in the subject line
* Wrap the body at 72 characters
* Use the body to explain what and why vs. how

Give his post a read through for more details.

Keep in mind that not all commits need a message with such a format. 
Sometimes a simple fix or format change just needs a single line commit. 
Use your best judgement, and think about the other people who may be using or vetting your work.

You can always use the `-m` flag to `git` on the command line to write a commit message, however it's not very amenable to multi-line messages. If you commit without `-m`, `git` will open the default editor for you to write a commit message; this is usually `vim` or `vi`. These can be unintuitive to new users, but just google/bing how to use `vim` if you get "stuck" here.

Alternatively, you can change the editor `git` uses by doing 

```
git config core.editor <editor>
```

where on Linux and Mac you might use `nano` for the editor, and on Windows you might use `notepad`.

`git` will automatically open this editor for you to write a commit message when you run `git commit`.
Write your commit message, save, and exit the editor. 

#### Note

You can find more info about first time `git` setup [here](https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup).

### Exercise 1.1: Writing Commit Messages

Write commit messages for each of the following `commit` situations. 

1. You rewrite the entire project README because it is out of date, too concise and didn't show how to build and use the code.
2. You notice that some request data in your system is often already sorted, so you replace the entire `quicksort` implementation with `heapsort` to get more predictable latency.
3. You use the `google-java-format` tool to format a single file to comply with the project's chosen source code style.
4. You fix typos in comments in several files.

## Exercise 1.2: Fixing and Viewing Commits

If you have messed up your commit message, or want to change it, simply do

`$ git commit --amend`

This will open the `EDITOR` and allow you to fix your commit message.

To view previous commits and messages in the repository history, run

`$ git log`

This will show previous commits, their hash ID and the commit message. 
To view a specific commit, copy its hash ID and run 

`$ git show <commit>`

### Exercise 1.3: Fixing and Viewing Commits Exercises

Try the following exercise (you can just clone this repo and navigate to it in a terminal, or one of your own repos):

1. Create a new file in this repo, add it, and commit it with an appropriate message.
2. Amend the commit message and save it.
3. Show the previous commits using `git log`.