package ch.epfl.sweng;

/**
 * You can modify this file freely.
 * This class is part of the question on visitor
 * design patterns for designing the printAddresses method.
 * An implementation of the visitor class that takes care
 * of printing all the addresses in the directory with the
 * given prefix.
 *
 * Each addresses should be printed on a new line with a comma
 * between different divisions.
 *
 * Example:
 *     Seychelles, Mahe, Port Glaud, 234
 *     Seychelles, Mahe, Port Glaud, 432
 */

public class PrintAddressesVisitor implements AddressUnitVisitor {
    /* The accumulated address prefix
     * (stores the address units higher in the address hierarchy) */
    private String prefix;
    /* The address unit separator (e.g. ", ") */
    private String separator;

    public PrintAddressesVisitor(String prefix, String separator) {
        this.prefix = prefix;
        this.separator = separator;
    }

    public void visit(CompositeAddressUnit addr) {
        String prevPrefix = prefix;
        /* Add name of CompositeAddressUnit to prefix */
        this.prefix = this.prefix + addr.getName() + separator;

        /* Make recursive call on children */
        for (AddressUnit i : addr.getSubUnits()) {
            i.accept(this);
        }

        /* Get back to the prefix we had before going into this subtree */
        this.prefix = prevPrefix;
    }

    public void visit(House house) {
        /* Print Address of House */
        System.out.println(this.prefix + house.getName());
    }
}
