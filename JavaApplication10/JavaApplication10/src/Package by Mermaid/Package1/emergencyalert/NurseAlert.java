package Package1.emergencyalert;

/**
 * محطة التمريض - تستقبل التنبيهات
 */
public class NurseAlert implements AlertSubscriber {
    private String stationName;
    private String floor;

    public NurseAlert(String stationName, String floor) {
        this.stationName = stationName;
        this.floor = floor;
    }

    @Override
    public void receiveAlert(String alertType, String message) {
        System.out.println("\n[Nurse Station: " + stationName + " - Floor " + floor + "]");
        System.out.println("   Alert Type: " + alertType);
        System.out.println("   Message: " + message);
        System.out.println("   Status: Alert Received\n");
    }

    @Override
    public String getSubscriberName() {
        return "NurseStation_" + stationName;
    }
}
