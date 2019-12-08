// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

public class DirectoryAlreadyExistsException extends Exception {
    private String msg;

    public DirectoryAlreadyExistsException(String path) {
        this.msg = "Directory already exists: " + path;
    }

    public String toString() {
        return msg;
    }
}
