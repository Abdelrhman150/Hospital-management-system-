package Package1.hospitalservice;

import Package1.payment.PaymentProcessor;
import Package1.room.Room;
import Package2.*;
public class RoomBill implements Bill {
    public String billId;
    public String patientId;
    public double amount;
    public String billingDate;
    public int DaysOfStay;
    private Room room;
    PaymentProcessor paymentProcessor;


    // Constructor
    public RoomBill(Room room) {
        this.room = room;

    }

    @Override
    public void generateBill(String patientId, int daysOfStay, Room room) {

        this.patientId = patientId;
        this.DaysOfStay = daysOfStay;
        amount = calculateamount();
        this.billId = IdGenerator.getInstance().nextBillId() ;
        billingDate = java.time.LocalDate.now().toString(); // Get current date as billing date
    }

    @Override
    public void getBillDetails(String billId) {
        // DAO logic moved to controller
    }

    @Override
    public double calculateamount() {
        this.amount = room.getDailyRate() * DaysOfStay;
        return this.amount;
    }

    @Override
    public String getBillId() {
        return billId;
    }

    @Override
    public void setPaymentProcessor(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
        // Payment processing immediately moved to controller
    }
}
