package ch.epfl.sweng.dp3.solutions.ex3;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntegerBox implements Iterable<Integer> {
  private int[] box;

  public IntegerBox(int... numbers) {
    int amount = numbers.length;
    box = new int[amount];

    for (int i = 0; i < amount; ++i) {
      box[i] = numbers[i];
    }
  }

  public void add(int number) {
    int[] newBox = new int[box.length + 1];

    for (int i = 0; i < box.length; ++i) {
      newBox[i] = box[i];
    }
    newBox[box.length] = number;
    box = newBox;
  }

  @Override
  public Iterator<Integer> iterator() {

    return new Iterator<Integer>() {
      private int currentIndex = 0;
      private int amount = box.length;

      @Override
      public boolean hasNext() {
        return currentIndex < amount;
      }

      @Override
      public Integer next() {
        if (hasNext()) {
          return box[currentIndex++];
        } else {
          throw new NoSuchElementException();
        }
      }
    };
  }
}
