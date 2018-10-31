package ch.epfl.sweng.dp3.solutions.ex4;

import java.util.Iterator;
import java.util.Stack;

public class BinarySearchTree implements Iterable<Integer> {

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

  private class BinarySearchTreeIterator implements Iterator<Integer> {

    private Node current;
    Stack<Node> stack;

    public BinarySearchTreeIterator(Node root) {
      current = root;
      stack = new Stack<>();
    }

    @Override
    public boolean hasNext() {
      return (!stack.isEmpty() || current != null);
    }

    @Override
    public Integer next() {
      while (hasNext()) {
        if (current != null) {
          stack.push(current);
          current = current.left;
        } else {
          current = stack.pop();
          int tmp = current.value;
          current = current.right;
          return tmp;
        }
      }

      return -1;
    }
  }

  @Override
  public Iterator<Integer> iterator() {
    return new BinarySearchTreeIterator(dummyNode.left);
  }
}
