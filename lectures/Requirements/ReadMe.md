# Requirements

We do not write code for the sake of writing code: we develop software to _help people perform tasks_.
These people can be "anyone on Earth" for widely-used software such as GitHub, "people who do a specific job" for internal applications,
or even a single person whose life can be helped with software.
In order to know _what_ software to develop, we must know what user needs: their _requirements_.


## Objectives

After this lecture, you should be able to:
- Define what user requirements are
- Formalize requirements into _personas_ and _user stories_
- Develop software based on formalized requirements
- Understand _implicit_ vs _explicit_ requirements


## What are requirements?

A requirement is something users need.
For instance, a user might want to move from one place to another. They could then use a car.
But perhaps the user has other requirements such as not wanting or being able to drive, in which case a train could satisfy their requirements.
Users may also have more specific requirements, such as specifically wanting a low-carbon means of transportation,
and having to travel between places that are far from public transport, in which case an electric car powered with low-carbon electricity could be a solution.

Requirements are _not_ about implementation details.
A specific kind of electric motor no a specific kind of steel for the car doors are not user requirements.
However, users may have needs that lead to such choices being made by the system designers.

Sometimes you may encounter a division between "functional" and "non-functional" requirements, with the latter also being known as "quality attributes".
"Functional" requirements are those that directly relate to features, whereas "non-functional" ones include accessibility, security, performance, and so on.
The distinction is not always clear, but it is sometimes made anyway.

Defining requirements typically starts by discussing with users.
This is easier if the software has a small and well-defined set of users, such as software specifically made for one person to do their job in a company.
It is harder if the software has a large and ill-defined set of users, such as a search engine or an app to listen to music.

It is important, when listening to what users say, to keep track of what they _need_ rather than what they _want_.
What users claim they want is heavily influenced by what they already know and use.
For instance, someone used to traveling by horse who needs to go across a mountain might ask for a "flying horse", when in fact their need is crossing the mountain,
and thus a train with a tunnel or a plane would satisfy their requirements.
Similarly, before the modern smart phone, users would have asked for old-style phones purely because they did not even know a smart phone was feasible,
even if now they actually like their smart phone more than they liked their old phone.

What users want can be ambiguous, especially when there are many users.
If you select cells with "100" and "200" in a spreadsheet program such as Microsoft Excel and expand the selection, what should happen?
Should Excel fill the new cells with "300", "400", and so on? Should it repeat "100" and "200" ?
What if you expand a selection containing the lone cell "Room 120" ? Should it be repeated or should it become "Room 121", "Room 122", and so on?
There is no perfect answer to these questions, as any answer will leave some users unsatisfied, but a developer must make a choice.

Not listening to users' requirements can be costly, as Microsoft found out with Windows 8.
Windows 8's user interface was a major reinvention of the Windows interface, using a "start screen" rather than a menu, with full-screen apps that could be tiled rather than moved.
It was a radical departure from the Windows that users knew, and it was a commercial failure. Microsoft had to bring back the start menu, and abandoned the concept of tiled full-screen apps.
However, Apple later did something similar for a new version of their iPad operating system, and it worked quite well there, perhaps because users have different expectations on desktops and tablets.


## How can we formalize requirements?

Once you've discussed with plenty of users and gotten many ideas on what requirements users have, how do you consolidate this feedback into actionable items?
This is what formalizing requirements is about, and we will discuss formalizing _who_ with "personas" and _what_ with "user stories".

Who is the software for? Sometimes the answer to that question is obvious because it is intended for a small and specific set of users, but most large pieces of software are intended for many people.
In fact, large pieces of software typically have way too many users for any kind of personalized process to scale.
Instead, you can use _personas_ to represent groups of users.

A persona is an _abstract_ person that represents a group of similar users.
For instance, in a music app, one persona might be Alice, a student, who uses the app in her commute on public transport.
Alice is not a real person, and she does not need specific features such as a hair color or a nationality.
Instead, Alice is an abstract representation of many people who could use the app and all have similar features from the app's point of view,
namely that they use the app on public transport while commuting to their school, university, or other similar place.
Alice's requirements might lead to features such as downloading podcasts in advance at home, and listening with the screen turned off.
Another persona for the same app might be Bob, a pensioner, who uses the app while cooking and cleaning.
Bob is not a real person either, but instead represents a group of potential users who aren't so familiar with the latest technology and want to use the app while performing tasks at home.

