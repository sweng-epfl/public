# TDD / Part 3

The code contains the following errors, which makes it not possible the reach a 100% code coverage :

- The method `canGrow()` of the Panda class returns true if the age is bigger than 20.
- The method `update()` of Bamboo increases the length if the age is odd instead of even.
- The method `equals()` of Position is not comparing the right attributes
- In the method `addEntity()` of Environment, the entities are actually never added to the attribute of the class, but always to an inner variable of the function
