public class AccountDemo {
    public static void main(String[] args) {
        Account a1 = new Account("Riya", 4000);
        Account a2 = new Account(a1.getAccountNumber(), "Riya", 4000);   // same account number
        Account a3 = new Account("Arjun", 2500);

        System.out.println("--- toString() ---");
        System.out.println(a1);
        System.out.println(a3);

        System.out.println("\n--- equals() / hashCode() ---");
        System.out.println("a1 == a2       : " + (a1 == a2));
        System.out.println("a1.equals(a2)  : " + a1.equals(a2));
        System.out.println("a1.equals(a3)  : " + a1.equals(a3));
        System.out.println("same hashCode  : " + (a1.hashCode() == a2.hashCode()));

        System.out.println("\n--- instanceof ---");
        Object[] things = { a1, "hello", 42 };
        for (Object o : things) {
            System.out.println(o + " is an Account? " + (o instanceof Account));
        }

        System.out.println("\n--- Customer, nested Address and clone() ---");
        Customer.Address addr = new Customer.Address("12 MG Road", "Ahmedabad", "380001");
        Customer c = new Customer("Riya Shah", "riya@mail.com", "9876543210", addr);
        Customer copy = c.clone();
        System.out.println("Address        : " + c.getAddress());
        System.out.println("clone same id  : " + copy.getCustomerId().equals(c.getCustomerId()));
        System.out.println("clone != orig  : " + (copy != c));
        System.out.println("address copied : " + (copy.getAddress() != c.getAddress())
                + " (city " + copy.getAddress().getCity() + ")");
    }
}
