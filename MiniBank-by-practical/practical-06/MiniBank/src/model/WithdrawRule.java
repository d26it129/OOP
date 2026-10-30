package model;

// functional interface: exactly one abstract method, so a lambda can implement it
public interface WithdrawRule {
    boolean allow(Account account, long amount);
}
