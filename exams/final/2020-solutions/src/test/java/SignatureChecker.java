import org.junit.jupiter.api.Test;

import ch.epfl.sweng.Student;
import ch.epfl.sweng.StudentDatabase;
import ch.epfl.sweng.StudentDatabaseAdapter;
import ch.epfl.sweng.database.CachedDatabase;
import ch.epfl.sweng.database.Database;
import ch.epfl.sweng.database.DatabaseException;
import ch.epfl.sweng.database.RetryDatabase;
import ch.epfl.sweng.database.UnreliableDatabase;
import ch.epfl.sweng.mvc.AppController;
import ch.epfl.sweng.mvc.AppModel;
import ch.epfl.sweng.mvc.AppView;
import ch.epfl.sweng.mvc.ModelGetResult;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * This test makes sure that the provided interfaces were not modified.
 * This would prevent the other tests from running and leading to zero points.
 * (note for curious students: this has some documentation but you should not waste time reading it,
 * it's meant for the staff :) you won't find solutions for the theory in there either)
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class SignatureChecker {

    private static final TypeToken<String> stringType = new TypeToken<>() {
    };
    private static final TypeToken<Student> studentType = new TypeToken<>() {
    };
    private static final TypeToken<ModelGetResult> modelResultType = new TypeToken<>() {
    };
    private static final TypeToken<Object> objectType = new TypeToken<>() {
    };

    private static final Class<?>[] databaseException = {DatabaseException.class};

    @Test
    void checkDatabaseHasCorrectMethods() {
        assertEquals(3, Database.class.getMethods().length, "You added method(s) to the Database interface.");

        List.of(Database.class, CachedDatabase.class, RetryDatabase.class, UnreliableDatabase.class).forEach(clazz -> {
            try {
                checkMethod(clazz, "put",
                        checkArgInPositionHasType(0, stringType)
                                .and(checkArgInPositionHasType(1, stringType)), String.class, String.class);
                checkMethod(clazz, "remove", checkArgInPositionHasType(0, stringType), databaseException, String.class);
                checkMethod(clazz, "get",
                        checkArgInPositionHasType(0, stringType)
                                .and(checkReturnType(stringType)), databaseException, String.class);
            } catch (ReflectiveOperationException ex) {
                fail(ex);
            }
        });

        checkImplements(CachedDatabase.class, Database.class);
        checkImplements(RetryDatabase.class, Database.class);
        checkImplements(UnreliableDatabase.class, Database.class);
        checkHasConstructor(CachedDatabase.class, Database.class);
        checkHasConstructor(RetryDatabase.class, Database.class);
        checkHasConstructor(UnreliableDatabase.class);
    }

    @Test
    void checkAppControllerHasCorrectMethods() {
        try {
            checkMethod(AppController.class, "handleRequest",
                    checkArgInPositionHasType(0, stringType)
                            .and(checkReturnType(stringType)), String.class);
        } catch (ReflectiveOperationException e) {
            fail(e);
        }

        checkHasConstructor(AppController.class, AppModel.class, AppView.class);
    }

    @Test
    void checkAppViewHasCorrectMethods() {
        try {
            checkMethod(AppView.class, "getErrorMsg",
                    checkArgInPositionHasType(0, stringType)
                            .and(checkReturnType(stringType)), String.class);
            checkMethod(AppView.class, "getSuccessMsg",
                    checkArgInPositionHasType(0, stringType)
                            .and(checkReturnType(stringType)), String.class);
            checkMethod(AppView.class, "getStudentResultMsg",
                    checkArgInPositionHasType(0, stringType)
                            .and(checkArgInPositionHasType(1, long.class))
                            .and(checkArgInPositionHasType(2, studentType))
                            .and(checkReturnType(stringType)), String.class, long.class, Student.class);
        } catch (ReflectiveOperationException e) {
            fail(e);
        }

        checkHasConstructor(AppView.class);
    }

    @Test
    void checkAppModelHasCorrectMethods() {
        try {
            checkMethod(AppModel.class, "put", checkArgInPositionHasType(0, studentType), databaseException, Student.class);
            checkMethod(AppModel.class, "remove", checkArgInPositionHasType(0, stringType), databaseException, String.class);
            checkMethod(AppModel.class, "get",
                    checkArgInPositionHasType(0, stringType)
                            .and(checkReturnType(modelResultType)), databaseException, String.class);
        } catch (ReflectiveOperationException e) {
            fail(e);
        }

        checkHasConstructor(AppModel.class, StudentDatabase.class);
    }

    @Test
    void checkStudentHasCorrectMethods() {
        try {
            checkMethod(Student.class, "toString", checkReturnType(stringType));
            checkMethod(Student.class, "equals",
                    checkArgInPositionHasType(0, objectType)
                            .and(checkReturnType(boolean.class)), Object.class);
            checkMethod(Student.class, "hashCode", checkReturnType(int.class));
        } catch (ReflectiveOperationException e) {
            fail(e);
        }

        checkImplements(Student.class, Serializable.class);
        checkHasConstructor(Student.class, String.class, String.class, String.class);
    }

    @Test
    void checkStudentDatabaseAdapterHasCorrectMethods() {
        assertEquals(3, StudentDatabase.class.getMethods().length, "You added method(s) to the StudentDatabase interface.");
        try {
            checkMethod(StudentDatabaseAdapter.class, "put", checkArgInPositionHasType(0, studentType), databaseException, Student.class);
            checkMethod(StudentDatabaseAdapter.class, "remove", checkArgInPositionHasType(0, stringType), databaseException, String.class);
            checkMethod(StudentDatabaseAdapter.class, "get",
                    checkArgInPositionHasType(0, stringType)
                            .and(checkReturnType(studentType)), databaseException, String.class);
        } catch (ReflectiveOperationException e) {
            fail(e);
        }

        checkImplements(StudentDatabaseAdapter.class, StudentDatabase.class);
        checkHasConstructor(StudentDatabaseAdapter.class, Database.class);
    }



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
    private static void checkMethod(Class<?> clazz, String method, Class<?>... args) throws ReflectiveOperationException {
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
    private static void checkMethod(Class<?> clazz, String method, Class<?>[] allowedExceptions, Class<?>... args) throws ReflectiveOperationException {
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
    private static void checkMethod(Class<?> clazz, String method, Predicate<Method> methodPredicate, Class<?>... args) throws ReflectiveOperationException {
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
    private static void checkMethod(Class<?> clazz, String method, Predicate<Method> methodPredicate, Class<?>[] allowedExceptions, Class<?>... args) throws ReflectiveOperationException {
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
    private static <T> void checkHasConstructor(Class<T> clazz, Class<?>... args) {
        assertDoesNotThrow(() -> clazz.getConstructor(args), "Required public constructor not found with arguments " + Arrays.toString(args) + " in class " + clazz);
    }

    /**
     * Checks that the given class implements all of the interfaces given as parameters.
     * <b>The check uses Hamcrest Matchers, this method is meant to be used in JUnit tests.</b>
     * @param clazz the class to check
     * @param args the list of interfaces
     * @param <T> the type of the class
     */
    private static <T> void checkImplements(Class<T> clazz, Class<?>... args) {
        assertThat("Required interface implementation not found with arguments " + Arrays.toString(args) + " in class " + clazz, List.of(clazz.getInterfaces()), containsInAnyOrder(args));
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
    private static Predicate<Method> checkArgInPositionHasType(int position, TypeToken<?> type) {
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
    private static Predicate<Method> checkArgInPositionHasType(int position, Type type) {
        return method -> {
            assertThat("(" + method.getDeclaringClass() + ") Method " + method + "'s argument " + (position + 1) + " has incorrect type.", method.getGenericParameterTypes()[position], is(type));
            return method.getGenericParameterTypes()[position].equals(type); // should be the exact same as before, we can possibly return true directly
        };
    }

    /**
     * Returns a predicate checking that, for a given method, the return type is correct.
     *
     * Pass this to the predicate argument of {@link ReflectionCheckers#checkMethod(Class, String, Predicate, Class[])}
     *
     * @param type the typetoken holding the type of that argument
     * @return a predicate running a Hamcrest test tp check the argument type on a provided method. Returns true
     * iff the method's return type matches the given type
     */
    private static Predicate<Method> checkReturnType(TypeToken<?> type) {
        return checkReturnType(type.getType());
    }

    /**
     * Returns a predicate checking that, for a given method, the return type is correct.
     *
     * Pass this to the predicate argument of {@link ReflectionCheckers#checkMethod(Class, String, Predicate, Class[])}
     *
     * @param type the requested type of that argument
     * @return a predicate running a Hamcrest test tp check the argument type on a provided method. Returns true
     * iff the method's return type matches the given type
     */
    private static Predicate<Method> checkReturnType(Type type) {
        return method -> {
            assertThat("(" + method.getDeclaringClass() + ") Method " + method + "'s has incorrect return type.", method.getReturnType(), is(type));
            return method.getReturnType().equals(type);
        };
    }

    /**
     * This typetoken allows you to extract a Type object from a parameterized type.
     *
     * For example, if you need a {@link java.lang.reflect.Type} object for a {@link java.util.function.Consumer<String>},
     * you can create a new subclass of {@link TypeToken<java.util.function.Consumer<String>>} and use
     * {@link TypeToken#getType()} on it to get the type.
     *
     * Note: this is a bit "hacky". In Java, because of type erasure, you need to extend a generic class in order to be
     * able to get the value of the type parameter. Therefore, it is very important that this class stays abstract and
     * that you subclass it (even with anonymous subclasses) whenever you need it :)
     *
     * @param <T> the parameterized type for which you need to extract a java Type
     */
    private static abstract class TypeToken<T> {
        private ParameterizedType getGenericSuperclass() {
            return (ParameterizedType) this.getClass().getGenericSuperclass();
        }

        /**
         * Get the Type representation of the type passed as a generic parameter to this class
         */
        public Type getType() {
            return getGenericSuperclass().getActualTypeArguments()[0];
        }
    }
}
