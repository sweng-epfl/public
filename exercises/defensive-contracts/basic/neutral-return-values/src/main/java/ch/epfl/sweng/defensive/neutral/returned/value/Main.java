package ch.epfl.sweng.defensive.neutral.returned.value;

import java.util.Scanner;

import ch.epfl.sweng.defensive.neutral.returned.value.goolge.Goolge;
import ch.epfl.sweng.defensive.neutral.returned.value.model.Result;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a search phrase:");
        String line = scanner.nextLine().trim();
        if (!line.isEmpty()) {
            String[] keywords = line.split(" ");
            Result result = Goolge.search(keywords);
            while (result != null) {
                System.out.println(result);
                System.out.println("...");
                scanner.nextLine();
                result = result.next();
            }
            System.out.println();
        }
    }
}