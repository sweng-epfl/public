# Development process

In SDP, you will use a GitHub repository as your navigation panel for the semester: issues, project board, code, and continuous integration.
The repository will allow you to track your progress and to communicate with your team and the staff.

This document will teach you how to manage your repository according to SDP's variant of the Scrum development process.
Scrum is all about helping you deliver better software faster through minimalism in processes, inspired by the Agile manifesto.
We encourage you to refresh your memory of concepts such as "version control", "user stories" and "sprints" using the course material we summarized on Moodle.

In SDP, your team is what Scrum calls the "Development Team", and you share the role of "Product Owner" with your coaches since you both can decide what to include in the app.
While this is not always realistic, it means that you will work on something you care about rather than merely implementing features you are told to.

Scrum also has the concept of "Scrum Master", one person who is in charge of helping the team work smoothly. In SDP, you will rotate this role among team members.


## Project board

The main way you will track your progress is through a GitHub project, which Scrum calls the "Board".
Your GitHub project should start with columns for the "Product Backlog", the "Sprint Backlog", and the "Sprint Tasks".
Each week, you will create an additional "Done in sprint N" column in which you will move the corresponding items.

The "Product Backlog" contains _all_ user stories related to your app, ordered by priority.
Having too many of them is never a problem, as the team will only implement the highest-priority ones.
Some may stay at the bottom of the product backlog forever, because new high-priority ones keep appearing.
You should add as many stories as you can think of and continue adding any new ideas you get.

There is one other kind of item in the product backlog: bugs.
When you encounter a bug, you should create an issue for it, placing it in the product backlog according to the priority you believe it has.
Some bugs may not be worth the time to fix compared to implementing a useful feature, which should be reflected in their priority.

The "Sprint Backlog" contains all stories and bugs that your team is working on in the current sprint, and only those.
Your team will move the top items from the product to the sprint backlog at the end of each sprint, to plan what you will do in the upcoming sprint.
Since the product backlog is ordered by priority, you will always pick the top items, unless an item depends on another one that hasn't been finished yet.
Items that are not done by the end of the sprint will stay there, unless their priority has changed.

At the top of the sprint backlog should be a note with the name of the Scrum Master for that sprint.

The "Sprint Tasks" contains the tasks corresponding to the stories in the sprint backlog.
To convert a user story from the sprint backlog into tasks, create notes in the tasks column, and convert them to issues.
Each task should correspond to work done by a single person, though a single person may do multiple tasks.
Each task implicitly includes the corresponding tests. Tasks should be have a clear "definition of done", such as implementing a specific interface, or making a specific end-to-end scenario work.
This definition of done can change if unexpected issues are found during development, but such changes must be discussed with the team members implementing other parts of the overall user story.

Knowing how much work to take on each week is not easy, and SDP will help you get better at this.
Each task should be assigned to a team member, and that team member should estimate how much time the task will take.
Once done, the team member can compare the expected and actual time to improve the next estimates.
In SDP, we implement this using issue labels: mark issues with labels such as "expected: 3h" and "actual: 5h".

Please keep in mind you that SDP is a 4-credit course, which means around 8 hours of work per week per teammate, 1 of which is used for meetings and code reviews.
See the [EPFL FAQs](https://www.epfl.ch/education/bachelor/study-programs-structure/faqs/) for more information.


## Workflow

Your workflow in SDP will be centered around GitHub pull requests.
You will write code, submit a pull request, and make changes requested by your teammates.
You will also review your teammates' pull requests. You will also use continuous integration to ensure your changes work on other people's machines and not just on yours.

We ask that all pull requests must be reviewed by at least two teammates.
Pull requests are the _only_ way your code can get into the main branch of the repository.

It may be useful to have more than one pull request per task.
For instance, if a task requires you to first refactor a large chunk of code and then add a feature to it, it is easier for your teammates to review these two changes separately.

You may also need to submit a pull request with less than a full task if you realize that you will not have time to finish the task in the current sprint.
In that case, you should deliver a polished version of a smaller part of the task, including tests for just that part.
This is always better than delivering nothing.

If you want a review on code that is not finished yet, use GitHub's "draft" pull requests.
This way, your teammates can give you early feedback.

Pull request reviews should be thorough.
Read the entire code, comment on code that looks wrong, or code that could be written in some better way.
You can prefix your comments with one word indicating the kind of comment it is, such as "important" or "nitpick" or "question".
Make sure you talk about the code, not the author. "You are wrong" or "Your code is bad" will put your teammates on the defensive, whereas "This code looks wrong to me, because..." is constructive.

To help the team members know what others are doing, you will hold what Scrum calls "standup meetings".
These are _short_ meetings during which each member, in turn, explains what they are working on and whether they are blocked on anything.
Standup meetings are not for extended discussion, but to identify any blocking issues so that the rest of the team can decide how to react,
such as holding a later meeting to discuss a design issue or asking one member to help another. You should have these meetings twice a week.

If you are the Scrum Master for the current sprint, you are responsible for making the process go smoothly.
This means keeping track of each member's progress to ensure nobody falls behind, scheduling the standup meetings, making sure pull requests get feedback on time, and so on.


## Weekly meeting

At the end of each sprint, you will write a summary of problems you had update the project board, then have your weekly meeting with members of the staff.

The summary should be in a `Sprint N` page in the repo wiki, where N is the week number.
In it, each member in alphabetical order writes a few sentences about any problems they encountered in terms of process, what they should have done instead, and how they will improve in the future.
The Scrum Master also writes the same for the entire team.
Writing this summary will help you reflect about how to improve and will help the staff know what kind of guidance you need.
The summary must be about the _process_, not about _implementation_ details.

Please read the [example of a weekly summary](./ExampleSummary.md) to get an idea of what we expect.

Updating the project board means creating a new "Done in sprint N" column and moving all items that are fully done in there, i.e., the tasks whose pull requests are all merged,
the user stories whose tasks are all done, and the bugs which have been fixed by merged pull requests.
You should also move the note with the name of the Scrum Master for sprint N at the top of the done column.

It is important that only merged pull requests count as done.
Pull requests which are still open do not count, even if they're almost done, and neither do branches that have no pull request open yet.

You should plan what you will do for the next week before the weekly meeting with the staff, though the staff can help you if you are unsure
and may require you to change your plans to ensure you meet the basic app requirements.
As the semester progresses, your team should become more independent and plan its next work well every time.

Each meeting begins with your team making a demo of your app to the staff, using either a real phone or an emulator.
The demo must use the code that is in the main branch of your repository, without any code that has not been merged yet.
At the beginning of the semester, your demos may be short due to a lack of features, but you should still work on the pitch itself,
such as why someone would want to use your app and what they would get out of it.
The Scrum Master is in charge of making sure the team prepares a good demo.

Please read the [short guide on giving a demo](./Demo.md) to get an idea of what we expect.

After the demo, you can discuss with the staff any issues or questions you have.
We are here to help you.
You will then discuss your plans for the upcoming sprint, and the staff will make sure that your project board is up-to-date.
