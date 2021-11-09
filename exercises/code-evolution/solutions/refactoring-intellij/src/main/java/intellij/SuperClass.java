package intellij;

public class SuperClass {
    public static final int COMMON_CONSTANT = 4761;

    public void commonMethod() {
        int a = 1, b = 1;
        int c = a + b;
        System.out.println(a + "+" + b + "=" + c);
    }
}
