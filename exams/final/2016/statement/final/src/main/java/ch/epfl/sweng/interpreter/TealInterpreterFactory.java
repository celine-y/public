package ch.epfl.sweng.interpreter;

import ch.epfl.sweng.TealFunction;
import ch.epfl.sweng.TealLibrary;
import ch.epfl.sweng.nodes.*;

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

//        TODO: return basic interpreter
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
        // TODO
        return null;
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

        public BasicInterpreter(TealLibrary library) {
            this.library = library;

            argName = null;
            argValue = null;
        }


        @Override
        public int invoke(String functionName, Integer argument) {
            if (functionName == null || functionName == ""){
                throw new IllegalArgumentException("functionName is empty");
            }

            TealNode tealNode;

            if (argument != null) {
                tealNode = new TealPrimitiveNode(argument);
            } else {
                tealNode = null;
            }

            TealFunctionCallNode functionCallNode = new TealFunctionCallNode(functionName, tealNode);

            return visit(functionCallNode);

        }

        @Override
        public Integer visit(TealPrimitiveNode node) {
            return node.value;
        }

        @Override
        public Integer visit(TealVariableNode node) {
            if (node.name.equals(argName)) {
                return argValue;
            }

            throw new TealInterpretationException("Unknown variable: "+argName);
        }

        @Override
        public Integer visit(TealAdditionNode node) {
            return node.left.acceptVisitor(this) + node.right.acceptVisitor(this);
        }

        @Override
        public Integer visit(TealFunctionCallNode node) {
            if (!library.functions.containsKey(node.functionName)){
                throw new TealInterpretationException("Unknown  function: "+node.functionName);
            }

            final TealFunction tealFunction = library.functions.get(node.functionName);

            if (tealFunction.parameter == null && node.argument != null) {
                throw new TealInterpretationException("Argument given, but not needed");
            }

            if (tealFunction.parameter != null && node.argument == null) {
                throw new TealInterpretationException("Argument required, but not given");
            }


            final Integer oldValue = argValue;
            final String oldName = argName;

            // Order matters here, the new argument may include the current parameter,
            // thus the name needs to be changed after evaluating the argument.
            argValue = node.argument == null ? null : node.argument.acceptVisitor(this);
            argName = tealFunction.parameter;

            final Integer result = tealFunction.body.acceptVisitor(this);

            argValue = oldValue;
            argName = oldName;

            return result;

        }
    }

}