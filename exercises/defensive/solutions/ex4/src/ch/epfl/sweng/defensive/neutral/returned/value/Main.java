package ch.epfl.sweng.defensive.neutral.returned.value;

import java.util.Optional;
import java.util.Scanner;

import ch.epfl.sweng.defensive.neutral.returned.value.goolge.Goolge;
import ch.epfl.sweng.defensive.neutral.returned.value.model.Result;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("> search ");
      String line = scanner.nextLine().trim();
      if (!line.isEmpty()) {
        String[] keywords = line.split(" ");
        Optional<Result> optional = Goolge.search(keywords);
        while (optional.isPresent()) {
          Result result = optional.get();
          System.out.println(result);
          System.out.println("...");
          scanner.nextLine();
          optional = result.next();
        }
        System.out.println();
      }
    }
  }
}