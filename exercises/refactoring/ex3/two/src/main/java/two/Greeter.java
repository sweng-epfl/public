package two;

import java.util.Scanner;

public class Greeter {

    // prints: Nice to meet you Willy
    public static void askForFirstName() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your first name:");
        String firstName = s.next();
        System.out.println("Nice to meet you " + firstName);
    }

    // prints: Nice to meet you Wonka
    public static void askForLastName() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your last name:");
        String lastName = s.next();
        System.out.println("Nice to meet you " + lastName);
    }

    // prints: Nice to meet you Willy Wonka
    public static void askForFullName() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your first name:");
        String firstName = s.next();
        System.out.println("Please enter your last name:");
        String lastName = s.next();
        System.out.println("Nice to meet you " + firstName + " " + lastName);
    }

    // prints: Nice to meet you Willy Wonka (23 years old)
    public static void askForFullNameAndAge() {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your first name:");
        String firstName = s.next();
        System.out.println("Please enter your last name:");
        String lastName = s.next();
        System.out.println("Please enter your age:");
        String age = s.next();
        System.out.println("Nice to meet you " + firstName + " " + lastName + " (" + age + " years old)");
    }
}
