package model;

public class CurrentAccount extends Account {

    private static final long serialVersionUID = 1L;

    private long overdraftLimit;

    public CurrentAccount(String ownerName, long openingBalance, long overdraftLimit) {
        super(ownerName, openingBalance);
        this.overdraftLimit = overdraftLimit;
    }

    public long getOverdraftLimit() { return overdraftLimit; }

    @Override
    public double interestRate() { return 0; }

    // balance may fall down to -overdraftLimit
    @Override
    public boolean canWithdraw(long amount) {
        return getBalance() - amount >= -overdraftLimit;
    }

    @Override
    protected long shortfallFor(long amount) {
        return amount - (getBalance() + overdraftLimit);
    }
}
