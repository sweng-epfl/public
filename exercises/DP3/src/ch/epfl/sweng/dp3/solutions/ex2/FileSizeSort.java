package ch.epfl.sweng.dp3.solutions.ex2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileSizeSort implements DirectorySort {

  @Override
  public List<File> sort(List<File> files) {
    List<File> listToBeSorted = new ArrayList(files);
    Collections.sort(
        listToBeSorted,
        new Comparator<File>() {
          @Override
          public int compare(File o1, File o2) {
            return o1.getSizeInBytes() - o2.getSizeInBytes();
          }
        });
    return listToBeSorted;
  }
}
