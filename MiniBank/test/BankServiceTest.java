import static org.junit.jupiter.api.Assertions.*;

import exception.AccountNotFoundException;
import exception.InsufficientFundsException;
import exception.InvalidInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import model.Account;
import org.junit.jupiter.api.Test;
import service.BankService;
import service.Repository;

class BankServiceTest {

    private BankService newService() throws Exception {
        Path dir = Files.createTempDirectory("minibank-test");
        return new BankService(new Repository<Account>(), dir);
    }

    @Test
    void openingAccountIncreasesRepositoryCount() throws Exception {
        BankService service = newService();
        service.openAccount("Riya Shah", "9876543210", "riya@mail.com", "SAVINGS", 1000);
        service.openAccount("Arjun Mehta", "9123456780", "arjun@mail.com", "CURRENT", 0);
        assertEquals(2, service.listAccounts().size());
    }

    @Test
    void depositIncreasesBalance() throws Exception {
        BankService service = newService();
        Account a = service.openAccount("Riya Shah", "9876543210", "riya@mail.com", "SAVINGS", 1000);
        service.deposit(a.getAccountNumber(), 500);
        assertEquals(1500, a.getBalance());
    }

    @Test
    void withdrawalOverTheLimitThrows() throws Exception {
        BankService service = newService();
        Account a = service.openAccount("Riya Shah", "9876543210", "riya@mail.com", "SAVINGS", 1000);
        InsufficientFundsException ex = assertThrows(InsufficientFundsException.class,
                () -> service.withdraw(a.getAccountNumber(), 5000));
        assertEquals(4500, ex.getShortfall());          // 5000 - (1000 - minimum balance 500)
        assertEquals(1000, a.getBalance());             // balance unchanged
    }

    @Test
    void invalidMobileIsRejected() throws Exception {
        BankService service = newService();
        assertThrows(InvalidInputException.class,
                () -> service.openAccount("Riya Shah", "12345", "riya@mail.com", "SAVINGS", 100));
    }

    @Test
    void unknownAccountThrowsNotFound() throws Exception {
        BankService service = newService();
        assertThrows(AccountNotFoundException.class, () -> service.statement("AC9999"));
    }

    @Test
    void manyConcurrentDepositsKeepBalanceCorrect() throws Exception {
        BankService service = newService();
        Account a = service.openAccount("Riya Shah", "9876543210", "riya@mail.com", "SAVINGS", 0);
        long finalBalance = service.runBatch(a.getAccountNumber(), 500, 10);   // 500 threads-tasks x 10
        assertEquals(5000, finalBalance);
    }

    @Test
    void savedStateLoadsUnchanged() throws Exception {
        Path dir = Files.createTempDirectory("minibank-state");
        BankService first = new BankService(new Repository<Account>(), dir);
        Account a = first.openAccount("Riya Shah", "9876543210", "riya@mail.com", "SAVINGS", 2500);
        first.saveState();

        BankService second = new BankService(new Repository<Account>(), dir);
        assertEquals(1, second.loadState());
        Account loaded = second.requireAccount(a.getAccountNumber());
        assertEquals(2500, loaded.getBalance());
        assertEquals("Riya Shah", loaded.getOwnerName());
    }
}
