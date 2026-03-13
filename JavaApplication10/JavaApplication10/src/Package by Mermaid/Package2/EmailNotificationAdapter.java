package Package2;

import Package1.Notification;

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
    public void sendNotification(Notification notification) {
        // تحويل الطلب من الواجهة الموحدة إلى الخدمة الأصلية
        emailService.sendEmail(notification.getMessage(), notification.getRecipient());
    }
}
