package Package2;

import Package1.User;
import Package3.UserDatabase;
import java.util.List;

/**
 * Controller: UserController
 * Handles user management operations
 */
public class UserController {
    
    private UserDatabase userDatabase;

    public UserController() {
        this.userDatabase = new UserDatabase();
    }

    /**
     * Gets all users registered in the system
     * @return List of users
     */
    public List<User> getAllUsers() {
        return userDatabase.getAllUsers();
    }

    /**
     * Gets a single user by their username (Login ID)
     * @param username The username to search for
     * @return User object or null
     */
    public User getUserByUsername(String username) {
        return userDatabase.getUserByUsername(username);
    }

    /**
     * Gets a single user by their ID or Email
     * @param identifier T
     * @return 
     */
    public User getUserByIdOrEmail(String identifier) {
        return userDatabase.getUserByIdOrEmail(identifier);
    }
}
