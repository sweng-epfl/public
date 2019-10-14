# Specifications

In these exercises, you will write informal and formal specifications.


## Hash table

A [hash table](https://en.wikipedia.org/wiki/Hash_table) is a data structure that associates keys with values using the keys' hashes to efficiently find the value associated with a key.

Write a specification, in informal language, for the following operations on a hash table:

- Adding a key/value pair
- Replacing the value associated with a key
- Removing a key and its value
- Clearing the entire table


## Network bridge

A [network bridge](https://en.wikipedia.org/wiki/Bridging_(networking)) is a device that is connected to many network hosts, and forwards packets to the right host.
It does this by remembering incoming packets' MAC addresses and forwarding them to the right output.

Write a specification, in informal language, for a bridge.


## Formal specification

Write a formal specification, i.e. a logical formula, for a function with the following signature based on its documentation:

```java
/** Returns the smallest value in the given list that is greater than zero.
    If there is no such value, or if the list is null, returns 0. */
public static int positiveMinimum(List<Integer> values) { ... }
```
