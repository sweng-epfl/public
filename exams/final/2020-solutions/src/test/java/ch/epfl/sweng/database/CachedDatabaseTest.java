package ch.epfl.sweng.database;

import org.junit.jupiter.api.BeforeEach;

import java.util.concurrent.atomic.AtomicBoolean;

import ch.epfl.sweng.mocks.DatabaseMock;
import grading.GradedCategory;
import grading.GradedTest;

import static ch.epfl.sweng.utils.AssertUtils.assertHandlesNullArgument;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@GradedCategory("Part 2.4: Implementing the CachedDatabase")
public final class CachedDatabaseTest {

    private DatabaseMock database;
    private CachedDatabase cache;

    @BeforeEach
    public void init() {
        database = new DatabaseMock();
        cache = new CachedDatabase(database);
    }

    @GradedTest("`constructor` should throw an IllegalArgumentException or a NullPointerException if the argument is null")
    public void constructorThrowOnNullArgument() {
        assertHandlesNullArgument(() -> new CachedDatabase(null));
    }

    @GradedTest("`put` should throw an IllegalArgumentException or a NullPointerException if the first argument is null")
    public void putThrowOnNullFirstArgument() {
        assertHandlesNullArgument(() -> cache.put(null, "value"));
    }

    @GradedTest("`put` should throw an IllegalArgumentException or a NullPointerException if the second argument is null")
    public void putThrowOnNullSecondArgument() {
        assertHandlesNullArgument(() -> cache.put("key", null));
    }

    @GradedTest("`put` should call the underlying database")
    public void putCallUnderlyingDatabase() {
        String key = "key";
        String value = "value";

        AtomicBoolean execFlag = new AtomicBoolean(false);
        database.onPut((k, v) -> {
            assertThat(k, is(key));
            assertThat(v, is(value));
            execFlag.set(true);
        });

        cache.put(key, value);
        assertThat("Database.put was not executed", execFlag.get());
    }

    @GradedTest("`remove` should throw an IllegalArgumentException or a NullPointerException if the argument is null")
    public void removeThrowOnNullArgument() {
        assertHandlesNullArgument(() -> {
            try {
                cache.remove(null);
            } catch(DatabaseException e) {
                fail("An unexpected DatabaseException was thrown");
            }
        });
    }

    @GradedTest("`remove` should call the underlying database")
    public void removeCallUnderlyingDatabase() {
        String key = "key";

        AtomicBoolean execFlag = new AtomicBoolean(false);
        database.onRemove((k) -> {
            assertThat(k, is(key));
            execFlag.set(true);
        });

        try {
            cache.remove(key);
        } catch(DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }
        assertThat("Database.remove was not executed", execFlag.get());
    }

    @GradedTest("`remove` should throw a DatabaseException if the key is not present in the database")
    public void removeThrowWithAbsentKey() {
        assertThrows(DatabaseException.class, () -> cache.remove("key"));
    }

    @GradedTest("`get` should throw an IllegalArgumentException or a NullPointerException if the argument is null")
    public void getThrowOnNullArgument() {
        assertHandlesNullArgument(() -> {
            try {
                cache.get(null);
            } catch (DatabaseException e) {
                fail("An unexpected DatabaseException was thrown");
            }
        });
    }

    @GradedTest("`get` should return the data from the underlying database")
    public void getReturnDataFromUnderlyingDatabase() {
        String key = "key";
        String value = "value";

        database.onGet((k) -> {
            assertThat(k, is(key));
            return value;
        });

        try {
            assertThat(cache.get(key), is(value));
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        String key2 = "key2";
        database.onGet((k) -> {
            assertThat(k, is(key2));
            return null;
        });

        try {
            assertNull(cache.get(key2));
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }
    }

    @GradedTest("`get` should return from the cache with two consecutive calls")
    public void getReturnFromCacheOnConsecutiveGets() {
        String key = "key";
        String value = "value";

        database.onGet(k -> value);
        try {
            cache.get(key);
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        database.onGet(k -> fail("Database.get was unexpectedly called"));
        try {
            cache.get(key);
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }
    }

    @GradedTest("`put` should invalidate the cache if the key is present and the value is different")
    public void putCorrectlyInvalidateCacheWithDifferentValues() {
        String key = "key";
        String value = "value";
        database.onGet(k -> value);
        try {
            cache.get(key);
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        String value2 = "value2";
        cache.put(key, value2);

        AtomicBoolean execFlag = new AtomicBoolean(false);
        database.onGet(k -> {
            execFlag.set(true);
            return value2;
        });
        try {
            assertThat(cache.get(key), is(value2));
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }
        assertThat("Database.get was not executed", execFlag.get());
    }

    @GradedTest("`put` should not invalidate the cache if the key is present and the value is the same")
    public void putCorrectlyInvalidateCacheWithSameValue() {
        String key = "key";
        String value = "value";
        database.onGet(k -> value);
        try {
            cache.get(key);
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        String value2 = "value";
        cache.put(key, value2);

        database.onGet(k -> fail("Database.get was unexpectedly called"));
        try {
            assertThat(cache.get(key), is(value2));
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }
    }

    @GradedTest("`remove` should invalidate the cache")
    public void removeCorrectlyInvalidateCache() {
        String key = "key";
        String value = "value";
        database.onGet(k -> value);
        try {
            cache.get(key);
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        database.onRemove(k -> {});
        try {
            cache.remove(key);
        } catch(DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        AtomicBoolean execFlag = new AtomicBoolean(false);
        database.onGet(k -> {
            execFlag.set(true);
            return null;
        });
        try {
            assertNull(cache.get(key));
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }
        assertThat("Database.get was not executed", execFlag.get());
    }
}
