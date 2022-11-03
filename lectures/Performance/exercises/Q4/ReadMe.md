# Benchmarking with JMH

The [Java Microbenchmark Harness](https://github.com/openjdk/jmh) is a set of
tools that help benchmark your code. It takes into account the warm-up of the
JVM and code optimizations that might dilute the results. In this exercise, you
will optimize a small program and use JMH to benchmark it and observe the
improvements.

## Setup

We provide you with a small working program that reads values from a CSV file
containing student records and parses them to
a [`Student`](src/main/java/example/Student.java) model. You can see an
example in [`App.java`](src/main/java/App.java).

We also provide you with a skeleton `Benchmark.java` file: it is the file in
which you will write your benchmark. Every method annotated with `@Benchmark`
will be run under the settings specified in the class' annotations:

- The benchmark mode determines what will be measured: the average execution
  time; the throughput, i.e. the number of operations per second; a single
  execution time (without warmup); or all combined. We will use average
  execution time.
- The output time unit determines the unit in which to display the output.
- The warmup annotation specifies how many iterations of warmup to run and for
  how long. As the name suggests, this is used to "warm up" the jvm, and the
  measurements of these iterations are not taken into account.
- The measurement annotation specifies the number of iterations to run and their
  duration. Their result will be taken into account during the benchmark.
- The number of forks is the number of trials to run.

Notice that the only benchmark present takes a `Blackhole` as an argument. This
structure provides a `consume` method to use values that would be otherwise
considered as *dead code*.
This ensures the Java compiler and the JVM do not remove entire parts of your
code as part of dead code elimination, a common optimization that we must avoid
here.

Build and run the benchmark using `gradlew.bat jmh` on Windows or `./gradlew jmh` on macOS and Linux.

You will see the progress of the benchmark. When it's done, it will present the
results.

## A baseline benchmark

Your first task is to write a baseline benchmark for the reader's read method.
Create a reader and use it to read the first 10 lines of the file: repeat that
operation a few times. Remember to use a `Blackhole` to consume your results and
avoid dead code! Re-build the benchmarks and run. A single benchmark is no use
if there's nothing to compare it with.

## Caching the reads

Notice that we also provide you with a `Cache` interface. Make use of it when
reading values: the first value of a line is a unique identifier that you can
use as a key. Assume that the cache can store infinitely many Student records (
and nothing else). When reading CSV values, access the cache only if all the
results are in it. If the (one of) values are not present in the cache: read the
file, retrieve the required values and cache them.

### Benchmarking the cache

Update your old benchmark and write a new one to showcase the effect of caching
reads: run multiple sequential reads in each benchmark. Can you notice the
difference?

> To compare the performance improvement, you may choose to implement a cache
> that does not cache anything, and use it as your baseline. This will allow you
> to keep the same code structure and compare the performance of the two
> implementations.

## Processing the reads: sequential vs. parallel

Imagine having to read all the file and collect all the student's emails. This
can efficiently be done using stream: see an example in `App.java`. Write two
benchmarks showcasing the effect of using a stream vs. a parallel version of it.

Note that parallel streams create some overhead for work-splitting and merging
results. To counter that, we suggest you read the file multiple times in order
to have a larger list of students.

## Storing the reads: Set vs. List

Let's measure the performance difference when storing the read records in
different data structures on which a `contains` check is done repeatedly.

## LinkedList vs. Iterator

This time, read the file into a `LinkedList<Student>`. Compare the performance
of iterating through the linked list using a for loop by using `get(i)` to
consume a Student (make sure to hardcode the list size to avoid recomputing it
at each iteration) vs using the foreach syntactic sugar of its Iterator (i.e.
using `for (final Student s : students)` and consuming `s` directly). Does the
result surprise you?

## ArrayList vs. Iterator

Same as above, but use an `ArrayList<Student>` instead. The result may surprise
you: can you think of what causes the difference?
