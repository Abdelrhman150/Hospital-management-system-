package Package4;

import Package1.Secretary;
import Package1.Payement_System.InsuranceAdaptor;
import Package1.Payement_System.Paypal;
import Package1.Payement_System.paypalAdapter;
import java.util.Scanner;

/**
 * UI for Secretary operations
 * Provides console interface to use all Secretary functions
 */
public class SecretaryUI {

    private Secretary secretary;
    private Scanner scanner;

    /**
     * Constructor with Secretary instance
     */
    public SecretaryUI(Secretary secretary) {
        this.secretary = secretary;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Default constructor for demo purposes
     */
    public SecretaryUI() {
        // Create a demo secretary for testing
        this.secretary = new Secretary("SEC001", "John Doe", "123-456-7890", "john.doe@hospital.com", "Morning");
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main menu for Secretary operations
     */
    public void start() {
        System.out.println("========================================");
        System.out.println("         Secretary Control Panel");
        System.out.println("========================================");

        while (true) {
            System.out.println("\nSecretary Functions:");
            System.out.println("1.  Display Secretary Information");
            System.out.println("2.  Get Secretary Role");
            System.out.println("3.  Get Secretary Shift");
            System.out.println("4.  Show Available Doctors");
            System.out.println("5.  Book Visiting Appointment");
            System.out.println("6.  Book Stay Appointment");
            System.out.println("7.  Generate Bill");
            System.out.println("8.  Process Payment");
            System.out.println("9.  Display Appointment Details");
            System.out.println("10. Display Bill Details");
            System.out.println("11. Exit");
            System.out.print("Choose an option (1-11): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    displaySecretaryInfo();
                    break;
                case "2":
                    displaySecretaryRole();
                    break;
                case "3":
                    displaySecretaryShift();
                    break;
                case "4":
                    showAvailableDoctors();
                    break;
                case "5":
                    bookVisitingAppointment();
                    break;
                case "6":
                    bookStayAppointment();
                    break;
                case "7":
                    generateBill();
                    break;
                case "8":
                    processPayment();
                    break;
                case "9":
                    displayAppointmentDetails();
                    break;
                case "10":
                    displayBillDetails();
                    break;
                case "11":
                    System.out.println("Exiting Secretary Control Panel. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1-11.");
            }
        }
    }

    /**
     * Display secretary information
     */
    private void displaySecretaryInfo() {
        System.out.println("\n--- Secretary Information ---");
        secretary.displayInfo();
    }

    /**
     * Display secretary role
     */
    private void displaySecretaryRole() {
        System.out.println("\n--- Secretary Role ---");
        System.out.println("Role: " + secretary.getRole());
    }

    /**
     * Display secretary shift
     */
    private void displaySecretaryShift() {
        System.out.println("\n--- Secretary Shift ---");
        System.out.println("Shift: " + secretary.getShift());
    }

    /**
     * Show all available doctors from the database
     */
    private void showAvailableDoctors() {
        System.out.println("\n--- Available Doctors ---");
        secretary.showAvailableDoctors();
    }

    /**
     * Book a visiting appointment
     */
    private void bookVisitingAppointment() {
        System.out.println("\n--- Book Visiting Appointment ---");

        try {
            System.out.print("Enter Patient ID: ");
            String patientId = scanner.nextLine().trim();

            System.out.print("Enter Doctor Name: ");
            String doctorName = scanner.nextLine().trim();

            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            String appointmentDate = scanner.nextLine().trim();

            secretary.bookVisitingAppointment(patientId, doctorName, appointmentDate);

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid patient ID. Please enter a number.");
        } catch (Exception e) {
            System.out.println("Error booking appointment: " + e.getMessage());
        }
    }

    /**
     * Book a stay appointment
     */
    private void bookStayAppointment() {
        System.out.println("\n--- Book Stay Appointment ---");

        try {
            System.out.print("Enter Patient ID: ");
            String patientId = scanner.nextLine().trim();

            System.out.print("Enter Doctor Name: ");
            String doctorName = scanner.nextLine().trim();

            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            String appointmentDate = scanner.nextLine().trim();

            System.out.print("Enter Room ID: ");
            String roomId = scanner.nextLine().trim();

            System.out.print("Enter Days of Stay: ");
            int daysOfStay = Integer.parseInt(scanner.nextLine().trim());

            secretary.bookStayAppointment(patientId, doctorName, appointmentDate, roomId, daysOfStay);

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Please enter valid numbers for ID and Room ID.");
        } catch (Exception e) {
            System.out.println("Error booking stay appointment: " + e.getMessage());
        }
    }

    /**
     * Generate a bill for the current appointment
     */
    private void generateBill() {
        System.out.println("\n--- Generate Bill ---");
        try {
            secretary.GenerateBill();
        } catch (Exception e) {
            System.out.println("Error generating bill: " + e.getMessage());
            System.out.println("(Make sure an appointment has been booked first.)");
        }
    }

    /**
     * Process payment using PayPal or Insurance
     */
    private void processPayment() {
        System.out.println("\n--- Process Payment ---");
        try {
            System.out.print("Enter Bill ID: ");
            String billId = scanner.nextLine().trim();

            System.out.print("Enter Amount to Pay: $");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            System.out.println("Select Payment Method:");
            System.out.println("1. PayPal");
            System.out.println("2. Insurance");
            System.out.print("Choose (1-2): ");
            String paymentChoice = scanner.nextLine().trim();

            switch (paymentChoice) {
                case "1":
                    Paypal paypal = new Paypal();
                    paypalAdapter paypalProcessor = new paypalAdapter(paypal);
                    secretary.Payment(billId, amount, paypalProcessor);
                    break;
                case "2":
                    InsuranceAdaptor insuranceProcessor = new InsuranceAdaptor();
                    secretary.Payment(billId, amount, insuranceProcessor);
                    break;
                default:
                    System.out.println("Invalid payment method selected.");
                    return;
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Please enter valid Bill ID and Amount.");
        } catch (Exception e) {
            System.out.println("Error processing payment: " + e.getMessage());
            System.out.println("(Make sure a bill has been generated first.)");
        }
    }

    /**
     * Display appointment details
     */
    private void displayAppointmentDetails() {
        System.out.println("\n--- Appointment Details ---");
        try {
            System.out.print("Enter Bill ID: ");
            String billId = scanner.nextLine().trim();
            secretary.DisplayAppointmentDetails(billId);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid Bill ID. Please enter a number.");
        } catch (Exception e) {
            System.out.println("Error displaying appointment details: " + e.getMessage());
            System.out.println("(Make sure an appointment has been booked first.)");
        }
    }

    /**
     * Display bill details
     */
    private void displayBillDetails() {
        System.out.println("\n--- Bill Details ---");
        try {
            System.out.print("Enter Bill ID: ");
            String billId = scanner.nextLine().trim();
            secretary.DisplayBillDetails(billId);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid Bill ID. Please enter a number.");
        } catch (Exception e) {
            System.out.println("Error displaying bill details: " + e.getMessage());
            System.out.println("(Make sure a bill has been generated first.)");
        }
    }

    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        SecretaryUI ui = new SecretaryUI();
        ui.start();
    }
}