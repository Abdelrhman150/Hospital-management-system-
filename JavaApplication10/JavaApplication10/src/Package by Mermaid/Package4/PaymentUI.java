package Package4;

import Package1.Bill;
import Package1.Payement_System.Insurance;
import Package1.Payement_System.InsuranceAdaptor;
import Package1.Payement_System.Paypal;
import Package1.Payement_System.PaymentProcessor;
import Package1.Payement_System.paypalAdapter;
import Package1.RoomBill;
import Package1.VisitingBill;
import Package3.BillDAO;

import java.util.Scanner;

public class PaymentUI {

    private Scanner scanner;

    public PaymentUI() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("========================================");
        System.out.println("           Payment Processing");
        System.out.println("========================================");

        while (true) {
            System.out.println("\nPayment Options:");
            System.out.println("1. Pay a specific amount (cash)");
            System.out.println("2. Pay with insurance");
            System.out.println("3. Pay with PayPal");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    processCashPayment();
                    break;
                case "2":
                    processInsurancePayment();
                    break;
                case "3":
                    processPayPalPayment();
                    break;
                case "4":
                    System.out.println("Exiting Payment Processing.");
                    return;
                default:
                    System.out.println("Invalid option. Enter a value between 1 and 4.");
            }
        }
    }

    public void startForAmount(String billId, double amount, Bill bill) {
        if (bill == null) {
            System.out.println("No active bill to process payment.");
            return;
        }

        System.out.println("\nProcess payment for Bill ID: " + billId + " Amount: $" + amount);
        System.out.println("Choose payment method:");
        System.out.println("1. Cash");
        System.out.println("2. Insurance");
        System.out.println("3. PayPal");
        System.out.print("Option: ");

        String opt = scanner.nextLine();
        PaymentProcessor processor;

        switch (opt) {
            case "1":
                processor = a -> System.out.println("Processing cash payment of $" + amount + "...\nPayment completed.");
                break;
            case "2":
                processor = new InsuranceAdaptor(new Insurance());
                break;
            case "3":
                processor = new paypalAdapter(new Paypal());
                break;
            default:
                System.out.println("Invalid selection.");
                return;
        }

        if (bill instanceof RoomBill) {
            ((RoomBill) bill).setPaymentProcessor(processor);
        } else if (bill instanceof VisitingBill) {
            ((VisitingBill) bill).setPaymentProcessor(processor);
        }

        try {
            BillDAO.getInstance().markAsPaid(billId);
            System.out.println("Bill status updated to Paid in database.");
        } catch (Exception e) {
            System.out.println("Error updating status in DB: " + e.getMessage());
        }
    }

    private void processCashPayment() {
        System.out.print("Enter amount to pay: ");
        String input = scanner.nextLine();
        try {
            double amount = Double.parseDouble(input);
            System.out.println("Processing cash payment of $" + amount + "...");
            System.out.println("Cash payment completed.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        }
    }

    private void processInsurancePayment() {
        System.out.print("Enter amount to pay: ");
        String input = scanner.nextLine();
        try {
            double amount = Double.parseDouble(input);
            new InsuranceAdaptor(new Insurance()).processPayment(amount);
            System.out.println("Insurance payment requested.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        }
    }

    private void processPayPalPayment() {
        System.out.print("Enter amount to pay: ");
        String input = scanner.nextLine();
        try {
            double amount = Double.parseDouble(input);
            new paypalAdapter(new Paypal()).processPayment(amount);
            System.out.println("PayPal payment requested.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        }
    }

    public static void main(String[] args) {
        new PaymentUI().start();
    }
}