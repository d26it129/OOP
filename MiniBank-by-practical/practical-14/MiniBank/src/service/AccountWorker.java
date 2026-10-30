package service;

import exception.InvalidAmountException;
import model.Account;

public class AccountWorker implements Runnable {

    private final Account account;
    private final int times;
    private final long amount;
    private final boolean safe;

    public AccountWorker(Account account, int times, long amount, boolean safe) {
        this.account = account;
        this.times = times;
        this.amount = amount;
        this.safe = safe;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started (state "
                + Thread.currentThread().getState() + ")");
        try {
            for (int i = 0; i < times; i++) {
                if (safe) account.deposit(amount);          // synchronized
                else      account.depositUnsafe(amount);    // not synchronized
            }
        } catch (InvalidAmountException e) {
            System.out.println(Thread.currentThread().getName() + ": " + e.getMessage());
        }
    }
}
