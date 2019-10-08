# Loop Invariants
This exercise will teach you about **code invariants**, in particular loop invariants. To this end, you will be asked to implement a given algorithm. You will need to prove the correctness of your implementation. For that, you will develop a loop invariant utility. You will use **assertions** and the loop invariant utility to verify the correctness of the algorithm implementation.

This exercise illustrates the value of using code invariants. We use code invariants to reason about why an implementation is correct or not.

In particular, we will look at loop invariants. We must show three things about a loop invariant `I`:

- **Initialization**: `I` is true prior to the first iteration of the loop.
- **Maintenance**: If `I` it is true before an iteration of the loop, `I` remains true  throughout the body of the loop until just before the next iteration.
- **Termination**: When the loop terminates (exits), `I` gives us a useful property that helps to show that the algorithm is correct.

In this exercise, you need to implement the **Insertion Sort** algorithm and prove the correctness of your implementation. You will use for that the loop invariant of the algorithm. Here, proving your implementation's correctness means implementing the loop invariant. To this end, you will make use of assertions.

### Insertion Sort algorithm
Here is the Insertion Sort algorithm for a 1-indexed array:
```
INSERTION-SORT(A)
1 for j = 2 to A.length
2   key = A[j]
3   // Insert A[j] into the sorted sequence A[1 .. j - 1]
4   i = j - 1
5   while i > 0 and A[i] > key
6     A[i + 1] = A[i]
7     i = i - 1
8   A[i + 1] = key
```

### Insertion Sort loop invariant
Here is the the proof of correctness of the algorithm:

- **Initialization**: Prior to the first iteration of the loop, j=2 ⇒ A[1.. j-1] = A[1].  Since there is only one element in A[1.. j-1], the array prefix up to j-1 is trivially sorted.
- **Maintenance**: The outer for loop selects element A[j], and the while loop positions it into A[1.. j-1] such that the element that appears before it (i.e., A[i]) is less-than-or-equal, and the element that appears after it is greater. Since the array prefix A[1.. j-1] started out sorted, inserting element A[j] in this way produces a new array prefix A[1.. j] that is sorted.
- **Termination**: The loop terminates when j=n+1 ⇒ A[1.. j-1] = A[1.. ( n+1)-1] = A[1.. n]. This means that the sorted array prefix is the entire array A[1.. n], so the entire array is sorted when the loop terminates. This is the desired property.

### Tasks
You are given a Java project you need to complete:

1. Implement the [sort](src/main/java/ch/epfl/sweng/defensive/loop/invariant/InsertionSort.java#L6) method of [InsertionSort.java](src/main/java/ch/epfl/sweng/defensive/loop/invariant/InsertionSort.java).
1. Implement the [hold](src/main/java/ch/epfl/sweng/defensive/loop/invariant/LoopInvariant.java#L6) method of [LoopInvariant.java](src/main/java/ch/epfl/sweng/defensive/loop/invariant/LoopInvariant.java).
1. Prove the correctness of the InsertionSort's [sort](src/main/java/ch/epfl/sweng/defensive/loop/invariant/InsertionSort.java#L6) method with the LoopInvariant's [hold](src/main/java/ch/epfl/sweng/defensive/loop/invariant/LoopInvariant.java#L6) method of `LoopInvariant.java`. Make use of assertions.
1. Write a test program [Main.java](src/main/java/ch/epfl/sweng/defensive/loop/invariant/Main.java) that checks your implementation.
