package ch.epfl.sweng.dp3.ex4;

public class BinarySearchTree {

  private class Node {
    private Node left, right;
    private int value;

    public Node(int value) {
      this.value = value;
    }
  }

  private Node dummyNode;

  public BinarySearchTree() {
    dummyNode = new Node(Integer.MAX_VALUE);
  }

  public void add(int value) {
    dummyNode = add(dummyNode, value);
  }

  private Node add(Node root, int value) {
    if (root == null) {
      return new Node(value);
    }

    if (value < root.value) {
      root.left = add(root.left, value);
    } else {
      root.right = add(root.right, value);
    }
    return root;
  }
}
