package ch.epfl.sweng.dp3.ex2;

import java.util.LinkedList;
import java.util.List;

public class Directory {
  private List<File> files;

  public Directory() {
    files = new LinkedList<>();
  }

  public void addFile(File file) {
    files.add(file);
  }
}
