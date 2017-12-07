package ch.epfl.sweng.nodes;

import java.util.Objects;

/**
 * Represents an addition in Teal.
 */
public final class TealAdditionNode extends TealNode {
    /**
     * The left hand side of the addition.
     */
    public final TealNode left;

    /**
     * The right hand side of the addition.
     */
    public final TealNode right;


    /**
     * Initializes a new addition node with the given nodes.
     *
     * @param left  The left node.
     * @param right The right node.
     */
    public TealAdditionNode(final TealNode left, final TealNode right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
    }


    @Override
    public <T> T accept(final TealNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TealAdditionNode that = (TealAdditionNode) o;
        return Objects.equals(left, that.left) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return left.toString() + " + " + right.toString();
    }
}