# Exam Manager

This software manages exams on GitHub, using Gradescope to delegate some of the grading to humans.

Specifically, it allows one to:
- Replicate an exam folder to a folder in each user's repo
- Show and hide user repos
- Grade automated metrics such as code coverage
- Delegate manual grading to humans via Gradescope and parse the outcome
- Publish grading reports to user repos

`cd` into the `ExamManager` folder then run `dotnet run -- help` for a list of commands.


## Software requirements

You need the [.NET](https://dot.net) SDK and Git.
On Windows, the software will work even if you run it from Windows but have Git only in WSL.

Why C#? Because it's one of the few languages for which GitHub officially maintains a library (unofficial libs tend to stop working quickly as GitHub deprecates stuff all the time),
and it's mainstream so any TA for a software engineering course should be able to maintain this software.


## Other requirements

**Patience**: sometimes Git will time out while publishing exams, just re-run the publish command again, it's idempotent.

**GitHub organization**: create one, then talk to GitHub to get an educational discount so you can have private repos and such for free.
Make sure your org defaults to giving members no rights at all, not reading all repos, otherwise everyone will see everyone else's exam.
Also, disable forking of private repositories by members, so people don't accidentally fork their exam repo and answer in a fork you can't easily see.

**Exams "skeleton" repo**: create a repo, whose name you'll put in the config (see below), that contains the exams to be mirrored to student repos.
See `samples/exam-skeleton` for an example that includes a simple "mock" exam to give to students so they can test their setup,
and see the `2022/exam-3` exam in the top-level `exams` folder of this repo for the latest example of a real exam.
Be particularly mindful of the build system: you want GitHub Actions to properly build the latest exam and only that exam, and you want that build to fail if any exam question fails.

**Gradescope**: grading programming exams currently requires paid Gradescope features.
You most likely want to configure the course to use positive grading, though the actual points within Gradescope do not matter (see below).
This software uploads solutions to grade using fake emails, so don't forget to disable the "let users know they were added" Gradescope feature.

**Moodle surveys**, if using: you must assign points to the surveys in Moodle _before_ the students take them, otherwise one cannot find out which students took the survey.

**Extra files**:
- `github-token.txt` with a GitHub token that has admin rights to the org.
- `users.csv` should contain one row per user (cells are comma-delimited), with the first cell being the e-mail,
  the second cell the primary ID that will be used for repo naming, and the remaining cells alternate IDs (such as those present on student cards).
  See the `samples/users.csv` file.
- `config.json` with configuration for the current exam, see the `samples/config.json` file.
  Note that paths should always use `/` for the path separator, even on Windows.


## Grading via Gradescope

Create one Gradescope assignment per question to grade, with the following settings:
- Autograder points: 0
- Manual grading: Enabled
- Release date: Now
- End date: <whenever you want>
- Allow late submissions: Yes
- DO NOT tick "Anonymous Grading" or the API to upload submission gets disabled (this feels like a bug but Gradescope says it's by design).

Then add a "Manual grading" item on the outline with the right number of points.

Use groups as needed when grading, but do not use `:` in the description of a rubric as this will be confused for a group due to how Gradescope exports evaluations.

To ignore a rubric completely, add `SKIP|` before its name.

Markdown is allowed, but don't overdo it or the report becomes hard to read.


## After grading is done

Create an `evaluations/` folder in this folder.
For each Gradescope assignment, download the ZIP from "Review Grades" > "Export Evaluations", rename the "manual grading" CSV in there to the question name and put that CSV in `evaluations/`.

If you ran a Moodle survey, download the list of students from the "grades" page in Moodle as an Excel or ODS file,
then cut it down to just have the student IDs in the first column and the grade (a number or '-' for unanswered) in the 2nd column, _without headers_.
Then export as a CSV, renamed as the question name and put in `evaluations/`.
The actual number in the sheet doesn't matter, the points in the config will be used.


## TODOs

Useful things that nobody had time to implement yet:
- Integrate student placement in this software, so that commands like `find-unchanged` can display _where_ a student is seated.
- Find some quick way to check if we should re-invite students into their team, in case they don't respond to the invite in time, and always do it
- Add the last push time per repo when running `freeze` for specific repos, so the staff can easily see that absent students at the start of the exam haven't pushed
- Check if Gradescope accepts `/` in filenames, and consequently pick a way to make sure files don't get overwritten if two files have the same name but are in a different folder locally
- Document the format of CoverageClasses in the config (package/classname; but what about subpackages?)
- Add a timeout to the GitHub Actions config, to ensure no build lasts too long if something goes wrong; 5 minutes should be more than enough.

If you decide to extend this software, look at the `TestPlan.md` file in this folder, which contains a manual test plan since so many dependencies are external.
