# The 5 WHYs

This is an iterative technique for exploring the cause-and-effect chain that leads to an observed problem. The idea is to iterate through asking "why" until you figure out what to fix (rule of thumb is that you get there by the 5th why) – you get there by following the relationship between different root causes of a problem. It was articulated in the early 20th century by Sakichi Toyoda, founder of Toyota Industries and widely considered "the father of the Japanese industrial revolution".  It is now widely used in manufacturing quality control and improvement, including Kaizen, lean manufacturing, and Six Sigma.

1. Was late for SwEng lecture. Why?
1. &rightarrow; I woke up late. Why?
1. &rightarrow; My alarm did not go off. Why?
1. &rightarrow; My smartphone ran out of battery. Why?

Because I forgot to plug it in before going to sleep

⇒ put a note on the night stand to remind me to plug the phone in at night

## How does it work?

Step 1: Write down the problem you are facing as precisely and completely as possible. This clarifies the problem and brings the team onto the same page.

Step 2: Ask _Why_ the problem happens. Write the answer below the problem statement.

Step 3: Does the answer obviously point to the root cause? If yes, you're done. If not, repeat step #2 with the answer being the new problem statement.

Example:

1. App appears to hang. Why?
1. &rightarrow; App is blocked on user input, but user does not see any indication that input is needed. Why?
1. &rightarrow; Bob's code queries for a username, but Alice didn't put an EditText object in the UI. Why?
1. &rightarrow; Alice didn't know that the input was needed. Why?
1. &rightarrow; Bob didn't tell Alice. Why?

Because they're not talking to each other.

⇒ resolve the internal team conflict

## How to apply it? 

The approach is most useful when the problems involve human factors or interactions, such as in a team. You typically stop when you've identified a broken process or an alterable behavior (i.e., indicative of a true root-cause).

Important to do:

- Distinguish causes from symptoms (focus on causality instead of correlation)
- Avoid assumptions and logic traps (identify causality in direct increments, don't skip steps)
- Double-check the cause-and-effect chain by reversing the sentences and saying "and therefore"
- Maximize the precision of the answers

The "classic example":

1. The battery is dead. Why?
1. &rightarrow; The alternator is not functioning. Why?
1. &rightarrow; The alternator belt has broken. Why?
1. &rightarrow; The alternator belt was well beyond its useful service life and not replaced. Why?
1. &rightarrow; The car was not maintained according to the recommended service schedule. Why?

The last WHY typically doesn't point to a solution but rather points to a processes (hence the applicability of the approach to problems involving human factors). The answer to the last WHY doesn’t tell you how to fix the problem, but is quite good at identify the root cause. You can then change the process to avoid recurrence of the root cause.