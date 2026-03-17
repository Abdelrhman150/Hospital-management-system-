package Package4;

import Package1.User;
import Package2.AuthController;
import java.util.Scanner;

/**
 * UI: LoginUI
 * Handles console interaction for Login and Registration
 * Communicates only with AuthController
 */
public class LoginUI {
    
    private AuthController authController;
    private Scanner scanner;

    public LoginUI() {
        this.authController = new AuthController();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Entry point for the login system workflow
     */
    public void start() {
        System.out.println("========================================");
        System.out.println("   Welcome to Hospital Management System");
        System.out.println("========================================");
        
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    handleLogin();
                    break;
                case "2":
                    handleRegistration();
                    break;
                case "3":
                    System.out.println("Thank you for using the system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Enter ID or Email: ");
        String identifier = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = authController.login(identifier, password);

        if (user != null) {
            System.out.println("\n✓ Login Successful!");
            System.out.println("Welcome, " + user.getName() + "!");
            System.out.println("Login ID: " + user.getUsername());
            System.out.println("Official Email: " + user.getEmail());
            System.out.println("Your Role: [" + user.getRole() + "]");
            
            // Role-Based Access: Display different options based on role
            displayRoleFunctions(user);
        } else {
            System.out.println("\n✗ Login Failed! Incorrect identifier or password.");
        }
    }

    private void handleRegistration() {
        System.out.println("\n--- Registration ---");
        
        // 1. Enter Full Name
        System.out.print("Enter Full Name: ");
        String fullName = scanner.nextLine();
        if (fullName.trim().isEmpty()) {
            System.out.println("✗ Error: Full Name cannot be empty!");
            return;
        }

        // 2. Select Role
        System.out.println("Select Your Role:");
        System.out.println("1. Admin");
        System.out.println("2. Doctor");
        System.out.println("3. Nurse");
        System.out.println("4. Secretary");
        System.out.println("5. Patient");
        System.out.print("Choose role (1-5): ");
        
        String roleChoice = scanner.nextLine();
        String role = "";
        switch (roleChoice) {
            case "1": role = "Admin"; break;
            case "2": role = "Doctor"; break;
            case "3": role = "Nurse"; break;
            case "4": role = "Secretary"; break;
            case "5": role = "Patient"; break;
            default:
                System.out.println("✗ Error: Invalid role choice.");
                return;
        }

        // 3. Enter Password
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        if (password.trim().isEmpty()) {
            System.out.println("✗ Error: Password cannot be empty!");
            return;
        }

        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("✗ Error: Passwords do not match!");
            return;
        }

        // 4. Finalize Registration
        String result = authController.register(fullName, role, password);
        System.out.println("\n" + result);
    }

    /**
     * Bonus Feature: Role-Based Access
     * Displays available functions based on the user's role
     */
    private void displayRoleFunctions(User user) {
        System.out.println("\n--- Available Functions for " + user.getRole() + " ---");
        
        switch (user.getRole().toLowerCase()) {
            case "admin":
                System.out.println("- Manage All Departments");
                System.out.println("- Manage Staff Accounts");
                System.out.println("- System Settings");
                break;
            case "doctor":
                System.out.println("- View Patient Medical Records");
                System.out.println("- Diagnose Patient");
                System.out.println("- Manage Appointments");
                break;
            case "nurse":
                System.out.println("- Monitor Patient Vitals");
                System.out.println("- Update Patient Status");
                break;
            case "secretary":
                System.out.println("- Book Appointments");
                System.out.println("- Manage Billing");
                break;
        }
        System.out.println("----------------------------------------");
    }

    /**
     * Main method to test the Sign In system
     */
    public static void main(String[] args) {
        LoginUI ui = new LoginUI();
        ui.start();
    }
}
