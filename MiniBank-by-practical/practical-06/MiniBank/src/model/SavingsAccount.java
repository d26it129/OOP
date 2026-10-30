package model;

public class SavingsAccount extends Account implements Premium {

    private long minBalance;

    public SavingsAccount(String ownerName, long openingBalance, long minBalance) {
        super(ownerName, openingBalance);          // initialise the inherited fields
        this.minBalance = minBalance;
    }

    public long getMinBalance() { return minBalance; }

    public double interestRate() { return 4.0; }

    // balance after the withdrawal must stay >= minBalance
    public boolean canWithdraw(long amount) {
        return getBalance() - amount >= minBalance;
    }
}
