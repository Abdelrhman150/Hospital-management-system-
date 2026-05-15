package Package1.notification;

/**
 * Adapter for EmailService.
 */
public class EmailNotificationAdapter implements NotificationService {
    private EmailService emailService;

    // Dependency Injection
    public EmailNotificationAdapter(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public boolean sendNotification(Notification notification) {
        try {
           
            emailService.sendEmail(notification.getMessage(), notification.getRecipient());
            return true; // Success
        } catch (Exception e) {
            System.err.println("✗ Failed to send Email: " + e.getMessage());
            return false; // Failure
        }
    }

    @Override
    public String getChannelName() {
        return "EMAIL";
    }
}
