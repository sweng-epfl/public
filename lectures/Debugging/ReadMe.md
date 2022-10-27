# Debugging

> **Prerequisite**: You are _strongly_ encouraged, though not strictly required, to use an IDE for the exercises about debugging in this lecture.
> Alternatively, you can use Java's built-in command-line debugger, `jdb`, but it is far less convenient than a graphical user interface.

Writing code is only one part of software engineering; you will often spend more time _reading_ code than writing code.
This may be to understand a piece of code so you can extend it, or _debug_ existing code by finding and fixing bugs.
These tasks are a lot easier if you write code with readability and debuggability in mind, and if you know how to use debugging tools.


## Objectives

After this lecture, you should be able to:

- Develop _readable_ code
- Use a _debugger_ to understand and debug code
- Isolate the _root cause_ of a bug
- Develop _debuggable_ code


## What makes code readable?

Take a look at [planets.py](exercises/lecture/planets.py) in the in-lecture exercise folder.
Do you find it easy to read and understand? Probably not.
Did you spot the fact that sorting doesn't work because the code uses a function that returns the sorted list, but does not use its return value, rather than an in-place sort?
It's a lot harder to spot that bug when you have to use so much brain power just to read the code.

Unfortunately, hard-to-read code doesn't only happen in exercises within course lecture notes.
Consider the following snipped from the ScalaTest framework:

```scala
package org.scalatest.tools

object Runner {
  def doRunRunRunDaDoRunRun(...): Unit
}  
```

