import facade.OrderFacade;
import model.Order;
import model.OrderStatus;

public class Mainbek {

    private static int passed = 0;
    private static int total = 0;

    public static void main(String[] args) {

        System.out.println("=== ASSIGNMENT 4: FACADE PATTERN ===\n");

        OrderFacade facade = new OrderFacade();

        // T1: Successful Order
        Order order1 = new Order(
                1, "Laptop", 2, 150000, "user1@gmail.com"
        );

        boolean result1 = facade.placeOrder(order1);

        check(
                "T1",
                "Successful Order",
                result1
                        && order1.getStatus() == OrderStatus.COMPLETED
                        && facade.getAvailableStock("Laptop") == 3,
                "status=" + order1.getStatus()
                        + " | stock=" + facade.getAvailableStock("Laptop")
        );

        // T2: Payment Failure + Rollback
        Order order2 = new Order(
                2, "Laptop", 2, 300000, "user2@gmail.com"
        );

        boolean result2 = facade.placeOrder(order2);

        check(
                "T2",
                "Payment Failure + Rollback",
                !result2
                        && order2.getStatus() == OrderStatus.FAILED
                        && facade.getAvailableStock("Laptop") == 3,
                "status=" + order2.getStatus()
                        + " | stockRestored="
                        + (facade.getAvailableStock("Laptop") == 3)
        );

        // T3: Shipping Failure + Refund
        Order order3 = new Order(
                3, "Keyboard", 1, 20000, "user3@gmail.com"
        );

        boolean result3 = facade.placeOrder(order3);

        check(
                "T3",
                "Shipping Failure + Rollback",
                !result3
                        && order3.getStatus() == OrderStatus.FAILED
                        && facade.getAvailableStock("Keyboard") == 7,
                "status=" + order3.getStatus()
                        + " | stockRestored="
                        + (facade.getAvailableStock("Keyboard") == 7)
        );

        // T4: Insufficient Stock
        Order order4 = new Order(
                4, "Mouse", 20, 5000, "user4@gmail.com"
        );

        boolean result4 = facade.placeOrder(order4);

        check(
                "T4",
                "Insufficient Stock",
                !result4
                        && order4.getStatus() == OrderStatus.FAILED
                        && facade.getAvailableStock("Mouse") == 10,
                "status=" + order4.getStatus()
                        + " | stock=" + facade.getAvailableStock("Mouse")
        );

        // T5: Cancel Successful Order
        boolean cancelled = facade.cancelOrder(1);

        check(
                "T5",
                "Order Cancellation",
                cancelled
                        && order1.getStatus() == OrderStatus.CANCELLED
                        && facade.getAvailableStock("Laptop") == 5,
                "status=" + order1.getStatus()
                        + " | stockRestored="
                        + (facade.getAvailableStock("Laptop") == 5)
        );

        // T6: Duplicate Cancellation
        boolean duplicateCancel = facade.cancelOrder(1);

        check(
                "T6",
                "Duplicate Cancellation",
                !duplicateCancel
                        && order1.getStatus() == OrderStatus.CANCELLED
                        && facade.getAvailableStock("Laptop") == 5,
                "rejected=" + !duplicateCancel
        );

        // T7: Invalid Quantity
        boolean exceptionCaught = false;

        try {
            new Order(
                    5, "Mouse", -2, 5000, "user5@gmail.com"
            );
        } catch (IllegalArgumentException e) {
            exceptionCaught = true;
        }

        check(
                "T7",
                "Invalid Quantity Validation",
                exceptionCaught,
                "exceptionCaught=" + exceptionCaught
        );

        System.out.println("\nSUMMARY: "
                + passed + "/" + total
                + (passed == total ? " PASS" : " FAIL"));
    }

    private static void check(
            String testId,
            String description,
            boolean condition,
            String details
    ) {

        total++;

        if (condition) {
            passed++;
        }

        System.out.println(
                testId + " "
                        + (condition ? "PASS" : "FAIL")
                        + " | " + description
                        + " | " + details
        );
    }
}