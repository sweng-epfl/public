# Evolution

Imagine you are an architect asked add a tower to a castle.
You know what towers look like, you know what castles look like, and you know how to build a tower in a castle. This will be easy!

But then you get to the castle, and it looks like this:

<p align="center"><img alt="Howl's Moving Castle from the movie of the same name: a messy 'castle' on legs" src="images/castle.jpg" width="50%" /></p>

Sure, it's a castle, and it fulfills the same purpose most castles do, but... it's not exactly what you had in mind.
It's not built like a standard castle.
There's no obvious place to add a tower, and what kind of tower will this castle need anyway?
Where do you make space for a tower? How do you make sure you don't break half the castle while adding it?

This lecture is all about evolving an existing codebase.


## Objectives

After this lecture, you should be able to:
- Find your way in a _legacy codebase_
- Apply common _refactorings_ to improve code
- Document and quantify _changes_
- Establish solid foundations with _versioning_


## What is legacy code, and why should we care?

"Legacy code" really means "old code that we don't like".
Legacy code may or may not have documentation, tests, and bugs.
If it has documentation and tests, they may or may not be complete enough; tests may even already be failing.

One common reaction to legacy code is disgust: it's ugly, it's buggy, why should we even keep it?
If we rewrote the code from scratch we wouldn't have to ask all of these questions about evolution! It certainly sounds enticing.

In the short term, a rewrite feels good. There's no need to learn about old code, instead you can use the latest technologies and write the entire application from scratch.

However, legacy code works. It has many features, has been debugged and patched many times, and users rely on the way it works.
If you accidentally break something, or if you decide that some "obscure" feature is not necessary, you will anger a lot of your users, who may decide to jump ship to a competitor.

