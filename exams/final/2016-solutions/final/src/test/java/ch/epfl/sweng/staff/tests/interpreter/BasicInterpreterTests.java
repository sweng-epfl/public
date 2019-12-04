package ch.epfl.sweng.staff.tests.interpreter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ch.epfl.sweng.grading.GradedCategory;
import ch.epfl.sweng.grading.GradedTest;
import ch.epfl.sweng.grading.JUnitGradeSheetTestRunner;
import ch.epfl.sweng.grading.models.GradingPoints;
import ch.epfl.sweng.interpreter.TealInterpretationException;
import ch.epfl.sweng.interpreter.TealInterpreter;
import ch.epfl.sweng.interpreter.TealInterpreterFactory;
import ch.epfl.sweng.parser.TealParseException;
import ch.epfl.sweng.parser.TealParser;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests for the basic interpreter.
 */

@RunWith(JUnitGradeSheetTestRunner.class)
@GradedCategory(name = "Provided Examples",
group = "`BasicInterpreter`",
pointsFormat = GradingPoints.PointsFormat.PARSABLE)
public final class BasicInterpreterTests {
    @Test
    @GradedTest(
            name = "`answer(): 42`",
            points = 0
    )
    public void shortExample1() {
        final String code = "answer(): 42";

        final TealInterpreter interpreter = makeInterpreter(code);

        assertThat(interpreter.invoke("answer", null), is(42));
    }

    @Test
    @GradedTest(
            name = "`f(n): n + 1`",
            points = 0
    )
    public void shortExample2() {
        final String code = "f(n): n + 1";

        final TealInterpreter interpreter = makeInterpreter(code);

        assertThat(interpreter.invoke("f", 41), is(42));
    }

    @Test
    @GradedTest(
            name = "Example library from `Practice.md`",
            points = 1
    )
    public void longExample() {
        final String code = "double(x): x + x \n" +
                "triple(x): !double(x) + x \n" +
                "tenTimes(x): !triple(!double(x)) + !double(!double(x)) \n" +
                "hundredTimes(x): !tenTimes(!tenTimes(x))";

        final TealInterpreter interpreter = makeInterpreter(code);

        assertThat(interpreter.invoke("hundredTimes", 13), is(1300));
    }

    @RunWith(JUnitGradeSheetTestRunner.class)
    @GradedCategory(name = "Illegal Arguments",
    group = "`BasicInterpreter`",
    pointsFormat = GradingPoints.PointsFormat.PARSABLE)
    public static final class IllegalArguments {
        @Test(expected = IllegalArgumentException.class)
        @GradedTest(
                name = "Passing `null` as the function library should throw an `IllegalArgumentException`",
                points = 1
        )
        public void nullLibrary() {
            TealInterpreterFactory.basicInterpreter(null);
        }

        @Test(expected = IllegalArgumentException.class)
        @GradedTest(
                name = "Calling `invoke()` with `null` as the function name should throw an `IllegalArgumentException`",
                points = 1
        )
        public void nullFunction() {
            final TealInterpreter interpreter = makeInterpreter("f(): 1");

            interpreter.invoke(null, null);
        }
    }

    @RunWith(JUnitGradeSheetTestRunner.class)
    @GradedCategory(name = "Illegal invocations",
    group = "`BasicInterpreter`",
    pointsFormat = GradingPoints.PointsFormat.PARSABLE)
    public static final class IllegalInvocations {
        @Test
        @GradedTest(
                name = "Passing no argument when an argument is expected should throw a `TealInterpretationException`",
                points = 1
        )
        public void noArgumentWhenOneIsExpected() {
            final TealInterpreter interpreter = makeInterpreter("f(n): 1");

            expectException(() -> interpreter.invoke("f", null));
        }

        @Test
        @GradedTest(
                name = "Passing an argument when no argument is expected should throw a `TealInterpretationException`",
                points = 1
        )
        public void argumentWhenNoneIsExpected() {
            final TealInterpreter interpreter = makeInterpreter("f(): 1");

            expectException(() -> interpreter.invoke("f", 1));
        }

        @Test
        @GradedTest(
                name = "Calling an undefined function should throw a `TealInterpretationException`",
                points = 1
        )
        public void nonExistentFunction() {
            final TealInterpreter interpreter = makeInterpreter("f(): 1");

            expectException(() -> interpreter.invoke("g", null));
        }

