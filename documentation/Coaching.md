# Coaching

## Goal

Technically, the goal of the project is to develop an app.
But the app itself is only an artifact of the real learning going on.
This project is likely the first time students work in a team of more than 2-3 people, deal with merge conflicts, automated tests and CI, code reviews, etc.
The goal is not for them to learn all about Android.
They will learn the bits they need, especially generic concepts such as app lifetime that are useful to many software engineers.

The goal of coaches is to help students learn by guiding them. This does not mean preventing failures;
students will fail in all kinds of ways during the semester and learn valuable lessons that way,
but coaches must be there to help them get back on track and avoid catastrophic failures.

"Failures" do not have to be technical.
Coaches should also watch out for problems in team dynamics, such as one team member not doing work,
one team member overstepping and taking onto their plate the work of others,
teams split into sub-groups that don't talk to each other, and so on.


## Contents

The project uses a Scrum-like methodology, and is thus centered around sprints.
Each sprint, students meet with the coaches to demo their app in its current state, discuss what happened during the elapsed sprint, and plan for the next sprint.
Unlike the real world, students are both team members and product owners:
they decide what their app will be (with occasional exceptions when the coaches have to impose restrictions).
This greatly helps motivation.


## Grading

Grading a project-based course is fundamentally harder than an exam-based course.

It can be difficult to tell whether a student worked enough,
since even good students can unexpectedly struggle due to external causes such as buggy SDKs or poorly documented APIs.
Thus, coaches should be observant and grading should take this into account, e.g.,
failing a student because they had the courage to try out some new package which ended up taking an unusually long time would be bad.
This does not mean we give a good grade to everyone, or that we give the benefit of the doubt all the time.

Coaches should use the "worst sprint grade is dropped" policy to put a positive spin in case a sprint goes really bad, i.e., "as long as you do better next time, you won't be penalized".


## Before the first meeting

Coaches then send an email to all of their teams introducing themselves, providing a time and location for all teams' meeting, and explaining that teams should discuss among themselves if they wish to switch slots

Remember that students are supposed to be free for all assigned course periods;
we should help them if they have constraints, but if a team cannot find a slot that works it's their problem.


## The first meeting

_Copy this checklist into a document per team and check items off as you go, otherwise you will forget at least one item for at least one team._

- Round of introductions: the coaches introduce themselves first, then the team members
  - Make everyone feel welcome; if you're not sure how to pronounce someone's name, ask
- If in a non-English-speaking country: remind them meetings are in English since not all SDP students/TAs speak the local language
  - Even if all coaches and team members for a given team could speak the local language, use English
- Remind them meeting attendance is mandatory, they should email if they absolutely cannot for a good reason
- Recap the course logistics:
  - They will meet for every sprint meeting at the same location and time
  - Explain that we ask for 8h/week of work, including meetings/code reviews, and they will estimate how long tasks take to plan their week.
    It's totally normal that time estimates will be off at first, they will get better.
  - Explain that all code work includes tests, e.g., a five-hour task means some part of those five hours will be spent writing tests
  - Explain that we grade based on "did they do a reasonable amount of work".
    So they don't need to work 20h if their task turns out to be way more difficult than estimated.
    But they also cannot pass just by doing very easy tasks and claiming it took 8h.
  - Emphasize that they must always have merged something into the main branch each sprint, even if it’s only a portion of their original task.
    No code merged into the main branch == bad sprint grade.
- Remind them that they can always ask questions
  - Personal stuff -> email coaches
  - Programming stuff -> Google, StackOverflow, etc; and the course forum
- Make sure the app idea makes sense.
  The team should be able to state clearly (1) who is the target audience of the app; and (2) what problem the app solves for its target audience.
- Make sure their app satisfies all requirements
  - If not, discuss with them until you find a solution
- Remind them that they should do a standup at least once a week
  - Explain that in the past, teams who don't do that always end up losing lots of time because members aren't aware of what's going on,
    leading to duplicate efforts and "blockers" that could've been quickly unblocked by another member
- Remind them that they should write summaries after each sprint before the meeting regarding issues with the process,
  and that they should not talk about implementation details in these
- Answer any questions they have
  - If you don't know, you can always write them down, ask the rest of staff later, and answer the team by email
  - In particular, avoid committing to answers such as how exactly they will be graded or whether they are allowed to do specific things if you're not sure


## Other meetings

_Note: We deliberately exclude any sort of tech support question from the sprint meetings. If anyone brings such things up, tell them to look it up or to use the course forum after the meeting._

- Start with a demo, led by the Scrum Master
  - The demo must only use code in the main branch (but fake data in a database is OK)
  - The demo must be from scratch, not a diff from last time
  - The demo must be at most 5 min, and start with a quick "elevator pitch"
- Discuss the elapsed week, based on their summaries you will have read (and discussed among coaches) beforehand
  - Work through any problems they have with them
  - If there are problems, ask whether they met during the week (not doing so is a common cause of problems)
- Review whether they still have the problems discussed in previous weeks
- Discuss anything you'd like to discuss with them, such as problems you've noticed in their repo, individual students doing too much / too little work, …
- Review their plan for the upcoming week, and help them do it if it's not (properly) done:
  - Choose a Scrum Master, create a note at the top of the sprint backlog with their name
  - Move top product backlog item to sprint backlog
  - Divide into tasks (ask them!), making sure each task has an assigned student, a time estimate & a clear "definition of done" in the title or description
  - Continue until everyone has 7h of assigned tasks (1h is for reviews, meetings, ...)
- Answer any questions they have
- After the meeting is over, quickly write down notes for grading while you have a fresh memory.
  Then once you have time after all meetings of the day, properly do any necessary grading.
