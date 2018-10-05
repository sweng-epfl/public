package ch.epfl.sweng.defensive.neutral.returned.value.internet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Internet {
  private static List<String> content = new ArrayList<>();

  static {
    String path = Internet.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    String filename  = String.format("%s%s", path, "hamlet.txt");
    try {
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      String line;
      while ((line = reader.readLine()) != null) { content.add(line); }
      reader.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static Iterator<String> find(String[] keywords) {
    return content.stream().filter(text -> Arrays.stream(keywords).anyMatch(text::contains)).iterator();
  }
}