# Identifying refactoring techniques - Solutions

## Solution 4.1

The two methods that were used are:

- Extract method
- Substitute algorithm

**Explanation:** The algorithm used here didn't really make sense (why would you compute a minimum if the name of the method was `getMostValuableMember` ?).
The method was pretty long so extracting a part of it into a separate method makes it more concise. It is also easier to spot a potential bug as one can test the `sumTransactions` method separately.

## Solution 4.2

Here a total of 4 methods were used:

- Extract variable (replace query by temp)
- Rename method
- Extract class (replace array by object)
- Replace magic number with symbolic constant

**Explanation:** The `getCurrentlyUsedVersionNumber` method was being called many times, especially inside the for-loop. Imagine the returned value requires performing expensive computations or needs to fetch the information from a distant server. Getting the value only once and storing it inside a variable reduces cost.
The name of the mentioned method was clearly too long, and was renamed to something simpler that describes it's role just as well.
The `VERSIONS` array stored the information about each version in arrays of `int`s. This hurts the understandability of the code "_what was stored in [1] again ??_". A `Version` object with explicit methods such as `setStatus` can be extracted.
Finally, the magic numbers (i.e. `0x3`) aren't meaningfull and can easily be misstyped. They have been replaced by constants in an `enum`.
