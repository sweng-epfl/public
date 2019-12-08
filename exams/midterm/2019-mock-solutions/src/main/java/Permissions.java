// !!!!!!!!!!!!!!!!!!!!!!
// DO NOT TOUCH THIS FILE
// !!!!!!!!!!!!!!!!!!!!!!

public class Permissions {
    private boolean read;
    private boolean write;

    public final static int rIdx = 0;
    public final static int wIdx = 1;

    public Permissions(String rwx) {
        if (rwx.length() != 2) {
            throw new IllegalArgumentException();
        }
        if (rwx.charAt(rIdx) != 'r' && rwx.charAt(rIdx) != '-') {
            throw new IllegalArgumentException();
        }

        if (rwx.charAt(wIdx) != 'w' && rwx.charAt(wIdx) != '-') {
            throw new IllegalArgumentException();
        }

        this.read = rwx.charAt(rIdx) == 'r';
        this.write = rwx.charAt(wIdx) == 'w';
    }

    public void setRead(boolean state) {
        read = state;
    }

    public void setWrite(boolean state) {
        write = state;
    }

    public String toString() {
        String readStr = read ? "r" : "-";
        String writeStr = write ? "w" : "-";

        return readStr + writeStr;
    }
}
