package ch.epfl.sweng.mocks;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sweng.Student;
import ch.epfl.sweng.StudentDatabase;
import ch.epfl.sweng.database.DatabaseException;

public final class StudentDatabaseMock implements StudentDatabase {

    @FunctionalInterface
    public interface PutFunction {
        void put(Student student) throws DatabaseException;
    }

    @FunctionalInterface
    public interface RemoveFunction {
        void remove(String sciper) throws DatabaseException;
    }

    @FunctionalInterface
    public interface GetFunction {
        Student get(String sciper) throws DatabaseException;
    }

    private Map<String, Student> database = new HashMap<>();

    private PutFunction putImpl = student -> {
        if(student == null) {
            throw new IllegalArgumentException();
        }
        database.put(student.sciper, student);
    };

    private RemoveFunction removeImpl = sciper -> {
        if(sciper == null) {
            throw new IllegalArgumentException();
        }
        if(!database.containsKey(sciper)) {
            throw new DatabaseException("sciper does not exist");
        }
        database.remove(sciper);
    };

    private GetFunction getImpl = sciper -> {
        if(sciper == null) {
            throw new IllegalArgumentException();
        }
        return database.getOrDefault(sciper, null);
    };

    @Override
    public void put(Student student) throws DatabaseException {
        if(this.putImpl == null) {
            throw new IllegalStateException("missing mock function implementation");
        }
        this.putImpl.put(student);
    }

    @Override
    public void remove(String key) throws DatabaseException {
        if(this.removeImpl == null) {
            throw new IllegalStateException("missing mock function implementation");
        }
        this.removeImpl.remove(key);
    }

    @Override
    public Student get(String key) throws DatabaseException {
        if(this.getImpl == null) {
            throw new IllegalStateException("missing mock function implementation");
        }
        return this.getImpl.get(key);
    }

    public void onPut(PutFunction putImpl) {
        this.putImpl = putImpl;
    }

    public void onRemove(RemoveFunction removeImpl) {
        this.removeImpl = removeImpl;
    }

    public void onGet(GetFunction getImpl) {
        this.getImpl = getImpl;
    }

}
