public final class PeopleCounter {
    private final int maximum;
    private int value;

    public PeopleCounter(int maximum) {
        if (maximum < 0) {
            throw new IllegalArgumentException("Maximum cannot be <0");
        }
        this.maximum = maximum;
        this.value = 0;
    }

    public int value() {
        return value;
    }

    public void increment() {
        if (value != maximum) {
            value++;
        }
    }

    public void reset() {
        value = 0;
    }
}
