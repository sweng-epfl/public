package ch.epfl.sweng.testing.solutions.part1;

import ch.epfl.sweng.testing.part1.Currency;
import ch.epfl.sweng.testing.part1.Euros;
import ch.epfl.sweng.testing.part1.Francs;

class CounterfeitDetector {

    public boolean isValid(Currency currency) {
        return (currency.getValue() >= 0 && currency.getExpiration() >= 2018);
    }

    public boolean isFrancsValid(Francs francs) {
        return isValid(francs) && (francs.getAuthority().equals("CH") || francs.getAuthority().equals("SNB"));
    }

    public boolean isEurosValid(Euros euros) {
        return isValid(euros) && euros.getCreation() > 2004;
    }
}