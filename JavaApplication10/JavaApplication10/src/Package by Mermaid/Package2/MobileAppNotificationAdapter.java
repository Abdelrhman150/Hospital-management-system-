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
    public void sendNotification(Notification notification) {
        // تحويل الطلب من الواجهة الموحدة إلى الخدمة الأصلية
        mobileAppService.sendPushNotification(notification.getMessage(), notification.getRecipient());
    }
}
