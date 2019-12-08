package ch.epfl.sweng.nodes;

import java.util.Objects;

/**
 * Represents a function call in Teal.
 */
public final class TealFunctionCallNode extends TealNode {
    /**
     * The function name.
     */
    public final String functionName;

    /**
     * The argument, or `null` if there is no argument.
     */
    public final TealNode argument;


    /**
     * Initializes a new function call node with the specified name and argument.
     *
     * @param functionName The name.
     * @param argument     The argument, or `null`.
     */
    public TealFunctionCallNode(final String functionName, final TealNode argument) {
        this.functionName = Objects.requireNonNull(functionName);
        this.argument = argument;
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

        final TealFunctionCallNode that = (TealFunctionCallNode) o;
        return Objects.equals(functionName, that.functionName) && Objects.equals(argument, that.argument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(functionName, argument);
    }

    @Override
    public String toString() {
        if (argument == null) {
            return "!" + functionName + "()";
        }

        return "!" + functionName + "(" + argument.toString() + ")";
    }
}
