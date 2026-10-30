package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import model.Account;

public class AccountStore {

    private final HashMap<String, Account> accounts = new HashMap<>();   // key = account number
    private final ArrayList<String> history = new ArrayList<>();

    public void add(Account account) {
        accounts.put(account.getAccountNumber(), account);
        history.add("ADDED " + account.getAccountNumber() + " " + account.getOwnerName());
    }

    public Account get(String accountNumber) {
        return accounts.get(accountNumber);               // O(1) look-up by key
    }

    public List<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> list = new ArrayList<>(accounts.values());
        list.sort(Comparator.comparingLong(Account::getBalance).reversed());   // highest first
        return list;
    }

    public List<String> getHistory() {
        return history;
    }
}
