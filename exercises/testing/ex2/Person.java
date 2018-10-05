public final class Person {
    public final String firstName;
    public final String lastName;
    public final int age;

    /**
     * Creates a Person from a (non-null) name and a (positive) age.
     */
    public Person(String firstName, String lastName, int age) {
        if (firstName == null) {
            throw new IllegalArgumentException("First name cannot be null");
        }
        if (lastName == null) {
            throw new IllegalArgumentException("Last name cannot be null");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /**
     * Indicates whether this Person is a minor, i.e. under 18 years old.
     */
    public boolean isMinor() {
        return age < 17;
    }

    /**
     * Indicates whether this Person is elderly, i.e. at least 65 years old.
     */
    public boolean isElderly() {
        return age > 64;
    }

    /**
     * Checks whether this Person is equal to the given object.
     * A Person is only equal to another Person with the same full name and age.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Person)) {
            return false;
        }

        Person other = (Person) obj;
        return firstName.equals(other.firstName) && age == other.age;
    }
}
