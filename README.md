# Assignment 4 — Facade Design Pattern

**Course:** Software Design Patterns (SDP)  
**Topic:** Facade Structural Design Pattern  
**Project:** Online Order Processing System  
**Language:** Java 17  
**IDE:** IntelliJ IDEA

## 1. Project Overview

This project demonstrates the implementation of the **Facade Design Pattern** using an Online Order Processing System.

The system simulates the process of placing and cancelling customer orders. It includes inventory management, payment processing, shipping arrangements, and customer notifications.

The `OrderFacade` class provides a simplified interface that coordinates these services without exposing their internal implementation to the client.

## 2. Design Pattern — Facade

The Facade Pattern is a structural design pattern that provides a simplified interface to a complex subsystem.

In this project, the client interacts with the `OrderFacade` instead of directly managing multiple subsystem services.

**Without Facade:** The client would need to coordinate inventory, payment, shipping, and notification services manually.

**With Facade:** The client only needs to call:

```java
OrderFacade facade = new OrderFacade();
facade.placeOrder(order);
```

The Facade coordinates all necessary operations in the correct order.

## 3. Project Structure

```text
Sdp_assignment_4/
├── img/
│   ├── Online_Order_Processing_Facade_UML.puml
│   └── Online_Order_Processing_Facade_UML.png
│
├── src/
│   ├── facade/
│   │   └── OrderFacade.java
│   │
│   ├── model/
│   │   ├── Order.java
│   │   └── OrderStatus.java
│   │
│   ├── subsystem/
│   │   ├── InventoryService.java
│   │   ├── NotificationService.java
│   │   ├── PaymentProcessor.java
│   │   └── ShippingService.java
│   │
│   └── Mainbek.java
│
├── .gitignore
└── README.md
```

## 4. Main Components

| Component | Responsibility |
|---|---|
| `Main` | Client that interacts with the Facade |
| `OrderFacade` | Coordinates subsystem operations |
| `Order` | Represents customer order information |
| `OrderStatus` | Defines order states |
| `InventoryService` | Checks, reserves, and releases stock |
| `PaymentProcessor` | Processes payments and refunds |
| `ShippingService` | Arranges and cancels shipments |
| `NotificationService` | Sends simulated customer notifications |

## 5. System Workflow

When a customer places an order, the system performs the following operations:

1. Validates the order.
2. Checks and reserves available stock.
3. Processes the payment.
4. Arranges shipping.
5. Sends an order confirmation.
6. Updates the order status.

### Rollback Mechanism

The system includes rollback logic for failed operations.

- **Inventory failure:** The order is rejected.
- **Payment failure:** Reserved stock is released.
- **Shipping failure:** The payment is refunded and reserved stock is released.
- **Order cancellation:** Shipping is cancelled, payment is refunded, and stock is restored.

This prevents the simulated system from retaining reserved stock or completed payments after an unsuccessful order.
