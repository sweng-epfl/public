# Performance

Producing correct results is not the only goal of software.
Results must be produced in reasonable time, otherwise users may not wait for a response and give up.
Providing a timely response is made harder by the interactions between software components and between software and hardware, as well as the inherent conflicts between different definitions of performance.
As a software engineer, you will need to find out what kind of performance matters for your code, measure the performance of your code, define clear and useful performance goals, and ensure the code meets these goals.
You will need to keep performance in mind when designing and writing code, and to debug the performance issues that cause your software to not meet its goals.


## Objectives

After this lecture, you should be able to:
- Contrast different metrics and scales for performance
- Create benchmarks that help you achieve performance objectives
- Profile code to find performance bottlenecks
- Optimize code by choosing the right algorithms and tradeoffs for a given scenario


## What is performance?

There are two key metrics for performance: _throughput_, the number of requests served per unit of time, and _latency_, the time taken to serve one request.

In theory, we would like latency to be constant per request and throughput constant over time, but in practice, this is usually never the case.
Some requests fundamentally require more work than others, such ordering a pizza with 10 toppings compared to a pizza with only cheese and tomatoes.
Some requests go through different code paths because engineers made tradeoffs, such as ordering a gluten-free pizza taking more time
because instead of maintaining a separate gluten-free kitchen, the restaurant has to clean its only kitchen.
Some requests compete with other requests for resources, such as ordering a pizza right after a large table has placed an order in a restaurant with only one pizza oven.

