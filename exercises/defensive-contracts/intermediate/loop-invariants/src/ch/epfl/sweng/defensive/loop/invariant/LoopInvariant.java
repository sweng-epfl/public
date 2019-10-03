package ch.epfl.sweng.defensive.loop.invariant;

import java.util.Comparator;

public class LoopInvariant {
  public static <C> boolean hold(C[] array, Comparator<C> comparator, int iteration) {
    return false;
  }
}
