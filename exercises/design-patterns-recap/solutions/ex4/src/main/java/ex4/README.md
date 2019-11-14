# Exercise 4

Here, the design pattern to use is the Adapter.
Indeed, we would like to use a relational database (`DatabaseImpl`) without changing all the places in the code that use a key-value store (`KeyValueStoreImpl`).
The adapter allows us to adapt the api of the relational database (`DatabaseImpl`) in order to make it match the api expected in the code (`KeyValueStore`).
The adapter `DatabaseImplToKeyValueStoreAdapter` adapts the api of `DatabaseImpl` to the interface `KeyValueStore`. This way, all the places in the code that expect a `KeyValueStore` can receive a `DatabaseImplToKeyValueStoreAdapter` because it implements the interface `KeyValueStore`.