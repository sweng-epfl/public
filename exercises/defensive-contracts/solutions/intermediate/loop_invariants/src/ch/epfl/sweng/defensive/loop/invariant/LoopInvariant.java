package ch.epfl.sweng.defensive.loop.invariant;

import java.util.Comparator;

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
