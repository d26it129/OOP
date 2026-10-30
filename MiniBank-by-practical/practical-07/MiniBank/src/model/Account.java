package model;

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
    public void deposit(long amount) {
        if (amount <= 0) return;                    // reject non-positive amounts
        balance += amount;
    }

    @Override
    public boolean withdraw(long amount) {
        if (amount <= 0 || !canWithdraw(amount)) return false;   // rule decided by the subclass
        balance -= amount;
        return true;
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
    public long getBalance()         { return balance; }
    public boolean isActive()        { return active; }
}
