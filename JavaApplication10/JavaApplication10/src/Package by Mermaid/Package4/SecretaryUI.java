package Package4;

import Package1.staff.Doctor;
import Package1.staff.Secretary;
import Package1.payment.Insurance;
import Package1.payment.InsuranceAdaptor;
import Package1.payment.Paypal;
import Package1.payment.paypalAdapter;
import Package3.DoctorDAO;
import Package2.SecretaryController;

import java.util.List;
import java.util.Scanner;

/**
 * UI for Secretary operations
 * Provides console interface to use all Secretary functions
 */
public class SecretaryUI {

    private Secretary secretary;
    private SecretaryController controller;
    private Scanner scanner;

    /**
     * Constructor with Secretary instance
     */
    public SecretaryUI(Secretary secretary) {
        this.secretary = secretary;
        this.controller = new SecretaryController();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Default constructor for demo purposes
     */
    public SecretaryUI() {
        // Create a demo secretary for testing
        this.secretary = new Secretary("SEC001", "John Doe", "123-456-7890", "john.doe@hospital.com", "Morning", "DEP001");
        this.controller = new SecretaryController();
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
            System.out.println("5.  Manage Appointment");
            System.out.println("6.  Display Appointment Details");
            System.out.println("7.  Display Bill Details");
            System.out.println("8.  Exit");
            System.out.print("Choose an option (1-8): ");

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
                    manageAppointmentMenu();
                    break;
                case "6":
                    displayAppointmentDetails();
                    break;
                case "7":
                    displayBillDetails();
                    break;
                case "8":
                    System.out.println("Exiting Secretary Control Panel. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1-8.");
            }
        }
    }

    /**
     * Sub-menu for Managing Appointments (Booking, Billing, Payment)
     */
    private void manageAppointmentMenu() {
        while (true) {
            System.out.println("\n--- Manage Appointment ---");
            System.out.println("1. Book Appointment");
            System.out.println("2. Generate Bill");
            System.out.println("3. Process Payment");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option (1-4): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    bookAppointmentSubMenu();
                    break;
                case "2":
                    generateBill();
                    break;
                case "3":
                    processPayment();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1-4.");
            }
        }
    }

    /**
     * Sub-sub-menu for Booking Appointments
     */
    private void bookAppointmentSubMenu() {
        while (true) {
            System.out.println("\n--- Book Appointment ---");
            System.out.println("1. Visiting Appointment");
            System.out.println("2. Stay Appointment");
            System.out.println("3. Back");
            System.out.print("Choose an option (1-3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    bookVisitingAppointment();
                    return; // Return to parent menu after operation
                case "2":
                    bookStayAppointment();
                    return; // Return to parent menu after operation
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1-3.");
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
        DoctorDAO doctorDAO = DoctorDAO.getInstance();
        try {
            List<Doctor> doctors = doctorDAO.getAvailableDoctors();
            for (Doctor doc : doctors) {
                System.out.println("ID: " + doc.getId() + ", Name: " + doc.getName()
                        + ", Specialization: " + doc.getSpecialization());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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

            System.out.print("Enter Appointment Date (yyyy-MM-dd): ");
            String appointmentDate = scanner.nextLine().trim();

            System.out.print("Enter Room ID: ");
            String roomId = scanner.nextLine().trim();

            String AppointmentID = controller.bookVisitingAppointment(patientId, doctorName, appointmentDate, roomId);
            controller.DisplayAppointmentDetails(AppointmentID);

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

            System.out.print("Enter Appointment Date (yyyy-MM-dd): ");
            String appointmentDate = scanner.nextLine().trim();

            System.out.print("Enter Room ID: ");
            String roomId = scanner.nextLine().trim();

            System.out.print("Enter Days of Stay: ");
            int daysOfStay = Integer.parseInt(scanner.nextLine().trim());

            String AppointmentID = controller.bookStayAppointment(patientId, doctorName, appointmentDate, roomId, daysOfStay);
            if (AppointmentID != null) {
                System.out.println("Stay appointment booked successfully!");
                controller.DisplayAppointmentDetails(AppointmentID);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format. Please enter valid numbers for days of stay.");
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
            controller.GenerateBill();
            System.out.println();
            controller.DisplayBillDetailsAfterGeneration();
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
            System.out.println("Select Payment Method:");
            System.out.println("1. PayPal");
            System.out.println("2. Insurance");
            System.out.print("Choose (1-2): ");
            String paymentChoice = scanner.nextLine().trim();

            switch (paymentChoice) {
                case "1":
                    Paypal paypal = new Paypal();
                    paypalAdapter paypalProcessor = new paypalAdapter(paypal);
                    controller.Payment(paypalProcessor);
                    break;
                case "2":
                    InsuranceAdaptor insuranceProcessor = new InsuranceAdaptor(new Insurance());
                    controller.Payment(insuranceProcessor);
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
            System.out.print("Enter Appointment ID: ");
            String appointmentId = scanner.nextLine().trim();
            controller.DisplayAppointmentDetails(appointmentId);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid Appointment ID. Please enter a number.");
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
            controller.DisplayBillDetails(billId);
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