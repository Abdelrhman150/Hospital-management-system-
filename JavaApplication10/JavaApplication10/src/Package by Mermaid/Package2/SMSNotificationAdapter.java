package Package2;

import Package1.Notification;

/**
 * Adapter for SMSService.
 */
public class SMSNotificationAdapter implements NotificationService {
    private SMSService smsService;

    // Dependency Injection
    public SMSNotificationAdapter(SMSService smsService) {
        this.smsService = smsService;
    }

    @Override
    public boolean sendNotification(Notification notification) {
        try {
            smsService.sendSMS(notification.getMessage(), notification.getRecipient());
            return true; // Success
        } catch (Exception e) {
            System.err.println("✗ Failed to send SMS: " + e.getMessage());
            return false; // Failure
        }
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }
}
