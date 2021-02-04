package ch.epfl.sweng;

import org.junit.jupiter.api.BeforeEach;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import ch.epfl.sweng.database.DatabaseException;
import ch.epfl.sweng.mocks.DatabaseMock;
import grading.GradedCategory;
import grading.GradedTest;

import static ch.epfl.sweng.utils.AssertUtils.assertHandlesNullArgument;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@GradedCategory("Part 2.1: Implementing the StudentDatabaseAdapter")
public final class StudentDatabaseAdapterTest {

    private DatabaseMock database;
    private StudentDatabase adapter;

    @BeforeEach
    public void init() {
        database = new DatabaseMock();
        adapter = new StudentDatabaseAdapter(database);
    }

    @GradedTest("`constructor` should throw an IllegalArgumentException or a NullPointerException if passed a null argument")
    public void constructorThrowOnNullArgument() {
        assertHandlesNullArgument(() -> new StudentDatabaseAdapter(null));
    }

    @GradedTest("`put` should throw an IllegalArgumentException or a NullPointerException if passed a null argument")
    public void putThrowOnNullArgument() {
        assertHandlesNullArgument(() -> {
            try {
                adapter.put(null);
            } catch (DatabaseException e) {
                fail("An unexpected DatabaseException was thrown");
            }
        });
    }

    @GradedTest("`put` should use the student SCIPER as database key")
    public void putUseSciperAsKey() {
        String sciper = "278563";
        Student student = new Student("John", sciper, "IC");
        final AtomicBoolean execFlag = new AtomicBoolean(false);
        database.onPut((key, value) -> {
            assertThat(key, is(sciper));
            execFlag.set(true);
        });
        try {
            adapter.put(student);
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }
        assertThat("Database.put was not executed", execFlag.get());
    }

    @GradedTest("`remove` should throw an IllegalArgumentException or a NullPointerException if passed a null argument")
    public void removeThrowOnNullArgument() {
        assertHandlesNullArgument(() -> {
            try {
                adapter.remove(null);
            } catch (DatabaseException e) {
                fail("An unexpected DatabaseException was thrown");
            }
        });
    }

    @GradedTest("`remove` should throw a DatabaseException if the key is not present in the database")
    public void removeThrowWithAbsentKey() {
        assertThrows(DatabaseException.class, () -> adapter.remove("key"));
    }

    @GradedTest("`remove` should call the database with the correct argument")
    public void removeCallDatabaseWithCorrectArgument() {
        String sciper = "513624";
        final AtomicBoolean execFlag = new AtomicBoolean(false);
        database.onRemove((key) -> {
            assertThat(key, is(sciper));
            execFlag.set(true);
        });
        try {
            adapter.remove(sciper);
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }
        assertThat("Database.remove was not executed", execFlag.get());
    }

    @GradedTest("`get` should throw an IllegalArgumentException or a NullPointerException if passed a null argument")
    public void getThrowOnNullArgument() {
        assertHandlesNullArgument(() -> {
            try {
                adapter.get(null);
            } catch (DatabaseException e) {
                fail("An unexpected DatabaseException was thrown");
            }
        });
    }

    @GradedTest("`get` should return null if the student is not in the database")
    public void getReturnNullOnMissingStudent() {
        database.onGet((key) -> null);

        try {
            assertNull(adapter.get("123456"));
        } catch(DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }
    }

    @GradedTest("`get` should throw a DatabaseException if the database content is invalid")
    public void getThrowOnInvalidData() {
        database.onGet(key -> "");
        assertThrows(DatabaseException.class, () -> adapter.get("123456"));

        database.onGet(key -> "aflkjn2938rhfkaésdjbf9p8q43rkéjhalskdjhf");
        assertThrows(DatabaseException.class, () -> adapter.get("123456"));
    }

    @GradedTest("`put` and `get` should serialize and deserialize a student correctly")
    public void putAndGetShouldConvertStudentToString() {
        Student student;
        student = new Student("John", "639845", "IC");
        try {
            adapter.put(student);
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        try {
            Student result = adapter.get(student.sciper);
            assertThat(result, is(student));
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        student = new Student("John", "639845", "IC'} I've escaped!!!");
        try {
            adapter.put(student);
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        try {
            Student result = adapter.get(student.sciper);
            assertThat(result, is(student));
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        student = new Student("John', sciper='000000', faculty='GC", "639845", "IC");
        try {
            adapter.put(student);
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        try {
            Student result = adapter.get(student.sciper);
            assertThat(result, is(student));
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        student = new Student("", "", "");
        try {
            adapter.put(student);
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }

        try {
            Student result = adapter.get(student.sciper);
            assertThat(result, is(student));
        } catch (DatabaseException e) {
            fail("An unexpected DatabaseException was thrown");
        }
    }
}
