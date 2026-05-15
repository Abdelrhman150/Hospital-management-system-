package Package4;

import Package1.notification.*;
import Package2.NotificationController;
import java.util.Scanner;

/**
 * Console-based UI for the Notifications System.
 * Operates independently of other systems.
 */
public class NotificationUI {
    private Scanner scanner;
    private NotificationController notificationController;

    public NotificationUI() {
        this.scanner = new Scanner(System.in);
        this.notificationController = new NotificationController();
    }

    /**
     * Starts the console-based UI loop for notifications.
     */
    public void start() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("      Hospital Notifications System      ");
            System.out.println("========================================");
            System.out.println("1. Send Email Notification");
            System.out.println("2. Send SMS Notification");
            System.out.println("3. Send Mobile App Push");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleNotification("EMAIL");
                    break;
                case "2":
                    handleNotification("SMS");
                    break;
                case "3":
                    handleNotification("MOBILE");
                    break;
                case "4":
                    System.out.println("Exiting Notifications System. Goodbye!");
                    return;
                default:
                    System.out.println("✗ Invalid choice. Please try again.");
            }
        }
    }

    private void handleNotification(String type) {
        System.out.print("\nEnter Recipient (" + type + "): ");
        String recipient = scanner.nextLine();
        System.out.print("Enter Message: ");
        String message = scanner.nextLine();

        if (recipient.trim().isEmpty() || message.trim().isEmpty()) {
            System.out.println("✗ Recipient and Message cannot be empty.");
            return;
        }

        NotificationService service = null;
        Notification notification = new Notification(message, recipient);

        // Choosing Service / Adapter based on user input
        switch (type) {
            case "EMAIL":
                service = new EmailNotificationAdapter(new EmailService());
                break;
            case "SMS":
                service = new SMSNotificationAdapter(new SMSService());
                break;
            case "MOBILE":
                service = new MobileAppNotificationAdapter(new MobileAppService());
                break;
        }

        if (service != null) {
            System.out.println("\n--- Sending Notification ---");
            notificationController.notifyPatient(service, notification);
            
            System.out.println("Result: [" + notification.getStatus() + "]");
            System.out.println("Log: Notification via " + notification.getChannel() + " to " + notification.getRecipient());
        } else {
            System.out.println("✗ Failed to initialize Notification Service.");
        }
    }

    /**
     * Independent main method to run the Notifications UI.
     */
    public static void main(String[] args) {
        NotificationUI ui = new NotificationUI();
        ui.start();
    }
}
