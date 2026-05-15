package Package1.payment;

public class CashPayment implements PaymentProcessor {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing cash payment of $" + amount + "...");
        System.out.println("Cash payment processed successfully.");
    }
}