package two;

import java.util.Scanner;

public class Greeter {

    private Greeter() {
    }

    private static final Scanner s = new Scanner(System.in);

    private static final String FIRST_NAME_REQUEST = "Please enter your first name:";
    private static final String LAST_NAME_REQUEST = "Please enter your last name:";
    private static final String AGE_REQUEST = "Please enter your age:";

    private static final String ageFormat = "(%s years old)";

    // prints: Nice to meet you Willy
    public static void askForFirstName() {
        greet(ask(FIRST_NAME_REQUEST));
    }

    // prints: Nice to meet you Wonka
    public static void askForLastName() {
        greet(ask(LAST_NAME_REQUEST));
    }

    // prints: Nice to meet you Willy Wonka
    public static void askForFullName() {
        greet(ask(FIRST_NAME_REQUEST), ask(LAST_NAME_REQUEST));
    }

    // prints: Nice to meet you Willy Wonka (23 years old)
    public static void askForFullNameAndAge() {
        String firstName = ask(FIRST_NAME_REQUEST);
        String lastName = ask(LAST_NAME_REQUEST);
        String age = ask(AGE_REQUEST);
        greet(firstName, lastName, String.format(ageFormat, age));
    }

    private static String ask(String question) {
        System.out.println(question);
        return s.next();
    }

    private static void greet(String... data) {
        StringBuilder message = new StringBuilder("Nice to meet you ");
        for (String s : data)
            message.append(s).append(" ");
        System.out.println(message.toString().strip());
    }
}
