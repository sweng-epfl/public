package ch.epfl.sweng.midterm;

// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

import ch.epfl.sweng.midterm.booking.*;
import ch.epfl.sweng.midterm.planes.Plane;
import ch.epfl.sweng.midterm.planes.SwengPlane;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
/**
 * This test checks that all the interfaces we expect are still declared and
 * still define the right methods
 */
public class SignaturesChecker {
    private void checkMethod(Class<?> clazz, String method, Class<?>... args) throws ReflectiveOperationException {
        checkMethod(clazz, method, t -> t.getReturnType().equals(void.class), args);
    }

    private void checkMethod(Class<?> clazz, String method, Class<?>[] allowedExceptions, Class<?>... args) throws ReflectiveOperationException {
        checkMethod(clazz, method, t -> t.getReturnType().equals(void.class), allowedExceptions, args);
    }

    private void checkMethod(Class<?> clazz, String method, Predicate<Method> returnTypeChecker, Class<?>... args) throws ReflectiveOperationException {
        checkMethod(clazz, method, returnTypeChecker, new Class<?>[0], args);
    }

    private void checkMethod(Class<?> clazz, String method, Predicate<Method> returnTypeChecker, Class<?>[] allowedExceptions, Class<?>... args) throws ReflectiveOperationException {
        assertDoesNotThrow(() -> clazz.getMethod(method, args), "method not found " + method);
        assertTrue(returnTypeChecker.test(clazz.getMethod(method, args)), "unexpected return type in method " + method);
        List<Class<?>> observed = List.of(clazz.getMethod(method, args).getExceptionTypes());
        assertThat("unexpected thrown exception in method " + method, observed, containsInAnyOrder(allowedExceptions));
    }

    @Test
    void checkPlaneHasMethods() throws ReflectiveOperationException {
        List.of(SwengPlane.class, Plane.class).forEach(plane -> {
            try {
                checkMethod(plane, "addFuel", double.class);
                checkMethod(plane, "additionalFuelNeeded", m -> m.getReturnType().equals(double.class), int.class, Function.class);
                checkMethod(plane, "getRemainingFuelLiters", m -> m.getReturnType().equals(double.class));
                checkMethod(plane, "load", int.class, int.class);
                checkMethod(plane, "unload");
                checkMethod(plane, "computePlaneWeight", m -> m.getReturnType().equals(double.class));
                checkMethod(plane, "computePlaneSpeedFunction", m -> m.getGenericReturnType().getTypeName().equals("java.util.function.Function<java.lang.Integer, java.lang.Double>"), int.class);
            } catch (ReflectiveOperationException ex) {
                fail(ex);
            }
        });
    }

    @Test
    void checkPlaneHasNoMoreMethods() {
        assertEquals(7, Plane.class.getMethods().length);
    }

    @Test
    void checkBookingSystemHasMethods() throws ReflectiveOperationException {
        List.of(BookingSystem.class, SwengBookingSystem.class).forEach(bs -> {
            try {
                checkMethod(bs, "chooseSeat", new Class<?>[] {IllegalSeatException.class, PersonAlreadySeatedException.class, SeatAlreadyTakenException.class}, Person.class, String.class);
                checkMethod(bs, "freeSeat", new Class<?>[] {PersonNotSeatedException.class}, Person.class);
                checkMethod(bs, "getPersonAtSeat", m -> "java.util.Optional<ch.epfl.sweng.midterm.booking.Person>".equals(m.getGenericReturnType().getTypeName()), new Class<?>[] {IllegalSeatException.class}, String.class);
                checkMethod(bs, "getSeatForPerson", m -> "java.util.Optional<java.lang.String>".equals(m.getGenericReturnType().getTypeName()), Person.class);
            } catch (ReflectiveOperationException ex) {
                fail(ex);
            }
        });

    }

    @Test
    void checkBookingSystemHasNoMoreMethods() {
        assertEquals(4, BookingSystem.class.getMethods().length);
    }
}
