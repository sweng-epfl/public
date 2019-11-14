package ex8;

public class App {
    public static void main(String[] args) {
        User u = new User("test-user");
        DecoratedUser du = new DecoratedUser(u, "John", "Lennon", 42);
        System.out.println(String.format("The decorated user with username '%s' has the following personal info: '%s'.", du.getUsername(), du.getPersonalInfo()));
    }
}
