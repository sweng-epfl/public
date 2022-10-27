# Introduction

Welcome to this Software Engineering course!
Let's start by discussing why you should even bother with software, why software _engineering_ is important, what the goal of this course is, and a quick introduction to modern tools.


## Why bother with software?

The goal of software is to _help humans_ by _automating tasks_.
This is all about _scale_: performing tasks that humans could not reasonably do themselves because it would take too much time or effort.

For instance, consider the United States census.
The way the United States' government knows how many people live where is by doing a census every ten years.
The government hires people who go to every house in every city in every state and ask questions about who lives there and what their demographics are.

The census used to be performed manually. This worked fine when the United States were founded, but by the end of the 19th century, scale became a problem.
Tallying the results of all houses into results for cities, states, and the whole country took a lot of time to do manually.
Because of population growth, the census started taking almost ten years to tally, meaning that by the time an iteration of the census was done,
its data was already considered obsolete and it was time for a new one.
This is where automation came in: a company developed [a machine](https://www.ibm.com/ibm/history/ibm100/us/en/icons/tabulator/)
that tallied cards in a specific format, allowing the census to take a reasonable time again.
Of course, there are plenty of other problems that do not scale when done manually, and creating one specific kind of machine for each individual problem would not scale.
This is where software comes in: we can now write _code_ that performs these tasks using a single kind of machine.
By the way, the company that made the tallying machine still survives today under the name IBM!

Software is everywhere nowadays, and thus knowing how to write and maintain software is a prized skill.
Cars contain millions of lines of code, even "old-fashioned" ones that run on gas instead of electricity.
Even appliances such as washing machines or barbecues run software complex enough that it needs regular updates to fix bugs!

Back in the early 2000s, the [most valuable companies on the planet](https://en.wikipedia.org/wiki/List_of_public_corporations_by_market_capitalization)
were mainly dealing with gas, oil, health, banking, and other such services.
These days, it's the opposite: only a small fraction of the most valuable companies deal with specific services such as health, and instead the vast majority deal with software, hardware, or both.

Software is also a key tool in science.
Scientists have developed [models of the brain](https://actu.epfl.ch/news/blue-brain-builds-neurons-with-mathematics/) in software, enabling experiments that would not be feasible on real brains.
Another example is a [brain-to-text communication](https://www.nature.com/articles/s41586-021-03506-2) technique that uses hardware to get signals directly from a human brain and software to process them,
enabling paralyzed people to communicate by thinking about writing with their hand, which sends signals from the brain, even if their body cannot actually move!

It is tempting to think that there is already enough software, but this is far from true.
For instance, at the beginning of the coronavirus pandemic, the government of Switzerland could not keep track of cases quickly enough because
[cases had to be filled out on paper forms and faxed](https://www.swissinfo.ch/eng/crunching-the-numbers_why-switzerland-struggles-to-keep-track-of-coronavirus-cases/45628604) to the federal government.
This lack of automation was a key problem in reacting quickly enough to a pandemic to help citizens.


## Why is software engineering important?

We just saw how important software itself is, but why do you need to follow a course on software _engineering_?
This is all about _trust_: enabling people to trust that the software will do what they want to do the way they want it to be done.
Users should be able to depend on software for everyday tasks, even those that could injure or kill them if performed incorrectly.

Consider cars again. Modern cars have self-driving capabilities, which are powered by software using inputs from hardware sensors.
If this software has bugs that cause errors, [crashes can happen](https://www.theguardian.com/technology/2021/aug/16/teslas-autopilot-us-investigation-crashes-emergency-vehicles).
These crashes lower users' trust in the software, which can make the entire self-driving software useless by causing people to refuse to use it.

Another kind of vehicle that users needs to trust is spaceships.
When NASA delegated some of the software building to Boeing and audited the outcome, [their investigation](https://www.businessinsider.com/nasa-investigating-potentially-catastrophic-boeing-spaceship-error-2020-2)
found "systemic issues" as the cause of a software error. That is, this error was not one person making a mistake, but the result of poor practices throughout the company.

Before NASA had machines, they employed ["computers"](https://en.wikipedia.org/wiki/Computer\_\(occupation\)): people whose job was to perform computations.
One such computer was [Katherine Johnson](https://www.nasa.gov/content/katherine-johnson-biography), who performed computations for the moon landing among other operations.
When astronaut John Glenn was set to orbit the earth in the Friendship 7 capsule, he refused to fly at first because NASA had used machines to perform the computations, which he did not trust.
Instead, he asked NASA to have Johnson check the machine's computations, famously stating "If she says they're good, then I'm ready to go". She did, and the mission was a success.

Can we get a Katherine Johnson to check all software we write? Unfortunately, the _complexity_ of modern software makes this infeasible.
Consider the following piece of code, which might be found in a self-driving car's acceleration function:
```python
# Accelerate, unless the speed is already 100
if speed >= 100:
  speed = 100
else:
  speed = speed + 1
```
This code splits the program into two paths: one in which the speed was at least 100 and got capped, and one in which the speed was under 100 and got increased.
One "if" statement led to a doubling of the number of paths.
If there was another such statement after this one, there would then be four paths.
Another one, and there would be eight, and so on.
The Apollo 11 lunar mission contained [about 150,000 lines of code](https://www.synopsys.com/blogs/software-security/apollo-11-software-development/).
If even 1% of these lines are "if" statements, that leads to 2^1500 paths.
And Apollo 11 is tiny by modern standards; the Windows operating system has tens of millions of lines of code. There aren't enough atoms in the universe to list each possible program path!

Engineering trustworthy software goes beyond the impossible problem of checking every path in the program, however.
Consider a program that takes as input a list of students with their grades, and sends an email to every student announcing their grade.
This is a good example of software automating a task that would be lengthy and error-prone to perform manually.
The software turns a line in the input such as `Alice, 9/10` into an email sent to Alice informing her of her grade.
Does it still work if, instead of having a name using English letters such as Alice, the student is named 狄仁傑 or محمد بن موسی خوارزمی?
What if the student has an American-style name such as "Bob, Jr", which includes a comma that is also used by the software to separate names and grades in the input?
What happens if, after sending the first few emails, the computer the software is running on loses its Internet connection? Do emails get lost? Does running the software again result in duplicate emails?
Can the software handle notifying users in another language than English?
For instance, in French, salutations are often gender-specific, with the English "Dear Alice"/"Dear Bob" becoming the French "_Chère_ Alice"/"_Cher_ Bob". Can the software handle this?
Even if it does handle all of the problems above, how can another person trust that this is the case?
The software's maintainer may claim it works, but that isn't enough to risk potentially sending the wrong grades to your students.
And if someone else trusts it and wants to add a feature, how can this feature be integrated back into the "main" version of the software?
Sending a version by email works if there are a few developers, but this won't scale with dozens of people making changes that might conflict.
Recall that Windows contains tens of millions of lines of code; one person alone, or even a team with dozens of people, is not enough to develop large software projects.


## The goal of this course

This course is intended to turn _students_ into _engineers_, introducing them to real-world concepts and their applications, to move from _writing code_ to _developing software_.

One key aspect of this is moving away from theoretical exercises with well-defined solutions and towards real-world exercises with debatable solutions.
For instance, a student might turn in a coding assignment and get back a grade reflecting "how good" their solution is.
But in the real world, an engineer submits a _project_ and gets _feedback_ from users. This feedback could include disagreements about the very problem the software is supposed to solve,
including changes of opinions from the customer that require software changes even if the engineer's solution was "good" from a theoretical point of view.

A useful analogy is to imagine building a plane.
If a student turns in a "95% complete" plane, they can expect to get a grade of 95%.
But if an engineer turns in a "95% complete" plane, the result is heavily dependent on which 5% are missing.
If the seats aren't as comfortable as they could be, or the maximal speed is a little below what it should be, the customer might still be happy.
But if the wing is only half finished, the plane cannot fly, even if it is "95% complete", and the customer will reject it!

In previous courses, you have learned how to write code. In this course, you will learn the other key steps of software development:
- **Requirements**: how to tell what users need, and how to translate these into software
- **Design**: how to think about software at a high level, to make software easier to write and maintain
- **Evolution**: how to take an existing piece of software and evolve it to fix bugs and add features

These steps are not entirely ordered either, as in real-world software it is common for requirements to change,
for designs to need adjustments, and for development to start by evolving an existing "legacy" piece of software.

You will also learn key tasks related to writing trustworthy and efficient code:
- **Operations**: how to keep track of software and its changes, and how to avoid human mistakes such as checking in a code change that breaks existing code
- **Testing**: how to test software in an automated fashion, providing evidence that the software does what it should, and how to use tests to help the entire development process
- **Debugging**: how to use modern tools and techniques to find and fix software bugs
- **Performance**: how to design and write efficient software, and how to find and fix performance issues
- **Security**: how to write software that is resilient to bad and malicious user inputs, and how to ensure users cannot break the confidentiality, integrity, and availability of data
- **Teamwork**: how to perform all of the above tasks in a team, at the scale of modern software codebases


## Modern tools

Thankfully, you do not have to start from scratch every time you or your team wants to engineer some software.
In fact, if you had to write software by starting from nothing with no outside help every time, you would never finish a project of reasonable size.

Software engineers use package repositories such as [Maven Central](https://mvnrepository.com/repos/central), the [NuGet Gallery](https://www.nuget.org/), 
or the [NPM Registry](https://www.npmjs.com/) to reuse existing code.
If you want your software to automatically retry operations that failed, [someone else has likely already done it](https://www.nuget.org/packages/Polly); you do not need to spend weeks
writing a new library, designing tests for it, thinking about edge cases, making it generic enough to reuse it in multiple projects, and so on.

When software engineers have problems, they do not solve them alone. While any problem could be solved with enough time, making efficient use of one's time is a key goal of engineering.
Instead, software engineers use websites such as [StackOverflow](https://stackoverflow.com/) to ask and answer questions.
Answering questions benefits the answerer because teaching is an excellent way to double-check one's understanding of a concept.


#### Exercise
Consider the following PHP code, even if you have never used PHP before:
```php
class Context {
    protected $config;
    public function getConfig($key) {
        $cnf = $this->config;
        return $cnf::getConfig($key);
    }
}
```
If you try to use this code, the PHP interpreter will give the following error: `syntax error, unexpected T_PAAMAYIM_NEKUDOTAYIM`.
What's wrong? _(Hint: this is not a problem you can solve by staring at the code, use tools!)_
<details>
<summary>Solution (click to expand)</summary>
<p>

Looking up this odd name in your favorite search engine will find existing questions
teaching you that this is actually Hebrew, the PHP authors' language, for "double colon".
The error is that instead of a double colon, the code should use an arrow `->`.

</p>
</details>

#### Exercise
If a colleague told you that they received a bug report from a user of the "Win32" version of your app, named after the old programming interface for Windows,
with the error code 39, could you tell what caused the error?
<details>
<summary>Solution (click to expand)</summary>
<p>

Looking up "win32 error code 39" or something similar in your favorite search engine will find Microsoft's documentation for the Win32 programming interface,
on which you will learn that error code 0x27, which is the number 39 in hexadecimal, means the disk is full.
You could then discuss how to best handle this error, such as by showing a helpful message to the user, or perhaps deleting some of your app's temporary files and trying again.

</p>
</details>


## A word about this course's method

This course is not a "traditional" lecture-based course in which you listen to a lecture, or read lecture notes, passively.
Research in teaching and learning has shown that interactivity improves learning, and thus there will be frequent exercises within lectures, such as the ones above.
There are also traditional exercises that you can complete after the lectures to test your own understanding of the material.
Exams will look just like exercises, as their goal is also to test your understanding of the material.
