package ch.epfl.sweng;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Teal program.
 */
public final class TealLibrary {
    /**
     * The functions in the program, indexed by name.
     */
    public final Map<String, TealFunction> functions;


    /**
     * Initializes a new program with the given functions.
     *
     * @param functions The functions.
     */
    public TealLibrary(final Map<String, TealFunction> functions) {
        if (functions == null) {
            throw new IllegalArgumentException("functions");
        }

        // Make a defensive copy to ensure the program's function map is immutable
        final Map<String, TealFunction> functionsCopy = new HashMap<>();
        functionsCopy.putAll(functions);
        this.functions = Collections.unmodifiableMap(functionsCopy);
    }
}