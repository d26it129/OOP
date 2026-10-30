public abstract class Account {

    private static int counter = 0;                 // shared by all accounts

    private final String accountNumber;             // never changes after creation
    private String ownerName;
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

    public void deposit(long amount) {
        if (amount <= 0) return;                    // reject non-positive amounts
        balance += amount;
    }

    public boolean withdraw(long amount) {
        if (amount <= 0 || !canWithdraw(amount)) return false;   // rule decided by the subclass
        balance -= amount;
        return true;
    }

    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }

    // two accounts are equal when their account numbers are equal
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account other = (Account) o;
        return accountNumber.equals(other.accountNumber);
    }

    public int hashCode() {
        return accountNumber.hashCode();
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName()     { return ownerName; }
    public long getBalance()         { return balance; }
    public boolean isActive()        { return active; }
}
