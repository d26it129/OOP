import static util.Validator.isValidMobile;          // static import: no class name needed

import model.Account;
import model.CurrentAccount;
import model.Premium;
import model.SavingsAccount;
import model.WithdrawRule;
import service.RuleService;

public class InterfaceDemo {
    public static void main(String[] args) {
        Account savings = new SavingsAccount("Riya", 50000, 1000);
        Account current = new CurrentAccount("Arjun", 8000, 2000);

        System.out.println("--- default method yearlyInterest() ---");
        System.out.println("Savings: " + savings.yearlyInterest());
        System.out.println("Current: " + current.yearlyInterest());

        // 1) anonymous class
        WithdrawRule anonymous = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount <= 5000;
            }
        };
        // 2) the same rule as a lambda
        WithdrawRule lambda = (account, amount) -> amount <= 5000;

        System.out.println("\n--- WithdrawRule: anonymous class vs lambda ---");
        for (long amt : new long[] { 3000, 7000 }) {
            System.out.println("amount " + amt + " -> anonymous=" + anonymous.allow(savings, amt)
                    + ", lambda=" + lambda.allow(savings, amt)
                    + ", service cap=" + RuleService.check(RuleService.PER_TRANSACTION_CAP, savings, amt));
        }

        System.out.println("\n--- marker interface Premium ---");
        for (Account a : new Account[] { savings, current }) {
            System.out.println(a.getOwnerName() + " premium? " + (a instanceof Premium));
        }

        System.out.println("\n--- static import ---");
        System.out.println("isValidMobile(\"9876543210\") = " + isValidMobile("9876543210"));
    }
}
