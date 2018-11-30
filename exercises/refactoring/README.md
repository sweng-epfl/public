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

### Question 3
Consider a snippet of Java code, from a larger project. 
This snippet sets up a printer for mac OS X. 
Each piece of functionality performed by the snippet is preceeded by a comment. 

```java

/* Make sure the code only runs on mac os x */
boolean mrjVersionExists = System.getProperty("mrj.version") != null;
boolean osNameExists = System.getProperty("os.name").startsWith("Mac OS");

if ( !mrjVersionExists || !osNameExists)
{
  System.err.println("Not running on a Mac OS X system.");
  System.exit(1);
}

/* Setup log files */
int currentLoggingLevel = DEFAULT_LOG_LEVEL;

File errorFile = new File(ERROR_LOG_FILENAME);
File warningFile = new File(WARNING_LOG_FILENAME);
File debugFile = new File(DEBUG_LOG_FILENAME);

if (errorFile.exists()) currentLoggingLevel = DDLoggerInterface.LOG_ERROR;
if (warningFile.exists()) currentLoggingLevel = DDLoggerInterface.LOG_WARNING;
if (debugFile.exists()) currentLoggingLevel = DDLoggerInterface.LOG_DEBUG;

logger = new DDSimpleLogger(CANON_DEBUG_FILENAME, currentLoggingLevel, true, true);

/* Setup preferences, get default colour */
preferences = Preferences.userNodeForPackage(this.getClass());
int r = preferences.getInt(CURTAIN_R, 0);
int g = preferences.getInt(CURTAIN_G, 0);
int b = preferences.getInt(CURTAIN_B, 0);
int a = preferences.getInt(CURTAIN_A, 255);
currentColor = new Color(r,g,b,a);
```
Clearly, this monolithic snippet of code does too much. You decide to refactor it using the `Extract Method` pattern, to increase modularity. Which of the following function names seem appropriate for the new methods? 

- [ ] dieIfNotRunningOnMacOsX()
- [ ] setupLogfile()
- [ ] getPreferencesAndColour()
- [ ] setupPreferences()

Refactor the given code according to your choices. You do not need to add any new code, simply break the existing monolith into separate methods. 

### Question 4

I need to add a feature to code that smells. Which of the following is true?

- [ ] I should add the feature first and then refactor the entire codebase. Refactoring the entire codebase gives me a better idea of how to refactor 
- [ ] I should first refactor the program to make it easy to add the feature and only then add the feature

### Question 5

Consider the following pseudo code for `Boxes` and `RobotPackers`. Consider how you would refactor the code:

```java 
class Box {
   String material;
   ......
}

class RobotPacker {

   private bool isFragile(Box foo) {
      switch(foo.material) {
      case GLASS: //Complex exponential calculation for glass 
      case WOOD: //Complex quadratic calculation for wood
      case METAL: //Complex logarithmic calculation for metal 
   }
   ......
}
