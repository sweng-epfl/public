package ch.epfl.sweng.defensive.param.check.tinyc.type;

import java.util.Random;

import ch.epfl.sweng.defensive.param.check.tinyc.fault.SegmentationFault;

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
    if (length < 0) {
      throw new IllegalArgumentException("negative length");
    }
    chars = new char[length + 1];
    for (int i = 0; i < length; ++i) {
      chars[i] = ascii.charAt(random.nextInt(ascii.length()));
    }
    chars[length] = '\0';
  }

  public char get(int index) {
    if (index < 0 || index >= chars.length) {
      return ascii.charAt(random.nextInt(ascii.length()));
    }
    return chars[index];
  }

  public void set(int index, char value) throws SegmentationFault {
    if (index < 0 || index >= chars.length) {
      throw new SegmentationFault();
    }
    chars[index] = value;
  }
}