One infamous rewrite story is [that of Netscape 5](https://www.joelonsoftware.com/2000/04/06/things-you-should-never-do-part-i/).
In the '90s, Netscape Navigator was in tough competition with Microsoft's Internet Explorer.
While IE became the butt of jokes later, at the time Microsoft was heavily invested in the browser wars.
The Netscape developers decided that their existing Netscape 4 codebase was too old, too buggy, and too hard to evolve.
They decided to write Netscape 5 from scratch.
The result is that it took them three years to ship the next version of Netscape; in that time, Microsoft had evolved their existing IE codebase, far outpacing Netscape 4, and Netscape went bankrupt.

Most rewrites, like Netscape, fail.
A rewrite means a loss of experience, a repeat of many previous mistakes, and that's just to get to the same point as the previous codebase.
There then needs to be time to add features that justify the cost of upgrading to users. Most rewrites run out of time or money and fail.

It's not even clear what "a bug" is in legacy code, which is one reason rewrites are dangerous: some users depend on things that might be considered "bugs".
For instance, Microsoft Excel [treats 1900 as a leap year](https://learn.microsoft.com/en-us/office/troubleshoot/excel/wrongly-assumes-1900-is-leap-year)
even though it is not, because back when it was released it had to compete with a product named Lotus 1-2-3 that did have this bug.
Fixing the bug means many spreadsheets would stop working correctly, as dates would become off by one. Thus, even nowadays, Microsoft Excel still contains a decades-old "bug" in the name of compatibility.

A better reaction to legacy code is to _take ownership of it_: if you are assigned to a legacy codebase, it is now your code, and you should treat it just like any other code you are responsible for.
If the code is ugly, it is your responsibility to fix it.


## How can we improve legacy code?

External improvements to an existing codebase, such as adding new features, fixing bugs, or improving performance, frequently require internal improvements to the code first.
Some features may be difficult to implement in an existing location, but could be much easier if the code was improved first.
This may require changing design _tradeoffs_, addressing _technical debt_, and _refactoring_ the codebase. Let's see each of these in detail.

### Tradeoffs

Software engineers make tradeoffs all the time when writing software, such as choosing an implementation that is faster at the cost of using more memory,
or simpler to implement at the cost of being slower, or more reliable at the cost of more disk space.
As code ages, its context changes, and old tradeoffs may no longer make sense.

For instance, Windows XP, released in 2001, groups background services into a small handful of processes.
If any background service crashes, it will cause all of the services in the same process to also crash.
However, because there are few processes, this minimizes resource use.
It would have been too much in 2001, on computers with as little as 64 MB of RAM, to dedicate one process and the associated overheads per background service.

But in 2015, when Windows 10 was released, computers typically had well over 2 GB of RAM.
Trading reliability for low resource use no longer made sense, so Windows 10 instead runs each background service in its own process.
The cost of memory is tiny on the computers Windows 10 is made for, and the benefits from not crashing entire groups of services at a time are well worth it.
The same choice made 15 years apart yielded a different decision.

### Technical debt

The cost of all of the "cheap" and "quick" fixes done to a codebase making it progressively worse is named _technical debt_.
Adding one piece of code that breaks modularity and hacks around the code's internals may be fine to meet a deadline, but after a few dozen such hacks, the code becomes hard to maintain.

The concept is similar to monetary debt: it can make sense to invest more money than you have, so you borrow money and slowly pay it back.
But if you don't regularly pay back at least the interest, your debt grows and grows, and so does the share of your budget you must spend on repaying your debt.
You eventually go bankrupt from the debt payments taking up your entire budget.

With technical debt, a task that should take hours can take a week instead, because one now needs to update the code in the multiple places where it has been copy-pasted for "hacks",
fix some unrelated code that in practice depends on the specific internals of the code to change, write complex tests that must set up way more than they should need, and so on.
You may no longer be able to use the latest library that would solve your problem in an hour, because your codebase is too old and depends on old technology the library is not compatible with,
so instead you need weeks to reimplement that functionality yourself.
You may regularly need to manually reimplement security patches done to the platform you use because you use an old version that is no longer maintained.

This is one reason why standards are useful: using a standard version of a component means you can easily service it.
If you instead have a custom component, maintenance becomes much more difficult, even if the component is nicer to work with at the beginning.

### Refactoring

Refactoring is the process of making _incremental_ and _internal_ improvements.
These are improvements designed to make code easier to maintain, but that do not directly affect end users.

Refactoring is about starting from a well-known code problem, applying a well-known solution, and ending up with better code.
The well-known problems are sometimes called "code smells", because they're like a strange smell: not harmful on its own, but worrying, and could lead to problems if left unchecked.

For instance, consider the following code:
```java
class Player {
    int hitPoints;
    String weaponName;
    int weaponDamage;
    boolean isWeaponRanged;
}
```
Something doesn't smell right. Why does a `Player` have all of the attributes of its weapon?
As it is, the code works, but what if you need to handle weapons independently of players, say, to buy and sell weapons at shops?
Now you will need fake "players" that exist just for their weapons. You'll need to write code that hides these players from view.
Future code that deals with players may not handle those "fake players" correctly, introducing bugs.
Pay some of your technical debt and fix this with a refactoring: extract a class.
```java
class Player {
    int hitPoints;
    Weapon weapon;
}
class Weapon { ... }
```
Much better. Now you can deal with weapons independently of players.

Your `Weapon` class now looks something like this:
```java
class Weapon {
    boolean isRanged;
    void attack() {
        if (isRanged) { ... } else { ... }
    }
}          
```
This doesn't smell right either. Every method on `Weapon` will have to first check if it is ranged or not, and some developers might forget to do that.
Refactor it: use polymorphism.
```java
abstract class Weapon {
    abstract void attack();
}
class MeleeWeapon extends Weapon { ... }
class RangedWeapon extends Weapon { ... }
```
Better.

But then you look at the damage calculation...
```java
int damage() {
    return level * attack - max(0, armor - 500) *
           attack / 20 + min(weakness * level / 10, 400);
}
```
What is this even doing? It's hard to tell what was intended and whether there's any bug in there, let alone to extend it.
Refactor it: extract variables.
```java
int damage() {
    int base = level * attack;
    int resistance = max(0, armor - 500) * attack / 20;
    int bonus = min(weakness * level / 10, 400);
    return base - resistance + bonus;
}
```
This does the same thing, but now you can tell what it is: there's one component for damage that scales with the level,
one component for taking the resistance into account, and one component for bonus damage.

You do not have to do these refactorings by hand; any IDE contains tools to perform refactorings such as extracting an expression into a variable or renaming a method.

---
#### Exercise
Take a look at the [gilded-rose/](exercises/lecture/gilded-rose) exercise folder.
The code is obviously quite messy.
First, try figuring out what the code is trying to do, and what problems it has.
Then, write down what refactorings you would make to improve the code. You don't have to actually do the refactorings, only to list them, though you can do them for more practice.

<details>
<summary>Proposed solution (click to expand)</summary>
<p>

The code tries to model the quality of items over time. But it has so many special cases and so much copy-pasted code that it's hard to tell.

Some possible refactorings:
- Turn the `for (int i = 0; i < items.length; i++)` loop into a simpler `for (Item item : items)` loop
- Simplify `item.quality = item.quality - item.quality` into the equivalent but clearer `item.quality = 0`
- Extract `if (item.quality < 50) item.quality = item.quality + 1;` into a method `incrementQuality` on `Item`, which can thus encapsulate this cap at 50
- Extract numbers such as `50` into named constants such as `MAX_QUALITY`
- Extract repeated checks such as those on the item names into methods on `Item` such as `isPerishable`, and maybe create subclasses of `Item` instead of checking names

</p>
</details>


## Where to start in a large codebase?

Refactorings are useful for specific parts of code, but where to even start if you are given a codebase of millions of lines of code and told to fix a bug?
There may not be documentation, and if there is it may not be accurate.

A naïve strategy is to "move fast and break things", as [was once Facebook's motto](https://www.businessinsider.com/mark-zuckerberg-on-facebooks-new-motto-2014-5).
The advantage is that you move fast, but... the disadvantage is that you break things.
The latter tends to massively outweigh the former in any large codebase with many users.
Making changes you don't fully understand is a recipe for disaster in most cases.

An optimistic strategy, often taken by beginners, is to understand _everything_ about the code.
Spend days and weeks reading every part of the codebase. Watch video tutorials about every library the code uses.
This is not useful either, because it takes far too long, and because it will in fact never finish since others are likely making changes to the code while you learn.
Furthermore, by the time you have read half the codebase, you won't remember what exactly the first part you looked at was, and your time will have been wasted.

Let's see three components of a more realistic strategy: learning as you go, using an IDE, and taking notes.

### Learning as you go

Think of how detectives such as [Sherlock Holmes](https://en.wikipedia.org/wiki/Sherlock_Holmes) or [Miss Marple](https://en.wikipedia.org/wiki/Miss_Marple) solve a case.
They need information about the victim, what happened, and any possible suspects, because that is how they will find out who did it.
But they do not start by asking every person around for their entire life story from birth to present.
They do not investigate the full history of every item found at the scene of the crime.
While the information they need is somewhere in there, getting it by enumerating all information would take too much time.

Instead, detectives only learn what they need when they need it. If they find evidence that looks related, they ask about that evidence.
If somebody's behavior is suspect, they look into that person's general history, and if something looks related, they dig deeper for just that detail.

This is what you should do as well in a large codebase. Learn as you go: only learn what you need when you need it.

### Using an IDE

You do not have to manually read through files to find out which class is used where, or which modules call which function.
IDEs have built-in features to do this for you, and those features get better if the language you're using is statically typed.
Want to find who uses a method? Right click on the method's name, and you should find some tool such as "find all references".
Do you realize the method is poorly named given how the callers use it? Refactor its name using your IDE, don't manually rename every use.

One key feature of IDEs that will help you is the _debugger_.
Find the program's "main" function, the one called at the very beginning, and put a breakpoint on its first statement.
Run the program with the debugger, and you're ready to start your investigation by following along with the program's flow.
Want to know more about a function call? Step into it. Think that call is not relevant right now? Step over it instead.

### Taking notes

You cannot hope to remember all context about every part of a large codebase by heart.
Instead, take notes as you go, at first for yourself. You may later turn these notes into documentation.

One formal way to take notes is to write _regression tests_.
You do not know what behavior the program should have, but you do know that it works and you do not want to break it.
Thus, write a test without assertions, run the test under the debugger, and look at the code's behavior.
Then, add assertions to the test for the current behavior.
This serves both as notes for yourself of what happens when the code is used in a specific way, and as an automated way to check if you broke something while modifying the code later.

Another formal way to take notes is to write _facades_.
"Facade" is a design pattern intended to simplify and modularize existing code by hiding complex parts behind simpler facades.
For instance, let's say you are working in a codebase that only provides a very generic "draw" method that takes a lot of arguments, but you only need to draw rectangles:
```java
var points = new Point[] {
    new Point(0, 0), new Point(x, 0),
    new Point(x, y), new Point(0, y)
};
draw(points, true, false, Color.RED, null, new Logger(), ...);
```
This is hard to read and maintain, so write a facade for it:
```java
drawRectangle(0, 0, x, y, Color.RED);
```
Then implement the `drawRectangle` method in terms of the complex `draw` method.
The behavior hasn't changed, but the code is easier to read.
Now, you only need to look at the complex part of the code if you actually need to add functionality related to it.
Reading the code that needs to draw rectangles no longer requires knowledge of the complex drawing function.

---
#### Exercise
Take a look at the [pacman/](exercises/lecture/pacman) exercise folder.
It's a cool "Pac-Man" game written in Java, with a graphical user interface.
It's fun! Imagine you were asked to maintain it and add features. You've never read its code before. so where to start?

First, look at the code, and take some notes: which classes exist, and what do they do?
Then, use a debugger to inspect the code's flow, as described above.
If someone asked you to extend this game to add a new kind of ghost, with a different color and behavior, which parts of the code would you need to change?
Finally, what changes could you make to the code to make it easier to add more kinds of ghosts?

<details>
<summary>Proposed solution (click to expand)</summary>
<p>

To add a kind of ghost, you'd need to add a value to the `ghostType` enum, and a class extending `Ghost`.
You would then need to add parsing logic in `MapEditor` for your ghost, and to link the enum and class together in `PacBoard`.

To make the addition of more ghosts easier, you could start by re-formatting the code to your desired standard, and changing names to be more uniform, such as the casing of `ghostType`.
One task would be to have a single object to represent ghosts, instead of having both `ghostType` and `Ghost`.
It would also probably make sense to split the parsing logic from `MapEditor`, since editing and parsing are not the same job.

Remember, you might look at this Pac-Man game code thinking it's not as nice as some idealized code that could exist, but unlike the idealized code, this one does exist already, and it works.
Put energy into improving the code rather than complaining about it.

</p>
</details>

---

Remember the rule typically used by scouts: leave the campground cleaner than you found it.
In your case, the campground is code.
Even small improvements can pay off with time, just like monetary investments.
If you improve a codebase by 1% every day, after 365 days, it will be around 38x better than you started.
But if you let it deteriorate by 1% instead, it will be only 0.03x as good.


## How should we document changes?

You've made some changes to legacy code, such as refactorings and bug fixes. Now how do you document this?
The situation you want to avoid is to provide no documentation, lose all knowledge you gained while making these changes, and need to figure it all out again. This could happen to yourself or to someone else.

Let's see three kinds of documentation you may want to write: for yourself, for code reviewers, and for maintainers.

### Documenting for yourself

The best way to check and improve your understanding of a legacy codebase is to teach others about it, which you can do by writing comments and documentation.
This is a kind of "refactoring": you improve the code's maintainability by writing the comments and documentation that good code should have.

For instance, let's say you find the following line in a method somewhere without any explanation:
```java
if (i > 127) i = 127;
```
After spending some time on it, such as commenting it out to see what happens, you realize that this value is eventually sent to a server which refuses values above 127.
You can document this fact by adding a comment, such as `// The server refuses values above 127`. You now understand the code better.
Then you find the following method:
```java
int indexOfSpace(String text) { ... }
```
You think you understand what this does, but when running the code, you realize it not only finds spaces but also tabs.
After some investigation, it turns out this was a bug that is now a specific behavior on which clients depend, so you must keep it.
You can thus add some documentation: `Also finds tabs. Clients depend on this buggy behavior`.
You now understand the code better, and you won't be bitten by this issue again.

### Documenting for code reviewers

You submit a pull request to a legacy codebase. Your changes touch code that your colleagues aren't quite familiar with either.
How do you save your colleagues some time? You don't want them to have to understand exactly what is going on before being able to review your change,
yet if you only give them code, this is what will happen.

First, your pull request should have a _description_, in which you explain what you are changing and why.
This description can be as long as necessary, and can include remarks such as whether this is a refactoring-only change or a change in behavior,
or why you had to change some code that intuitively looks like it should not need changes.

Second, the commits themselves can help reviewing if you split your work correctly, or if you rewrite the history once you are done with the work.
For instance, your change may involve a refactoring and a bug fix, because the refactoring makes the bug fix cleaner.
If you submit the change as a single commit, reviewers will need time and energy to read and understand the entire change at once.
If a reviewer is interrupted in the middle of understanding that commit, they will have to start from scratch after the interruption.

Instead of one big commit, you can submit a pull request consisting of one commit per logical task in the request.
For instance, you can have one commit for a refactoring, and one for a bug fix. This is easier to review, because they can be reviewed independently.
This is particularly important for large changes: the time spent reviewing a commit is not linear in the length of the commit, but closer to exponential,
because we humans have limited brain space and usually do not have large chunks of uninterrupted time whenever we'd like.
Instead of spending an hour reviewing 300 modified lines of code, it's easier to spend 10 times 3 minutes reviewing commits of 30 lines at a time.
This also lessens the effects of being interrupted during a review.

### Documenting for maintainers

We've talked about documentation for individual bits of code, but future maintainers need more than that to understand a codebase.
Design decisions must be documented too: what did you choose and why?
Even if you plan on maintaining a project yourself for a long time, this saves some work: your future colleagues could each take 5 minutes of your time asking
you the same question about some design decision taken long ago, or you could spend 10 minutes once documenting it in writing.

At the level of commits, this can be done in a commit message, so that future maintainers can use tools such as [git blame](https://git-scm.com/docs/git-blame)
to find the commit that last changed a line of code and understand why it is that way.

At the level of modules or entire projects, this can be done with _Architectural Decision Records_.
As their name implies, these are all about recording decisions made regarding the architecture of a project: the context, the decision, and its consequences.
The goal of ADRs is for future maintainers to know not only what choices were made but also why, so that maintainers can make an informed decision on whether to make changes.
For instance, knowing that a specific library for user interfaces was chosen because of its excellent accessibility features informs maintainers that even if they do not like
the library's API, they should pay particular attention to accessibility in any potential replacement. Perhaps the choice was made at a time when alternatives had poor accessibility,
and the maintainers can change that choice because in their time there are alternatives that also have great accessibility features.

The context includes user requirements, deadlines, compatibility with previous systems, or any other piece of information that is useful to know to understand the decision.
For instance, in the context of lecture notes for a course, the context could include "We must provide lecture notes, as student feedback tells us they are very useful"
and "We want to allow contributions from students, so that they can easily help if they find typos or mistakes".

The decision includes the alternatives that were considered, the arguments for and against each of them, and the reasons for the final choice.
For instance, still in the same context, the alternatives might be "PDF files", "documents on Google Drive", and "documents on a Git repository".
The arguments could then include "PDF files are convenient to read on any device, but they make it hard to contribute".
The final choice might then be "We chose documents on a Git repository because of the ease of collaboration and review, and because GitHub provides a nice preview of Markdown files online".

The consequences are the list of tasks and changes that must be done in the short-to-medium term.
This is necessary to apply the decision, even if it may not be useful in the long term.
For instance, one person might be tasked with converting existing lecture notes to Markdown, and another might be tasked to create an organization and a repository on GitHub.

It is important to keep ADRs _close to the code_, such as in the same repository, or in the same organization on a platform like GitHub.
If ADRs are in some unrelated location that only current maintainers know about, they will be of no use to future maintainers.


## How can we quantify changes?

Making changes is not only about describing them qualitatively, but also about telling people who use the software whether the changes will affect them or not.
For instance, if you publish a release that removes a function from your library, people who used that function cannot use this new release immediately.
They need to change their code to no longer use the function you removed, which might be easy if you provided a replacement, or might require large changes if you did not.
And if the people using your library cannot or do not want to update their software, for instance because they have lost the source code, they now have to rewrite their software.

Let's talk _compatibility_, and specifically three different axes: theory vs practice, forward vs backward, and source vs binary.

### Theory vs practice

In theory, any change could be an "incompatible" change.
Someone could have copied your code or binary somewhere, and start their program by checking that yours is still the exact same one. Any change would make this check fail.
Someone could depend on the exact precision of some computation whose precision is not documented, and then [fail](https://twitter.com/stephentyrone/status/1425815576795099138)
when the computation is made more precise.

In practice, we will ignore the theoretical feasibility of detecting changes and choose to be "reasonable" instead.
What "reasonable" is can depend on the context, such as Microsoft Windows having to provide compatibility modes for all kinds of old software which did questionable things.
Microsoft must do this because one of the key features they provide is compatibility, and customers might move to other operating systems otherwise.
Most engineers do not have such strict compatibility requirements.

### Forward vs backward

A software release is _forward compatible_ if clients for the next release can also use it without needing changes.
This is also known as "upward compatibility".
That is, if a client works on version N+1, forward compatibility means it should also work on version N.
Forward compatibility means that you cannot ever add new features or make changes that break behavior,
since a client using those new features could not work on the previous version that did not have these features.
It is rare in practice to offer forward compatibility, since the only changes allowed are performance improvements and bug fixes.

A software release is _backward-compatible_ if clients for the previous release can also use it without needing changes.
That is, if a client works on version N-1, backward compatibility means it should also work on version N.
Backward compatibility is the most common form of compatibility and corresponds to the intuitive notion of "don’t break things".
If something works, it should continue working. For instance, if you upgrade your operating systems, your applications should still work.

Backward compatibility typically comes with a set of "supported" scenarios, such as a public API.
It is important to define what is supported when defining compatibility, since otherwise some clients could misunderstand what is and is not supported,
and their code could break even though you intended to maintain backward compatibility.
For instance, old operating systems did not have memory protection: any program could read and write any memory, including the operating system's.
This was not something programs should have been doing, but they could. When updating to a newer OS with memory protection, this no longer worked,
but it was not considered breaking backward compatibility since it was never a feature in the first place, only a limitation of the OS.

One extreme example of providing backward compatibility is Microsoft's [App Assure](https://www.microsoft.com/en-us/fasttrack/microsoft-365/app-assure):
if a company has a program that used to work and no longer works after upgrading Windows, Microsoft will fix it, for free.
This kind of guarantee is what allowed Microsoft to dominate in the corporate world; no one wants to have to frequently rewrite their programs,
no matter how much "better" or "nicer" the new technologies are. If something works, it works.

### Source vs binary

Source compatibility is about whether your customers' source code still compiles against a different version of your code.
Binary compatibility is about whether your customers’ binaries still link with a different version of your binary.
These are orthogonal to behavioral compatibility; code may still compile and link against your code even though the behavior at run-time has changed.

Binary compatibility can be defined in terms of "ABI", "Application Binary Interface", just like "API" for source code.
The ABI defines how components call each other, such as method signatures: method names, return types, parameter types, and so on.
The exact compatibility requirements differ due to the ABI of various languages and platforms.
For instance, parameter names are not a part of Java's ABI, and thus can be changed without breaking binary compatibility.
In fact, parameter names are not a part of Java's API either, and can thus also be changed without breaking source compatibility.

An interesting example of preserving source but not binary compatibility is C#'s optional parameters.
A definition such as `void Run(int a, int b = 0)` means the parameter `b` is optional with a default value of `0`.
However, this is purely source-based; writing `Run(a)` is translated by the compiler as if one had written `Run(a, 0)`.
This means that evolving the method from `void Run(int a)` to `void Run(int a, int b = 0)` is compatible at the source level,
because the compiler will implicitly add the new parameter to all existing calls, but not at the binary level, because the method signature changed so existing binaries will not find the one they expect.

An example of the opposite is Java's generic type parameters, due to type erasure.
Changing a class from `Widget<X>` to `Widget<X, Y>` is incompatible at the source level,
since the second type parameter must now be added to all existing uses by hand.
It is however compatible at the binary level,
because generic parameters in Java are erased during compilation.
If the second generic parameter is not otherwise used, binaries will not even know it exists.

---
#### Exercise
Which of these preserves backward compatibility?
- Add a new class
- Make a return type _less_ specific (e.g., from `String` to `Object`)
- Make a return type _more_ specific (e.g., from `Object` to `String`)
- Add a new parameter to a function
- Make a parameter type _less_ specific
- Make a parameter type _more_ specific
<details>
<summary>Proposed solution (click to expand)</summary>
<p>

- Adding a new class is binary compatible, since no previous version could have referred to it.
  It is generally considered to be source compatible, except for the annoying scenario in which someone used wildcard imports (e.g., `import org.example.*`)
  for two modules in the same file, and your new class has the same name as another class in another module, at which point the compiler will complain of an ambiguity.
- Making a return type less specific is not backward compatible.
  The signature changes, so binary compatibility is already broken,
  and calling code must be modified to be able to deal with more kinds of values, so source compatibility is also broken.
- Making a return type more specific is source compatible, since code that dealt with the return type can definitely deal with a method that happens to always return a more specific type.
  However, since the signature changes, it is not binary compatible.
- Adding a parameter is neither binary nor source compatible, since the signature changes and code must now be modified to pass an additional argument whenever the function is called.
- Making a parameter type less specific is source compatible, since a function that accepts a general type of parameter can be used by always giving a more specific type.
  However, since the signature changes, it is not binary compatible.
- Making a parameter type more specific is not backward compatible.
  The signature changes, so binary compatibility is already broken,
  and calling code must be modified to always pass a more specific type for arguments, so source compatibility is also broken.
</p>
</details>

---

What compatibility guarantees should you provide, then?
This depends on who your customers are, and asking them is a good first step.
Avoid over-promising; the effort required to maintain very strict compatibility may be more than the benefits you get from the one or two specific customers who need this much compatibility.
Most of your customers are likely to be "reasonable".

Backward compatibility is the main guarantee people expect in practice, even if they do not say so explicitly.
Breaking things that work is viewed poorly.
However, making a scenario that previously had a "failure" result, such as throwing an exception, return a "success” result instead is typically OK.
Customers typically do not depend on things _not_ working.


## How can we establish solid foundations?

You are asked to run an old Python script that a coworker wrote ages ago.
The script begins with `import simplejson`, and then proceeds to use the `simplejson` library.
You download the library, run the script... and you get a `NameError: name 'scanstring' is not defined`.

Unfortunately, because the script did not specify what _version_ of the library it expected, you now have to figure it out by trial and error.
For crashes such as missing functions, this can be done relatively quickly by going through versions in a binary search.
However, it is also possible that your script will silently give a wrong result with some versions of the library.
For instance, perhaps the script depends on a bug fix made at a specific time,
and running the script with a version of the library older than that will give a result that is incorrect but not obviously so.

Versions are specific, tested, and named releases.
For instance, "Windows 11" is a version, and so is "simplejson 3.17.6".
Versions can be more or less specific; for instance, "Windows 11" is a general product name with a set of features, and some more minor features were added in updates such as "Windows 11 22H2".

Typical components of a version include a major and a minor version number, sometimes followed by a patch number and a build number;
a name or sometimes a codename; a date of release; and possibly even other information.

In typical usage, changing the major version number is for big changes and new features,
changing the minor version number is for small changes and fixes, and changing the rest such as the patch version number is for small fixes that may not be noticeable to most users,
as well as security patches.

Versioning schemes can be more formal, such as [Semantic Versioning](https://semver.org/),
a commonly used format in which versions have three main components: `Major.Minor.Patch`.
Incrementing the major version number is for changes that break compatibility, the minor version number is for changes that add features while remaining compatible,
and the patch number is for compatible changes that do not add features.
As already stated, remember that the definition of "compatible" changes is not objective.
Some people may consider a change to break compatibility even if others think it is compatible.

Let's see three ways in which you will use versions: publishing versioned releases, _deprecating_ public APIs if truly necessary, and consuming versions of your dependencies.

### Versioning your releases

If you allowed customers to download your source code and compile it whenever they want,
it would be difficult to track who is using what, and any bug report would start with a long and arduous process of figuring out exactly which version of the code is in use.
Instead, if a customer states they are using version 5.4.1 of your product and encounter a specific bug, you can immediately know which code this corresponds to.

Providing specific versions to customers means providing specific guarantees, such as "version X of our product is compatible with versions Y and Z of the operating system",
or "version X of our product will be supported for 10 years with security patches".

You do not have to maintain a single version at a time; products routinely have multiple versions under active support,
such as [Java SE](https://www.oracle.com/java/technologies/java-se-support-roadmap.html).

In practice, versions are typically different branches in a repository.
If a change is made to the "main" branch, you can then decide whether it should be ported to some of the other branches.
Security fixes are a good example of changes that should be ported to all versions that are still supported.

### Deprecating public APIs

Sometimes you realize your codebase contains some very bad mistakes that lead to problems,
and you'd like to correct them in a way that technically breaks compatibility but still leads to a reasonable experience for your customers.
That is what _deprecation_ is for.

By declaring that a part of your public surface is deprecated, you are telling customers that they should stop using it, and that you may even remove it in a future version.
Deprecation should be reserved for cases that are truly problematic, not just annoying.
For instance, if the guarantees provided by a specific method force your entire codebase to use a suboptimal design that makes everything else slower, it may be worth removing the method.
Another good example is methods that accidentally make it very easy to introduce bugs or security vulnerabilities due to subtleties in their semantics.

For instance, Java's `Thread::checkAccess` method was deprecated in Java 17,
because it depends on the Java Security Manager, which very few people use in practice and which constrains the evolution of the Java platform,
as [JEP 411 states](https://openjdk.org/jeps/411).

Here is an example of a less reasonable deprecation from Python:
```
>>> from collections import Iterable
DeprecationWarning: Using or importing the ABCs
from 'collections'
instead of from 'collections.abc'
is deprecated since Python 3.3,
and in 3.10 it will stop working
```
Sure, having classes in the "wrong" module is not great, but the cost of maintaining backward compatibility is low.
Breaking all code that expects the "Abstract Base Collections" to be in the "wrong" module is likely more trouble than it's worth.

Deprecating in practice means thinking about whether the cost is worth the risk, and if it is, using your language's way of deprecating,
such as `@Deprecated(...)` in Java or `[Obsolete(...)]` in C#.

### Consuming versions

Using specific versions of your dependencies allows you to have a "known good" environment that you can use as a solid base to work.
You can update dependencies as needed, using version numbers as a hint for what kind of changes to expect.

This does not mean you should 100% trust all dependencies to follow compatibility guidelines such as semantic versioning.
Even those who try to follow such guidelines can make mistakes, and updating a dependency from 1.3.4 to 1.3.5 could break your code due to such a mistake.
But at least you know that your code worked with 1.3.4 and you can go back to it if needed.
The worst-case scenario, which is unfortunately common with old code, is when you cannot build a codebase anymore
because it does not work with the latest versions of its dependencies and you do not know which versions it expects.
You then have to spend lots of time figuring out which versions work and do not work, and writing them down so future you does not have to do it all over again.

In practice, in order to manage dependencies, software engineers use package managers, such as Gradle for Java, which manage dependencies given versions:
```
testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
```
This makes it easy to get the right dependency given its name and version, without having to manually find it online.
It's also easy to update dependencies; package managers can even tell you whether there is any newer version available.
You should however be careful of "wildcard" versions:
```
testImplementation 'org.junit.jupiter:junit-jupiter-api:5.+'
```
Not only can such a version cause your code to break because it will silently use a newer version when one is available,
which could contain some bug that breaks your code, but you will have to spend time figuring out which version you were previously using, since it is not written down.

For big dependencies such as operating systems, one way to easily save the entire environment as one big "version" is to use a virtual machine or container.
Once it is built, you know it works, and you can distribute your software on that virtual machine or container.
This is particularly useful for software that needs to be exactly preserved for long periods of time, such as scientific artifacts.


## Summary

In this lecture, you learned:
- Evolving legacy code: goals, refactorings, and documentation
- Dealing with large codebases: learning as you go, improving the code incrementally
- Quantifying and describing changes: compatibility and versioning

You can now check out the [exercises](exercises/)!
