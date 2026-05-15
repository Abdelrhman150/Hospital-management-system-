package Package2;

import Package1.emergencyalert.AlertManager;
import Package1.emergencyalert.AlertSubscriber;
import java.util.List;

/**
 * AlertManagerController
 * طبقة التحكم - واجهة بسيطة بين UI و AlertManager
 */
public class AlertManagerController {

    private AlertManager alertManager;

    public AlertManagerController() {
        this.alertManager = AlertManager.getInstance();
    }

    /**
     * تسجيل مشترك
     */
    public void registerSubscriber(AlertSubscriber sub) {
        alertManager.subscribe(sub);
    }

    /**
     * إطلاق تنبيه طارئ
     */
    public void triggerEmergencyAlert(String alertType, String message) {
        alertManager.triggerAlert(alertType, message);
    }

    /**
     * الحصول على قائمة المشتركين
     */
    public List<AlertSubscriber> getAllSubscribers() {
        return alertManager.getSubscribers();
    }

    /**
     * عدد المشتركين
     */
    public int getSubscriberCount() {
        return alertManager.getSubscriberCount();
    }
}
