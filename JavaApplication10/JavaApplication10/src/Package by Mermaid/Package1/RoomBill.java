package Package1;

import Package1.Payement_System.PaymentProcessor;
import Package1.roomsystemfactoryflyweight.Room;
import Package2.*;
import Package3.BillDAO;

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
        this.room = room;
        BillDAO billDAO = BillDAO.getInstance() ;
        billId = IdGenerator.getInstance().nextRecordId(); ///////////////
        amount = calculateamount(room, daysOfStay);;
        try {
            billDAO.addBill(patientId, amount, "Unpaid");
        } catch (Exception e) {
            e.printStackTrace();
        }
        billingDate = java.time.LocalDate.now().toString(); // Get current date as billing date
    }

    @Override
    public void getBillDetails(String billId) {
            BillDAO billDAO = BillDAO.getInstance();
            try {
                billDAO.BillDetails(billId);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public double calculateamount(Room room, double days) {
        this.amount = room.getDailyRate() * (int) days;
        return this.amount;
    }

    @Override
    public String getBillId() {
        return billId;
    }

    @Override
    public void setPaymentProcessor(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
        paymentProcessor.processPayment(amount); // Process payment immediately for room bill
    }
}