import library.BinaryTree;

import java.util.List;

/**
 * An adapter class that adapts a list of integers into a binary tree
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN add new private constructors, methods, attributes, and nested classes to this class.
 * You MUST NOT edit the parameters, return types, and checked exceptions of the existing methods and constructors.
 * You MUST NOT edit the names of the existing methods and constructors.
 * You MUST NOT rename this file.
 * You MUST NOT delete this file.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public final class ListAdapter implements BinaryTree {

    /**
     * Constructs a ListAdapter with the specified list.
     * @param list Representation of a binary tree.
     * @throws IllegalArgumentException when the list is null or empty.
     */
    public ListAdapter(List<Integer> list) {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Integer getRoot() {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public ListAdapter getLeft() {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public ListAdapter getRight() {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
