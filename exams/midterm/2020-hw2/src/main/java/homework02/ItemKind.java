// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

package homework02;

/**
 * Represents an item kind.
 */
public enum ItemKind {
    T_SHIRT(15),
    SHIRT(30),
    JEANS(55),
    NINTENDO_SWITCH(300.5),
    APPLE_WATCH(500),
    IPHONE(800),
    SUPER_MARIO(59.9);

    /** The item kind's price. */
    public final double price;

    ItemKind(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemKind=" + super.toString() +
                "price=" + price +
                '}';
    }

    /**
     * Returns the NAME (uppercased) of the item kind.
     */
    public String getName() {
        return super.toString();
    }
}
