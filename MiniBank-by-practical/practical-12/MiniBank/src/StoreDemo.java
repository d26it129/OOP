import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;
import service.AccountStore;
import service.TransactionProcessor;

public class StoreDemo {
    public static void main(String[] args) throws InterruptedException {
        AccountStore store = new AccountStore();
        store.add(new SavingsAccount("Riya", 5000, 500));
        store.add(new CurrentAccount("Arjun", 2000, 1000));
        store.add(new FixedDepositAccount("Meera", 9000));

        System.out.println("--- get(\"AC0002\") ---");
        System.out.println(store.get("AC0002"));
        System.out.println("get(\"AC9999\") -> " + store.get("AC9999"));

        System.out.println("\n--- sortedByBalance() (Comparator, highest first) ---");
        for (Account a : store.sortedByBalance()) System.out.println(a);

        System.out.println("\n--- natural order with Comparable (by account number) ---");
        List<Account> all = new ArrayList<>(store.getAll());
        Collections.sort(all);
        for (Account a : all) System.out.println(a);

        System.out.println("\n--- history (ArrayList) ---");
        store.getHistory().forEach(System.out::println);

        System.out.println("\n--- ConcurrentHashMap inside TransactionProcessor ---");
        TransactionProcessor processor = new TransactionProcessor();
        for (Account a : store.getAll()) processor.register(a);
        Account shared = processor.find("AC0003");
        for (int i = 0; i < 100; i++) {
            processor.submit(() -> {
                try { shared.deposit(10); } catch (exception.BankException e) { System.out.println(e.getMessage()); }
            });
        }
        processor.stop();
        System.out.println("AC0003 after 100 concurrent deposits of 10: " + shared.getBalance() + " (expected 10000)");
    }
}
