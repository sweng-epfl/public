// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

public class NoSuchPathException extends Exception {
    private String msg;

    public NoSuchPathException(String path) {
        this.msg = "No such path " + path;
    }

    public String toString() {
        return msg;
    }
}
