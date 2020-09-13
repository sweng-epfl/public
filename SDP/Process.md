# The SDP Development Process

In SDP, you will use a **GitHub repository** as your navigation panel for the semester: issues, project board, and code.
The repository will allow you to track your progress and to communicate with your team and with the SDP staff.

This document will teach you how to manage your team's GitHub repository according to SDP's variant of the [Scrum development process](https://www.scrumguides.org/).
Scrum is all about minimalism in processes, since it was inspired by the [Agile manifesto](https://agilemanifesto.org/).

Getting familiar with the basics explained here will help you deliver better software faster.


## 0. Reminders

SDP is 4 credits, and each credit corresponds to 25-30h of work during the semester, thus 4 credits divided by 14 weeks is around 8 hours of work per person per week.
See the [EPFL FAQs](https://www.epfl.ch/education/bachelor/study-programs-structure/faqs/) for more information.

In SDP, your team is what Scrum calls the "Development Team", and you share the role of "Product Owner" with your coaches since you both can decide what to include in the app.
While this is not always realistic, it means that this semester you make sure to work on something you care about rather than merely implementing features you are told to.

Scrum also has the concept of "Scrum Master", one person who is in charge of helping the team work smoothly.
In SDP, you will rotate this role among team members.


## 1. Issues

Issues are a way to track work.
Each issue has a name, a description, and other features such as labels.
Anyone can comment on the issue, which is a convenient way to thread discussions.

### Assignment

You can assign an issue to one or more people, indicating that these people are responsible for the issue.
We will use this extensively in SDP, to mark who has to do what work.

### Labels

Labels are a way to annotate issues with predefined categories.
When you create a repository, GitHub automatically creates a bunch of labels for you.
For simplicity, we suggest you use the following labels for issue types:
- "New feature" for issues related to adding new functionality to the application
- "Enhancement" for issues related to improving the user experience of existing features
- "Bug" for issues related to fixing defects in the application

In a real project, some other labels might be necessary, such as "good first issue" to help orient newcomers, or "won't fix" for issues that are valid but out of scope.
However, you won't need these in SDP, since your team will be the only one working on your project.

Another use of labels is to track the time you expect to spend on an issue, as well as the time you actually did spend on it.
This allows you to improve your estimations over time. We suggest you create the following labels:
- "estimated: 1h", "estimated: 2h", "estimated: 3h", "estimated: 5h", "estimated: 8h"
- "actual: 1h", "actual: 2h", "actual: 3h", "actual: 5h", "actual: 8h", "actual: 13h", "actual: 21h"

These numbers follow the Fibonacci sequence, which is convenient for dividing work among people since you can add up issues to ensure everybody has the same amount of work.

When creating an issue, you will assign someone to it, add a label for the issue type, and add a label for the estimated time.
When closing an issue, add a label for the actual time it took.

### Milestones

Another useful feature of issues is milestones: you can group issues together and assign some date at which they should be completed.

However, we will not use milestones in SDP, but instead a more powerful tool: projects.


## 2. Projects

Projects are a visual way to organize issues in groups. You will use one project in SDP to represent all work on your team's application.

GitHub Projects are a list of columns, each of which contains two kinds of items: notes and issues.
To keep it simple, we will only use issues in SDP, so when adding something to the project, always finish by clicking on the three dots and then on "Convert to issue".

Note that GitHub Projects include automation tools, which can automatically trigger actions based on events such as code being merged.
We will not use them in SDP, but we encourage you to check them out.

Create one project called "Scrum Board" and create the following columns:
- "Product Backlog"
- "Sprint backlog"
- "Sprint tasks"
- "Done in Sprint 1"

During the semester, you will create a "Done in sprint N" for every new week.

### Product Backlog

The Product Backlog contains **User Stories**.
User Stories are descriptions of a software feature from the point of view of an user.
In SDP, the format for a User Story is _"As a [role], I want to [action], so that [reason]"_.
For instance, "As a TA, I want to write tutorials, so that I can help my teams".
All three parts are important: the role indicates who the user is and what you can assume about their background, the action is a user-centric view of the feature, and the reason provides context.
If you cannot find a role or reason for a User Story, or if you have to use very generic ones such as "As a user", the feature you're thinking of may not be something users actually care about.

The Product Backlog contains _all_ User Stories that the customers can come up with, ordered by priority.
Since the Product Backlog should be always sorted, having too many User Stories is never a problem, as the team will only implement the highest-priority ones. Some User Stories may permanently stay at the bottom of the Product Backlog, because new high-priority User Stories keep taking their place at the top.
You should add as many User Stories as you can think of, and continue doing so when you have new ideas as the product evolves.

Before the weekly SDP meeting, your team should move User Stories in the Product Backlog to match the priorities your team has.
During the meeting, the coaches may have you move items around if your app does not meet the [app requirements], to ensure you work on them before anything else.

There is another kind of item in the Product Backlog: Bugs. When you encounter a Bug, you should create an issue for it, placing it in the Product Backlog according to the priority you believe it has.
Note that Bugs are not always high-priority, since they may be minor annoyances that are not worth the time to fix compared to implementing a useful feature.

### Sprint Backlog

The Sprint Backlog contains User Stories for the current "Sprint", i.e. one week's worth of work.

At or before the weekly SDP meeting, you and your teammates pick from the top of the Product Backlog as many User Stories as you think you can implement in a Sprint.
Because the Product Backlog is ordered, the team always picks from the top, skipping items only if they have a hard requirement on a higher-priority item that hasn't been completed yet. 

Once the Sprint backlog is defined, you cannot remove items from it or add more during the week: it represents a commitment from the team.

### Sprint tasks

The Sprint tasks contains the **Developer Tasks** corresponding to the Sprint backlog's User Stories. 
User Stories can correspond to one or more tasks depending on how complex they are.
Each User Story issue should contain in its description a list of the corresponding task issues.

The art of translating User Stories to developer tasks requires time to master, so you will have to practice it as much as possible. 
Let's take an example story: "as an administrator, I can log into the system, so that I can use the admin interface". 
This User Story tells us nothing about how it will be implemented, or how much time it will take. 
As a team, you need to decide how you will implement this story. 
You are the experts on how to produce software, so your opinion is what matters here, given that there is no other constraint. 
You can choose to use an authentication service (e.g., Google, Facebook, EPFL). 
Then you need to show something to the user: a login screen. 
And the last part is that you want to keep some settings associated with each user's account in your database. 
These steps of achieving the login functionality are not the only way to implement what is necessary for the User Story to be considered done but they are one possible way. 
Whatever the steps for delivering the functionality described in a User Story, you need to describe these steps as tasks, estimate how much time each task is going to take, assign a team member to work on each, and do it.

After breaking up a User Story into smaller pieces, you estimate the time necessary to deliver each task. 
You then create an issue corresponding to each task, and go on to split up the next User Story if some team members are still available for more work.

Using this workflow, at the end of each Sprint the repository's "master" branch will contain a working application where all tasks have been implemented.

Note that testing is not a separate developer task from implementation: all tasks should include both code and tests.


## 3. Code

Follow the [SDP Bootcamp](https://github.com/sweng-epfl/public/blob/master/bootcamp/SDPBootcamp.md) to set up a basic codebase, including branch protection rules, and to learn about pull requests.

Pull requests are the only way your code will get into the team's master branch, to ensure that nobody can accidentally commit code that does not meet quality standards.

In SDP, we ask that each pull request be reviewed by at least two teammates.
You can speed up this process by assigning teammates as reviewers after you have opened a pull request, using the "Reviewers" list in the pull request page.

To get good feedback on a pull request, make sure the code you push is clean and readable; for instance, do not leave commented out lines of code you used for debugging, and use Android Studio's code formatting tools to make everything consistent.
Otherwise, your teammates will have to give you feedback on low-level issues such as formatting, instead of high-level code design issues.

Remember to write good commit messages. 
Always say what was done, not how, and use meaningful messages.
Do not explain too much in the headline. 
Respecting these guidelines will make your commits list understandable for the reviewers and will help if you need to go back in the history of modifications.

To review a pull request, go to the "files changed" tab, and look at the code. When you have a question or remark, hover over a line and click on the "+" button that appears, write your thoughts down, then click "Start a review". Write down as many comments as you want, then use the "Finish your review" button at the top. You can refer to people through their GitHub usernames so they get a notification; for instance, "@alice might know how to better integrate this" will send a notification to "alice" so she can give her opinion. You can refer to GitHub issues and pull requests using their number; for instance, "this is related to #123" will link to issue/PR 123 of the repository.

GitHub also lets you create "draft" pull requests, which cannot be merged, as a way to show others what you're doing and get early feedback on important design decisions.


## 4. Summary

Before the weekly meeting:
- The team orders the Product Backlog according to their priorities
- The team moves all completed issues to the "Done in Sprint N" column, where N is the current Sprint, including User Stories whose tasks have all been completed
- The team creates a new "Done in Sprint N+1" column to be used for the next Sprint

During the weekly meeting:
- The team demoes the app as it is in their repository's "master" branch to the coaches, in 3 minutes or less
  - The demo should treat the coaches as customers: they have no software development knowledge, and they do not know what the app can do
  - The demo should be from scratch, i.e., explain the app from zero, not explain what the team added since the previous week
  - The demo does not need to show all of the app's features, only the key ones; for instance, creating a forum post is a nice feature to show, but editing it is probably a waste of demo time
- The team discusses any developer tasks that were not completed in time, to understand why and to improve their estimation skills
- The coaches reorder the Product Backlog if necessary to meet the app requirements
- While not all team members have a full 8 hours of work for the week:
  - The team picks the topmost User Story from the Product Backlog
  - The team moves that story to the Sprint backlog
  - The team decides on how to split it into developer tasks
  - For each such developer task:
    - The team estimates how long the task will take
    - The team assigns the task to someone who can take on that amount of work
    - If no team member has enough time to implement the task, it can exceptionally stay at the top of the Sprint backlog, to be carried over for the next Sprint

During the week:
- Each team member works on their assigned task
  - When a task is completed, the member opens a pull requests and assigns at least two reviewers, who should provide a thorough review of the code
  - When the reviewers are satisfied, and continuous integration accepts the code, any team member can merge the pull request and delete the corresponding branch
- The team adds any bug they find to the Product Backlog
- The team adds as many new User Stories to the Product Backlog as they want
- At least twice a week, the team meets for a "standup meeting" in which they shortly describe what they are working on and whether anything is blocking their work
  - This meeting should last 10 minutes at most, ideally less
  - Any blockers identified this way should be discussed between a few of the team members later, so that one member does not waste time figuring out something that one of their teammates knows how to solve
- The team prepares a demo to show the coaches for the next meeting

The Scrum master is in charge of making sure the team works smoothly and prepares a good demo. 
The coaches are there to help the team both during the weekly meeting and outside of it if needed.

After a few weeks, once the app gets into shape, the team may do more and more of the "weekly meeting" work itself, before the meeting happens.
Then the meeting becomes more of a formality: demo the app, explain what you did, present the tasks you'd like to do next, and get the coaches' approval.
The goal is to make you autonomous developers who deliver working high-quality software and are able to work in a team.
