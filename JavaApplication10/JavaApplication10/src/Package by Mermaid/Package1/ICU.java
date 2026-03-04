package Package1;

import java.io.*;
import java.util.*;

public class ICU implements Room {

    // Constructor
    public ICU() {
        // waits for the roomID to be assigned by ID generator
        roomType = RoomType.ICU;
        occupancyStatus = RoomStatus.Available;
        dailyRate = 500.0; 
    }

   
    public int roomId;
    public RoomType roomType;
    public RoomStatus occupancyStatus;
    public double dailyRate;
   
   
    @Override
    public void markOccupied() {
        occupancyStatus = RoomStatus.Occupied;
    }

    
        @Override
    public void markAvailable() {
        occupancyStatus = RoomStatus.Available; 
    }

    @Override
    public void markUnderMaintenance() {
        occupancyStatus = RoomStatus.UnderMaintenance; 
    }

    /**
     * @param days
     */@Override   
    public double calculateCost(int days) {
        double totalCost = dailyRate * days;
        return totalCost;
    }
}