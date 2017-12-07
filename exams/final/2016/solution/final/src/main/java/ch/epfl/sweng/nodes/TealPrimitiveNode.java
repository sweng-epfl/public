package ch.epfl.sweng.nodes;

import java.util.Objects;

/**
 * Represents a primitive in Teal, which is an integer.
 */
public final class TealPrimitiveNode extends TealNode {
    /**
     * The value.
     */
    public final int value;


    /**
     * Initializes a new primitive node with the given value.
     *
     * @param value The value.
     */
    public TealPrimitiveNode(final int value) {
        this.value = value;
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

        final TealPrimitiveNode that = (TealPrimitiveNode) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
