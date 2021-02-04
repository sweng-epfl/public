package ch.epfl.sweng.database;

import org.junit.jupiter.api.BeforeEach;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import ch.epfl.sweng.mocks.DatabaseMock;
import grading.GradedCategory;
import grading.GradedTest;

import static ch.epfl.sweng.utils.AssertUtils.assertHandlesNullArgument;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@GradedCategory("Part 2.3: Implementing the RetryDatabase")
public final class RetryDatabaseTest {

    private DatabaseMock database;
    private RetryDatabase retry;

    @BeforeEach
    public void init() {
        database = new DatabaseMock();
        retry = new RetryDatabase(database);
    }

    @GradedTest("`constructor` should throw an IllegalArgumentException or a NullPointerException if the argument is null")
    public void constructorThrowOnNullArgument() {
        assertHandlesNullArgument(() -> new RetryDatabase(null));
    }

    @GradedTest("`put` should throw an IllegalArgumentException or a NullPointerException if the first argument is null")
    public void putThrowOnNullFirstArgument() {
        assertHandlesNullArgument(() -> retry.put(null, "value"));
    }

    @GradedTest("`put` should throw an IllegalArgumentException or a NullPointerException if the second argument is null")
    public void putThrowOnNullSecondArgument() {
        assertHandlesNullArgument(() -> retry.put("key", null));
    }

    @GradedTest("`put` should call the underlying database with the correct data")
    public void putCallUnderlyingDatabaseWithCorrectData() {
        String key = "key";
        String value = "value";

        AtomicBoolean execFlag = new AtomicBoolean(false);
        database.onPut((k, v) -> {
            assertThat(k, is(key));
            assertThat(v, is(value));
            execFlag.set(true);
        });
        retry.put(key, value);
        assertThat("Database.put was not executed", execFlag.get());
    }

    @GradedTest("`remove` should throw an IllegalArgumentException or a NullPointerException if the argument is null")
    public void removeThrowOnNullArgument() {
        assertHandlesNullArgument(() -> {
            try {
                retry.remove(null);
            } catch(DatabaseException e) {
                fail("An unexpected DatabaseException was thrown");
            }
        });
    }

    @GradedTest("`remove` should call the underlying database with the correct data")
    public void removeCallUnderlyingDatabaseWithCorrectData() {
        String key = "key";

        AtomicBoolean execFlag = new AtomicBoolean(false);
        database.onRemove(k -> {
            assertThat(k, is(key));
            execFlag.set(true);
        });
        try {
            retry.remove(key);
        } catch(DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }
        assertThat("Database.remove was not executed", execFlag.get());
    }

    @GradedTest("`remove` should throw a DatabaseException if the key is not present in the database")
    public void removeThrowWithAbsentKey() {
        assertThrows(DatabaseException.class, () -> retry.remove("key"));
    }

    @GradedTest("`get` should fail with an IllegalArgumentException or a NullPointerException if the argument is null")
    public void getThrowExceptionOnNullArgument() {
        assertHandlesNullArgument(() -> {
            try {
                retry.get(null);
            } catch (DatabaseException e) {
                fail("An unexpected DatabaseException was thrown");
            }
        });
    }

    @GradedTest("`get` should fail with a DatabaseException if the underlying database fails more than 10 times")
    public void getThrowExceptionAfterTenRetries() {
        AtomicInteger counter = new AtomicInteger(0);
        database.onGet(k -> {
            int c = counter.incrementAndGet();
            if(c > 10) {
                return "value";
            }
            throw new DatabaseException("simulated database exception");
        });
        assertThrows(DatabaseException.class, () -> retry.get("123456"));
    }

    @GradedTest("`get` should return a value if the underlying database fails less or equals than 10 times")
    public void getReturnDataIfLessOrEqualThanTenRetries() {
        String value = "value";
        AtomicInteger counter = new AtomicInteger(0);
        database.onGet(k -> {
            int c = counter.incrementAndGet();
            if(c > 9) {
                return value;
            }
            throw new DatabaseException("simulated database exception");
        });
        try {
            assertThat(retry.get("123456"), is(value));
        } catch(DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }
    }
}
