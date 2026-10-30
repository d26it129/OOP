package model;

public class CurrentAccount extends Account {

    private long overdraftLimit;

    public CurrentAccount(String ownerName, long openingBalance, long overdraftLimit) {
        super(ownerName, openingBalance);
        this.overdraftLimit = overdraftLimit;
    }

    public long getOverdraftLimit() { return overdraftLimit; }

    public double interestRate() { return 0; }

    // balance may fall down to -overdraftLimit
    public boolean canWithdraw(long amount) {
        return getBalance() - amount >= -overdraftLimit;
    }
}
