package Package2;

import Package1.Notification;

/**
 * Target Interface: توحد طريقة إرسال الإشعارات.
 */
public interface NotificationService {
    void sendNotification(Notification notification);
}
