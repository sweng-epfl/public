# Exercise 4

You have been using a key-value store (`KeyValueStoreImpl`) in your application. 
You realize that you might need to use transactions in the future,
so you decide to store your data in a relational database (`DatabaseImpl`).

How would you leverage `DatabaseImpl` but without modifying your application, meaning you can keep using
the `KeyValueStore` interface? What design pattern would you use?

Create a new class if needed, and instantiate it in the `StoreTest` class so you pass the test.
