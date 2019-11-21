package ex5;

public enum Logger2 {
    INSTANCE;

    public Logger2 getInstance() {
        return INSTANCE;
    }

    protected void print() {
        System.out.println("Logged");
    }
}
