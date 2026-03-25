package Package1.Payement_System;

public class InsuranceAdaptor implements PaymentProcessor {
    private Insurance insurance;
     

    @Override
    public void processPayment(double amount) {
        insurance.insurancePayment(amount);
    }
    
}
