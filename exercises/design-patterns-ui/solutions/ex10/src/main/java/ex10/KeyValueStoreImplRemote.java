package ex10;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class KeyValueStoreImplRemote extends UnicastRemoteObject implements KeyValueStoreRemote {

    private HashMap<Integer, Integer> map;

    public KeyValueStoreImplRemote() throws RemoteException {
        map = new HashMap<>();
    }

    @Override
    public void put(int key, int value) {
        System.out.println("This is the remote implementation's put");
        map.put(key, value);
    }

    @Override
    public Integer get(int key) {
        System.out.println("This is the remote implementation's get");
        return map.get(key);
    }

    @Override
    public int remove(int key) {
        System.out.println("This is the remote implementation's remove");
        if (map.containsKey(key)) {
            map.remove(key);
            return 1;
        }
        return 0;
    }
}
