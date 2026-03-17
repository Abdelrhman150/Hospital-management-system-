package Package2;

import Package1.Notification;

/**
 * Adapter for MobileAppService.
 */
public class MobileAppNotificationAdapter implements NotificationService {
    private MobileAppService mobileAppService;

    // Dependency Injection
    public MobileAppNotificationAdapter(MobileAppService mobileAppService) {
        this.mobileAppService = mobileAppService;
    }

    @Override
    public boolean sendNotification(Notification notification) {
        try {
            mobileAppService.sendPushNotification(notification.getMessage(), notification.getRecipient());
            return true; // Success
        } catch (Exception e) {
            System.err.println("✗ Failed to send Mobile Push: " + e.getMessage());
            return false; // Failure
        }
    }

    @Override
    public String getChannelName() {
        return "MOBILE";
    }
}
