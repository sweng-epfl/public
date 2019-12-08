package ch.epfl.sweng.interpreter;

/**
 * Interprets a Teal function library.
 */
public interface TealInterpreter {
    /**
     * Executes the given function, with the given argument (if needed).
     *
     * @param functionName The function name.
     * @param argument     The argument, iff the function requires one; otherwise, `null`.
     * @return The value returned by an execution of the function.
     * @throws TealInterpretationException if an exception occurs during interpretation.
     */
    int invoke(final String functionName, final Integer argument);
}
