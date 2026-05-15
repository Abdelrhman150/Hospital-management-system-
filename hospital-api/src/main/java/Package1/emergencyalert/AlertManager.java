package Package1.emergencyalert;

import Package3.NotificationDAO;
import Package1.notification.Notification;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class AlertManager {

    private static AlertManager instance;

    private AlertManager() {
        System.out.println("AlertManager initialized");
    }

    public static synchronized AlertManager getInstance() {
        if (instance == null) {
            instance = new AlertManager();
        }
        return instance;
    }

    private List<AlertSubscriber> subscribers = new ArrayList<>();

    public void subscribe(AlertSubscriber sub) {
        subscribers.add(sub);
        System.out.println("Subscribed: " + sub.getSubscriberName());
    }

    public void unsubscribe(AlertSubscriber sub) {
        subscribers.remove(sub);
        System.out.println("Unsubscribed: " + sub.getSubscriberName());
    }

    public void triggerAlert(String alertType, String message) {
        System.out.println("\n=== EMERGENCY ALERT TRIGGERED ===");
        System.out.println("Type: " + alertType);
        System.out.println("Notifying " + subscribers.size() + " subscriber(s)...\n");

        for (AlertSubscriber sub : subscribers) {
            sub.receiveAlert(alertType, message);

            try {
                saveAlertToDatabase(sub.getSubscriberName(), alertType, message);
            } catch (Exception e) {
                System.err.println("Failed to save alert to DB: " + e.getMessage());
            }
        }

        System.out.println("=== ALERT SENT TO ALL ===\n");
    }

    private void saveAlertToDatabase(String recipient, String alertType, String message) {
        NotificationDAO dao = NotificationDAO.getInstance();
        Notification notification = new Notification("[" + alertType + "] " + message, recipient);
        notification.setChannel("EMERGENCY_ALERT");
        notification.setStatus("SENT");
        notification.setSentAt(new Timestamp(System.currentTimeMillis()));
        dao.saveNotification(notification);
        System.out.println("  Saved to DB for: " + recipient);
    }

    public List<AlertSubscriber> getSubscribers() {
        return new ArrayList<>(subscribers);
    }

    public int getSubscriberCount() {
        return subscribers.size();
    }
}
