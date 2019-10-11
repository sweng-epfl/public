import java.util.HashMap;
import java.util.Map;

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
