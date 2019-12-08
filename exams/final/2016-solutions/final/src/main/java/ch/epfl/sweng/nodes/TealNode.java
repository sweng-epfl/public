package ch.epfl.sweng.nodes;

/**
 * Base class for nodes in a Teal syntax tree.
 */
public abstract class TealNode {
    private int visitCount = 0;

    /**
     * Gets the number of times this node was visited.
     *
     * @return The number of visits.
     */
    public int getVisitCount() {
        return visitCount;
    }


    /**
     * Accepts the specified visitor.
     *
     * @param visitor The visitor.
     * @param <T>     The visitor's return value type.
     * @return The visitor's return value.
     */
    public final <T> T acceptVisitor(final TealNodeVisitor<T> visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException("Visitor cannot be null.");
        }

        visitCount++;

        return accept(visitor);
    }


    /**
     * Accepts the given, non-null visitor.
     *
     * @param visitor The visitor.
     * @param <T>     The visitor's return value type.
     * @return The visitor's return value.
     */
    protected abstract <T> T accept(TealNodeVisitor<T> visitor);
}
