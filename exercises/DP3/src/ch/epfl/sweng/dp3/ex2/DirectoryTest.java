package ch.epfl.sweng.dp3.ex2;

import org.junit.Test;

public class DirectoryTest {

  @Test
  public void testFunctionality() {
    Directory directory = new Directory();
    File f1 = new File("Buth", "this file has also quite some data");
    File f2 = new File("Aaron", "this file has quite some data");
    File f3 = new File("Charles", "not that much data really");

    directory.addFile(f1);
    directory.addFile(f2);
    directory.addFile(f3);

    // TODO: uncomment the following lines and introduce code as needed to make the assertions
    // succeed
    //
    //        DirectorySort fileNameSort = new FileNameSort();
    //        DirectorySort fileSizeSort = new FileSizeSort();
    //
    //        List<File> expectedNameSortedList = Arrays.asList(f2, f1, f3);
    //        List<File> expectedSizeSortedList = Arrays.asList(f3, f2, f1);
    //        assertEquals(expectedNameSortedList, directory.sort(fileNameSort));
    //        assertEquals(expectedSizeSortedList, directory.sort(fileSizeSort));
  }
}
