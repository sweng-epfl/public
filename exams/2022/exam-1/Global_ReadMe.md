# Software Engineering Exams

All exams come from this `exam-GASPAR` repo in the `sweng-epfl` GitHub organization.
Push your answers to the `main` branch in this repo.


## Exam grading

Grading will always be done on a snapshot of the `main` branch of this repository from GitHub, not your machine.
Please be careful not to overwrite your work, as we will not roll back your commits.
Commit and push your work often, to make sure you don't lose local changes and to mitigate the risk of GitHub going down shortly before the deadline.
We use automated scripts to extract the answers from your repo, so follow the instructions exactly, and do not place stray characters in the files you push, or else you will likely lose many points.

Every time you push a version of your solution to the `main` branch, GitHub Actions will build your code and run automated tests.
Please check the `Actions` tab in the web interface to your repo (_Build and run checks_ step of the workflow) to make sure it's OK.
If your exam fails to build or to pass the tests in GitHub Actions, it will certainly not pass our private tests, and you could lose a lot or all of the points.

We strongly advise that you read each problem statement at least twice.
You need to provide a correct answer to the _correct_ question in order to receive full credit.
A correct answer to a _wrong or misinterpreted_ question will not earn credit.
If you have any doubts, ask the SwEng staff.


## Exam rules

- Exams are open-book, so you can consult any resource during exams (website, class material, StackOverflow, etc.),
  but the resource must have been created prior to the start of the exam,
  and you must cite in your solution all resources you use (e.g., by giving the URL) that are not SwEng course materials or were not prepared by you in the context of this course;
  using such resources without citing them in exams constitutes plagiarism.
- You may use code that you yourself wrote for this class prior to the exam, or code given in the lectures.
- You are not permitted to interact with or receive/give any assistance from/to any human during exams, except for the course staff;
  any use of Slack, Discord, Facebook, e-mail, IRC, phone, SMS, Skype, TeamViewer, WhatsApp, Telegram, or similar will be automatically deemed to constitute cheating.
- Use your personal laptop for exams;
  if you do not have a laptop, notify the SwEng staff at least 24h prior to the exam, and we will provide a laptop to you on the day of the exam.
- You must do all your work locally on your laptop; doing it on a remote computer constitutes cheating.
- If you wish to leave early, you must show your CAMIPRO card to one of the course staff and await their confirmation that you can indeed leave;
  we will turn off access to your exam repo at that time.
- If you need to use the bathroom during an exam, you must be escorted by a member of the staff, otherwise it will be treated as an early departure:
  your repo will be locked, and you will not be permitted to re-enter.
- If you leave early without showing your CAMIPRO card to one of the staff, you will not receive any points for the exam.
- Toward the end of each exam, the staff may ask you to remain seated, in order to avoid disturbing students who want to work until the end of the exam timeslot.
- You are not permitted to wear headphones during exams, except for medical reasons approved by Prof. Candea.

Any violations of the exam rules will be handled as per [EPFL's cheating and plagiarism rules](https://www.epfl.ch/about/overview/wp-content/uploads/2019/09/2.4.0.2Disciplinary_Rules_Regulations_ang.pdf).
We will employ multiple plagiarism detection tools, in accordance with EPFL policies.


## Solving the coding problems

As is often the case in software engineering, exam problems themselves are fairly easy to reason about,
and what really matters is that your code employs solid software engineering principles.

You should write code that is:
- Correct and robust
- Readable and concise
- Judiciously commented

Your code should fail cleanly when it cannot continue safely, and it should throw adequately specific runtime exceptions (or custom versions of these):
- When implementing known interfaces, follow their prescribed exceptions
- Throw `IndexOutOfBoundsException` for any kind of index whose value is too low or too high
- Throw `IllegalArgumentException` for arguments other than indexes whose value is invalid
- Throw `IllegalStateException` for operations that fail because of an object's current state

Exams use Java 17.
To validate your installation, you can run `java -version` from the command line; the output should start with something like `java version "17"`.
If you do not have Java 17, you can get it from [the JDK website](https://jdk.java.net/17/). Older or newer versions may not be compatible.

You can build locally with `gradlew.bat build` on Windows or `./gradlew build` on Mac and Linux, which is also what we will use to build and test your code.
You may not modify the Gradle setup. For instance, you may not add new dependencies, or change the versions of existing dependencies.
You are free to use an editor or IDE of your choice, as long as the code and tests build using the `./gradlew build` command line.
We recommend IntelliJ, which can import this project as a Gradle project.
