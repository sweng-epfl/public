package ch.epfl.sweng.nodes;

/**
 * Interface for Teal syntax tree visitors.
 *
 * @param <T> The type of the values returned by visits.
 */
public interface TealNodeVisitor<T> {
    /**
     * Visits the given primitive node.
     *
     * @param node The primitive node.
     * @return The returned value.
     */
    T visit(TealPrimitiveNode node);

    /**
     * Visits the given variable node.
     *
     * @param node The variable node.
     * @return The returned value.
     */
    T visit(TealVariableNode node);

    /**
     * Visits the given addition node.
     *
     * @param node The addition node.
     * @return The returned value.
     */
    T visit(TealAdditionNode node);

    /**
     * Visits the given function call node.
     *
     * @param node The function call node.
     * @return The returned value.
     */
    T visit(TealFunctionCallNode node);
}
