package Package1;
import Package2.*;

public class RoomBill implements Bill {
    public int billId;
    public int patientId;
    public double amount;
    public String billingDate;
    public int DaysOfStay;
    private Room room;


    // Constructor
    public RoomBill(Room room) {
    this.room = room;
    }

    @Override
    public void generateBill(int patientId , int daysOfStay) {

        this.patientId = patientId;
        this.DaysOfStay = daysOfStay;
        billId = IdGenerator.getInstance().nextRecordId(); ///////////////
        amount = room.calculateCost(daysOfStay);
        billingDate = java.time.LocalDate.now().toString(); // Get current date as billing date
    }

    @Override
    public void getBillDetails(int billId) {
        if (this.billId == billId) {
            System.out.println("===============================");
            System.out.println("Bill Details:");
            System.out.println("================================");
            System.out.println("Billing Date: " + this.billingDate);
            System.out.println("Bill ID: " + this.billId);
            System.out.println("Patient ID: " + this.patientId);
            System.out.println("Days of Stay: " + this.DaysOfStay);
            System.out.println("Amount: $" + this.amount);
        } else {
            System.out.println("Bill not found for Bill ID: " + billId);
        }
    }
}