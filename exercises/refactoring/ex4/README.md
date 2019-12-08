# Identifying refactoring techniques

In this exercise we provide you with _before refactoring_ and _after refactoring_ versions of a code. Your task is to find out which refactoring techniques were used during the refactoring process.

## Exercise 4.1

Before:

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

After:

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

## Exercise 4.2

Before:

```java
if (getCurrentlyUsedVersionNumber() != oldVersionNumber)
  oldVersionNumber = getCurrentlyUsedVersionNumber();
for (int i = 0; i < getCurrentlyUsedVersionNumber() - 1; ++i) {
  VERSIONS[i][0] = 0x3;        // setting status (stored in [0]) to 0x3 (DEPRECATED)
  VERSIONS[i][1] = 0b11000111; // setting corresponding flags (stored in [1])
}
```

After:

```java
int versionNumber = getVersion();
if (versionNumber != oldVersionNumber)
  oldVersionNumber = versionNumber;
for (int i = 0; i < versionNumber - 1; ++i) {
  VERSIONS[i].setStatus(Status.DEPRECATED);
  VERSIONS[i].setFlags(Flags.DEPRECATED);
}
```
