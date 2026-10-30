import model.Account;
import model.SavingsAccount;
import util.AnnotationValidator;

public class AnnotationDemo {

    static void check(String label, Account account) {
        String[] errors = AnnotationValidator.validate(account);
        System.out.println(label + " -> " + errors.length + " error(s)");
        for (String e : errors) System.out.println("    " + e);
    }

    public static void main(String[] args) {
        check("Valid account (balance 5000)   ", new SavingsAccount("Riya", 5000, 500));
        check("Negative balance (-100)        ", new SavingsAccount("Arjun", -100, 0));
        check("Owner name longer than 40 chars", new SavingsAccount("A".repeat(45), 3000, 0));
    }
}
