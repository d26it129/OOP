import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.Account;
import model.SavingsAccount;
import util.AuditSession;

public class ExceptionDemo {
    public static void main(String[] args) {
        Account riya = new SavingsAccount("Riya", 1000, 0);
        Account arjun = new SavingsAccount("Arjun", 500, 0);

        System.out.println("--- 1. withdraw more than the balance ---");
        try {
            riya.withdraw(5000);
            System.out.println("Withdrawal done");
        } catch (InsufficientFundsException e) {
            System.out.println("Withdrawal failed: short by " + e.getShortfall());
        } catch (InvalidAmountException e) {
            System.out.println("Invalid amount: " + e.getMessage());
        } finally {
            System.out.println("Balance is still " + riya.getBalance() + " (finally always runs)");
        }

        System.out.println("\n--- 2. invalid amounts ---");
        try {
            riya.deposit(-50);
        } catch (BankException e) {
            System.out.println("Deposit rejected: " + e.getMessage());
        }
        try {
            riya.withdraw(0);
        } catch (BankException e) {
            System.out.println("Withdraw rejected: " + e.getMessage());
        }

        System.out.println("\n--- 3. transfer with try / catch / finally ---");
        try {
            riya.transfer(arjun, 300);
            System.out.println("Transfer OK -> Riya " + riya.getBalance() + ", Arjun " + arjun.getBalance());
            riya.transfer(arjun, 9000);
        } catch (BankException e) {
            System.out.println("Transfer failed: " + e.getMessage());
            System.out.println("Balances unchanged -> Riya " + riya.getBalance() + ", Arjun " + arjun.getBalance());
        }

        System.out.println("\n--- 4. try-with-resources ---");
        try (AuditSession session = new AuditSession("end-of-day")) {
            session.record("checking Riya's balance: " + riya.getBalance());
            riya.withdraw(100000);                      // throws inside the block
            session.record("this line is never reached");
        } catch (BankException e) {
            System.out.println("Caught after the session was closed: " + e.getMessage());
        }
    }
}
