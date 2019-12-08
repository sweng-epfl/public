package ex8;

public class CapitalizedUser extends User {

    public CapitalizedUser(User user) {
        super(user.getUsername().toUpperCase());
    }
}
