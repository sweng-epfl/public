package ch.epfl.sweng.dp3.solutions.ex1;

public class SimpleHashFunction implements HashFunction {

  @Override
  public String hash(String data) {
    // a naive hash function
    int hash = 0;
    for (char c : data.toCharArray()) {
      hash += ((int) c) * hash + 19;
    }

    return String.valueOf(hash);
  }
}
