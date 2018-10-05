package ch.epfl.sweng.defensive.loop.invariant;

import java.util.Comparator;

public class InsertionSort {
  public static <C> void sort(C[] array, Comparator<C> comparator) {
    int j = 1;
    assert LoopInvariant.hold(array, comparator, j) : "initialization property does not hold";
    while (j < array.length) {
      C key = array[j];
      int i = j - 1;
      while (i > -1 && comparator.compare(array[i], key) > 0) {
        array[i + 1] = array[i];
        i = i - 1;
      }
      array[i + 1] = key;
      assert LoopInvariant.hold(array, comparator, j) : "maintenance property does not hold";
      ++j;
    }
    assert LoopInvariant.hold(array, comparator, j) : "termination property does not hold";
  }
}
