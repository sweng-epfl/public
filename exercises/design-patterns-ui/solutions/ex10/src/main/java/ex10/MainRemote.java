package ex10;

import java.rmi.Naming;

public class MainRemote {

    public static void main(String[] args) {
        KeyValueStoreRemote store;

        try {
            store = (KeyValueStoreRemote) Naming.lookup("//localhost/kvstore");

            store.put(3, 1993);
            System.out.println(store.get(3));

            store.put(9, 1873);
            System.out.println(store.get(9));

            store.remove(3);
            System.out.println(store.get(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
