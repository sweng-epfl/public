# Exam review

This document is a review of Exam 3, including common errors.


## Question 1

Answer the question you are asked, not more.
There is no point in spending time writing about abstraction leaks or other possible issues when asked “what is your web services provider?”.
We did not penalize such answers unless they were really too long, but their authors did spend time on them that would have been better spent on the rest of the exam.

Common errors:
* Q1.1: Some people answered “SpotFleet” as the web service provider, presumably because of the error name.
  As the very first in-lecture exercise showed you, the answer to this kind of question is not something you can guess yourself, and searching online is the right way to go.
* Q1.2: “While loops” do not by themselves cause too many paths; `int x = 0; while(x < 5) { x++; }` has exactly one path.
  “Asking for user input” does not cause too many paths by itself either.
  It is possibly-infinite loops that cause many paths, and re-asking the user for input until they enter a valid one is one example of a possibly-infinite loop.
* Q1.2: Some answers did not mention paths at all, and would have been valid for any other kind of coverage, such as “maybe the team needs to push a fix very quickly”.
  One can also doubt the usefulness of quickly pushing a fix that has not been adequately tested.
* Q1.2: Some answers were tautologies, e.g., path coverage was dropped because the team had to drop path coverage.
* Q1.2: Path coverage is about paths in the code, not the space of possible inputs. A function can deal with strings and still have a single path, even though there are infinitely many strings.
* Q1.3: Some answers suggested using separate API credentials for testing and production. This can work in specific cases,
  but it may not be feasible depending on the API, and it risks running into rate limit issues as well.
* Q1.3: “API” is an interface, not an implementation. One does not create a “different API” for a mock, but a mock implementation of the same API.


## Question 2

By and large, most did well in this question. There were still a few of you that missed parts of the answer.

Common errors:
* Q2.1: Quite a few gave an explanation of why the _text box_ likely went out of view instead of why only the text disappeared.
  If you made this mistake, please note the subtle difference between what the question described as the bug and what you interpreted!
* Q2.2: Most of you realized that the current sprint is not mutable, but did not give the client an actionable suggestion such as also talking to the Product Owner
* Q2.3: Some of you identified that this was not a bug, but did not give a correct explanation. Please refer to the solutions.


## Question 3

When asked about whether an approach is right or not, make sure to clearly state your stance and avoid taking a neutral position by listing contradictory statements on the question topic.

Common errors:
* Q3.1: The app freezes because the code is synchronous and executed on the main thread, and not because we’re loading all the images at once.
* Q3.2: Caching works well if the same images are loaded frequently. However, it is not a general solution since it assumes a certain usage pattern.
  On the other hand, since the image grid is scrollable, lazy loading will limit image fetching to the content the user actually sees.
* Q3.2: Image compression techniques may already be applied.
* Q3.3: The details of the issue shouldn’t be discussed during the stand-up meeting, even if it might benefit one of your teammates.
  The meeting has to remain short, and technical details should be discussed afterwards.


## Question 4

Here we expected an implementation that follows what was described in `BookProvider.java`.

In particular findBooksByTitle should not throw exceptions, instead if for some reasons the chapters for a manga could not be fetched,
that manga should just not be included in the result. Note that we saw comments about why fetching the chapters for a manga would never fail,
since that manga was given after calling searchManga on the underlying API, but in practice you never really know. When you use an external API like this, things can always fail and be inconsistent.

For testing, for findBooksByTitle we expected at least 4 tests, one when the parameter is null, one when we search with an unknown title,
one where we search with a known title, and one when using an API which would return inconsistent results (returns a manga, but throws an exception when asked to returns its chapters).
For fetchPage we expected at least 3 tests for errors (null, unknown chapter, unknown page) and one test for the success path.

Common implementation errors: 
* Throwing exceptions in findBooksByTitle
* Throwing wrong type of exception in fetchPage
* Having all the logic to fetch and convert chapter, inside the Book constructor inside findBooksByTitle was one of the most common problems in term of maintainability
* Catching all types of exceptions, instead of just the relevant one
    * This anti-pattern is sometimes known as _Pokémon exception handling_… gotta catch’em all!

Common testing errors:
* Not testing all cases
* In fetchPage, many tested that passing a page number out of bounds should fail, but at the same time were testing with a non-existent chapter,
  meaning that it is unknown which case is actually tested, as it depends on which check is performed first in the implementation.
* Not asserting anything about the chapters when fetching a book, even though that was quite important to assert if the implementation is indeed working.
* Only asserting that a result is not null, or does not throw, is not very useful


## Question 5

The correct intuition for Part I was to mock the dependencies you want to test.
You might have noticed that the functions could take up to 10 seconds to complete, and decided to add a timeout of 11 seconds in your test.
However, in a real codebase you most likely won't be able to accurately estimate the time a specific function will take, so it is better to use mocks.
Remember also to give a name to your tests so that it is easy to understand what is tested, under what conditions, and what is expected.
If you don’t know what to test, check the coverage and try to identify what needs to be tested to reach 100%.

For Part II, note that the function is async. Also, be sure to read carefully the doc and what is asked of you. The function should do what the doc says, nothing more, nothing less. 

Common errors:
* Part I:
    * “Works”, “fails”, “correctly”, … in test names
    * Mocking the dependencies but making them async with random sleeps instead of immediately returning completed Futures
    * Forgetting to call join() on Futures before asserting 
    * Assert inside Futures 
    * Multiple checks in one test
    * Not asserting anything in tests
    * try-catch to assert exceptions instead of assertThrows
    * No mocks and overly short timeouts
    * Writing test for others classes than the target one, which was unnecessary
* Part II:
    * Sort in wrong order
    * getAllBooks then join -> not async anymore
    * Using thenCompose + CompletableFuture.completedFuture | CompletableFuture.failedFuture, instead of thenApply which is simpler
    * Some checked for grade > 0 (not specified in doc)
    * Checking number >= list.size instead of number > list.size as specified
