import java.util.Scanner;

public class GuardedCalculator {

    static double calculate(double a, String op, double b) throws DivideByZeroException {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) throw new DivideByZeroException("Cannot divide " + a + " by zero");
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Unknown operator: " + op);
        };
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean done = false;
        int attempt = 0;
        while (!done) {
            attempt++;
            try {
                System.out.print("First number : ");  String x = sc.next();
                System.out.print("Operator     : ");  String op = sc.next();
                System.out.print("Second number: ");  String y = sc.next();
                double result = calculate(Double.parseDouble(x), op, Double.parseDouble(y));
                System.out.println("Result = " + result);
                done = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: that is not a valid number.");
            } catch (DivideByZeroException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                System.out.println("[log] attempt " + attempt + " finished");
            }
        }
        sc.close();
    }
}
