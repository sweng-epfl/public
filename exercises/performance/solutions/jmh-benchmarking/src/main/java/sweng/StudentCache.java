package sweng;

import java.util.HashMap;
import java.util.Map;

/**
 * Very basic cache  with infinite capacity for Student objects
 */
public class StudentCache implements Cache<Integer, Student>{

    private Map<Integer, Student> kvStore;

    public StudentCache() {
        kvStore = new HashMap<>();
    }

    @Override
    public boolean contains(Integer key) {
        return kvStore.containsKey(key);
    }

    @Override
    public void put(Integer key, Student value) {
        kvStore.put(key, value);
    }

    @Override
    public void clear() {
        kvStore = new HashMap<>();
    }

    @Override
    public Student get(Integer key) {
         return kvStore.get(key);
    }

}
