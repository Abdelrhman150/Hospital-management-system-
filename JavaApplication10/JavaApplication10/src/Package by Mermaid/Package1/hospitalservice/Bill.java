package Package1.hospitalservice;

import Package1.payment.PaymentProcessor;
import Package1.room.Room;

public interface Bill {
    public void generateBill(String patientId , int daysOfStay);
    public void getBillDetails(String billId);
    public void setPaymentProcessor(PaymentProcessor paymentProcessor);
    public double calculateamount();
    public String getBillId();
}
