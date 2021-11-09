package intellij;

public class GoodClassName extends SuperClass {
    private String goodAttributeName;

    public GoodClassName(String s) {
        goodAttributeName = s;
    }

    public String longAndComplicatedMethod() {
        int n = computeN();
        return modifyString(n);
    }

    private int computeN() {
        int n = goodAttributeName.length();
        n *= 51;
        n >>= 3;
        n %= 5;
        n = Math.max(n, 1);
        return n;
    }

    private String modifyString(int n) {
        String s = goodAttributeName.repeat(n);
        s = s.concat("!!");
        s = s.toUpperCase();
        s = s.replaceAll("[AEIOUY]", "<3");
        s = s.concat(goodAttributeName);
        s = s.repeat(n);
        s = s.substring(s.length() / 2);
        return s;
    }

    public void multiplesOf1() {
        multiplesOf(1);
    }

    public void multiplesOf2() {
        multiplesOf(2);
    }

    private void multiplesOf(int a) {
        System.out.printf("=== Multiples of %d ===\n", a);
        for (int i = 1; i <= 12; i++) {
            System.out.printf("%d * %d = ", i, a);
            System.out.print(i * a);
            System.out.print("\n");
        }
    }
}
