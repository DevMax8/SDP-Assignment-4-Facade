package subsystem;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {

    private final Map<String, Integer> stock = new HashMap<>();

    public InventoryService() {
        stock.put("Laptop", 5);
        stock.put("Mouse", 10);
        stock.put("Keyboard", 7);
    }

    public boolean reserve(String product, int quantity) {

        if (quantity <= 0) {
            return false;
        }

        int available = stock.getOrDefault(product, 0);

        if (available < quantity) {
            System.out.println("Inventory: Not enough stock");
            return false;
        }

        stock.put(product, available - quantity);

        System.out.println("Inventory: Reserved " + quantity
                + " x " + product);

        return true;
    }

    public void release(String product, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        int available = stock.getOrDefault(product, 0);

        stock.put(product, available + quantity);

        System.out.println("Inventory: Stock released");
    }

    public int getStock(String product) {
        return stock.getOrDefault(product, 0);
    }
}