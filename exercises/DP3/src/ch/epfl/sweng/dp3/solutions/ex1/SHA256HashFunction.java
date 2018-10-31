package ch.epfl.sweng.dp3.solutions.ex1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256HashFunction implements HashFunction {

  @Override
  public String hash(String data) {
    StringBuffer hexString = new StringBuffer();
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    byte[] hash = md.digest(data.getBytes());

    // code snippet taken from
    // https://stackoverflow.com/questions/5470219/get-md5-string-from-message-digest
    for (int i = 0; i < hash.length; i++) {
      if ((0xff & hash[i]) < 0x10) {
        hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
      } else {
        hexString.append(Integer.toHexString(0xFF & hash[i]));
      }
    }

    return hexString.toString();
  }
}
