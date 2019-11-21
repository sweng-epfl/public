package ex10;

import java.util.HashMap;

public class KeyValueStoreImpl implements KeyValueStore {

    private HashMap<Integer, Integer> map;

    public KeyValueStoreImpl() {
        map = new HashMap<>();
    }

    @Override
    public void put(int key, int value) {
        map.put(key, value);
    }

    @Override
    public Integer get(int key) {
        return map.get(key);
    }

    @Override
    public int remove(int key) {
        if (map.containsKey(key)) {
            map.remove(key);
            return 1;
        }
        return 0;
    }
}
