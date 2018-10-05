public class CalculatorProgram {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.print("Wrong number of arguments.");
            return;
        }

        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[2]);
        Calculator calc = new Calculator();

        switch (args[1]) {
            case "+":
                System.out.print(calc.add(a, b));
                break;

            case "/":
                if (b == 0) {
                    System.out.print("Error!");
                } else {
                    System.out.print(calc.divide(a, b));
                }
                break;

            default:
                System.out.print("Unknown operation.");
                break;
        }
    }
}
