// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!
package internal.reflect.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * (only used for SwEng Signature Checks Tests. Students do not need to use this class in their tests)
 *
 * (note for curious students: this has some documentation but you should not waste time reading it, it's meant
 * for the sweng staff :)) (you won't find solutions for the theory in there either.)
 *
 * This class provides utils to check methods or constructors in a class
 */
public final class ReflectionCheckers {
    /**
     * Check that the given class has a method with given name and given arguments, returning void and throwing no
     * exception.
     *
     * <b>The check uses Hamcrest Matchers, this method is meant to be used in JUnit tests.</b>
     * @param clazz name of the class or interface
     * @param method name of the method
     * @param args arguments of the method (if empty, the method has no arguments)
     * @throws ReflectiveOperationException
     *  all ReflectiveOperationException are normally caught by the JUnit assertions.
     *  if any is thrown, this indicates a problem somewhere (likely a missing class or method) ==> the test must fail
     */
    public static void checkMethod(Class<?> clazz, String method, Class<?>... args) throws ReflectiveOperationException {
        checkMethod(clazz, method, t -> t.getReturnType().equals(void.class), args);
    }

    /**
     * Check that the given class has a method with given name and given arguments, returning void and throwing only the
     * given exceptions.
     *
     * Regarding exception, this method only checks that no new exception is added to the list of thrown exceptions.
     * It doesn't check if exceptions are removed from the list of thrown exceptions.
     * TODO: we may want to fix this, but we must make a difference between classes and interfaces.
     *
     * <b>The check uses Hamcrest Matchers, this method is meant to be used in JUnit tests.</b>
     * @param clazz name of the class or interface
     * @param method name of the method
     * @param allowedExceptions the list of exceptions the method is allowed to throw
     * @param args arguments of the method (if empty, the method has no arguments)
     * @throws ReflectiveOperationException
     *  all ReflectiveOperationException are normally caught by the JUnit assertions.
     *  if any is thrown, this indicates a problem somewhere (likely a missing class or method) ==> the test must fail
     */
    public static void checkMethod(Class<?> clazz, String method, Class<?>[] allowedExceptions, Class<?>... args) throws ReflectiveOperationException {
        checkMethod(clazz, method, t -> t.getReturnType().equals(void.class), allowedExceptions, args);
    }

    /**
     * Check that the given class has a method with given name and given arguments, throwing no exception and matching
     * a given predicate (useful to check return type or parameterized arguments).
     *
     * <b>The check uses Hamcrest Matchers, this method is meant to be used in JUnit tests.</b>
     * @param clazz name of the class or interface
     * @param method name of the method
     * @param methodPredicate a predicate the method must pass
     * @param args arguments of the method (if empty, the method has no arguments)
     * @throws ReflectiveOperationException
     *  all ReflectiveOperationException are normally caught by the JUnit assertions.
     *  if any is thrown, this indicates a problem somewhere (likely a missing class or method) ==> the test must fail
     */
    public static void checkMethod(Class<?> clazz, String method, Predicate<Method> methodPredicate, Class<?>... args) throws ReflectiveOperationException {
        checkMethod(clazz, method, methodPredicate, new Class<?>[0], args);
    }

    /**
     * Check that the given class has a method with given name and given arguments, matching
     * a given predicate (useful to check return type or parameterized arguments) and throwing only the given exceptions.
     *
     * Regarding exception, this method only checks that no new exception is added to the list of thrown exceptions.
     * It doesn't check if exceptions are removed from the list of thrown exceptions.
     * TODO: we may want to fix this, but we must make a difference between classes and interfaces.
     *
     * <b>The check uses Hamcrest Matchers, this method is meant to be used in JUnit tests.</b>
     * @param clazz name of the class or interface
     * @param method name of the method
     * @param methodPredicate a predicate the method must pass
     * @param allowedExceptions the list of exceptions the method is allowed to throw
     * @param args arguments of the method (if empty, the method has no arguments)
     * @throws ReflectiveOperationException
     *  all ReflectiveOperationException are normally caught by the JUnit assertions.
     *  if any is thrown, this indicates a problem somewhere (likely a missing class or method) ==> the test must fail
     */
    public static void checkMethod(Class<?> clazz, String method, Predicate<Method> methodPredicate, Class<?>[] allowedExceptions, Class<?>... args) throws ReflectiveOperationException {
        assertDoesNotThrow(() -> clazz.getMethod(method, args), "Method not found " + method + "(" + Arrays.stream(args).map(Class::toString).collect(Collectors.joining(", ")) + ") [with " + args.length + " args] in class " + clazz);
        assertTrue(methodPredicate.test(clazz.getMethod(method, args)), "Error in method " + method + " in class " + clazz);
        List<Class<?>> observed = List.of(clazz.getMethod(method, args).getExceptionTypes());
        assertThat("Unexpected thrown exception in method " + method + " of class " + clazz, observed, containsInAnyOrder(allowedExceptions));
    }

    /**
     * Check that the given class has a constructor with given parameters
     * <b>The check uses Hamcrest Matchers, this method is meant to be used in JUnit tests.</b>
     * @param clazz the class to check
     * @param args the list of arguments
     * @param <T> the type of the class
     */
    public static <T> void checkHasConstructor(Class<T> clazz, Class<?>... args) {
        assertDoesNotThrow(() -> clazz.getConstructor(args), "Required public constructor not found with arguments " + Arrays.toString(args) + " in class " + clazz);
    }

    /**
     * Returns a predicate checking that, for a given method, the argument at given position of that method (first
     * argument being at position 0) has given type.
     *
     * Useful for checking arguments with parameterized types.
     * Pass this to the predicate argument of {@link ReflectionCheckers#checkMethod(Class, String, Predicate, Class[])}
     *
     * @param position the position of the argument to check, starting from 0
     * @param type the typetoken holding the type of that argument
     * @return a predicate running a Hamcrest test to check the argument type on a provided method ; returns true iff
     * the argument at given position has given type
     */
    public static Predicate<Method> checkArgInPositionHasType(int position, TypeToken<?> type) {
        return checkArgInPositionHasType(position, type.getType());
    }

    /**
     * Returns a predicate checking that, for a given method, the argument at given position of that method (first
     * argument being at position 0) has given type.
     *
     * Useful for checking arguments with parameterized types.
     * Pass this to the predicate argument of {@link ReflectionCheckers#checkMethod(Class, String, Predicate, Class[])}
     *
     * @param position the position of the argument to check, starting from 0
     * @param type the requested type of that argument
     * @return a predicate running a Hamcrest test to check the argument type on a provided method ; returns true iff
     * the argument at given position has given type
     */
    public static Predicate<Method> checkArgInPositionHasType(int position, Type type) {
        return method -> {
            assertThat("(" + method.getDeclaringClass() + ") Method " + method + "'s argument " + (position + 1) + " has incorrect type.", method.getGenericParameterTypes()[position], is(type));

            return method.getGenericParameterTypes()[position].equals(type); // should be the exact same as before, we can possibly return true directly
        };
    }
}
