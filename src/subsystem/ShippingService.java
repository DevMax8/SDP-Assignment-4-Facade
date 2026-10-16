package subsystem;

import java.util.HashSet;
import java.util.Set;

public class ShippingService {

    private final Set<Integer> shipments = new HashSet<>();

    public boolean arrangeShipping(int orderId, String product) {

        if (product == null || product.isBlank()) {
            return false;
        }

        if (shipments.contains(orderId)) {
            return false;
        }

        // Simulated shipping failure
        if (product.equalsIgnoreCase("Keyboard")) {
            System.out.println("Shipping: Delivery unavailable");
            return false;
        }

        shipments.add(orderId);

        System.out.println("Shipping: Arranged for order #" + orderId);

        return true;
    }

    public void cancelShipping(int orderId) {

        if (shipments.remove(orderId)) {
            System.out.println("Shipping: Cancelled");
        }
    }
}