While one can create personas for many potential groups of users, not all of them will make the cut.
Still for the music app example, another persona could be Carol, a "hacker" who wants to listen to pirated music.
Carol would need features such as loading existing music tracks into the app and bypassing copyright protection.
Is the app intended for people like Carol? That's up to the developers to decide.

One last word about personas: avoid over-abstracting. Personas are useful because they represent real people in ways that are helpful to development.
If your personas end up sounding like "John, a user who uses the app" or "Jane, a person who has a phone", they will not be useful.
Similarly, if you already know who exactly is in a group of users, there is no need to abstract it. "Sam, a sysadmin" is not a useful persona if your app has exactly one sysadmin: use the real person instead.

---

#### Exercise
What personas could a video chat app have?
<details>
<summary>Example solutions (click to expand)</summary>
<p>

Anne, a manager who frequently talks to her team while working remotely.

Basil, a pensioner who wants to video chat his grandkids to stay in touch.

Carlos, a doctor who needs to talk to patients as part of a telemedicine setup.

</p>
</details>

---

What can users do? After defining who the software is for, one must decide what features to build.
_User stories_ are a useful tool to formalize features based on requirements, including who wants the feature, what the feature is, and what the context is.
Context is key because the same feature could be implemented in wildly different ways based on context.
For instance, "sending emails with information" is a feature a software system might have.
If the context is that users want to archive information, the emails should include very detailed information, but their arrival time matters little.
If the context is that users want a notification as soon as something happens, the emails should be sent immediately, and great care should be taken to avoid ending up in a spam filter.
If the context is that users want to share data with their friends who don't use the software, the emails should have a crisp design that contains only the relevant information so they can be easily forwarded.

There are many formats for user stories; in this course, we will use the three-part one "_as a ... I want to ... so that ..._".
This format includes the user who wants the feature, which could be a persona or a specific role, the feature itself, and some context explaining why that user wants that feature.
For instance, "As a student, I want to watch course recordings, so that I can catch up after an illness".
This user story lets developers build a feature that is actually useful to the person: it would not be useful to this student, for instance,
to build a course recording feature that is an archive accessible once the course has ended, since presumably the student will not be ill for the entire course duration.

Going back to our music app example, consider the following user story: "As Alice, I want to download podcasts in advance, so that I can save mobile data".
This implies the app should download the entire podcast in advance, but Alice still does have mobile data, she just doesn't want to use too much of it.
A similar story with a different context could be "As a commuter by car, I want to download podcasts in advance, so that I can use the app without mobile data".
This is a different user story leading to a different feature: now the app cannot use mobile data at all, because the commuter simply does not have data at some points of their commute.

To evaluate user stories, remember the "INVEST" acronym:
- *I*ndependent: the story should be self-contained
- *N*egotiable: the story is not a strict contract but can evolve
- *V*aluable: the story should bring clear value to someone
- *E*stimable: the developers should be able to estimate how long the story will take to implement
- *S*mall: the story should be of reasonable size, not a huge "catch-all" story
- *T*estable: the developers should be able to tell what acceptance criteria the story has based on its text, so they can test their implementation

Stories that are too hard to understand and especially too vague will fail the "INVEST" test.

#### Exercise
What user stories could a video chat app have?
<details>
<summary>Example solutions (click to expand)</summary>
<p>

As Anne, I want to see my calendar within the app, so that I can schedule meetings without conflicting with my other engagements.

As Basil, I want to launch a meeting from a message my grandkids send me, so that I do not need to spend time configuring the app.

As a privacy enthusiast, I want my video chats to be encrypted end-to-end, so that my data cannot be leaked if the app servers get hacked.

</p>
</details>

