package service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import model.Account;

public class TransactionProcessor {

    // 4 reusable worker threads instead of one new thread per task
    private final ExecutorService pool = Executors.newFixedThreadPool(4);

    // thread-safe map shared by all worker threads
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    public void register(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Account find(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void submit(Runnable task) {
        pool.execute(task);
    }

    public void stop() throws InterruptedException {
        pool.shutdown();                                    // no new tasks; finish the queued ones
        pool.awaitTermination(30, TimeUnit.SECONDS);        // wait for completion
    }
}
