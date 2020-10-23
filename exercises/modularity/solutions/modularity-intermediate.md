# Solution: Modularity - Intermediate

The problems in the code are:

- The `CurrencyType` does not logically belong to `BankAccount`, and should thus be moved to the top level.
- The code to convert currencies is duplicated, and should be consolidated in one place.
- The code to convert currencies has duplication within itself, since e.g. converting euros to dollars can be computed from the conversion of dollars to euros.
- The code to convert currencies does not logically belong to `BankAccount`, but to a currency conversion service, since currency rates are not fixed constants.

One can thus define a currency service:

```java
interface CurrencyService {
    double convert(double amount, CurrencyType from, CurrencyType to);
}
```

And implement it by de-duplicating the existing code:

```java
class FixedCurrencyService implements CurrencyService {
    private static double getMultiplier(CurrencyType from, CurrencyType to) {
        if (from == to) {
            return 1;
        }
        if (from == CurrencyType.DOLLAR && to == CurrencyType.EURO) {
            return 0.87;
        }
        if (from == CurrencyType.DOLLAR && to == CurrencyType.POUND) {
            return 0.76;
        }
        if (from == CurrencyType.POUND && to == CurrencyType.EURO) {
            return 1.14;
        }
        return 1 / getMultiplier(to, from);
    }

    public double convert(double amount, CurrencyType from, CurrencyType to) {
        return getMultiplier(from, to) * amount;
    }
}
```

The BankAccount class then has fewer responsibilities:

```java
class BankAccount {
    public BankAccount(CurrencyType currencyType, double amount, CurrencyService currencyService) {
        this.currencyType = currencyType;
        this.amount = amount;
        this.currencyService = currencyService;
    }

    private CurrencyType currencyType;
    private double amount;
    private CurrencyService currencyService;

    public void add(CurrencyType addedType, double addedAmount) {
        if (addedAmount < 0) {
            throw new IllegalArgumentException("Added amount cannot be negative.");
        }

        amount += currencyService.convert(addedAmount, addedType, currencyType);
    }

    public boolean remove(CurrencyType removedType, double removedAmount) {
        if (removedAmount < 0) {
            throw new IllegalArgumentException("Removed amount cannot be negative.");
        }

        double amountToRemove = currencyService.convert(removedAmount, removedType, currencyType);
        if(amountToRemove > amount) {
            return false;
        }
        amount -= amountToRemove;
        return true;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public double getAmount() {
        return amount;
    }
}
```

And the main method can use the classes like this:

```java
BankAccount account = new BankAccount(CurrencyType.EURO, 9001, new FixedCurrencyService());
account.add(CurrencyType.DOLLAR, 100);
account.remove(CurrencyType.POUND, 10);
System.out.println("Balance: " + account.getAmount());
```
