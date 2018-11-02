package ch.epfl.sweng.dp3.ex3;

public class IntegerBox {
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

  public int[] getIntegers() {
    return box;
  }
}
