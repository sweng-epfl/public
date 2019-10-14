# Solutions: Cover the code

## Increasing coverage

To get 100% statement coverage, you will need the following (not necessarily in the same test):

- a negative distance
- a current speed greater than 200
- a zero distance (boom! accident!)
- a paranoid user, with a speed between 100 and 200

To get 100% path coverage, take a notepad and write down the branches in the code, then make one test per path in your diagram.


## Finding dead code

The problem is that the `LARGE` branch of the `switch(discount)` in case `isFrequentCustomer` is `false` cannot be reached, since there is already an if branch at the start of the method handling that case.

To fix the code, replace the contents of the switch branch by the if branch body, and remove the if.
