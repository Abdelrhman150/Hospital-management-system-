package Package1;

/**
 * Entity representing a Notification.
 */
public class Notification {
    private String message;
    private String recipient;

    public Notification(String message, String recipient) {
        this.message = message;
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getRecipient() {
        return recipient;
    }
}
