package Package1.payment;

public class InsuranceAdaptor implements PaymentProcessor {
    private Insurance insurance;
     
    public InsuranceAdaptor(Insurance insurance) {
        this.insurance = insurance;
    }

    @Override
    public void processPayment(double amount) {
        insurance.insurancePayment(amount);
    }
    
}
