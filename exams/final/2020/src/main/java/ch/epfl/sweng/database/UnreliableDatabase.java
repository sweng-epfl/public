package ch.epfl.sweng.database;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * An implementation of the {@link Database} interface. This class tries to emulate a real database
 * by simulating random failures and processing delay.
 * Please look at the implementation of each functions to see which problems can occur with this
 * implementation.
 *
 * !!!!!!!!!!!!!!!!!!!!!!
 * DO NOT TOUCH THIS FILE
 * !!!!!!!!!!!!!!!!!!!!!!
 */
public final class UnreliableDatabase implements Database {

    private Map<String, String> database;
    private Random rng;

    /**
     * Constructs a new empty database.
     */
    public UnreliableDatabase() {
        this.database = new HashMap<>();
        this.rng = new Random(0L);
    }

    @Override
    public void put(String key, String value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        database.put(key, value);
    }

    @Override
    public void remove(String key) throws DatabaseException {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (!database.containsKey(key)) {
            throw new DatabaseException("key does not exist");
        }
        database.remove(key);
    }

    @Override
    public String get(String key) throws DatabaseException {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        // Simulates processing delay by waiting for 2s.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new DatabaseException("unexpected error in get", e);
        }

        // Simulates a random failure with probability 50%.
        if (this.rng.nextBoolean()) {
            throw new DatabaseException("unexpected error in get");
        }

        return database.getOrDefault(key, null);
    }
}
