# Exam review

This document is a review of Exam 2, including common errors.


## Question 1

Your colleague was wrong for the first two and right for the last one.

Coverage is only a metric to help evaluate tests, having high coverage is not a goal in itself.

Function length is only a metric to help evaluate code quality, having short functions is not a goal in itself.

Cleaning up functions while you are editing them is good practice though; remember the scout rule, “leave the code cleaner than you found it”.

Common errors:
* Overall, many answers did not adhere to what was asked in the question.
  The question very specifically required a stance on a coding habit and a one-sentence justification.
  Very few actually explicitly took a stance, and most simply gave your opinions on coverage, modularity, maintainability, etc.
  While it is good to see that many had correct opinions, it is also important to realize what is asked and what you need to communicate to make it most efficient.
  This is important for being a good engineer in large software engineering teams as well.
* Part 1:
    * Describing different types of coverage and what are their benefits/drawbacks wasn’t the point
    * Giving opinions based on how much time there is to do this task; nobody has infinite time anyway, so there is always something else that could be done instead
* Part 2:
    * Just discussing at a high level that refactoring “makes sense” but 10 seems too small a number, without actually taking any explicit stance on what you think
* Part 3:
    * Describing a mixture of things like backward compatibility, harder to debug many changes, code health and technical debt.
      A mixture of these make it hard to discern what your stance on the coding habit is when you advocate both for and against the habit. 


## Question 2

Replacing `Document` by `String` breaks backward compatibility, which will affect consumers of the library.

The maintainability problem is an abstraction leak: users of the function should not have to know or care that it internally uses Google services.

To fix this problem, one could internally catch the Google error and throw a more general one, or, if the Google error already has
a non-Google-specific superclass that is precise enough, declare that the function throws that superclass instead.

Common errors:
* Part 1:
    * Buffer overflow attacks are not relevant here, this question included nothing about security
* Part 2:
    * The maintainability problem is not whether this function is well documented or not
    * The InputStream is not an abstraction leak.
    * Stating that there is an abstraction leak is enough, there is no need to define what an abstraction leak is
      (that’s why we say “abstraction leak”, so we don’t have to repeat the definition every time)
* Part 3:
    * Returning an empty InputStream or default value on error is not better than throwing an exception


## Question 3

Because the server knows that most of the time the client will ask for another image, it can prefetch the next image already, i.e.,
speculate that the work done to generate another image will be useful, lowering latency.

If the server’s interface (and thus the client) can be changed, the API could instead _batch_ images, instead of making one request per image, which will increase throughput.

The image generation should be a priority: even though the savings are greater in absolute terms for the reporting feature, moderation takes around a day
so saving a few seconds is too low in relative terms. This is why you should optimize for the common case.

Common errors:
* Part 1:
    * The image generation service should not cache images, since it is supposed to generate original images for each user request.
    * Speculating on future topics could improve performance but may be infeasible or too difficult to implement.
* Part 2:
    * Parallelizing queries does not reduce the per-request overhead.
    * Displaying images one by one instead of in a grid does not increase the speed of displaying “all 9 images”.
* Part 3:
    * Absolute time gain isn't a good metric to choose which feature to optimize first, the frequency of use is more important.


## Question 4

We expected a solid test suite that covers both successful cases of conversion and unsuccessful ones.

We then automatically graded the implementation with tests. We also manually looked at the implementation, to see how clean and maintainable it was.
We did not evaluate the simplicity of the implementation even though some of these were quite complicated, remember that this is a SwEng exam,
if you start to implement a convoluted algorithm, you probably are doing it wrong.

Common errors:
* Not testing the case when getLeft or getRight should return null
* Testing multiple functions at the same time, while this is good to have one test on an entire tree, you should also independently test each function’s behavior,
  so that if one test fails you know immediately where the bug is.
* Not following the interface semantics as documented in its Javadoc, the most common case was to throw an exception instead of returning null when one of the child trees was empty.
* Code duplication in the implementation, the getLeft and getRight methods should both use a private reusable method instead of copy/pasted code
* Cleanliness issues in the implementation, such as adding unnecessary public methods


## Question 5

One way to implement this cleanly is to make the cache a _middleware_ that intercepts weather forecast requests,
and that _observes_ forecast changes by introducing an interface. The middleware’s constructor checks if the underlying API is observable, and if so, registers for changes.

Thus, the middleware code won’t have to change with new weather providers, and cache invalidation will still work as long as they implement that new interface if they have that feature.

Common errors:
* We saw many separate “cache” classes that duplicate the functionality of a map, without adding anything new,
  sometimes with an interface even though there is only implementation.
  This is not useful unless you have a good reason to believe that you will need more behavior for the cache, or more than one implementation. YAGNI, “You Aren’t Gonna Need It”!
* Some caches included logic to “expire” cache entries that are “too old”, with various policies. The question did not ask for this, and it makes the code more complex than necessary.
* Some middleware implementations only accept UnstableWeatherAPI or some type more specific than WeatherAPI, making them not true middlewares since they cannot be used generally.
* Some implementations reinvented the wheel, for instance by using a loop over a map’s keys to delete each key instead of calling “clear()”.
