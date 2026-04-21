package Package1.hospitalservice;

import Package1.payment.PaymentProcessor;
import Package2.IdGenerator;

public abstract class Bill {
    protected String patientId;
    protected int daysOfStay;
    protected double amount;
    protected String billId;
    protected String billingDate;
    protected PaymentProcessor paymentProcessor;


    public void generateBill(String patientId , int daysOfStay){
        this.patientId = patientId;
        this.daysOfStay = daysOfStay;
        amount = calculateamount();
        this.billId = IdGenerator.getInstance().nextBillId() ;
        billingDate = java.time.LocalDate.now().toString(); // Get current date as billing date
    }

    public void setPaymentProcessor(PaymentProcessor paymentProcessor){
        this.paymentProcessor = paymentProcessor;
    }
    
    public String getBillId(){
        return billId;
    }
    
    public abstract double calculateamount();
    
}
