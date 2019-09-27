# Solutions: Interfaces - Basics


## Collections

`ArrayList`, `HashSet`, and `ArrayDeque` are all `Iterable`s, but that's it. Only one of them is a set (unordered, no duplicates), none of them are queues (first-in-first-out), an only one is a list (can have duplicate elements and supports insertion/removal/query at any index).

`ArrayList`, `Vector`, and `LinkedList` are both `Iterable`s and `List`s. They are not  `Set`s since they have an order and allow duplicate elements, and certainly not `Queue`s.

`ConcurrentLinkedQueue`, `AbstractSet`, and `Properties` are all `Iterable`s, but that's it.

You can see in this exercise how powerful the `Iterable` interface is: every collection naturally implements it!


## Files

`BufferedReader`, `OutputStreamWriter` and `GZIPInputStream` can all be conceptually "closed", thus they should implement `Closeable`. But not all of them can receive input or output, thus they are not all `InputStream`s, `Readable`s or `Flushable`s (flushing is only doable on something you can write to).

`PrintStream`, `OutputStreamWriter` and `FilterWriter` all represent something you can write to, which mean they should be `Flushable`s and `Closeable`s but not `InputStream`s or `Readable`s.


## Various

`Integer`, `Path` and `ZonedDateTime` are all things you can compare to other things of their category. They can also all logically be serialized. Thus they should implement `Comparable` and `Serializable` , but not `Iterable` (how would one iterate an integer or a date?) or `Watchable` (how would one watch an integer?).

`BigDecimal`, `ThaiBuddhistCalendar` and `java.util.regex.Pattern` do not have much in common, beyond the fact that they can all be represented in some way in a binary form, i.e. `Serializable`.

