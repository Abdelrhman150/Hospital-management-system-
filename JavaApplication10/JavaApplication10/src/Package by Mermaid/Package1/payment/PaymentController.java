package Package1.payment;

import Package1.payment.PaymentProcessor;

public class PaymentController {

    private PaymentProcessor paymentProcessor;

    public void setPaymentProcessor(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void processPayment(double amount) {
        if (paymentProcessor == null) {
            System.out.println("No payment method selected.");
            return;
        }

        paymentProcessor.processPayment(amount);
    }
}