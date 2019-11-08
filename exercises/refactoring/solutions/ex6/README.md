# Refactoring with IntelliJ - Solutions

If we only stick to the issues mentioned in the comments, we can do the following:

| What we want to modify | Action | Option to select |
| :---: | :---: | :---: |
| Change the class name | Right-click on `BadClassName` | Refactor > Rename... |
| Change the attribute name | Right-click on `badAttributeName` | Refactor > Rename... |
| Move the common constant and method to a superclass / interface | Right-click anywhere in the file | Refactor > Extract > Superclass / Interface and select both the constant and the method |
| Break down the long method into smaller parts | Right-click inside block of code to extract, previously selected with mouse | Refactor > Extract > Method and give it some explicit name |
| Remove useless method | Right-click on `getLengthOfOurAttribute()` | Refactor > Inline Method and select the _Inline all and remove the method_ option |
| Remove duplicate code | Hover over the underlined line of code | Extract method from duplicate code and select an explicit name |
