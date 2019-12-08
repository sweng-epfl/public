package ch.epfl.sweng.tests.parser;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import ch.epfl.sweng.TealFunction;
import ch.epfl.sweng.TealLibrary;
import ch.epfl.sweng.nodes.TealAdditionNode;
import ch.epfl.sweng.nodes.TealFunctionCallNode;
import ch.epfl.sweng.nodes.TealNode;
import ch.epfl.sweng.nodes.TealPrimitiveNode;
import ch.epfl.sweng.nodes.TealVariableNode;
import ch.epfl.sweng.parser.TealParseException;
import ch.epfl.sweng.parser.TealParser;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for the Teal parser.
 */
public final class TealParserTests {
    /**
     * NOTE: DO NOT REMOVE THIS TEST!
     *
     * This test is needed so that you can obtain 100% line coverage (JaCoCo bug).
     */
    @Test
    public void callPrivateConstructors() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor<TealParser> c = TealParser.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
    }

    public static final class IllegalArguments {
        @Test(expected = IllegalArgumentException.class)
        public void nullCode() throws TealParseException {
            TealParser.parse(null);
        }
    }

    public static final class NoFunctions {
        @Test
        public void noFunctions() throws TealParseException {
            final TealLibrary library = TealParser.parse("");

            assertThat(library.functions.entrySet(), is(empty()));
        }
    }

    public static final class OneFunction {
        @Test
        public void constantFunction() throws TealParseException {
            final TealLibrary library = TealParser.parse("f(): 42");

            assertThat(library.functions.entrySet(), hasSize(1));
            assertThat(library, hasFunction("f", null, new TealPrimitiveNode(42)));
        }

        @Test
        public void constantFunctionWithParameter() throws TealParseException {
            final TealLibrary library = TealParser.parse("f(n): 42");

            assertThat(library.functions.entrySet(), hasSize(1));
            assertThat(library, hasFunction("f", "n", new TealPrimitiveNode(42)));
        }

        @Test
        public void functionWithParameterNotNamedN() throws TealParseException {
            final TealLibrary library = TealParser.parse("f(x): 42");

            assertThat(library.functions.entrySet(), hasSize(1));
            assertThat(library, hasFunction("f", "x", new TealPrimitiveNode(42)));
        }

        @Test
        public void identityFunctionPrivate() throws TealParseException {
            final TealLibrary library = TealParser.parse("f(n): n");

            assertThat(library.functions.entrySet(), hasSize(1));
            assertThat(library, hasFunction("f", "n", new TealVariableNode("n")));
        }

        @Test
        public void functionWithLongParamName() throws TealParseException {
            final TealLibrary library = TealParser.parse("f(abcdefghi): abcdefghi");

            assertThat(library.functions.entrySet(), hasSize(1));
            assertThat(library, hasFunction("f", "abcdefghi", new TealVariableNode("abcdefghi")));
        }

        @Test
        public void functionWithAddition() throws TealParseException {
            final TealLibrary library = TealParser.parse("f(n): 1 + n");

            assertThat(library.functions.entrySet(), hasSize(1));
            assertThat(library, hasFunction("f", "n",
                    new TealAdditionNode(new TealPrimitiveNode(1), new TealVariableNode("n"))
            ));
        }

        @Test
        public void functionWithParameterlessCall() throws TealParseException {
            final TealLibrary library = TealParser.parse("f(): !g()");

            assertThat(library.functions.entrySet(), hasSize(1));
            assertThat(library, hasFunction("f", null,
                    new TealFunctionCallNode("g", null)
            ));
        }

        @Test
        public void functionWithCall() throws TealParseException {
            final TealLibrary library = TealParser.parse("f(): !g(42)");

            assertThat(library.functions.entrySet(), hasSize(1));
            assertThat(library, hasFunction("f", null,
                    new TealFunctionCallNode("g", new TealPrimitiveNode(42))
            ));
        }

        @Test
        public void complexFunction() throws TealParseException {
            final TealLibrary library = TealParser.parse("f(n): 1 + !g(3 + n) + !h(!i(3) + !j())");

            assertThat(library.functions.entrySet(), hasSize(1));
            assertThat(library, hasFunction("f", "n",
                    new TealAdditionNode(
                            new TealPrimitiveNode(1),
                            new TealAdditionNode(
                                    new TealFunctionCallNode("g",
                                            new TealAdditionNode(new TealPrimitiveNode(3), new TealVariableNode("n"))
                                    ),
                                    new TealFunctionCallNode("h",
                                            new TealAdditionNode(
                                                    new TealFunctionCallNode("i", new TealPrimitiveNode(3)),
                                                    new TealFunctionCallNode("j", null)
                                            )
                                    )
                            )
                    )
            ));
        }
    }

    public static final class MultipleFunctions {
        @Test
        public void fourFunctions() throws TealParseException {
            final TealLibrary library = TealParser.parse("f(): 1 \r g(): 2 \n h(): 3 \r\n i(): 4");

            assertThat(library.functions.entrySet(), hasSize(4));
            assertThat(library, hasFunction("f", null, new TealPrimitiveNode(1)));
            assertThat(library, hasFunction("g", null, new TealPrimitiveNode(2)));
            assertThat(library, hasFunction("h", null, new TealPrimitiveNode(3)));
            assertThat(library, hasFunction("i", null, new TealPrimitiveNode(4)));
        }
    }

    public static final class ExamplesFromStatement {
        @Test
        public void example1() throws TealParseException {
            final TealLibrary library = TealParser.parse("f(n): n + 1");

            assertThat(library.functions.entrySet(), hasSize(1));
            assertThat(library, hasFunction("f", "n",
                    new TealAdditionNode(new TealVariableNode("n"), new TealPrimitiveNode(1))
            ));
        }

        @Test
        public void example2() throws TealParseException {
            final TealLibrary library = TealParser.parse("g(n): !f(2 + n) + 1");

            assertThat(library.functions.entrySet(), hasSize(1));
            assertThat(library, hasFunction("g", "n",
                    new TealAdditionNode(
                            new TealFunctionCallNode("f",
                                    new TealAdditionNode(new TealPrimitiveNode(2), new TealVariableNode("n"))
                            ),
                            new TealPrimitiveNode(1)
                    )
            ));
        }

        @Test
        public void example3() throws TealParseException {
            final TealLibrary library = TealParser.parse(
                    "f(n): n \n answer(): 42 \n complexAnswer(n): !answer() + !f(!answer() + 2)"
            );

            assertThat(library.functions.entrySet(), hasSize(3));
            assertThat(library, hasFunction("f", "n", new TealVariableNode("n")));
            assertThat(library, hasFunction("answer", null, new TealPrimitiveNode(42)));
            assertThat(library, hasFunction("complexAnswer", "n",
                    new TealAdditionNode(
                            new TealFunctionCallNode("answer", null),
                            new TealFunctionCallNode("f",
                                    new TealAdditionNode(
                                            new TealFunctionCallNode("answer", null),
                                            new TealPrimitiveNode(2)
                                    )
                            )
                    )
            ));
        }
    }

    public static final class Errors {
        @Test(expected = TealParseException.class)
        public void integerOverflow() throws TealParseException {
            TealParser.parse("f(): 12345678901234567890");
        }

        @Test(expected = TealParseException.class)
        public void onlyFunctionName() throws TealParseException {
            TealParser.parse("f");
        }

        @Test(expected = TealParseException.class)
        public void onlyFunctionNameAndParen() throws TealParseException {
            TealParser.parse("f()");
        }

        @Test(expected = TealParseException.class)
        public void functionWithoutBody() throws TealParseException {
            TealParser.parse("f():");
        }

        @Test(expected = TealParseException.class)
        public void functionWithTwoParams1() throws TealParseException {
            TealParser.parse("f(a b): 42");
        }

        @Test(expected = TealParseException.class)
        public void functionWithTwoParams2() throws TealParseException {
            TealParser.parse("f(a + b): 42");
        }

        @Test(expected = TealParseException.class)
        public void wrongFunctionDelim() throws TealParseException {
            TealParser.parse("f() ! 1");
        }

        @Test(expected = TealParseException.class)
        public void subtraction() throws TealParseException {
            TealParser.parse("f(): 1 - 1");
        }

        @Test(expected = TealParseException.class)
        public void multiplication() throws TealParseException {
            TealParser.parse("f(): 1 * 1");
        }

        @Test(expected = TealParseException.class)
        public void randomSymbol() throws TealParseException {
            TealParser.parse("f(): §");
        }

        @Test(expected = TealParseException.class)
        public void consecutivePluses() throws TealParseException {
            TealParser.parse("f(): 1 + + 1");
        }

        @Test(expected = TealParseException.class)
        public void trailingPlus() throws TealParseException {
            TealParser.parse("f(): 1 + 1 +");
        }

        @Test(expected = TealParseException.class)
        public void trailingNumber() throws TealParseException {
            TealParser.parse("f(): 1 + 1 2");
        }

        @Test(expected = TealParseException.class)
        public void negativeNumber() throws TealParseException {
            TealParser.parse("f(): -2");
        }

        @Test(expected = TealParseException.class)
        public void functionCallWithoutBang() throws TealParseException {
            TealParser.parse("f(): g()");
        }

        @Test(expected = TealParseException.class)
        public void functionCallOfNumber() throws TealParseException {
            TealParser.parse("f(): !1()");
        }

        @Test(expected = TealParseException.class)
        public void functionCallOfPlus() throws TealParseException {
            TealParser.parse("f(): !+()");
        }

        @Test(expected = TealParseException.class)
        public void functionCallWithoutOpeningParen() throws TealParseException {
            TealParser.parse("f(): !g) + 1");
        }

        @Test(expected = TealParseException.class)
        public void functionCallWithoutClosingParen() throws TealParseException {
            TealParser.parse("f(): !g(1");
        }

        @Test(expected = TealParseException.class)
        public void multipleFunctionsWithSameName() throws TealParseException {
            TealParser.parse("f(): 1 \n g(): 2 \n f(): 3");
        }
    }

    @Test
    public void identityFunction() throws TealParseException {
        final TealLibrary library = TealParser.parse("f(n): n");

        assertThat(library.functions.entrySet(), hasSize(1));
        assertThat(library, hasFunction("f", "n", new TealVariableNode("n")));
    }

    /**
     * Matches one function from a Teal library.
     *
     * @param functionName  The function's name.
     * @param parameterName The name of the function's parameter, or `null` if there is no parameter.
     * @param body          The function's body.
     * @return A matcher with the given values.
     */
    private static Matcher<TealLibrary> hasFunction(final String functionName,
                                                    final String parameterName,
                                                    final TealNode body) {
        return new FunctionMatcher(functionName, parameterName, body);
    }

    /**
     * Matcher for a function from a Teal library.
     * (You do not need to understand, or even read, this code!)
     */
    private static final class FunctionMatcher extends TypeSafeDiagnosingMatcher<TealLibrary> {
        private final String functionName;
        private final String parameterName;
        private final TealNode body;

        FunctionMatcher(final String functionName, final String parameterName, final TealNode body) {
            this.functionName = functionName;
            this.parameterName = parameterName;
            this.body = body;
        }

        @Override
        protected boolean matchesSafely(final TealLibrary item, final Description mismatchDescription) {
            if (!item.functions.containsKey(functionName)) {
                mismatchDescription.appendText("No function with that name exists in the library.");
            }

            final TealFunction function = item.functions.get(functionName);
            boolean result = true;
            if (parameterName == null && function.parameter != null) {
                mismatchDescription.appendText("The function had a parameter named '" + function.parameter + "'.");
                mismatchDescription.appendText(System.lineSeparator());
                result = false;
            }
            if (parameterName != null && function.parameter == null) {
                mismatchDescription.appendText("The function had no parameter.");
                mismatchDescription.appendText(System.lineSeparator());
                result = false;
            }
            if (parameterName != null && !parameterName.equals(function.parameter)) {
                mismatchDescription.appendText("The function's parameter was named '" + function.parameter + "'.");
                mismatchDescription.appendText(System.lineSeparator());
                result = false;
            }

            if (body.equals(function.body)) {
                return result;
            }

            mismatchDescription.appendText("The body was " + function.body);
            return false;
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("A function with name '" + functionName + "', ");

            if (parameterName == null) {
                description.appendText("no parameter, ");
            } else {
                description.appendText("parameter '" + parameterName + "', ");
            }

            description.appendText("and body " + body.toString());
        }
    }
}
