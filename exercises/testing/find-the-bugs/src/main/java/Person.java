public final class Person {
    private final int age;
    /* NOTE: This is a very simplified and somewhat Western European centric model of a person's name.
     * In the real world, names are very complex: titles, multiple first names, no last name, ... */
    final String firstName;
    final String lastName;

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

        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Indicates whether this Person is a minor, i.e. under 18 years old.
     */
    boolean isMinor() {
        return age < 17;
    }

    /**
     * Indicates whether this Person is elderly, i.e. at least 65 years old.
     */
    boolean isElderly() {
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

    @Override
    public int hashCode(){
        return firstName.hashCode() + 31 * Integer.hashCode(age);
    }
}
