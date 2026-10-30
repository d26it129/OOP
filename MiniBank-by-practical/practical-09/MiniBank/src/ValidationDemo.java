import model.Command;
import model.SavingsAccount;
import util.CommandParser;
import util.StatementFormatter;
import util.Validator;

public class ValidationDemo {

    static void show(String label, String input, boolean result) {
        System.out.printf("%-8s %-14s -> %s%n", label, "\"" + input + "\"", result);
    }

    public static void main(String[] args) {
        System.out.println("--- Validators ---");
        show("Mobile", "9876543210", Validator.isValidMobile("9876543210"));
        show("Mobile", "12345", Validator.isValidMobile("12345"));
        show("Email", "abc@xyz.com", Validator.isValidEmail("abc@xyz.com"));
        show("Email", "abc@xyz", Validator.isValidEmail("abc@xyz"));
        show("PAN", "ABCDE1234F", Validator.isValidPan("ABCDE1234F"));
        show("PAN", "abcde1234f", Validator.isValidPan("abcde1234f"));
        show("IFSC", "SBIN0001234", Validator.isValidIfsc("SBIN0001234"));
        show("IFSC", "SBIN1001234", Validator.isValidIfsc("SBIN1001234"));
        show("Amount", "500", Validator.isValidAmount("500"));
        show("Amount", "-50", Validator.isValidAmount("-50"));

        System.out.println("\n--- CommandParser ---");
        Command cmd = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println("type          = " + cmd.type());
        System.out.println("accountNumber = " + cmd.accountNumber());
        System.out.println("amount        = " + cmd.amount());
        try {
            CommandParser.parse("DEPOSIT AC0001");
        } catch (IllegalArgumentException e) {
            System.out.println("Bad line rejected: " + e.getMessage());
        }

        System.out.println("\n--- StatementFormatter ---");
        System.out.println(StatementFormatter.buildStatement(new SavingsAccount("Riya", 4000, 500)));
    }
}
