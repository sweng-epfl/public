# Logistics

## Timing

Each lecture is 120 minutes, including the exercises during the lecture.
This translates to 30 minutes + 2x45 minutes, which means a lecture fills three 45-minute slots with 15 minutes to spare at the beginning.
This beginning slot can be used for (1) administrivia during the first lecture, (2) quizzes during most lectures to recap the previous one, and (3) exam reviews for lectures after an exam.


## Requirements

This course assumes students have a computer on which they can install standard programming tools,
and that they have access to this computer _during lectures_ for in-lecture exercises.

The first lecture, as an exception, does not require a computer. Its exercises can be done orally and with a smartphone.

The second and third lecture require students to install software.
These requirements are explicitly stated at the top of their notes, and should be communicated in advance to avoid stressing students at the start of the corresponding lecture.


## Assessment

The course is designed to be assessed with exams similar to the post-lecture exercises.
Each exam contains some theoretical questions, answered with text, and some practical questions, answered with code.
Grading the coding part is partly automated: the basic correctness of code can be assessed with automated tests, and the coverage of test code can be measured automatically.

To provide fair and valid assessments,
all post-lecture exercises have not only solutions but also assessment criteria similar to those of an exam, though less detailed.
This way, students can tell whether their solution matches what is expected, especially with regards to design.
For instance, solving a problem in a "brute force" manner by writing lots of code without thinking should get a worse mark than a thoughtful design
that solves the problem in a maintainable manner.
As another example, a test suite achieving good code coverage with unreliable and poorly-written tests should get a worse mark than one achieving slightly worse
coverage but with reliable and maintainable tests.

In general, exercises and exams should be graded as if they were real-world scenarios. Would a pull request containing the student's answer be merged as is?
Would it receive minor "nitpicking" feedback, or perhaps major feedback? Would it be closed as too poor in quality to even review?
