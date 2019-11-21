package ex10;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KeyValueStoreRemote extends Remote {

    /** Inserts (key, value) pair in the store. If some pair with the same key already exists in the store, it is
     * removed and the (key, value) pair is inserted).
     */
    public void put(int key, int value) throws RemoteException;

    /** Returns the associated value for the given key or null in case no such association exists. */
    public Integer get(int key) throws RemoteException;

    /** If a (key, _) pair is in the store and it returns 1. Otherwise, returns 0. */
    public int remove(int key) throws RemoteException;
}
