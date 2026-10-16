package subsystem;

public class NotificationService {

    public void sendConfirmation(String email, int orderId) {

        System.out.println("Notification: Order #" + orderId
                + " confirmed for " + email);
    }

    public void sendCancellation(String email, int orderId) {

        System.out.println("Notification: Order #" + orderId
                + " cancelled for " + email);
    }

    public void sendFailure(String email, int orderId) {

        System.out.println("Notification: Order #" + orderId
                + " failed for " + email);
    }
}