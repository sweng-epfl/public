package ch.epfl.sweng.dp3.solutions.ex3;

public class Main {

  public static void main(String[] args) {
    IntegerBox box = new IntegerBox(9, 87, 13, 34, 51);
    for (Integer i : box) {
      System.out.print(i + " ");
    }
    System.out.println();

    box.add(0);
    box.add(123);
    box.add(99);
    for (Integer i : box) {
      System.out.print(i + " ");
    }
    System.out.println();
  }
}
