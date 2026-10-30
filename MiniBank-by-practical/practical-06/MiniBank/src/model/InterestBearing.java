package model;

public interface InterestBearing {
    double interestRate();
    long getBalance();

    // default method: gives every implementing class a ready-made behaviour
    default double yearlyInterest() {
        return getBalance() * interestRate() / 100.0;
    }
}
