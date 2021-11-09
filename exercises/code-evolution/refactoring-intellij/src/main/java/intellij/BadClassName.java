package intellij;

public class BadClassName {
    // This name is really bad
    private String badAttributeName;

    // This constant will most likely be common to multiple classes
    public static final int COMMON_CONSTANT = 4761;

    public BadClassName(String s) {
        badAttributeName = s;
    }

    // This method will most likely be common to multiple classes
    public void commonMethod() {
        int a = 1, b = 1;
        int c = a + b;
        System.out.println(a + "+" + b + "=" + c);
    }

    // This method is very long and hard to understand
    public String longAndComplicatedMethod() {
        // Compute some value of n
        int n = getLengthOfOurAttribute();
        n *= 51;
        n >>= 3;
        n %= 5;
        n = Math.max(n, 1);

        // Do some stuff to the string, based on n
        String s = badAttributeName.repeat(n);
        s = s.concat("!!");
        s = s.toUpperCase();
        s = s.replaceAll("[AEIOUY]", "<3");
        s = s.concat(badAttributeName);
        s = s.repeat(n);
        s = s.substring(s.length()/2);

        return s;
    }

    // This method returns the length of our attribute
    private int getLengthOfOurAttribute() {
        return badAttributeName.length();
    }

    // Intellij sometimes recognizes code smells and offers you quick fixes
    public void multiplesOf1() {
        int a = 1;
        System.out.printf("=== Multiples of %d ===\n", a);
        for(int i = 1; i <= 12; i++) {
            System.out.printf("%d * %d = ", i, a);
            System.out.print(i * a);
            System.out.print("\n");
        }
    }

    public void multiplesOf2() {
        int a = 2;
        System.out.printf("=== Multiples of %d ===\n", a);
        for(int i = 1; i <= 12; i++) {
            System.out.printf("%d * %d = ", i, a);
            System.out.print(i * a);
            System.out.print("\n");
        }
    }
}
