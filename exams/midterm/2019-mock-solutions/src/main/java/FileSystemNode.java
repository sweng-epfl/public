// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// You CANNOT edit the names, parameters or return types of existing methods/constructors
// You CANNOT add or remove checked exceptions to existing methods/constructors
// You CANNOT delete existing methods/constructors
// You CAN change the bodies of existing methods/constructors
// You CAN add new methods/constructors
// You CAN add interface implementations
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileSystemNode {
    private String name;
    private Permissions perm;
    private FileSystemNode parent;
    private ArrayList<FileSystemNode> children;

    private static final Permissions DEFAULT_FILE_PERMISSIONS = new Permissions("rw");
    private static final Permissions DEFAULT_DIRECTORY_PERMISSIONS = new Permissions("rw");

    public FileSystemNode(String name, Permissions perm) {
        this.name = name;
        this.perm = perm;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public void setParent(FileSystemNode p) {
        this.parent = p;
    }

    public String getName() {
        return name;
    }

    public FileSystemNode getParent() {
        return parent;
    }

    public FileSystemNode getChild(String name) throws NoSuchFileException {
        for (FileSystemNode c : children) {
            if (c.name.equals(name)) {
                return c;
            }
        }

        throw new NoSuchFileException(name);
    }

    public int getSizeInBytes() {
        int size = 0;

        for (FileSystemNode c : children) {
            size += c.getSizeInBytes();
        }

        return size;
    }

    /**
     * Returns the DIRECTORY in which the FILE is located (so we can chain
     * file system manipulations)
     */
    public FileSystemNode addFile(String filename, int sizeInBytes) throws FileAlreadyExistsException, IllegalArgumentException {
        // Bug 4:
        // bug => if (fileExists(filename));
        // fix => if (fileExists(filename)) throw new FileAlreadyExistsException(filename);
        if (fileExists(filename)) throw new FileAlreadyExistsException(filename);
        if (sizeInBytes < 0) throw new IllegalArgumentException();

        FileSystemNode child = new File(filename, DEFAULT_FILE_PERMISSIONS, sizeInBytes);
        child.setParent(this);
        children.add(child);
        return this;
    }

    /**
     * Returns the child directory that is created (so we can chain file system
     * manipulations.
     */
    public FileSystemNode addDirectory(String dirname) throws DirectoryAlreadyExistsException {
        if (fileExists(dirname)) throw new DirectoryAlreadyExistsException(dirname);

        FileSystemNode child = new FileSystemNode(dirname, DEFAULT_DIRECTORY_PERMISSIONS);
        child.setParent(this);
        children.add(child);
        return child;
    }

    public boolean isFile() {
        return false;
    }

    public void removeChild(String name) throws NoSuchFileException {
        OptionalInt indexOpt = IntStream.range(0, children.size())
                                        .filter(i -> children.get(i).name.equals(name))
                                        .findFirst();

        if (indexOpt.isPresent()) {
            // file exists
            children.remove(indexOpt.getAsInt());
        } else {
            // file doesn't exist
            throw new NoSuchFileException(name);
        }
    }

    public String getPermissions() {
        return perm.toString();
    }

    public void setPermissions(Permissions p) {
        perm = p;
    }

    private boolean fileExists(String filename) {
        return children.stream().anyMatch(c -> c.name.equals(filename));
    }

    public List<String> getChildrenNames() {
        return children.stream().map(c -> c.name).collect(Collectors.toList());
    }
}
