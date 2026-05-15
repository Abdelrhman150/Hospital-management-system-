package Package1.emergencyalert;

/**
 * Alert Subscriber Interface
 * كل قسم يجب يطبقها عشان يستقبل التنبيهات
 */
public interface AlertSubscriber {
    void receiveAlert(String alertType, String message);
    String getSubscriberName();
}
