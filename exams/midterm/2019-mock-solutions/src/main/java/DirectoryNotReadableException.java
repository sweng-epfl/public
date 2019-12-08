// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

public class DirectoryNotReadableException extends Exception {
    private String msg;

    public DirectoryNotReadableException(String path) {
        this.msg = "Directory is not readable: " + path;
    }

    public String toString() {
        return msg;
    }
}
