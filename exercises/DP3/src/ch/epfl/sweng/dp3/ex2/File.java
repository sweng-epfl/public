package ch.epfl.sweng.dp3.ex2;

public class File {
  private String name;
  private String data;
  private int sizeInBytes;

  public File(String name, String data) {
    this.name = name;
    this.data = data;
    sizeInBytes = data.length();
  }

  public String getName() {
    return name;
  }

  public String getData() {
    return data;
  }

  public int getSizeInBytes() {
    return sizeInBytes;
  }

  @Override
  public String toString() {
    return String.format("(name: %s, size: %d bytes)", name, sizeInBytes);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof File) {
      File otherFile = (File) o;
      return name.compareTo(otherFile.name) == 0
          && data.compareTo(otherFile.data) == 0
          && sizeInBytes == otherFile.sizeInBytes;
    }
    return false;
  }
}
