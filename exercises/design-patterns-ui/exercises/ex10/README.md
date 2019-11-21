# DP - UI -- Ex10
You implemented a service that uses a key-value store.
You decide you want to move the key-value store to a remote server but without changing the `KeyValueStore` interface
the client is currently using. 

Use the [Java Remote Method Invocation (RMI)](https://docs.oracle.com/javase/tutorial/rmi/index.html) system,
so that you actually call a key-value store that resides in a (possibly) different machine. What is
the underlying design pattern?

Hint: You might have something like the following:

```Java
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
```


