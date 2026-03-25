package Package2;

import Package1.User;
import Package3.UserDatabase;

/**
 * Controller: AuthController
 * Handles all authentication-related logic (Login, Registration)
 * Interacts with the UserDatabase to perform database operations
 */
public class AuthController {
    
    private UserDatabase userDatabase;
    private IdGenerator idGenerator;

    public AuthController() {
        this.userDatabase = new UserDatabase();
        this.idGenerator = IdGenerator.getInstance();
    }

    /**
     * Authenticates a user based on ID or Email and password
     * @param identifier The ID or Email provided by the user
     * @param password The password provided by the user
     * @return User object if authentication is successful, null otherwise
     */
    public User login(String identifier, String password) {
        User user = userDatabase.getUserByIdOrEmail(identifier);
        // بيشوف الباسورد صح ولا لا
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Registers a new user with auto-generated ID and Email
     */
    public String register(String fullName, String role, String password) {
       
        String generatedId = idGenerator.generateUserIdByRole(role);
        
       
        String generatedEmail = idGenerator.generateEmail(fullName, generatedId);

        
        if (userDatabase.userExists(generatedId, generatedEmail)) {
            return "Error: Generated ID or Email already exists!";
        }

       
        User newUser = new User(generatedId, fullName, password, generatedEmail, role);
        
        
        boolean success = userDatabase.createUser(newUser);
        
        if (success) {
            return "Registration Successful!\n" +
                   "Your Login ID: " + generatedId + "\n" +
                   "Your Official Email: " + generatedEmail;
        } else {
            return "Error: Registration failed due to a database issue.";
        }
    }


    public String register(String username, String password, String email, String role) {
        if (userDatabase.userExists(username, email)) {
            return "Error: Username or Email already exists!";
        }
        User newUser = new User(username, "Unknown", password, email, role);
        if (userDatabase.createUser(newUser)) {
            return "Registration Successful!";
        }
        return "Error: Registration failed.";
    }


    public String[] validateOfficialId(String officialId, String role) {
        return userDatabase.findOfficialPerson(officialId, role);
    }
}
