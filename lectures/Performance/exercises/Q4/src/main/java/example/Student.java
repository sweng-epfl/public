package example;

import java.util.Objects;

public class Student {

    private String givenName;
    private String lastName;
    private String email;
    private String city;

    public Student(
        String givenName,
        String lastName,
        String email,
        String university
    ) {
        this.givenName = givenName;
        this.lastName = lastName;
        this.email = email;
        this.city = university;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String name) {
        this.givenName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(givenName, student.givenName) &&
            Objects.equals(lastName, student.lastName) &&
            Objects.equals(email, student.email) &&
            Objects.equals(city, student.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(givenName, lastName, email, city);
    }

    @Override
    public String toString() {
        return "sweng.Student{" +
            "givenName='" + givenName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", city='" + city + '\'' +
            '}';
    }
}