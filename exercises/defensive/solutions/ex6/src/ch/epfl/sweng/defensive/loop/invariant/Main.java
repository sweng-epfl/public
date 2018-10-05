package ch.epfl.sweng.defensive.loop.invariant;

public class Main {
  public static void main(String... args) {
    Integer[] numbers = {4, 5, 1, 3, 2};
    printNumbers(numbers);
    InsertionSort.sort(numbers, (Integer a, Integer b) -> a - b);
    printNumbers(numbers);
  }

  public static void printNumbers(Integer[] array) {
    for (int i = 0; i < array.length; ++i) {
      System.out.print(array[i] + " ");
    }
    System.out.println();
  }
}
