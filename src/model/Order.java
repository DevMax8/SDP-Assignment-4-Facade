package model;

public class Order {

    private final int id;
    private final String product;
    private final int quantity;
    private final double price;
    private final String customerEmail;

    private OrderStatus status;

    public Order(int id, String product, int quantity,
                 double price, String customerEmail) {

        if (id <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }

        if (product == null || product.isBlank()) {
            throw new IllegalArgumentException("Invalid product");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        if (!Double.isFinite(price) || price <= 0) {
            throw new IllegalArgumentException("Invalid price");
        }

        if (customerEmail == null || customerEmail.isBlank()) {
            throw new IllegalArgumentException("Invalid email");
        }

        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.customerEmail = customerEmail;
        this.status = OrderStatus.CREATED;
    }

    public int getId() {
        return id;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }

        this.status = status;
    }
}