#### Exercise
Which of the following are good user stories and why?
1. As a user, I want to log in quickly, so that I don't lose time
2. As a Google account owner, I want to log in with my Gmail address
3. As a movie buff, I want to view recommended movies at the top on a dark gray background with horizontal scroll
4. As an occasional reader, I want to see where I stopped last time, so that I can continue reading
5. As a developer, I want to improve the log in screen, so that users can log in with Google accounts
<details>
<summary>Solutions (click to expand)</summary>
<p>

1 is too vague, 2 is acceptable since the reason is implicit and obvious, 3 is way too specific, 4 is great, and 5 is terrible as it relates to developers not users.

</p>
</details>


## How can we develop from requirements?

You've listened to users, you abstracted them into personas and their requirements into user stories, and you developed an application based on that.
You're convinced your personas would love your app, and your implementation answers the needs defined by the user stories.
After spending a fair bit of time and money, you now have an app that you can demo to real users... and they don't like it.
It's not at all what they envisioned. What went wrong?
What you've just done, asking users for their opinion on the app, is _validation_: checking if what you specified is what users want.
This is different from _verification_, which is checking if your app correctly does what you specified.

One key to successful software is to do validation early and often, rather than leaving it until the end.
If what you're building isn't what users want, you should know as soon as possible, instead of wasting resources building something nobody will use.
To do this validation, you will need to build software in a way that can be described by users, using a _common language_ with them and _integrating_ them into the process so they can give an opinion.
We will see two ways to do this.

First, you need a _common language_ with your users, as summarized by Eric Evans in his 2003 book "Domain-Driven Design".
Consider making some candy: you could ask people in advance whether they want some candy containing NaCl, $C_{24}$ $H_{36}$ $O_{18}$, $C_{36}$ $H_{50}$ $O_{25}$, $C_{125}$ $H_{188}$ $O_{80}$, and so on.
This is a precise definition that you could give to chemists, who would then implement it. However, it's unlikely an average person will understand until your chemists actually build it.
Instead, you could ask people if they want candy with "salted caramel". This is less precise, as one can imagine different kinds of salted caramel, but much more understandable by the end users.
You don't need to create salted caramel biscuits for people to tell you whether they like the idea or not, and a discussion about your proposed candy will be much more fruitful using the term "salted caramel"
than using any chemical formula.

In his book, Evans suggests a few specific terms that can be taught to users, such as "entity" for objects that have an identity tied to a specific property,
"value object" for objects that are data aggregates without a separate identity, and so on.
The point is not which exact terms you use, but the idea that you should design software in a way that can be easily described to users.

Consider what a user might call a "login service", which identifies users by what they know as "email addresses".
A programmer used to the technical side of things might call this a `PrincipalFactory`, since "principals" is one way to call a user identity, and a "factory" is an object that creates objects.
The identifiers could be technically called "IDs".
However, asking a user "What should happen when the ID is not found? Should the principal returned by the factory be `null`?" will yield puzzled looks.
Most users do not know any of these terms.
Instead, if the object in the code is named `LoginService`, and takes in objects of type `Email` to identify users, the programmer can now ask the user
"What should happen when the email is not found? Should the login process fail?" and get a useful answer.

Part of using the _right_ vocabulary to discuss with people is also using _specific_ vocabulary. Consider the term "person".
If you ask people at a university what a "person" is, they will mumble some vague answer about people having a name and a face, because they do not deal with general "people" in their jobs.
Instead, they deal with specific kinds of people. For instance, people in the financial service deal with "employees" and "contractors", and could happily teach you exactly what those concepts are,
how they differ, what kind of attributes they have, what operations are performed on their data, and so on.
Evans calls this a "bounded context": within a specific business domain, specific words have specific meanings, and those must be reflected in the design.
Imagine trying to get a financial auditor, a cafeteria employee, and a professor to agree on what a "person" is, and to discuss the entire application using a definition of "person" that includes all possible attributes.
It would take forever, and everyone would be quite bored.
Instead, you can talk to each person separately, represent these concepts separately in code, and have operations to link them together via a common identity,
such as one function `get_employee(email)`, one function `get_student(email)`, and so on.

