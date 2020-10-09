# Solution - [Covariance and contravariance](https://en.wikipedia.org/wiki/Covariance_and_contravariance_(computer_science))
1. Operation is completely legitimate, because we know that whatever element is in `l1`, it extends `Student`, so we can refer to it as to a `Student`.
2. Operation is dangerous, because `l2` may contain a super class of `Student`, e.g. `Object`, that does not implement `Student`, so `s2` may not respond correctly when we try to call methods of `Student` on it.
3. Operation is also dangerous, because `l1` may contain objects that are more specific than a `Student`, e.g. `SwengStudent`. In such a case, in another place the code may expect to get `SwengStudent`s out of the list, and it will break when they pull out the `Student` that we added here.
4. Operation is safe, because the type guarantees that `l2` contains objects of type `Student` or lower in the inheritance chain. Hence, whatever code works with `l2` will not expect anything more specific than a `Student`, and we can safely put a `Student` there knowing that it will fullfill the expectations.

