package Package4;

import Package1.Bill;
import Package1.HospitalServicFactory;
import Package1.OutPatientServiceFactory;
import Package1.RoomBill;
import Package1.StayPatientServiceFactory;
import Package1.VistingAppointment;
import Package1.VisitingBill;
import Package1.Payement_System.Insurance;
import Package1.Payement_System.InsuranceAdaptor;
import Package1.Payement_System.Paypal;
import Package1.Payement_System.paypalAdapter;
import Package2.HospitalServiceController;
import Package3.BillDAO;
import Package1.roomsystemfactoryflyweight.Room;
import Package3.RoomDAO;

import java.util.Scanner;

public class BillUI {

    private Scanner scanner;
    private Bill currentBill;
    private int currentBillId;
    private double currentAmount;

    public BillUI() {
        this.scanner = new Scanner(System.in);
        this.currentBill = null;
        this.currentBillId = -1;
    }

    public void start() {
        System.out.println("========================================");
        System.out.println("           Billing Management");
        System.out.println("========================================");

        while (true) {
            System.out.println("\nBill Options:");
            System.out.println("1. Create Visiting Bill");
            System.out.println("2. Create Stay/Room Bill");
            System.out.println("3. Show Last Bill Details");
            System.out.println("4. Process Payment for Last Bill");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            String choice = scanner.nextLine();
            try {
                switch (choice) {
                    case "1":
                        createVisitingBill();
                        break;
                    case "2":
                        createRoomBill();
                        break;
                    case "3":
                        showCurrentBill();
                        break;
                    case "4":
                        new PaymentUI().startForAmount(currentBillId, currentAmount, currentBill);
                        break;
                    case "5":
                        System.out.println("Exiting Billing Management.");
                        return;
                    default:
                        System.out.println("Invalid option. Enter a value between 1 and 5.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void createVisitingBill() throws Exception {
        System.out.print("Enter Patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());

        HospitalServiceController controller = new HospitalServiceController(new OutPatientServiceFactory());
        Bill bill = controller.CreateBill(patientId, 0);
        if (bill instanceof VisitingBill) {
            VisitingBill visitingBill = (VisitingBill) bill;
            visitingBill.generateBill(patientId, 0, null);
            currentBill = visitingBill;
            currentBillId = visitingBill.billId;
            currentAmount = visitingBill.calculateamount(null, 0);
            System.out.println("Visiting bill created with amount: $" + currentAmount);
            BillDAO.getInstance().addBill(patientId, currentAmount, "Unpaid");
        } else {
            System.out.println("Failed to create visiting bill.");
        }
    }

    private void createRoomBill() throws Exception {
        System.out.print("Enter Patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Room ID: ");
        int roomId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Days of Stay: ");
        int days = Integer.parseInt(scanner.nextLine());

        Room room = RoomDAO.getInstance().getRoomById(roomId);
        HospitalServiceController controller = new HospitalServiceController(new StayPatientServiceFactory(roomId));
        Bill bill = controller.CreateBill(patientId, days);

        if (bill instanceof RoomBill) {
            RoomBill roomBill = (RoomBill) bill;
            currentBill = roomBill;
            currentBillId = roomBill.billId;
            currentAmount = roomBill.amount;
            System.out.println("Room bill created with amount: $" + currentAmount);
            BillDAO.getInstance().addBill(patientId, currentAmount, "Unpaid");
        } else {
            System.out.println("Failed to create room bill.");
        }
    }

    private void showCurrentBill() throws Exception {
        if (currentBill == null) {
            System.out.println("No active bill created yet.");
            return;
        }

        System.out.println("\n--- Current Bill Details ---");
        currentBill.getBillDetails(currentBillId);

        try {
            var billRecord = BillDAO.getInstance().getBillById(currentBillId);
            System.out.println("(DB) " + billRecord);
        } catch (Exception e) {
            System.out.println("Bill not yet saved in DB or not found: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new BillUI().start();
    }
}