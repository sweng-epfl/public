package ch.epfl.sweng.dp3.ex4;

import org.junit.Test;

public class BinarySearchTreeTest {

  @Test
  public void testFunctionality() {
    BinarySearchTree tree = new BinarySearchTree();
    tree.add(4);
    tree.add(9);
    tree.add(13);
    tree.add(6);
    // TODO: Make BinarySearchTree implement Iterable. Then uncomment the following lines and make
    // sure to pass the tests.
    //        List<Integer> expectedResult = Arrays.asList(4, 6, 9, 13);
    //        List<Integer> actualResult = new LinkedList<>();
    //        for (Integer elem: tree) {
    //            actualResult.add(elem);
    //        }
    //        assertEquals(expectedResult, actualResult);
    //
    //        tree.add(2);
    //        tree.add(8);
    //
    //        expectedResult = Arrays.asList(2, 4, 6, 8, 9, 13);
    //        actualResult = new LinkedList<>();
    //        for (Integer elem: tree) {
    //            actualResult.add(elem);
    //        }
    //        assertEquals(expectedResult, actualResult);
  }
}
