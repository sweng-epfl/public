# Notes from grading

**Question 1**: Avoid stating unrealistic assumptions, such as the use of Model-View-Presenter or server-side prefetching.

**Question 2**: Remember compatibility exists at both the source and binary levels, changing the return type is never binary compatible

**Question 3**: Abstraction leaks have nothing to do with information leaks, they are about "is this at the right level of abstraction?".
I/O is anything Input/Output-related, such as networking, disks, and so on, not only "the disk might be too full".
Checked exceptions means that it's not the code's fault but expected errors such as potential I/O or backend issues,
not anything related to compile-time vs run-time, easy vs hard to handle, or precise vs abstract definitions.

**Question 4**: Use design patterns! They are well-known solutions to well-known problems, in real life if you try to submit spaghetti code it will most likely
be rejected by your teammates and you will have wasted time.

**Question 5**: No need to track users and attempts in separate data structures, a map from users to number of attempts is the only state needed.


# Software Engineering - Exam 2

Grading will be done on the state of the `exam-GASPAR` repository at **12:00 on Friday, November 19, 2021**, and access to this repo will be terminated at that point.

There are two kinds of questions in this exam: free-form theory questions and code questions.
For free-form theory questions, answer in the space indicated by question marks `???`.
For code questions, answer in the files mentioned in the question, without touching other files.

> :information_source: If you use an IDE such as IntelliJ, make sure to import each code question on its own
> by importing its build.gradle file. If you import the entire exam as a project instead, you will get build errors.

The exam is worth 100 points in total, and it consists of five questions plus a feedback survey:
- [Question 1](Q1.md), worth 10 points
- [Question 2](Q2.md), worth 15 points
- [Question 3](Q3.md), worth 15 points
- [Question 4](Q4/README.md), worth 19 points
- [Question 5](Q5/README.md), worth 39 points

The feedback survey is [on Moodle](https://moodle.epfl.ch/mod/questionnaire/view.php?id=1182061), worth 2 points.
You must complete it before Friday, November 19, 23:59, but you do not need to do so during the exam.
Your answers are automatically anonymized by Moodle; it only tells us who filled in the survey and who didn't.
