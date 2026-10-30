package service;

import model.Account;
import model.WithdrawRule;

public class RuleService {

    // a ready-made rule written as a lambda: at most Rs. 10000 per withdrawal
    public static final WithdrawRule PER_TRANSACTION_CAP = (account, amount) -> amount <= 10000;

    public static boolean check(WithdrawRule rule, Account account, long amount) {
        return account.isActive() && rule.allow(account, amount);
    }
}
