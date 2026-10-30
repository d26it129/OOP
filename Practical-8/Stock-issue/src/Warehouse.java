import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private final Map<String, Integer> stock = new HashMap<>();

    public Warehouse() {
        stock.put("pen", 50);
        stock.put("book", 10);
        stock.put("bag", 3);
    }

    public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) throw new InvalidQuantityException(qty);
        int have = stock.getOrDefault(item, 0);
        if (qty > have) throw new OutOfStockException(item, qty - have);
        stock.put(item, have - qty);
        System.out.println("Issued " + qty + " x " + item + " (left " + (have - qty) + ")");
    }

    public static void main(String[] args) {
        Warehouse w = new Warehouse();
        String[] items = { "pen", "bag", "book", "laptop", "pen" };
        int[] qtys     = {  20,     5,     -2,     1,        30  };
        for (int i = 0; i < items.length; i++) {
            try {
                w.issue(items[i], qtys[i]);
            } catch (OutOfStockException e) {
                System.out.println("FAILED: " + e.getMessage());
            } catch (InvalidQuantityException e) {
                System.out.println("FAILED: " + e.getMessage());
            }
        }
        System.out.println("All requests processed.");
    }
}
