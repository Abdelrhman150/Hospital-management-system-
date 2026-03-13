package Package2;

/**
 * Legacy/External Service for Mobile Push Notifications.
 */
public class MobileAppService {
    public void sendPushNotification(String message, String userId) {
        System.out.println("📱 Push Notification sent to user " + userId + ": " + message);
    }
}
