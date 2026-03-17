package Package2;

import Package1.Notification;
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
     * Sends a notification using the provided service and saves the record to the DB.
     */
    public void notifyPatient(NotificationService service, Notification notification) {
        if (service == null || notification == null) {
            System.err.println("✗ Cannot send notification: Service or Data is null.");
            return;
        }

        
        notification.setChannel(service.getChannelName());
        
        
        boolean isSent = service.sendNotification(notification);
        
        
        notification.setStatus(isSent ? "SENT" : "FAILED");
        notification.setSentAt(new Timestamp(System.currentTimeMillis()));

        
        boolean isSaved = notificationDAO.saveNotification(notification);

        if (isSaved) {
            System.out.println("✓ Notification record saved to Database (Status: " + notification.getStatus() + ")");
        } else {
            System.err.println("✗ Notification record failed to save to Database.");
        }
    }
}
