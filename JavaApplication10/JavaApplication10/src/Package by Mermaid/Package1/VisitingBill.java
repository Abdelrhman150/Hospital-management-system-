package Package1;

public class VisitingBill implements Bill {
    public int billId;
    public int patientId;
    public String billingDate;
    public double VistingFee = 50.0; // Flat fee for outpatient services
    

    @Override
    public void generateBill(int patientId, int daysOfStay) {
        this.patientId = patientId;
        billId = IDGenerator.generateBillID(); ///////////////
        billingDate = java.time.LocalDate.now().toString(); // Get current date as billing date
    }

    @Override
    public void getBillDetails(int billId) {
        if (this.billId == billId) {
            System.out.println("===============================");
            System.out.println("Bill Details:");
            System.out.println("================================");
            System.out.println("Billing Date: " + this.billingDate);
            System.out.println("Bill ID: " + this.billId);
            System.out.println("Patient ID: " + this.patientId);
            System.out.println("Visiting Fee: $" + this.VistingFee);
        } else {
            System.out.println("Bill not found for Bill ID: " + billId);
        }
    }
}