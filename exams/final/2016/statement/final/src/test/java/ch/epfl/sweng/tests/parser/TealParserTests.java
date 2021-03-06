package ch.epfl.sweng.tests.parser;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import ch.epfl.sweng.TealFunction;
import ch.epfl.sweng.TealLibrary;
import ch.epfl.sweng.nodes.TealNode;
import ch.epfl.sweng.nodes.TealVariableNode;
import ch.epfl.sweng.parser.TealParseException;
import ch.epfl.sweng.parser.TealParser;

import static org.hamcrest.Matchers.hasSize;
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

    // TODO: Add your own tests
    @Test (expected = IllegalArgumentException.class)
    public void testParseNull() throws TealParseException {
        TealParser.parse(null);
    }

    @Test
    public void testZeroFunctions() throws TealParseException {
        final TealLibrary library = TealParser.parse("");

        assertThat(library.functions.entrySet(), hasSize(0));
    }

    @Test (expected = TealParseException.class)
    public void testDuplicateName() throws TealParseException {
        final TealLibrary library = TealParser.parse("f(n): n"+System.lineSeparator()+"f(n): n+1");

        assertThat(library.functions.entrySet(), hasSize(1));
        assertThat(library, hasFunction("f", "n", new TealVariableNode("n")));
    }

    @Test
    public void testNoParamFunction() throws TealParseException {
        final TealLibrary library = TealParser.parse("answer(): 42");

        assertThat(library.functions.entrySet(), hasSize(1));
    }

    @Test (expected = TealParseException.class)
    public void testLeftOverInput() throws TealParseException {
        final TealLibrary library = TealParser.parse("f(n): n (");

        assertThat(library.functions.entrySet(), hasSize(1));
        assertThat(library, hasFunction("f", "n", new TealVariableNode("n")));
    }

    @Test
    public void additionNode() throws TealParseException {
        final TealLibrary library  = TealParser.parse("f(x): x + 2");

        assertThat(library.functions.entrySet(), hasSize(1));
        assertThat(library, hasFunction("f", "x", new TealVariableNode("x + 2")));
    }

    @Test
    public void validFunctionCall() throws TealParseException {
        final TealLibrary library = TealParser.parse("f(x): x + 1"+
                System.lineSeparator() + "h(): 100" +
                System.lineSeparator() + "g(n): !f(2+n) + 1" +
                System.lineSeparator() + "z(x): !h() + x");

        assertThat(library.functions.entrySet(), hasSize(4));
    }

    @Test (expected = TealParseException.class)
    public  void invalidFunctionCall() throws TealParseException {
        final TealLibrary library = TealParser.parse("f(x): x + 1"+
                System.lineSeparator() + "g(n): !2(2+n) + 1");

        assertThat(library.functions.entrySet(), hasSize(1));
    }

    @Test (expected = TealParseException.class)
    public void largeNumberError() throws TealParseException {
        final TealLibrary library = TealParser.parse("f(n): 1000000000000000000000");

        assertThat(library.functions.entrySet(), hasSize(0));
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

            String strBody = body.toString();
            String strFunctionBody = function.body.toString();

            if (strBody.equals(strFunctionBody)) {
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
