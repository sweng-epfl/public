# Teamwork

> [!WARNING]
> These lecture notes are preliminary and will be improved soon.

Working on your own typically means engineering a small application, such as a calculator.
To design bigger systems, teams are needed, including not only engineers but also designers, managers, customer representatives, and so on.
There are different kinds of tasks to do, which need to be sub-divided and assigned to people: requirements elicitation, design, implementation, verification, maintenance, and so on.

This lecture is all about teamwork: who does what when and why?


## Objectives

After this lecture, you should be able to:
- Contrast different software development methodologies
- Apply the Scrum methodology in a software development team
- Divide tasks within a development team
- Produce effective code reviews


## What methodologies exist to organize a project?

We will see different methodologies, but let's start with one you may have already followed without giving it a name, _waterfall_.

### Waterfall

In Waterfall, the team completes each step of the development process in sequence.
Waterfall is named that way because water goes down, never back up. First requirements are written down, then the software is designed, then it is implemented,
then it is tested, then it is released and maintained.
Once a step is finished, its output is considered done and can no longer be modified, then the next step begins.
There is a clear deadline for each task, and a specific goal, such as documents containing requirements or a codebase implementing the design.

Waterfall is useful for projects that have a well-defined goal which is unlikely to change, typically due to external factors such as legal frameworks or externally-imposed deadlines.
Projects that use Waterfall get early validation of their requirements, and the team is forced to document the project thoroughly during design.

However, Waterfall is not a good fit if customer requirements aren't set in stone, for instance because the customers might change their mind, or because the target customers aren't even well-defined.
The lack of flexibility can also result in inefficiencies, since steps must be completed regardless of whether their output is actually useful.
Waterfall projects also delay the validation of the product itself until the release step, which might lead to wasted work if the software does not match what the customers expected.

Typically, a project might use Waterfall if there are clear requirements that cannot change, using mature technologies that won't cause surprises along the way,
with a team that may not have enough experience to take decisions on its own.

### Agile

