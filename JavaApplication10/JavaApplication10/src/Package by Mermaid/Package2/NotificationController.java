package Package2;

import Package1.Notification;

/**
 * Controller managing notification logic.
 */
public class NotificationController {

    /**
     * Sends notification using the provided service (Adapter).
     * الفائدة هنا هي أن الـ Controller لا يهمه نوع الخدمة، بل يهمه أنها تنفذ NotificationService.
     */
    public void notifyPatient(NotificationService service, Notification notification) {
        if (service != null && notification != null) {
            service.sendNotification(notification);
        } else {
            System.err.println("✗ Cannot send notification: Service or Data is null.");
        }
    }
}
