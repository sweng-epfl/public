package homework03;

import grading.GradedCategory;
import grading.GradedTest;
import homework03.mocks.TestDatabaseImpl;
import homework03.mocks.TestServerImpl;
import homework03.util.AlreadyBookedException;
import homework03.util.Client;
import homework03.util.DayFullException;
import org.junit.jupiter.api.BeforeEach;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static homework03.utils.AssertUtils.assertCallbackWasCalled;
import static homework03.utils.AssertUtils.assertHandlesNullArgument;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@GradedCategory("Part 2.3: Implementing the Client")
public class ClientImplTest {

    private static final String SCIPER = "263250";
    private Client client;
    private TestServerImpl server;
    private AtomicBoolean flag;

    @BeforeEach
    public void init() {
        server = new TestServerImpl(new TestDatabaseImpl());
        client = new ClientImpl(SCIPER, server);
        flag = new AtomicBoolean(false);
    }

    @GradedTest("`constructor` should throw an IllegalArgumentException or a NullPointerException if passed a null sciper")
    public void test01_constructorThrowOnNullSciper() {
        assertHandlesNullArgument(() -> new ClientImpl(null, new TestServerImpl(new TestDatabaseImpl())));
    }

    @GradedTest("`constructor` should throw an IllegalArgumentException or a NullPointerException if passed a null server")
    public void test02_constructorThrowOnNullServer() {
        assertHandlesNullArgument(() -> new ClientImpl(SCIPER, null));
    }

    @GradedTest("`constructor` should throw an IllegalArgumentException if passed too short a sciper")
    public void test03_constructorThrowOnShortSciper() {
        assertThrows(IllegalArgumentException.class, () -> new ClientImpl("01234", new TestServerImpl(new TestDatabaseImpl())));
    }

    @GradedTest("`constructor` should throw an IllegalArgumentException if passed too long a sciper")
    public void test04_constructorThrowOnLongSciper() {
        assertThrows(IllegalArgumentException.class, () -> new ClientImpl("0123456", new TestServerImpl(new TestDatabaseImpl())));
    }

    @GradedTest("`constructor` should throw an IllegalArgumentException if passed an invalid sciper")
    public void test05_constructorThrowOnInvalidSciper() {
        assertThrows(IllegalArgumentException.class, () -> new ClientImpl("a12345", new TestServerImpl(new TestDatabaseImpl())));
        assertThrows(IllegalArgumentException.class, () -> new ClientImpl("01234;", new TestServerImpl(new TestDatabaseImpl())));
    }

    @GradedTest("`book` should throw an IllegalArgumentException or a NullPointerException if passed a null day value")
    public void test06_bookThrowsOnNullDay() {
        assertHandlesNullArgument(() -> client.book(null, s -> {
        }, e -> {
        }));
    }

    @GradedTest("`book` should throw an IllegalArgumentException or a NullPointerException if passed a null success callback")
    public void test07_bookThrowsOnNullSuccessCallback() {
        assertHandlesNullArgument(() -> client.book("01.01.2020", null, e -> {
        }));
    }

    @GradedTest("`book` should throw an IllegalArgumentException or a NullPointerException if passed a null error callback")
    public void test08_bookThrowsOnNullErrorCallback() {
        assertHandlesNullArgument(() -> client.book("01.01.2020", s -> {
        }, null));
    }

    @GradedTest("`book` should throw a DayFullException if booking a single day more than five times")
    public void test09_bookThrowsOnOverbooking() {
        String day = "01.01.2020";
        for (int i = 0; i <= 5; i++) {
            Client otherClient = new ClientImpl("01234" + i, server);
            otherClient.book(day, d -> {
            }, e -> {
            });
        }

        client.book(day, d -> fail("The success callback was unexpectedly called"), e -> {
            assertThat(e, isA(DayFullException.class));
            flag.set(true);
        });
        assertCallbackWasCalled(flag);
    }

    @GradedTest("`book` should throw an AlreadyBookedException if booking the same day twice")
    public void test10_bookThrowsOnDoubleBooking() {
        String day = "01.01.2020";
        client.book(day, d -> {
        }, e -> {
        });

        client.book(day, d -> fail("The success callback was unexpectedly called"), e -> {
            assertThat(e, isA(AlreadyBookedException.class));
            flag.set(true);
        });
        assertCallbackWasCalled(flag);
    }

    @GradedTest("`book` should return the booked day in the callback")
    public void test11_bookReturnDayInCallback() {
        String day = "01.01.2020";
        client.book(day, d -> {
            assertThat(d, is(day));
            flag.set(true);
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
        assertCallbackWasCalled(flag);
    }

    @GradedTest("`book` should invoke the error callback if an exception occurs")
    public void test12_bookInvokeErrorCallbackOnException() {
        server.setBookImpl((sciper, day, onSuccess, onError) -> onError.accept(new IllegalArgumentException()));
        client.book("01.01.2020", d -> fail("The success callback was unexpectedly called"), e -> {
            assertThat(e, isA(IllegalArgumentException.class));
            flag.set(true);
        });
        assertCallbackWasCalled(flag);
    }

    @GradedTest("`getBookings` should throw an IllegalArgumentException or a NullPointerException if passed a null success callback")
    public void test13_getBookingsThrowsOnNullSuccessCallback() {
        assertHandlesNullArgument(() -> client.getBookings(null, e -> {
        }));
    }

    @GradedTest("`getBookings` should throw an IllegalArgumentException or a NullPointerException if passed a null error callback")
    public void test14_getBookingsThrowsOnNullErrorCallback() {
        assertHandlesNullArgument(() -> client.getBookings(d -> {
        }, null));
    }

    @GradedTest("`getBookings` should return the booked days in the success callback")
    public void test15_getBookingsReturnDaysInCallback() {
        String day1 = "01.01.2020";
        String day2 = "02.01.2020";
        client.book(day1, s -> {
        }, e -> {
        });
        client.book(day2, s -> {
        }, e -> {
        });

        client.getBookings(s -> {
            assertThat(s, is(Set.of(day1, day2)));
            flag.set(true);
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
        assertCallbackWasCalled(flag);
    }

    @GradedTest("`getBookings` should invoke the error callback if an exception occurs")
    public void test16_getBookingsInvokeErrorCallbackOnException() {
        server.setGetBookingsImpl((sciper, onSuccess, onError) -> onError.accept(new IllegalArgumentException()));
        client.getBookings(d -> fail("The success callback was unexpectedly called"), e -> {
            assertThat(e, isA(IllegalArgumentException.class));
            flag.set(true);
        });
        assertCallbackWasCalled(flag);
    }
}
