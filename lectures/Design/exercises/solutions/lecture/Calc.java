import java.util.*;

public class App {
    public static void main(String[] args) {
        while(true) {
            var text = getInput();
            if (text == null) {
                break;
            }
            var tokens = parseInput(text);
            var result = compute(tokens);
            display(result);
        }
    }

    /** Gets input, or returns `null` if there is no more input. */
    private static String getInput() {
        var scanner = new Scanner(System.in);
        System.out.print("Computation to perform? (in reverse Polish notation; or 'exit') ");
        var text = scanner.nextLine().trim();
        if (text.equals("exit")) {
            return null;
        }
        return text;
    }

    /** Parses text into a sequence of objects, either `Integer` or `String`. */
    private static List<Object> parseInput(String text) {
        var parts = text.split(" ");
        var result = new ArrayList<Object>();
        for (String p : parts) {
            if (p.equals("")) {
                continue;
            }
            try {
                result.add(Integer.parseInt(p));
            } catch (NumberFormatException e) {
                result.add(p);
            }
        }
        return result;
    }

    /** Computes the result given a list of integers and operators in reverse Polish notation, or returns `null` if the computation is invalid. */
    private static Integer compute(List<Object> tokens) {
        var stack = new ArrayDeque<Integer>();
        for (Object t : tokens) {
            if (t instanceof Integer) {
                stack.push((Integer) t);
            } else {
                if (stack.size() < 2) {
                    return null;
                }
                var right = stack.pop();
                var left = stack.pop();
                var result = execute((String) t, left, right);
                if (result == null) {
                    return null;
                }
                stack.push(result);
            }
        }
        if (stack.size() == 1) {
            return stack.pop();
        }
        return null;
    }

    /** Executes the given operator on the given inputs. */
    private static Integer execute(String operator, int left, int right) {
        switch (operator) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                return left / right;
            default:
                return null;
        }
    }

    /** Displays the result, or an error if there is none. */
    private static void display(Integer result) {
        if (result == null) {
            System.out.println("Invalid computation");
        } else {
            System.out.println(result);
        }
    }
}
