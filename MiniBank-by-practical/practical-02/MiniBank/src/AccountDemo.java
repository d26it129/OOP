public class AccountDemo {
    public static void main(String[] args) {
        Customer c = new Customer("Riya Shah", "riya@mail.com", "9876543210");
        System.out.println("Customer: " + c.getCustomerId() + " - " + c.getName());

        Account[] accounts = new Account[3];
        accounts[0] = new Account("Riya", 5000);
        accounts[1] = new Account("Arjun", 2000);
        accounts[2] = new Account("Meera");                // opening balance 0

        System.out.println("\n--- Riya: deposit(2000), withdraw(3000), withdraw(10000) ---");
        accounts[0].deposit(2000);
        System.out.println("After deposit(2000)    : " + accounts[0].getBalance());
        System.out.println("withdraw(3000) returns : " + accounts[0].withdraw(3000)
                + ", balance = " + accounts[0].getBalance());
        System.out.println("withdraw(10000) returns: " + accounts[0].withdraw(10000)
                + ", balance = " + accounts[0].getBalance());

        accounts[1].deposit(500);
        accounts[2].deposit(1500);
        accounts[2].withdraw(200);

        System.out.println("\n--- All balances ---");
        for (Account a : accounts) {
            System.out.println(a.getAccountNumber() + "  " + a.getOwnerName() + "  " + a.getBalance());
        }
        // accounts[0].balance = 1;      // compile error: balance is private
    }
}
