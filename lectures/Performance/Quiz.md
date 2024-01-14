## Question 1

It's typically good to _decrease_...
1. Latency
2. Throughput
3. Complexity
4. Predictability

<details>
<summary>Click to show the answer</summary>
<p>

1: Latency is the time a user must wait for a request to finish, so decreasing it is a good idea.
One may end up decreasing one of the other three metrics as a tradeoff during design or optimization, but it should not be a primary goal.

</p>
</details>


## Question 2

Before optimizing, one must have...
1. Workloads
2. Objectives
3. Service Level Agreements
4. Benchmarks

<details>
<summary>Click to show the answer</summary>
<p>

1 & 2 & 4: These are all needed to know what is being optimized, but an SLA is not necessary.

</p>
</details>


## Question 3

A profiler can...
1. Measure time taken per function
2. Find functions that take too much time
3. Measure memory consumption
4. Rank lines of code by execution count

<details>
<summary>Click to show the answer</summary>
<p>

1 & 3 & 4: A profiler measures, it cannot know what "too much" is. A function could take 50% of total time and still be reasonable because it is inherently slow,
whereas another function might take 2% of total time and be considered a problem because it should be much faster.

</p>
</details>


## Question 4

Which of the following could help with server load?
1. Streaming
2. Lazy loading
3. Prefetching
4. Caching

<details>
<summary>Click to show the answer</summary>
<p>

1 & 2 & 4: It's very unlikely that prefetching could help, since it involves making more server calls, not fewer. Streaming, lazy loading, and caching are all likely to involve requesting less data.

</p>
</details>


## Question 5

Which of the following could help with throughput?
1. Streaming
2. Lazy loading
3. Prefetching
4. Caching

<details>
<summary>Click to show the answer</summary>
<p>

3 & 4: Trying to guess which data will be needed, either through prefetching guesses or caching past data, can help serve more data. Streaming and lazy loading, on the other hand, involve the overhead of making more requests.

</p>
</details>
