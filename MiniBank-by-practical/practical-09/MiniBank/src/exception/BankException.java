package exception;

// checked base class: callers are forced to handle every banking failure
public class BankException extends Exception {
    public BankException(String message) {
        super(message);
    }
}
