package model;

// functional interface: exactly one abstract method, so a lambda can implement it
@FunctionalInterface
public interface WithdrawRule {
    boolean allow(Account account, long amount);
}
