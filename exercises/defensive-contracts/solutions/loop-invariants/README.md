# Solution - Loop Invariants

### Implementing the insertion sort

Given the fact that the insertion sort algorithm is given in the exercise statement, the implementation is straighforward:

```java
public class InsertionSort {
  public static <C> void sort(C[] array, Comparator<C> comparator) {
    int j = 1;
    while (j < array.length) {
      C key = array[j];
      int i = j - 1;
      while (i > -1 && comparator.compare(array[i], key) > 0) {
        array[i + 1] = array[i];
        i = i - 1;
      }
      array[i + 1] = key;
      ++j;
    }
  }
}
```

### Implementing the loop invariant

Implementing the loop invariant is done in two steps. First, we complete the `hold` method of the `LoopInvariant` class. This method basically tests whether A[0 .. i-1] is sorted or not. This is achieved with a regular for loop that checks that two successive elements of A are sorted:

```java
public class LoopInvariant {
  public static <C> boolean hold(C[] array, Comparator<C> comparator, int iteration) {
    assert iteration <= array.length : "iteration > array.length";
    for (int i = 0; i < iteration - 1; ++i) {
      if (comparator.compare(array[i], array[i + 1]) > 0) {
        return false;
      }
    }
    return true;
  }
}
```

We arbitrarily chose the increasing order.

Last but not least, we use the above utility to verify the loop invariant properties. This is a straighforward application of the definition provided in the exercise statement. First, we verify the **initialization** property before the first iteration, then the **maintenance** property before the next iteration, and finally the **termination** property when the loop terminates. Those verifications are done with `assert` statements.

```java
public class InsertionSort {
  public static <C> void sort(C[] array, Comparator<C> comparator) {
    int j = 1;
    assert LoopInvariant.hold(array, comparator, j) : "initialization property does not hold";
    while (j < array.length) {
      // ...
      assert LoopInvariant.hold(array, comparator, j) : "maintenance property does not hold";
      ++j;
    }
    assert LoopInvariant.hold(array, comparator, j) : "termination property does not hold";
  }
}
```

We add those verifications directly in the implementation of the insertion sort algorithm.
