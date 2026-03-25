package Package1;

import Package1.Payement_System.*;
import Package1.roomsystemfactoryflyweight.Room;
import Package2.IdGenerator;
import Package3.BillDAO;

public class VisitingBill implements Bill {
    public String billId;
    public String patientId;
    public String billingDate;
    public double VistingFee = 50.0; // Flat fee for outpatient services
    PaymentProcessor paymentProcessor;
    public Room room; // Added room reference for payment processing
    

    @Override
    public void generateBill(String patientId, int daysOfStay, Room room) {
        this.patientId = patientId;
        this.room = room;
        billId = IdGenerator.getInstance().nextRecordId(); ///////////////
        billingDate = java.time.LocalDate.now().toString(); // Get current date as billing date
    }

    @Override
    public double calculateamount(Room room, double days) {
        return VistingFee;
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
    public void setPaymentProcessor(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
        paymentProcessor.processPayment(VistingFee); // Process payment immediately for visiting bill
    }
}