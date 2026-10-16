package subsystem;

import java.util.HashSet;
import java.util.Set;

public class PaymentProcessor {

    private final Set<Integer> paidOrders = new HashSet<>();

    public boolean processPayment(int orderId, double amount) {

        if (!Double.isFinite(amount) || amount <= 0) {
            System.out.println("Payment: Invalid amount");
            return false;
        }

        if (paidOrders.contains(orderId)) {
            System.out.println("Payment: Already paid");
            return false;
        }

        // Simulated payment rejection
        if (amount > 500000) {
            System.out.println("Payment: Declined");
            return false;
        }

        paidOrders.add(orderId);

        System.out.println("Payment: Successful - " + amount);

        return true;
    }

    public void refund(int orderId) {

        if (paidOrders.remove(orderId)) {
            System.out.println("Payment: Refunded");
        }
    }
}