        @Test
        @GradedTest(
                name = "Using a variable when no arguments are expected should throw a `TealInterpretationException`",
                points = 1
        )
        public void nonExistentVariable1() {
            final TealInterpreter interpreter = makeInterpreter("f(): n");

            expectException(() -> interpreter.invoke("f", null));
        }

        @Test
        @GradedTest(
                name = "Using a different variable than the argument should throw a `TealInterpretationException`",
                points = 1
        )
        public void nonExistentVariable2() {
            final TealInterpreter interpreter = makeInterpreter("f(n): x");

            expectException(() -> interpreter.invoke("f", 0));
        }

        @Test
        @GradedTest(
                name = "Using a different variable than the argument in a chain of calls should throw a `TealInterpretationException`",
                points = 0
        )
        public void nonExistentVariable3() {
            final String code = "f(b): a \n g(a): !f(a)";
            final TealInterpreter interpreter = makeInterpreter(code);

            expectException(() -> interpreter.invoke("g", 0));
        }


        private static void expectException(final Runnable function) {
            try {
                function.run();
            } catch (final IllegalArgumentException | TealInterpretationException ignored) {
                // Pass
                return;
            } catch (final Throwable t) {
                fail("Expected IllegalArgumentException or TealInterpretationException, but got a " + t.getClass());
            }

            fail("Expected IllegalArgumentException or TealInterpretationException, but no exception was thrown.");
        }
    }

    @RunWith(JUnitGradeSheetTestRunner.class)
    @GradedCategory(name = "Single-function library",
    group = "`BasicInterpreter`",
    pointsFormat = GradingPoints.PointsFormat.PARSABLE)
    public static final class SingleFunction {
        @Test
        @GradedTest(
                name = "Calling a function that returns a constant works.",
                points = 1
        )
        public void constant() {
            final int result = makeInterpreter("f(): 1").invoke("f", null);

            assertThat(result, is(1));
        }

        @Test
        @GradedTest(
                name = "Calling an identity function (`f(n): n`) works.",
                points = 1
        )
        public void variable() {
            final int result = makeInterpreter("f(n): n").invoke("f", 2);

            assertThat(result, is(2));
        }

        @Test
        @GradedTest(
                name = "Adding two constants is correct.",
                points = 0
        )
        public void additionOfConstants() {
            final int result = makeInterpreter("f(): 1 + 2").invoke("f", null);

            assertThat(result, is(3));
        }

        @Test
        @GradedTest(
                name = "Adding a constant and a variable in a function works.",
                points = 1
        )
        public void additionOfConstantAndVariable() {
            final int result = makeInterpreter("f(n): 1 + n").invoke("f", 3);

            assertThat(result, is(4));
        }

        @Test
        @GradedTest(
                name = "Calling a function with no parameters works.",
                points = 1
        )
        public void functionCall() {
            final String code = "f(): 10 \n g(): !f()";
            final int result = makeInterpreter(code).invoke("g", null);

            assertThat(result, is(10));
        }

        @Test
        @GradedTest(
                name = "Computing the result of another function with a parameter works.",
                points = 1
        )
        public void functionCallWithParameter() {
            final String code = "f(n): n \n g(): !f(9)";
            final int result = makeInterpreter(code).invoke("g", null);

            assertThat(result, is(9));
        }

        // This tests that parameters are "stacked" properly
        // A naive implementation that just sets the "current" param will fail
        @Test
        @GradedTest(
                name = "Computing the result of addition between a constant and another function with a parameter works.",
                points = 1
        )
        public void additionOfCallAndVariable() {
            final String code = "f(n): n \n g(x): !f(3) + x";
            final int result = makeInterpreter(code).invoke("g", 4);

            assertThat(result, is(7));
        }

        @Test
        @GradedTest(
                name = "Calling a long chain of functions works.",
                points = 1
        )
        public void longCallChain() {
            final String code = "a(n): n \n b(m): !a(m) \n c(i): !b(i) \n d(j): !c(j)";
            final int result = makeInterpreter(code).invoke("c", 0);

            assertThat(result, is(0));
        }
    }


    private static TealInterpreter makeInterpreter(final String text) {
        try {
            return TealInterpreterFactory.basicInterpreter(TealParser.parse(text));
        } catch (final TealParseException e) {
            throw new AssertionError("Don't write tests with bad code!", e);
        }
    }
}
