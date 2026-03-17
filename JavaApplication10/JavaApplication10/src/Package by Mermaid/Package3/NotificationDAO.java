package Package3;

import Package1.Notification;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object: NotificationDAO
 * Handles saving and retrieving notifications from the database.
 * Implemented using the Singleton Pattern.
 */
public class NotificationDAO {
    private static NotificationDAO instance;

    private NotificationDAO() {}

    public static synchronized NotificationDAO getInstance() {
        if (instance == null) {
            instance = new NotificationDAO();
        }
        return instance;
    }

    /**
     * Saves a notification record to the database.
     */
    public boolean saveNotification(Notification notification) {
        String sql = "INSERT INTO Notifications (message, recipient, channel, status, sentAt) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, notification.getMessage());
            ps.setString(2, notification.getRecipient());
            ps.setString(3, notification.getChannel());
            ps.setString(4, notification.getStatus());
            ps.setTimestamp(5, notification.getSentAt());
            
            int rows = ps.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("✗ Error saving notification to DB: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all notifications from the database.
     */
    public List<Notification> getAllNotifications() {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM Notifications ORDER BY sentAt DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Notification n = new Notification(rs.getString("message"), rs.getString("recipient"));
                n.setChannel(rs.getString("channel"));
                n.setStatus(rs.getString("status"));
                n.setSentAt(rs.getTimestamp("sentAt"));
                list.add(n);
            }
        } catch (SQLException e) {
            System.err.println("✗ Error fetching notifications: " + e.getMessage());
        }
        return list;
    }

    /**
     * Retrieves notifications for a specific recipient.
     */
    public List<Notification> getNotificationsByRecipient(String recipient) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE recipient = ? ORDER BY sentAt DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, recipient);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Notification n = new Notification(rs.getString("message"), rs.getString("recipient"));
                    n.setChannel(rs.getString("channel"));
                    n.setStatus(rs.getString("status"));
                    n.setSentAt(rs.getTimestamp("sentAt"));
                    list.add(n);
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Error fetching recipient notifications: " + e.getMessage());
        }
        return list;
    }
}
