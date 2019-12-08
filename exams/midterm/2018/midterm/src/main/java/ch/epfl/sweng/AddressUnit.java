package ch.epfl.sweng;

import java.util.List;

/**
 * You can modify this file freely.
 * An abstract class unifying different units of the address directory.
 * The directory implements a composite design pattern and forms a tree
 * with {@link CompositeAddressUnit} in the intermediate nodes and
 * {@link House} in the leafs.
 * The address tree starts with a root unit representing the country
 * (Seychelles) and goes down unit by unit ending in a house number.
 */

public abstract class AddressUnit {
    private final String name;

    public AddressUnit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Add an address into the address directory.
     * The address list must start with the name of the current address
     * unit (root) and list names of all the intermediate units up to
     * and including the leaf node (House).
     * @param address - the list of strings - address units names that
     *                  location of the house.
     * @param house - all the information related to the end-point, see
     *                {@link House} for the details
     * @throws InvalidAddressException if the address is not complete,
     *         the last name in the list does not correspond to the
     *         house number, or the address being added already exists.
     */
    public abstract void addAddress(List<String> address, House house)
        throws InvalidAddressException;
}
