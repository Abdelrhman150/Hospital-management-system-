package Package4;

import Package1.DoctorRole.DoctorService;
import Package2.DoctorRoleController;
import java.util.Scanner;

/**
 * Console-based UI for managing doctor roles.
 */
public class DoctorRoleUI {

    private DoctorRoleController controller = new DoctorRoleController();
    private Scanner scanner = new Scanner(System.in);

    /**
     * Entry point for the Doctor Role Management UI.
     */
    public void start() {
        System.out.println("=== Doctor Role Management System ===");
        System.out.print("Enter Doctor ID (e.g. 4 or D004): ");
        String inputId = scanner.nextLine();
        String doctorId = parseDoctorId(inputId);

        if (doctorId == null) {
            System.out.println("Invalid Doctor ID format! Please use numbers or format like D001.");
            return;
        }

        try {
            boolean running = true;
            while (running) {
                System.out.println("\nSelect an action:");
                System.out.println("1. Assign Role");
                System.out.println("2. Show Doctor Description and Duties");
                System.out.println("3. Exit");
                System.out.print("Choice: ");

                String choiceInput = scanner.nextLine();
                int choice;
                try {
                    choice = Integer.parseInt(choiceInput);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number for choice!");
                    continue;
                }

                switch (choice) {
                    case 1:
                        assignRole(doctorId);
                        break;
                    case 2:
                        showDoctor(doctorId);
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Helper method to parse doctor ID from formats like "D004" or "4"
     */
    private String parseDoctorId(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        // Basic cleaning: trim and keep as is (supporting alphanumeric like D001)
        String cleaned = input.trim();
        if (cleaned.isEmpty()) {
            return null;
        }
        return cleaned;
    }

    private void assignRole(String doctorId) throws Exception {
        System.out.println("\nSelect Role to Assign:");
        System.out.println("1. Surgeon");
        System.out.println("2. Head of Department");
        System.out.println("3. On-call");
        System.out.print("Choice: ");

        String roleInput = scanner.nextLine();
        int roleChoice;
        try {
            roleChoice = Integer.parseInt(roleInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number!");
            return;
        }

        String roleName = "";
        switch (roleChoice) {
            case 1:
                roleName = "Surgeon";
                break;
            case 2:
                roleName = "Head of Department";
                break;
            case 3:
                roleName = "On-call";
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        System.out.print("Save to database? (yes/no): ");
        String save = scanner.nextLine();
        if (save.equalsIgnoreCase("yes")) {
            controller.assignRole(doctorId, roleName);
            System.out.println("Role saved successfully!");
        } else {
            System.out.println("Role not saved.");
        }
    }

    private void showDoctor(String doctorId) throws Exception {
        DoctorService doctor = controller.getDecoratedDoctor(doctorId);
        if (doctor != null) {
            System.out.println("\n--- Doctor Final Profile ---");
            System.out.println("Description: " + doctor.getDescription());
            System.out.println("--- Duties ---");
            doctor.performDuties();
        } else {
            System.out.println("Doctor with ID " + doctorId + " not found!");
        }
    }

    public static void main(String[] args) {
        new DoctorRoleUI().start();
    }
}
