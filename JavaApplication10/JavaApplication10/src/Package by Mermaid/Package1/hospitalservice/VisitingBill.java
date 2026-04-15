package Package1.hospitalservice;

import Package1.payment.*;
import Package1.room.Room;
import Package2.IdGenerator;
public class VisitingBill implements Bill {
    public String billId;
    public String patientId;
    public String billingDate;
    public double amount;
    PaymentProcessor paymentProcessor;
    public Room room; 
    

    @Override
    public void generateBill(String patientId, int daysOfStay) {
        this.patientId = patientId;
        amount = calculateamount();
        this.billId = IdGenerator.getInstance().nextBillId() ;
        System.out.println(billId);
        billingDate = java.time.LocalDate.now().toString(); // Get current date as billing date
    }
    

    @Override
    public double calculateamount() {
        double VistingFee = 50.0;
        return VistingFee;
    }

    @Override
    public void getBillDetails(String billId) {
        // DAO logic moved to controller
    }

    @Override
    public void setPaymentProcessor(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    @Override
    public String getBillId() {
        return billId;
    }
}
