import exception.BankException;
import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;

public class PolymorphismDemo {

    static String tryWithdraw(Account a, long amount) {
        try {
            a.withdraw(amount);
            return "true";
        } catch (BankException e) {
            return "false (" + e.getMessage() + ")";
        }
    }

    public static void main(String[] args) {
        Account[] accounts = {
            new SavingsAccount("Riya", 5000, 1000),
            new CurrentAccount("Arjun", 1000, 5000),
            new FixedDepositAccount("Meera", 20000)
        };

        System.out.println("--- interestRate(): the subclass version runs at run time ---");
        for (Account a : accounts) {
            System.out.println(a.getClass().getSimpleName() + " " + a.getAccountNumber()
                    + " -> interest " + a.interestRate() + "%");
        }

        System.out.println("\n--- withdraw rules ---");
        System.out.println("Savings  withdraw(3000) : " + tryWithdraw(accounts[0], 3000)
                + "  (balance " + accounts[0].getBalance() + ")");
        System.out.println("Savings  withdraw(2000) : " + tryWithdraw(accounts[0], 2000)
                + " (would break the minimum balance, balance " + accounts[0].getBalance() + ")");
        System.out.println("Current  withdraw(4000) : " + tryWithdraw(accounts[1], 4000)
                + "  (balance " + accounts[1].getBalance() + ", overdraft used)");
        System.out.println("Fixed    withdraw(100)  : " + tryWithdraw(accounts[2], 100)
                + " (deposit is locked, balance " + accounts[2].getBalance() + ")");

        System.out.println("\n--- pattern instanceof ---");
        for (Account a : accounts) {
            if (a instanceof SavingsAccount s) {
                System.out.println(s.getOwnerName() + " has a savings account, minimum balance " + s.getMinBalance());
            } else {
                System.out.println(a.getOwnerName() + " is not a savings account");
            }
        }
    }
}
