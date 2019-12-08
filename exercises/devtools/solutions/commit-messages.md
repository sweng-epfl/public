# Exercise 1.1: Writing Commit Messages


1. You rewrite the entire project README because it is out of date, too concise and didn't show how to build and use the code.

```
Rewrite out of date README

The project has outgrown the current README. It is too short, and does not describe the full breadth of the project. Numerous new primary functions should be prominently advertised. In addition, there is no section detailing how to actually compile and run the project software, or test it. 

This commit rewrites the README as follows:

 - Sections for intro, features, compilation, testing, further resources
 - include screenshots of primary GUIs
 - includes new project logo (see #123)
```

2. You notice that some request data in your system is often already sorted, so you replace the entire `quicksort` implementation with `heapsort` to get more predictable latency.

```
Replace request data quicksort with heapsort

Production traces of request data has shown that in more than 80% of cases, the request data is _already_ sorted. 
Due to this, the current quicksort scheme often degrades to n^2 complexity. 

Replacing the current quicksort with HeapSort guarantees us nlogn complexity, and tests have shown reduced latency variability and average latency improvements of 15%.
```

3. You use the `google-java-format` tool to format a single file to comply with the project's chosen source code style.

```
formatting <file> to comply with project code style
```

4. You fix typos in comments in several files.

```
correcting typos in <file1>, <file2>
```