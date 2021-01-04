package homework03;

import grading.GradedCategory;
import grading.GradedTest;
import homework03.util.Database;
import org.junit.jupiter.api.BeforeEach;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static homework03.utils.AssertUtils.assertCallbackWasCalled;
import static homework03.utils.AssertUtils.assertHandlesNullArgument;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@GradedCategory("Part 2.1: Implementing the Database")
public class DatabaseImplTest {

    private Database database;
    private AtomicBoolean flag;

    @BeforeEach
    public void init() {
        database = new DatabaseImpl();
        flag = new AtomicBoolean(false);
    }

    @GradedTest("`put` should throw an IllegalArgumentException or a NullPointerException if passed a null sciper")
    public void test01_putThrowsOnNullSciper() {
        assertHandlesNullArgument(() -> database.put(null, "2", s -> {
        }, e -> {
        }));
    }

    @GradedTest("`put` should throw an IllegalArgumentException or a NullPointerException if passed a null day")
    public void test02_putThrowsOnNullDay() {
        assertHandlesNullArgument(() -> database.put("012345", null, s -> {
        }, e -> {
        }));
    }

    @GradedTest("`put` should throw an IllegalArgumentException or a NullPointerException if passed a null success callback")
    public void test03_putThrowsOnNullSuccessCallback() {
        assertHandlesNullArgument(() -> database.put("012345", "2", null, e -> {
        }));
    }

    @GradedTest("`put` should throw an IllegalArgumentException or a NullPointerException if passed a null error callback")
    public void test04_putThrowsOnNullErrorCallback() {
        assertHandlesNullArgument(() -> database.put("012345", "2", s -> {
        }, null));
    }

    @GradedTest("`put` should return the booked day in the callback")
    public void test06_putReturnsTheBookedDay() {
        var day = "02.12.2020";
        database.put("123456", day, d -> {
            assertThat(d, is(day));
            flag.set(true);
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
        assertCallbackWasCalled(flag);
    }

    @GradedTest("`get` should throw an IllegalArgumentException or a NullPointerException if passed a null sciper")
    public void test07_getThrowsOnNullSciper() {
        assertHandlesNullArgument(() -> database.get(null, s -> {
        }, e -> {
        }));
    }

    @GradedTest("`get` should throw an IllegalArgumentException or a NullPointerException if passed a null success callback")
    public void test08_getThrowsOnNullSuccessCallback() {
        assertHandlesNullArgument(() -> database.get("012345", null, e -> {
        }));
    }

    @GradedTest("`get` should throw an IllegalArgumentException or a NullPointerException if passed a null error callback")
    public void test09_getThrowsOnNullErrorCallback() {
        assertHandlesNullArgument(() -> database.get("012345", s -> {
        }, null));
    }

    @GradedTest("`get` should return the correct bookings in the callback")
    public void test10_getReturnsTheBookings() {
        var day = "02.12.2020";
        var sciper = "123456";
        database.put(sciper, day, d -> {
            assertThat(d, is(day));
            database.get(sciper,
                    bookings -> {
                        assertThat(bookings, is(Set.of(day)));
                        flag.set(true);
                    },
                    e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));

        assertCallbackWasCalled(flag);
    }

    @GradedTest("`get` should return the empty set if the sciper is not in the database")
    public void test11_getReturnsTheEmptySetIfNotInDatabase() {
        var sciper = "123456";
        database.get(sciper,
                bookings -> {
                    assertThat(bookings, is(Set.of()));
                    flag.set(true);
                },
                e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));

        assertCallbackWasCalled(flag);
    }

    @GradedTest("`getBookingsCount` should throw an IllegalArgumentException or a NullPointerException if passed a null date")
    public void test12_getBookingsCountThrowsOnNullDate() {
        assertHandlesNullArgument(() -> database.getBookingsCount(null, s -> {
        }, e -> {
        }));
    }

    @GradedTest("`getBookingsCount` should throw an IllegalArgumentException or a NullPointerException if passed a null success callback")
    public void test13_getBookingsCountThrowsOnNullSuccessCallback() {
        assertHandlesNullArgument(() -> database.getBookingsCount("01.01.2020", null, e -> {
        }));
    }

    @GradedTest("`getBookingsCount` should throw an IllegalArgumentException or a NullPointerException if passed a null error callback")
    public void test14_getBookingsCountThrowsOnNullErrorCallback() {
        assertHandlesNullArgument(() -> database.getBookingsCount("01.01.2020", s -> {
        }, null));
    }

    @GradedTest("`getBookingsCount` should return the correct count in the callback")
    public void test15_getBookingsCountReturnsTheCorrectCount() {
        var day = "02.12.2020";
        var sciper1 = "123456";
        var sciper2 = "123457";
        database.put(sciper1, day, d1 -> {
            assertThat(d1, is(day));
            database.put(sciper2, day, d2 -> {
                assertThat(d2, is(day));
                database.getBookingsCount(day,
                        count -> {
                            assertThat(count, is(2));
                            flag.set(true);
                        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
            }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));
        }, e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));

        assertCallbackWasCalled(flag);
    }

    @GradedTest("`getBookingsCount` should return 0 if the day was never booked")
    public void test16_getBookingsCountReturnsZeroIfNeverBooked() {
        var day = "01.01.2020";
        database.getBookingsCount(day,
                count -> {
                    assertThat(count, is(0));
                    flag.set(true);
                },
                e -> fail("An unexpected exception was thrown: " + e.getMessage(), e));

        assertCallbackWasCalled(flag);
    }

}
