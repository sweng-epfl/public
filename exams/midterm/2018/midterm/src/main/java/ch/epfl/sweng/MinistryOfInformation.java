package ch.epfl.sweng;

import java.util.List;

/**
 * Do not modify the method signatures in this class.
 * Do not remove methods from the class.
 * You can(and should) change the implementation of the methods.
 *
 * A principal interface class for our information system.
 * MinistryOfInformation uses the address directory to conveniently
 * query and modify the information base about the country population.
 * The interface of this class is very important, as we have build many
 * services around it, and you should under no circumstance change it.
 */

public class MinistryOfInformation {
    private final AddressUnit directory;

    /**
     * Initialize the instance with the root unit(dierctory) for the
     * address tree. The root unit name must be the country name ("Seychelles").
     */
    public MinistryOfInformation(AddressUnit directory) {
        this.directory = directory;
    }

    /**
     * Looks up for a house located by the given address.
     * @param address - the full address of the house, starting with the country
     *                  name and ending with the house number.
     * @returns a house located by the given address.
     * @throws - InvalidAddressException - if the address is incomplete
     *         (missing some of the elements, like house number,
     *          country name or others)
     *         - AddressNotFoundException - if there is no house registered
     *         in the system by the given address.
     */
    public House findHouse(List<String> address)
        throws InvalidAddressException, AddressNotFoundException {
        // Put your code here.
        throw new RuntimeException("Not implemented");
    }

    /**
     * Inserts a new house by the given address into the directory.
     * @param address - the full address of the house, starting with the
     *                  name and ending by the house number.
     * @param house - the data about the new house. It's name must be equal to
     *                the last element of the address.
     * @throws - InvalidAddressException if the there is already a house by the
     *         given address or if the address is malformed
     *         (empty, or missing parts);
     */
    public void addAddress(List<String> address, House house)
        throws InvalidAddressException {
        directory.addAddress(address, house);
    }

    /**
     * Count the total population of an address unit, identified by
     * partial address.
     * E.g. getPopulation(Arrays.asList("Seychelles")) will return population
       of the whole country.
     * @param address - an address prefix, starting with the country name,
     *                  and listing the names of the intermediate address units
     *                  down to the necessary level.
     * @throws - InvalidAddressException if the address is malformed (e.g. empty,
     *         or contains dangling identifiers after the house number)
     *         - AddressNotFoundException if the address refers to
     *         an address unit that is not registered in the directory.
     */
    public int getPopulation(List<String> address)
        throws InvalidAddressException, AddressNotFoundException {
        // Put your code here.
        throw new RuntimeException("Not implemented");
    }

    /**
     * Print all the given addresses in the subunit located by the
     * given address prefix.
     * @param address - an address prefix, starting with the country name,
     *                  and listing the names of the intermediate address units
     *                  down to the necessary level.
     * @throws - InvalidAddressException if the address is malformed (e.g. empty,
     *         or contains dangling identifiers after the house number)
     *         - AddressNotFoundException if the address refers to an address
     *         unit that is not registered in the directory.
     */
    public void printAddresses(List<String> address)
        throws InvalidAddressException, AddressNotFoundException {
        // Put your code here.
        throw new RuntimeException("Not implemented");
    }
}
