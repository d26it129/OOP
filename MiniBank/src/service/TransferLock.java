package service;

import exception.BankException;
import model.Account;

public class TransferLock {

    private static void pause() {
        try { Thread.sleep(150); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    // DEADLOCK-PRONE: locks "from" first, then "to". A->B and B->A lock in opposite order.
    public static void transferUnsafe(Account from, Account to, long amount) throws BankException {
        synchronized (from) {
            pause();
            synchronized (to) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    // SAFE: always lock the account with the smaller account number first.
    public static void transferSafe(Account from, Account to, long amount) throws BankException {
        Account first  = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        Account second = (first == from) ? to : from;
        synchronized (first) {
            pause();
            synchronized (second) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }
}
