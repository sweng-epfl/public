# Guide to GitHub

This guide is a quick introduction to GitHub's main features.

It assumes that you already know how to create a GitHub repository, and how to use Git's main features.
If you need to refresh your memory, the [Software Engineering mini-project part 3](https://github.com/sweng-epfl/public/blob/master/project/03-Operations/Handout.md) contains a good description.

GitHub has many features we will not need in SDP, such as automatically moving items within projects based on actions done in the corresponding issues or pull requests.
You may find [their documentation](https://docs.github.com/en/github) interesting to discover advanced features.


## Issues

Issues are a way to track work.
Each issue has a name, a description, and other features such as labels.
Anyone can comment on the issue, which is a convenient way to thread discussions.

You can **assign** an issue to one or more people, indicating that these people are responsible for the issue.
We will use this extensively in SDP, to mark who has to do what work.

**Labels** are a way to annotate issues with predefined categories.
You can assign labels to issues in order to filter them later, such as "bugs" or "new features".

In SDP, you will use labels to track the time you expect to spend on an issue, as well as the time you actually spend on it.
This allows you to improve your estimations over time. We suggest you create the following labels, which make it easy to divide work in equal parts:
- "estimated: 2h", "estimated: 3h", "estimated: 5h", "estimated: 8h"
- "actual: 1h", "actual: 2h", "actual: 3h", "actual: 5h", "actual: 8h", "actual: 13h", "actual: 21h"


## Projects

Projects are a visual way to organize issues in groups. You will use one project in SDP to represent all work on your team's application.

GitHub Projects are a list of columns, each of which contains two kinds of items: notes and issues.

All items you create from the projects interface start as notes, but you can then convert them to issues, which is necessary for anything more than a quick note.


## Branches

In SDP, you will use git branches extensively.

To keep your repository manageable, name branches well and avoid keeping branches around any longer than they need to.
For instance, `Alice-patch-1` or `Bob-bugfix` are not good branch names, as they make it hard to understand what they are about.
Better names could be `feature/map-itinerary` and `bugfix/empty-favorites-list`.
As soon as a branch has been merged, delete it. You can [configure GitHub](https://docs.github.com/en/github/administering-a-repository/managing-the-automatic-deletion-of-branches) to do this automatically.

In addition to minimizing conflicts, branches can be _protected_ in GitHub to prevent any direct push to the branch, requiring team members to go through the pull request workflow instead.
In SDP, we will use these protections to avoid accidentally merging code that is not ready.

Branches can also be used to keep old versions of a codebase around, such as maintaining v1.0 of your software for some customers even when v2.0 is the latest version, but this won't be necessary in SDP.


## Pull requests

Pull requests are the way you will merge your code into your team's codebase in SDP.

Instead of allowing anyone to change the codebase at any time, pull requests allow the team to know who wants to change what how.

You can speed up reviewing by assigning teammates as reviewers after you have opened a pull request, using the "Reviewers" list in the pull request page.

To get good feedback on a pull request, make sure the code you push is clean and readable; for instance, do not leave commented out lines of code you used for debugging, and use Android Studio's code formatting tools to make everything consistent.
Otherwise, your teammates will have to give you feedback on low-level issues such as formatting, instead of high-level code design issues.

Remember to write good commit messages, respecting the 5 rules we taught you:

1. Separate subject from body with a blank line.
2. Limit the subject line to 50 characters.
3. Use the imperative mood in the subject line.
4. Wrap the message body at 72 characters.
5. Use the message body to explain _what_ and _why_ not _how_.

Please refer to [GitHub's documentation](https://docs.github.com/en/github/collaborating-with-issues-and-pull-requests/reviewing-proposed-changes-in-a-pull-request) to learn how to review a pull request.

GitHub also lets you create "draft" pull requests, which cannot be merged, as a way to show others what you're doing and get early feedback on important design decisions.


## Pull request checks

To ensure that you do not accidentally merge code that does not work, or that only works on your machine but not on anyone else's, you can add checks for pull requests.

These commonly take the form of a server cloning your branch and running your tests.
The main difference is that these servers typically do not have graphical outputs, and may have some different configuration than on your machine, such as the Android emulator's screen size.

Another common form of checks is static analysis, which looks for bugs in your code without running it, for patterns that could indicate bugs, or for coding style issues.

As part of branch protection, you can enforce that some checks must pass before a pull request can be merged.
