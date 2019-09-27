# Solution: Modularity - Intermediate

The problems in the code are:

- The `CurrencyType` does not logically belong to `BankAccount`, and should thus be moved to the top level.
- The code to convert currencies is duplicated, and should be consolidated in one place.
- The code to convert currencies has duplication within itself, since e.g. converting euros to dollars can be computed from the conversion of dollars to euros.
- The code to convert currencies does not logically belong to `BankAccount`, but to a currency conversion service, since currency rates are not fixed constants.
