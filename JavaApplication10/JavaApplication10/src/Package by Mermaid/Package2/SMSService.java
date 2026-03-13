package Package2;

/**
 * Legacy/External Service for SMS.
 */
public class SMSService {
    public void sendSMS(String message, String phone) {
        System.out.println("💬 SMS sent to " + phone + ": " + message);
    }
}
