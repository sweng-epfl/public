// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

public class File extends FileSystemNode {
    private int sizeInBytes;

    public File(String name, Permissions perm, int sizeInBytes) {
        super(name, perm);
        this.sizeInBytes = sizeInBytes;
    }

    public int getSizeInBytes() {
        return sizeInBytes;
    }

    public boolean isFile() {
        return true;
    }
}
