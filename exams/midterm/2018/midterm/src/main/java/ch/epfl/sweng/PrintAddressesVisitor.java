package ch.epfl.sweng;

/**
 * You can modify this file freely.
 * This class is part of the question on visitor
 * design patterns for designing the printAddresses method.
 * An implementation of the visitor class that takes care
 * of printing all the addresses in the directory with the
 * given prefix.
 */

public class PrintAddressesVisitor implements AddressUnitVisitor {

	/* Each addresses should be printed on a new line with a comma and space
     between different divisions */
	/* Example:
	   Seychelles, Mahe, Port Glaud, 234
	   Seychelles, Mahe, Port Glaud, 432
	*/

	public void visit(CompositeAddressUnit unit) {
		/* Your code here */
	}

	public void visit(House house) {
		/* Your code here */
	}
}
