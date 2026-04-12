package Package4;

import Package1.Admin;
import Package1.staff.Doctor;
import Package1.staff.User;
import Package2.AuthController;
import Package3.DoctorDAO;

import java.util.Scanner;

/**
 * UI: LoginUI
 * Handles console interaction for Login and Registration
 * Communicates with AuthController and opens role-based menus
 */
public class LoginUI {

    private AuthController authController;
    private Scanner scanner;

    public LoginUI() {
        this.authController = new AuthController();
        this.scanner = new Scanner(System.in);
    }

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

            openRoleMenu(user);
        } else {
            System.out.println("\n✗ Login Failed! Incorrect identifier or password.");
        }
    }

    private void handleRegistration() {
        System.out.println("\n--- Registration ---");

        System.out.print("Enter Full Name: ");
        String fullName = scanner.nextLine();
        if (fullName.trim().isEmpty()) {
            System.out.println("✗ Error: Full Name cannot be empty!");
            return;
        }

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

        String result = authController.register(fullName, role, password);
        System.out.println("\n" + result);
    }

    private void openRoleMenu(User user) {
        if (user.getRole() == null) {
            System.out.println("No role assigned.");
            return;
        }

        switch (user.getRole().toLowerCase()) {
            case "admin":
                openAdminMenu(user);
                break;
            case "doctor":
                openDoctorMenu(user);
                break;
            case "nurse":
            case "secretary":
            case "patient":
                displayBasicRoleFunctions(user);
                break;
            default:
                System.out.println("Unknown role.");
        }
    }

    private void openAdminMenu(User user) {
        boolean running = true;

        while (running) {
            System.out.println("\n=================================");
            System.out.println("            ADMIN MENU");
            System.out.println("=================================");
            System.out.println("1. Assign Salary To Doctor");
            System.out.println("2. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleAssignSalary((Admin) user);
                    break;
                case "2":
                    System.out.println("Admin logged out successfully.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleAssignSalary(Admin admin) {
        try {
            DoctorDAO doctorDAO = DoctorDAO.getInstance();

            System.out.println("\n------ Assign Salary To Doctor ------");
            System.out.print("Enter Doctor ID: ");
            String doctorId = scanner.nextLine();

            Doctor doctor = doctorDAO.findDoctorObjectById(doctorId);

            if (doctor == null) {
                System.out.println("Doctor not found in the database.");
                return;
            }

            System.out.println("\nDoctor found successfully:");
            System.out.println("---------------------------------");
            doctor.displayInfo();
            System.out.println("---------------------------------");

            int nightShifts = 0;
            int onCallDays = 0;
            boolean hasHazard = false;

            System.out.print("\nDid this doctor work night shifts this month? (yes/no): ");
            String nightShiftAnswer = scanner.nextLine().trim().toLowerCase();

            if (nightShiftAnswer.equals("yes")) {
                System.out.print("Enter number of night shifts: ");
                nightShifts = Integer.parseInt(scanner.nextLine());
            }

            System.out.print("\nWas this doctor on-call this month? (yes/no): ");
            String onCallAnswer = scanner.nextLine().trim().toLowerCase();

            if (onCallAnswer.equals("yes")) {
                System.out.print("Enter number of on-call days: ");
                onCallDays = Integer.parseInt(scanner.nextLine());
            }

            System.out.print("\nDoes this doctor receive hazard allowance this month? (yes/no): ");
            String hazardAnswer = scanner.nextLine().trim().toLowerCase();

            if (hazardAnswer.equals("yes")) {
                hasHazard = true;
            }

            admin.assignSalaryToDoctor(doctor, nightShifts, onCallDays, hasHazard);

            System.out.println("\n========= SALARY BREAKDOWN =========");
            doctor.viewSalary();
            System.out.println("====================================");

        } catch (Exception e) {
            System.out.println("Error while assigning salary: " + e.getMessage());
        }
    }

    private void openDoctorMenu(User user) {
        boolean running = true;

        while (running) {
            System.out.println("\n=================================");
            System.out.println("           DOCTOR MENU");
            System.out.println("=================================");
            System.out.println("1. View Payroll");
            System.out.println("2. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleViewPayroll(user);
                    break;
                case "2":
                    System.out.println("Doctor logged out successfully.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleViewPayroll(User user) {
        try {
            DoctorDAO doctorDAO = DoctorDAO.getInstance();
            Doctor doctor = doctorDAO.findDoctorObjectById(user.getId());

            if (doctor == null) {
                System.out.println("Doctor not found in database.");
                return;
            }

            System.out.println("\n========= SAVED PAYROLL =========");
            doctor.viewSavedSalaryFromDatabase();
            System.out.println("=================================");

        } catch (Exception e) {
            System.out.println("Error while loading payroll: " + e.getMessage());
        }
    }

    private void displayBasicRoleFunctions(User user) {
        System.out.println("\n--- Available Functions for " + user.getRole() + " ---");

        switch (user.getRole().toLowerCase()) {
            case "nurse":
                System.out.println("- Monitor Patient Vitals");
                System.out.println("- Update Patient Status");
                break;
            case "secretary":
                System.out.println("- Book Appointments");
                System.out.println("- Manage Billing");
                break;
            case "patient":
                System.out.println("- View Appointments");
                System.out.println("- View Medical Records");
                break;
            default:
                System.out.println("- No functions assigned yet");
        }

        System.out.println("----------------------------------------");
    }

    public static void main(String[] args) {
        LoginUI ui = new LoginUI();
        ui.start();
    }
}