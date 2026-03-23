package Package1;

/**
 * Entity: User
 * Represents a user in the Hospital Management System
 */
public class User {
    protected int id; // Account ID (Primary Key in Users table)
    protected int personId; // Official ID from Doctors/Nurses/etc. table
    protected String username;
    protected String password;
    protected String email;
    protected String role;
    protected String name; // Official Full Name
    protected String phone; 

    // Default constructor
    public User() {}

    // Constructor for Sign-In system
    public User(int id, int personId, String username, String password, String email, String role) {
        this.id = id;
        this.personId = personId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // New constructor for the updated registration flow
    public User(String username, String name, String password, String email, String role) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Constructor with ID for database retrieval
    public User(int id, String username, String name, String password, String email, String role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Old constructor for compatibility
    public User(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPersonId() { return personId; }
    public void setPersonId(int personId) { this.personId = personId; }

<<<<<<< HEAD
    public int getPersonId() {
        return id;
    }

    public String getName() {
        return name;
    }
=======
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
>>>>>>> 76be148b668c5adf33b60945c060c4d7885aeed7

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public void displayInfo() {
        System.out.println("--- User Profile ---");
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);
        System.out.println("--------------------");
    }
}
