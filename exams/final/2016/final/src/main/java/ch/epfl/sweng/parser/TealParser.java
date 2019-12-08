package ch.epfl.sweng.parser;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sweng.TealFunction;
import ch.epfl.sweng.TealLibrary;
import ch.epfl.sweng.nodes.TealNode;
import ch.epfl.sweng.nodes.TealAdditionNode;
import ch.epfl.sweng.nodes.TealFunctionCallNode;
import ch.epfl.sweng.nodes.TealPrimitiveNode;
import ch.epfl.sweng.nodes.TealVariableNode;

/**
 * Parser for the Toy Exam Abstract Language (Teal).
 * <p>
 * Specification:
 * <p>
 * Each line is a function declaration, containing:
 * - The function's name
 * - Zero or one parameters, between parentheses
 * - A colon (`:`)
 * - The function's body, which is an expression
 * <p>
 * Expressions can be
 * - An integer
 * - A variable, referring to the current function's parameter
 * - An addition, which is two expressions separated by a plus sign (`+`)
 * - A function call, beginning with an exclamation mark (`!`),
 * then the function name, then the argument (if any) between parentheses
 * <p>
 * Examples:
 * f(n): n + 1
 * g(n): !f(2 + n) + 1
 * answer(): 42
 * answer_plus_n(n): n + !answer()
 */
public final class TealParser {
    /**
     * Prevents actual instantiation of this class.
     */
    private TealParser() { }

    /**
     * Parses the given Teal code into a syntax tree.
     *
     * @param code The code.
     * @return The syntax tree corresponding to the code.
     * @throws TealParseException if a parse error occurs.
     */
    public static TealLibrary parse(final String code) throws TealParseException {
        if (code == null) {
            throw new IllegalArgumentException("Null code.");
        }

        final String[] lines = code.trim().split("\\r\\n|\\r|\\n");
        final Map<String, TealFunction> functions = new HashMap<>();

        // If the text contains 0 functions, need to behave properly
        if (lines.length == 1 && lines[0].equals("")) {
            return new TealLibrary(functions);
        }

        for (int n = 0; n < lines.length; n++) {
            final TealTokenizer line = new TealTokenizer(lines[n], n);

            final String functionName = line.next();
            if (functions.containsKey(functionName)) {
                throw line.makeError("Function name already exists: '" + functionName + "'");
            }

            line.expect("(");

            String parameterName = null;
            final String paramOrEnd = line.next();
            if (!paramOrEnd.equals(")")) {
                parameterName = paramOrEnd;
                line.expect(")");
            }

            line.expect(":");

            functions.put(functionName, new TealFunction(parameterName, parseNode(line)));

            // There might be some leftover input
            if (line.hasNext()) {
                throw line.makeError("Text remaining, expected end-of-line.");
            }
        }

        return new TealLibrary(functions);
    }


    /**
     * Parses a node from the given code, as a sequence of tokens.
     *
     * @param code The tokens.
     * @return The parsed node.
     * @throws TealParseException if a parse error occurs.
     */
    private static TealNode parseNode(final TealTokenizer code) throws TealParseException {
        TealNode node = parseSingleNode(code);

        if (code.hasNext() && "+".equals(code.peek())) {
            code.next();
            node = new TealAdditionNode(node, parseNode(code));
        }

        return node;
    }

    /**
     * Parses a single node from the given code, as a sequence of tokens.
     *
     * @param code The tokens.
     * @return The parsed node.
     * @throws TealParseException if a parse error occurs.
     */
    private static TealNode parseSingleNode(final TealTokenizer code) throws TealParseException {
        final String token = code.next();

        // Either it's an int...
        final Integer tokenAsInt = tryParseInt(token);
        if (tokenAsInt != null) {
            return new TealPrimitiveNode(tokenAsInt);
        }

        // ...or a method call...
        if (token.equals("!")) {
            final String functionName = code.next();
            if (!Character.isLetter(functionName.charAt(0))) {
                throw code.makeError("Can't call numbers or symbols.");
            }

            code.expect("(");
            if (")".equals(code.peek())) {
                code.next();
                return new TealFunctionCallNode(functionName, null);
            }

            final TealNode argument = parseNode(code);
            code.expect(")");

            return new TealFunctionCallNode(functionName, argument);
        }

        // Don't accept symbols as variables. (Numbers are taken care of above)
        if (!Character.isLetter(token.charAt(0))) {
            throw code.makeError("Unexpected symbol: " + token.charAt(0));
        }

        // ...or a variable.
        return new TealVariableNode(token);
    }


