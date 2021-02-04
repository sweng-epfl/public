package ch.epfl.sweng.mocks;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sweng.database.Database;
import ch.epfl.sweng.database.DatabaseException;

public final class DatabaseMock implements Database {

    @FunctionalInterface
    public interface PutFunction {
        void put(String key, String value);
    }

    @FunctionalInterface
    public interface RemoveFunction {
        void remove(String key) throws DatabaseException;
    }

    @FunctionalInterface
    public interface GetFunction {
        String get(String key) throws DatabaseException;
    }

    private Map<String, String> database = new HashMap<>();

    private PutFunction putImpl = (key, value) -> {
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        database.put(key, value);
    };

    private RemoveFunction removeImpl = (key) -> {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        if(!database.containsKey(key)) {
            throw new DatabaseException("key does not exist");
        }
        database.remove(key);
    };

    private GetFunction getImpl = (key) -> {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        return database.getOrDefault(key, null);
    };

    @Override
    public void put(String key, String value) {
        if(this.putImpl == null) {
            throw new IllegalStateException("missing mock function implementation");
        }
        this.putImpl.put(key, value);
    }

    @Override
    public void remove(String key) throws DatabaseException {
        if(this.removeImpl == null) {
            throw new IllegalStateException("missing mock function implementation");
        }
        this.removeImpl.remove(key);
    }

    @Override
    public String get(String key) throws DatabaseException {
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
