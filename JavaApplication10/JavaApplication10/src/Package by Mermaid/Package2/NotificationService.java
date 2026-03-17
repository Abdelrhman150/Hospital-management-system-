package Package2;

import Package1.Notification;


public interface NotificationService {
    boolean sendNotification(Notification notification); 
    String getChannelName(); 
}
