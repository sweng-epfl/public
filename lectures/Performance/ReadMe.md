# Performance

Producing correct results is not the only goal of software.
Results must be produced in reasonable time, otherwise users may not wait for a response and give up.
Making software faster can dramatically improve the user experience, for instance by [producing nicer-looking video games](https://www.youtube.com/watch?v=Drame-4ufso).
Speed can also have more direct consequences, as Google once discovered when they found that [0.5s more time to display search results led to a 20% loss in ad revenue](http://glinden.blogspot.com/2006/11/marissa-mayer-at-web-20.html).


## Objectives

After this lecture, you should be able to:
- Define performance goals
- Create benchmarks to measure performance
- Design and implement fast systems
- Know common performance tradeoffs


## How are performance goals defined?

There are many factors to take into account when defining goals. What unit to use? What metric? How much variability is acceptable? How formal should the goal be? Let's examine each in turn.

Depending on the operation you're measuring, the _unit_ of measurement can vary wildly.
Restoring data from [magnetic tapes](https://en.wikipedia.org/wiki/Magnetic-tape_data_storage) used for long term storage can take hours if a truck has to drive to the storage location, get the tapes, bring them to a data center, and copy them.
In that case, whether the operation takes 3 or 10 hours probably matters, but whether it's 3 hours and 10 minutes or 3 hours and 12 minutes is unlikely to be important.
Backing up a database from one server to another might take a few minutes.
Again, whether it's 4 or 15 minutes matters, but whether it's 4 minutes and 3 seconds or 4 minutes and 10 seconds is unlikely to be important.
We can go down to smaller and smaller units.
Uploading a large file to a server is measured in seconds.
Fetching a Web page is measured in milliseconds.
The request made from one server to another within a datacenter to serve that Web page takes microseconds.
Computations on a single server take nanoseconds, since modern CPUs perform billions of operations per second.

If you're not sure of how long a nanosecond is, check out [Grace Hopper's thought-provoking illustration](https://www.youtube.com/watch?v=gYqF6-h9Cvg) (the video has manually-transcribed subtitles you can enable).
Grace Hopper was a pioneer in programming languages. With her team, she designed COBOL, the first programming language that used English-like commands.
As she [recalled](https://www.amazon.com/Grace-Hopper-Admiral-Computer-Contemporary/dp/089490194X) it,
"_I had a running compiler and nobody would touch it. They told me computers could only do arithmetic_".
Thankfully, she ignored the naysayers, or we wouldn't be programming in high-level languages today!

Once we've settled on a unit of time, there are two key metrics for performance.
_Throughput_ is the number of requests served per unit of time, and _latency_ is the time taken to serve one request.
In a system that serves one request at a time, throughput is the inverse of mean latency.
In a system that serves multiple requests at a time, there is no such correlation.
For instance, one core of a dual-core CPU might process a request in 10 seconds while another core has served 3 requests in that time, thus the throughput was 4 requests per 10 seconds but the latency varied per request.
We often only want to consider successful requests, since quickly serving an error response is rarely useful.
The throughput of successful requests is called "_goodput_".

There are of course other performance metrics than time.
The amount of memory a program uses can matter, as does the amount of storage space. For instance, a program that finishes quickly but requires more RAM than your computer has is not useful to you,
and you would be better off with a slower program that can actually run on your machine.
Energy efficiency is also an important metric, which can translate to battery life for mobile devices such as smartphones.
Users are unlikely to want a slightly faster program that eats their phone's battery.

In theory, we would like performance to be the same for all requests and all users, but in practice, this is rarely the case.
Some requests fundamentally require more work than others, such ordering a pizza with 10 toppings compared to a pizza with only cheese and tomatoes.
Some requests go through different code paths because engineers made tradeoffs, such as ordering a gluten-free pizza taking more time
because instead of maintaining a separate gluten-free kitchen, the restaurant has to clean its only kitchen.
Some requests compete with other requests for resources, such as ordering a pizza right after a large table has placed an order in a restaurant with only one pizza oven.

There are many sub-metrics in common use to deal with statistics: mean, median, 99th percentile, and even performance just for the first request, i.e., including the time the system takes to start.
Which of these metrics you target depends on your use case, and has to be defined in collaboration with customers.
High percentiles such as the 99th percentile make sense for large systems in which many components are involved in any request, as [Google describes](https://research.google/pubs/pub40801/) in their "Tail at Scale" paper.
Some systems actually care about the "100th percentile", i.e., the _worst-case execution time_, because performance can be a safety goal.
For instance, when an airplane pilot issues a command, the plane must reply quickly.
It would be catastrophic for the plane to execute the command minutes after the pilot has issued it, even if the execution is semantically correct.
For such systems, worst-case execution time is typically over-estimated through manual or automated analysis.
Some systems also need no variability at all, i.e., _constant-time_ execution. This is typically used for cryptographic operations, whose timing must not reveal any information about secret keys or passwords.

While users can give you subjective perceptions of what is "fast" and what is "slow", customers need formal definitions to be put in a contract.
Performance is typically defined in terms of _Service Level Indicators_, which are measures such as "median request latency",
_Service Level Objectives_, which define goals for these measures such as "under 50ms", and _Service Level Agreements_, which add consequences to objectives such as "or we reimburse 20% of the customer's spending that month".
For instance, pizza chain Domino's once had a marketing campaign stating your pizza would be free if it did not arrive within 30 minutes of your order.
Time to receiving the pizza is the SLI, 30 minutes the SLO, and the offer of a free pizza is the SLA.
This specific SLA turned out to [be a terrible idea](https://www.nytimes.com/1993/12/22/business/domino-s-ends-fast-pizza-pledge-after-big-award-to-crash-victim.html) as it led to an increase in car crashes for their drivers.
SLAs can even be codified into law, such as the [Swiss ordinance on the Swiss Post](https://www.fedlex.admin.ch/eli/cc/2012/586/fr#art_32) giving specific delays for letters and packages, percentiles of deliveries that must meet these delays,
and instructions for the Swiss Post to mandate an external observer for these measures.


## How is performance measured?

The process of measuring performance is called _benchmarking_, and it is a form of testing for performance.
Like tests, benchmarks are usually automated, and come with issues such as how to isolate a specific component rather than always benchmark the entire system.
Benchmarks for individual functions are typically called _microbenchmarks_, while benchmarks for entire systems are "end to end".

In theory, benchmarking is a simple process: start a timer, perform an action, stop the timer.
But in practice, there are many tricky parts. How precise is the timer? What's the timer's resolution? How variable is the operation? How many measurements should be taken for the results to be valid?
Should outliers be discarded? What's the best API on each OS to time actions? How to avoid "warm up" effects? And caching effects? Are compiler optimizations invalidating the benchmark by changing the code?

You should never write your own benchmarking code. Unless you one day become an expert in benchmarking, at which point you will know you can break this rule.
Instead, use an existing benchmarking framework, such as JMH for Java, BenchmarkDotNet for .NET, or pytest-benchmark for Python.

The general process of benchmarking is:
1. Choose the code you want to benchmark
2. Choose the _baseline_ you want to benchmark against, which could be another piece of code or an absolute level of performance
3. Choose the inputs for your benchmark, depending on what realistic or common inputs are for your software
4. Write and run your benchmark

Even when using a benchmarking framework, you should still be aware of some important facts.
First, optimizations are not always your friend.
Consider the following Python function:
```python
lst = ...
def append():
    lst + [0]
```
If you benchmark `append`, you may not be benchmarking anything at all, because Python could notice that the result of `+` is not used, and since it has no side effects, not perform it at all,
as if you had written `def append(): pass`.
This can be worked around, for instance, by returning the result of `+`.
But even if you do this, did you intend to benchmark just `+`? Because this method also creates a 1-item list, and that operation's time will be included in the result.
So you may want to write this instead:
```python
lst = ...
lst2 = ...
def append():
    return lst + lst2
```

It's easy to accidentally write benchmark code that is meaningless in a non-obvious way.
Always ask someone to review your benchmark code, and provide the code whenever you provide the results.
Try small variations of the code to see if you get small variations in the benchmark results.
Reproduce previous results, if you have any, to ensure that your overall setup works.

---
#### Exercise
Now that you know how to benchmark, open [the `benchmarking` in-lecture exercise project](exercises/lecture/benchmarking).

First, run the benchmarks. Do the results match your intuition?

Second, add new benchmark methods to test the same operations but with `LinkedList` and run the benchmarks. Again, do the results match what you expected?

Finally, write a benchmark in which `ArrayList` is clearly faster.

<details>
<summary>Example solution (click to expand)</summary>
<p>

It's expected that prepending a value to `ArrayList` is much slower, as the list must be copied to a new array in order to add an element at the start,
whereas most appends can reuse the existing array that is not yet full.

`LinkedList` makes it fast to append at the start, since only a few successor/predecessor links need updating.

A simple benchmark that is much faster is to access the middle element of the list. It's nearly instantaneous with an array list, whereas a lot of nodes need traversing using a linked list.

</p>
</details>

---

Before using your newfound knowledge to write tons of benchmarks, here are some important issues to keep in mind.

Beware of focusing too much on intermediate measurements that may or may not be representative of the full thing.
For instance, [researchers found](https://dl.acm.org/doi/abs/10.1145/3519939.3523440) that some garbage collectors for Java had optimized for
the time spent in the garbage collector at the expense of the time taken to run an app overall. By trying to minimize GC time at all costs, some GCs actually increased the overall running time.

Beware of the "small input problem": it is easy to miss poorly-performing algorithms by only benchmarking with small inputs.
For instance, if you sort a list of `10` items, any two sorting algorithms will be very close to each other, even if you are comparing a very inefficient one
like ["bubble sort"](https://en.wikipedia.org/wiki/Bubble_sort) to a very efficient one like ["quick sort"](https://en.wikipedia.org/wiki/Quicksort).

Beware of which workload you use, especially when measuring frameworks designed to be invoked alongside user operations.
For instance, [researchers found](https://www.usenix.org/conference/osdi20/presentation/pirelli) that one networking framework could achieve higher throughput than another when running a "no-op" network function that simply forwards packets,
but the second framework was much better when running a "real" network function that inspected traffic and took decisions on which packets to let through.
Indeed, both the framework and the network function code were competing for resources such as CPU caches.

Another interesting example of a workload is [this issue in the .NET repository](https://github.com/dotnet/runtime/issues/38660).
A simple benchmark that generates strings and puts them in a map shows great performance for Java's `HashMap` compared to .NET's `Dictionary`, which both do the same task.
It turns out the reason is that Java's default string hash function is very different from .NET's, which means for the specific case of very similar strings, `HashMap` looks faster,
but `Dictionary` performs similarly when using random strings.

Finally, remember that it is not useful to benchmark and improve code that is already fast enough.
While microbenchmarking can be addictive, you should spend your time on tasks that have impact to end users.
Speeding up an operation from 100ms to 80ms can feel great, but if users only care about the task taking less than 1s, they would probably have preferred you spend time on adding features or fixing bugs.


## How can we design fast systems?

Designing fast systems is a form of engineering, in the same way that designing correct and secure systems is about engineering.
Importantly, one should _not_ approach performance in a "set of tips and tricks" fashion. Knowing about low-level bit manipulation techniques can occasionally come handy, but the biggest impact on a system's performance is its design.
The idea of "performance as a feature" is the _wrong_ way to go. One does not easily "add performance" to an existing system, in the same way that it is difficult to "add correctness" or "add security" to a wrong or insecure system.

You want your system to be on the [_Pareto frontier_](https://en.wikipedia.org/wiki/Pareto_front) of your chosen performance metrics.
For instance, for a given level of throughput, there are many systems with different mean latency, and you'd like the best one.
There is no point in having a system that is worse than what it could be.
On the other hand, there are many combinations of throughout and mean latency, and it is not clear which one is the right tradeoff along the Pareto frontier.

We used "throughput" and "mean latency" in the example above, but there are many different axes.
Some of them are not related to performance but to other concerns such as maintainability and code readability.
It's often better to optimize for maintainability if the code does not need to be as fast as possible, for instance.
And even within a performance metric such as latency, you will need to trade off statistics such as "throughput for request kinds X and Y" for others such as "99.99% latency overall".
Because using a lot of axes is [hard to visualize](https://en.wikipedia.org/wiki/10-cube), you typically want to focus on 2 or 3 specific metrics when dealing with performance.

We will see three general performance concerns: high-level dataflow, specialization, and efficient use of available tools.
All three revolve around designing systems that do only what is necessary, without extra operations or overly general abstractions, because the fastest code is the code that doesn't need to exist.

_High-level dataflow_ is the most important factor in a system's overall performance.
A system that processes data in stages, transferring it from one module to another and possibly running multiple instances of a stage in parallel, is going to be a lot faster than a system
that copies data all over the place, sends data in cycles between modules, and cannot be parallelized due to complex dependencies between stages.
This is a big reason _immutable_ data structures are seen as a good engineering practice: you don't need to copy them, and you can process them in parallel if needed.
It's easy to accidentally write code that copies data between modules, or worse, to design a system such that data copies are required to implement its public interface.

Consider networking buffers as an example.
Traditional networking works by having the app ask the operating system to receive data. The OS then asks the network card to receive data.
Once the card has received data, it fills a buffer that the OS provided, and the OS then copies that buffer to a buffer the app provided.
While the copy from the network to a buffer is required to store the data at all, the copy from the OS buffer to the app buffer is wasteful work.
It's necessary in traditional networked systems because the app is allowed to pass any buffer at all, even one that doesn't satisfy the requirements of the network card, such as not being large enough.
Newer "zero copy" network APIs, such as [DPDK](https://www.dpdk.org/), instead require apps to allocate buffers in a specific way.
The app can then pass the buffer to the OS, who instructs the network card to use the buffer directly without a copy.
You may notice when thinking about this example that there is a form of abstraction leak here: in the "zero copy" model, the app must be aware of low-level network card requirements.
This is a tradeoff between maintainability and performance, known as "layer bypass".
Modern virtual machines work the same way: it would be extremely slow for the host to intercept every single instruction the guest wants to execute, so instead only some important instructions are intercepted,
and most instructions are executed directly.

_Specialization_ is another important aspect of system performance.
Writing the most general-purpose system possible typically leads to poor performance without bringing in any real benefits, as few systems need to handle very large varieties of requests.
For instance, if you want to paint a wall, you would pick a large paint roller, not a tiny brush.
The tiny brush is useful for all kinds of painting, since you cannot use a paint roller for small tasks, but it is extremely inefficient for painting a wall.
Yet many systems end up doing the equivalent of painting a wall with a tiny paint brush, because they are "over-engineered" to be extremely general without a need for this.

An interesting example of generality is the statement `a += b` in different programming languages, given, e.g., `a = 0, b = 42`.
In a compiled language like Java, if `a` and `b` are `int`s, this addition is compiled to a single assembly instruction, such as `add eax, ebx` in x86 assembly.
This is because the compiler knows exactly what operation is to be done: addition on 32-bit integers.
On the other hand, in an interpreted language like Python, the interpreter must first figure out what `a` and `b` are, find a method that implements `+=` or `+`, and then execute it.
Even then, after finding out that they are integers, Python still has more work to do because Python integers can have unbounded length, thus they may not fit in a single CPU register,
so there is yet more code to handle the case where the addition result fits in a register and the case where it does not.
If all the user wanted was to add a couple of small numbers, using Python is very inefficient.

_Using available tools efficiently_ is the final key aspect of system performance we will see.
At a high level, it seems simple. Use a hammer if you want to drive a nail, not the handle of a paintbrush, even though it is technically possible to use a paintbrush with enough patience.
As obvious as it seems, there are many systems out there that accidentally do the equivalent of driving nails with a paintbrush.

Consider the "N+1 query problem", illustrated by this example:
```java
var cars = getAllCars(); // e.g., "SELECT id FROM cars"
for (var car : cars) {
  var plate = getPlate(car); // e.g., "SELECT plate FROM cars WHERE id = ..."
  // ...
}
```
This code performs 1 query to list cars, then 1 query for each of the N cars to get its license plate.
Thus, there are `N+1` queries, even though the database is perfectly capable of returning all cars and their plates in 1 query.
This problem is rarely as obvious as in a 3-line example. Typically, there will be one general-purpose module calling another general-purpose module, which can be merged into one efficient and specialized module.

As another example, let's say you need to write a system in Python.
You could use the default implementation, called CPython, which lets you do anything you want.
But you may want higher performance using the PyPy "just-in-time" compiler, which claims to be 3x faster than CPython.
However, PyPy is [not 100% compatible](https://pypy.org/compat.html) with CPython, as some edge cases do not behave the same way in both.
If you consider these edge cases while designing your system, you can make sure the system is PyPy-compatible, whereas retrofitting a complex system that uses advanced Python features to use PyPy is a lot harder.

Once you have exhausted general-purpose options for efficient system design, it's time for tradeoffs.
Which cases are common? Which requests can take more time than others? Which metrics matter?
Nobody can answer these questions for all possible systems.


## How can we write fast code?

Now that we discussed designing fast systems, let's see how one can write fast code at the level of individual functions and classes.
The general workflow is:
1. Write correct code, as there is no point in an incorrect system that produces fast answers
2. Define a performance goal, so you know when to stop
3. Find the _bottleneck_, i.e., the part that takes the most time
4. Speed this bottleneck up
5. Repeat the process until you've reached your goal

Let's start with a simple example to discuss improving latency and throughput: a pizzeria where waiters give orders to the cook, who prepares the dough, adds toppings, cooks the pizza, then puts it on a plate and hands it to the waiter.
Because this example processes one request at a time, throughput and latency are linked by a simple equation: `throughput = 1 / latency`.
That is, decreasing the time it takes to make an individual pizza directly increases the number of pizzas made per second.

There are some simple options to decrease the latency of our pizzeria example, such as training the cook to become faster, or increasing the oven temperature to bake pizzas faster.
One could also increase throughput in many ways, such as hiring more cooks, or in any of the ways that decrease latency.
However, the pizzeria's _bottleneck_ is likely the oven. It takes more time to cook a pizza than to add toppings or cut it.
There is no point in hiring many more chefs if they all have to wait for their turn to use the oven.
More generally, there is little point in discussing the performance of non-bottleneck parts of the system since their performance is not key.
For instance, if it takes 90 seconds to cook a pizza, it is not particularly useful to save 0.5 seconds when adding cheese to the pizza, or to save 10 nanoseconds in the software that sends the order to the kitchen.

This example illustrates _Amdahl's law_, which states that the overall speedup achieved by optimizing one component is limited by that component's share of overall execution time.
For instance, if a function takes 2% of total execution time, it is not possible to speed up the system more than 2% by optimizing that function, even if the function itself is made orders of magnitude faster.

It's important to keep in mind that the culprit of poor performance is usually a handful of big bottlenecks in the system that can be found and fixed with a profiler.
The average function in any given codebase is fast enough compared to those bottlenecks, and thus it is important to profile before optimizing to ensure an engineer's time is well spent.
For instance, a hobbyist once managed to [cut loading times by 70%](https://nee.lv/2021/02/28/How-I-cut-GTA-Online-loading-times-by-70/) in an online game by finding two bottlenecks and fixing them.
Both of them were inefficient algorithms that could be changed to more efficient versions without compromising maintainability.

Identifying these bottlenecks always requires measurements. Intuition is often wrong when it comes to performance.
See online questions such as ["Why is printing "B" dramatically slower than printing "#"?"](https://stackoverflow.com/questions/21947452/why-is-printing-b-dramatically-slower-than-printing)
or ["Why does changing 0.1f to 0 slow down performance by 10x?"](https://stackoverflow.com/questions/9314534/why-does-changing-0-1f-to-0-slow-down-performance-by-10x).
Another concrete example is [this .NET Runtime pull request](https://github.com/dotnet/runtime/pull/76619) that provides a 25% speedup in the debug version of the runtime for a complex input,
by... using a more specialized way to write log messages that does not take a global lock.
One would not expect a large runtime to be bottlenecked by a single lock used in debug prints, yet that pull request made a bigger impact than any change in the compiler or standard library of the runtime could.

So how does one measure the time it takes to execute various parts of the code?
The answer is _profiling_. There are two main kinds of _profilers_:
There are two main kinds of profilers: _instrumenting_ profilers that add code to the system to record the time before and after each operation,
and _sampling_ profilers that periodically stop the program to take a sample of what operation is running at that time.
A sampling profiler is less precise, as it could miss some operations entirely if the program happens to never be running them when the profiler samples,
but it also has far less overhead than an instrumenting profiler.
There are plenty of profilers for each platform, and some IDEs come with their own.

Let us now discuss a few common performance fixes:
choosing appropriate right data structures and algorithms,
speeding up common cases, and
avoiding unneeded work.

The complexity of data structures and algorithms can be expressed with ["big O" notation](https://en.wikipedia.org/wiki/Big_O_notation),
which defines the _asymptotic complexity_ of an operation.
For instance, prepending to an array-based list is in `O(N)` where `N` is the list size, as it requires copying all elements to a new array,
whereas querying the value of an array-based list at a specific index is in `O(1)` as it takes constant time regardless of which index is queried.
Inversely, a linked list has `O(1)` prepending and `O(N)` querying.
Thus, depending on which operation is needed in a piece of code, choosing between these two lists can make a huge difference.
Similarly, some algorithms are simply better than others. Bubble sort is simple but always in `O(N^2)`, whereas quicksort is typically in `O(N log N)`,
so one can swap the former for the latter and almost always improve performance.

One can often speed up the common case encountered by a system at the expense of rare cases.
For instance, if profiling reveals that a 90% of requests to a system query data while the remaining 10% modify data,
the system can be sped up in practice by picking data structures and algorithms that make data queries faster, even if modifications become slower.

But the fastest code is the code that does not need executing at all.
Sometimes operations can be combined to avoid doing unneeded work.
For instance, consider the following code:
```python
sortedLst = sorted(lst)
return sortedLst[0]
```
Sorting is, in the best case, `O(N log N)`. Querying is then, in the best case, `O(1)`.
But what this code is actually doing is getting the smallest item in the list, i.e., `min(lst)`, which can be done in `O(N)` by looking through all elements one by one
and keeping track of the minimum.
There is no point in discussing which sorting algorithm to use or which kind of list has faster querying when both these operations can be replaced by a simpler one.

Finally, it's important to take a step back when dealing with slow code and think about its design.
Most of the time, the largest performance gains or losses are decided by high-level design, as we discussed above.
One cannot "optimize" bubble sort line by line to get something as fast as quicksort, because they have fundamentally different designs that lead to fundamentally different performance.

---
#### Exercise
Try some basic profiling. Open [the `profiling` in-lecture exercise project](exercises/lecture/profiling) and follow the instructions in its ReadMe.

Are the results from profiling what you expected?

How could you make it faster?

<details>
<summary>Example solution (click to expand)</summary>
<p>

Creating a string character-by-character in the way the project does is extremely slow.
Every time a new character is added, Java copies the entire string, since Java strings are immutable.

At the very least, you could use a `StringBuilder` to avoid all these copies.
Ideally, you'd work line-by-line instead of character-by-character.

</p>
</details>

---


## What are common performance tradeoffs?

After you've exhausted general performance improvements in design and implementation, it's time for tradeoffs.
We'll discuss four important ones, which are by no means the only ones:
- Recomputing vs. caching
- Eager vs. lazy loading
- Immediate vs. deferred execution
- Batching vs. streaming


### Recomputing vs. caching

Unstead of _recomputing_ a response to every request, you can _cache_ responses.
This works similarly to what we do in the real world. When you're reading a book, you keep it on a nearby table, and you probably also have a bookshelf with books you like.
For new books, or for books that you read a long time ago and didn't keep, you go to the bookstore.
It would be a huge waste to buy a book every time you want to read part of a chapter only to throw the book away afterwards.
However, it would also be wasteful to keep every book you've ever read in your bookshelf, as you'd run out of space quickly.

You might thus implement caching like this Python pseudocode:
```python
cache = {}
def getContents(id):
  res = cache.get(id)
  if res is None:
      cache[id] = res = ...
  return res
```
Importantly, you should always measure whether a cache is worth it.
For tasks that only involve computations and no I/O, it's often faster to recompute the result every time than to access RAM that holds a previously computed result,
because CPUs are extremely fast and RAM is not that fast in comparison.

If you have a cache, you can also _prefetch_ responses, i.e., make requests that you think the user will make later so that the response is ready by the time you need it.
An example of this is shopping websites that tell you "customers who bought this also bought...". The website spent extra computational power on finding related items because it thinks you're likely to want these items.

Caching is not as simple as it sounds, however. You need to decide how many responses to keep in cache, whether responses expire and if so after how much time, whether to prefetch and if so how much, and so on.
For instance, if you're designing a weather forecast app, you probably don't want to serve cached responses for "what's the weather today" that are hours old, as the forecast will have changed.

Overall, compared to recomputing, caching lowers latency and increases throughput for common requests, but it also increases memory use, and decreases code maintainability as there is more code and especially more complex logic.

### Eager vs. lazy loading


Instead of _eagerly_ doing work that might be needed, you can _lazily_ do only the exact work necessary.
For instance, search engines do not give you a page with every single result on the entire Internet for your query, because you will most likely find what you were looking for among the first results.
Instead, the next results are only loaded if you need them. Lazy loading reduces the resource use for requests that end up not being necessary, but requires more work for requests that are actually necessary yet were not executed early.

Thus, you might use a "paginated find" query that asks for N items starting at index M instead of a "find all" query that returns all items in the database.
You may also want to only initialize a component the first time it is used, rather than at program startup:
```python
def doStuff():
  if not _initialized:
    init()
    _initialized = True
  ...
```

However, lazy loading adds more complexity as you must handle concerns such as what to do if two requests to the same uninitialized component happen in parallel.
Is it acceptable to initialize it twice? Should you have some way to "lock" access so that the second request waits for the initialization performed by the first request to complete?
In practice, you can often use existing modules to do this, such as [`Lazy<T>`](https://learn.microsoft.com/en-us/dotnet/api/system.lazy-1) in .NET.

Overall, compared to eager loading, lazy loading decreases the amount of work for unneeded requests, but increases the amount of work for needed requests, and once again decreases code maintainability due to more and more complex logic.

### Immediate vs. deferred execution

Instead of _immediately_ sending a request to a server when a user clicks a button and waiting for the server to answer,
you can _defer_ the response by showing a "loading..." message and starting a background task that contacts the server and updates the user interface once it gets a response.
This ensures your app remains responsive even if the server is slow to respond.

Another example of this happens in your IDE: when you type the beginning of a line, such as `System.out.`, the IDE will propose possible next inputs, such as `println`, and maybe even sort them according to an expected likelihood.
However, if your IDE were to block all user interface work while it computed the list of next inputs, typing would be extremely slow.
Instead, each typed letter starts a background task to find what you could be typing next, and if a subsequent letter invalidates the previous task's output, a new task is started.
This then involves managing concurrent tasks to ensure results do not appear out of order, such as proposing `println` even though you typed `fl` because you wanted `flush`.

It's also important to deal with _cancellation_, i.e., not continuing to do work that isn't necessary anymore. For instance, if the user asks for the weather but then leaves the app while the weather is loading,
the code that parses the server response does not need to run once the server has responded, because the result isn't necessary.

Overall, compared to immediate execution, deferred execution increases responsiveness but also increases latency as there is more work to do, and once again decreases code maintainability.

### Batching vs. streaming

Instead of downloading an entire movie as a _batch_ before watching it, you can _stream_ it: your device will load the movie in small chunks, and while you're watching the first few chunks the next ones can be downloaded in the background.
This massively decreases latency, but it also decreases throughput since there are more requests and responses for the same amount of overall data.

For instance, in Java, batching means returning a `List<...>` that contains all items, while streaming means returning a `Stream<...>` that fetches items one by one.
Some languages even have tools to make streaming easier, such as `yield return` in C# or `yield` in Python:
```python
def get_zeroes():
  while True:
    print("Yielding")
    yield 0
```
Note how this stream is infinite, which would not be possible to do with a batch.
If you call it in the following way:
```python
for n in get_zeroes():
  print("n = ", n)
```
The output will be `Yielding`, then `n = 0`, then `Yielding` again, then `n = 0` again, forever.
The `get_zeroes` method only runs until the `yield`, then yields back control to the loop for each iteration.

If you were to call `sum(get_zeroes())`, your program would hang forever, because it would attempt to sum an infinite sequence.
You must instead use methods that stop early if your sequence might be infinite, for instance, in Java:
```java
stream.filter(x -> x > 5)
      .findFirst()
```
This code will look at the first element of the stream, and if it's greater than `5`, returns it without looking at the rest.
If not, it looks at the second element, checks if it's greater than `5`, and so on.

Overall, compared to batching, streaming lowers latency but also lowers throughput.

---
#### Exercise
Let's optimize an app for responsiveness. Open [the `tradeoffs` in-lecture exercise project](exercises/lecture/tradeoffs) and run it.
As you can tell, it takes a while to show the results.
Looking at the code, you can see an entire batch of results is loaded at once.

Follow the three exercises in the code comments: stream the results, cache them, and add some prefetching.

<details>
<summary>Example solution (click to expand)</summary>
<p>

To stream, change the return type of `getNews` to be `Stream<String>`, and its implementation to something like:
```java
return IntStream.range(index, index + count)
                .mapToObj(App::getNewsItem);
```
Then you can use it with `getNews(...).forEach(System.out::println);` instead of a for loop.

To cache, change `getNews` to use a new `getNewsItemCached` function that uses a map to keep results:
```java
static Map<Integer, String> CACHE = new HashMap<>();
static String getNewsItemCached(int index) {
    // in a real app, you may want to limit the size of this map
    return CACHE.computeIfAbsent(index, App::getNewsItem);
}
```

To prefetch, you can for instance add the following right after calling `getNews(...).forEach(...)` in `main`:
```java
// `toArray` ensures the stream is actually iterated, otherwise nothing will happen
Executors.newSingleThreadExecutor().submit(() -> getNews(index + BATCH_SIZE, BATCH_SIZE).toArray());
```
Don't forget to change the cache to, e.g., a `ConcurrentHashMap` otherwise your app might crash if the user makes a request currently being prefetched.

</p>
</details>


## Summary

In this lecture, you learned:
- Defining goals: latency, throughput, and variability
- Optimization: design, benchmarking, and profiling
- Common tradeoffs: caching, laziness, deferral, and streaming

You can now check out the [exercises](exercises/)!
