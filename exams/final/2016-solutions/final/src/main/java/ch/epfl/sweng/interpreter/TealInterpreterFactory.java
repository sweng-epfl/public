package ch.epfl.sweng.interpreter;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sweng.TealFunction;
import ch.epfl.sweng.TealLibrary;
import ch.epfl.sweng.nodes.TealAdditionNode;
import ch.epfl.sweng.nodes.TealFunctionCallNode;
import ch.epfl.sweng.nodes.TealNode;
import ch.epfl.sweng.nodes.TealNodeVisitor;
import ch.epfl.sweng.nodes.TealPrimitiveNode;
import ch.epfl.sweng.nodes.TealVariableNode;

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
        if (library == null) {
            throw new IllegalArgumentException("library is null");
        }

        return new BasicInterpreter(library);
    }

    /**
     * Creates a cached interpreter for the given library.
     * The interpreter should return cached results from previous invocations if possible.
     *
     * @param library The library.
     * @return The interpreter.
     */
    public static TealInterpreter cachedInterpreter(final TealLibrary library) {
        return new CachedInterpreter(basicInterpreter(library));
    }


    /**
     * Prevents this class from being instantiated.
     */
    private TealInterpreterFactory() {
        // Nothing.
    }

    private static final class BasicInterpreter implements TealInterpreter, TealNodeVisitor<Integer> {
        private final TealLibrary library;
        private String argName;
        private Integer argValue;


        BasicInterpreter(final TealLibrary library) {
            this.library = library;

            argName = null;
            argValue = null;
        }


        @Override
        public int invoke(final String functionName, final Integer argument) {
            if (functionName == null) {
                throw new IllegalArgumentException("functionName is null");
            }

            final TealNode argNode = argument == null ? null : new TealPrimitiveNode(argument);
            return visit(new TealFunctionCallNode(functionName, argNode));
        }


        @Override
        public Integer visit(final TealPrimitiveNode node) {
            return node.value;
        }

        @Override
        public Integer visit(final TealVariableNode node) {
            if (node.name.equals(argName)) {
                return argValue;
            }

            throw new TealInterpretationException("Unknown variable: " + node.name);
        }

        @Override
        public Integer visit(final TealAdditionNode node) {
            return node.left.acceptVisitor(this) + node.right.acceptVisitor(this);
        }

        @Override
        public Integer visit(TealFunctionCallNode node) {
            if (!library.functions.containsKey(node.functionName)) {
                throw new TealInterpretationException("Unknown function name.");
            }

            final TealFunction function = library.functions.get(node.functionName);
            if (function.parameter == null && node.argument != null) {
                throw new TealInterpretationException("Argument given but not expected");
            }
            if (function.parameter != null && node.argument == null) {
                throw new TealInterpretationException("Argument expected but not given");
            }

            final Integer oldValue = argValue;
            final String oldName = argName;

            // Order matters here, the new argument may include the current parameter,
            // thus the name needs to be changed after evaluating the argument.
            argValue = node.argument == null ? null : node.argument.acceptVisitor(this);
            argName = function.parameter;

            final Integer result = function.body.acceptVisitor(this);

            argValue = oldValue;
            argName = oldName;

            return result;
        }
    }

    private static final class CachedInterpreter implements TealInterpreter {
        private final TealInterpreter wrapped;
        private final Map<String, Map<Integer, Integer>> cache;


        CachedInterpreter(final TealInterpreter wrapped) {
            this.wrapped = wrapped;
            cache = new HashMap<>();
        }


        @Override
        public int invoke(final String functionName, final Integer argument) {
            if (!cache.containsKey(functionName)) {
                cache.put(functionName, new HashMap<>());
            }

            if (!cache.get(functionName).containsKey(argument)) {
                final int result = wrapped.invoke(functionName, argument);
                cache.get(functionName).put(argument, result);
            }

            return cache.get(functionName).get(argument);
        }
    }
}
