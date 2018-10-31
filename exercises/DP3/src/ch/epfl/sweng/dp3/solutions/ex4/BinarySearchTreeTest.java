package ch.epfl.sweng.dp3.solutions.ex4;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;

public class BinarySearchTreeTest {

  @Test
  public void testFunctionality() {
    BinarySearchTree tree = new BinarySearchTree();
    tree.add(4);
    tree.add(9);
    tree.add(13);
    tree.add(6);

    List<Integer> expectedResult = Arrays.asList(4, 6, 9, 13);
    List<Integer> actualResult = new LinkedList<>();
    for (Integer elem : tree) {
      actualResult.add(elem);
    }
    assertEquals(expectedResult, actualResult);

    tree.add(2);
    tree.add(8);

    expectedResult = Arrays.asList(2, 4, 6, 8, 9, 13);
    actualResult = new LinkedList<>();
    for (Integer elem : tree) {
      actualResult.add(elem);
    }
    assertEquals(expectedResult, actualResult);
  }
}
