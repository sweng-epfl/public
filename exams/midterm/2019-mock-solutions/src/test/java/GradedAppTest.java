import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;

import grading.GradedCategory;
import grading.GradedTest;
import static org.junit.jupiter.api.Assertions.*;

// we use test names like "t00" to fix the order
@GradedCategory("Bug fixes")
class GradedAppTest {
    private SwengShell sh;

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

    @GradedTest("Bug 1")
    void t01_testListHiddenFiles() throws NoSuchPathException, DirectoryNotReadableException {
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

    @GradedTest("Bug 2")
    void t02_testRemoveDirectoryNonEmpty() throws NoSuchPathException, DirectoryNotReadableException {
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

    @GradedTest("Bug 3")
    void t03_testFileSizeCorrect() throws NoSuchPathException, NoSuchFileException {
        sh.changeDirectory("/");
        assertThat(sh.sizeInBytes("f_a.txt"), is(10));

        sh.changeDirectory("/d_a");
        assertThat(sh.sizeInBytes("d_c"), is(20));
    }

    @GradedTest("Bug 4")
    void t04_testAddDuplicateFileOrDir() throws NoSuchPathException {
        sh.changeDirectory("/");
        assertThrows(FileAlreadyExistsException.class, () -> sh.createFile("f_a.txt", 13));
        assertThrows(DirectoryAlreadyExistsException.class, () -> sh.createDirectory("d_a"));
    }

    @GradedTest("Bug 5")
    void t05_testCannotCreateFileOrDirectoryIfCwdNotWriteable() throws NoSuchPathException, NoSuchFileException, DirectoryNotWriteableException, DirectoryAlreadyExistsException {
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

    @GradedTest("Bug 6")
    void t06_testCannotListWorkingDirectoryIfCwdNotReadable() throws NoSuchPathException, NoSuchFileException, DirectoryNotReadableException {
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
