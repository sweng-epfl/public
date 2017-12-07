package ch.epfl.sweng.interpreter;

import ch.epfl.sweng.TealLibrary;

/**
 * Factory that creates Teal interpreters for specific purposes.
 */
public final class TealInterpreterFactory {
    /**
     * Creates a basic interpreter for the given library.
     * The interpreter should simply invoke functions from the library as is.
     *
     * @param library The library.
     * @return The interpreter.
     */
    public static TealInterpreter basicInterpreter(final TealLibrary library) {
        // TODO
        return null;
    }

    /**
     * Creates a cached interpreter for the given library.
     * The interpreter should return cached results from previous invocations if possible.
     *
     * @param library The library.
     * @return The interpreter.
     */
    public static TealInterpreter cachedInterpreter(final TealLibrary library) {
        // TODO
        return null;
    }


    /**
     * Prevents this class from being instantiated.
     */
    private TealInterpreterFactory() {
        // Nothing.
    }
}
