package Package4;

import Package2.MedicalRecordBridgeController;
import Package3.MedicalRecordDAO;
import Package1.MedicalRecordDisplay.MedicalRecord;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based UI for viewing Medical Records using the Bridge Pattern.
 */
public class MedicalRecordUI {
    private Scanner scanner;
    private MedicalRecordBridgeController bridgeController;
    private MedicalRecordDAO medicalRecordDAO;

    public MedicalRecordUI() {
        this.scanner = new Scanner(System.in);
        this.bridgeController = new MedicalRecordBridgeController();
        this.medicalRecordDAO = MedicalRecordDAO.getInstance();
    }

    /**
     * Starts the console-based UI loop.
     */
    public void start() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("      Medical Records Bridge System     ");
            System.out.println("========================================");
            System.out.println("1. View Patient History");
            System.out.println("2. View Record (Bridge Pattern)");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleViewHistory();
                    break;
                case "2":
                    handleBridgeView();
                    break;
                case "3":
                    System.out.println("Exiting Medical Records System. Goodbye!");
                    return;
                default:
                    System.out.println("✗ Invalid choice. Please try again.");
            }
        }
    }

    private void handleViewHistory() {
        System.out.print("\nEnter Patient ID: ");
        String patientId = scanner.nextLine();

        if (patientId.isEmpty()) {
            System.out.println("✗ Patient ID cannot be empty.");
            return;
        }

        try {
            List<MedicalRecord> history = medicalRecordDAO.getPatientHistory(patientId);
            System.out.println("\n--- History for Patient: " + patientId + " ---");
            System.out.printf("%-12s | %-12s | %-20s | %-20s\n", "Record ID", "Date", "Diagnosis", "Complaint");
            System.out.println("----------------------------------------------------------------------");
            
            if (history.isEmpty()) {
                System.out.println("No records found for this patient.");
            } else {
                for (MedicalRecord rec : history) {
                    System.out.printf("%-12s | %-12s | %-20s | %-20s\n",
                            rec.recordId,
                            rec.dateCreated,
                            rec.clinicalDiagnosis,
                            rec.chiefComplaint);
                }
            }
        } catch (Exception e) {
            System.err.println("✗ Error loading history: " + e.getMessage());
        }
    }

    private void handleBridgeView() {
        System.out.print("\nEnter Record ID: ");
        String recordId = scanner.nextLine();

        if (recordId.isEmpty()) {
            System.out.println("✗ Record ID cannot be empty.");
            return;
        }

        // Choose Platform
        System.out.println("\nChoose Display Platform:");
        System.out.println("1. Desktop Display");
        System.out.println("2. Web Display");
        System.out.print("Choice: ");
        String platformChoice = scanner.nextLine();
        String platformType = platformChoice.equals("2") ? "web" : "desktop";

        // Choose View Mode
        System.out.println("\nChoose View Mode:");
        System.out.println("1. Doctor View (Full Details)");
        System.out.println("2. Patient View (Summary)");
        System.out.print("Choice: ");
        String viewChoice = scanner.nextLine();
        String viewType = viewChoice.equals("2") ? "patient" : "doctor";

        System.out.println("\n--- Executing Bridge View Pattern ---");
        bridgeController.viewRecord(recordId, platformType, viewType);
    }

    /**
     * Main method to run the Medical Record UI independently.
     */
    public static void main(String[] args) {
        MedicalRecordUI ui = new MedicalRecordUI();
        ui.start();
    }
}