Once you have a common language, you can also write test scenarios in a way users can understand.
This is _behavior-driven development_, which can be done by hand or with the help of tools such as [Cucumber](https://cucumber.io/) or [Behave](https://behave.readthedocs.io/).
The idea is to write test scenarios as three steps: "_given ... when ... then ..._", which contain an initial state, an action, and a result.

For instance, here is a Behave example from their documentation:
```
Scenario Outline: Blenders
   Given I put <thing> in a blender,
    when I switch the blender on
    then it should transform into <other thing>

Examples: Amphibians
  | thing         | other thing |
  | Red Tree Frog | mush        |

Examples: Consumer Electronics
  | thing         | other thing |
  | iPhone        | toxic waste |
  | Galaxy Nexus  | toxic waste |
```
Using this text, one can write functions for "putting a thing in a blender", "switching on the blender", and "checking what is in the blender",
and Behave will run the functions for the provided arguments.
Users don't have to look at the functions themselves, only at the text, and they can then state whether this is what they expected.
Perhaps only blue tree frogs, not red ones, need to turn into mush. Or perhaps the test scenario is precisely what they need, and developers should go implement the actual feature.

The overall workflow is as follows:
1. Discuss with users to get their requirements
2. Translate these requirements into user stories
3. Define test scenarios based on these user stories
4. Get feedback on these scenarios from users
5. Repeat as many times as needed until users are happy, at which point implementation can begin

#### Exercise
What language would you use to discuss a course registration system at a university (or any other system you use frequently), and what test scenarios could you define?
<details>
<summary>Example solutions (click to expand)</summary>
<p>

A course registration system could have students, which are entities identified by a university e-mail who have a list of courses and grades associated with the courses, and
lecturers, which are associated with the courses they teach and can edit said courses. Courses themselves might have a name, a code, a description, and a number of credits.

Some testing scenarios might be "given that a user is already enrolled in a course, when the user tries to enroll again, then that has no effect", or "given that a lecturer
is in charge of a course, when the lecturer sets grades for a student in the course, then that student's grade is updated".

</p>
</details>


## What implicit requirements do audiences have?

Software engineers design systems for all kinds of people, from all parts of the world, with all kinds of needs.
Often some people have requirements that are _implicit_, yet are just as required as the requirements they will explicitly tell you about.
Concretely, we will see _localization_, _internationalization_, and _accessibility_.
Sometimes these are shortened to "l10n", "i18n", and "a11y", each having kept their start and end letter, with the remaining letters being reduced to their number.
For instance, there are 10 letters between "l" and "n" in "localization", thus "l10n".

_Localization_ is all about translations. Users expect all text in programs to be in their language, even if they don't explicitly think about it.
Instead of `print("Hello " + user)`, for instance, your code should use a constant that can be changed per language.
It is tempting to have a `HELLO_TEXT` constant with the value `"Hello "` for English, but this does not work for all languages because text might also come after the user name, not only before.
The code could thus use a function that takes care of wrapping the user name with the right text: `print(hello_text(user))`.

Localization may seem simple, but it also involves double-checking assumptions in your user interface and your logic.
For instance, a button that can hold the text "Log in" may not be wide enough when the text is the French "Connexion" instead.
A text field that is wide enough for each of the words in "Danube steamship company captain" might overflow with the German "Donaudampfschifffahrtsgesellschaftkapitän".
Your functions that provide localized text may need more information than you expect.
English nouns have no grammatical gender, so all nouns can use the same text, but French has two, German has three, and Swahili has [eighteen](https://en.wikipedia.org/wiki/Swahili_grammar#Noun_classes)!
English nouns have one "singular" and one "plural" form, and so do many languages such as French, but this is not universal; Slovenian, for instance, has one ending for 1, one for 2, one for 3 and 4, and one for 5 and more.

Translations can have bugs just like software can.
For instance, the game Champions World Class Soccer's German translation infamously has a bug in which "shootout" is not, as it should, translated to "schiessen",
but instead to "scheissen", which has an entirely different meaning despite being one letter swap apart.
Another case of German issues is [in the game Grandia HD](https://www.nintendolife.com/news/2019/08/random_amazingly_grandia_hds_translation_gaffe_is_only_the_second_funniest_german_localisation_mistake):
missing an attack displays the text "fräulein", which is indeed "miss" but in an entirely different context.

Localization is not something you can do alone unless you are translating to your native language, since nobody knows all features of all languages.
Just like other parts of software, localization needs testing, in this case by native speakers.

_Internationalization_ is all about cultural elements other than language.
Consider the following illustration:

<p align="center"><img alt="Illustration of (from left to right) a dirty t-shirt, a washing machine, a clean t-shirt" src="images/washing.svg" width="50%" /></p>

What do you see? Since you're reading English text, you might think this is an illustration of a t-shirt going from dirty to clean through a washing machine.
But someone whose native language is read right to left, such as Arabic, might see the complete opposite: a t-shirt going from clean to dirty through the machine.
This is rather unfortunate, but it is the way human communication works: people have implicit expectations that are sometimes at odds. Software thus needs to adapt.
Another example is putting "Vincent van Gogh" in a list of people sorted by last name. Is Vincent under "G" for "Gogh" or under "V" for "van Gogh"?
That depends on who you ask: Dutch people expect the former, Belgians the latter. Software needs to adapt, otherwise at least one of these two groups will be confused.
Using the culture-specific format for dates is another example: "10/01" mean very different dates to an American and to someone from the rest of the world.

One important part of localization is people's names.
Many software systems out there are built with odd assumptions about people's names, such as "they are composed entirely of letters", or "family names have at least 3 letters", or simply "family names are something everyone has".
In general, [programmers believe many falsehoods about names](https://shinesolutions.com/2018/01/08/falsehoods-programmers-believe-about-names-with-examples/),
which leads to a lot of pain for people who have names that do not comply to those falsehoods, such as people with hyphens or apostrophes in their names, people whose names are one or two letters long,
people from cultures that do not have a concept of family names, and so on.
Remember that _a person's name is never invalid_.

Like localization, internationalization is not something you can do alone. Even if you come from the region you are targeting, that region is likely to contain many people from many cultures.
Internationalization needs testing.

_Accessibility_ is a property software has when it can be used by everyone, even people who cannot hear, cannot see, do not have hands, and so on.
Accessibility features include closed captions, text-to-speech, dictation, one-handed keyboards, and so on. User interface frameworks typically have accessibility features documented along with their other features.
These are not only useful to people who have permanent disabilities, but also to people who are temporarily unable to use some part of their body.
For instance, closed captions are convenient for people in crowded trains who forgot their headphones. Text-to-speech is convenient for people who are doing something else while using an app on their phone.
Features designed for people without hands are convenient for parents holding their baby.

Accessibility is not only a good thing to do from a moral standpoint: it is often legally required, especially in software for government agencies.
It is also good even from a selfish point of view: a more accessible app has more potential customers.


## Are all requirements ethical?

We've talked about requirements from users under the assumption that you should do whatever users need. But is that always the case?

Sometimes, requirements conflict with your ethics as an engineer, and such conflicts should not be brushed aside.
Just because "a computer is doing it" does not mean the task is acceptable or that it will be performed without any biases.
The computer might be unbiased, but it is executing code written by a human being, which replicates that human's assumptions and biases, as well as all kinds of issues in the underlying data.

For instance, there have been many variants of "algorithms to predict if someone will be a criminal", using features such as people's faces.
If someone were to go through a classroom and tell every student "you're a criminal" or "you're not a criminal", one would reasonably be upset and suspect all kinds of bad reasons for these decisions.
The same must go for a computer: there is no magical "unbiased", "objective" algorithm, and as a software engineer you should always be conscious of ethics.


## Summary

In this lecture, you learned:
- Defining and formalizing user needs with requirements, personas, and user stories
- Developing based on requirements through domain-driven design and behavior-driven development
- Understanding implicit requirements such as localization, internationalization, accessibility, and ethics

You can now check out the [exercises](exercises/)!