    /**
     * Parses an integer from the given string, or returns `null` if the string does not represent an integer.
     * (This method is guaranteed to be bug-free!)
     *
     * @param s The string.
     * @return The integer, if the string represented one, or null.
     */
    private static Integer tryParseInt(final String s) {
        // According to the spec, numbers are all digits (no leading '+' or '-' allowed).
        if (!Character.isDigit(s.charAt(0))) return null;
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException ignored) {
            return null;
        }
    }

    /**
     * Represents a sequence of tokens in Teal.
     * (This class is guaranteed to be bug-free!)
     */
    private static final class TealTokenizer {
        private final String line;
        private final int lineNumber;
        private int col;


        /**
         * Initializes a new tokenizer from the given line, with its line number (for error messages).
         *
         * @param line       The line.
         * @param lineNumber The line number.
         */
        TealTokenizer(final String line, final int lineNumber) {
            this.line = line;
            this.lineNumber = lineNumber;
            col = 0;
        }


        /**
         * Peeks at the next token, without advancing the tokenizer.
         *
         * @return The next token.
         * @throws TealParseException if there are no tokens left.
         */
        String peek() throws TealParseException {
            if (!hasNext()) {
                throw makeError("No input left to consume");
            }

            // Either it's a sequence of letters, or of digits, or just one symbol
            final char currentChar = line.charAt(col);
            if (Character.isLetter(currentChar)) {
                int end = col;
                while (end < line.length() && Character.isLetter(line.charAt(end))) {
                    end++;
                }

                return line.substring(col, end);
            }

            if (Character.isDigit(currentChar)) {
                int end = col;
                while (end < line.length() && Character.isDigit(line.charAt(end))) {
                    end++;
                }

                return line.substring(col, end);
            }

            // Teal has a limited number of possible tokens
            if (currentChar == '(' || currentChar == ')'
                    || currentChar == ':'
                    || currentChar == '!'
                    || currentChar == '+') {
                return Character.toString(currentChar);
            }

            throw makeError("Invalid token: '" + currentChar + "'");
        }

        /**
         * Gets the next token (and advances the tokenizer).
         *
         * @return The token.
         * @throws TealParseException if there are no tokens left.
         */
        String next() throws TealParseException {
            final String token = peek();
            col += token.length();
            return token;
        }


        /**
         * Consumes the next token, ensuring that it is the given value.
         *
         * @param token The expected value.
         * @throws TealParseException if there are no more tokens, or if the next token is unexpected.
         */
        void expect(final String token) throws TealParseException {
            if (!token.equals(next())) {
                throw makeError("Expected '" + token + "'");
            }
        }

        /**
         * Checks whether there are any more tokens.
         *
         * @return A value indicating whether there are any tokens left.
         */
        boolean hasNext() {
            skipWhitespace();

            return col != line.length();
        }

        /**
         * Creates an exception with the given text, annotated with the current line and column numbers.
         *
         * @param text The error text.
         * @return The exception.
         */
        TealParseException makeError(final String text) {
            return new TealParseException(text + ", line = " + lineNumber + ", column = " + col);
        }


        /**
         * Skips all whitespace until the beginning of the next token.
         */
        private void skipWhitespace() {
            while (col < line.length() && Character.isWhitespace(line.charAt(col))) {
                col++;
            }
        }
    }
}
