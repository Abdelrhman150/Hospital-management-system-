package Package1;

import java.sql.Timestamp;

/**
 * Entity representing a Notification.
 */
public class Notification {
    private String message;
    private String recipient;
    private String channel; // EMAIL, SMS, MOBILE
    private String status;  // SENT, FAILED, PENDING
    private Timestamp sentAt;

    public Notification(String message, String recipient) {
        this.message = message;
        this.recipient = recipient;
        this.status = "PENDING"; // Default status
    }

    public String getMessage() {
        return message;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }
}
