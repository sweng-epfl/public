package homework03;

import grading.GradedCategory;
import grading.GradedTest;
import homework03.mocks.TestDatabaseImpl;
import homework03.util.AlreadyBookedException;
import homework03.util.DayFullException;
import homework03.util.Server;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static homework03.utils.AssertUtils.assertCallbackWasCalled;
import static homework03.utils.AssertUtils.assertHandlesNullArgument;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.fail;

@GradedCategory("Part 2.2: Implementing the Server")
public class ServerImplTest {

    private TestDatabaseImpl database;
    private Server server;
    private AtomicBoolean flag;

    public void init() {
        database = new TestDatabaseImpl();
        server = new ServerImpl(database);
        flag = new AtomicBoolean(false);
    }

    @GradedTest("`constructor` should throw an IllegalArgumentException or a NullPointerException if passed a null database value")
    public void test01_constructorThrowsOnNullDatabase() {
        assertHandlesNullArgument(() -> new ServerImpl(null));
    }

    @GradedTest("`book` should throw an IllegalArgumentException or a NullPointerException if passed a null sciper value")
    public void test02_bookThrowsOnNullSciper() {
        init();
        assertHandlesNullArgument(() -> server.book(null, "01.01.2020", s -> {
        }, e -> {
        }));
    }

    @GradedTest("`book` should throw an IllegalArgumentException or a NullPointerException if passed a null day value")
    public void test03_bookThrowsOnNullDay() {
        init();
        assertHandlesNullArgument(() -> server.book("012345", null, s -> {
        }, e -> {
        }));
    }

    @GradedTest("`book` should throw an IllegalArgumentException or a NullPointerException if passed a null success callback")
    public void test04_bookThrowsOnNullSuccessCallback() {
        init();
        assertHandlesNullArgument(() -> server.book("012345", "01.01.2020", null, e -> {
        }));
    }

    @GradedTest("`book` should throw an IllegalArgumentException or a NullPointerException if passed a null error callback")
    public void test05_bookThrowsOnNullErrorCallback() {
        init();
        assertHandlesNullArgument(() -> server.book("012345", "01.01.2020", s -> {
        }, null));
    }

    @GradedTest("`book` should throw a DayFullException if booking a single day more than five times")
    public void test06_bookThrowsOnOverbooking() {
        init();
        String day = "01.01.2020";
        for (int i = 0; i <= 5; i++) {
            server.book("01234" + i, day, d -> {
            }, e -> {
            });
        }

        server.book("012346", day, d -> fail("The success callback was unexpectedly called"), e -> {
            assertThat(e, isA(DayFullException.class));
            flag.set(true);
        });
        assertCallbackWasCalled(flag);
    }

    @GradedTest("`book` should throw an AlreadyBookedException if booking the same day twice with one sciper")
    public void test07_bookThrowsOnDoubleBooking() {
        init();
        String day = "01.01.2020";
        server.book("012345", day, d -> {
        }, e -> {
        });

        server.book("012345", day, d -> fail("The success callback was unexpectedly called"), e -> {
            assertThat(e, isA(AlreadyBookedException.class));
            flag.set(true);
        });
        assertCallbackWasCalled(flag);
    }

    @GradedTest("`book` should return the booked day in the callback")
    public void test08_bookReturnDayInCallback() {
        init();
        String day = "01.01.2020";
        server.book("012345", day, d -> {
            assertThat(d, is(day));
            flag.set(true);
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
        assertCallbackWasCalled(flag);
    }

    @GradedTest("`book` should invoke the error callback if an exception occurs")
    public void test09_bookInvokeErrorCallbackOnException() {
        init();
        database.setPutImpl((sciper, day, onSuccess, onError) -> onError.accept(new IllegalArgumentException()));
        server.book("012345", "01.01.2020", d -> fail("The success callback was unexpectedly called"), e -> {
            assertThat(e, isA(IllegalArgumentException.class));
            flag.set(true);
        });
        assertCallbackWasCalled(flag);
    }

    @GradedTest("`getBookings` should throw an IllegalArgumentException or a NullPointerException if passed a null sciper value")
    public void test10_getBookingsThrowsOnNullSciper() {
        init();
        assertHandlesNullArgument(() -> server.getBookings(null, s -> {
        }, e -> {
        }));
    }

    @GradedTest("`getBookings` should throw an IllegalArgumentException or a NullPointerException if passed a null success callback")
    public void test11_getBookingsThrowsOnNullSuccessCallback() {
        init();
        assertHandlesNullArgument(() -> server.getBookings("012345", null, e -> {
        }));
    }

    @GradedTest("`getBookings` should throw an IllegalArgumentException or a NullPointerException if passed a null error callback")
    public void test12_getBookingsThrowsOnNullErrorCallback() {
        init();
        assertHandlesNullArgument(() -> server.getBookings("012345", d -> {
        }, null));
    }

    @GradedTest("`getBookings` should return the booked days in the success callback")
    public void test13_getBookingsReturnDaysInCallback() {
        init();
        String day1 = "01.01.2020";
        String day2 = "02.01.2020";
        server.book("012345", day1, s -> {
        }, e -> {
        });
        server.book("012345", day2, s -> {
        }, e -> {
        });

        server.getBookings("012345", s -> {
            assertThat(s, is(Set.of(day1, day2)));
            flag.set(true);
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
        assertCallbackWasCalled(flag);
    }

    @GradedTest("`getBookings` should invoke the error callback if an exception occurs")
    public void test14_getBookingsInvokeErrorCallbackOnException() {
        init();
        database.setGetImpl((sciper, onSuccess, onError) -> onError.accept(new IllegalArgumentException()));
        server.getBookings("012345", d -> fail("The success callback was unexpectedly called"), e -> {
            assertThat(e, isA(IllegalArgumentException.class));
            flag.set(true);
        });
        assertCallbackWasCalled(flag);
    }
}
