package ch.epfl.sweng.defensive.garbage.in.non.garbage.out;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format {
  private Format() {}
  public static boolean matches(String value, String regex) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}