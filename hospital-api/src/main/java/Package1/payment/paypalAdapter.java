package Package1.payment;

public class paypalAdapter implements PaymentProcessor {
    private Paypal paypal;
    
    public paypalAdapter(Paypal paypal) {
        this.paypal = paypal;
    }
    
    @Override
    public void processPayment(double amount) {
        paypal.paypalPayment(amount);
    }
    
}
