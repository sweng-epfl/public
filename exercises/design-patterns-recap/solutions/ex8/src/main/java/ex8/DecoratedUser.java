package ex8;

public class DecoratedUser extends User {

    private String firstName, lastName;
    private int age;

    public DecoratedUser(User user, String firstName, String lastName, int age) {
        super(user.getUsername());
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String getPersonalInfo() {
        return String.format("first name: %s, last name: %s, age: %s", firstName, lastName, age);
    }
}
