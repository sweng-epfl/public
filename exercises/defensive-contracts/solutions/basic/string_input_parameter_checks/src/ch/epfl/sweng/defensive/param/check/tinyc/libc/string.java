package ch.epfl.sweng.defensive.param.check.tinyc.libc;

import ch.epfl.sweng.defensive.param.check.tinyc.fault.SegmentationFault;
import ch.epfl.sweng.defensive.param.check.tinyc.type.cstring;

public class string {

  public static cstring strcpy(cstring destination, cstring source) throws SegmentationFault {
    if (destination == null) {
      throw new IllegalArgumentException("null destination");
    }
    if (source == null) {
      throw new IllegalArgumentException("null source");
    }
    int index = 0;
    while (source.get(index) != '\0') {
      destination.set(index, source.get(index));
      index++;
    }
    destination.set(index, '\0');
    return destination;
  }

  public static cstring strncpy(cstring destination, cstring source, int num) throws SegmentationFault {
    if (destination == null) {
      throw new IllegalArgumentException("null destination");
    }
    if (source == null) {
      throw new IllegalArgumentException("null source");
    }
    int index = 0;
    while (source.get(index) != '\0' && index < num) {
      destination.set(index, source.get(index));
      index++;
    }
    return destination;
  }

  public static cstring strcat(cstring destination, cstring source) throws SegmentationFault {
    if (destination == null) {
      throw new IllegalArgumentException("null destination");
    }
    if (source == null) {
      throw new IllegalArgumentException("null source");
    }
    int i = 0, j = 0;
    while (destination.get(i) != '\0') {
      i++;
    }
    while (source.get(j) != '\0') {
      destination.set(i++, source.get(j++));
    }
    destination.set(i, '\0');
    return destination;
  }

  public static cstring strncat(cstring destination, cstring source, int num) throws SegmentationFault {
    if (destination == null) {
      throw new IllegalArgumentException("null destination");
    }
    if (source == null) {
      throw new IllegalArgumentException("null source");
    }
    int i = 0, j = 0;
    while (destination.get(i) != '\0') {
      i++;
    }
    while (source.get(j) != '\0' && j < num) {
      destination.set(i++, source.get(j++));
    }
    return destination;
  }

  public static int strlen(cstring str) {
    if (str == null) {
      throw new IllegalArgumentException("null str");
    }
    int length = 0;
    while (str.get(length) != '\0') {
      length++;
    }
    return length;
  }

  public static int strcmp(cstring str1, cstring str2) {
    if (str1 == null) {
      throw new IllegalArgumentException("null str1");
    }
    if (str2 == null) {
      throw new IllegalArgumentException("null str2");
    }
    int index = 0;
    while (str1.get(index) != '\0' && str1.get(index) == str2.get(index)) {
      index++;
    }
    return str1.get(index) - str2.get(index);
  }

  public static int strncmp(cstring str1, cstring str2, int num) {
    if (str1 == null) {
      throw new IllegalArgumentException("null str1");
    }
    if (str2 == null) {
      throw new IllegalArgumentException("null str2");
    }
    int index = 0;
    while (str1.get(index) != '\0' && str1.get(index) == str2.get(index) && index < num) {
      index++;
    }
    return str1.get(index) - str2.get(index);
  }
}