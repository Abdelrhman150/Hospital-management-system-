package Package2;

import Package1.notification.*;
import Package3.NotificationDAO;
import java.sql.Timestamp;

/**
 * Controller managing notification logic and database persistence.
 */
public class NotificationController {

    private NotificationDAO notificationDAO;

    public NotificationController() {
        this.notificationDAO = NotificationDAO.getInstance();
    }

    /**
     * بتخزن في داتا بيز
     */
    public void notifyPatient(NotificationService service, Notification notification) {
        if (service == null || notification == null) {
            System.err.println("✗ Cannot send notification: Service or Data is null.");
            return;
        }

        
        notification.setChannel(service.getChannelName());
        
        
        boolean isSent = service.sendNotification(notification);
        
        if (isSent) {
            notification.setStatus("SENT");
        } else {
            notification.setStatus("FAILED");
        }
        
        notification.setSentAt(new Timestamp(System.currentTimeMillis()));

        
        boolean isSaved = notificationDAO.saveNotification(notification);

        if (isSaved) {
            System.out.println("✓ Notification record saved to Database (Status: " + notification.getStatus() + ")");
        } else {
            System.err.println("✗ Notification record failed to save to Database.");
        }
    }
}
