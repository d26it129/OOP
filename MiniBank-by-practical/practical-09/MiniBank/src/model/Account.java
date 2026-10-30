package model;

import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;

import model.annotation.Id;
import model.annotation.MaxLength;
import model.annotation.Positive;

public abstract class Account implements Transactable, InterestBearing {

    private static int counter = 0;                 // shared by all accounts

    @Id
    private final String accountNumber;             // never changes after creation
    @MaxLength(40)
    private String ownerName;
    @Positive
    private long balance;                           // whole rupees; no public setter
    private boolean active;

    private static String generateAccountNumber() {
        counter++;
        return String.format("AC%04d", counter);
    }

    protected Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    // used to rebuild an existing account (e.g. when comparing or restoring); does not use the counter
    protected Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    protected Account(String ownerName) {
        this(ownerName, 0);                         // constructor chaining
    }

    // every account type supplies its own rules
    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException(amount);
        balance += amount;
    }

    @Override
    public synchronized void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException(amount);
        if (!canWithdraw(amount)) throw new InsufficientFundsException(shortfallFor(amount));
        balance -= amount;
    }

    // Demo only: the same deposit WITHOUT synchronization. The yield() mimics a slow update
    // (e.g. a database write) and makes the race condition visible on every run.
    public void depositUnsafe(long amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException(amount);
        long current = balance;                     // 1. read
        Thread.yield();                             // another thread may run here
        balance = current + amount;                 // 2. write (overwrites the other thread's update)
    }

    // how much more money would be needed for this withdrawal to be allowed
    protected long shortfallFor(long amount) {
        return amount - balance;
    }

    public void transfer(Account to, long amount) throws BankException {
        boolean withdrawn = false;
        try {
            withdraw(amount);
            withdrawn = true;
            to.deposit(amount);
        } catch (BankException e) {
            if (withdrawn) balance += amount;       // undo the debit if the credit failed
            throw e;                                // rethrow to the caller
        } finally {
            System.out.println("[audit] transfer " + accountNumber + " -> " + to.accountNumber
                    + " amount " + amount);
        }
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }

    // two accounts are equal when their account numbers are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account other = (Account) o;
        return accountNumber.equals(other.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName()     { return ownerName; }
    @Override
    public synchronized long getBalance()         { return balance; }
    public boolean isActive()        { return active; }
}
