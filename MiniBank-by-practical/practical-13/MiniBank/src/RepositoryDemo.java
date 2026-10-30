import model.Account;
import model.CurrentAccount;
import model.Customer;
import model.FixedDepositAccount;
import model.SavingsAccount;
import service.Repository;

public class RepositoryDemo {
    public static void main(String[] args) {
        Repository<Account> accounts = new Repository<>();
        accounts.save(new SavingsAccount("Riya", 5000, 500));
        accounts.save(new CurrentAccount("Arjun", 2000, 1000));
        accounts.save(new FixedDepositAccount("Meera", 9000));

        System.out.println("--- Repository<Account> ---");
        System.out.println("size        : " + accounts.size());
        System.out.println("findById    : " + accounts.findById("AC0002"));
        System.out.println("findById(?) : " + accounts.findById("AC9999"));
        System.out.println("findAll     :");
        accounts.findAll().stream().sorted().forEach(a -> System.out.println("   " + a));
        System.out.println("delete AC0001 -> " + accounts.delete("AC0001") + ", size now " + accounts.size());

        System.out.println("\n--- the SAME class reused for Customer ---");
        Repository<Customer> customers = new Repository<>();
        Customer c1 = new Customer("Riya Shah", "riya@mail.com", "9876543210");
        Customer c2 = new Customer("Arjun Mehta", "arjun@mail.com", "9123456780");
        customers.saveAll(c1, c2);                                   // @SafeVarargs saveAll
        System.out.println("size        : " + customers.size());
        System.out.println("findById    : " + customers.findById(c2.getCustomerId()).getName());

        System.out.println("\n--- bounded generic method <N extends Number> ---");
        System.out.println("average(Integer[]) = " + Repository.average(new Integer[] { 10, 20, 30 }));
        System.out.println("average(Double[])  = " + Repository.average(new Double[] { 1.5, 2.5 }));
        // Repository.average(new String[] {"a"});    // compile-time error: String is not a Number
    }
}
