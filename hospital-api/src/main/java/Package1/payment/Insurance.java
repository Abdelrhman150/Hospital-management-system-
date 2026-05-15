package Package1.payment;

public class Insurance {
    int insuranceDiscountPercentage = 70;
    public void insurancePayment(double amount ){
        amount = amount - (amount * insuranceDiscountPercentage / 100);
        System.out.println("Applying insurance discount of " + insuranceDiscountPercentage + "%...");   
        System.out.println("Processing insurance payment of $" + amount + "...");
        System.out.println("Insurance payment processed successfully.");
    }   
}
