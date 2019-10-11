# Cover the code!

In these exercises, you will write tests and evaluate them using code coverage metrics.

Code coverage helps you determine how useful your tests are, since it tells you how much of the code is actually executed in tests.
While 100% code coverage is not always worth the effort, you should pay attention to it when adding new tests: if you expected a test to trigger a specific path in the code that no other test covers, but coverage hasn't changed, perhaps your test is not working as intended.


## Increasing coverage

In [`App.java`](src/main/java/App.java), we provide a `getEngineSpeed` method that tells a self-driving car how fast it should go given the current state of the car and its environment.

We also provide an existing test that achieves some line coverage.

Add tests to achieve 100% line coverage for the function.

Then add more tests to achieve 100% path coverage for the function.


## Finding dead code

Try to achieve 100% path coverage for the `getCarPrice` function. This is not possible; why? How would you fix the code so that there is no dead code?
