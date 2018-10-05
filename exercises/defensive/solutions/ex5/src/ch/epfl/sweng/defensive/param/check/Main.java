package ch.epfl.sweng.defensive.param.check;

import ch.epfl.sweng.defensive.param.check.tinyc.fault.SegmentationFault;
import ch.epfl.sweng.defensive.param.check.tinyc.libc.string;
import ch.epfl.sweng.defensive.param.check.tinyc.type.cstring;

public class Main {
  public static void main(String[] args) throws SegmentationFault {
    cstring sweng = new cstring(5);
    sweng.set(0, 's'); sweng.set(1, 'w'); sweng.set(2, 'e'); sweng.set(3, 'n'); sweng.set(4, 'g');

    int length = string.strlen(sweng);
    System.out.println(length);
    for (int i = 0; i < length; ++i) {
      System.out.print(sweng.get(i));
    }
    System.out.println();

    try {
      sweng.set(10, 'c');
    } catch (SegmentationFault segfault) {
      System.out.println(segfault);
    }

    cstring sdp = new cstring(3);
    sdp.set(0, 's'); sdp.set(1, 'd'); sdp.set(2, 'p');

    int ret = string.strcmp(sweng, sdp);
    System.out.println(String.format("sweng ==? sdp: %b", ret == 0));
  }
}