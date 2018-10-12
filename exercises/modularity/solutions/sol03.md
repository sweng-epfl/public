The main tasks in this problem are:

- Conversion between different currencies
- Adding and removing the amount, checking that the value doesn't go below 0

The main one being conversion, and since it is duplicated in the code, we can be tempted to simply move the lines to a separate function.  However, it becomes clear that this is not a scalable solution when you consider that there can exist dozens or even hundreds of different currencies.

A cleaner, more modular solution is to simply have a base currency (for instance, the US dollar) and to have the exchange rates with all other currencies relative to that currency. We can then use those rates to calculate the exchange rates between all different currencies.

```java
public class BankAccount {
    public enum CurrencyType {
        DOLLAR, EURO, POUND;
    }

    public BankAccount(CurrencyType currencyType, double ammount) {
        this.currencyType = currencyType;
        this.amount = ammount;
    }
    private CurrencyType currencyType;
    private double amount;

    double values[] = {1.0, 1.15, 1.31};

    public double convertCurrency(CurrencyType startType, double ammount, CurrencyType endType) {
        return (values[startType.ordinal()] / values[endType.ordinal()]) * ammount;

    }

    public boolean add(CurrencyType addedType, double addedAmmount) {
        double inCurrency = convertCurrency(addedType, addedAmmount, currencyType);
        amount += inCurrency;
        return true;
    }

    public boolean remove(CurrencyType removedType, double removedAmmount) {
        double inCurrency = convertCurrency(removedType, removedAmmount, currencyType);
        if(inCurrency > amount) {
            return false;
        }
        amount -= inCurrency;
        return true;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public double getAmount() {
        return amount;
    }

};
```
