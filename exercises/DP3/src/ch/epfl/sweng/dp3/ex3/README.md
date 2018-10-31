## Exercise 3

You have the `IntegerBox` class that corresponds to a conceptual "box" that holds several integers.

Currently, you provide a `getIntegers` method so somebody can see the integers in the box. `getIntegers`
returns an array of integers in the box. This might lead to your code being used as follows:

```Java
        int[] values = box.getIntegers();
        values[1] = 7818; 
```
allowing somebody to accidentally or perhaps maliciously trash the data in the box.

How can you allow the traversal of the integers held in the box without actually revealing the internals of the box?

Remove the `getIntegers` method from the `IntegerBox` and make it implement the `Iterable` interface, and
then execute the `main` method from the `Main` class.