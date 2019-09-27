enum CurrencyType { DOLLAR, EURO, POUND };

interface CurrencyService {
    double convert(double amount, CurrencyType from, CurrencyType to);
}

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

public class App {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(CurrencyType.EURO, 9001, new FixedCurrencyService());
        account.add(CurrencyType.DOLLAR, 100);
        account.remove(CurrencyType.POUND, 10);
        System.out.println("Balance: " + account.getAmount());
    }
}
