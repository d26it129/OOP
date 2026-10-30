package service;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InvalidAmountException;
import exception.InvalidInputException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Account;
import model.CurrentAccount;
import model.Customer;
import model.FixedDepositAccount;
import model.SavingsAccount;
import util.Validator;
import util.StatementFormatter;

// The service layer: validates input, uses the repository, and reports failures as exceptions.
public class BankService {

    private final Repository<Account> repository;
    private final Repository<Customer> customers = new Repository<>();
    private final Path dataDir;

    public BankService(Repository<Account> repository, Path dataDir) {
        this.repository = repository;
        this.dataDir = dataDir;
    }

    private Path accountsFile() { return dataDir.resolve("accounts.dat"); }
    private Path logsDir()      { return dataDir.resolve("logs"); }
    private Path logFile()      { return logsDir().resolve("transactions.log"); }
    private Path reportFile()   { return dataDir.resolve("report.txt"); }

    // ---------- use cases ----------

    public Account openAccount(String ownerName, String mobile, String email, String type, long opening)
            throws BankException {
        if (!Validator.isValidName(ownerName)) throw new InvalidInputException("Invalid name: " + ownerName);
        if (!Validator.isValidMobile(mobile))  throw new InvalidInputException("Invalid mobile number: " + mobile);
        if (!Validator.isValidEmail(email))    throw new InvalidInputException("Invalid email: " + email);
        if (opening < 0)                       throw new InvalidAmountException(opening);

        Account account = switch (type.toUpperCase()) {
            case "SAVINGS" -> new SavingsAccount(ownerName, opening, 500);
            case "CURRENT" -> new CurrentAccount(ownerName, opening, 10000);
            case "FD"      -> {
                if (opening == 0) throw new InvalidAmountException(opening);
                yield new FixedDepositAccount(ownerName, opening);
            }
            default -> throw new InvalidInputException("Unknown account type: " + type + " (use SAVINGS, CURRENT or FD)");
        };
        customers.save(new Customer(ownerName, email, mobile));
        return repository.save(account);
    }

    public Account requireAccount(String accountNumber) throws AccountNotFoundException {
        Account account = repository.findById(accountNumber);
        if (account == null) throw new AccountNotFoundException(accountNumber);
        return account;
    }

    public void deposit(String accountNumber, long amount) throws BankException, IOException {
        requireAccount(accountNumber).deposit(amount);
        TransactionLog.append(logFile(), "DEPOSIT " + accountNumber + " " + amount);
    }

    public void withdraw(String accountNumber, long amount) throws BankException, IOException {
        requireAccount(accountNumber).withdraw(amount);
        TransactionLog.append(logFile(), "WITHDRAW " + accountNumber + " " + amount);
    }

    public void transfer(String from, String to, long amount) throws BankException, IOException {
        Account source = requireAccount(from);
        Account target = requireAccount(to);
        source.transfer(target, amount);
        TransactionLog.append(logFile(), "WITHDRAW " + from + " " + amount);
        TransactionLog.append(logFile(), "DEPOSIT " + to + " " + amount);
    }

    public String statement(String accountNumber) throws AccountNotFoundException {
        return StatementFormatter.buildStatement(requireAccount(accountNumber));
    }

    public List<Account> listAccounts() {
        ArrayList<Account> list = repository.findAll();
        list.sort(Comparator.naturalOrder());
        return list;
    }

    public List<Account> searchByOwner(String text) {
        List<Account> found = new ArrayList<>();
        for (Account a : listAccounts()) {
            if (a.getOwnerName().toLowerCase().contains(text.toLowerCase())) found.add(a);
        }
        return found;
    }

    // ---------- concurrent batch ----------

    // Runs 'count' deposits of 'amount' on a thread pool over the thread-safe store.
    public long runBatch(String accountNumber, int count, long amount) throws BankException, InterruptedException {
        Account account = requireAccount(accountNumber);
        TransactionProcessor processor = new TransactionProcessor();
        processor.register(account);
        for (int i = 0; i < count; i++) {
            processor.submit(() -> {
                try {
                    processor.find(accountNumber).deposit(amount);
                    TransactionLog.append(logFile(), "DEPOSIT " + accountNumber + " " + amount);
                } catch (BankException | IOException e) {
                    System.out.println("Batch task failed: " + e.getMessage());
                }
            });
        }
        processor.stop();
        return account.getBalance();
    }

    // ---------- persistence and report ----------

    public int loadState() throws IOException, ClassNotFoundException {
        if (!Files.exists(accountsFile())) return 0;
        Account[] loaded = StatePersister.load(accountsFile());
        for (Account a : loaded) repository.save(a);
        return loaded.length;
    }

    public void saveState() throws IOException {
        StatePersister.save(listAccounts().toArray(new Account[0]), accountsFile());
    }

    public String report() throws IOException {
        Files.createDirectories(logsDir());
        return ReportGenerator.generate(logsDir(), reportFile());
    }
}
