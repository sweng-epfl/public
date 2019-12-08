package ch.epfl.sweng.midterm.booking;

import java.util.Objects;

public class Person {
    private final String firstName;
    private final String lastName;
    private final String nationality;

    public Person(String firstName, String lastName, String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Person))
            return false;

        Person person = (Person) o;

        if (!Objects.equals(firstName, person.firstName))
            return false;
        if (!Objects.equals(lastName, person.lastName))
            return false;
        return Objects.equals(nationality, person.nationality);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        return result;
    }
}
