import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import model.Account;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;
import service.ReportGenerator;
import service.StatePersister;
import service.TransactionLog;

public class PersistenceDemo {
    public static void main(String[] args) throws Exception {
        Path data = Paths.get("data");
        Path logs = data.resolve("logs");
        Path day1 = logs.resolve("day1.log");
        Path day2 = logs.resolve("day2.log");
        Files.deleteIfExists(day1);
        Files.deleteIfExists(day2);

        Account[] accounts = {
            new SavingsAccount("Riya", 5000, 500),
            new CurrentAccount("Arjun", 2000, 1000),
            new FixedDepositAccount("Meera", 9000)
        };

        System.out.println("--- Saving accounts to data/accounts.dat ---");
        StatePersister.save(accounts, data.resolve("accounts.dat"));
        for (Account a : accounts) System.out.println("saved  : " + a);

        Account[] loaded = StatePersister.load(data.resolve("accounts.dat"));
        System.out.println("\n--- Loaded into a new array ---");
        for (Account a : loaded) System.out.println("loaded : " + a + "   (" + a.getClass().getSimpleName() + ")");
        System.out.println("Same data? " + loaded[0].equals(accounts[0]) + ", new object? " + (loaded[0] != accounts[0]));

        System.out.println("\n--- Writing transaction logs ---");
        TransactionLog.append(day1, "DEPOSIT AC0001 500");
        TransactionLog.append(day1, "WITHDRAW AC0002 200");
        TransactionLog.append(day1, "DEPOSIT AC0003 1500");
        TransactionLog.append(day2, "WITHDRAW AC0001 300");
        TransactionLog.append(day2, "DEPOSIT AC0002 700");
        System.out.println("day1.log lines:\n" + Files.readString(day1));

        System.out.println("--- Report ---");
        System.out.print(ReportGenerator.generate(logs, data.resolve("report.txt")));
    }
}
