package Package4;

import Package1.emergencyalert.AlertSubscriber;
import Package1.emergencyalert.DoctorAlert;
import Package1.emergencyalert.NurseAlert;
import Package2.AlertManagerController;
import java.util.List;
import java.util.Scanner;

/**
 * EmergencyAlertUI
 * واجهة المستخدم - Console Menu
 */
public class EmergencyAlertUI {

    private Scanner scanner;
    private AlertManagerController controller;

    public EmergencyAlertUI() {
        this.scanner = new Scanner(System.in);
        this.controller = new AlertManagerController();
        setupSubscribers();
    }

    /**
     * تسجيل المشتركين عند بدء البرنامج
     */
    private void setupSubscribers() {
        System.out.println("\n--- Registering Subscribers ---\n");

        // أطباء ICU
        controller.registerSubscriber(new DoctorAlert("Dr. Ahmed", "ICU"));
        controller.registerSubscriber(new DoctorAlert("Dr. Fatima", "Emergency"));

        // محطات التمريض
        controller.registerSubscriber(new NurseAlert("Station A", "1st Floor"));
        controller.registerSubscriber(new NurseAlert("Station B", "2nd Floor"));

        System.out.println("\nAll subscribers registered!\n");
    }

    /**
     * القائمة الرئيسية
     */
    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("=============================");
            System.out.println("   Emergency Alert System    ");
            System.out.println("=============================");
            System.out.println("1. Trigger CODE BLUE Alert");
            System.out.println("2. Trigger LAB CRITICAL Alert");
            System.out.println("3. List All Subscribers");
            System.out.println("4. Exit");
            System.out.print("\nChoose option (1-4): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleCodeBlue();
                    break;
                case "2":
                    handleLabCritical();
                    break;
                case "3":
                    listSubscribers();
                    break;
                case "4":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.\n");
            }
        }
    }

    // ==================== Alert Handlers ====================

    private void handleCodeBlue() {
        System.out.print("Enter Room Number: ");
        String room = scanner.nextLine().trim();
        String message = "CODE BLUE in Room " + room + " - IMMEDIATE ACTION REQUIRED";
        controller.triggerEmergencyAlert("CODE_BLUE", message);
    }

    private void handleLabCritical() {
        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine().trim();
        String message = "CRITICAL LAB RESULT for Patient " + patientId + " - URGENT";
        controller.triggerEmergencyAlert("LAB_CRITICAL", message);
    }



    private void listSubscribers() {
        System.out.println("\n--- Active Subscribers ---");
        List<AlertSubscriber> subscribers = controller.getAllSubscribers();
        int i = 1;
        for (AlertSubscriber sub : subscribers) {
            System.out.println(i + ". " + sub.getSubscriberName());
            i++;
        }
        System.out.println("Total: " + controller.getSubscriberCount() + " subscribers\n");
    }

    // ==================== Main ====================

    public static void main(String[] args) {
        EmergencyAlertUI ui = new EmergencyAlertUI();
        ui.start();
    }
}
