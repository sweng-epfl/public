# EPFL Software Development Project

In this companion course taught after the Software Engineering course, students build an Android app in a team following a [Scrum-like process](./process),
rotating for the role of "Scrum Master", advised by coaches from the staff.

The course is divided into two-week Scrum sprints. Teams meet with their assigned coaches every week, either to finish a sprint and plan the next one,
or as an intermediary meeting during a sprint to ensure everything is going well.
During each sprint, students work on their assigned tasks, review each other’s code, and have intermediate “standup” meetings to coordinate.

The first sprint is a special one in which all teams work on the ["bootcamp"](./bootcamp), basic tasks to ensure everyone has a solid foundation for the rest of the course.

At the end of the course, teams show off their app to the entire class, and vote for their favorite apps as part of a friendly competition.
Teams who made the highest-voted apps get prizes. The competition does not impact grades, but helps with motivation.


## Objectives

* Build a useful, robust, and maintainable app as part of a team
* Plan development time effectively to release features on schedule
* Evaluate code produced by teammates and give constructive feedback
* Apply the Scrum methodology in a real-world scenario

As part of building an app, students will learn the pitfalls of real-world software engineering,
such as fixing code that only works on one developer’s machine, working around tooling bugs, and dealing with incomplete documentation.


## Prerequisites

This course requires mastery of EPFL's Software Engineering course, but does _not_ assume previous knowledge of Android programming.


## App requirements

All apps must meet the basic requirements of **functionality** and **resilience**: 
apps must accomplish compelling tasks for clear use cases, in a way that is easy to use and consistent with the Android experience;
and apps must work in the face of user errors, malice, and external issues such as a lack of Internet connectivity, with a test suite to demonstrate this.

To ensure that students encounter real-world challenges, and to provide fair grading conditions across teams, all apps must also meet the following requirements:
* **Split app model**: the app must use a public cloud service, such as Google Firebase
* **User support**: the app must have a concept of authentication, for instance using Android’s built-in Google authentication
* **Sensor use**: the app must use at least one phone sensor, such as GPS, camera, or microphone, as part of a core feature of the app
* **Offline mode**: the app must be usable without Internet connectivity, by paring down features compared to the normal experience

Teams are not allowed to write their own backend unless they have a good reason approved in writing by the instructor.
This is to ensure (a) the app will still work in the future without someone to set up and maintain a backend,
and (b) the project scope is restricted enough to be feasible within the course.
Security, privacy, and vendor lock-in are valid concerns but outside the scope of this course.

Sensor usage should drive some behavior in the app, such as finding nearby points of interest with the GPS, or augmenting reality by drawing over a video feed.
Merely getting sensor data and storing it in a field shown to users is too simple and not enough.


## Grading

The course grade is composed of an individual grade and a team grade.

The individual grade is based on what each student does each sprint.
The first sprint, in which all teams do the same tasks, does not count for the individual grade, though students still receive indicative feedback.
The individual grade is an average of all sprint grades with equal weight, dropping the worst sprint so that students need not worry if they miss one sprint due to illness or some other emergency.

The team grade is based on the app and applies to all members of the team.
The team is graded after each third of the course, counting for 10%, 30%, and 60% of the team grade respectively.

Grades are assessed per course objective as one of _Excellent_, _Good_, _OK_, _Poor_, _Very Poor_.
These correspond to 100%, 80%, 60%, 30%, and 0%, where 60% is a passing grade.
The final course grade is half individual grade and half team grade, though the course staff reserve the right to make exceptions for cases that warrant it.

All criteria, below, are equally weighted within each grade.
The staff will use their professional opinion to decide which grade best applies for each case.
These criteria are provided as guidelines to help set expectations, not to argue about semantics.


| **Individual** | **Excellent** | **Good** | **OK** | **Poor** | **Very Poor** |
|----------------|---------------|----------|--------|----------|---------------|
| **Professionalism** | Proactive and professional communication with the team and coaches | Communication with the team and coaches when needed, in a professional manner | The team and coaches are kept informed, but not always early enough or in a professional manner | Misunderstandings with the team and coaches, subpar communication skills | Little to no communication with the team and coaches |
| **Planning** | Contributing a large improvement, directly related to what was planned, with time for review and merging | Contributing a reasonable improvement, mostly related to what was planned, with time for review and merging | Contributing an improvement that is incremental or not a priority according to what was planned, or without enough time to be reviewed and merged | No meaningful improvements in time | No work done, not even draft pull requests |
| **Code** | Maintainable, robust, and documented code at the module level | Maintainable, robust, and documented code at the function level | The code is resilient to basic errors, but not easy to maintain | The code only works in the “happy path” | No code merged, or code doesn’t work at all |
| **Tests** | Tests for all or almost all cases | Tests for most cases | Tests for success cases and some basic errors | Few tests, even for success cases | No tests at all |
| **Reviews** | Thorough code reviews that consider the architecture and future evolution of the codebase | Deep code reviews that consider the context of the code within the codebase | Code reviews that keep everyone informed and point out potential bugs | Code reviews that don't say much, only pointing out glaring problems | No code reviews |

| **Team** | **Excellent** | **Good** | **OK** | **Poor** | **Very Poor** |
|----------|---------------|----------|--------|----------|---------------|
| **Functionality** | The app provides clear value to users in a way that fits the Android ecosystem | The app provides value but needs some explanation for the average Android user | The app has some useful features, but not enough to form a coherent whole | The app might be useful in niche scenarios, but only for users willing to learn how to use it | The app has too few features or is too hard to use to provide value to anyone |
| **Resilience** | The app is resilient to all or almost all failures, including malicious inputs, and has a comprehensive test suite to demonstrate this | The app is resilient to user and environment errors but not to malice, and has a test suite that covers the non-malicious cases | The app occasionally crashes due to edge case errors, and may not have enough tests to expose these bugs | The app may corrupt data due to errors, and has few tests outside of the “happy path” | Even normal usage of the app frequently leads to crashes or data corruption, and there is no serious testing |
| **Maintainability** | The code is modular, is clean, makes use of appropriate abstractions, and has enough documentation that another team could take over productively |The code is clean and documented at the function level, but modules are not always organized well, and another team would need some help to take over | The code is mostly clean, but not clear or documented enough for another team to take over without serious help | The code is hard to understand even at the level of functions, another team might prefer to start from scratch | The code is so hard to understand that even the current team finds it difficult |
