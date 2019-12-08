package ch.epfl.sweng;

import java.util.List;

/**
 * You can modify this file freely.
 * The leaf node in the address hierarchy. House stores all the
 * information for a smallest addressable unit.
 * It may contain multiple people ({@link #getPopulation()}) and
 * have a private land line ({@link #getLandLinePhone()}).
 */

public class House extends AddressUnit {
    private final int population;
    private final String landLinePhone;

    public House(String name, int population, String landLinePhone) {
        super(name);
        this.population = population;
        this.landLinePhone = landLinePhone;
    }

    public String getLandLinePhone() {
        return landLinePhone;
    }

    public int getPopulation() {
        return population;
    }

    /**
     * Adding address into a does not make sense in the current address
     * system. We do not support floors/buildings/P.O boxes/room numbers.
     * Our tests assume that a house is the least addressable unit.
     */
    @Override
    public void addAddress(List<String> address, House house)
        throws InvalidAddressException {
        throw new InvalidAddressException("The house can not have sub units");
    }
}

