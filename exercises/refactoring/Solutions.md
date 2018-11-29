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