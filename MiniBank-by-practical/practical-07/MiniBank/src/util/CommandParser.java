package util;

import model.Command;
import model.TransactionType;

public class CommandParser {

    // "DEPOSIT AC0001 500"  ->  Command(DEPOSIT, "AC0001", 500)
    public static Command parse(String line) {
        if (line == null) throw new IllegalArgumentException("Command is empty");
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Expected 3 parts (TYPE ACCOUNT AMOUNT) but got " + parts.length);
        }
        TransactionType type = TransactionType.valueOf(parts[0].toUpperCase());
        if (!Validator.isValidAmount(parts[2])) {
            throw new IllegalArgumentException("Amount must be a positive whole number: " + parts[2]);
        }
        return new Command(type, parts[1], Long.parseLong(parts[2]));
    }
}
