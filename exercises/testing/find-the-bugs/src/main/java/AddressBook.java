import java.util.HashMap;
import java.util.Map;

/**
 A book that keeps track of people's addresses.

 Users can add or remove people and their addresses.
 An address is allowed to be null (the person isn't living anywhere!).
 For simplicity, people are considered equal if they have the same first name, same last name, and same age.

 The book can be printed to a string, which has each person and their address on a separate line.
 */
public final class AddressBook {
    private final Map<Person, String> addresses;

    public AddressBook() {
        addresses = new HashMap<>();
    }

    public void setAddress(Person person, String address) {
        addresses.put(person, address);
    }

    public void removePerson(Person person) {
        addresses.remove(person);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Map.Entry<Person, String> pair : addresses.entrySet()) {
            builder.append(pair.getKey().firstName);
            builder.append(" ");
            builder.append(pair.getKey().lastName);
            builder.append(": ");
            builder.append(pair.getValue());
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}
