
package ch.epfl.sweng;

/**
 * You can modify this file freely.
 * This class is part of the question on visitor
 * design patterns for designing the printAddresses method.
 * A generic interface for the visitor that walks through the
 * AddressUnit hierarchy.
 */

public interface AddressUnitVisitor {
	void visit(CompositeAddressUnit unit);
	void visit(House house);
}
