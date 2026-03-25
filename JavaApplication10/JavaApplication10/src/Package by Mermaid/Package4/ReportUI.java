package Package4;

import Package1.Report;
import Package2.ExcelFormatter;
import Package2.PDFFormatter;
import Package2.ReportController;
import Package2.ReportFormatter;
import java.util.Scanner;

/**
 * Console-based UI for the Reports System.
 * Operates independently of other systems.
 */
public class ReportUI {
    private Scanner scanner;
    private ReportController reportController;

    public ReportUI() {
        this.scanner = new Scanner(System.in);
        this.reportController = new ReportController();
    }

    /**
     * Starts the console-based UI loop for reports.
     */
    public void start() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("        Hospital Reports System         ");
            System.out.println("========================================");
            System.out.println("1. Create Patient Report");
            System.out.println("2. Create Financial Report");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handlePatientReport();
                    break;
                case "2":
                    handleFinancialReport();
                    break;
                case "3":
                    System.out.println("Exiting Reports System. Goodbye!");
                    return;
                default:
                    System.out.println("✗ Invalid choice. Please try again.");
            }
        }
    }

    private void handlePatientReport() {
        System.out.print("\nEnter Patient ID: ");
        try {
            int patientId = Integer.parseInt(scanner.nextLine());

            // Choose Formatter
            ReportFormatter formatter = chooseFormatter();
            if (formatter == null)
                return;

            System.out.println("\n--- Generating Patient Report ---");
            Report report = reportController.createPatientReport(patientId, formatter);

            if (report != null) {
                System.out.println(report.getFullContent());
                System.out.println("✓ Patient Report generated successfully.");
            } else {
                System.out.println("✗ Failed to generate Patient Report. Please check ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid ID format. Please enter a numeric ID.");
        }
    }

    private void handleFinancialReport() {
        // Choose Formatter
        ReportFormatter formatter = chooseFormatter();
        if (formatter == null)
            return;

        System.out.println("\n--- Generating Financial Report ---");
        Report report = reportController.createFinancialReport(formatter);

        if (report != null) {
            System.out.println(report.getFullContent());
            System.out.println("✓ Financial Report generated successfully.");
        } else {
            System.out.println("✗ Failed to generate Financial Report.");
        }
    }

    /**
     * Helper method to choose the formatter.
     */
    private ReportFormatter chooseFormatter() {
        System.out.println("Choose Formatter:");
        System.out.println("1. PDF");
        System.out.println("2. Excel");
        System.out.print("Choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                return new PDFFormatter();
            case "2":
                return new ExcelFormatter();
            default:
                System.out.println("✗ Invalid formatter choice.");
                return null;
        }
    }

    /**
     * Independent main method to run the Reports UI.
     */
    public static void main(String[] args) {
        ReportUI ui = new ReportUI();
        ui.start();
    }
}
