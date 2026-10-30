import java.util.Scanner;

public class MiniBank {

    // A record is a short, read-only class: constructor and accessors are generated.
    record BankInfo(String name, String branch) { }

    enum MenuOption { OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT }

    public static void main(String[] args) {
        BankInfo info = new BankInfo("MiniBank", "CSPIT Campus Branch");
        System.out.println("==========================================");
        System.out.println(info);
        System.out.println("==========================================");

        Scanner sc = new Scanner(System.in);
        MenuOption[] options = MenuOption.values();
        boolean running = true;

        while (running) {
            System.out.println();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number between 1 and " + options.length + ".");
                continue;
            }
            if (choice < 1 || choice > options.length) {
                System.out.println("Invalid menu number. Try again.");
                continue;
            }

            MenuOption option = options[choice - 1];
            String message = switch (option) {
                case OPEN_ACCOUNT -> "Open Account - to be implemented in a later lab";
                case DEPOSIT      -> "Deposit - to be implemented in a later lab";
                case WITHDRAW     -> "Withdraw - to be implemented in a later lab";
                case TRANSFER     -> "Transfer - to be implemented in a later lab";
                case EXIT         -> {
                    running = false;
                    yield "Thank you for using MiniBank. Goodbye!";
                }
            };
            System.out.println(message);
        }
        sc.close();
    }
}
