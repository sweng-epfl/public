package ex8;

public class App {
    public static void main(String[] args) {
        User u = new User("test-user");
        System.out.println(String.format("The user with username '%s' has the following personal info: '%s'.", u.getUsername(), u.getPersonalInfo()));
    }
}