What does this method do? The method name isn't going to help you. It's a reference to [a song from 1963](https://en.wikipedia.org/wiki/Da_Doo_Ron_Ron).
Sure, it's a funny reference, but wouldn't you rather be reading a name that told you what the method does?
Worse, [the method has 21 parameters](https://github.com/scalatest/scalatest/blob/282da2aff8f439906fe5e7d9c3112c84019545e7/jvm/core/src/main/scala/org/scalatest/tools/Runner.scala#L1044-L1066).
You can of course understand what the method does if you spend enough time on it, but should you have to?

Let's talk about five components of code readability: naming, documentation, comments, formatting, and consistency.

### Names

First, an example of _naming_ without even using code: measurement errors.
If you measure the presence of something that's absent, that's a `type I error`. If you measure the absence of something that's present, that's a `type II error`.
These kinds of errors happen all the time in, for instance, tests to detect viruses.
However, it's easy to forget which is type `I` and which is type `II`.
And even if you remember, a typo duplicating a character can completely change the meaning of a sentence.
Instead, you can use `false positive` and `false negative`.
These names are easier to understand, easier to remember, and more resilient to spelling mistakes.

Names in code can also make the difference between code that's easy to understand and code that's hard to even mentally parse.
A variable named `isUserOnline` in Java is fine... as long as it really is for an "is the user online?" boolean variable, that is. If it's an integer, it's a lot more confusing.
And if you're writing Python instead, it's also a problem since Python uses underscores to separate words, a.k.a. "snake case", so it should be `is_user_online`.
A variable named `can_overwrite_the_data` is quite a long name, which isn't a problem by itself, but it's redundant: of course we're overwriting "data", the name is too vague.

Names are not only about variable, method, or class names. Consider the following method call:

```java
split("abc", "a", true, true)
```

What does this method do? Good question. What if the call looked like this instead, with constants or enums?

```java
split("abc", "a",
      SplitOptions.REMOVE_EMPTY_ENTRIES,
      CaseOptions.IGNORE_CASE)            
```

This is the same method call, but we've given names instead of values, and now the meaning is clearer.
These constants are only a workaround for Java's lack of named parameters, though. In Scala, and other languages like C#, you could call the method like this:

```scala
split("abc",
      separator = "a",
      removeEmptyEntries = true,
      ignoreCase = true)                  
```

The code is now much more readable thanks to explicit names, without having to write extra code.

A cautionary tale in good intentions with naming is _Hungarian notation_.
Charles Simonyi, a Microsoft engineer from Hungary, had the good idea to start his variable names with the precise data type the variables contained, such as `xPosition` for a position on the X axis,
or `cmDistance` for a distance in centimeters. This meant anyone could easily spot the mistake in a like such as `distanceTotal += speedLeft / timeLeft`, since dividing speed by time does not make a distance.
This became known as "Hungarian notation", because in Simonyi's native Hungary, names are spelled with the family name first, e.g., "Simonyi Károly".
Unfortunately, another group within Microsoft did not quite understand what Simonyi's goal was, and instead thought it was about the variable type.
So they wrote variable names such as `cValue` for a `char` variable, `lIndex` for a `long` index, and so on, which makes the names harder to read without adding any more information than is already in the type.
This became known as "Systems Hungarian", because the group was in the operating systems division, and unfortunately Systems Hungarian made its way throughout the "Win32" Windows APIs,
which were the main APIs for Windows until recently. Lots of Windows APIs got hard-to-read names because of a misunderstanding!
Once again, naming is not the only way to solve this issue, only one way to solve it. In the F# programming language, you can declare variables with units, such as `let distance = 30<cm>`,
and the compiler will check that comparisons and computations make sense given the units.

---
#### Exercise
The following names all look somewhat reasonable, why are they poor?
- `pickle` (in Python)
- `binhex` (in Python)
- `LinkedList<E>` (in Java's `java.util`)
- `vector<T>` (in C++'s `std`)
- `SortedList` (in C#'s `System.Collections`)
<details>
<summary>Answers (click to expand)</summary>
<p>

`pickling` is a rather odd way to refer to serializing and deserializing data as "preserving" it.

`binhex` sounds like a name for some binary and hexadecimal utilities, but it's actually for a module that handles an old Mac format.

A linked list and a doubly linked list are not the same thing, yet Java names the latter as if it was the former.

A vector has a specific meaning in mathematics; C++'s `vector` is really a resizeable array.

`SortedList` is an acceptable name for a sorted list class. But the class with that name is a sorted map!

</p>
</details>

---


Overall, names are a tool to make code clear and succinct, as well as consistent with other code so that readers don't have to explicitly think about names.

### Documentation

_Documentation_ is a tool to explain _what_ a piece of code does.

Documentation is the main way developers learn about the code they use. When writing code, developers consult its documentation comments, typically within an IDE as tooltips.
Documentation comments should thus succinctly describe what a class or method does, including information developers are likely to need such as whether it throws exceptions,
or whether it requires its inputs to be in some specific format.

### Comments

_Comments_ are a tool to _why_ a piece of code does what it does.
Importantly, comments should not say _how_ a piece of code does what it does, as this information already exists in the code itself.

Unfortunately, not all code is "self-documenting".
Comments are a way to explain tricky code.
Sometimes, code has to be written in a way that looks overly complicated or wrong because the code is working around some problem in its environment, such as a bug in a library,
or a compiler that only produces fast assembly code in specific conditions.

Consider the following good example of a comment, taken from an old version of the Java development kit's `libjsound`:

```c
/* Workaround: 32bit app on 64bit linux gets assertion failure trying to open ports.
   Until the issue is fixed in ALSA (bug 4807) report no midi in devices in the configuration. */
if (jre32onlinux64) { return 0; }
```

This is a great example of an inline comment: it explains what the external problem is, what the chosen solution is, and refers to an identifier for the external problem.
This way, a future developer can look up that bug in ALSA, a Linux audio system, and check if was fixed in the meantime so that the code working around the bug can be deleted.

Inline comments are a way to explain code beyond what code itself can do, which is often necessary even if it ideally should not be.
This explanation can be for the people who will review your code before accepting your proposed changes, or for colleagues who will read your code when working on the codebase months later.
Don't forget that one of these "colleagues" is likely "future you". No matter how clear you think a piece of code is right now, future you will be grateful for comments that explain the non-obvious parts.

### Formatting

Formatting code is all about making it easier to read code. You don't notice good formatting, but you do notice bad formatting, and it makes it harder to focus.

Here is a real world example of bad formatting:

```c
if (!update(&context, &params))
  goto fail;
  goto fail;    
```

Did you spot the problem? The code looks like the second `goto` is redundant, because it's formatted that way.
But this is C. The second `goto` is actually outside of the scope of the `if`, and is thus always executed.
This was a real bug that triggered [a vulnerability](https://nakedsecurity.sophos.com/2014/02/24/anatomy-of-a-goto-fail-apples-ssl-bug-explained-plus-an-unofficial-patch/) in Apple products.

Some languages enforce at least some formatting consistency, such as Python and F#. But as you saw with the `planets.py` exercise earlier, that does not mean it's impossible to format one's code poorly.

### Consistency

Should you use `camelCase` or `snake_case` for your names? 4 or 8 spaces for indentation? Or perhaps tabs? So many questions.

This is what _conventions_ are for. The entire team decides, once, what to do.
Then every member of the team accepts the decisions, and benefits from a consistent code style without having to explicitly think about it.

Beware of a common problem called _bikeshedding_ when deciding on conventions.
The name comes from the story illustrating it: a city council meeting has two items on the agenda, the maintenance of a nuclear power plant and the construction of a bike shed.
The council quickly approves the nuclear maintenance, which is very expensive, because they all agree that this maintenance is necessary to continue to provide electricity to the city.
Then the council spends an hour discussing the plans for the bike shed, which is very cheap. Isn't it still too expensive? Surely the cost can be reduced a bit, a bike shed should be even cheaper.
Should it be blue or red? Or perhaps gray? How many bikes does it need to contain?
It's easy to spend lots of time on small decisions that ultimately don't matter much, because it's easy to focus on them.
But that time should be spent on bigger decisions that are more impactful, even if they are harder to discuss.

Once you have agreed on a convention, you should use tools to enforce it, not manual efforts.
Command-line tools exist, such as `clang-format` for C code, as well as tools built into IDEs, which can be configured to run whenever you save a file.
That way, you no longer have to think about what the team's preferences are, tools do it for you.


## How can one efficiently debug a program?

Your program just got a bug report from a user: something doesn't work as expected. Now what?
The presence of a bug implies that the code's behavior doesn't match the intent of the person who wrote it.

The goal of _debugging_ is to find and fix the _root cause_ of the bug, i.e., the problem in the code that is the source of the bug.
This is different from the _symptoms_ of the bug, in the same way that symptoms of a disease such as loss of smell are different from a root cause such as a virus.

You will find the root cause by repeatedly asking "_why?_" when you see symptoms until you get to the root cause.
For instance, let's say you have a problem: you arrived late to class.
Why? Because you woke up late.
Why? Because your alarm didn't ring.
Why? Because your phone had no battery.
Why? Because you forgot to plug your phone before going to sleep.
Aha! That's the cause of the bug. If you had stopped at, say, "your alarm didn't ring", and tried to fix it by adding a second phone with an alarm, you would simply have two alarms that didn't ring,
because you would forget to plug in the second phone as well.
But now that you know you forgot to plug in your phone, you can attack the root cause, such as by putting a post-it above your bed reminding you to charge your phone.
You could in theory continue asking "why?", but it stops being helpful after a few times.
In this case, perhaps the "real" root cause is that you forget things often, but you cannot easily fix that.

At a high level, there are three steps to debugging: reproduce the bug, isolate the root cause, and debug.

Reproducing the bug means finding the conditions under which it appears:
- What environment?
  Is it on specific operating systems? At specific times of the day? In specific system languages?
- What steps need to be taken to uncover the bug?
  These could be as simple as "open the program, click on the 'login' button, the program crashes", or more complex, such as creating
  multiple users with specific properties and then performing a sequence of tasks that trigger a bug.
- What's the expected outcome? That is, what do you expect to happen if there is no bug?
- What's the actual outcome? This could be simply "a crash", or it could be something more complex, such as "the search results are empty even though there is one item matching the search in the database"
- Can you reproduce the bug with an automated test? This makes it easier and less error-prone to check if you have fixed the bug or not.

Isolating the bug means finding roughly where the bug comes from.
For instance, if you disable some of your program's modules by commenting out the code that uses them, does the bug still appear? Can you find which modules are necessary to trigger the bug?
You can also isolate using version control: does the bug exist in a previous commit? If so, what about an even older commit? Can you find the one commit that introduced the bug?
If you can find which commit introduced the bug, and the commit is small enough, you have drastically reduced the amount of code you need to look at.

Finally, once you've reproduced and isolated the bug, it's time to debug: see what happens and figure out why.
You could do this with print statements:
```c
printf("size: %u, capacity: %u\n", size, capacity);
```
However, print statements are not convenient. You have to write potentially a lot of statements, especially if you want to see the values within a data structure or an object.
You may not even be able to see the values within an object if they are private members, at which point you need to add a method to the object just to print some of its private members.
You also need to manually remove prints after you've fixed the bug.
Furthermore, if you realize while executing the program that you forgot to print a specific value, using prints forces you to stop the program, add a print, and run it again, which is slow.

Instead of print statements, use a tool designed for debugging: a debugger!

### Debuggers

A debugger is a tool, typically built into an IDE, that lets you pause the execution of a program wherever you want, inspect what the program state is and even modify the state,
and generally look at what a piece of code is actually doing without having to modify that piece of code.

One remark about debuggers, and tools in general: some people think that not using a tool, and doing it the "hard" way instead, somehow makes them better engineers.
This is completely wrong, it's the other way around: knowing what tools are available and using them properly is key to being a good engineer.
Just like you would ignore people telling you to not take a flashlight and a bottle of water when going hiking in a cave, ignore people who tell you to not use a debugger or any other tool you think is useful.

Debuggers also work for software that runs on other machines, such as a server, as long as you can launch a debugging tool there, you can run the graphical debugger on your machine to debug a program on a remote machine.
There are also command-line debuggers, such as `jdb` for Java or `pdb` for Python, though these are not as convenient since you must manually input commands to, e.g., see what values variables have.

The one prerequisite debuggers have is a file with _debug symbols_: the link between the source code and the compiled code.
That is, when the program executes a specific line of assembly code, what line is it in the source code? What variables exist at that point, and in which CPU registers are they?
This is of course not necessary for interpreted languages such as Python, for which you have the source code anyway.
It is technically possible to run a debugger without debug symbols, but you then have to figure out how the low-level details map to high-level concepts yourself, which is tedious.

Let's talk about five key questions you might ask yourself while debugging, and how you can answer them with a debugger.

_Does the program reach this line?_
Perhaps you wonder if the bug triggers when a particular line of code executes.
To answer this question, use a _breakpoint_, which you can usually do by right-clicking on a line and selecting "add a breakpoint" from the context menu.
Once you have added a breakpoint, run the program in debug mode, and execution will pause once that line is reached.
Debuggers typically allow more advanced breakpoints as well, such as "break only if some condition holds", or "break once every N times this line is executed".

You can even use breakpoints to print things instead of pausing execution.
Wait, didn't we just say prints weren't a good idea? The reason why printing breakpoints are better is that you don't need to edit the code, and thus don't need to revert those edits later,
and you can change what is printed where while the program is running.

_What's the value of this variable?_
You've added a breakpoint, the program ran to it and paused execution, now you want to see what's going on.
In an IDE, you can typically hover your mouse over a variable in the source code to see its value while the program is paused, as well as view a list of all local variables.
This includes the values within data structures such as arrays, and the private members in classes.
You can also execute code to ask questions, such as `n % 12 == 0` to see if `n` is currently a multiple of 12, or `IsPrime(n)` if you have an `IsPrime` method and want to see if what it returns for `n`.

_What if instead...?_
You see that a variable has a value you didn't expect, and wonder if the bug would disappear if it had a different value.
Good news: you can try exactly that. Debuggers typically have some kind of "command" window where you can write lines such as `n = 0` to change the value of `n`, or `lst.add("x")` to add `"x"` to the list `lst`.

_What will happen next?_
The program state looks fine, but maybe the next line is what causes the problem?
"Step" commands let you execute the program step-by-step, so that you can look at the program state after executing each step to see when something goes awry.
"Step into" will let you enter any method that is called, "step over" will go to the next line instead of entering a method, and "step out" will finish executing the current method.
Some debuggers have additional tools, such as a "smart" step that only goes inside methods with more than a few lines.
Depending on the programming language and the debugger, you might even be able to change the instruction pointer to whichever line of code you want, and edit some code on the fly without having to stop program execution.

Note that you don't have to use the mouse to run the program and run step by step: debuggers typically have keyboard shortcuts, such as using F5 to run, F9 to step into, and so on, and you can usually customize those.
Thus, your workflow will be pressing a key to run, looking at the program state after the breakpoint is hit, then pressing a key to step, looking at the program state, stepping again, and so on.

_How did we get here?_
You put a breakpoint in a method, the program reached it and paused execution, but how did the program reach this line?
The _call stack_ is there to answer this: you can see which method called you, which method called that one, and so on.
Not only that, but you can view the program state at the point of that call. For instance, you can see what values were given as arguments to the method who called the method who called the method you are currently in.

_What happened to cause a crash?_
Wouldn't it be nice if you could see the program state at the point at which there was a crash on a user's machine?
Well, you can! The operating system can generate a _crash dump_ that contains the state of the program when it crashes, and you can load that crash dump into a debugger,
along with the source code of the program and the debugging symbols, to see what the program state was. This is what happens when you click on "Report the problem to Microsoft" after your Word document crashed.
Note that this only works with crashes, not with bugs such as "the behavior is not what I expect" since there is no automated way to detect such unexpected behavior.


### Debugging in practice

When using a debugger to find the root cause of a bug, you will add a breakpoint, run the program until execution pauses to inspect the state, optionally make some edits to the program given your observations,
and repeat the cycle until you have found the root cause.

However, sometimes you cannot figure out the problem on your own, and you need help.
This is perfectly normal, especially if you are debugging code written by someone else.
In this case, you can ask a colleague for help, or even post on an Internet forum such as [StackOverflow](https://stackoverflow.com).
Come prepared, so that you can help others help you. What are the symptoms of the bug? Do you have an easy way to reproduce the bug? What have you tried?

Sometimes, you start explaining your problem to a colleague, and during your explanation a light bulb goes off in your head: there's the problem!
Your colleague then stares at you, happy that you figured it out, but a bit annoyed to be interrupted.
To avoid this situation, start by _rubber ducking_: explaining your problem to a rubber duck, or to any other inanimate object.
Talk to the object as if it was a person, explaining what your problem is.
The reason this works is that when we explain a problem to someone else, we typically explain what is actually happening, rather than what we wish was happening.
If you don't understand the problem while explaining it to a duck, at least you have rehearsed how you will explain the bug, and you will be able to better explain it to a human.

There is only one way to get better at debugging: practice doing it. Next time you encounter a bug, use a debugger.
The first few times, you may be slower than you would be without one, but after that your productivity will skyrocket.

---
#### Exercise
Run the code in the [binary-tree](exercises/lecture/binary-tree) folder.
First, run it. It crashes! Use a debugger to add breakpoints and inspect what happens until you figure out why, and fix the bugs.
Note that the crash is not the only bug.

<details>
<summary>Solution (click to expand)</summary>
<p>

First, there is no base case to the recursive method that builds a tree, so you should add one to handle the `list.size() == 0` case.

Second, the bounds for sub-lists are off: they should be `0..mid` and `mid+1..list.size()`.

There is a correctness bug: the constructor uses `l` twice, when it should set `right` to `r`. This would not have happened if the code used better names!

We provide a [corrected version](exercises/solutions/lecture/BinaryTree.java).

</p>
</details>


## What makes code debuggable?

The inventor [Charles Babbage](https://en.wikipedia.org/wiki/Charles_Babbage) once said about a machine of his
"_On two occasions,  I have been asked 'Pray, Mr. Babbage, if you put into the machine wrong figures, will the right answers come out?'_
_I am not able to rightly apprehend the kind of confusion of ideas that could provoke such a question._"

It should be clear that a wrong input cannot lead to a correct output.
Unfortunately, often a wrong input leads to a wrong output _silently_: there is no indication that anything wrong happened.
This makes it hard to find the root cause of a bug.
If you notice something is wrong, is it because the previous operation did something wrong?
Or because the operation 200 lines of code earlier produced garbage that then continued unnoticed until it finally caused a problem?

[Margaret Hamilton](https://en.wikipedia.org/wiki/Margaret_Hamilton_\(software_engineer\)),
who coined the term "software engineering" to give software legitimacy based on her experience developing software for early NASA missions,
[said](https://www.youtube.com/watch?v=ZbVOF0Uk5lU) of her early work "we learned to spend more time up front [...] so that we wouldn't waste all that time debugging".

We will see three methods to make code debuggable: defensive programming, logging, and debug-only code.

### Defensive programming

Bugs are attacking you, and you must defend your code and data!
That's the idea behind _defensive programming_: make sure that problems that happen outside of your code cannot corrupt your state or cause you to return garbage.
These problems could for instance be software bugs, humans entering invalid inputs, humans deliberately trying to attack the software, or even hardware corruption.

It may seem odd to worry about hardware corruption, but it happens more often than one thinks;
for instance, a single bit flip can turn `microsoft.com` into `microsfmt.com`, since `o` is usually encoded as `01101101` in binary, which can flip to `01101111`, the encoding for `m`.
Software that intends to talk to `microsoft.com` could thus end up receiving data from `microsfmt.com` instead, which may be unrelated, specifically set up for attacks, or
[an experiment to see how much this happens](http://dinaburg.org/bitsquatting.html).

Instead of silently producing garbage, code should fail as early as possible.
The closest a failure is to its root cause, the easier it is to debug.

The key tool to fail early is _assertions_.
An assertion is a way to check whether the code actually behaves in the way the engineer who wrote it believes it should.

For instance, if a piece of code finds the `index` of a `value` in an `array`, an engineer could write the following immediately after finding `index`:
```java
if (array[index] != val) {
  throw new AssertionError(...);
}
```
If this check fails, there must be a bug in the code that finds `index`. The "isolate the bug" step of debugging is already done by the assertion.

What should be done if the check fails, though? Crash the program?
This depends on the program. Typically, if there is a way to cut off whatever caused the problem from the rest of the program, that's a better idea.
For instance, the current _request_ could fail.
This could be a request made to a server, for instance, or an operation triggered by a user pressing a button.
The software can then display a message stating an error occurred, enabling the user to either retry or do something else.
However, some failures are so bad that it is not reasonable to continue.
For instance, if the code loading the software's configuration fails an assertion, there is no point in continuing without the configuration.

An assertion that must hold when calling a method is a _precondition_ of that method.
For instance, an `int pickOne(int[] array)` method that returns one of the array's elements likely has as precondition "the array isn't `null` and has at least `1` element".
The beginning of the method might look like this:
```java
int pickOne(int[] array) {
  if (array == null || array.length == 0) {
    throw new IllegalArgumentException(...);
  }
  // ...
}
```
If a piece of code calls `pickOne` with a null or empty array, the method will throw an `IllegalArgumentException`.

Why bother checking this explicitly when the method would fail early anyway if the array was null or empty, since the method will dereference the array and index its contents?
The type of exception thrown indicates _whose fault it is_.
If you call `pickOne` and get a `NullPointerException`, it is reasonable to assume that `pickOne` has a bug, because this exception indicates
the code of `pickOne` believes a given reference is non-null, since it dereferences it, yet in practice the reference is null.
However, if you call `pickOne` and get an `IllegalArgumentException`, it is reasonable to assume that your code has a bug,
because this exception indicates you passed an argument whose value is illegal.
Thus, the type of exception helps you find where the bug is.

An assertion that must hold when a method returns is a _postcondition_ of that method.
In our example, the postcondition is "the returned value is some value within the array", which is exactly what you call `pickOne` to get.
If `pickOne` returns a value not in the array, code that calls it will yield garbage, because the code expected `pickOne` to satisfy its contract yet this did not happen.
It isn't reasonable to insert assertions every time one calls a method to check that the returned value is acceptable; instead, it's up to the method to check that it honors its postcondition.
For instance, the end of `pickOne` might look like this:
```java
int result = ...
if (!contains(array, result)) {
  throw new AssertionError(...);
}
return result;
```
This way, if `result` was computed incorrectly, the code will fail before corrupting the rest of the program with an invalid value.

Some assertions are both pre- and postconditions for the methods of an object: _object invariants_.
An invariant is a condition that always hold from the outside.
It may be broken during an operation, as long as this is not visible to the outside world because it is restored before the end of the operation.
For instance, consider the following fields for a stack:
```java
class Stack {
  private int[] values;
  private int top; // top of the stack within `values`
}
```
An object invariant for this class is `-1 <= top < values.length`, i.e., either `top == -1` which means the stack is empty, or `top` points to the top value of the stack within the array.
One way to check invariants is to write an `assertInvariants` method that asserts them and call it at the end of the constructor and the beginning and end of each method.
All methods of the class must preserve the invariant so that they can also rely on it holding when they get called.
This is one reason encapsulation is so useful: if anyone could modify `values` or `top` without going through the methods of `Stack`,
there would be no way to enforce this invariant.

Consider the following Java method:
```java
void setWords(List<String> words) {
  this.words = words;
}
```
It seems trivially correct, and yet, it can be used in the following way:
```
setWords(badWords);
badWords.add("Bad!");
```
Oops! Now the state of the object that holds `words` has been modified from outside of the object, which could break any invariants the object is supposed to have.

To avoid this and protect one's state, _data copies_ are necessary when dealing with mutable values:
```java
void setWords(List<String> words) {
  this.words = new ArrayList<>(words);
}
```
This way, no changes can occur to the object's state without its knowledge.
The same goes for reads, with `return this.words` being problematic and `return new ArrayList<>(this.words)` avoiding the problem.

Even better, if possible the object could use an _immutable_ list for `words`, such as Scala's default `List[A]`.
This fixes the problem without requiring data copies, which slow down the code.

---
#### Exercise
Check out the code in the [stack](exercises/lecture/stack) folder, which contains an `IntStack` class and a usage example.
Add code to `IntStack` to catch problems early, and fix any bugs you find in the process.
First, look at what the constructor should do. Once you've done that, add an invariant and use it, and a precondition for `push`.
Then fix any bugs you find.

<details>
<summary>Solution (click to expand)</summary>
<p>

First, the constructor needs to throw if `maxSize < 0`, since that is invalid.

Second, the stack should have the invariant `-1 <= top < values.length`, as discussed above.

After adding this invariant, note that `top--` in `pop` can break the invariant since it is used unconditionally. The same goes for `top++` in `push`.
These need to be changed to only modify `top` if necessary.

To enable the users of `IntStack` to safely call `push`, one can expose an `isFull()` method, and use it as a precondition of `push`.

We provide a [corrected version](exercises/solutions/lecture/Stack.java).

</p>
</details>


### Logging

_What happened in production?_
If there was a crash, then you can get a crash dump and open it in a debugger.
But if it's a more subtle bug where the outcome "looks wrong" to a human, how can you know what happened to produce this outcome?

This is where _logging_ comes in: recording what happens during execution so that the log can be read in case something went wrong.
One simple way to log is print statements:
```python
print("Request: " + request)
print("State: " + state)
```
This works, but is not ideal for multiple reasons.
First, the developer must choose what to log at the time of writing the program.
For instance, if logging every function call is considered too much for normal operation, then the log of function calls will never be recorded, even though in some cases it could be useful.
Second, the developer must choose how to log at the time of writing the program.
Should the log be printed to the console? Saved to a file? Both? Perhaps particular events should send an email to the developers?
Third, using the same print function for every log makes it hard to see what's important and what's not so important.

Instead of using a specific print function, logging frameworks provide the abstraction of a log with multiple levels of importance, such as Python's logging module:
```python
logging.debug("Detail")
logging.info("Information")
logging.warning("Warning")
logging.error("Error")
logging.critical("What a Terrible Failure")
```
The number of log levels and their name changes in each logging framwork, but the point is that there are multiple ones and they do not imply anything about where the log goes.

Engineers can write logging calls for everything they believe might be useful, using the appropriate log level, and decide _later_ what to log and where to log.
For instance, by default, "debug" and "info" logs might not even be stored, as they are too detailed and not important enough.
But if there is currently a subtle bug in production, one can enable them to see exactly what is going on, without having to restart the program.
It may make sense to log errors with an email to the developers, but if there are lots of errors the developers are already aware of,
they might decide to temporarily log errors to a file instead, again without having to restart the program.

It is important to think about privacy when writing logging code.
Logging the full contents of every request, for instance, might lead to logging plain text passwords for a "user creation" function.
If this log is kept in a file, and the server is hacked, the attackers will have a nice log of every password ever set.

### Debug-only code

What about defensive programming checks and logs that are too slow to reasonably enable in production?
For instance, if a graph has as invariant "no node has more than 2 edges", but the graph typically has millions of nodes, what to do?

This is where _debug-only_ code comes in.
Programming languages, their runtimes, and frameworks typically offer ways to run code only in debug mode, such as when running automated tests.

For instance, in Python, one can write an `if __debug__:` block, which will execute only when the code is not optimized.

It's important to double-check what "debug" and "optimized" mean in any specific context.
For instance, in Python `__debug__` is `True` by default, unless the interpreter is run with the `-O` switch, for "optimize".
In Java, assertions are debug-only code but they are disabled by default, and can be enabld with the `-ea` switch.
Scala has multiple levels of debug-only code that are all on by default but can be selectively disabled with the `-Xelide-below` switch.

Even more important, before writing debug-only code, think hard about what is "reasonable" to enable in production given the workloads you have.
Spending half a second checking an invariant is fine in a piece of code that will take seconds to run because it makes many web requests, for instance,
even though half a second is a lot in CPU time.

Keep in mind what [Tony Hoare](https://en.wikipedia.org/wiki/Tony_Hoare), one of the pioneers of computer science and especially programming languages and verification,
once said in his ["Hints on Programming Language Design"](https://dl.acm.org/doi/abs/10.5555/63445.C1104368):
"_It is absurd to make elaborate security checks on debugging runs, when no trust is put in the results,_
_and then remove them in production runs, when an erroneous result could be expensive or disastrous._
_What would we think of a sailing enthusiast who wears his life-jacket when training on dry land_
_but takes it off as soon as he goes to sea?_"


## Summary

In this lecture, you learned:
- How to write readable code: naming, formatting, comments, conventions
- How to debug code: reproducing bugs, using a debugger
- How to write debuggable code: defensive programming, logging, debug-only code

You can now check out the [exercises](exercises/)!
