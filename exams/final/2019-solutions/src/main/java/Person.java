// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

import java.util.Objects;

/**
 * Model of a Person, with some basic attributes.
 */
final class Person {
    /**
     * Name of the person; never null nor empty.
     */
    final String name;
    /**
     * Title of the person; may be null but not empty.
     */
    final String title;
    /**
     * Age of the person, in years; may be null (meaning the age is unknown) but not less than zero.
     */
    final Integer age;
    /**
     * Address of the person, may be null but not empty.
     */
    final String address;

    /**
     * Creates a new Person given attributes, ignoring leading and trailing spaces in text.
     */
    Person(String name, String title, Integer age, String address) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        if (age != null && age < 0) {
            throw new IllegalArgumentException("Age cannot be less than zero.");
        }

        this.name = ensureNotEmpty(name, "Name");
        this.title = ensureNotEmpty(title, "Title");
        this.age = age;
        this.address = ensureNotEmpty(address, "Address");
    }

    /**
     * Helper method: returns the value if it is null, the trimmed value if it's non-null and non-empty, or throws.
     */
    private static String ensureNotEmpty(String value, String description) {
        if (value == null) {
            return null;
        }
        value = value.trim();
        if (value.isEmpty()) {
            throw new IllegalArgumentException(description + " cannot be empty or contain only spaces.");
        }
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Person)) {
            return false;
        }
        Person other = (Person) obj;
        return name.equals(other.name)
                && Objects.equals(title, other.title)
                && Objects.equals(age, other.age)
                && Objects.equals(address, other.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, title, age, address);
    }

    @Override
    public String toString() {
        return name + ", " + title + ", " + age + ", " + address;
    }
}
