# Infrastructure

> **Prerequisites**: Before following this lecture, you should:
> - Install Git. On Windows, use [WSL](https://docs.microsoft.com/en-us/windows/wsl/install) as Git is designed mostly for Linux.
>   On macOS, see [the git documentation](https://git-scm.com/download/mac). On Linux, Git may already be installed, or use your distribution's package manager.
>   If you have successfully installed Git, running `git --version` in the command line should show a version number.
> - Create a GitHub account (you do not have to use an existing GitHub account, you can create one just for this course if you wish)
> - Set up an SSH key for GitHub by following [their documentation](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/adding-a-new-ssh-key-to-your-github-account)
> - Tell Git who you are by running `git config --global user.name 'your_name'` with your name and `git config --global user.email 'your_email'` with the e-mail you used for GitHub
> - Choose an editor Git will open to write a summary of your changes with `git config --global core.editor 'your_editor'`, since Git defaults to `vi` which is hard to use for newcomers.
>   On Windows with WSL you can use `notepad.exe`, which will open Windows's Notepad.
>   On macOS you can use `open -e -W -n` which will open a new TextEdit window.
>   On Linux you can use your distribution's built-in graphical text editor, or `nano`.
>
> If you use Windows with WSL, note that running `explorer.exe .` from the Linux command line will open Windows's Explorer in the folder your command-line is, which is convenient.
>
> Optionally, you may want to set the Git config setting `core.autocrlf` to `true` on Windows and `input` on Linux and macOS,
> so that Git converts between Unix-style line endings (`\n`) and Windows-style line separators (`\r\n`) automatically.


Where do you store your code, and how do you make changes to it?
If you're writing software on your own, this is not a problem, as you can use your own machine and change whichever files you want whenever you want.
But if you're working with someone else, it starts being problematic.
You could use an online cloud service where you store files, and coordinate who changes which file and when.
You could email each other changes to sets of files.
But this does not work so well when you have more people, and it is completely unusable when you have tens or hundreds of people working on the same codebase.
This is where infrastructure comes in.


## Objectives

- Contrast old and new _version control_ systems
- Organize your code with the _Git_ version control system
- Write useful descriptions of code changes
- Avoid mistakes with _continuous integration_


## What is version control?

Before we talk about how to manage your code using a version control system, we must define some terms.

A _repository_ is a location in which you store a codebase, such as a folder on a remote server.
When you make a set of changes to a repository, you are _pushing_ changes.
When you retrieve the changes that others have made from the repository, you are _pulling_ changes.

A set of changes is called a _commit_.
Commits have four main components: who, what, when, and why.
"Who" is the author of the commit, the person who made the changes.
"What" is the contents of the commit, the changes themselves.
"When" is the date and time at which the commit was made. This can be earlier than when the commit was actually pushed to a repository.
"Why" is a message associated with the commit that explains why the changes were made, such as detailing why there was a bug and why the new code fixes the bug.
The "why" is particularly important because you will often have to look at old changes later and understand why they were made.

Sometimes, a commit causes problems. Perhaps a commit that was supposed to improve performance also introduces a bug.
Version control systems allow you to _revert_ this commit, which creates a new commit whose contents are the reverse of the original one.
That is, if the original commit replaced "X" by "Y", then the revert commit replaces "Y" with "X".
Importantly, the original commit is not lost or destroyed, instead a new revert commit is created.

Commits are put together in a _history_ of changes.
Initially, a repository is empty. Then someone adds some content in a commit, then more content in another commit, and so on.
The history of a repository thus contains all the changes necessary to go from nothing to the current state.
Some of these changes could be going back and forth, such as revert commits, or commits that replace code that some previous commit added.
At any time, any developer with access to the repository can look at the entire history to see who made what changes when and why.

_1st generation_ version control systems were essentially a layer of automation on manual versioning.
As we mentioned earlier, if you are developing with someone else, you might put your files somewhere and coordinate who is changing what and when.
A 1st generation system helps you do that with fewer mistakes, but still fundamentally uses the same model.

With 1st generation version control, if Alice wants to work on file A, she "checks out" the file.
At that point, the file is locked: Alice can change it, but nobody else can. If Bob wants to also check out file A, the system will reject his attempt.
Bob can, however, check out file B if nobody else is using it.
Once Alice is done with her work, she creates a commit with her changes, and releases the lock. At that point, Bob can check out file A and make his changes.

1st generation version control systems thus act as locks at the granularity of files.
They prevent developers from making parallel changes to the same file, which prevents some mistakes but isn't very convenient.
For instance, Alice might want to modify function X in file A, while Bob wants to modify function Y in file A.
These changes won't conflict, but they still cannot do them in parallel, because locks in 1st generation version control are at the file granularity.

Developers moved on from 1st generation systems because they wanted more control over _conflicts_.
When two developers want to work on the same file at the same time, they should be able to, as long as they can then _merge_ their changes into one unified version.
Merging changes isn't always possible automatically. If two developers changed the same function in different ways, for instance, they probably need to have a chat to decide which changes should be kept.

Another feature that makes sense if a system can handle conflicts and merges is _branches_.
Sometimes, developers want to work on multiple copies of the codebase in parallel.
For instance, you might be working on some changes that improve performance, when a customer comes in with a bug report.
You could fix the bug and commit it with your performance changes, but the resulting commit is not convenient.
If you later need to revert the performance changes, for instance, you would also revert the bugfix because it's in the same commit.
Instead, you create a branch for your performance changes, then you switch to a branch for the bugfix, and you can work on both in parallel.
When your bugfix is ready, you can _merge_ it into the "main" branch of the repository, and the same goes for the performance changes.
One common use of branches is for versions: you can release version 1.0 of your software, for instance, and create a branch representing the state of the repository for that version.
You can then work on the future version 2.0 in the "main" branch. If a customer reports a bug in version 1.0, you can switch to the branch for version 1.0, fix the bug, release the fix,
then go back to working on version 2.0. Your changes for version 1.0 did not affect your main branch, because you made them in another branch.

The typical workflow with branches for modern software is that you will create a branch starting from the main branch of the repository,
then add some commits to the branch to fix a bug or add a feature or perform whatever task the branch is for,
and then ask a colleague to review it. If the colleague asks for some changes, such as adding more code comments, you can add a commit to the branch with these changes.
Once your colleague is happy, you can merge the branch's commits into the main branch.
Then you can create another branch to work on something else, and so on. Your colleagues are themselves also working on their own branches.
This workflow means everyone can push whatever commits they want on their branch without conflicting with others, even if their work isn't quite finished yet.
Often it is a good idea to _squash_ a branch's commits into a single commit and merge the resulting commit into the main branch.
This combines all of the branch's changes into one clean commit in the history of the main branch, rather than having a bunch of commits that do a few small changes each but make no sense without each other.

In the case of branches that represent versions, one sometimes needs to apply the same changes to multiple branches.
For instance, while developing version 2.0 in the main branch, you may find a bug, and realize that the bug also exists in version 1.0.
You can make a commit fixing the bug in version 2.0, and then _cherry pick_ the commit into the branch for version 1.0.
As long as the change does not conflict with other changes made in the version 1.0 branch, the version control system can copy your bugfix commit into a commit for another branch.

_2nd generation_ version control systems were all about enabling developers to handle conflicts.
Alice can work on file A without the need to lock it, and Bob can also work on file A at the same time.
If Alice pushes her changes first, the system will accept them, and when Bob then wants to apply his changes, two things can happen.
One possibility is that the changes can be merged automatically, for instance because the changes are on two different parts of the file.
The other possibility is that the changes conflict and must be merged manually. Bob then has to choose what to do, perhaps by asking Alice, and produce one "merged" version of the file that can be pushed.

The main remaining disadvantage of 2nd generation version control is its centralization.
There is one repository that developers work with, hosted on one server.
Committing changes requires an Internet connection to that server.
This is a problem if the server is down, or a developer is in a place without access to the Internet, or any other issue that prevents a developer from reaching the server.

_3rd generation_ version control systems are all about decentralization.
Every machine has its own repository. It is not a "backup" or a "replica" of some "main" repository, but just another clone of the repository.
Developers can make commits locally on their own repository, then push these commits to other clones of the repository, such as on a server.
Developers can also have multiple branches locally, with different commits in each, and push some or all of these branches to other clones of the repository.
This all works as long as the repositories have compatible histories. That is, one cannot push a change to a repository that isn't based on the same history as one's local repository.

In practice, teams typically agree on one "main" repository that they will all push commits to, and work locally on their clone of that repository.
While from the version control system's point of view all repository clones are equal, it is convenient for developers to agree on one place where everyone puts their changes.

The main version control system in use today is _Git_.
Git was invented by Linus Torvalds, who invented Linux, because he was tired of the problems with the previous version control system he used for Linux.
There are also other 3rd generation version control systems such as Mercurial and Bazaar, but Git is by far the most used.

Many developers use public websites to host the "main" repository clone of their projects.
The most famous these days is GitHub, which uses Git but isn't technically related to it.
GitHub not only stores a repository clone, but can also host a list of "issues" for the repository, such as bugs and feature requests, as well as other data such as a wiki for documentation.
There are also other websites with similar features such as GitLab and BitBucket, though they are not as popular.

An example of a project developed on GitHub is [the .NET Runtime](https://github.com/dotnet/runtime), which is developed mainly by Microsoft employees and entirely using GitHub.
Conversations about bugs, feature requests, and code reviews happen in the open, on GitHub.


## How does one use Git?

Now that we've seen the theory, let's do some practice!
You will create a repository, make some changes, and publish it online. Then we'll see how to contribute to an existing online repository.

Git has a few basic everyday commands that we will see now, and many advanced commands we won't discuss here.
You can always look up commands on the Internet, both basic and advanced ones.
You will eventually remember the basics after using them enough, but there is no shame at all in looking up what to do.

We will use Git on the command line for this tutorial, since it works the same everywhere.
However, for everyday tasks you may prefer using graphical user interfaces such as [GitKraken](https://www.gitkraken.com/), [GitHub Desktop](https://desktop.github.com/), or the Git support in your favorite IDE.

Start by creating a folder and _initializing_ a repository in that folder:

```sh
~$ mkdir example
~$ cd example
~/example$ git init
```

Git will tell you that you have initialized an empty Git repository in `~/example/.git/`.
This `.git/` folder is a special folder Git uses to store metadata. It is not part of the repository itself, even though it is in the repository folder.

Let's create a file:

```sh
$ echo 'Hello' > hello.txt"
```

We can now ask Git what it thinks is going on:

```sh
$ git status
...
Untracked files:
        hello.txt
```

Git tells us that it sees we added `hello.txt`, but this file isn't tracked yet.
That is, Git won't include it in a commit unless we explicitly ask for it. So let's do exactly that:

```sh
$ git add -A
```

This command asks Git to include all current changes in the repository for the next commit.
If we make more changes, we will have to ask for these new changes to be tracked as well.
But for now, let's ask Git what it thinks:

```sh
$ git status
...
Changes to be committed:
        new file:   hello.txt
```

Now Git knows we want to commit that file. So let's commit it:

```sh
$ git commit
```

This will open a text editor for you to type the commit message in. As we saw earlier, the commit message should be a description of _why_ the changes were made.
Often the very first commit in a repository sets up the basic file structure as an initial commit, so you could write `Initial commit setting up the file` or something similar.
You will then see output like this:

```sh
[...] Initial commit.
 1 file changed, 1 insertion(+)
 create mode 100644 hello.txt
```

Git repeats the commit message you put, here `Initial commit.`, and then tells you what changes happened. Don't worry about that `mode 100644`, it's more of an implementation detail.

Let's now make a change by adding one line:

```sh
$ echo 'Goodbye' >> hello.txt
```

We can ask git for the details of what changes we did:

```sh
$ git diff
```

This will show a detailed list of the differences between the state of the repository as of the latest commit and the current state of the repository, i.e., we added one line saying `Goodbye`.

Let's track the changes we just made:

```sh
$ git add -A
```

What happens if we ask for a list of differences again?

```sh
$ git diff
```

...Nothing! Why? Because `diff` by default shows differences that are not tracked for the next commit.
There are three states for changes to files in Git: modified, staged, and committed.
By default changes are modified, then with `git add -A` they are staged, and with `git commit` they are committed.
We have been using `-A` with `git add` to mean "all changes", but we could in fact add only specific changes, such as specific files or even parts of files.

In order to see staged changes, we have to ask for them:

```sh
$ git diff --staged
```

We can now commit our changes. Because this is a small commit that does not need much explanation, we can use `-m` to write the commit message directly in the command:

```sh
$ git commit -m 'Say goodbye'
```

Let's now try out branches, by creating a branch and switching to it:

```sh
git switch -c feature/today
```

The slash in the branch name is nothing special to Git, it's only a common naming convention to distinguish the purpose of different branches.
For instance, you might have branches named `feature/delete-favorites` or `bugfix/long-user-names`.
But you could also name your branch `delete-favorites` or `bugfix/long/user/names` if you'd like, as long as everybody using the repository agrees on a convention for names.

Now make a change to the single line in the file, such as changing "Hello" to "Hello today".
Then, track the changes and commit them:

```sh
$ git add -A && git commit -m 'Add time'
```

You will notice that Git tells you there is `1 insertion(+), 1 deletion (-)`.
This is a bit odd, we changed one line, why are there two changes?
The reason is that Git tracks changes at the granularity of lines.
When you edit a line, Git sees this as "you deleted the line that was there, and you added a new line". The fact that the "deleted" and the "added" lines are similar is not relevant.

If you've already used Git before, you may have heard of the `-a` to `git commit`, which could replace the explicit `git add -A` in our case.
The reason we aren't using it here, and the reason why you should be careful if using it, is that `-a` only adds changes to existing files.
It does not add changes to new files or deleted files.
This makes it very easy to accidentally forget to include some new or deleted files in the commit, and to then have to make another commit with just these files, which is annoying.

Anyway, we've made a commit on our `feature/today` branch.
In case we want to make sure that we are indeed on this branch, we can ask Git:

```sh
$ git branch
```

This will output a list of branches, with an asterisk `*` next to the one we are on.

Let's now switch to our main branch.
Depending on your Git version, this branch might have different names, so look at the output of the previous command and use the right one, such as `master` or `main`:

```sh
$ git switch main
```

To see what happens when two commits conflict, let's make a change to our `hello.txt` file that conflicts with the other branch we just made.
For instance, replace "Hello" with "Hello everyone".
Then, track the change and commit it as before.

At this point, we have two branches, our main branch and `feature/today`, that have diverged: they both have one commit that is not in the other.
Let's ask Git to merge the branches, that is, add the commits from the branch we specify into the current branch:

```sh
$ git merge feature/today
```

Git will optimistically start with `Auto-merging hello.txt`, but this will soon fail with a `Merge conflict in hello.txt`.
Git will ask us to fix conflicts and commit the result manually.

What does `hello.txt` look like now?

```sh
$ cat hello.txt
<<<<<<< HEAD
Hello everyone
=======
Hello today
>>>>>>> feature/today
Goodbye
```

Let's take a moment to understand this. The last line hasn't changed, because it's not a part of the conflict.
The first line has been expanded to include both versions: between the `<<<` and `===` is the version in `HEAD`, that is, the "head", the latest commit, in the current branch.
Indeed, on our main branch the first line was `Hello everyone`.
Between the `===` and the `>>>` is the version in `feature/today`.
What we need to do is manually merge the changes, i.e., edit the file to replace the conflict including the `<<<`, `===`, and `>>>` lines with the merged changes we want.
For instance, we could end up with a file containing the following:

```sh
$ cat hello.txt
Hello everyone today
Goodbye
```

This is one way to merge the file. We could also have chosen `Hello today everyone`, or perhaps we would rather discard one of the two changes and keep `Hello everyone` or `Hello today`.
Or perhaps we want yet another change, we could have `Hello hello` instead. Git does not care, it only wants us to decide what the merged version should be.

Once we have made our merge changes, we should add the changes and commit as before:

```sh
$ git add -A && git commit -m 'Merge'
```

Great. Wait, no, actually, not so great. That's a pretty terrible commit message. It's way too short and not descriptive.
Thankfully, _because we have not published our changes to another clone of the repository yet_, we can make changes to our commits!
This is just like how a falling tree makes no sound if there's no one around to hear it. If nobody can tell, it did not happen.
We can change our commit now, and when we push it to another clone the clone will only see our modified commit.
However, if we had already pushed our commit to a clone, our commit would be visible, so we could not change it any more as the clone would get confused by a changing commit since commits are supposed to be immutable.

To change our commit, which again should only be done if the commit hasn't been pushed yet, we "amend" it:

```sh
$ git commit --amend -m 'Merge the feature/today branch'
```

We have only modified the commit message here, but we could also modify the commit contents, i.e., the changes themselves.

Sometimes we make changes we don't actually want, for instance temporary changes while we debug some code.
Let's make a "bad" change:

```sh
$ echo 'asdf' >> hello.txt
```

We can restore the file to its state as of the latest commit to cancel this change:

```sh
$ git restore hello.txt
```

Done! Our temporary changes have disappeared.
You can also use `.` to restore all files in the current directory, or any other path.
However, keep in mind that "disappeared" really means disappeared. It's as if we never changed the file, as the file is now in the state it was after the latest commit.
Do not use `git restore` unless you actually want to lose your changes.

Sometimes we accidentally add files we don't want. Perhaps a script went haywire, or perhaps we copied some files by accident.
Let's make a "bad" new file:

```sh
$ echo 'asdf' > mistake.txt
```

We can ask Git to "clean" the repository, which means removing all untracked files and directories.
However, because this will delete files, we'd better first run it in "dry run" mode using `-n`:

```sh
$ git clean -fdn
```

This will show a list of files that _would_ be deleted if we didn't include `-n`.
If we're okay with the proposed deletion, let's do it:

```sh
$ git clean -fd
```

Now our `mistake.txt` is gone.

Finally, before we move on to GitHub, one more thing: keep in mind that Git only tracks _files_, not _folders_.
Git will only keep track of folders if they are a part of a file's path.

So if we create a folder and ask Git what it sees, it will tell us there is nothing, because the folder is empty:

```sh
$ mkdir folder
$ git status
```

If you need to include an "empty" folder in a Git repository for some reason, you should add some empty file in it so that Git can track the folder as part of that file.

Let's now publish our repository. Go to [GitHub](https://github.com) and create a repository using the "New" button on the home page.
You can make it public or private, but do not create files such as "read me" files or anything else, just an empty repository.

Then, follow the GitHub instructions for an existing repository from the command line. Copy and paste the commands GitHub gives you.
These commands will add the newly-created GitHub repository as a "remote" to your local repository, which is to say, another clone of the repository that Git knows about.
Since it will be the only remote, it will also be the default one. The default remote is traditionally named `origin`.
The commands GitHub provide will also push your commits to this remote.
Once you've executed the commands, you can refresh the page on your GitHub repository and see your files.

Now make a change to your `hello.txt`, track the change, and commit it.
You can then sync the commit with the GitHub repository clone:

```sh
$ git push
```

You can also get commits from GitHub:

```sh
$ git pull
```

Pulling will do nothing in this case, since nobody else is using the repository.
In a real-world scenarios, other developers would also have a clone of the repository on their machine and use GitHub as their default remote.
They would push their changes, and you would pull them.

Importantly, `git pull` only synchronizes the current branch. If you would like to sync commits from another branch, you must `git switch` to that branch first.

Similarly, `git push` only synchronizes the current branch, and if you create a new branch you must tell it where to push with `-u` by passing both the remote name and the branch name:

```sh
$ git switch -c example
$ git push -u origin example
```

Publishing your repository online is great, but sometimes there are files you don't want to publish.
For instance, the binary files compiled from source code in the repository probably should not be in the repository, since they can be recreated easily and would only take up space.
Files that contain sensitive data such as passwords should also not be in the repository, especially if it's public.
Let's simulate a sensitive file:

```sh
$ echo '1234' > password.txt
```

We can tell Git to pretend this file doesn't exist by adding a line with its name to a special file called `.gitignore`:

```sh
$ echo 'password.txt' >> .gitignore
```

Now, if you try `git status`, it will tell you that `.gitignore` was created but ignore `password.txt` since you told Git to ignore it.

You can also ignore entire directories.
Note that this only works for files that haven't been committed to the repository yet.
If you had already made a commit in which `password.txt` exists, adding its name to `.gitignore` will only ignore future changes, not past ones.
If you accidentally push to a public repository a commit with a file that contains a password, you should assume that the password is compromised and immediately change it.
There are bots that scan GitHub looking for passwords that have been accidentally committed, and they will find your password if you leave it out there, even for a few seconds.

Now that you have seen the basics of Git, time to contribute to an existing project!
You will do this through a _pull request_, which is a request that the maintainers of an existing project pull your changes into their project.
This is a GitHub concept, as from Git's perspective it's merely syncing changes between clones of a repository.

Go to <https://github.com/sweng-example/hello> and click on the "Fork" button.
A _fork_ is a clone of the repository under your own GitHub username, which you need here because you do not have write access to `sweng-example/hello` so you cannot push changes to it.
Instead, you will push changes to your fork, to which you have write access, and then ask the maintainers of `sweng-example/hello` to accept the change.
You can create branches within a fork as well, as a fork is just another clone of the repository.
Typically, if you are a collaborator of a project, you will use a branch in the project's main repository, while if you are an outsider wanting to propose a change, you will create a fork first.

Now that you have a forked version of the project on GitHub, click on the "Code" button and copy the SSH URL, which should start with `git@github.com:`.
Then, ask Git to make a local clone of your fork, though you should go back to your home directory first, since creating a repository within a repository causes issues:

```sh
$ cd ~
$ git clone git@github.com:...
```

Git will clone your fork locally, at which point you can make a change, commit, and push to your fork.
Once that's done, if you go to your fork on GitHub, there should be a banner above the code telling you that the branch in your fork is 1 commit ahead of the main branch in the original repository.
Click on the "Contribute" button and the "Open pull request" button that shows up, then confirm that you want to open a pull request, and write a description for it.

Congratulations, you've made your first contribution to an open source project!

The best way to get used to Git is to use it a lot. Use Git even for your own projects, even if you do not plan on using branches.
You can use private repositories on GitHub as backups, so that even if your laptop crashes you will not lose your code.

There are many advanced features in Git that can be useful in some cases, such as `bisect`, `blame`, `cherry-pick`, `stash`, and many more.
Read the [official documentation](https://git-scm.com/docs/) or find online advanced tutorials for more if you're curious!


## How does one write good commit messages?

Imagine being an archaeologist and having to figure out what happened in the past purely based on some half-erased drawings, some fossils, and some tracks.
You will eventually figure out something that could've happened to cause all this, but it will take time and you won't know if your guess is correct.
Wouldn't it be nice if there was instead a journal that someone made, describing everything important they did and why they did it?

This is what commit messages are for: keep track of what you do and why you did it, so that other people will know it even after you're done.
Commit messages are useful to people who will review your code before approving it for merging in the main branch, and to your colleagues who investigate bugs months after the code was written.
Your colleagues in this context include "future you". No matter how "obvious" or "clear" the changes seems to you when you make them, a few months later you won't remember why you did something the way you did it.

The typical format of a commit message is a one-line summary followed by a blank line and then as many lines as needed for details. For instance, this is a good commit message:

```
Fix adding favorites on small phones

The favorites screen had too many buttons stacked on the same row.
On phones with small screens, there wasn't enough space to show them all,
and the "add" button was out of view.

This change adds logic to use multiple rows of buttons if necessary.
```

As we saw earlier, "squashing" commits is an option when merging your code into the main branch, so not all commits on a branch need to have such detailed messages.
Sometimes a commit is just "Fix a typo" or "Add a comment per the review feedback". These commits aren't important to understand the changes,
so their messages will be dropped once the branch is squashed into a single commit while being merged.

The one-line summary is useful to get an overview of the history without having to see every detail.
You can see it on online repositories such as GitHub, but also locally.
Git has a `log` command to show the history, and `git log --oneline` will show only the one-line summary of each commit.

A good summary should be short and in the imperative mood.
For instance:
- "Fix bug #145"
- *Add an HD version of the wallpaper"
- "Support Unicode 14.0"

The details should describe _what_ the changes do and _why_ you did them, but not _how_.
There is no point in describing how because the commit message is associated with the commit contents, and those already describe how you changed the code.


## How can we avoid merging buggy code?

Merging buggy code to the main branch of a repository is an annoyance for all contributors to that repository.
They will have to fix the code before doing the work they actually want to do, and they may not all fix it in the same way, leading to conflicts.

Ideally, we would only accept pull requests if the resulting code compiles, is "clean" according to the team's standards, and has been tested.
Different teams have different ideas of what "clean" code is, as well as what "testing" means since it could be manual, automated, performed on one or multiple machines, and so on.

When working in an IDE, there will typically be menu options to analyze the code for cleanliness, to compile the code, to run the code, and to run automated tests if the developers wrote some.
However, not everyone uses the same IDE, which means they may have different definitions of what these operations mean.

The main issue with using operations in an IDE to check properties about the code is that humans make mistakes.
On a large enough projects, human mistakes happen all the time. For instance, it's unreasonable to expect hundreds of developers to never forget even once to check that the code compiles and runs.
Checking for basic mistakes also a poor use of people's time. Reviewing code should be about the logic of the code, not whether every line is syntactically valid, that's the compiler's job.

We would instead like to _automate_ the steps we need to check code.
This is done using a _build system_, such as CMake for C++, MSBuild for C#, or Gradle for Java.
There are many build systems, some of which support multiple languages, but they all fundamentally provide the same feature: build automation.
A build system can invoke the compiler on the right files with the right flags to compile the code, invoke the resulting binary to run the code,
and even perform more complex operations such as downloading dependencies based on their name if they have not been downloaded already.

Build systems are configured with code. They typically have a custom declarative language embedded in some other language such as XML.
Here is an example of build code for MSBuild:

```xml
<Project Sdk="Microsoft.NET.Sdk">
  <ItemGroup>
    <PackageReference Include="Microsoft.Z3" Version="4.10.2" />
  </ItemGroup>
</Project>
```

This code tells MSBuild that (1) this is a .NET project, which is the runtime typically associated with C#, and (2) it depends on the library `Microsoft.Z3`, specifically its version `4.10.2`.
One can then run MSBuild with this file from the command line, and MSBuild will compile the project after first downloading the library it depends on if it hasn't been downloaded already.
In this case, the library name is associated with an actual library by looking up the name on [NuGet](https://www.nuget.org/), the library catalog associated with MSBuild.

Build systems remove the dependency on an IDE to build and run code, which means everyone can use the editor they want as long as they use the same build system.
Most IDEs can use build system code as the base for their own configuration. For instance, the file above can be used as-is by Visual Studio to configure a project.

Build systems enable developers to build, run, and check their code anywhere. But it has to be somewhere, so which machine or machines should they use?
Once again, using a developer's specific machine is not a good idea because developers customize their machine according to their personal preferences.
The machines developers use may not be representatives of the machines the software will actually run on when used by customers.

Just as we defined builds using code through a build system, we can define environments using code!
Here is an example of environment definition code for the Docker container system, which you do not need to understand:

```
FROM node:12-alpine
RUN apk add python g++ make
COPY . .
RUN yarn install
CMD ["node", "src/index.js"]
EXPOSE 3000
```

This code tells Docker to use the `node:12-alpine` base environment, which has Node.js preinstalled on an Alpine Linux environment.
Then, Docker should run `apk add` to install specific packages, including `make`, a build system.
Docker should then copy the current directory inside of the container, and run `yarn install` to invoke Node.js's `yarn` build system to pre-install dependencies.
The file also tells Docker the command to run when starting this environment and the HTTP port to expose to the outside world.

Defining an environment using code enables developers to run and test their code in specific environments that can be customized to match customers' environments.
Developers can also define multiple environments, for instance to ensure their software can run on different operating systems, or on operating systems in different languages.

We have been using the term "machine" to refer to the environment code runs in, but in practice it's unlikely to be a physical machine as this would be inefficient and costly.
Pull requests and pushes happen fairly rarely given that modern computers can do billions of operations per second. Provisioning one machine exclusively for one project would be a waste.

Instead, automated builds use _virtual machines_ or _containers_.
A virtual machine is a program that emulates an entire machine inside it. For instance, one can run an Ubuntu virtual machine on Windows.
From Windows's perspective, the virtual machine is just another program. But for programs running within the virtual machine, it looks like they are running on real hardware.
This enables partitioning resources: a single physical machine can run many virtual machines, especially if the virtual machines are not all busy at the same time.
It also isolates the programs running inside the virtual machine, meaning that even if they attempt to break the operating system, the world outside of the virtual machine is not affected.
However, virtual machines have overhead, especially when running many of them.
Even if 100 virtual machines all run the exact same version of Windows, for instance, they must all run an entire separate instance of Windows including the Windows kernel.
This is where _containers_ come in. Containers are a lightweight form of virtual machines that share the host operating system's kernel instead of including their own.
Thus, there is less duplication of resources, at the cost of less isolation.
Typically, services that allow anyone to upload code will use virtual machines to isolate it as much as possible, whereas private services can use containers since they trust the code they run.

Using build systems and virtual machines to automatically compile, run, and check code whenever a developer pushes commits is called _continuous integration_,
and it is a key technique in modern software development.
When a developer opens a pull request, continuous integration can run whatever checks have been configured, such as testing that the code compiles and passes some static analysis.
Merging can then be blocked unless continuous integration succeeds.
Thus, nobody can accidentally merge broken code into the main branch, and developers who review pull requests don't need to manually check that the code works.

Importantly, whether a specific continuous integration run succeeds or fails means that there exists a machine on which the code succeeds or fails.
It is possible that code works fine on the machine of the developer who wrote it, yet fails in continuous integration.
A common response to this is "but it works on my machine!", but that is irrelevant. The goal of software is not to work on the developer's machine but to work for users.

Problems with continuous integration typically stem from differences between developers' machines and the virtual machines configured for continuous integration.
For instance, a developer may be testing a phone app on their own phone, with a test case of "open the 'create item' page and click the 'no' button", which they can do fine.
But their continuous integration environment may be set up with a phone emulator that has a small screen with few pixels, and the way the app is written means
the 'no' button is not visible:

<p align="center"><img alt="Illustration of the phones example." src="images/phones.svg" width="50%" /></p>

The code thus does not work in the continuous integration environment, not because of a problem with continuous integration, but because the code does not work on some phones.
The developer should fix the code so that the "No" button is always visible, perhaps below the "Yes" button with a scroll bar if necessary.

---

#### Exercise: Add continuous integration
Go back to the GitHub repository you created, and add some continuous integration!
GitHub includes a continuous integration service called GitHub Actions, which is free for basic use.
Here is a basic file you can use, which should be named `.github/workflows/example.yml`:
```yaml
on: push
jobs:
  example:
    runs-on: ubuntu-latest
      steps:
        - uses: actions/checkout@v3
        - run: echo "Hello!"
```
After pushing this file to the GitHub repository and waiting for a few seconds, you should see a yellow circle next to the commit indicating GitHub Actions is running,
which you can also see in the "Actions" tab of the repository.
This is a very basic action that only clones the repository and prints text. In a real-world scenario, you would at least invoke a build system.
GitHub Actions is quite powerful, as you can read on [the GitHub Actions documentation](https://docs.github.com/en/actions).

---

Version control, continuous integration, and other such tasks were typically called "operations", and were done by a separate team from the "development" team.
However, nowadays, these concepts have combined into "DevOps", in which the same team does both, which makes it easier for developers to configure exactly the operations they want.


## Summary

In this lecture, you learned:
- Version control systems, and the differences between 1st, 2nd, and 3rd generation
- Git: how to use it for basic scenarios, and how to write good commit messages
- Continuous integration: build systems, virtual machines, and containers

You can now check out the [exercises](exercises/)!
