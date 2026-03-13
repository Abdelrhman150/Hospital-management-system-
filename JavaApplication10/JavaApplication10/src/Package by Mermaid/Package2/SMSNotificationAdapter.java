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
    public void sendNotification(Notification notification) {
        // تحويل الطلب من الواجهة الموحدة إلى الخدمة الأصلية
        smsService.sendSMS(notification.getMessage(), notification.getRecipient());
    }
}
