package ch.epfl.sweng.nodes;

import java.util.Objects;

/**
 * Represents a variable in Teal.
 * As Teal does not have variable declarations, only parameters are variables.
 */
public final class TealVariableNode extends TealNode {
    /**
     * The variable's name.
     */
    public final String name;


    /**
     * Initializes a new variable node with the given name.
     *
     * @param name The name.
     */
    public TealVariableNode(final String name) {
        this.name = Objects.requireNonNull(name);
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

        final TealVariableNode that = (TealVariableNode) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}