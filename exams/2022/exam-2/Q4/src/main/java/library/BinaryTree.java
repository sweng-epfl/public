package library;

/**
 * Represents a binary tree of integers.
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public interface BinaryTree {

    /**
     * @return the integer value of the root element of the binary tree.
     */
    Integer getRoot();

    /**
     * @return the left subtree of the root element or null if there is none.
     */
    BinaryTree getLeft();

    /**
     * @return the right subtree of the root element or null if there is none.
     */
    BinaryTree getRight();
}
