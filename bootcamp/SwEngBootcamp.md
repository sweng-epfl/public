# Bootcamp - Intro to Java, Git, and testing

This bootcamp will show you how to get started with the tools you will need for this course.
You will learn about Git, Gradle and Travis CI.

Please use your own laptop for the bootcamp (not on the BC machines and not in a remote VM).

The lab session is a great opportunity to ask the teaching assistants about any problems you have with the bootcamp.
Thus, we encourage you to start it today, and ask your questions at the lab.

Now let's dive right in.


## Install Java, and validate your setup

You should use Java 12.
Download and install Java SE 12 from [the Oracle website](https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html).
For detailed installation look at [the Oracle documentation](https://docs.oracle.com/en/java/javase/12/install/overview-jdk-installation.html).

> :information_source: **On Ubuntu 19.04 or newer** (check your version with `lsb_release -a`), you can `sudo apt install openjdk-12-jdk`; this package is unfortunately not available for older Ubuntus.

> :information_source: **On Ubuntu, Debian or RHEL**, it may be easier to use Azul's OpenJDK via your package manager, see instructions [here](https://www.linuxuprising.com/2019/04/install-latest-openjdk-12-11-or-8-in.html).

> :warning: **On Windows**, you need to set the `JAVA_HOME` system variable and update the `PATH` variable.
> Find the advanced system settings.
> You can use the search box for that and type **View advanced system settings**.
> Select the **Advanced** tab, click **Environment Variables**.
> In System variables, add a new `JAVA_HOME` variable and point it to the JDK installation folder, which should look something like `C:\Program Files\Java\jdk-12.0.1`.
> In System variables again, find PATH, click edit and append `%JAVA_HOME%\bin` at the end.
> Make sure both variables are in "System" variables and not "User" variables.
> Then close any open command line windows, to make sure the changes are taken into account.

> :information_source: If you already had another version of Java installed, you may need to uninstall it to make sure you are using Java 12.
> **On Windows**, you may need to remove `C:\ProgramData\Oracle\Java\javapath` from your PATH environment variable.

To validate your installation, run `java -version` from the command line.
You are ready to go if the command outputs something like this:

```sh
$ java -version
java version "12.0.2"
Java(TM) SE Runtime Environment (build 12)
Java HotSpot(TM) 64-Bit Server VM (build 23.2-b04, mixed mode)
```

Do not worry if the minor version number or build number are not exactly the same, but **the major version number must be 12, not older or newer**.

A command line, a text editor, and a working Java environment should be sufficient enough for this part of the bootcamp.
We do not ask you to install an integrated development environment (IDE).
You may do so if you wish; Android Studio is a good pick, as you will use it in SDP next semester, and it can be used for any kind of Java development.


## Create a Hello World program, compile it, and run it

Let's start with a simple Hello World program.
We will then use it as a base to learn Git.

First, create a project folder where you will store your Java program.
For instance, you can name your folder `Bootcamp`.

Here is a program ready to use:

```java
public class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello, world!");
  }
}
```

Create a file `HelloWorld.java`, and paste the above snippet into it.
Open a command line and `cd` to where your project is.
Compile your program with `javac HelloWorld.java`, then run it with `java HelloWorld`.

You should get a `Hello, world!` message.
If this does not happen, please double-check the steps you performed, and ask a TA if you are stuck.


## Set up Git, and create a repository

Let's save a snapshot of the program, so that you can get back to this stage if you make a mistake.
You will use Git throughout the course for version control, including exams.

Git is a version control system for tracking changes in files and coordinating work on those files among multiple people.
It is primarily used for source code management in software development, but it can be used to keep track of changes in any set of files.

First, you need an account on [GitHub](https://github.com), the site that will host our code during this course.
If you don't already have a GitHub account, [here](https://github.com/join) are instructions on how to create one.
Using your real name in your GitHub profile will make debugging easier for the staff in case you run into issues with the class, but this is not required.

**Before going further, please [fill out this form](https://forms.gle/EesHVbRTMrRzA4yt7).**
We need this information to associate your GitHub account with your EPFL identity.

You then need to install Git if you do not already have it.

> :information_source: **On macOS or Linux**, Git may already be preinstalled on your system. You can check this by running `git --version`.

> :information_source: **On Windows**, when installing Git, select "Use Git from the Windows Command Prompt" when asked about adjusting your PATH; if you choose one of the other options, whenever this bootcamp talks about "a command line", open Git Bash instead.

> :information_source: If you are asked to generate a new SSH key and add it to the ssh-agent, please follow [GitHub's instructions](https://help.github.com/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent/).

GitHub provides [setup instructions](https://help.github.com/articles/set-up-git/) to install Git on your computer.

> :warning: In the next step, when doing a "git commit", you will probably open [vim](https://www.vim.org/), a command-line editor that is somewhat hard to use.
> To be able to write content, press `i`. This will enable the editing mode. You can write your commit message. Once your are done with it, press `ESCAPE`, write `:x` and then press `ENTER` to save your changes and quit.
> Alternatively, you can change the editor Git uses by doing `git config --global core.editor <editor>` where on Linux and Mac you might use `nano` for the editor, and on Windows you might use `notepad`.

Now open a command line and cd to where your program is; this folder should have a file named `HelloWorld.java`.

To track your program using Git, execute the following commands:

```sh
git init   # Initialize a new repository
git add .  # Add all the files to the repository
git commit # Create a first commit
```

The commit you just created contains all of the changes you made; since this is your first commit, those changes are the creation of files.


## Publish your repository on GitHub

You will now publish your code.
Create a **public** repository on GitHub.
You can name it as you please.
If GitHub offers you to initialize the repository with a README, don't do it.

Publishing code on GitHub requires two steps:
1. Add GitHub as a "remote" to your repository, using the following command: `git remote add origin https://github.com/your-github-username/your-repository-name.git`
2. Push your commits to GitHub: `git push -u origin master`

> :information_source: If you make a mistake in the URL when adding the remote, you can change it with `git remote set-url origin <the correct URL>`

The `git push` command is used to upload local repository content to a remote repository.
Pushing is how you transfer commits from your local repository to a remote repository.
Here, `origin` corresponds to the remote repository you declared using the `git remote add origin <url>` command.
You can have multiple remote repositories, with different names.

> :information_source: You will have to authenticate using your github username and password when you want to interact with the remote.
> By default, Git will "remember" your credentials for 15 minutes - you can change this time using `git config credential.helper cache <timeout in seconds>`.
> Alternatively, you can use a SSH key pair to commit.
> In that case, you'll need to add your public key to your Github account, and replace the remote url with `git@github.com:your-github-username/your-repository-name.git`.
> Take a look at [GitHub's help page](https://help.github.com/articles/connecting-to-github-with-ssh/) for more details.


## Adding and updating files

To demonstrate the power of Git, let's apply some changes to the hello world program.
Get rid of the comma and the exclamation mark from the printed message.

Modify your program so that it prints **Hello world**.
Compile your program and run it to validate your changes.

Add the file you just changed: (you can also add any generated file)

```sh
git add <file>
```

The `git add` command adds a change in the working directory to the staging area.
It tells Git that you want to include updates to a particular file in the next commit.
However, `git add` doesn't affect the repository by itself; changes are not actually recorded until you commit them.

Commit the changes you just made to your local repository:

```sh
git commit # You can write the commit message inline with the parameter "-m <your commit message>"
```

Commits are like checkpoints you set for yourself.
It is a good practice to keep each commit as small and to the point as possible.
This is especially important when working on a team, since team members often need to look at what their teammates changed.

When it comes to commit messages, beginners (and non-beginners) tend to write anything they have in mind just to get rid of them.
It is actually quite a challenge to write a good commit message.
There is no absolute rule, but there are several guidelines on the internet, such as [Chris Beams' post](https://chris.beams.io/posts/git-commit/) on how to write a Git commit message.

Keep in mind his `Seven rules of a great Git commit message`:

1. Separate subject from body with a blank line
2. Limit the subject line to 50 characters
3. Capitalize the subject line
4. Do not end the subject line with a period
5. Use the imperative mood in the subject line
6. Wrap the body at 72 characters
7. Use the body to explain *what* and *why* vs. *how*


You can now push your commit to your remote repository:
```sh
git push # You only need "-u origin master" the first time you are pushing
```


## Ignoring files

Git sees every file in your working copy as one of three things:

1. tracked - a file which has been previously staged or committed;
2. untracked - a file which has not been staged or committed; or
3. ignored - a file which Git has been explicitly told to ignore.

Ignored files are usually build artifacts and machine generated files that can be derived from your repository source or should otherwise not be committed.
In the particular case of this part of the bootcamp, these generated files are the **.class** files.

Git uses a special file named **.gitignore** to list all the ignored files.
Note that this is not a file extension; the name must be exactly **.gitignore** and nothing else.
It should be at the root of your repository.
There is no explicit ignore command: you must edit the **.gitignore** file by hand when you have new files that you wish to ignore.

Note that unlike the files it ignores the **.gitignore** file itself must be tracked.

Create a **.gitignore** file at the root of your repository, and write the following in it:

```
*.class
```

This line tells Git to ignore any files whose name ends with **.class**.

You can now add the `.gitignore` file, commit that change, and push it to the remote repository.

If you look into your remote repository on GitHub, you will notice that the **.class** are still there.
You cannot force a file that is already committed in the repository to be removed just because it is added to the **.gitignore**.
To get rid of them, run ``git rm --cached <file>``.
Here, the `--cached` argument tells Git to stop tracking your file while keeping it in the actual filesystem.
Omitting this argument will cause the file to be removed from the filesystem as well.

Remove the generated files with this command.
Add the changes, commit, and push.

These generated files should have disappeared from your remote repository.

Once these files are removed, compile your program again.
The generated files are back.
Run `git status`, which prints your current status, including files that have changed since your last commit and files that are not currently tracked by your repository.
You should see that there is nothing to commit, which means that **.class** files are indeed ignored.

Usually, your typical `.gitignore` file will exclude all build artifacts (**.class**, **.jar**, **.so**, ...), as well as project dependencies that you manage via a specific build tool (SBT, Gradle, NPM, ...), local IDE configuration (**.eclipse**, **.idea**, **project.iml**, ...) and, most important if your repository is public, **secrets** (**NEVER** upload a private key or API secret on a public facing git repository - thousands of people do that and get hacked, even by automatic bots that look for API keys on GitHub).

> :information_source: Most of the huge libraries and frameworks (such as Android) provide typical `.gitignore` files you can use in your projects.
> Github maintains a collection of such files on [this repo](https://github.com/github/gitignore), feel free to use them!

## Working with multiple Git copies

Git is a useful tool to keep track of your changes, but it's also very powerful when working in teams, to synchronize changes in the code among team members.

As you might have guessed already, a Git repository can have many copies.
Up until now, your repository had two copies: the one on your computer, and the one hosted by GitHub.
When you start working in teams, each member of the team will have a copy of the repository on their computer.
But how can one synchronize their own copy with, for example, the remote?
And what happens when you try to push new commits to a remote without having first synchronized your local copy with that remote?

To simulate the effect of you working with multiple people, you will host two copies of the same repository on your computer.
Open a second command line in another directory (**NOT** inside your git repository).
There, clone your github repository using `git clone https://github.com/your-github-username/your-repository-name.git`.
Git will create a directory with the name of your repository, containing everything you pushed so far.
If you go inside, you will see that git was initialized as well, and `git remote` will show you that your GitHub repository has already been added as the `origin` remote.
We will now refer to the directory just created as **the second copy**, while the directory in which you worked earlier will be called **the first copy**.

### Getting commits on remote with `git pull`

First, let's try to change some parts of the code.
Go to the **second copy** and update your `HelloWorld.java` file.
For example, make it say `Hello, SwEng!` instead of `Hello World`.
Save the file then commit and push your changes (refer to the begining of the bootcamp if you can't remember how to do it).
Then, go to the **first copy**.
As you didn't ask anything to Git, the changes you just made are not yet reflected!
To retrieve the changes, open the command line and run `git pull`.
You will now see that the changes you made to the second copy are present in the first one as well.

The `git pull` command works in a similar fashion than the `git push` command.
If you previously ran `git push -u <remote> <branch>`, then you can call `git pull` with no argument.
In the other case, you have to specify the remote and branch using `git pull <remote> <branch>`. Note that when you cloned your repository to create the second copy, Git already linked `origin master` as the default remote and branch for the `master` branch, so you can run `git pull` and `git push` directly.


### Merging different copies

Now, let's see what can happen if you change the code on the two copies _at the same time_.
Let's say we want to compute the answer to the [ultimate question of life, the universe, and everything](https://en.wikipedia.org/wiki/Phrases_from_The_Hitchhiker%27s_Guide_to_the_Galaxy#Answer_to_the_Ultimate_Question_of_Life,_the_Universe,_and_Everything_(42)).

First, open the **first copy** and create the following method:

```java
public static int computeUltimateAnswer() {
	return 6 * 7;
}
```

Inside the `main` method, you also want to display that result, so add in the following code after your first print:

```java
System.out.println("The answer to the ultimate question of Life, The Universe, and Everything is: " + computeUltimateAnswer());
```

You can now commit and push your updates. Make sure that it has been pushed correctly by checking that you can see your changes on GitHub.

Now, go to the **second copy** and try to compute the question, using the following method:

```java
public static String computeUltimateQuestion() {
	return "still searching...";
}
```

It is **normal** that the `computeUltimateAnswer()` method is not present yet, as you didn't pull.
That's expected, as we want you to see what happens when you do this kind of thing.

You will indeed want to display the (non) question as well, so add the code after your `Hello World!` print:

```java
System.out.println("The ultimate question of Life, The Universe, and Everything is: " + computeUltimateQuestion());
```

Again, commit your code, and _try_ to push it: Git should not allow you to push your changes.
It is normal.
Internally, Git maintains a tree with all the commits of the repository.
Thus, each commit you create has a **parent commit**, the latest commit you had before commiting.
If you try to push a commit that has the same parent commit as a commit that is already on the remote, this will not work, as it would split the tree in two.
To avoid this situation, we will have to merge the changes on the remote and the local changes, to create a new commit combining them, and having two parents.

> :information_source: You can see the history of commits on your copy using `git log`.
> `git log --parents` will also show the hash of the parent commits of each commit in the history!
> You will indeed see that merge commits have two parents, and that normal commits only have one, usually the commit just before them.

When you try to pull commits from the remote while already having locally unpublished commits, git will automatically try to merge them. Run `git pull` to see what happens.

There is a chance here that you'll get a **merge conflict**.
This means that a given file was modified in both versions you're trying to merge.
As a result, Git doesn't know which changes to keep and which changes to remove, and you'll have to tell it what to do.
There are powerful tools out there that can help you resolve merge conflicts, but here we will use a simple text editor.
When you have a conflict, Git will list all the files that couln't be merged automatically.
Here, only `HelloWorld.java` should be listed.
Open it in your favourite text editor to see what happened.
In every place where there is a conflict, you will see something like this:

```
<<<<<<< HEAD

All the code you changed on your local version

=======

All the code that changed on the remote version

>>>>>>> da110d39a9f4494805b6eb5ce1e89ec57d947b04
```

> :warning: The hexadecimal number at the bottom is the hash of the latest remote commit, so don't expect it to be the same as the one we provided in this example.

What you have to do now is to update the files to get them to be exactly what you want them to be.
Here, as you want to keep both changes, you can just remove the `<<<<<<< HEAD`, `=======` and `>>>>>>> da110d39a9f4494805b6eb5ce1e89ec57d947b04` parts.
In other situations, it might be a bit trickier, and dedicated software (sometimes directly included in your IDE) will help you sort it out.

Once everything sounds good to you, run `git add`, `git commit` and `git push` as usual.
You will notice that the `git commit` message is pre-filled with an automatic merge message.
It is good practice to leave it as-is.

> :information_source: Even though you can freely update all the files while merging, it's not a good idea to edit them more than needed to complete the merge.
> Any change you want to make to the code besides from merging should be done in a new commit.
> Otherwise, the commit will confuse anyone looking at it, since they will not expect unrelated changes in a merge commit.

> :information_source: For trivial merges, you will sometimes see your editor directly when running `git pull`.
> This means that the merge didn't require human intervention, and Git just generated the merge commit for you.
> You just have to accept to finish the merge.


### Branches

In huge projects, having everyone work on the same code becomes problematic because their changes will often conflict.
This is where _branches_ come to the rescue.
When you want to develop a new feature, you will create a feature branch, and work there.
When your work is done, you will merge it into master, or open a **pull request** for your changes to be reviewed by members of your team before being approved for merge.
You can learn more about this concept [here](https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow).
There are also even more separated variants, like the [git flow](https://nvie.com/posts/a-successful-git-branching-model/) model, but we will not discuss them here.
Let's just see how we can create and switch between branches.
For this part, we won't need to use two copies, so you can remove one of them if you want.

![The git flow model](flow.png)

_The git flow model - [nvie](https://twitter.com/nvie)_

Let's begin with the creation of the branch.
Try to find a good name for it, usually a 2-3 words description of what you're trying to do.
If you want to implement [The Guide](https://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy_(fictional)) in your program, `feature/the-guide` is a decent name.
To create it, run `git checkout -b feature/the-guide`.
This will create a new branch and switch to it.
Everything you commit will now be on that branch.

To push the branch to the `origin` remote, you will have to run `git push -u origin feature/the-guide`.
Again, the `-u origin feature/the-guide` is only needed the first time you want to push your branch.

If you want to go to another branch, type `git checkout <branch name>`.

When your work is done, you will want to make your changes appear in the main `master` branch.
For that, you have a few options.

The first one is to simply merge the branch.
To do that, go to the `master` branch and run `git merge <the branch you want to merge>`.

The second option is to open a pull request on GitHub.
To do so, follow [GitHub's instructions](https://help.github.com/en/articles/creating-a-pull-request#creating-the-pull-request).
Then go to your repository page and click the `Pull Requests` button.
You will then be able to accept it, which performs the merge for you.
The main advantage of this technique is that opening a pull request (or **PR** for short) opens a discussion in which everyone can review the changes and provide feedback.


## Git Wrap-Up

![In case of fire](fire.png)

_In case of fire - [Louis Michel Couture](https://twitter.com/louim)_

Git is the most widely used version control system, so there is plenty of documentation on the internet.
We have given you a quick intro about how it works, but if you want to learn more, feel free to explore the following links:

- The [Git documentation](https://git-scm.com/doc): a very comprehensive documentation of the software. A web version of the `man` pages of git that you can also get by typing `git help`.
- [git - the simple guide](https://rogerdudler.github.io/git-guide/): a short tutorial to get your hands on git, covers the content of this bootstrap with a few more insights about the internals of git.
- [Commit Often, Perfect Later, Publish Once: Git Best Practices](https://sethrobertson.github.io/GitBestPractices/): an article to help you not only use git correctly, but use it efficiently

A final note: as with all tools, you'll get most of your experience in using git by... actually using it.
There are a lot of very powerful features you will find handy in some situations.
If you want to do something a bit tricky, like identify which commit introduced a particular bug or regression, don't hesitate and look up in the documentation and try to hack!

> :information_source: If you want to use an advanced Git feature but are not sure about what it could do, create a copy of the entire repository folder first!
> All of the information Git stores is in a folder named `.git` in your repository, thus your folder copy will behave exactly as the original one, and you can switch to it if you accidentally break the original one.


## The Gradle build tool

When working in big projects with lots of modules and dependencies, we often use **build tools**.
They handle, among other things, managing dependencies, compiling, packaging and running our tests for us.
In this course, we will use [Gradle](https://gradle.org/).

Since we will be using Gradle in the exams, we will now quickly introduce the tool.
You will have the chance to use it more in depth if you take SDP next semester.


### Installing Gradle

Before continuing, you will need to download and install gradle.
It is possible that you already have it installed, so try running in a terminal

```sh
$ gradle -v
```

If the command doesn't work, you need to install it.
Refer to the [gradle installation guide](https://gradle.org/install/) for the full process.
Feel free to request help from a TA if you encounter a problem in the process.

After the installation, you should get something like this:

```sh
$ gradle -v

... some welcome text ...

------------------------------------------------------------
Gradle 5.6.2
------------------------------------------------------------

... and some more details ...
```

Make sure you at least have Gradle **5.6.x** or Java 12 compilation will **NOT WORK**!


### Generating a configuration

To generate a basic Gradle configuration, go to your project directory and run

```sh
$ gradle init --type java-application
```

Gradle will ask you to choose some parameters.
Pick the `Groovy` build script (default), the `JUnit Jupiter` testing framework (not default!), and whichever project name and source package you'd like.

After a few seconds, a few files should have been added to your project, namely:

- `build.gradle`, the file in which you will describe your project
- `settings.gradle`, a file that contains some parameters related to the build
- `gradlew.bat` and `gradlew`, two scripts (respectively for Windows and macOS/Linux) that allow you to run gradle from a machine even if it's not installed
- `gradle`, a folder holding some files used by gradle internally
- `src`, the folder that will hold your source files

You can open the `build.gradle` file to see what it looks like.
Read the comments, as they provide a very useful insight about what all this stuff does.
The parts you will likely use the most in your future projects are the following:

- `dependencies`, where you specify external libraries to use in your project.
  Gradle will then ensure that they are in the classpath when compiling and/or running tests, depending on what scope you selected
  Here, Gradle included the package `guava` in version `28.0-jre` from `com.google.guava`.
  Its scope is `implementation`, so it will be in scope when compiling and testing.
  It should however be distributed with your app in order for it to work.
  Gradle also included `junit-jupiter-api` with scope `testImplementation`, meaning it will only be used when compiling and running tests, but won't be available for classes that are not in the `test` directory.
  Finally, Gradle included `junit-jupiter-engine` with scope `testRuntimeOnly`, meaning it will be used when running tests but is not available when compiling them.
- `repositories`, where you specify the servers from which the dependencies specified earlier should be downloaded.
  `jcenter()` will cover most of your needs, and Gradle included it for you.

When using Gradle, you no longer put your source files at the root directory. Instead, you have to follow a hierarchy:
- `src/`
  - `main/` for the non-test code
    - `java/` for the code written in Java
      - your source files and package directories go here
    - `resources/` for the resources used by the code
      - your resource files (e.g. images, config files, ...) go here
  - `test/` for the tests
    - `java/` for the tests written in Java
      - your test files and package directiries go here
    - `resources/` for the resources used by the tests
      - your resource files (e.g. images, config files, ...) go here


### Building your project

To help you get started, Gradle generated a pretty Hello World in `src/main/java/App.java`. To compile it, you can run

```sh
$ gradle build
```

After some time, the directory `build` will appear.
You can go inside the `build/classes/java/main/` folder.
In this folder, there is a folder for the package name you chose when creating the project, which itself contains your `App.class` file.

> :information_source: Conveniently, `gradle build` also ran your tests; we will talk about testing in a further lecture.

You can run the `App.class` file by running `java packagename.App`, where `packagename` is your package name, **while in the `build/classes/java/main/` folder and not the package folder**.

However, running class files by hand is not very efficient. Instead, you can run

```sh
$ gradle run
```

This command will compile and run your app for you.

That's all you need to know for now about Gradle.
You can either remove your `HelloWorld.java` or move it to the `src/main/java` folder to have it compiled on the next `gradle build`.

It's about the right time to ignore some files, and to commit the rest! Open your `.gitignore` and add in the following:

```
build/*
.gradle/*
```

You can then commit and push the rest of your files, including the **gradle/** directory (without the `.`)


## Continuous Integration with Travis CI

Travis CI is what we call a **continuous integration** service.
It's a website that hooks onto your GitHub repository and, on every commit, tries to build your project and test it to see if everything goes well.
It can also be used to produce and distribute binary packages.

Continuous Integration is a central part in many development workflows, as it runs tests on every single commit pushed on the repository, meaning that even if the developper forgot to run the tests or to build the project before pushing, the CI will do and catch easy mistakes.
CI also runs on pull requests, meaning you know if the PR will break your code or if you can merge it safely.

During the course, we will use [Travis](https://travis-ci.org/).
There are two versions of Travis:

- [travis-ci.org](https://travis-ci.org/), for public git repositories
- [travis-ci.com](https://travis-ci.com/), for private git repositories

Both work the exact same way, but the second one requires you to pay, or to have an active [GitHub Student Developer Pack](https://education.github.com/pack), which you can get for free as a student.

First, you'll need to sign up on Travis, using your GitHub account.
Authorize Travis CI to access your GitHub account when requested, then select the repository you want to use with Travis CI (use the toggle button).
Once selected, click on your bootcamp repository: you will get to a build dashboard for your repositories.

> :information_source: If you have already used Travis before, you may need to go to your profile settings (click on the arrow next to your picture in the top right, then on Settings) and sync your GitHub repositories using the "Sync account" button in the top right.


### Setting up your build

To know what to do, Travis will need a configuration file.
At the root of your project, create a file named `.travis.yml`, containing the following:

```yaml
language: java
jdk: openjdk12 # by default, travis runs on Java 8. This forces Java 12.
before_install:
  - chmod +x gradlew # make sure the Gradle wrapper can be executed
```

Add the file to git, commit it, and push it.
On the Travis dashboard, a build should have appeared.
After some time, you'll be able to see the console and the build logs.
And, after more time, the build will either succeed or fail.
If it fails, you can check the logs to see what went wrong and fix it.

> :information_source: If the build fails because 'ant' is currently not installed, your project is not at the root of your repository, but in some folder.
> As a consequence, Travis does not find your Gradle script.
> You can either move your whole project at the root of your repository or add `- cd <your project folder>` as a new line just after `before_install:` in your `.travis.yml`

> :information_source: If the build fails with `failed to read class` at phase `:test`, make sure you run an up to date version of Gradle!
> If that is not the case, go to your project directory and run `gradle wrapper --gradle-version=5.6.2` to install the latest version of the wrapper to your repository.
> You can then use `./gradlew` instead of your local `gradle`, but it is still strongly advised to update your system's gradle version.

If you cannot get your Travis build to pass, please ask a TA.

That's it!
Your first build is up and running on Travis!
Congrats, and good luck for the rest of the semester!
