package ch.epfl.sweng.defensive.param.check.tinyc.type;

import java.util.Random;

public class cstring {

  private static Random random = new Random();
  private static String ascii = "";
  private char[] chars;

  static {
    for (int i = 0; i < 128; ++i) {
      ascii += (char) i;
    }
  }

  public cstring(int length) {
    chars = new char[length + 1];
    for (int i = 0; i < length; ++i) {
      chars[i] = ascii.charAt(random.nextInt(ascii.length()));
    }
    chars[length] = '\0';
  }

  public char get(int index) {
    return chars[index];
  }

  public void set(int index, char value) {
    chars[index] = value;
  }
}