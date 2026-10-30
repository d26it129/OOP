public class InvalidQuantityException extends Exception {
    public InvalidQuantityException(int qty) {
        super("Invalid quantity: " + qty + " (must be > 0)");
    }
}
