package Package1.notification;

/**
 * Legacy/External Service for Email.
 */
public class EmailService {
    public void sendEmail(String message, String email) {
        System.out.println("📧 Email sent to " + email + ": " + message);
    }
}
