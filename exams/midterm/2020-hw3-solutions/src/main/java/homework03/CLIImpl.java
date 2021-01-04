// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN change the bodies of existing methods/constructors
// You CAN add new private methods/constructors
// You CANNOT add interface implementations unless explicitly instructed to do so
// You CANNOT add new public, package-private or protected methods/constructors
// You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
// You CANNOT delete existing methods/constructors
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
package homework03;

import homework03.util.AlreadyBookedException;
import homework03.util.CLI;
import homework03.util.Client;
import homework03.util.DayFullException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * You CAN change the bodies of existing methods/constructors
 * You CAN add new private methods/constructors
 * You CANNOT add interface implementations unless explicitly instructed to do so
 * You CANNOT add new public, package-private or protected methods/constructors
 * You CANNOT edit the names, parameters, checked exceptions or return types of existing methods/constructors
 * You CANNOT delete existing methods/constructors
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class CLIImpl implements CLI {
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private final Client client;
    private final Scanner scanner;

    public CLIImpl(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("The client cannot be null");
        }

        this.client = client;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void getQueryAndExecute() {
        System.out.println("Please enter your query: ");
        String args = scanner.nextLine().trim();

        // book command
        boolean isBookCmd = args.contains("book");
        if (isBookCmd) {
            // Execute book command
            String[] argsSplit = args.split(" ");
            if (argsSplit.length != 2) {
                System.out.println("Your query does not conform to the syntax.");
                return;
            }
            
            String dateString = argsSplit[1];
            if (checkDateSanity(dateString)) {
                client.book(dateString,
                        dateStringParam -> System.out.printf("Your booking for %s was successful.%n", dateString),
                        exception -> {
                            if (exception instanceof DayFullException) {
                                System.out.printf("Your booking for %s was unsuccessful. All the available slots are occupied.%n", dateString);
                            } else if (exception instanceof AlreadyBookedException) {
                                System.out.printf("Your booking for %s was unsuccessful. You already booked this date.%n", dateString);
                            } else {
                                System.out.println("An error occurred, please try again later.");
                            }
                        });
            } else {
                System.out.println("Your date has the wrong format or represents a date that does not exist. The correct format is 'dd.MM.yyyy'.");
            }
            return;
        }

        // getBookings command
        boolean isGetBookingsCmd = Pattern.matches("getBookings", args);
        if (isGetBookingsCmd) {
            // Execute getBookings command
            if(args.split(" ").length > 1) {
                System.out.println("Your query does not conform to the syntax.");
                return;
            }
            client.getBookings(set -> {
                        System.out.println("Here are your current booking(s):");
                        for (String booking : set) {
                            System.out.println("\t" + booking);
                        }
                    },
                    exception -> System.out.println("An error occurred, please try again later."));
            return;
        }

        // Invalid command
        System.out.println("Your query does not conform to the syntax.");
    }

    private boolean checkDateSanity(String dateString) {
        if (Pattern.matches("[0-3][0-9].[0-1][0-9].[0-9]{4}", dateString)) {
            try {
                DateFormat df = new SimpleDateFormat(DATE_FORMAT);
                df.setLenient(false);
                df.parse(dateString);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        return false;
    }
}
