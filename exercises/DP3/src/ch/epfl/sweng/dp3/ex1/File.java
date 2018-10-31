package ch.epfl.sweng.dp3.ex1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.naming.OperationNotSupportedException;

public class File {
  private String data;

  public File(String data) {
    this.data = data;
  }

  public String getData() {
    return data;
  }

  public String getHash(String hashFunction) throws OperationNotSupportedException {

    if (hashFunction.compareTo("SIMPLE") == 0) {
      return getSimpleHash();
    } else if (hashFunction.compareTo("SHA-256") == 0) {
      return getSHA256Hash();
    } else {
      throw new OperationNotSupportedException(
          "Only SIMPLE and SHA-256 hash functions are currently supported");
    }
  }

  private String getSimpleHash() {
    // a naive hash function
    int hash = 0;
    for (char c : data.toCharArray()) {
      hash += ((int) c) * hash + 19;
    }

    return String.valueOf(hash);
  }

  private String getSHA256Hash() {
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
