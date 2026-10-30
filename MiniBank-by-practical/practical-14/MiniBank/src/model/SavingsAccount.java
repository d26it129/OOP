package model;

public class SavingsAccount extends Account implements Premium {

    private static final long serialVersionUID = 1L;

    private long minBalance;

    public SavingsAccount(String ownerName, long openingBalance, long minBalance) {
        super(ownerName, openingBalance);          // initialise the inherited fields
        this.minBalance = minBalance;
    }

    public long getMinBalance() { return minBalance; }

    @Override
    public double interestRate() { return 4.0; }

    // balance after the withdrawal must stay >= minBalance
    @Override
    public boolean canWithdraw(long amount) {
        return getBalance() - amount >= minBalance;
    }

    @Override
    protected long shortfallFor(long amount) {
        return amount - (getBalance() - minBalance);
    }
}