Agile is not a methodology by itself but a mindset born out of reaction to Waterfall's rigidity and formality, which is not a good fit for many teams including startups.
The [Agile Manifesto](https://agilemanifesto.org/) emphasizes individuals and interactions, working software, customer collaboration, and responding to change,
over processes and tools, comprehensive documentation, contract negotiation, and following a plan.

In practice, Agile methods are all about iterative development in a way that lets the team get frequent feedback from customers and adjust as needed.

### Scrum

Scrum is an Agile method that is all about developing projects in _increments_ during _sprints_.
Scrum projects are a succession of fixed-length sprints that each produce a functional increment, i.e., something the customers can try and give feedback on.
Sprint are usually a few weeks long; the team chooses at the start how long sprints will be, and that duration remains constant throughout the project.
At the start of each sprint, the team assigns to each member tasks to complete for that sprint, and at the end the team meets with the customer to demo the increment.

Scrum teams are multi-disciplinary, have no hierarchy, and should be small, i.e., 10 people or fewer.
In addition to typical roles such as engineer and designer, there are two Scrum-specific roles: the "Scrum Master" and the "Product Owner".

The Scrum Master facilitates the team's work and checks in with everyone to make sure the team is on pace to deliver the expected increment.
The Scrum Master is _not_ a manager, they do not decide who does what. In general, a developer takes on the extra role of Scrum Master.

The Product Owner is an internal representative for the customers, who formalizes and prioritizes requirements and converts them into a "Product Backlog" of items for the development team.
The Product Backlog is a _sorted_ list of items, i.e., the most important are at the top. It contains both user stories and bugs.
Because it is constantly sorted, new items might be inserted in any position depending on their priority, and the bottom-most items will likely never get done, because they are not important enough.
For instance, the following items might be on a backlog:
- "As an admin, I want to add a welcome message on the main page, so that I can keep my users informed"
- "Bug: Impossible to connect if the user name has non-ASCII characters"
- "As a player, I want to chat with my team in a private chat, so that I can discuss strategies with my team"

To plan a sprint, the team starts by taking the topmost item in the Product Backlog and moves it to the "Sprint Backlog", which is the list of items they expect to complete in a sprint.
The team then divides the item into development tasks, such as "add an UI to set the welcome message", "return the welcome message as part of the backend API", and "show the welcome message in the app".
The team assigns each task a time estimate, dependent on its complexity, a "definition of done", which will be used to know when the task is finished, and a developer to implement the task.
The "definition of done" represents specific expectations from the team, so that the person to which the task is assigned knows what they have to do.
It can for instance represent a user scenario: "an admin should be able to go to the settings page and write a welcome message, which must be persisted in the database".
This avoids misunderstandings between developers, e.g., Alice thought saving the message to the database was part of Bob's task and Bob thought Alice would do it.
Tasks implicitly contain testing: whoever writes or modifies a piece of code is in charge of testing it, though the team might make specific decisions on specific kind of tests or test scenarios.

During a sprint, the team members each work on their own tasks, and have a "Daily Scrum Meeting" at the start of each day.
The Daily Scrum Meeting is _short_: it should last at most 15 minutes, preferably much less, and should only be attended by the development team including the Scrum Master,
not by the Product Owner nor by any customer or other person.
This meeting is also known as a "standup" meeting because it should be short enough that the team does not need to formally get a room and sit down.
The Daily Scrum Meeting consists of each team member explaining what they've done in the previous day, what they plan to do this day, and whether they are blocked for any reason.
Any such "blockers" can then be discussed _after_ the meeting is over, with only the relevant people.
This way, all team members know each other's status, but they do not need to sit through meetings that are not relevant to them.

Any bugs the team finds during the sprint should be either fixed on the spot if they are small enough, or reported to the Product Owner if they need more thought.
The Product Owner will then prioritize these bugs in the Product Backlog.
It is entirely normal that some bugs may stay for a while in the backlog, or even never be fixed, if they are not considered important enough compared to other things the team could spend time on.

Importantly, the Sprint Backlog cannot be modified during a sprint.
Once the team has committed to deliver a specific increment, it works only on that increment, in a small-scale version of Waterfall.
If a customer has an idea for a change, they communicate it with the Product Owner, who inserts it at the appropriate position in the Product Backlog once the sprint is over.

Once the sprint is over, the team demoes the resulting increment to the customer and any relevant stakeholders as part of a "Sprint Review" to get feedback,
which can then be used by the Product Owner to add, remove, or edit Product Backlog items.
The team then performs a "Sprint Retrospective" without the customer to discuss the development process itself.
Once the Review and Retrospective are done, the team plans the next sprint and executes it, and the process starts anew.

Scrum does not require all requirements to be known upfront, unlike Waterfall, which gives it flexibility.
The team can change direction during development, since each sprint is an occasion to get customer feedback and act on it.
The product can thus be validated often with the customer, which helps avoid building the wrong thing.

However, Scrum does not impose any specific deadlines for the final product, and requires the existence of customers,
or at least of someone who can play the role of customer if the exact customers are not yet known.
It also does not fit well with micro-managers or specific external deadlines, since the team is in charge of its own direction.

### Other methodologies

There are plenty of other software development methodologies we will not talk about in depth.
For instance, the "[V Model](https://en.wikipedia.org/wiki/V-Model)" tries to represent Waterfall with more connections between design and testing,
and the "[Spiral model](https://en.wikipedia.org/wiki/Spiral_model)" is designed to minimize risks.
[Kanban](https://en.wikipedia.org/wiki/Kanban_(development)) is an interesting methodology that essentially takes Scrum to its logical extreme,
centered on a board with tasks in various states that start from a sorted backlog and end in a "done" column.


## How can one effectively work in a team?

The overall workflow of an engineer in a team is straightforward: create a branch in the codebase, work on it,
then iterate on the work with feedback from the team before integrating the work in the codebase.
This raises many questions. How can one form a team? How to be a "good" team member? How to divide tasks among team members?

Team formation depends on development methodologies.
In Scrum, teams are multi-disciplinary, i.e., there is no "user interface team" or "database team" but rather teams focused on an overall end-to-end product that include diverse specialists.
Scrum encourages "two-pizza" teams, i.e., teams that could be fed with two large pizzas, so 4-8 people.

Being a good team member requires three skills: communication, communication, and communication.
Do you need help because you can't find a solution to a problem? Are you blocked because of factors outside of your control? Communicate!
There are no winners on a losing team. It is not useful to write "perfect" code in isolation if it does not integrate with the rest of the team's code,
or if there are other more important tasks to be done than the code itself, such as helping a teammate.
A bug is never due to a single team member, since it implies the people who reviewed the code also made mistakes by not spotting the bug.
Do not try to assign blame within the team for a problem, but instead communicate in a way that is the team vs. the problem.

Dividing tasks within a team is unfortunately more of an art than a science, and thus requires practice to get right.

If the tasks are too small, the overhead of each task's fixed costs is too high.
If the tasks are too big, planning is hard because there are too many unknowns per task.
One heuristic for task size is to think in terms of code review: what will the code for the task roughly look like, and how easy will that be to review?

If the tasks are estimated to take more time than they really need, the team will run out of work to do and will need to plan again.
If the tasks are estimated to take too little time, the team will not honor its deadlines.
Estimating the complexity of a task is difficult and comes with experience.
One way to do it is with "planning poker", in which team members write down privately their time estimate, then everyone reveals their estimate at once, the team discusses the results,
and the process starts again until all team members independently agree.

If the tasks are assigned to the wrong people, the team may not finish them on time because members have to spend too much time doing things they are not familiar with or do not enjoy doing.
One key heuristic to divide tasks is to maximize parallelism while minimizing dependencies and interactions.
If two people have to constantly meet in order to work, perhaps their tasks could be split differently so they need to meet less.
Typically, a single task should be assigned to and doable by a single person.

An important concept when assigning work within a team is the "bus factor": how many team members can be hit by a bus before the team can no longer continue working?
This is a rather morbid way to look at it; one can also think of vacations, illnesses, or personal emergencies.
Many teams have a bus factor of 1, because there is at least one member who is the only person with knowledge of some important task, password, or code.
If this member leaves the team, gets sick, or is in any way incapacitated, the team grinds to a halt because they can no longer perform key tasks.
Thus, tasks should be assigned such that nobody is the only person who ever does specific key tasks.

One common mistake is to assign tasks in terms of application layers rather than end-to-end functionality.
For instance, the entirety of the database work for a sprint in Scrum could be assigned to one person, the entirety of the UI work to another, and so on.
However, if the database person cannot do their work, for instance because of illness, then no matter what other team members do, nothing will work end-to-end.
Furthermore, if the same people are continuously assigned to the same layers, the bus factor becomes 1.
Instead, team members should be assigned to features, such as one person in charge of user login, one in charge of informational messages, and one in charge of chat.

Finally, another common mistake is to make unrealistic assumptions regarding time: everyone will finish on time, and little time is necessary to integrate the code from all tasks into the codebase.
Realistically, some tasks will always be late due to incorrect estimations, illnesses, or other external factors.
Integrating the code from all tasks also takes time and may require rework, because engineers may realize that they misunderstood each other and that they did not produce compatible code.
It is necessary to plan for more time than the "ideal" time per task.


## How can one produce a useful code review?

Code reviews have multiple goals.
The most obvious one is to review the code for bugs, to stop bugs getting into the main branch of the codebase.
Reviewers can also propose alternative solutions that may be more maintainable than the one proposed by the code author.

Code review is also a good time for team members to learn about codebase changes.
In 2013, [Bacchelli and Bird](https://dl.acm.org/doi/10.5555/2486788.2486882) found that knowledge transfer and shared code ownership were important reasons developers did code reviews in practice,
right behind finding defects, improving the code, and proposing alternative solutions.

Code reviews are cooperative, not adversarial. The point is not to try and find possible "backdoors" a colleague might have inserted;
if you have a malicious colleague, code reviews are not the tool to deal with the problem.
That is, unless your "colleague" is not a colleague but a random person on the Internet suggesting a change to your open source software, at which point you need to be more careful.

### Team standards and tools

Each team must decide the standards with which it will review code, such as naming and formatting conventions and expected test coverage.
Automate as much as possible: do not ask reviewers to check compliance with a specific naming convention if a static analyzer can do it,
or to check the code coverage if a tool can run the tests and report the coverage.

### From the author's side

To maximize the usefulness of the reviews you get as a code author, first review the code yourself to avoid wasting people's time with issues you could've found yourself,
and then choose appropriate reviewers.
For instance, you may ask a person who has worked for a while on that part of the codebase to chime in, as well as an expert on a specific subject such as security.
Make it clear what you expect from the reviewers. Is this a "draft" pull request that you might heavily change? Is it a fix that must go in urgently and thus should get reviews as soon as possible?

You also want to give reviewers a reasonable amount of code to review, ideally a few hundred lines of code at most.
It's perfectly acceptable to open multiple pull requests in parallel for independent features, or to open pull requests sequentially for self-contained chunks of a single feature.

### From the reviewer's side

Skim the code in its entirety first to understand what is going on, then read it in details with specific goals in mind, adding comments as you go. Finally, make a decision:
does the code need changes or should it be merged as-is? If you request changes, perform another review once these are done.
Since you might do multiple reviews, don't bother pointing out small issues if you are going to ask for major changes anyway.
Sometimes you may also want to merge the code yet create a bug report for small fixes that should be performed later, if merging the code is important to unblock someone else.

Evaluate the code in terms of correctness, design, maintainability, readability, and any other bar you think is important.
For instance, do you think another developer could easily pick up the code and evolve it? If not, you likely want to explain why and what could be done to improve this aspect.

When writing a comment, categorize it: are you requesting an important change? is it a small "nitpick"? is it merely a question for your own understanding?
For instance, you could write "Important: this bound should be N+1, not N, because..." or "Question: could this code use the existing function X instead of including its own logic to do Y?".
Be explicit: make it clear whether you are actually requesting a change, or merely doing some public brain-storming for potential future changes.
Pick your battles: sometimes you may personally prefer one way to do it, but still accept what the author did instead of asking for small changes that don't really matter in the big picture.

Remember to comment on _the code_, not _the person who wrote the code_. "Your code is insecure" is unnecessarily personal; "this method is insecure" avoids this problem.

If you do not perform a thorough review of all of the code, specify what you did. It's perfectly fine to only check changes to code you already know,
or to not be confident in evaluating specific aspects such as security or accessibility, but you must make this clear so that the code author does not get the wrong idea.

There are plenty of guidelines available on the Internet that you might find useful, such as [Google's](https://google.github.io/eng-practices/review/reviewer/).


## Summary

In this lecture, you learned:
- Development methodologies, including Waterfall and especially Scrum
- Dividing tasks within a team to maximize productivity and minimize conflicts
- What code reviews are for and how to write one

You can now check out the [exercises](exercises/)!
