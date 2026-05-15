package Package1.hospitalservice;

import Package1.payment.*;
import Package1.room.Room;
import Package2.IdGenerator;

public class VisitingBill extends Bill {
    public double amount;
    

    @Override
    public double calculateamount() {
        double VistingFee = 50.0;
        return VistingFee;
    }

    
}
