package Package1;

import Package1.Payement_System.PaymentProcessor;
import Package1.roomsystemfactoryflyweight.Room;

public interface Bill {
    public void generateBill(int patientId , int daysOfStay, Room room);
    public void getBillDetails(int billId);
    public void setPaymentProcessor(PaymentProcessor paymentProcessor);
    public double calculateamount(Room room, double days);
}