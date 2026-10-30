import exception.BankException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import model.Account;
import service.BankService;
import service.Repository;

public class MiniBank {

    record BankInfo(String name, String branch) { }

    enum MenuOption {
        OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, STATEMENT,
        LIST_ACCOUNTS, SEARCH_BY_OWNER, RUN_BATCH, REPORT, EXIT
    }

    private static final Scanner sc = new Scanner(System.in);

    private static String ask(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static long askAmount(String prompt) {
        return Long.parseLong(ask(prompt));
    }

    public static void main(String[] args) throws Exception {
        Path dataDir = Paths.get("data");
        BankService service = new BankService(new Repository<Account>(), dataDir);

        BankInfo info = new BankInfo("MiniBank", "CSPIT Campus Branch");
        System.out.println("==========================================");
        System.out.println(info);
        System.out.println("==========================================");
        System.out.println("Loaded " + service.loadState() + " saved account(s).");

        MenuOption[] options = MenuOption.values();
        boolean running = true;

        while (running) {
            System.out.println();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }

            int choice;
            try {
                choice = Integer.parseInt(ask("Choose an option: "));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number between 1 and " + options.length + ".");
                continue;
            }
            if (choice < 1 || choice > options.length) {
                System.out.println("Invalid menu number. Try again.");
                continue;
            }

            try {
                switch (options[choice - 1]) {
                    case OPEN_ACCOUNT -> {
                        String name   = ask("Owner name      : ");
                        String mobile = ask("Mobile          : ");
                        String email  = ask("Email           : ");
                        String type   = ask("Type (SAVINGS/CURRENT/FD): ");
                        long opening  = askAmount("Opening balance: ");
                        Account a = service.openAccount(name, mobile, email, type, opening);
                        System.out.println("Account created: " + a);
                    }
                    case DEPOSIT -> {
                        String no = ask("Account number: ");
                        service.deposit(no, askAmount("Amount        : "));
                        System.out.println("Deposit successful. " + service.requireAccount(no));
                    }
                    case WITHDRAW -> {
                        String no = ask("Account number: ");
                        service.withdraw(no, askAmount("Amount        : "));
                        System.out.println("Withdrawal successful. " + service.requireAccount(no));
                    }
                    case TRANSFER -> {
                        String from = ask("From account: ");
                        String to   = ask("To account  : ");
                        service.transfer(from, to, askAmount("Amount      : "));
                        System.out.println("Transfer successful.");
                    }
                    case STATEMENT -> System.out.println(service.statement(ask("Account number: ")));
                    case LIST_ACCOUNTS -> {
                        if (service.listAccounts().isEmpty()) System.out.println("No accounts yet.");
                        service.listAccounts().forEach(System.out::println);
                    }
                    case SEARCH_BY_OWNER -> {
                        var found = service.searchByOwner(ask("Owner name contains: "));
                        if (found.isEmpty()) System.out.println("No match.");
                        found.forEach(System.out::println);
                    }
                    case RUN_BATCH -> {
                        String no = ask("Account number     : ");
                        int count = (int) askAmount("Number of deposits  : ");
                        long amt  = askAmount("Amount per deposit  : ");
                        System.out.println("Final balance after the concurrent batch: "
                                + service.runBatch(no, count, amt));
                    }
                    case REPORT -> System.out.print(service.report());
                    case EXIT -> {
                        service.saveState();
                        System.out.println("Accounts saved. Thank you for using MiniBank. Goodbye!");
                        running = false;
                    }
                }
            } catch (BankException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a whole number.");
            }
        }
        sc.close();
    }
}
