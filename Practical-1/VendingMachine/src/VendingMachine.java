import java.util.Scanner;

public class VendingMachine {

    enum Coin { ONE, TWO, FIVE, TEN }

    public static void main(String[] args) {
        final int snackPrice = 15;
        int total = 0;
        Scanner sc = new Scanner(System.in);

        while (total < snackPrice) {
            System.out.print("Insert coin (ONE/TWO/FIVE/TEN): ");
            String name = sc.next().toUpperCase();
            try {
                Coin coin = Coin.valueOf(name);
                int value = switch (coin) {
                    case ONE -> 1;
                    case TWO -> 2;
                    case FIVE -> 5;
                    case TEN -> 10;
                };
                total += value;
                System.out.println("Total so far: " + total);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid coin: " + name);
            }
        }
        System.out.println("Paid. Change: " + (total - snackPrice));
        sc.close();
    }
}
