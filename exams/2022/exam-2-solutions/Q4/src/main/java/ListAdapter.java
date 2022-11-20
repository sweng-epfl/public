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

    private final List<Integer> list;
    private final int rootIndex;

    /**
     * Constructs a ListAdapter with the specified list.
     * @param list Representation of a binary tree.
     * @throws IllegalArgumentException when the list is null or empty.
     */
    public ListAdapter(List<Integer> list) {
        this(list, 1);
    }

    private ListAdapter(List<Integer> list, int rootIndex) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.list = list;
        this.rootIndex = rootIndex;
    }

    @Override
    public Integer getRoot() {
        return list.get(rootIndex - 1);
    }

    @Override
    public ListAdapter getLeft() {
        if ((rootIndex * 2) > list.size()) {
            return null;
        }
        return new ListAdapter(list, rootIndex * 2);
    }

    @Override
    public ListAdapter getRight() {
        if ((rootIndex * 2 + 1) > list.size()) {
            return null;
        }
        return new ListAdapter(list, rootIndex * 2 + 1);
    }
}
