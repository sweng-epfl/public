// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

public class DirectoryNotWriteableException extends Exception {
    private String msg;

    public DirectoryNotWriteableException(String path) {
        this.msg = "Directory is not writeable: " + path;
    }

    public String toString() {
        return msg;
    }
}