Latency thus has many sub-metrics in common use: mean, median, 99th percentile, and even latency just for the first request, i.e., the time the system takes to start.
Which of these metrics you target depends on your use case, and has to be defined in collaboration with the customer.
High percentiles such as the 99th percentile make sense for large systems in which many components are involved in any request, as [Google describes](https://research.google/pubs/pub40801/) in their "Tail at Scale" paper.
Some systems actually care about the "100th percentile", i.e., the _worst-case execution time_, because performance can be a safety goal.
For instance, when an airplane pilot issues a command, the plane must reply quickly.
It would be catastrophic for the plane to execute the command minutes after the pilot has issued it, even if the execution is semantically correct.
For such systems, worst-case execution time is typically over-estimated through manual or automated analysis.
Some systems also need no variability at all, i.e., _constant-time_ execution. This is typically used for cryptographic operations, whose timing must not reveal any information about secret keys or passwords.

In a system that serves one request at a time, throughput is the inverse of mean latency.
In a system that serves multiple requests at a time, there is no such correlation.
For instance, one core of a dual-core CPU might process a request in 10 seconds while another core has served 3 requests in that time, thus the throughput was 4 requests per 10 seconds but the latency varied per request.

We used "time" as an abstract concept above, but in practice you will use concrete units such as minutes, seconds, or even as low as nanoseconds.
Typically, you will care about the _bottleneck_ of the system: the part that takes the most time.
There is little point in discussing other parts of the system since their performance is not key.
For instance, if it takes 90 seconds to cook a pizza, it is not particularly useful to save 0.5 seconds when adding cheese to the pizza, or to save 10 nanoseconds in the software that sends the order to the kitchen.

If you're not sure of how long a nanosecond is, check out [Grace Hopper's thought-provoking illustration](https://www.youtube.com/watch?v=gYqF6-h9Cvg) (the video has manually-transcribed subtitles you can enable).
Grace Hopper was a pioneer in programming languages. With her team, she designed COBOL, the first language that could execute English-like commands.
As she [recalled](https://www.amazon.com/Grace-Hopper-Admiral-Computer-Contemporary/dp/089490194X) it,
"_I had a running compiler and nobody would touch it. They told me computers could only do arithmetic_". Thankfully, she ignored the naysayers, or we wouldn't be programming in high-level languages today!

_Amdahl's law_ states that the speedup achieved by optimizing one component is at most that component's share of overall execution time.
For instance, if a function takes 2% of total execution time, it is not possible to speed up the system more than 2% by optimizing that function, even if the function itself is made orders of magnitude faster.

As you can tell, the word "performance" hides a lot of details, and "improving performance" is usually too abstract a goal.
Optimizing a system requires clear goals defined in terms of specific workloads and metrics, and an understanding of which parts of the system currently take the most time.
Performance objectives for services used by customers are typically defined in terms of _Service Level Indicators_, which are measures such as "median request latency",
_Service Level Objectives_, which define goals for these measures such as "under 50ms", and _Service Level Agreements_, which add consequences to objectives such as "or we reimburse 20% of the customer's spending that month".

Picking useful objectives is a key first step. "As fast as possible" is usually not useful since it requires too many tradeoffs,
as [pizza chain Domino's found out](https://www.nytimes.com/1993/12/22/business/domino-s-ends-fast-pizza-pledge-after-big-award-to-crash-victim.html) after their promise of reimbursing late pizzas led to
an increase in car crashes for their drivers.
Systems should be fast enough, not perfect. What "enough" is depends on the context.
Some software systems have specific performance requirements.
Other times the performance requirements are implicit, making them harder to define.
For instance, in 2006 Google [found](http://glinden.blogspot.com/2006/11/marissa-mayer-at-web-20.html)
that traffic dropped 20% if their search results took 0.9s to show instead of 0.4s.
In 2017, Akamai [found](https://www.akamai.com/uk/en/about/news/press/2017-press/akamai-releases-spring-2017-state-of-online-retail-performance-report.jsp)
that even a 100ms increase in page load time could lead to a 7% drop in sales. In general, if users feel that an interface is "too slow", they won't like it, and if they have another choice they may use it instead.


## How can one measure performance?

The process of measuring performance is called _benchmarking_, and it is a form of testing for performance.
Like tests, benchmarks are usually automated, and come with issues such as how to isolate a specific component rather than always benchmark the entire system.
Benchmarks for individual functions are typically called _microbenchmarks_, while benchmarks for entire systems are "end to end".

In theory, benchmarking is a simple process: start a timer, perform an action, stop the timer.
But in practice, there are many tricky parts. How precise is the timer? What's the timer's resolution? How variable is the operation? How many measurements should be taken for the results to be valid?
Should outliers be discarded? What's the best API on each OS to time actions? How to avoid "warm up" effects? And caching effects? Are compiler optimizations invalidating the benchmark by changing the code?

You should never write your own benchmarking code. (Unless you become an expert in benchmarking, at which point you will know you can break this rule.)
Instead, use an existing benchmarking framework, such as JMH for Java, BenchmarkDotNet for .NET, or pytest-benchmark for Python.

In practice, to benchmark some code, you first need to define the workload you will use, i.e., the inputs, and the metric(s) you care about.
Then you need a _baseline_ for your benchmark. Absolute performance numbers are rarely useful, instead one usually compares to some other numbers, such as the performance of the previous version of the software.
For low-level code or when benchmarking operations that take very little time, you may also need to set up your system in specific ways, such as configuring the CPU to not vary its frequency.

It is very easy to accidentally write benchmark code that is meaningless in a non-obvious way.
Always ask your colleagues to review your benchmark code, and provide the code whenever you provide the results.
Try small variations of the code to see if you get small variations in the benchmark results.
If you have any, reproduce previous results to ensure that your overall setup works.

Beware of the "small input problem": it is easy to miss poorly-performing algorithms by only benchmarking with small inputs.
For instance, if an operation on strings is in `O(n^3)` of the string length and you only use a string of size 10, that is 1000 operations, which is instantaneous on any modern CPU.
And yet the algorithm is likely not great, since `O(n^3)` is usually not an optimal solution.

Make sure you are actually measuring what you want to measure, rather than intermediate measurements that may or may not be representative of the full thing.
For instance, [researchers found](https://dl.acm.org/doi/abs/10.1145/3519939.3523440) that some garbage collectors for Java had optimized for
the time spent in the garbage collector at the expense of the time taken to run an app overall. By trying to minimize GC time at all costs, some GCs actually increased the overall running time.

Finally, remember that it is not useful to benchmark and improve code that is already fast enough.
While microbenchmarking can be addictive, you should spend your time on tasks that have impact to end users.
Speeding up an operation from 100ms to 80ms can feel great, but if users only care about the task taking less than 200ms, they would probably have preferred you spend time on adding features or fixing bugs.


## How can one debug performance?

Just like benchmarking is the performance equivalent of testing, _profiling_ is the performance equivalent of debugging.
The goal of profiling is to find out how much time each operation in a system takes.

There are two main kinds of profilers: _instrumenting_ profilers that add code to the system to record the time before and after each operation,
and _sampling_ profilers that periodically stop the program to take a sample of what operation is running at that time.
A sampling profiler is less precise, as it could miss some operations entirely if the program happens to never be running them when the profiler samples,
but it also has far less overhead than an instrumenting profiler.

It's important to keep in mind that the culprit of poor performance is usually a handful of big bottlenecks in the system that can be found and fixed with a profiler.
The average function in any given codebase is fast enough compared to those bottlenecks, and thus it is important to profile before optimizing to ensure an engineer's time is well spent.
For instance, a hobbyist once managed to [cut loading times by 70%](https://nee.lv/2021/02/28/How-I-cut-GTA-Online-loading-times-by-70/) in an online game by finding the bottlenecks and fixing them.
Both of them were `O(n^2)` algorithms that could be fixed to be `O(n)` instead.

There are plenty of profilers for each platform, and some IDEs come with their own. For instance, Java has VisualVM.


## How can one improve performance?

The typical workflow is as follows:
1. Double-check that the code is actually correct and that it is slower than what users need (otherwise there is no point in continuing)
2. Identify the bottleneck by profiling
3. Remove any unnecessary operations
4. Make some tradeoffs, if necessary
5. Go to step 1

Identifying the problem always requires measurements. Intuition is often wrong when it comes to performance.
See online questions such as ["Why is printing "B" dramatically slower than printing "#"?"](https://stackoverflow.com/questions/21947452/why-is-printing-b-dramatically-slower-than-printing)
or ["Why does changing 0.1f to 0 slow down performance by 10x?"](https://stackoverflow.com/questions/9314534/why-does-changing-0-1f-to-0-slow-down-performance-by-10x).

Once a problem is found, the first task is to remove any unnecessary operations the code is doing, such as algorithms that can be replaced by more efficient ones without losing anything other than development time.
For instance, a piece of code may use a `List` when a `Set` would make more sense because the code mostly checks for membership.
Or a piece of code may be copying data when it could use a reference to the original data instead, especially if the data can be made immutable.
This is also the time to look for existing faster libraries or runtimes, such as [PyPy](https://www.pypy.org/) for Python, that are usually less flexible but are often enough.

If necessary, after fixing obvious culprits that do not need complex tradeoffs, one must make serious tradeoffs.
Unfortunately, in practice, performance tradeoffs occur on many axes: latency, throughput, code readability and maintainability, and sub-axes such as specific kinds of latency.

### Separating common cases

One common tradeoff is to increase code size to reduce latency by adding specialized code for common requests.
Think of how public transport is more efficient than individual cars, but only works for specific routes.
Public transport cannot entirely replace cars, but for many trips it can make a huge difference.
If common requests can be handled more efficiently than in the general case, it is often worthwhile to do so.

### Caching

It is usually straightforward to reduce latency by increasing memory consumption through _caching_, i.e., remembering past results.
In fact, the reason why modern CPUs are so fast is because they have multiple levels of caches on top of main memory, which is slow.

Beware, however: caching introduces potential correctness issues, as the cache may last too long, or return obsolete data that should have been overwritten with the results of a newer query.

### Speculation and lazy loading

You go to an online shop, find a product, and scroll down. "Customers who viewed this item also viewed..." and the shop shows you similar items you might want to buy, even before you make further searches.
This is _speculation_: potentially lowering latency and increasing throughput if the predictions are successful, at the cost of increasing resource use and possibly doing useless work if the predictions are wrong.

The opposite of speculation is _lazy loading_: only loading data when it is absolutely needed, and delaying any loading until then.
For instance, search engines do not give you a page with every single result on the entire Internet for your query, because you will most likely find what you were looking for among the first results.
Instead, the next results are only loaded if you need them. Lazy loading reduces the resource use for requests that end up not being necessary, but requires more work for requests that are actually necessary
yet were not executed early.

### Streaming and batching

Instead of downloading an entire movie before watching it, you can _stream_ it: your device will load the movie in small chunks, and while you're watching the first few chunks the next ones can be downloaded in the background.
This massively decreases latency, but it also decreases throughput since there are more requests and responses for the same amount of overall data.

The opposite of streaming is _batching_: instead of making many requests, make a few big ones.
For instance, the postal service does not come pick up your letters every few minutes, since most trips would be a waste; instead, they come once or twice a day to pick up all of the letters at once.
This increases throughput, at the expense of increasing latency.


## Summary

In this lecture, you learned:
- Defining performance: latency, throughput, variability, and objectives
- Benchmarking and profiling: measuring and understanding performance and bottlenecks
- Tradeoffs to improve performance: common cases, caching, speculation vs lazy loading, and streaming vs batching

You can now check out the [exercises](exercises/)!
