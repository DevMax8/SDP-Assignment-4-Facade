package facade;

import model.Order;
import model.OrderStatus;
import subsystem.InventoryService;
import subsystem.PaymentProcessor;
import subsystem.ShippingService;
import subsystem.NotificationService;

import java.util.HashMap;
import java.util.Map;

public class OrderFacade {

    private final InventoryService inventory;
    private final PaymentProcessor payment;
    private final ShippingService shipping;
    private final NotificationService notification;

    private final Map<Integer, Order> orders = new HashMap<>();

    public OrderFacade() {
        inventory = new InventoryService();
        payment = new PaymentProcessor();
        shipping = new ShippingService();
        notification = new NotificationService();
    }

    public boolean placeOrder(Order order) {

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (orders.containsKey(order.getId())
                || order.getStatus() != OrderStatus.CREATED) {
            System.out.println("Order: Invalid or duplicate order");
            return false;
        }

        System.out.println("\nProcessing order #" + order.getId());

        orders.put(order.getId(), order);

        boolean reserved = inventory.reserve(
                order.getProduct(),
                order.getQuantity()
        );

        if (!reserved) {
            failOrder(order);
            return false;
        }

        boolean paid = payment.processPayment(
                order.getId(),
                order.getTotalPrice()
        );

        if (!paid) {
            inventory.release(
                    order.getProduct(),
                    order.getQuantity()
            );

            failOrder(order);
            return false;
        }

        boolean shipped = shipping.arrangeShipping(
                order.getId(),
                order.getProduct()
        );

        if (!shipped) {
            payment.refund(order.getId());

            inventory.release(
                    order.getProduct(),
                    order.getQuantity()
            );

            failOrder(order);
            return false;
        }

        order.setStatus(OrderStatus.COMPLETED);

        notification.sendConfirmation(
                order.getCustomerEmail(),
                order.getId()
        );

        System.out.println("Order: Completed successfully");

        return true;
    }

    public boolean cancelOrder(int orderId) {

        Order order = orders.get(orderId);

        if (order == null
                || order.getStatus() != OrderStatus.COMPLETED) {

            System.out.println("Order: Cannot cancel order #" + orderId);
            return false;
        }

        shipping.cancelShipping(orderId);
        payment.refund(orderId);

        inventory.release(
                order.getProduct(),
                order.getQuantity()
        );

        order.setStatus(OrderStatus.CANCELLED);

        notification.sendCancellation(
                order.getCustomerEmail(),
                orderId
        );

        System.out.println("Order: Cancelled successfully");

        return true;
    }

    private void failOrder(Order order) {

        order.setStatus(OrderStatus.FAILED);

        notification.sendFailure(
                order.getCustomerEmail(),
                order.getId()
        );

        System.out.println("Order: Processing failed");
    }

    public int getAvailableStock(String product) {
        return inventory.getStock(product);
    }

    public OrderStatus getOrderStatus(int orderId) {

        Order order = orders.get(orderId);

        return order == null ? null : order.getStatus();
    }
}