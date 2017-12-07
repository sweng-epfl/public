package ch.epfl.sweng;

import ch.epfl.sweng.nodes.TealNode;

/**
 * Represents a Teal function.
 */
public final class TealFunction {
    /**
     * The function parameter's name, or `null` if it has no parameter.
     */
    public final String parameter;

    /**
     * The function's body.
     */
    public final TealNode body;


    /**
     * Initializes a new TealFunction with the given parameter name and body.
     *
     * @param parameter The parameter name, or `null`.
     * @param body      The body.
     */
    public TealFunction(final String parameter, final TealNode body) {
        if (body == null) {
            throw new IllegalArgumentException("statements");
        }

        this.parameter = parameter;
        this.body = body;
    }
}
