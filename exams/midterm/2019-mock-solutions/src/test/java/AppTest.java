// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CAN edit everything in this file
// You CAN delete this file
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    public SwengShell sh;

    @BeforeEach
    void localSetup() throws FileAlreadyExistsException, DirectoryAlreadyExistsException {
        // The root node has no "name". It is represented by a single slash "/"
        // located at the root of any absolute path.
        FileSystemNode root = new FileSystemNode(null, new Permissions("rw"));

        root.addFile("f_a.txt", 10)
            .addFile("f_b.txt", 13);

        root.addDirectory("d_a")
                .addFile("f_c.txt", 10)
                .addFile("f_d.txt", 4)
                .addDirectory("d_c")
                    .addFile("f_g.txt", 8)
                    .addFile("f_h.txt", 12);

        root.addDirectory("d_b")
                .addFile("f_e.txt", 1)
                .addFile("f_f.txt", 3)
                .addDirectory("d_d")
                    .addFile("f_i.txt", 9)
                    .addFile("f_j.txt", 32)
                    .addFile(".f_hidden_1.txt", 1)
                    .addFile(".f_hidden_2.txt", 2)
                    .addDirectory("d_e");

        sh = new SwengShell(root);
    }

    // ///////////////////
    // Example other tests
    // ///////////////////

    @Test
    void testCanListRootChildren() throws NoSuchPathException, DirectoryNotReadableException {
        sh.changeDirectory("/");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "f_a.txt",
                "f_b.txt",
                "d_a",
                "d_b"
        ));
    }

    @Test
    void testChangeDirectoryAbsolute() throws NoSuchPathException, DirectoryNotReadableException {
        sh.changeDirectory("/d_a");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "f_c.txt",
                "f_d.txt",
                "d_c"
        ));

        sh.changeDirectory("/d_a/d_c");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "f_g.txt",
                "f_h.txt"
        ));
    }

    @Test
    void testChangeDirectoryToParentOfRootLoopsBackToRoot() throws NoSuchPathException, DirectoryNotReadableException {
        sh.changeDirectory("/");
        sh.changeDirectory("..");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "d_a",
                "d_b",
                "f_a.txt",
                "f_b.txt"
        ));
    }

    @Test
    void testChangeDirectoryToFileThrowsException() {
        assertThrows(NoSuchPathException.class, () -> sh.changeDirectory("/d_a/d_c/f_g.txt"));
    }

    @Test
    void testChangeDirectoryToNonExistantDirectoryThrowsException() {
        assertThrows(NoSuchPathException.class, () -> sh.changeDirectory("/d_a/d_b"));
    }

    @Test
    void testChangeRelativeDirectory() throws NoSuchPathException, DirectoryNotReadableException {
        sh.changeDirectory("/d_a");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "f_c.txt",
                "f_d.txt",
                "d_c"
        ));

        // Change back to SAME directory by going up one level
        sh.changeDirectory("../d_a");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "f_c.txt",
                "f_d.txt",
                "d_c"
        ));

        // Change to DIFFERENT directory by going up first
        sh.changeDirectory("../d_b");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "f_e.txt",
                "f_f.txt",
                "d_d"
        ));
    }

    @Test
    void testAddFileAndDirectory() throws FileAlreadyExistsException, DirectoryNotWriteableException, NoSuchPathException, DirectoryNotReadableException, DirectoryAlreadyExistsException {
        sh.changeDirectory("/");
        sh.createDirectory("test");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "f_a.txt",
                "f_b.txt",
                "d_a",
                "d_b",
                "test"
        ));

        sh.changeDirectory("test");
        sh.createFile("bingo.txt", 5);
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
            "bingo.txt"
        ));
    }

    // Test that the printed working directory is correct
    @Test
    void testCwdString() throws NoSuchPathException {
        sh.changeDirectory("/");
        assertThat(sh.printWorkingDirectory(), is("/"));

        sh.changeDirectory("d_a/d_c");
        assertThat(sh.printWorkingDirectory(), is("/d_a/d_c"));

        sh.changeDirectory("../../d_b");
        assertThat(sh.printWorkingDirectory(), is("/d_b"));
    }

    @Test
    void testRemoveFiles() throws NoSuchPathException, DirectoryNotReadableException, DirectoryNotWriteableException, DirectoryNotEmptyException, NoSuchFileException, FileNotWriteableException {
        sh.changeDirectory("/");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "f_a.txt",
                "f_b.txt",
                "d_a",
                "d_b"
        ));

        sh.remove("f_a.txt");
        sh.remove("f_b.txt");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "d_a",
                "d_b"
        ));
    }

    @Test
    void testRemoveNonExistingFile() throws NoSuchPathException {
        sh.changeDirectory("/");
        assertThrows(NoSuchFileException.class, () -> sh.remove("f_c.txt"));
    }

    @Test
    void testChangePermissions() throws NoSuchPathException, NoSuchFileException {
        sh.changeDirectory("/d_b");
        String permOld = sh.getPermissions("f_e.txt");
        assertThat(permOld, is("rw"));

        sh.setPermissions("f_e.txt", new Permissions("r-"));
        String permNew = sh.getPermissions("f_e.txt");
        assertThat(permNew, is("r-"));
    }

    @Test
    void testAddFileWithNegativeSize() throws FileAlreadyExistsException, DirectoryNotWriteableException, NoSuchPathException, DirectoryNotReadableException {
        sh.changeDirectory("/");
        sh.createFile("good.txt", 0);
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "f_a.txt",
                "f_b.txt",
                "good.txt",
                "d_a",
                "d_b"
        ));

        assertThrows(IllegalArgumentException.class, () -> sh.createFile("bad.txt", -17));
    }

    // /////////////////////////
    // To be written by students
    // /////////////////////////

    // Bug 1
    @Test
    void testListHiddenFiles() throws NoSuchPathException, DirectoryNotReadableException {
        sh.changeDirectory("/d_b/d_d");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "f_i.txt",
                "f_j.txt",
                "d_e"
        ));

        assertThat(sh.listWorkingDirectory(true), containsInAnyOrder(
                "f_i.txt",
                "f_j.txt",
                ".f_hidden_1.txt",
                ".f_hidden_2.txt",
                "d_e"
        ));
    }

    // Bug 2
    @Test
    void testRemoveDirectoryNonEmpty() throws NoSuchPathException, DirectoryNotReadableException {
        sh.changeDirectory("/");
        // Removing non-empty directory
        assertThrows(DirectoryNotEmptyException.class, () -> sh.remove("d_b"));

        sh.changeDirectory("/d_b/d_d");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "f_i.txt",
                "f_j.txt",
                "d_e"
        ));

        // Removing empty directory
        assertDoesNotThrow(() -> sh.remove("d_e"));
    }

    // Bug 3
    @Test
    void testFileSizeCorrect() throws NoSuchPathException, NoSuchFileException {
        sh.changeDirectory("/");
        assertThat(sh.sizeInBytes("f_a.txt"), is(10));

        sh.changeDirectory("/d_a");
        assertThat(sh.sizeInBytes("d_c"), is(20));
    }

    // Bug 4
    @Test
    void testAddDuplicateFileOrDir() throws NoSuchPathException {
        sh.changeDirectory("/");
        assertThrows(FileAlreadyExistsException.class, () -> sh.createFile("f_a.txt", 13));
        assertThrows(DirectoryAlreadyExistsException.class, () -> sh.createDirectory("d_a"));
    }

    // Bug 5
    @Test
    void testCannotCreateFileOrDirectoryIfCwdNotWriteable() throws NoSuchPathException, NoSuchFileException, DirectoryNotWriteableException, DirectoryAlreadyExistsException {
        sh.changeDirectory("/");

        sh.createDirectory("test");
        sh.setPermissions("test", new Permissions("r-"));

        sh.changeDirectory("test");
        assertThrows(DirectoryNotWriteableException.class, () -> sh.createFile("dummy_file.txt", 10));
        assertThrows(DirectoryNotWriteableException.class, () -> sh.createDirectory("dummy_directory"));

        sh.changeDirectory("..");
        sh.setPermissions("test", new Permissions("rw"));
        sh.changeDirectory("test");
        assertDoesNotThrow(() -> sh.createFile("dummy_file.txt", 10));
        assertDoesNotThrow(() -> sh.createDirectory("dummy_directory"));
    }

    // Bug 6
    @Test
    void testCannotListWorkingDirectoryIfCwdNotReadable() throws NoSuchPathException, NoSuchFileException, DirectoryNotReadableException {
        sh.changeDirectory("/");

        sh.setPermissions("d_a", new Permissions("-w"));
        sh.changeDirectory("d_a");
        assertThrows(DirectoryNotReadableException.class, () -> sh.listWorkingDirectory());

        sh.changeDirectory("..");
        sh.setPermissions("d_a", new Permissions("r-"));
        sh.changeDirectory("d_a");
        assertThat(sh.listWorkingDirectory(), containsInAnyOrder(
                "f_c.txt",
                "f_d.txt",
                "d_c"
        ));
    }
}
