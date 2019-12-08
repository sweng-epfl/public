# Solutions: Specifications

We provide here examples of specifications, but they are not the only possible solutions.


## Hash table

The table is a set of (key, value) pairs.

- Adding a `key` and a `value` adds the `(key, value)` element to the set
- Replacing the `value` associated with a `key` requires the set to contain a pair whose first item is `key`; it removes this pair, then adds the `(key, value)` pair
- Removing a `key` requires the set to contain a pair whose first item is `key`; it removes this pair
- Clearing the entire table removes everything from the set


## Network bridge

A bridge's state is a table mapping MAC addresses to "ports" of the bridge (a port is where a cable is plugged in).

When the bridge receives a packet:
- It parses the packet's source and destination MAC addresses
- It replaces the value associated with the source address in its state with the port the packet came from
- It looks up the destination address in its state:
  - If a port is found, the packet is forwarded there
  - Otherwise, the packet is broadcast everywhere except the port it came from


## Formal specification

```java
public static int positiveMinimum(List<Integer> values) { ... }
// if values == null then result == 0
//                   else if exists(x in values: x > 0) then result == min(x in values: x > 0)
//                                                      else result == 0
```
