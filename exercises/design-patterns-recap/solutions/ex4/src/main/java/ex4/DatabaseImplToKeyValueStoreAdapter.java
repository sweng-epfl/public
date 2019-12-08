package ex4;

public class DatabaseImplToKeyValueStoreAdapter implements KeyValueStore {

    private DatabaseImpl database;
    private static final String tableName = "SINGLE_TABLE";


    public DatabaseImplToKeyValueStoreAdapter(DatabaseImpl database) {
        this.database = database;
        database.createTable(tableName);
    }

    @Override
    public void put(int key, int value) {
        database.insert(tableName, Integer.toString(key), Integer.toString(value));
    }

    @Override
    public Integer get(int key) {
        String result = database.retrieve(tableName, Integer.toString(key));
        return result == null? null: Integer.valueOf(result);
    }

    @Override
    public int remove(int key) {
        boolean result = database.delete(tableName, Integer.toString(key));
        return result? 1: 0;
    }
}
