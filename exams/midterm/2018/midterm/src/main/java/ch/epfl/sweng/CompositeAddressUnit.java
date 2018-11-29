package ch.epfl.sweng;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

import ch.epfl.sweng.InvalidAddressException;

/**
 * You can modify this file freely.
 * A compound node in the address hierarchy. CompositeAddressUnit represents a
 * postal division. It is further subdivided into smaller chunks.
 */

public class CompositeAddressUnit extends AddressUnit {
    /**
     * A map of all the subunits, mapping a subunit name into the
     * corresponding subunit.
     * invariant: for each key k in the map, subUnit.get(k)
     * returns a subunit with name k.
     */
    private Map<String, AddressUnit> subUnits;

    public CompositeAddressUnit(String name) {
        super(name);
        this.subUnits = new HashMap<String, AddressUnit>();
    }

    protected void addSubUnit(AddressUnit unit) {
        subUnits.put(unit.getName(), unit);
    }

    public AddressUnit getSubUnit(String name) {
        return subUnits.get(name);
    }

    public final Collection<AddressUnit> getSubUnits() {
        return subUnits.values();
    }

    /**
     * The implementation of {@link AddressUnit#addAddress(List, House)},
     * finds the smallest existing address unit in the address list and 
     * creates a branch starting from it down to the house.
     */
    @Override
    public void addAddress(List<String> address, House house)
        throws InvalidAddressException {
        if (address.isEmpty()) {
            throw new InvalidAddressException("Address does not " +
                                              "specify the house");
        }
        String unitName = address.get(0);
        if (!unitName.equals(getName())) {
            throw new InvalidAddressException("The " + unitName +
                                              " does not match " +
                                              getName());
        }
        if (address.size() < 2) {
            throw new InvalidAddressException("Incomplete address");
        }

        String subUnitName = address.get(1);
        if (address.size() < 3) {
            if (getSubUnit(subUnitName) != null) {
                throw new InvalidAddressException("Address exists");
            }
            if (!subUnitName.equals(house.getName())) {
                throw new InvalidAddressException("P.O Box mismatches " +
                                                  "the address");
            }
            addSubUnit(house);
        } else {
            AddressUnit subUnit = getSubUnit(subUnitName);
            if (subUnit == null) {
                subUnit = new CompositeAddressUnit(subUnitName);
                addSubUnit(subUnit);
            }
            subUnit.addAddress(address.subList(1, address.size()), house);
        }
    }

    @Override
    public AddressUnit findUnit(List<String> address)
        throws InvalidAddressException, AddressNotFoundException {
        checkAddress(address);
        if (address.size() == 1) {
            return this;
        } else {
            String subUnitName = address.get(1);
            AddressUnit subUnit = getSubUnit(subUnitName);
            if (subUnit == null) {
                throw new AddressNotFoundException("The address does " +
                                                   "not exist.");
            }
            return subUnit.findUnit(address.subList(1, address.size()));
        }
    }

    @Override
    public int getPopulation() {
        int total = 0;
        for (AddressUnit subUnit : subUnits.values()) {
            total = Math.addExact(total, subUnit.getPopulation());
        }
        return total;
    }

    @Override
    public final void accept(AddressUnitVisitor v) {
        v.visit(this);
    }
}
