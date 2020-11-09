// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

package homework02.util;

import homework02.ItemKind;

import java.util.Objects;

/**
 * Represents a movement of the inventory.
 * This class is immutable.
 */
public final class Movement {
    public final ItemKind itemKind;
    public final MovementType movementType;

    public Movement(ItemKind itemKind, MovementType movementType) {
        this.itemKind = itemKind;
        this.movementType = movementType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return itemKind == movement.itemKind &&
                movementType == movement.movementType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemKind, movementType);
    }

    @Override
    public String toString() {
        return "Movement{" +
                "itemKind=" + itemKind +
                ", movementType=" + movementType +
                '}';
    }
}
