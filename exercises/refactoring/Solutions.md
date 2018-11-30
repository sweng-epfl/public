# Solutions

### Question 1

- [n] Inline method
- [n] Extract class
- [y] Extract method
- [y] Substitute algorithm

**Explanation for students**: No method was inlined, thus the 1st answer is incorrect; in fact, the opposite happened, a method was extracted, thus the 3rd answer is correct. No class was extracted, thus the 2nd answer is incorrect (the code does even mention a class). The algorithm to pick the most valuable member was changed and improved, thus the 4th answer is correct.

### Question 2

- [y] In the original example, a caller could have directly called methods of Vector, potentially breaking the code contracts of Stack (for example, by adding an element in the middle of the Vector). Using delegation, an external caller cannot access the Vector member of Stack (if it is private, as it should be).
- [n] In the original example, the isEmpty() method of the Stack always calls the isEmpty() method of the Vector. The isEmpty() method of the Stack should be computed differently than that of the Vector, which is only possible using delegation.
- [n] The Stack has two additional methods, push() and pop(). Therefore, Stack cannot subclass Vector.

**Explanation for students**: The fact that calling methods of Vector breaks the contract of Stack means that the inheritance breaks LSP. Replacing the inheritance with delegation indeed solves this issue, as the underlying Vector that is used to implement Stack can be hidden from public access. This makes the code of Stack a little longer, since methods are not automatically inherited, and therefore each relevant method in Vector must be explicitely declared, like the isEmpty() method on the slide. The methods can just delegate to their counterparts in Vector.
      The isEmpty() method is not different in Stack and Vector. This is why in the example on the slide, the refactored isEmpty() method in Stack actually delegates responsability to its counterpart in Vector.
      The fact that Stack has extra methods is not a problem for inheritance; subclasses can add functionality to superclasses. The problem is the fact that Stack should *hide* some functions from the superclass, such as the add(int index, E element) method that allows inserting an element at an arbitrary position.
      
### Question 3

- [y] dieIfNotRunningOnMacOsX()
- [y] setupLogfile()
- [n] getPreferencesAndColour()
- [y] setupPreferences()

**Explanation for students**:
Method names are extremely important; a good method name must convey exactly what a method does. Further a method must not do too much, hence it is better to have two separate methods for `setupPreferences` and `getColor` as opposed to a single method. 

Example refactor:
```java
private void dieIfNotRunningOnMacOsX() {
  boolean mrjVersionExists = System.getProperty("mrj.version") != null;
  boolean osNameExists = System.getProperty("os.name").startsWith("Mac OS");
  
  if ( !mrjVersionExists || !osNameExists) {
    System.err.println("Not running on a Mac OS X system.");
    System.exit(1);
  }
}

private void setupLogfile() {
  int currentLoggingLevel = DEFAULT_LOG_LEVEL;
  
  File errorFile = new File(ERROR_LOG_FILENAME);
  File warningFile = new File(WARNING_LOG_FILENAME);
  File debugFile = new File(DEBUG_LOG_FILENAME);
  
  if (errorFile.exists()) currentLoggingLevel = DDLoggerInterface.LOG_ERROR;
  if (warningFile.exists()) currentLoggingLevel = DDLoggerInterface.LOG_WARNING;
  if (debugFile.exists()) currentLoggingLevel = DDLoggerInterface.LOG_DEBUG;
  
  logger = new DDSimpleLogger(CANON_DEBUG_FILENAME, currentLoggingLevel, true, true);
}

private void setupPreferences() {
  preferences = Preferences.userNodeForPackage(this.getClass());
}

private void getColor(Preferences preferences) {
  int r = preferences.getInt(CURTAIN_R, 0);
  int g = preferences.getInt(CURTAIN_G, 0);
  int b = preferences.getInt(CURTAIN_B, 0);
  int a = preferences.getInt(CURTAIN_A, 255);
  currentColor = new Color(r,g,b,a);
}
```



### Question 4

- [n] I should add the feature first and then refactor the codebase + the feature. Refactoring the entire codebase gives me a better idea of how to refactor 
- [y] I should first refactor the program to make it easy to add the feature and only then add the feature

**Explanation for students**:
It is always better to add a new feature to clean, refactored code. This ensures that the implementation of the feature is less likely to contain bugs and also less likely to break other parts of the code. 
The first statement is false. Ideally, the new feature should be decoupled and so refactoring should not depend on it. 

### Question 5

```java 

class Box {
   Material material;
   public bool isFragile() {
      return material.isFragile();
   } 
}

public interface Material {
  public bool isFragile();
}

class GlassMaterial implements Material {
  @Override
  public bool isFragile() {
     //Complex exponential calculation for glass 
  }
}

class WoodMaterial implements Material {
  @Override
  public bool isFragile() {
     //Complex quadratic calculation for wood
  }
}

class MetalMaterial implements Material {
  @Override
  public bool isFragile() {
     //Complex logarithmic calculation for metal 
  }
}
  
class RobotPacker {

   private bool isFragile(Box foo) {
      return foo.isFragile()
   }
   ......
}
```

**Explanation for students**: There is no single correct answer here. The reasoning behind the provided answer is the following: 
- The functionality to compute whether the box is fragile belongs in the `Box` class, not in the `RobotPacker` class
- Instead of having a single function contain the code for all the complex calculations, it is better to modularise this and   break it into separate functions
- Each complex calculation is a property of the material, hence it belongs within a separate class for the material, instead of individual methods for `exponential`,`logarithmic` and `quadratic` functions. However, one can reasonably argue for further modularising the calculations behind a `Calculations` interface with separate classes for each type. 
