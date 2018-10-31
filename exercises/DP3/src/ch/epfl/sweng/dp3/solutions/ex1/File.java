package ch.epfl.sweng.dp3.solutions.ex1;

public class File {
  private String data;

  public File(String data) {
    this.data = data;
  }

  public String getData() {
    return data;
  }

  public String getHash(HashFunction simpleHashFunction) {
    return simpleHashFunction.hash(data);
  }
}
