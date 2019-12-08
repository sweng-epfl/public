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

    // Bug 1
    @Test
    void testListHiddenFiles() {
        // TODO: fill me!
    }

    // Bug 2
    @Test
    void testRemoveDirectoryNonEmpty() {
        // TODO: fill me!
    }

    // Bug 3
    @Test
    void testFileSizeCorrect() {
        // TODO: fill me!
    }

    // Bug 4
    @Test
    void testAddDuplicateFileOrDir() {
        // TODO: fill me!
    }

    // Bug 5
    @Test
    void testCannotCreateFileOrDirectoryIfCwdNotWriteable() {
        // TODO: fill me!
    }

    // Bug 6
    @Test
    void testCannotListWorkingDirectoryIfCwdNotReadable() {
        // TODO: fill me!
    }
}
