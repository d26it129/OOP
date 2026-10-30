public class OutOfStockException extends Exception {
    private final int shortfall;
    public OutOfStockException(String item, int shortfall) {
        super("Out of stock: " + item + " (short by " + shortfall + ")");
        this.shortfall = shortfall;
    }
    public int getShortfall() { return shortfall; }
}
