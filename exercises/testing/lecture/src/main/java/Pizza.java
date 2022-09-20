public final class Pizza {
    private final String name;
    private boolean isCalzone;
    private final String[] toppings;

    /**
     * Creates a pizza with the given name and toppings, or a calzone if specified.
     */
    public Pizza(String name, boolean isCalzone, String[] toppings) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (toppings == null) {
            throw new IllegalArgumentException("Toppings cannot be null");
        }
        this.name = name;
        this.isCalzone = isCalzone;
        this.toppings = toppings;
    }

    /**
     * Prints the pizza, in the format `[Calzone/Pizza] [name], [comma-separated toppings]` (without the []).
     * May also include an opinion on your choice of toppings.
     */
    @Override
    public String toString() {
        var builder = new StringBuilder();
        if (isCalzone) {
            builder.append("Calzone");
        } else {
            builder.append("Pizza ");
        }
        builder.append(name);
        for (String topping : toppings) {
            builder.append(", ");
            builder.append(topping);
            if (topping.equals("ketchup")) {
                builder.append(" (Mamma mia, why would you do this?)");
            }
        }
        return builder.toString();
    }
}
