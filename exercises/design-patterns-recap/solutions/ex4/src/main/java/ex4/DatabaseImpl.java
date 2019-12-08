package ex4;

import java.util.HashMap;
import java.util.Map;

public class DatabaseImpl {

    private Map<String, Map<String, String>> database;

    public DatabaseImpl() {
        database = new HashMap<>();
    }

    public void createTable(String tableName) {
        database.put(tableName, new HashMap<String, String>());
    }

    public void insert(String tableName, String key, String data) {
        System.out.print("This is a DatabaseIml insert.");
        Map<String, String> table = database.get(tableName);
        table.put(key, data);
    }

    public boolean delete(String tableName, String key) {
        System.out.print("This is a DatabaseIml delete.");
        Map<String, String> table = database.get(tableName);
        String result = table.remove(key);
        return result == null? false: true;
    }

    public String retrieve(String tableName, String key) {
        System.out.print("This is a DatabaseIml retrieve.");
        Map<String, String> table = database.get(tableName);
        return table.get(key);
    }
}
