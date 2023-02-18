# Development Process

In this course, you will use GitHub as a centralized code repository and work tracker.
You will work in a fashion inspired by the [Scrum](https://www.scrum.org/resources/what-is-scrum) development process.
Take another look at [the Software Engineering lecture notes on teamwork](../../lectures/Teamwork) to refresh your memory.

Your team in this course is what Scrum calls the "Development Team", and you share the role of "Product Owner" with your coaches since you both can decide what to include in the app.
While this is not always realistic, it means that you will work on something you care about rather than merely implementing features you are told to.
You will rotate the role of "Scrum Master", the person who is in charge of helping the team work smoothly.

Your day-to-day workflow will be centered around GitHub: write code, submit a pull request, get feedback, make changes based on the feedback, and merge.
To ensure code works on another machine than yours, you will use continuous integration.


## Starting the project

Create a GitHub project with which you will track your team’s progress, which Scrum calls the “Board”.
There should be three columns: "Product Backlog", the "Sprint Backlog", and the "Sprint Tasks". Each sprint, you will create an additional "Done in sprint N" column.

Add as many user stories to the Product Backlog as you can think of, and sort them in descending order of priority.
Having too many stories is not a problem, it is expected that you will still have stories you could implement when the course finishes.
These stories should be centered on end-to-end features, with each story providing clear value to its audience.


## Beginning a sprint

Create a “Done in sprint N” column in the Board, and add a note at the top of that column with the name of the Scrum Master for that sprint.

Ideally, the “Sprint Backlog” and “Sprint Tasks” columns should be empty at this point, but if some items remain, they should be carried over.
The exception is if some work is blocked by external factors, such as a major framework bug that prevents the implementation of a feature, in which case items may move back to the Product Backlog.

Move the top item from the Product to the Sprint Backlog.
Create notes in the Sprint Tasks for each task making up that item.
For instance, a user story about finding new friends by interests could have one task for letting users define their interests in their profile,
and one task for letting users filter other users by interests.
Tasks always implicitly include testing.

For each task, assign _one_ person to it, who then estimates how much time the task will take, with the help of their teammates, and annotates the task with that time.
Make sure the task’s name is an unambiguous “definition of done”, such as implementing a specific interface, or making a specific end-to-end scenario work.
Estimating the time a task takes is hard, and this course will help you get better at it.
Time estimates are not graded directly. However, if you over-estimate, you will need to spend time finding a new task,
and if you under-estimate, you will need to spend time splitting your task into parts so you can merge part of it.

Once all tasks for the current item have been created and assigned, do the same with the next item in the Product Backlog, until all team members have a full sprint’s worth of work.

_(If you'd like, you can "pair program" on tasks, but there must be exactly one person ultimately responsible for each task, and the distribution of responsibilities must be balanced across tasks)_


## Working on a task

Create a branch for the task, and push code to it that completes the task, including tests, then open a pull request.
If you want early feedback on code that is not finished yet, use GitHub's "draft" pull requests.
Once you have at least one accepting code review from a teammate, and continuous integration is happy, merge the pull request.

You may need to create multiple pull requests for a single task, typically because a teammate depends on one part of your task.
For instance, if your task is to create a new module, and a teammate needs to use that module,
you can create a pull request with just the module’s interface, so that you can agree with your teammate on what the interface should be.

If you realize you will not have time to finish a task in the current sprint, submit a pull request with a polished version of a smaller part of the task,
including tests for just that part. It is normal that your time estimates will be occasionally wrong, though this should happen less often as the course goes on.

While writing code for a task, you must balance two concerns: making clean self-contained code, and making code that is easy to evolve in the near future.
Don’t make your design overly complex for hypothetical future changes that may never happen, but think about the next few tasks that are likely to come up and how they will fit in your code.


## Reviewing code

Each pull request must be reviewed by at least one teammate, in a thorough and inquisitive fashion to uncover bugs, discuss design, and learn about changes.
The Scrum Master is responsible for ensuring people review the pull requests they are assigned to in a timely fashion.

Read the entire code, and comment on code that looks wrong or that you think could be improved.
Ask questions when you do not understand a change.
Leave positive comments as well, such as congratulating the author for getting rid of an ugly hack.

You can prefix your comments with one word indicating the kind of comment it is, such as "important" or "nitpick" or "question".
Make sure you talk about the code, not the author. "You are wrong" or "Your code is bad" will put your teammates on the defensive, whereas "This code looks wrong to me, because..." is constructive.

If you really have nothing to say, a simple “looks good to me” or “LGTM” for short is fine.
This should only be the case for the occasional tiny pull request that fixes a simple bug, not for large changes for which there is always something to discuss or learn from.

Here are some good examples of code reviews in past projects:
[1](https://github.com/epfl-SDP/android/pull/255),
[2](https://github.com/epfl-SDP/android/pull/410),
[3](https://github.com/PalFinderTeam/palFinder/pull/82),
[4](https://github.com/PalFinderTeam/palFinder/pull/302).

And some examples from large open-source projects:
[1](https://github.com/dotnet/runtime/pull/81725),
[2](https://github.com/dotnet/runtime/pull/72712),
[3](https://github.com/openjdk/jdk/pull/7431),
[4](https://github.com/ruby/ruby/pull/5002),
[5](https://github.com/lampepfl/dotty/pull/16612)


## Meetings

To help the team members know what others are doing, you will hold what Scrum calls "standup meetings".
These are **short** meetings, maximum 15 minutes, during which each member, in turn, explains what they are working on and whether they are blocked on anything.
Standup meetings are not for extended discussion, but to identify any blocking issues so that the rest of the team can decide how to react,
such as holding a later meeting to discuss a design issue or asking one member to help another. 

In the middle of the sprint, you will meet your team’s coaches for an intermediary meeting, at the same time and place as the retrospective/planning meetings.
These meetings start with a standup meeting, and continue with whatever your team needs from the coaches, typically asking for advice.

In addition to the intermediary meeting, your team should have at least two standup meetings per sprint, i.e., one per week.
The Scrum Master is responsible for planning this.


## Ending a sprint

Move each finished task to the “Done” column in the Board, and annotate it with the time it actually took to help you improve your time estimation skills.
Only tasks whose code is fully merged, including tests, count as done.

Create a “Sprint N summary” page in the repository’s wiki, and have each member write a few sentences about what issues they had with the process,
what they should’ve done instead, and how they will improve in the future.
The Scrum Master additionally writes a summary for the entire team to point out global issues and opportunities for improvement.
This makes you reflect on what happened and helps the coaches help you.
The summary must be about the _process_, not about implementation details.
Please read the [example of a sprint summary](./ExampleSummary.md) for a good example.

Plan how you will demo the app to the customers, whose role is played by the coaches.
The demo must use the code that is in the main branch of your repository, without any code that has not been merged yet.
At the beginning of the project, your demos may be short due to a lack of features, but you should still work on the pitch itself.
The Scrum Master of the elapsed sprint must lead the demo, though other team members can participate if necessary.
Please read the [short guide on giving a demo](./Demo.md) to get an idea of what we expect.

Come to the meeting with the coaches prepared: you will start with the demo, then discuss any issues from your summaries that you or the coaches would like to discuss in depth, then plan the next sprint.

You are _strongly_ encouraged to plan the next sprint on your own before the meeting, so that you can spend more time discussing with the coaches, but this is not required.
