// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

public class FileNotWriteableException extends Exception {
    private String msg;

    public FileNotWriteableException(String path) {
        this.msg = "File is not writeable: " + path;
    }

    public String toString() {
        return msg;
    }
}
