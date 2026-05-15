package Package1.emergencyalert;

/**
 * طبيب ICU - يستقبل التنبيهات الحرجة
 */
public class DoctorAlert implements AlertSubscriber {
    private String doctorName;
    private String department;

    public DoctorAlert(String doctorName, String department) {
        this.doctorName = doctorName;
        this.department = department;
    }

    @Override
    public void receiveAlert(String alertType, String message) {
        System.out.println("\n[Doctor: " + doctorName + " - " + department + "]");
        System.out.println("   Alert Type: " + alertType);
        System.out.println("   Message: " + message);
        System.out.println("   Status: CRITICAL - Action Required\n");
    }

    @Override
    public String getSubscriberName() {
        return "Doctor_" + doctorName;
    }
}
