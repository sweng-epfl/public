# Refactoring

### Question 1

One of your colleagues decided to refactor a code smell, and transformed the following code

```java
public static int getMostValuableMemberId(List<Member> members) {
  int result = 0;
  int value = 0;
  int min = 0;
  for (Member m : members) {
    for (Transaction t : member.transactions) {
      value -= t.value;
    }
    if (min < value) {
      continue;
    } else {
      min = value;
      result = m.id;
    }
    value = 0;
  }
  return result;
}
```

into the following better version:

```java
public static int getMostValuableMemberId(List<Member> members) {
  int resultId = 0;
  int currentMax = 0;
  for (Member m : members) {
    int sum = sumTransactions(member);
      if (currentMax < sum) {
        currentMax = sum;
        resultId = member.id;
      }
    }
  }
  return resultId;
}

private static int sumTransactions(Member member) {
  int sum = 0;
  for (Transaction transaction : member.transactions) {
    sum = sum + transaction.value;
  }
  return sum;
}
```

Indicate below which refactoring technique(s) your colleague used?

- [ ] Inline method
- [ ] Extract class
- [ ] Extract method
- [ ] Substitute algorithm

### Question 2

In the video lecture on Refactoring Inheritance (at 8m55s) you saw an example of replacing Inheritance with Delegation. How does the refactoring solve the problem in the example? Choose all that apply.

- [ ] In the original example, a caller could have directly called methods of Vector, potentially breaking the code contracts of Stack (for example, by adding an element in the middle of the Vector). Using delegation, an external caller cannot access the Vector member of Stack (if it is private, as it should be).
- [ ] In the original example, the isEmpty() method of the Stack always calls the isEmpty() method of the Vector. The isEmpty() method of the Stack should be computed differently than that of the Vector, which is only possible using delegation.
- [ ] The Stack has two additional methods, push() and pop(). Therefore, Stack cannot subclass Vector.