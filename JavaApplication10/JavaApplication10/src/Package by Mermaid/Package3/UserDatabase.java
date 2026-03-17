package Package3;

import Package1.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: UserDatabase
 * Handles all database operations for the User entity
 */
public class UserDatabase {

    /**
     * Retrieves a user from the database by their username
     */
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM Users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a user from the database by their ID (username) or Email
     */
    public User getUserByIdOrEmail(String identifier) {
        String query = "SELECT * FROM Users WHERE username = ? OR email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, identifier);
            ps.setString(2, identifier);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user by ID or Email: " + e.getMessage());
        }
        return null;
    }

    /**
     * Helper to map a ResultSet row to a User object
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        // We use a safe check to see if 'full_name' column exists
        String name = "";
        try {
            name = rs.getString("full_name");
        } catch (SQLException e) {
            // If full_name column is missing, fallback to 'name' or empty
            try { name = rs.getString("name"); } catch (SQLException e2) {}
        }

        return new User(
            rs.getInt("id"),
            rs.getString("username"),
            name,
            rs.getString("password"),
            rs.getString("email"),
            rs.getString("role")
        );
    }

    /**
     * Checks if a user exists by username or email
     */
    public boolean userExists(String username, String email) {
        String query = "SELECT 1 FROM Users WHERE username = ? OR email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, username);
            ps.setString(2, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error checking if user exists: " + e.getMessage());
        }
        return false;
    }

    /**
     * Finds a person in their official table (Doctors, Nurses, etc.)
     * Returns name and official email
     */
    public String[] findOfficialPerson(int personId, String role) {
        String table = "";
        String idColumn = "";
        
        switch (role.toLowerCase()) {
            case "doctor": table = "Doctors"; idColumn = "doctorId"; break;
            case "nurse": table = "Nurses"; idColumn = "nurseId"; break;
            case "patient": table = "Patients"; idColumn = "patientId"; break;
            case "secretary": table = "Secretaries"; idColumn = "secretaryId"; break;
            case "admin": table = "Admins"; idColumn = "adminId"; break;
            default: return null;
        }

        String query = "SELECT name, email FROM " + table + " WHERE " + idColumn + " = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, personId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new String[] { rs.getString("name"), rs.getString("email") };
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding official person: " + e.getMessage());
        }
        return null;
    }

    /**
     * Checks if a person (ID + Role) already has an account
     */
    public boolean isPersonRegistered(int personId, String role) {
        String query = "SELECT 1 FROM Users WHERE personId = ? AND role = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, personId);
            ps.setString(2, role);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error checking registration: " + e.getMessage());
        }
        return false;
    }

    /**
     * Checks if a username already exists
     */
    public boolean usernameExists(String username) {
        String query = "SELECT 1 FROM Users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
        }
        return false;
    }

    /**
     * Saves a new user account
     */
    public boolean createUser(User user) {
        String query = "INSERT INTO Users (username, full_name, password, email, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getRole());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all users from the database
     * @return List of all User objects
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all users: " + e.getMessage());
        }
        return users;
    }
}
